package com.example.decml.decmlcraft.logic;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

/**
 * Created by Decml on 2017/4/3.
 */

public class Back {

    private Resources   res;
    //返回坐标
    private int         BackX,BackY;
    //返回宽高
    private int         BackW,BackH;
    //返回资源
    private Bitmap      bmpBack;
    //地图宽高
    private int         screenW;
    private int         screenH;
    //是否被打开
    private boolean     isOpenBack;

    public Back(Resources res, Bitmap bmpBack, int screenW, int screenH){
        this.res            =   res;
        this.bmpBack        =   bmpBack;
        this.screenH        =   screenH;
        this.screenW        =   screenW;
        BackH               =   bmpBack.getHeight();
        BackW               =   bmpBack.getWidth();
        BackX               =   screenW/4;
        BackY               =   bmpBack.getHeight()+20;
        isOpenBack          =   false;
    }

    public void draw(Canvas canvas, Paint paint){
        if(isOpenBack){
            canvas.drawBitmap(bmpBack,BackX,BackY,paint);
        }
    }

    public void logic(){}

    public void onTouchEvent(MotionEvent event){

    }
    public boolean isOpenBack() {
        return isOpenBack;
    }

    public void setOpenBack(boolean openBack) {
        isOpenBack = openBack;
    }

    public Bitmap getBmpBack() {
        return bmpBack;
    }

    public void setBmpBack(Bitmap bmpBack) {
        this.bmpBack = bmpBack;
    }

    public int getBackH() {
        return BackH;
    }

    public void setBackH(int backH) {
        BackH = backH;
    }

    public int getBackW() {
        return BackW;
    }

    public void setBackW(int backW) {
        BackW = backW;
    }

    public int getBackY() {
        return BackY;
    }

    public void setBackY(int backY) {
        BackY = backY;
    }

    public int getBackX() {
        return BackX;
    }

    public void setBackX(int backX) {
        BackX = backX;
    }

}
