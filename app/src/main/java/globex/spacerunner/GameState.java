package globex.spacerunner;

import java.util.ArrayList;
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
import android.widget.Toast;

public class GameState implements SensorEventListener{

	//screen width and height
	int screenWidth = 540;
	int screenHeight = 960;
	private int playerRadius = 75;
	private int x;
	private int y;
	final int speed = 10;
	private int initialSquares = 15;

	public Square playerSquare;
	public List<Square> squares = new ArrayList<Square>();
	

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
			//squares.add(new Square((int)(Math.random()*screenWidth),(int)(Math.random()*screenHeight),(int)Math.random()*10,(int)Math.random()*10,(int)(Math.random()*600)));
			squares.add(new Square((int)(Math.random()*screenWidth),(int)(Math.random()*screenHeight),(int)(Math.random()*10),(int)(Math.random()*10)+1,(int)(Math.random()*100)));
		}
	}
	
//	public GameState( int screenHeight,int  screenWidth)
//	{
//		Sensor accelerometer = MainActivity.manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
//		MainActivity.manager.registerListener(this, accelerometer,SensorManager.SENSOR_DELAY_GAME);
//		this.screenHeight = screenHeight;
//		this.screenWidth = screenWidth;
//	}
	

	@Override
	public void onSensorChanged(SensorEvent event) {
		//	CHULETA event.values[]:
//			0 = X
//			1 = Y
//			2 = Z
//		VALORES DE X:
//			0: 0º
//			9: 90º
		
		
//		<-------_topBatX------->
//											_topBatY
//		0 |     _topBat		|screenWidth        ^
//		  |					|					 |
//		  |					|					 |
//		  |					|					 |
//		  |					|					 |
//		  |					|					 |
//		  |					|					 |
//		0 |	   _bottomBat	|screenWidth		 ↓
//											_bottomBatY
//
		if((event.values[0] > 1)&&(playerSquare.getPos().getX() > 0))//left
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
		}

		
	}

	//The update method
	public void update() {
		for (Square a: squares){
			if (a.getPos().getY()>screenHeight){
				//a.setPos((int)(Math.random() * screenWidth), 0 - a.getSize());
				a.reloadSquare((int) (Math.random() * screenWidth), 0 - a.getSize());
			}else if ((a.getPos().getX()>screenWidth)||(a.getPos().getX()< 0)){
				a.changeDir();
				a.update();
			}else{
				a.update();
			}
		}
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

		canvas.drawRect(new Rect((int) playerSquare.getPos().getX(), (int) playerSquare.getPos().getY(), (int) playerSquare.getPos().getX() + playerRadius, (int) playerSquare.getPos().getY() + playerRadius), paint);

		paint.setARGB(150, 255, 255, 255);

		for (Square a: squares){
			canvas.drawRect(new Rect((int) a.getPos().getX(), (int) a.getPos().getY(), (int) (a.getPos().getX() + a.getSize()), (int) (a.getPos().getY() + a.getSize())), paint);
			//Log.d("POSICION: ",a.toString());
		}

		//DRAW THE... CAMPO DE JUEGO XD
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
