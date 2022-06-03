package com.javarush.task.task35.task3513;

public class MoveEfficiency implements Comparable<MoveEfficiency> {
    private int numberOfEmptyTiles;
    private int score;
    private Move move;

    public MoveEfficiency(int numberOfEmptyTiles, int score, Move move) {
        this.numberOfEmptyTiles = numberOfEmptyTiles;
        this.score = score;
        this.move = move;
    }

    public Move getMove() {
        return move;
    }

    @Override
    public int compareTo(MoveEfficiency o) {
        int rez = (this.numberOfEmptyTiles > o.numberOfEmptyTiles ? 1 : this.numberOfEmptyTiles < o.numberOfEmptyTiles ? -1 : 0);

        if(rez == 0){
          rez = (this.score > o.score ? 1 : this.score < o.score ? -1 : 0);
        }
        return rez;
    }
}
