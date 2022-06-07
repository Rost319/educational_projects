package com.javarush.task.task20.task2027;

import java.util.ArrayList;
import java.util.List;

/* 
Кроссворд
*/

public class Solution {
    public static void main(String[] args) {
        int[][] crossword = new int[][]{
                {'f', 'd', 'e', 'r', 'l', 'k'},
                {'u', 's', 'a', 'm', 'e', 'o'},
                {'l', 'n', 'g', 'r', 'o', 'v'},
                {'m', 'l', 'p', 'r', 'r', 'h'},
                {'p', 'o', 'e', 'e', 'j', 'j'}
        };
        detectAllWords(crossword, "home", "same");
        /*
Ожидаемый результат
home - (5, 3) - (2, 0)
same - (1, 1) - (4, 1)
         */
    }

    public static List<Word> detectAllWords(int[][] crossword, String... words) {
        List<Word> list = new ArrayList<>();
        for(String word : words) {
            for (int row = 0; row < crossword.length; row++) {
                for (int col = 0; col < crossword[0].length; col++) {
                    if (search2D(crossword, row, col, word)){
                       Word word1 = new Word(word);
                       word1.setStartPoint(col, row);
                       word1.setEndPoint(Word.Z, Word.W);
                       list.add(word1);
                        System.out.println(word1);

                    }

                }

            }
        }

        return list;
    }

    public static boolean search2D(int[][] crossword, int row, int col, String word){
    int R = crossword.length;
    int C = crossword[0].length;
    int[] x = {-1,-1,-1,0,0,1,1,1};
    int[] y = {-1,0,1,-1,1,-1,0,1};

    if(crossword[row][col] != word.charAt(0))
        return false;

    int len = word.length();

    for (int dir = 0; dir < 8; dir++){
        int k;
        int rd = row + x[dir];
        int cd = col + y[dir];

        for(k = 1; k < len; k++){
        if(rd >= R || rd < 0 || cd >= C || cd < 0)
            break;

        if(crossword[rd][cd] != word.charAt(k))
            break;

            rd += x[dir];
            cd += y[dir];
            Word.W = rd - x[dir];
            Word.Z = cd - y[dir];
        }
        if(len == k)
            return true;
    }
    return false;
    }



    public static class Word {
        private String text;
        private int startX;
        private int startY;
        private int endX;
        private int endY;

        static int W,Z;

        public Word(String text) {
            this.text = text;
        }

        public void setStartPoint(int i, int j) {
            startX = i;
            startY = j;
        }

        public void setEndPoint(int i, int j) {
            endX = i;
            endY = j;
        }

        @Override
        public String toString() {
            return String.format("%s - (%d, %d) - (%d, %d)", text, startX, startY, endX, endY);
        }
    }
}
