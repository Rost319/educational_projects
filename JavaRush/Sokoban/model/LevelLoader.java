package com.javarush.task.task34.task3410.model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class LevelLoader {
    private Path levels;

    public LevelLoader(Path levels) {
        this.levels = levels;
    }

    public GameObjects getLevel(int level) {
        if(level > 60) level = level%60;
        Set<Wall> wallSet = new HashSet<>();
        Set<Box> boxSet = new HashSet<>();
        Set<Home> homeSet = new HashSet<>();
        Player player = null;

        try(BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(levels)))) {
            while (!reader.readLine().equals("Maze: " + level)){

            }
            reader.readLine();
            int sizeX = Integer.parseInt(reader.readLine().split(" ")[2]);
            int sizeY = Integer.parseInt(reader.readLine().split(" ")[2]);
            for (int i = 0; i < 3; i++) {
                reader.readLine();
            }

            for (int i = 0; i < sizeY; i++) {
                for (int j = 0; j < sizeX; j++) {
                    int yc = Model.FIELD_CELL_SIZE/2 + i * Model.FIELD_CELL_SIZE;
                    int xc = Model.FIELD_CELL_SIZE/2 + j * Model.FIELD_CELL_SIZE;
                    char c = (char) reader.read();
                    switch (c){
                        case 'X':
                            wallSet.add(new Wall(xc, yc));
                            break;
                        case'*':
                            boxSet.add(new Box(xc,yc));
                            break;
                        case'.':
                            homeSet.add(new Home(xc,yc));
                            break;
                        case'&':
                            boxSet.add(new Box(xc,yc));
                            homeSet.add(new Home(xc, yc));
                            break;
                        case '@':
                            player = new Player(xc, yc);
                            break;
                    }
                }
                reader.read();
            }

            GameObjects gameObjects = new GameObjects(wallSet, boxSet, homeSet, player);
            return gameObjects;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
