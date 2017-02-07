package globex.spacerunner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameThread extends Thread {

	private SurfaceHolder surfaceHolder;
	private Paint paint;
	private GameState state;
	private Paint paintScore;
	private long lastFrameMillis;
	private long thisFrameMillis;
	private int delta;
	private int fps = 1000/60;
	private Canvas canvas;

	public GameThread(SurfaceHolder surfaceHolder, Context context, Handler handler) {
		this.surfaceHolder = surfaceHolder;
		paint = new Paint();
		paintScore = new Paint();
		state = new GameState();
		lastFrameMillis = 0;
		thisFrameMillis = 0;
	}

	@Override
	public void run() {
		lastFrameMillis = System.currentTimeMillis();
		paintScore.setARGB(150, 0, 255, 0);
		while(true) {
			thisFrameMillis = System.currentTimeMillis();
			delta = (int)(thisFrameMillis - lastFrameMillis);
			if (delta >= fps){
				canvas = surfaceHolder.lockCanvas();
				if (canvas != null) {
					lastFrameMillis = thisFrameMillis;
					state.update();
					state.draw(canvas, paint, paintScore);
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}
	}

	public GameState getGameState()
	{
	return state;
	}
}