package com.javarush.task.task35.task3513;

import java.util.*;

public class Model {
    private static final int FIELD_WIDTH = 4;
    private Tile [][] gameTiles;
    protected int score;
    protected int maxTile;
    private Stack<Tile[][]> previousStates = new Stack<>();
    private Stack<Integer> previousScores = new Stack<>();
    private boolean isSaveNeeded  = true;


    public Model() {
        resetGameTiles();
    }

    public Tile[][] getGameTiles() {
        return gameTiles;
    }

    private void addTile() {
        List<Tile> emptyTiles = getEmptyTiles();
        if (!emptyTiles.isEmpty()) {
            int index = (int) (Math.random() * emptyTiles.size()) % emptyTiles.size();
            Tile emptyTile = emptyTiles.get(index);
            emptyTile.value = Math.random() < 0.9 ? 2 : 4;
        }
    }


    private List<Tile> getEmptyTiles() {
        List<Tile> listempy = new ArrayList<>();
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                if (gameTiles[i][j].isEmpty()){
                    listempy.add(gameTiles[i][j]);
                }
            }
        }
        return listempy;
    }

    protected void resetGameTiles() {
        score = 0;
        maxTile = 0;
        gameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for (int i = 0; i <FIELD_WIDTH ; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                gameTiles[i][j] = new Tile();
            }
        }

        addTile();
        addTile();
    }

    private boolean compressTiles(Tile[] tiles) {
        boolean flag = false;
        for (int out = FIELD_WIDTH-1; out >= 1 ; out--) {
            for (int in = 0; in < out; in++) {
                if (tiles[in].isEmpty() && !tiles[in + 1].isEmpty()) {
                    int val = tiles[in].value;
                    tiles[in].value = tiles[in + 1].value;
                    tiles[in + 1].value = val;
                    flag = true;
                }
            }
        }
        return flag;
    }

    private boolean mergeTiles(Tile[] tiles) {
        boolean flag = false;
        for (int i = 0; i < FIELD_WIDTH-1; i++) {
            if(tiles[i].value > 0 && tiles[i+1].value == tiles[i].value){
                tiles[i].value += tiles[i+1].value;
                tiles[i+1].value = 0;
                score += tiles[i].value;
                if(tiles[i].value > maxTile){
                    maxTile = tiles[i].value;
                }
                flag = true;
                compressTiles(tiles);
            }
        }
        return flag;
    }

    private Tile[][] rotate(Tile[][] array) {
        int side = array.length;
        Tile[][] rezult = new Tile[side][side];
        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                rezult[i][j] = array[side - j - 1][i];
            }
        }
        return rezult;
    }

    public void left() {
        if(isSaveNeeded){
            saveState(gameTiles);
        }
        Tile [] tiles = new Tile[FIELD_WIDTH];
        boolean comp = false;
        boolean merge = false;
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                tiles[j] = gameTiles[i][j];
            }
            comp = compressTiles(tiles);
            merge = mergeTiles(tiles);
        }
        if(comp || merge){
            addTile();
        }

        isSaveNeeded = true;
    }


    public void right(){
        saveState(gameTiles);
        gameTiles = rotate(gameTiles);
        gameTiles = rotate(gameTiles);
        Tile [] tiles = new Tile[FIELD_WIDTH];
        boolean comp = false;
        boolean merge = false;
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                tiles[j] = gameTiles[i][j];
            }
            comp = compressTiles(tiles);
            merge = mergeTiles(tiles);
        }
        if(comp || merge){
            addTile();
        }

        gameTiles = rotate(gameTiles);
        gameTiles = rotate(gameTiles);
    }

    public void up() {
        saveState(gameTiles);
        for (int i = 0; i < 3; i++) {
            gameTiles = rotate(gameTiles);
        }

        Tile [] tiles = new Tile[FIELD_WIDTH];
        boolean comp = false;
        boolean merge = false;
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                tiles[j] = gameTiles[i][j];
            }
            comp = compressTiles(tiles);
            merge = mergeTiles(tiles);
        }
        if(comp || merge){
            addTile();
        }
            gameTiles = rotate(gameTiles);
    }

    public void down() {
        saveState(gameTiles);
        gameTiles = rotate(gameTiles);
        Tile [] tiles = new Tile[FIELD_WIDTH];
        boolean comp = false;
        boolean merge = false;
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                tiles[j] = gameTiles[i][j];
            }
            comp = compressTiles(tiles);
            merge = mergeTiles(tiles);
        }
        if(comp || merge){
            addTile();
        }
        for (int i = 0; i < 3; i++) {
            gameTiles = rotate(gameTiles);
        }
    }

    public boolean canMove(){
        boolean flag = false;
        if(getEmptyTiles().size() > 0){
            return true;
        }
            for (int i = 0; i < FIELD_WIDTH-1; i++) {
                for (int j = 0; j < FIELD_WIDTH-1; j++) {
                    if(gameTiles[i][j].value != 0 && gameTiles[i][j].value == gameTiles[i][j+1].value || gameTiles[i][j].value != 0 && gameTiles[i][j].value == gameTiles[i+1][j].value){
                        flag = true;
                    }
                }
            }
        return flag;
    }

    private void saveState(Tile[][] gameTiles){
        Tile[][] gameSave = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                gameSave[i][j] = new Tile(gameTiles[i][j].value);
            }
        }
        previousStates.push(gameSave);
        previousScores.push(score);
        isSaveNeeded = false;
    }

    public void rollback() {
        if(!previousStates.isEmpty() && !previousScores.isEmpty()){
            gameTiles = previousStates.pop();
            score = previousScores.pop();
        }
    }

    public void randomMove() {
        int n = ((int) (Math.random() * 100)) % 4;
        switch (n) {
            case 0 : left();
            break;
            case 1 : right();
            break;
            case 2 : up();
            break;
            case 3 : down();
        }
    }

    public boolean hasBoardChanged() {
        boolean flag = false;
        Tile[][] gameTileQue = previousStates.peek();
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                if(gameTiles[i][j].value != gameTileQue[i][j].value){
                    flag = true;
                }
            }
        }
        return flag;
    }

    public MoveEfficiency getMoveEfficiency(Move move) {
        MoveEfficiency moveEfficiency;
        move.move();
        if(!hasBoardChanged()){
            moveEfficiency = new MoveEfficiency(-1,0, move);
        } else {
            moveEfficiency = new MoveEfficiency(getEmptyTiles().size(),score, move);
        }
        rollback();
        return moveEfficiency;
    }

    public void autoMove() {
        Queue<MoveEfficiency> queue = new PriorityQueue<>(4, Collections.reverseOrder());
        queue.offer(getMoveEfficiency(this::left));
        queue.offer(getMoveEfficiency(this::right));
        queue.offer(getMoveEfficiency(this::up));
        queue.offer(getMoveEfficiency(this::down));
        queue.peek().getMove().move();
    }
}
