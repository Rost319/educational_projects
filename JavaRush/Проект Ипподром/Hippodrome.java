package com.javarush.task.task21.task2113;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Hippodrome {

    static Hippodrome game;
    private List<Horse> horses;

    public List<Horse> getHorses() {
        return horses;
    }

    public Hippodrome(List<Horse> horses) {
        this.horses = horses;
    }

    public static void main(String[] args) {
        Horse horse1 = new Horse("horseOne",3,0);
        Horse horse2 = new Horse("horseTwo",3,0);
        Horse horse3 = new Horse("horseThree",3,0);
        ArrayList<Horse> list = new ArrayList<>();
        Collections.addAll(list, horse1, horse2,horse3);
        game = new Hippodrome(list);
        game.run();
        game.printWinner();

    }

    void run() {
        for (int i = 1; i <= 100; i++) {
            move();
            print();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void move() {
        for (int i = 0; i < horses.size(); i++) {
            horses.get(i).move();
        }
    }

    public void print() {
        for (int i = 0; i < horses.size(); i++) {
            horses.get(i).print();
        }
        for (int i = 0; i < 10; i++) {
            System.out.println("");
        }
    }

    public Horse getWinner() {
        double max = 0;
        for (int i = 0; i < horses.size(); i++) {
            if(horses.get(i).distance > max){
                max = horses.get(i).distance;
            }
        }
        for (int i = 0; i < horses.size(); i++) {
            if(horses.get(i).distance == max){
                return horses.get(i);
            }
        }
        return null;
    }

    public void printWinner() {
        System.out.println("Winner is " + getWinner().name + "!");
    }


}
