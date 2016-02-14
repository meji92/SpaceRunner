package globex.spacerunner;

import java.util.concurrent.atomic.AtomicInteger;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class GameState implements SensorEventListener{

	//screen width and height
	int _screenWidth = 540;
	int _screenHeight = 960;

	//The ball
	final int _ballSize = 10;
	private AtomicInteger _ballX; 	
	private AtomicInteger _ballY;
	private int _ballVelocityX = 5; 	
	private int _ballVelocityY = 5;

	//The bats
	final int _batLength = 75;	
	final int _batHeight = 10;
	private AtomicInteger _topBatX;
	final int _topBatY = 20;
	private AtomicInteger _bottomBatX;	
	final int _bottomBatY;
	final int _batSpeed = 5;
	boolean jugando =  false;
	

	public GameState()
	{
		Sensor accelerometer = MainActivity.manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
		MainActivity.manager.registerListener(this, accelerometer,SensorManager.SENSOR_DELAY_GAME);
		_topBatX = new AtomicInteger();
		_bottomBatX = new AtomicInteger();
		_ballX = new AtomicInteger();
		_ballY = new AtomicInteger();
		_ballX.set(_screenWidth/2); 	
		_ballY.set(_screenHeight / 2);
		_screenHeight = MainActivity.screenHeight;
		_screenWidth = MainActivity.screenWidth;
		_topBatX.set((_screenWidth/2) - (_batLength / 2));
		_bottomBatX.set((_screenWidth/2) - (_batLength / 2));
		_bottomBatY =  _screenHeight - 75;  //Cambiado
	}
	
//	public GameState( int _screenHeight,int  _screenWidth)
//	{
//		Sensor accelerometer = MainActivity.manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
//		MainActivity.manager.registerListener(this, accelerometer,SensorManager.SENSOR_DELAY_GAME);
//		this._screenHeight = _screenHeight;
//		this._screenWidth = _screenWidth;			
//	}
	

	@Override
	public void onSensorChanged(SensorEvent event) {
		//		CHULETA event.values[]:
//			0 = X
//			1 = Y
//			2 = Z
//		VALORES DE X:
//			0: 0º
//			9: 90º
		
		
//		<-------_topBatX------->
//											_topBatY
//		0 |     _topBat		|_screenWidth        ^
//		  |					|					 |
//		  |					|					 |
//		  |					|					 |
//		  |					|					 |
//		  |					|					 |
//		  |					|					 |
//		0 |	   _bottomBat	|_screenWidth		 ↓
//											_bottomBatY
//
		if((event.values[0] > 1)&&(_topBatX.get() > 0))//left
		{
			if (event.values[0] > 4.5) { //TOP SPEED!!!!
				_topBatX.set(_topBatX.get() - _batSpeed * 2);
//						_bottomBatX.set(_bottomBatX.get() -_batSpeed*2);
			} else {
				_topBatX.set(_topBatX.get() - _batSpeed);
//						_bottomBatX.set(_bottomBatX.get() -_batSpeed);
			}
		}else {
			if ((event.values[0] < -1) && (_topBatX.get() + _batLength < _screenWidth)) //right
			{
				if (event.values[0] < -4.5) {
					_topBatX.set(_topBatX.get() + _batSpeed * 2);
//							_bottomBatX.set(_bottomBatX.get()+_batSpeed*2);
				} else {
					_topBatX.set(_topBatX.get() + _batSpeed);
//							_bottomBatX.set(_bottomBatX.get()+_batSpeed);
				}
			}
		}

		
	}
	
	public void set_bottomBatX(int _bottomBatX) {
		this._bottomBatX.set(_bottomBatX);
	}
	
	public void set_topBatX(int _topBatX) {
		this._topBatX.set(_topBatX);
	}
	
	public void set_ballX(int _ballX){
		this._ballX.set(_ballX);
	}
	
	public void set_ballY (int _ballY){
		this._ballY.set(_ballY);
	}
	
	public AtomicInteger get_ballX() {
		return _ballX;
	}
	
	public AtomicInteger get_ballY() {
		return _ballY;
	}
	
	public void setBallSpeed (int speed){
		_ballVelocityX = speed;
		_ballVelocityY = speed;
	}

	//The update method
	public void update() {

//	_ballX += _ballVelocityX;
	_ballX.set(_ballX.get()+_ballVelocityX);
//	_ballY += _ballVelocityY;
	_ballY.set(_ballY.get()+_ballVelocityY);
	
//	if (MainActivity.isServer()){
	//DEATH!
	if(_ballY.get() < 10){
		_ballX.set(_screenWidth/2); 	
		_ballY.set(_screenHeight/2);
		if (jugando == true){
			jugando = false;
		}
	}
	
	//DEATH!
	if(_ballY.get() > _screenHeight-65){
		_ballX.set(_screenWidth/2); 	
		_ballY.set(_screenHeight/2);
		if (jugando == true){
			jugando = false;
		}
	}
	
	//Collisions with the sides
	if(_ballX.get() > _screenWidth || _ballX.get() < 0)
	     		_ballVelocityX *= -1; 	
	
	//Collisions with the bats     	
	if(_ballX.get() > _topBatX.get() && _ballX.get() < _topBatX.get()+_batLength && _ballY.get() < _topBatY){
		_ballVelocityY *= -1;  
		jugando = true;
	}
	
	//Collisions with the bats     	
	if(_ballX.get() > _bottomBatX.get() && _ballX.get()< _bottomBatX.get()+_batLength && _ballY.get() > _bottomBatY){
		_ballVelocityY *= -1;
		jugando = true;
	}
	                       
//	}
	}

	//the draw method
	public void draw(Canvas canvas, Paint paint, Paint paintScore) {

	//Clear the screen
	canvas.drawRGB(20, 20, 20);

	//set the colour
	paint.setARGB(200, 0, 200, 0);
	
	paintScore.setColor(Color.GRAY);
//	paintScore.setStyle(Paint.Style.FILL_AND_STROKE);
	paintScore.setTextSize(_screenHeight/8);
	paintScore.setAlpha(150);
	//Puntuacion _bottomBat
	//canvas.drawText(Integer.toString(MainActivity.puntBottom),_screenWidth - _screenWidth/4, _screenHeight - _screenHeight/8, paintScore);
	//Puntuacion _TopBat
	//canvas.drawText(Integer.toString(MainActivity.puntTop),_screenWidth - _screenWidth/4, _screenHeight/8+40, paintScore);
	

	//draw the ball
	canvas.drawRect(new Rect(_ballX.get(),_ballY.get(),_ballX.get() + _ballSize,_ballY.get() + _ballSize),
	                             paint);

	//draw the bats
	canvas.drawRect(new Rect(_topBatX.get(), _topBatY, _topBatX.get() + _batLength,
	                                      _topBatY + _batHeight), paint); //top bat
	canvas.drawRect(new Rect(_bottomBatX.get(), _bottomBatY, _bottomBatX.get() + _batLength, 
	                                      _bottomBatY + _batHeight), paint); //bottom bat
	
	//DRAW THE... CAMPO DE JUEGO XD
//	ABAJO
	canvas.drawRect(new Rect(0,_screenHeight-40, _screenWidth, _screenHeight-35), paint);
//	ARRIBA
	canvas.drawRect(new Rect(0,0, _screenWidth, 5), paint);
//	IZQ
	canvas.drawRect(new Rect(0,0,5, _screenHeight-40), paint);
//	DER
	canvas.drawRect(new Rect(_screenWidth,0, _screenWidth-5,_screenHeight-40), paint);
	
	
//	
//	CANVAS.DRAWRECT
//	
//	
//				P1(x1,y1)| 			|  
//						 |			|
//						 |			|
//	 					 |			|
//	 					 |			|
//						 |			|
//						 |			|
//						 |			|P2(x2,y2)
//					
//				   canvas.drawRect(x1,y1,x2,y2)
	

	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	}
