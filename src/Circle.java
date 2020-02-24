//Tommy Phiravanh (5170525)
//CSCI1933
//Sec 04

import java.awt.*;

public class Circle {

    double x, y, r;
    Color color;

    public Circle(double x, double y, double r, Color color) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.color = color;

    }

    // Methods
    public double calculatePerimeter() {
        return 2 * Math.PI * r;
    }

    public double calculateArea() {
        return Math.PI * r * r;
    }

    public void setColor(Color c) {
        color = c;
    }

    public void setPos(double x, double y) {
        this.x = x;
        this.y = y;

    }

    public void setRadius(double r) {
        this.r = r;
    }

    public Color getColor() {
        return color;
    }

    public double getXPos() {
        return x;
    }

    public double getYPos() {
        return y;
    }

    public double getRadius() {
        return r;
    }

}

class Ball extends Circle {

    private double ballXSpeed = 0;
    private double ballYSpeed = 0;

    public Ball(double x, double y, double r, Color color) {
        super(x, y, r, color);
    }

    // Methods
    public void setSpeedX(double ballXSpeed) {
        this.ballXSpeed = ballXSpeed;
    }

    public void setSpeedY(double ballYSpeed) {
        this.ballYSpeed = ballYSpeed;
    }

    public double getSpeedX() {
        return ballXSpeed;
    }

    public double getSpeedY() {
        return ballYSpeed;
    }

    public void updatePosition(double time) {
        double x = this.getXPos() + (getSpeedX() / time);
        double y = this.getYPos() + (getSpeedY() / time);
        super.setPos(x, y);
    }

    public boolean intersect(Ball a) {
        double x = this.getXPos();
        double y = this.getYPos();
        double r = this.getRadius();
        double ax = a.getXPos();
        double ay = a.getYPos();
        double ar = a.getRadius();

        double xdiff = x - ax;
        double ydiff = y - ay;
        double newR = r + ar;

        double distance = Math.sqrt(Math.pow(xdiff, 2) + Math.pow(ydiff, 2));
        if (distance < newR) {
            return true;
        }
        return false;
    }

    public void collide(Ball ball) {
        boolean isIntersects = intersect(ball);
        if (isIntersects) {
            double x = this.getXPos();
            double y = this.getYPos();
            double ax = ball.getXPos();
            double ay = ball.getYPos();
            double sx = this.getSpeedX();
            double sy = this.getSpeedY();
            double asx = ball.getSpeedX();
            double asy = ball.getSpeedY();
            double xdiff = x - ax;
            double ydiff = y - ay;
            double distance = Math.sqrt(Math.pow(xdiff, 2) + Math.pow(ydiff, 2));
            double deltaX = xdiff / distance;
            double deltaY = ydiff / distance;

            double newXSpeed = (((asx * deltaX) + (asy * deltaY)) * deltaX)
                    - (((-sx * deltaY) + (sy * deltaX)) * deltaY);
            double newYSpeed = (((asx * deltaX) + (asy * deltaY)) * deltaY)
                    + (((-sx * deltaY) + (sy * deltaX)) * deltaX);

            double newAXSpeed = (((sx * deltaX) + (sy * deltaY)) * deltaX)
                    - (((-asx * deltaY) + (asy * deltaX)) * deltaY);
            double newAYSpeed = (((sx * deltaX) + (sy * deltaY)) * deltaY)
                    + (((-asx * deltaY) + (asy * deltaX)) * deltaX);

            this.setSpeedX(newXSpeed);
            this.setSpeedY(newYSpeed);
            ball.setSpeedX(newAXSpeed);
            ball.setSpeedY(newAYSpeed);
            if (this.getColor() == Color.RED) {
                ball.setColor(Color.RED);
            } else if (ball.getColor() == Color.RED) {
                this.setColor(Color.RED);
            }
        }
    }

}
