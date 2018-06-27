package com.example.namvu.shutting;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;



public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{
    public static int WIDTH;
    public static int HEIGHT;
    private MainThread thread;
    private Background bg;
    //asdhuas

    public GamePanel(Context context)
    {
        super(context);


        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        //make gamePanel focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while(retry)
        {
            try{thread.setRunning(false);
                thread.join();

            }catch(InterruptedException e){e.printStackTrace();}
            retry = false;
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){

        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background));
        bg.setVector(-5);
        WIDTH = bg.image.getWidth();
        HEIGHT = bg.image.getHeight();
        //we can safely start the game loop
        thread.setRunning(true);
        thread.start();

    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return super.onTouchEvent(event);
    }

    public void update()
    {

        bg.update();
    }
    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);
        float scaleFactorX;
        scaleFactorX = getWidth()*1.f/WIDTH*1.f;
        float scaleFactorY;
        scaleFactorY = getHeight()*1.f/HEIGHT*1.f;
//        System.out.println(getWidth());
//        System.out.println(getHeight());
//        System.out.println(scaleFactorX);
//        System.out.println(scaleFactorY);

        if(canvas!=null) {
            final int savedState = canvas.save();
            //System.out.println(savedState);
            canvas.scale(scaleFactorX, scaleFactorY);
            bg.draw(canvas);
            canvas.restoreToCount(savedState);
        }
    }

}