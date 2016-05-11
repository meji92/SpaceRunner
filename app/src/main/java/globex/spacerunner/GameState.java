package globex.spacerunner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import android.graphics.Canvas;
import android.graphics.Color;
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
	private int playerRadius = 75;
	private int x;
	private int y;
	private final int speed = 10;
	private int moveX = 0;
	private int moveY = 0;
	private int initialSquares = 1;
	private Iterator<Square> iterator;
	private Square iteratedSquare;

	public Square playerSquare;
	public List<Square> squares = new ArrayList<Square>();
	public List<Square> rubishAux = new ArrayList<Square>();


	public GameState()
	{
		Sensor accelerometer = MainActivity.manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
		MainActivity.manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
		screenHeight = MainActivity.screenHeight;
		screenWidth = MainActivity.screenWidth;
		x = ((screenWidth /2) - (playerRadius / 2));
		y =  screenHeight - (int)(playerRadius*1.5);
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
		/*if((event.values[0] > 1)&&(playerSquare.getPos().getX() > 0))//left
		{
			if (event.values[0] > 4.5) { //TOP SPEED!!!!
				playerSquare.setPos(playerSquare.getPos().getX() - speed * 2, playerSquare.getPos().getY());
			} else {
				playerSquare.setPos(playerSquare.getPos().getX() - speed, playerSquare.getPos().getY());
			}
		}else {
			if ((event.values[0] < -1) && (playerSquare.getPos().getX() + playerRadius < screenWidth)) //right
			{
				if (event.values[0] < -4.5) {
					playerSquare.setPos(playerSquare.getPos().getX() + speed * 2, playerSquare.getPos().getY());
				} else {
					playerSquare.setPos(playerSquare.getPos().getX() + speed, playerSquare.getPos().getY());
				}
			}
		}*/
	}

	//The update method
	public void update() {
		boolean addSquare = false;
		if ((moveX > 0)&&(playerSquare.getPos().getX()<(screenWidth-playerRadius))) {
			playerSquare.setPos(playerSquare.getPos().getX() + moveX, playerSquare.getPos().getY() + moveY);
		}else if ((moveX < 0)&&(playerSquare.getPos().getX()>0)){
			playerSquare.setPos(playerSquare.getPos().getX() + moveX, playerSquare.getPos().getY() + moveY);
		}

		iterator = squares.iterator();
		while (iterator.hasNext()){
			iteratedSquare = iterator.next();
			if (iteratedSquare.getPos().getY()>screenHeight){
				if (iteratedSquare instanceof Rubish){
					iterator.remove();
				}else{
					iteratedSquare.reloadSquare();
				}
			}else{
				if ((iteratedSquare.getPos().getX() > screenWidth) || (iteratedSquare.getPos().getX() < 0)) {
					iteratedSquare.changeDir();
				}
				iteratedSquare.update();
				if (iteratedSquare.collision(playerSquare)) {
					rubishAux.addAll(iteratedSquare.getRubish());
					//iteratedSquare.reloadSquare();
					addSquare = true;
					/*if (playerSquare.getSize()<=200) {
						playerSquare.setSize(playerSquare.getSize() + 1);
					}*/
				}
			}
		}
		if (addSquare){
			squares.addAll(rubishAux);
			Log.d("squares:",squares.toString());
		}
		//squares.addAll(rubishAux);
		//Log.d("Squares", squares.toString());
		//rubishAux.clear();
	}

	//the draw method
	public void draw(Canvas canvas, Paint paint, Paint paintScore) {
		//Clear the screen
		canvas.drawRGB(20, 20, 20);
		//set the colour
		paint.setARGB(150, 0, 255, 0);
		paintScore.setColor(Color.RED);
		paintScore.setTextSize(screenHeight / 8);
		paintScore.setAlpha(150);

		canvas.drawRect(new Rect((int) playerSquare.getPos().getX(), (int) playerSquare.getPos().getY() - (int)(playerSquare.getSize()*1.5), (int) playerSquare.getPos().getX() + playerSquare.getSize(), (int) playerSquare.getPos().getY() - (int)(playerSquare.getSize()*0.5)), paint);

		paint.setARGB(150, 255, 255, 255);

		for (Square a: squares){
			if (a instanceof Rubish){
				paint.setARGB(150, 255, 0, 0);
				canvas.drawRect(new Rect((int) a.getPos().getX(), (int) a.getPos().getY(), (int) (a.getPos().getX() + a.getSize()), (int) (a.getPos().getY() + a.getSize())), paint);
			}else {
				paint.setARGB(150, 255, 255, 255);
				canvas.drawRect(new Rect((int) a.getPos().getX(), (int) a.getPos().getY(), (int) (a.getPos().getX() + a.getSize()), (int) (a.getPos().getY() + a.getSize())), paint);
			}
		}

		canvas.drawRect(new Rect(0, screenHeight - 5, screenWidth, screenHeight), paint);
		canvas.drawRect(new Rect(0, 0, screenWidth, 5), paint);
		canvas.drawRect(new Rect(0, 0, 5, screenHeight), paint);
		canvas.drawRect(new Rect(screenWidth, 0, screenWidth - 5, screenHeight), paint);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	}
