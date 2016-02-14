package globex.spacerunner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.view.SurfaceHolder;

public class GameThread extends Thread {

	/** Handle to the surface manager object we interact with */
	private SurfaceHolder _surfaceHolder;
	private Paint _paint;
	private GameState _state;
	private Paint _paintScore;

	public GameThread(SurfaceHolder surfaceHolder, Context context, Handler handler) {
		_surfaceHolder = surfaceHolder;
		_paint = new Paint();
		_paintScore = new Paint();
		_state = new GameState();
	}

	@Override
	public void run() {
		while(true) {
		Canvas canvas = _surfaceHolder.lockCanvas();
			if (canvas != null) {
				_state.update();
				_state.draw(canvas, _paint, _paintScore);
				_surfaceHolder.unlockCanvasAndPost(canvas);
			}
		}
	}

	public GameState getGameState()
	{
	return _state;
	}
	
	}