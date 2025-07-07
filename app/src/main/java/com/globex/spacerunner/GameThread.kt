package com.globex.spacerunner

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;

class GameThread(var state: GameState) : Thread() {

	var lastFrameMillis: Long = 0
	var thisFrameMillis: Long = 0
	var delta: Int = 0
	var fps: Int = 1000/60;
	var gameOver: Boolean = false;
	var running: Boolean = true;
	var finish: Int = 0;

	override fun run() {
		lastFrameMillis = System.currentTimeMillis()
		while(running) {
			thisFrameMillis = System.currentTimeMillis()
			delta = (thisFrameMillis - lastFrameMillis).toInt()
			if (delta >= fps){
					lastFrameMillis = thisFrameMillis;
					if (gameOver) {
						if (finish < 200) {
							finish++;
							// state.drawGameOver(canvas, paintScore);
						}else{
							running = false;
						}
					}else{
						//invalidate()
						// gameOver = state.update();
						// state.draw(canvas, paint, paintScore);
					}
			}
		}
	}
}