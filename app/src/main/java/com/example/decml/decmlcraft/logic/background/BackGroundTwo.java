package com.example.decml.decmlcraft.logic.background;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.decml.decmlcraft.base.Constants;


/**
 * Created by Decml on 2017/3/5.
 */

public class BackGroundTwo {

    private  Bitmap      bmpBackground1;
    private  Bitmap      bmpBackground2;
    private  Bitmap      bmpBackground3;
    private  int         screenW,screenH;
    private  int         backX1,backY1;
    private  int         backX2,backY2;
    private  int         backX3,backY3;
    private  int         moveSpeed=8;
    private  Resources   res;
    public BackGroundTwo(Resources res,Bitmap bmpBackground,int screenW,int screenH)
    {
        this.res=res;
        this.bmpBackground1=bmpBackground;
        this.bmpBackground2=bmpBackground;
        this.bmpBackground3=bmpBackground;
        this.screenW=screenW;
        this.screenH=screenH;
        backX1=-(Math.abs(bmpBackground1.getWidth()-screenW))+5;
        backX2=backX1-bmpBackground1.getWidth()+5;
        backX3=backX1+bmpBackground1.getWidth()-5;
        backY1=screenH-bmpBackground1.getHeight();
        backY2=screenH-bmpBackground1.getHeight();
        backY3=screenH-bmpBackground1.getHeight();
    }

    public void draw(Canvas canvas, Paint paint)
    {
        canvas.drawBitmap(bmpBackground1,backX1,backY1,paint);
        canvas.drawBitmap(bmpBackground2,backX2,backY2,paint);
        canvas.drawBitmap(bmpBackground3,backX3,backY3,paint);
    }

    public void logic(int duraction)
    {
        if(duraction== Constants.MOVE_LEFT)
        {
            backX1 += moveSpeed;
            backX2 += moveSpeed;
            backX3 += moveSpeed;
            //当第一张图片的X坐标超出屏幕，
            //立即将第三张坐标设置到第二张图的左方
            if (backX1 > screenW) {
                backX3 = backX2 - bmpBackground1.getWidth()+5;
            }
            //当第二张图片的Y坐标超出屏幕，
            //立即将第一张图片坐标设置到第三张图的左方
            if (backX2 > screenW) {
                backX1 = backX3 - bmpBackground1.getWidth()+5;
            }
            //当第三张图片的Y坐标超出屏幕，
            //立即将第二张坐标设置到第一张图的上方
            if(backX3>screenW){
                backX2 = backX1 - bmpBackground1.getWidth()+5;
            }

        }
        else if(duraction==Constants.MOVE_RIGHT)
        {
            backX1 -= moveSpeed;
            backX2 -= moveSpeed;
            backX3 -= moveSpeed;
            //当第一张图片的X坐标超出屏幕，
            //立即将第二张坐标设置到第三张图的右方
            if (backX1 <(- bmpBackground1.getWidth())) {
                backX2 = backX3 + bmpBackground1.getWidth()-5;
            }
            //当第二张图片的Y坐标超出屏幕，
            //立即将第三张坐标设置到第一张图的右方
            if (backX2 <(- bmpBackground1.getWidth())) {
                backX3 = backX1 + bmpBackground1.getWidth()-5;
            }
            //当第三张图片的Y坐标超出屏幕，
            //立即将第一张坐标设置到第二张图的右方
            if (backX3 <(- bmpBackground1.getWidth())) {
                backX1 = backX2 + bmpBackground1.getWidth()-5;
            }
        }
    }


    public Bitmap getBmpBackground() {
        return bmpBackground1;
    }

    public void setBmpBackground(Bitmap bmpBackground) {
        this.bmpBackground1 = bmpBackground;
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

    public int getBackX() {
        return backX1;
    }

    public void setBackX(int backX1) {
        this.backX1 = backX1;
    }

    public int getBackY() {
        return backY1;
    }

    public void setBackY(int backY1) {
        this.backY1 = backY1;
    }
    public int getBackX2() {
        return backX2;
    }

    public void setBackX2(int backX2) {
        this.backX2 = backX2;
    }

    public int getBackY2() {
        return backY2;
    }

    public void setBackY2(int backY2) {
        this.backY2 = backY2;
    }

    public int getBackX3() {
        return backX3;
    }

    public void setBackX3(int backX3) {
        this.backX3 = backX3;
    }

    public int getBackY3() {
        return backY3;
    }

    public void setBackY3(int backY3) {
        this.backY3 = backY3;
    }

    public int getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }
}
