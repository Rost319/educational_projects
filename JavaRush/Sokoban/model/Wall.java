package com.javarush.task.task34.task3410.model;

import java.awt.*;

public class Wall extends CollisionObject {

    public Wall(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(Color.black);

        int xc = getX();
        int yc = getY();
        int width = getWidth();
        int height = getHeight();


        graphics.drawRect(xc - width / 2, yc - height / 2, width, height);
        graphics.drawLine(xc - width/2, yc - height / 3, xc + width/2, yc - height / 3);
        graphics.drawLine(xc - width/2, yc - height / 10, xc + width/2, yc - height / 10);
        graphics.drawLine(xc - width/2, yc + height/3, xc + width/2, yc + height/3);
        graphics.drawLine(xc - width/2, yc + height / 10, xc + width/2, yc + height / 10);

        graphics.drawLine(xc - width/3, yc + height / 2, xc - width/3, yc - height / 2);
        graphics.drawLine(xc - width/10, yc + height / 2, xc - width/10, yc - height / 2);
        graphics.drawLine(xc + width/3, yc + height/2, xc + width/3, yc - height/2);
        graphics.drawLine(xc + width/10, yc + height / 2, xc + width/10, yc - height / 2);


    }
}
