package com.javarush.task.task34.task3410.model;

import java.awt.*;

public class Home extends GameObject {

    public Home(int x, int y) {
        super(x, y);
        width = 10;      // в дальнейшем изменить на 10, иначе не видно объект
        height = 10;
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(Color.red);

        int xc = getX();
        int yc = getY();
        int height = getHeight();
        int width = getWidth();

        graphics.drawOval(x - width/2, y - height/2, width, height);
    }
}
