package com.javarush.task.task34.task3410.model;

import com.javarush.task.task34.task3410.controller.EventListener;

import java.net.URISyntaxException;
import java.nio.file.Paths;


public class Model {

    public static final int FIELD_CELL_SIZE = 20;

    private EventListener eventListener;
    private GameObjects gameObjects;
    private int currentLevel = 1;
    LevelLoader levelLoader;

    public Model() {
        try {
            levelLoader = new LevelLoader(Paths.get(getClass().getResource("../res/levels.txt").toURI()));
        }catch (URISyntaxException e) {
        }
    }

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    public GameObjects getGameObjects() {
        return gameObjects;
    }

    public void restartLevel(int level) {
        gameObjects = levelLoader.getLevel(level);
    }

    public void restart() {
        restartLevel(currentLevel);
    }

    public void startNextLevel() {
        currentLevel++;
        restartLevel(currentLevel);
    }

    public void move(Direction direction) {
        Player player = gameObjects.getPlayer();
       if(checkWallCollision(player, direction)){
           return;
       }
       if(checkBoxCollisionAndMoveIfAvailable(direction)){
           return;
       }
        int dx = direction == Direction.LEFT ? -FIELD_CELL_SIZE : direction == Direction.RIGHT ? +FIELD_CELL_SIZE : 0;
        int dy = direction == Direction.DOWN ? +FIELD_CELL_SIZE : direction == Direction.UP ? -FIELD_CELL_SIZE : 0;
        player.move(dx, dy);
        checkCompletion();
    }

    public boolean checkWallCollision(CollisionObject gameObject, Direction direction) {
        for (GameObject gameObjectWall : gameObjects.getWalls()){
            if(gameObject.isCollision(gameObjectWall, direction))
                return true;
        }
        return false;
    }

    public boolean checkBoxCollisionAndMoveIfAvailable(Direction direction) {
        for (Box box : gameObjects.getBoxes()){
            if(gameObjects.getPlayer().isCollision(box, direction)){
                for(GameObject gameObjectBox : gameObjects.getBoxes()){
                    if(box.isCollision(gameObjectBox, direction))
                        return true;
                }
                if(checkWallCollision(box, direction)){
                    return true;
                }
                int dx = direction == Direction.LEFT ? -FIELD_CELL_SIZE : direction == Direction.RIGHT ? +FIELD_CELL_SIZE : 0;
                int dy = direction == Direction.DOWN ? +FIELD_CELL_SIZE : direction == Direction.UP ? -FIELD_CELL_SIZE : 0;
                box.move(dx, dy);
            }
        }
        return false;
    }

    public void checkCompletion() {
        int count = 0;
        for(Home home : gameObjects.getHomes()){
            for(Box box : gameObjects.getBoxes()){
                if(box.x == home.x && box.y == home.y){
                    count++;
                }
            }
        }
        if(count == gameObjects.getHomes().size())
            eventListener.levelCompleted(currentLevel);
    }
}
