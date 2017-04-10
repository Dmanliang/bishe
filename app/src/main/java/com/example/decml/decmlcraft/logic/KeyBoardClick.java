package com.example.decml.decmlcraft.logic;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2017/2/8.
 */

public class KeyBoardClick {

    //按钮坐标
    private int buttonX;
    private int buttonY;
    private Bitmap bmpButton;
    private Bitmap bmpButtonPress;
    private int screenW,screenH;
    private Resources res;
    private boolean isPress=false;

    public KeyBoardClick(Resources res, Bitmap bmpButton, Bitmap bmpButtonPress, int buttonX, int buttonY, int screenW, int screenH)
    {
        this.res=res;
        this.bmpButton=bmpButton;
        this.bmpButtonPress=bmpButtonPress;
        this.buttonX=buttonX;
        this.buttonY=buttonY;
        this.screenH=screenH;
        this.screenW=screenW;
    }

    public void draw(Canvas canvas,Paint paint)
    {
        if(!isPress)
        {
            canvas.drawBitmap(bmpButton,buttonX,buttonY,paint);
        }
        else
        {
            canvas.drawBitmap(bmpButtonPress,buttonX,buttonY,paint);
        }

    }

    //菜单触屏事件函数,主要用于处理按钮
    public void onTouchEvent(MotionEvent event)
    {
        //获取用户当前触屏位置
        int pointX=(int)event.getX();
        int pointY=(int)event.getY();

        //当前用户是按下动作或移动动作
        if(event.getAction()==MotionEvent.ACTION_DOWN||event.getAction()==MotionEvent.ACTION_MOVE)
        {
            //判定用户是否点击了按钮
            if(pointX>buttonX && pointX<buttonX+bmpButton.getWidth())
            {
                if(pointY>buttonY && pointY<buttonY+bmpButton.getHeight())
                {
                    isPress=true;
                }
                else
                {
                    isPress=false;
                }
            }
            else
            {
                isPress=false;
            }
        }
        else if(event.getAction()==MotionEvent.ACTION_UP)
        {
            //抬起判断是否点击按钮,防止用户移动到别处
            if(pointX>buttonX && pointX<buttonX+bmpButton.getWidth())
            {
                if(pointY>buttonY && pointY<buttonY+bmpButton.getHeight())
                {
                    //还原Button状态为未按下状态
                    isPress=false;
                }
            }
        }
    }


    public int getButtonX() {
        return buttonX;
    }

    public void setButtonX(int buttonX) {
        this.buttonX = buttonX;
    }

    public int getButtonY() {
        return buttonY;
    }

    public void setButtonY(int buttonY) {
        this.buttonY = buttonY;
    }

    public int getScreenW() {
        return screenW;
    }

    public void setScreenW(int screenW) {
        this.screenW = screenW;
    }

    public int getScreenH() {
        return screenH;
    }

    public void setScreenH(int screenH) {
        this.screenH = screenH;
    }

}
