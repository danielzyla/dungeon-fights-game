# Dungeon-fights - board game <img src="/src/main/resources/img/skull.png">

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Instruction](#instruction)
  - [Install](#install)
  - [Steering](#steering)
  - [Rules](#rules)
## General info
The main character goes through dungeons full of dark creatures to get gold.
<img src="/src/main/resources/img/preview.png">
## Technologies
* Java OpenJDK 11 (UI is rendered with Java Swing library)
* Maven
## Instruction
### Install
POM.xml file is configured to build .jar file ready to use.
Once installed package with Maven choose the one with dependencies.
To execute the app just try the following:
```
java -jar dungeon-fights-1.0-SNAPSHOT-jar-with-dependencies.jar
```
### Steering
Use arrows (UP, DOWN, LEFT, RIGHT) to move around.\
Press SPACEBAR to speed up.\
Press F to shoot.
### Rules
Get all the gold on the board to pass the level.\
You have three lives. Avoid dark creatures. You can shoot them.\
The ability to shoot is unlocked after getting a rune.\
The results are saved in the database. The best results are displayed at the end of the game.
