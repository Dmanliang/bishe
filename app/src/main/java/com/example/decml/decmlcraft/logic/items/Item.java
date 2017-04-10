package com.example.decml.decmlcraft.logic.items;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Decml on 2017/4/4.
 */

public class Item {

    private Resources   res;
    //道具资源图片
    private Bitmap bmpItem;
    //道具坐标
    private int     itemX,itemY;
    //道具宽高
    private int     itemW;
    private int     itemH;
    //道具数量
    private int     amount;
    //道具类型
    private int     itemType;
    private int     screenW,screenH;

    public Item(Resources res,Bitmap bmpItem,int itemType,int itemX,int itemY,int screenW,int screenH){
        this.res        =   res;
        this.bmpItem    =   bmpItem;
        this.screenH    =   screenH;
        this.screenW    =   screenW;
        this.itemType   =   itemType;
        this.itemX      =   itemX;
        this.itemY      =   itemY;
        this.amount     =   1;
        itemH           =   bmpItem.getHeight();
        itemW           =   bmpItem.getWidth();
    }
    
    public void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(bmpItem,itemX,itemY,paint);
        paint.setColor(Color.WHITE);
        paint.setTextSize(18);
        canvas.drawText(amount+"",itemX+itemW-10,itemY+itemH,paint);
    }

    public void logic(){

    }


    public Bitmap getBmpItem() {
        return bmpItem;
    }

    public void setBmpItem(Bitmap bmpItem) {
        this.bmpItem = bmpItem;
    }

    public int getItemX() {
        return itemX;
    }

    public void setItemX(int itemX) {
        this.itemX = itemX;
    }

    public int getItemY() {
        return itemY;
    }

    public void setItemY(int itemY) {
        this.itemY = itemY;
    }

    public int getItemW() {
        return itemW;
    }

    public void setItemW(int itemW) {
        this.itemW = itemW;
    }

    public int getItemH() {
        return itemH;
    }

    public void setItemH(int itemH) {
        this.itemH = itemH;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

}

