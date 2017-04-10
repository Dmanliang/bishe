package com.example.decml.decmlcraft.logic;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;

import com.example.decml.decmlcraft.R;
import com.example.decml.decmlcraft.base.Constants;

/**
 * Created by Decml on 2017/3/26.
 */

public class Muzzle {

    //子弹图片资源
    private Bitmap      bmpMuzzle;
    //子弹的坐标
    private int         muzzleX,muzzleY;
    //子弹的宽高
    private int         muzzleW,muzzleH;
    //子弹的速度
    private int         muzzleSpeed =   5;
    //子弹是否超屏,优化处理
    private boolean     isDead;
    private int         screenW,screenH;
    private Resources   res;
    private int         dirs;
    private Player      player;
    private int[]       muzzleLeft      =    {R.drawable.muzzle_left_0,R.drawable.muzzle_left_1,R.drawable.muzzle_left_2,R.drawable.muzzle_left_3,R.drawable.muzzle_left_4,
                                            R.drawable.muzzle_left_5,R.drawable.muzzle_left_6,R.drawable.muzzle_left_7,R.drawable.muzzle_left_8};
    private int[]       muzzleRight     =   {R.drawable.muzzle_right_0,R.drawable.muzzle_right_1,R.drawable.muzzle_right_2,R.drawable.muzzle_right_3,R.drawable.muzzle_right_4,R.drawable.muzzle_right_5,R.drawable.muzzle_right_6,R.drawable.muzzle_right_7,R.drawable.muzzle_right_8};
    private int         index           =   0;
    private Handler     muzzleHandler   =   new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (dirs){
                case Constants.MOVE_LEFT:

                    if(index <= muzzleLeft.length) {
                        bmpMuzzle = BitmapFactory.decodeResource(res, muzzleLeft[index]);
                        logic();
                    }
                    break;
                case Constants.MOVE_RIGHT:

                    if(index <= muzzleRight.length){
                        bmpMuzzle= BitmapFactory.decodeResource(res,muzzleRight[index]);
                        logic();
                    }
                    break;
            }
            muzzleHandler.postDelayed(muzzleRunnable,30);
        }
    };
    private Runnable    muzzleRunnable  =   new Runnable() {
        @Override
        public void run() {
            Message msg =   new Message();
            index=(index+1)%muzzleRight.length;
            muzzleHandler.sendMessage(msg);
        }
    };

    public Muzzle(Resources   res,Bitmap bmpMuzzle,Player player,int screenW,int screenH){
        this.res        =   res;
        this.bmpMuzzle  =   bmpMuzzle;
        this.screenH    =   screenH;
        this.screenW    =   screenW;
        this.player     =   player;
        muzzleW         =   bmpMuzzle.getWidth();
        muzzleH         =   bmpMuzzle.getHeight();
    }

    public void initMuzzle(int dir){
        if(dir == Constants.MOVE_LEFT){
            dirs=Constants.MOVE_LEFT;
            muzzleX         =   player.getPlayerX()-bmpMuzzle.getWidth();
            muzzleY         =   player.getPlayerY()+player.getPlayerH()/2-15;
        }
        else if(dir == Constants.MOVE_RIGHT){
            dirs=Constants.MOVE_RIGHT;
            muzzleX         =   player.getPlayerX()+player.getPlayerW();
            muzzleY         =   player.getPlayerY()+player.getPlayerH()/2-15;
        }
    }

    public void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(bmpMuzzle,muzzleX,muzzleY,paint);
    }

    public void logic(){
        switch (dirs){
            case Constants.MOVE_LEFT:
                muzzleX -=  muzzleSpeed;
                break;
            case Constants.MOVE_RIGHT:
                muzzleX += muzzleSpeed;
                break;
        }
        //边界处理
        if(muzzleY > screenH || muzzleY <= -40 || muzzleX > screenW || muzzleX <= -40)
        {
            isDead=true;
        }
    }

    //开始破坏
    public void StartMuzzleChange()
    {
        muzzleHandler.post(muzzleRunnable);
    }

    //停止破坏
    public void StopMuzzleChange()
    {
        muzzleHandler.removeCallbacks(muzzleRunnable);
        muzzleHandler.removeCallbacksAndMessages(null);
        index=0;
        bmpMuzzle= BitmapFactory.decodeResource(res,muzzleRight[index]);
    }


    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }


    public int getMuzzleX() {
        return muzzleX;
    }

    public void setMuzzleX(int muzzleX) {
        this.muzzleX = muzzleX;
    }

    public int getMuzzleY() {
        return muzzleY;
    }

    public void setMuzzleY(int muzzleY) {
        this.muzzleY = muzzleY;
    }

    public int getMuzzleSpeed() {
        return muzzleSpeed;
    }

    public void setMuzzleSpeed(int muzzleSpeed) {
        this.muzzleSpeed = muzzleSpeed;
    }

    public Bitmap getBmpMuzzle() {

        return bmpMuzzle;
    }

    public void setBmpMuzzle(Bitmap bmpMuzzle) {
        this.bmpMuzzle = bmpMuzzle;
    }

    public int getMuzzleW() {
        return muzzleW;
    }

    public void setMuzzleW(int muzzleW) {
        this.muzzleW = muzzleW;
    }

    public int getMuzzleH() {
        return muzzleH;
    }

    public void setMuzzleH(int muzzleH) {
        this.muzzleH = muzzleH;
    }

}
