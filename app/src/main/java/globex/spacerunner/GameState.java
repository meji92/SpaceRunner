package globex.spacerunner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.MotionEvent;

public class GameState implements SensorEventListener{

	//screen width and height
	private int screenWidth = 540;
	private int screenHeight = 960;
	private int normalizedWidth = MainActivity.normalizedWidth;
	private int normalizedHeight = MainActivity.normalizedHeight;
	private int playerRadius = 75;
	private int x;
	private int y;
	private final int speed = 10;
	private int moveX = 0;
	private int moveY = 0;
	private int initialSquares = 20;
	private Iterator<Square> iterator;
	private Square iteratedSquare;
	private Rect auxRect = new Rect(0,0,0,0);
	private Score score = new Score();

	public Square playerSquare;
	public List<Square> squares = new ArrayList<Square>();
	public List<Square> rubbishAux = new ArrayList<Square>();


	public GameState()
	{
		Sensor accelerometer = MainActivity.manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
		MainActivity.manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
		screenHeight = MainActivity.screenHeight;
		screenWidth = MainActivity.screenWidth;
		x = ((normalizedWidth /2) - (playerRadius / 2));
		y =  normalizedHeight - (int)(playerRadius*1.5);
		playerSquare = new Square(x,y,speed,speed,playerRadius);
		for (int i=0; i < initialSquares; i++){
			//squares.add(new Square((int)(Math.random()*screenWidth),(int)(Math.random()*screenHeight),(int)(Math.random()*10),(int)(Math.random()*10)+1,(int)(Math.random()*100)));
			squares.add(new Square());
		}
	}

	public void onTouchEvent(MotionEvent e) {
		float x = e.getX();
		switch (e.getAction()) {
			case MotionEvent.ACTION_MOVE:
				if (x > screenWidth / 2) {
					moveX = speed;
				} else {
					moveX = -speed;
				}
				break;
			case MotionEvent.ACTION_DOWN:
				if (x > screenWidth / 2) {
					moveX = speed;
				} else {
					moveX = -speed;
				}
				break;
			case MotionEvent.ACTION_UP:
				moveX = 0;
				moveY = 0;
				break;

		}
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		/*if((event.values[0] > 1)&&(playerSquare.getPosition().getX() > 0))//left
		{
			if (event.values[0] > 4.5) { //TOP SPEED!!!!
				playerSquare.setPosition(playerSquare.getPosition().getX() - speed * 2, playerSquare.getPosition().getY());
			} else {
				playerSquare.setPosition(playerSquare.getPosition().getX() - speed, playerSquare.getPosition().getY());
			}
		}else {
			if ((event.values[0] < -1) && (playerSquare.getPosition().getX() + playerRadius < screenWidth)) //right
			{
				if (event.values[0] < -4.5) {
					playerSquare.setPosition(playerSquare.getPosition().getX() + speed * 2, playerSquare.getPosition().getY());
				} else {
					playerSquare.setPosition(playerSquare.getPosition().getX() + speed, playerSquare.getPosition().getY());
				}
			}
		}*/
	}

	//The update method
	public boolean update() {
		boolean addSquare = false;
		if ((moveX > 0)&&(playerSquare.getPosition().getX()<(normalizedWidth-playerRadius))) {
			playerSquare.setPosition(playerSquare.getPosition().getX() + moveX, playerSquare.getPosition().getY() + moveY);
		}else if ((moveX < 0)&&(playerSquare.getPosition().getX()>0)){
			playerSquare.setPosition(playerSquare.getPosition().getX() + moveX, playerSquare.getPosition().getY() + moveY);
		}

		iterator = squares.iterator();
		while (iterator.hasNext()){
			iteratedSquare = iterator.next();
			if (iteratedSquare.getPosition().getY()>normalizedHeight){
				if (iteratedSquare instanceof Rubbish){
					iterator.remove();
				}else{
					score.addScore(iteratedSquare.getSize());
					iteratedSquare.reloadSquare();
				}
			}else{
				if ((iteratedSquare.getPosition().getX() > normalizedWidth) || (iteratedSquare.getPosition().getX() < 0)) {
					iteratedSquare.changeDir();
				}
				iteratedSquare.update();
				if (iteratedSquare.isCollision(playerSquare)) {
					playerSquare.collision(iteratedSquare);
					if (playerSquare.getSize()<=0){
						return true;
					}
					rubbishAux.addAll(iteratedSquare.getRubbishAndReload());
					addSquare = true;
				}
			}
		}
		if (addSquare){
			squares.addAll(rubbishAux);
			rubbishAux.clear();
		}
		return false;
	}

	//the draw method
	public void draw(Canvas canvas, Paint paint, Paint paintScore) {
		//Clear the screen
		canvas.drawRGB(20, 20, 20);
		//Set color
		paint.setARGB(150, 0, 255, 0);

		paintScore.setTextSize(normalizedHeight / 20);
		canvas.drawText(score.getScoreString(),normalizedWidth / 25,normalizedHeight / 20,paintScore);

		canvas.drawRect(getNormalizedRect(playerSquare.getPosition().getX(), playerSquare.getPosition().getY(), playerSquare.getPosition().getX() + playerSquare.getSize(), playerSquare.getPosition().getY() + playerSquare.getSize()), paint);
		//Draw centers
		/*paint.setARGB(150, 0, 0, 255);
		canvas.drawRect(new Rect(playerSquare.getCenter().getX() - 5, playerSquare.getCenter().getY() - 5, playerSquare.getCenter().getX() + 5, playerSquare.getCenter().getY() + 5), paint);*/

		for (Square a: squares){
			paint.setARGB(150, 255, 255, 255);
			canvas.drawRect(getNormalizedRect(a.getPosition().getX(), a.getPosition().getY(), a.getPosition().getX() + a.getSize(), a.getPosition().getY() + a.getSize()), paint);
			//Draw centers
			/*paint.setARGB(150, 0, 0, 255);
			canvas.drawRect(new Rect(a.getCenter().getX()-5, a.getCenter().getY()-5, a.getCenter().getX()+5, a.getCenter().getY()+5), paint);*/
		}

		/*auxRect.set(0, screenHeight - 5, screenWidth, screenHeight);
		canvas.drawRect(auxRect, paint);
		auxRect.set(0, 0, screenWidth, 5);
		canvas.drawRect(auxRect, paint);
		auxRect.set(0, 0, 5, screenHeight);
		canvas.drawRect(auxRect, paint);
		auxRect.set(screenWidth, 0, screenWidth - 5, screenHeight);
		canvas.drawRect(auxRect, paint);*/
	}

	public void drawGameOver(Canvas canvas, Paint paintScore) {
		paintScore.setTextSize((normalizedHeight*(float)Math.random())/8);
		paintScore.setARGB(255, 0, (int)(255*Math.random()), 0);
		canvas.drawText("GAME OVER", -screenWidth + screenWidth*(float)Math.random()*2, screenHeight*(float)Math.random(), paintScore);
	}

	public Rect getNormalizedRect(int x1, int y1, int x2, int y2){
		auxRect.set(x1*screenWidth/normalizedWidth, y1*screenHeight/normalizedHeight, x2*screenWidth/normalizedWidth, y2*screenHeight/normalizedHeight);
		return auxRect;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
}
