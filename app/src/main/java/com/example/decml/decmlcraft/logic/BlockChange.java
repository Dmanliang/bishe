package com.example.decml.decmlcraft.logic;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.view.MotionEvent;

import com.example.decml.decmlcraft.MyMap;
import com.example.decml.decmlcraft.R;
import com.example.decml.decmlcraft.logic.blocks.Block;


/**
 * Created by Decml on 2017/2/22.
 */

public class BlockChange {

    private Resources res;

    private int x,y;
    private Bitmap bmpBlockChange;
    public int[] blockChange={R.drawable.image_no_change, R.drawable.image_change1,R.drawable.image_change2,R.drawable.image_change3,R.drawable.image_change4};
    private int index=0;
    private int touchX,touchY;
    private Block block;
    private int breakSpeed=700;
    private Handler mHandlerBreakBlock = new Handler();
    private Runnable mRunnableBreakBlock= new Runnable(){
        @Override
        public void run() {
            index++;
            bmpBlockChange= BitmapFactory.decodeResource(res,blockChange[index]);
            if(index==blockChange.length-1)
            {
                index=0;
                MyMap.blocks.remove(block);
                mHandlerBreakBlock.removeCallbacks(this);
            }
            else
            {
                mHandlerBreakBlock.postDelayed(this,breakSpeed);
            }
        }
    };

    public BlockChange(Resources res, Bitmap bmpBlockChange, int x, int y)
    {
        this.res=res;
        this.bmpBlockChange=bmpBlockChange;
        this.x=x;
        this.y=y;
    }

    public void draw(Canvas canvas, Paint paint)
    {
        canvas.drawBitmap(bmpBlockChange,x,y,paint);
    }

    public void onTouchEvent(MotionEvent event,Bitmap bmpBlock,Block block)
    {
        this.block=block;
        touchX=(int)event.getX();
        touchY=(int)event.getY();
        switch (event.getAction())
        {
            case MotionEvent.ACTION_UP:
                StopBlockChange();
                break;
            case MotionEvent.ACTION_DOWN:
                if(touchX>=block.getBlockX() && touchX<=(block.getBlockX()+bmpBlock.getWidth()) && touchY>=block.getBlockY() && touchY<=(block.getBlockY()+bmpBlock.getHeight()))
                {
                    StartBlockChange();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
        }
    }

    //开始破坏
    public void StartBlockChange()
    {
        mHandlerBreakBlock.post(mRunnableBreakBlock);
    }

    //停止破坏
    public void StopBlockChange()
    {
        mHandlerBreakBlock.removeCallbacks(mRunnableBreakBlock);
        index=0;
        bmpBlockChange= BitmapFactory.decodeResource(res,blockChange[index]);
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


}
