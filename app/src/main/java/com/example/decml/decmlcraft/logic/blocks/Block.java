package com.example.decml.decmlcraft.logic.blocks;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.decml.decmlcraft.MyMap;
import com.example.decml.decmlcraft.base.Constants;
import com.example.decml.decmlcraft.logic.Player;


/**
 * Created by Decml on 2017/2/22.
 */

public class Block {

    private Resources res;
    //方块坐标
    private int blockX, blockY;
    //方块宽高
    private int blockW, blockH;
    //方块图片
    private Bitmap bmpBlock;
    //方块破坏图片
/*    public Bitmap bmpBlockChange;
    public BlockChange blockChange;*/
    private int blockType;

    public Block(Resources res, Bitmap bmpBlock, int blockX, int blockY, int blockType) {
        this.res            =   res;
        this.blockX         =   blockX;
        this.blockY         =   blockY;
        this.bmpBlock       =   bmpBlock;
        this.blockType      =   blockType;
        blockW              =   bmpBlock.getWidth();
        blockH              =   bmpBlock.getHeight();
   //     bmpBlockChange = BitmapFactory.decodeResource(res, R.drawable.image_no_change);
   //     blockChange = new BlockChange(res, bmpBlockChange, blockX, blockY);
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(bmpBlock, blockX, blockY, paint);
  //      blockChange.draw(canvas, paint);
    }

    public void logic(){
        //处理主角移动
        if (Player.direction== Constants.MOVE_LEFT) {
            blockX += MyMap.speed;
   //         blockChange.setX(blockX);
        }
        if (Player.direction==Constants.MOVE_RIGHT) {
            blockX -= MyMap.speed;
     //       blockChange.setX(blockX);
        }
    }


    public int getBlockType() {
        return blockType;
    }

    public void setBlockType(int blockType) {
        this.blockType = blockType;
    }

    public Resources getRes() {
        return res;
    }

    public void setRes(Resources res) {
        this.res = res;
    }

    public int getBlockW() {
        return blockW;
    }

    public void setBlockW(int blockW) {
        this.blockW = blockW;
    }

    public int getBlockH() {
        return blockH;
    }

    public void setBlockH(int blockH) {
        this.blockH = blockH;
    }

    public int getBlockX() {
        return blockX;
    }

    public void setBlockX(int blockX) {
        this.blockX = blockX;
    }

    public int getBlockY() {
        return blockY;
    }

    public void setBlockY(int blockY) {
        this.blockY = blockY;
    }

    public Bitmap getBmpBlock() {
        return bmpBlock;
    }

    public void setBmpBlock(Bitmap bmpBlock) {
        this.bmpBlock = bmpBlock;
    }


    public void onTouchEvent(MotionEvent motionEvent) {
     //   blockChange.onTouchEvent(motionEvent, bmpBlock, this);
    }


}




