//Tommy Phiravanh (5170525)
//CSCI1933
//Sec 04

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class BallScreenSaver extends AnimationFrame {
	private Ball[] balls;
	int ballSize = 18;
	final int START_SPEED = 300;
	int BORDER = 30;
	private CollisionLogger collisionLogger;
	private static final int COLLISION_BUCKET_WIDTH = 10;

	public BallScreenSaver(int w, int h, String name, int count) {
		super(w, h, name);
		balls = new Ball[count];
		for (int i = 0; i < balls.length; i++) {
			double ballX, ballY;
			ballX = randdouble(BORDER, getWidth() - BORDER);
			ballY = randdouble(BORDER, getHeight() - BORDER);
			if (i == 0) {
				balls[i] = new Ball(ballX, ballY, ballSize, Color.RED);
			} else {
				balls[i] = new Ball(ballX, ballY, ballSize, Color.GREEN);
			}
			setRandDir(START_SPEED, i);
			setFPS(20);
			collisionLogger = new CollisionLogger(this.getWidth(), this.getHeight(), COLLISION_BUCKET_WIDTH);
		}

	}

	@Override
	public void action() {
		for (int i = 0; i < balls.length; i++) {
			Ball ball = balls[i];
			ball.updatePosition(getFPS());

			if (ball.getXPos() < BORDER || ball.getXPos() + ballSize > getHeight() - BORDER) {
				double xSpeed = ball.getSpeedX() * -1;
				ball.setSpeedX(xSpeed);
			}
			if (ball.getYPos() < BORDER || ball.getYPos() + ballSize > getWidth() - BORDER) {
				double ySpeed = ball.getSpeedY() * -1;
				ball.setSpeedY(ySpeed);

			}
			for (int j = 0; j < balls.length; j++) {
				if (i != j) {
					Ball ball2 = balls[j];
					if (ball.intersect(ball2)) {
						ball.collide(ball2);
						collisionLogger.collide(ball, ball2);
					}
				}
			}
		}

	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.WHITE);
		g.drawRect(BORDER, BORDER, getWidth() - BORDER * 2, getHeight() - BORDER * 2);
		for (Ball ball : balls) {
			g.setColor(ball.color);
			g.fillOval((int) ball.x, (int) ball.y, (int) ball.r, (int) ball.r);
		}
	}

	@Override
	public void processKeyEvent(KeyEvent e) {

		int keyCode = e.getKeyCode();
		if (e.getID() == KeyEvent.KEY_PRESSED && keyCode == KeyEvent.VK_P) {
			EasyBufferedImage image = EasyBufferedImage.createImage(collisionLogger.getNormalizedHeatMap());
			try {
				image.save("heatmap_" + System.currentTimeMillis() + ".png", EasyBufferedImage.PNG);
			} catch (IOException exc) {
				exc.printStackTrace();
				System.exit(1);
			}

		}

		if (e.getID() == KeyEvent.KEY_PRESSED && keyCode == KeyEvent.VK_LEFT) {
			for (int i = 0; i < balls.length; i++) {
				Ball ball = balls[i];
				ball.setSpeedX(ball.getSpeedX() - (ball.getSpeedX() * 10) / 100);
				ball.setSpeedY(ball.getSpeedY() - (ball.getSpeedY() * 10) / 100);
			}
		}

		if (e.getID() == KeyEvent.KEY_PRESSED && keyCode == KeyEvent.VK_RIGHT) {
			for (int i = 0; i < balls.length; i++) {
				Ball ball = balls[i];
				ball.setSpeedX(ball.getSpeedX() + (ball.getSpeedX() * 10) / 100);
				ball.setSpeedY(ball.getSpeedY() + (ball.getSpeedY() * 10) / 100);
			}
		}
	}

	public void setRandDir(double speed, int i) {
		double dir = randdouble(0, Math.PI * 2);
		balls[i].setSpeedX(Math.cos(dir) * speed);
		balls[i].setSpeedY(Math.sin(dir) * speed);
	}

	public int randInt(int min, int max) {
		return (int) (Math.random() * (max - min) + min);
	}

	public double randdouble(double min, double max) {
		return (Math.random() * (max - min) + min);
	}

}
