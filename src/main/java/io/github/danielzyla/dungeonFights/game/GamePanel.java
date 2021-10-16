package io.github.danielzyla.dungeonFights.game;

import io.github.danielzyla.dungeonFights.audio.AudioPlayer;
import io.github.danielzyla.dungeonFights.component.Bullet;
import io.github.danielzyla.dungeonFights.component.Component;
import io.github.danielzyla.dungeonFights.component.Freak;
import io.github.danielzyla.dungeonFights.component.Hero;
import io.github.danielzyla.dungeonFights.service.ResultService;
import io.github.danielzyla.dungeonFights.view.DungeonFightsGameFrame;
import io.github.danielzyla.dungeonFights.view.GameBoard;
import io.github.danielzyla.dungeonFights.view.ScorePanel;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.Timer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    Timer t = new Timer(10, this);
    private GameBoard gameBoard;
    private final List<String> boardList;
    private final ScorePanel scorePanel;
    private Set<Component> componentSet;
    private final LinkedList<Hero> heroes = new LinkedList<>();
    private final Set<Integer> pressedKeys = new HashSet<>();
    private Hero current;
    private final List<Bullet> bulletList = new ArrayList<>();
    private final AudioPlayer soundTrack;
    private final ResultService resultService;

    public GamePanel(ScorePanel scorePanel) throws IOException, LineUnavailableException, URISyntaxException {
        this.scorePanel = scorePanel;
        this.resultService = new ResultService();
        this.boardList = getBoardList();
        this.gameBoard = new GameBoard(this, boardList.get(0));
        this.componentSet = gameBoard.getComponentSet();
        this.soundTrack = new AudioPlayer();
        IntStream.range(0, 3).forEach(i -> heroes.add(i, new Hero(50, 50, this, scorePanel)));
        scorePanel.setRemainingHeroCount(heroes.size());
        scorePanel.setGamePanel(this);
        addKeyListener(this);
        setFocusable(true);
    }

    private List<String> getBoardList() throws IOException {
        List<String> boardList = new ArrayList<>();
        File file = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        if (file.getPath().endsWith(".jar")) {
            final JarFile jar = new JarFile(file);
            final Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                final String name = jarEntry.getName();
                if (name.startsWith("csv/")) {
                    String boardFileName = jarEntry.getName().substring(4);
                    if (boardFileName.length() > 0) boardList.add(jarEntry.getName().substring(4));
                }
            }
            jar.close();
        } else {
            File local = new File(String.valueOf(Objects.requireNonNull(getClass().getResource("/csv")).getPath()));
            int boardListSize = Objects.requireNonNull(local.listFiles()).length;
            for (int i = 1; i <= boardListSize; i++) {
                boardList.add("board-" + i + ".csv");
            }
        }
        return boardList.stream().sorted().collect(Collectors.toList());
    }

    public void startGame() {
        t.start();
        soundTrack.playSoundInLoop("soundtrack.wav");
    }

    public void levelWon() {
        t.stop();
        pressedKeys.clear();
        soundTrack.stopSoundClip();
        AudioPlayer.playSingleSound("levelWon.wav");
        openGameStatusPopUp("<html><p>CONGRATULATIONS !</p><br><p>LEVEL COMPLETE !</p></html>");
    }

    public void gameOver() {
        t.stop();
        pressedKeys.clear();
        soundTrack.stopSoundClip();
        AudioPlayer.playSingleSound("gameOver.wav");
        openGameStatusPopUp("<html><u>GAME OVER !</u></html>");
    }

    private void openGameStatusPopUp(String message) {
        Thread thread = new Thread(() -> {
            ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/hero-right.png")));
            JOptionPane.showMessageDialog(this,
                    message,
                    "Game status",
                    JOptionPane.INFORMATION_MESSAGE,
                    icon);
            SwingUtilities.invokeLater(() -> {
                if (heroes.size() == 0) {
                    saveResult();
                } else {
                    try {
                        nextLevel();
                    } catch (InterruptedException | IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        });
        thread.setDaemon(true);
        thread.start();
    }

    private void saveResult() {
        Thread thread = new Thread(() -> {
            Player player = new Player(this);
            try {
                resultService.savePlayerResult(player.getName(), scorePanel.getScore());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            SwingUtilities.invokeLater(() -> {
                try {
                    showBestScores();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });
        thread.setDaemon(true);
        thread.start();
    }

    private void showBestScores() throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                resultService.showBestScores();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            SwingUtilities.invokeLater(() -> {
                try {
                    reloadGame();
                } catch (InterruptedException | SQLException | IOException | LineUnavailableException | URISyntaxException e) {
                    e.printStackTrace();
                }
            });
        });
        thread.setDaemon(true);
        thread.start();
    }

    public void reloadGame() throws InterruptedException, SQLException, IOException, LineUnavailableException, URISyntaxException {
        ScorePanel scorePanel = new ScorePanel();
        GamePanel gamePanel = new GamePanel(scorePanel);
        new DungeonFightsGameFrame(gamePanel);
        JFrame jFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        jFrame.dispose();
    }

    private void nextLevel() throws InterruptedException, IOException {
        pressedKeys.clear();
        componentSet.clear();
        bulletList.clear();
        gameBoard = new GameBoard(this, boardList.get(nextBoardIndex()));
        componentSet = gameBoard.getComponentSet();
        JFrame jFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        jFrame.dispose();
        new DungeonFightsGameFrame(this);
        scorePanel.setPreferredSize(new Dimension(jFrame.getWidth(), 50));
        repaint();
        t.start();
        soundTrack.playSoundInLoop("soundtrack.wav");
        resultService.getContentPane().removeAll();
        current.setX(50);
        current.setY(50);
    }

    private int nextBoardIndex() {
        int currentBoardIndex = boardList.indexOf(gameBoard.getSourceFileName());
        if (currentBoardIndex < boardList.size() - 1) return currentBoardIndex + 1;
        else return 0;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        try {
            this.gameBoard.drawBoard(g2d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!heroes.isEmpty()) {
            try {
                current = heroes.getLast();
                current.draw(g2d);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!bulletList.isEmpty()) {
            bulletList.forEach(bullet -> {
                try {
                    bullet.draw(g2d);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void move() throws Exception {
        heroes.getLast().move();
        componentSet.stream()
                .filter(component -> component instanceof Freak)
                .forEach(component -> {
                    try {
                        ((Freak) component).move();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        Iterator<Bullet> bulletIterator = bulletList.iterator();
        while (bulletIterator.hasNext()) {
            Bullet next = bulletIterator.next();
            next.move(bulletIterator);
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        //TODO
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (!heroes.isEmpty()) {
            pressedKeys.add(keyEvent.getKeyCode());
            try {
                current.changePositionWith(pressedKeys);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if (!heroes.isEmpty()) {
            pressedKeys.remove(keyEvent.getKeyCode());
            try {
                current.changePositionWith(pressedKeys);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (current.isShooter() && keyEvent.getKeyCode() == 70) {
            try {
                current.shoot();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (!heroes.isEmpty()) {
            repaint();
            try {
                move();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public ScorePanel getScorePanel() {
        return scorePanel;
    }

    public LinkedList<Hero> getHeroes() {
        return heroes;
    }

    public Hero getCurrent() {
        return current;
    }

    public List<Bullet> getBulletList() {
        return bulletList;
    }

}
