package globex.spacerunner;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class GameView extends SurfaceView implements SurfaceHolder.Callback
{
    static private GameThread _thread;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //So we can listen for events...
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        setFocusable(true);

        //and instantiate the thread
        _thread = new GameThread(holder, context, new Handler());

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                _thread.getGameState().onTouchEvent(event);
                return true;
            }
        });
    }

   /*@Override
   public boolean onKeyDown(int keyCode, KeyEvent msg) {
       return _thread.getGameState().keyPressed(keyCode, msg);
   }*/

    //Implemented as part of the SurfaceHolder.Callback interface
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        //Mandatory, just swallowing it for this example

    }

    //Implemented as part of the SurfaceHolder.Callback interface
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!_thread.isAlive()){
            _thread.start();
        }
    }

    //Implemented as part of the SurfaceHolder.Callback interface
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
//       _thread.stop();
        _thread.interrupt();
    }

    public static GameThread get_thread() {
        return _thread;
    }
}
