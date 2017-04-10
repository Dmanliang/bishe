package com.example.decml.decmlcraft.logic;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.decml.decmlcraft.MyMap;
import com.example.decml.decmlcraft.logic.items.FoodApple;
import com.example.decml.decmlcraft.logic.items.FoodChicken;
import com.example.decml.decmlcraft.logic.items.Item;

/**
 * Created by Decml on 2017/4/3.
 */

public class Package {

    private Resources   res;
    //背包坐标
    private int         packageX,packageY;
    //背包宽高
    private int         packageW,packageH;
    //背包资源
    private Bitmap      bmpPackage;
    //地图宽高
    private int         screenW,screenH;
    //是否被打开
    private boolean     isOpenPackage;
    //道具对象
    private Item item;
    //道具数组，用于存放道具
    private Item[]  items   =   new Item[10];

    public Package(Resources res, Bitmap bmpPackage, int screenW, int screenH){
        this.res            =   res;
        this.bmpPackage     =   bmpPackage;
        this.screenH        =   screenH;
        this.screenW        =   screenW;
        packageH            =   bmpPackage.getHeight();
        packageW            =   bmpPackage.getWidth();
        packageX            =   screenW/4;
        packageY            =   bmpPackage.getHeight()+20;
        isOpenPackage       =   false;
    }


    public void draw(Canvas canvas,Paint paint){
        if(isOpenPackage){
            canvas.drawBitmap(bmpPackage,packageX,packageY,paint);
            for(int i=0;i< items.length;i++){
                if(items[i]!=null ){
                    items[i].setItemX(packageX+29+52*i);
                    items[i].setItemY(packageY+14);
                    items[i].draw(canvas,paint);
                }
            }
        }
    }


	//道具创建
	public void addItems(Item item){
        this.item   =   item;
        for(int i=0;i<items.length;i++){
            if (items[i] != null) {
                if (item.getItemType() == items[i].getItemType()) {
                    items[i].setAmount(items[i].getAmount() + item.getAmount());
                    break;
                }
            }
            else{
                items[i]    =   item;
                break;
            }
        }
	}

    public void logic(){

    }

    public void onTouchEvent(MotionEvent event){

    }

    public Resources getRes() {
        return res;
    }

    public void setRes(Resources res) {
        this.res = res;
    }

    public int getPackageX() {
        return packageX;
    }

    public void setPackageX(int packageX) {
        this.packageX = packageX;
    }

    public int getPackageY() {
        return packageY;
    }

    public void setPackageY(int packageY) {
        this.packageY = packageY;
    }

    public int getPackageW() {
        return packageW;
    }

    public void setPackageW(int packageW) {
        this.packageW = packageW;
    }

    public int getPackageH() {
        return packageH;
    }

    public void setPackageH(int packageH) {
        this.packageH = packageH;
    }

    public Bitmap getBmpPackage() {
        return bmpPackage;
    }

    public void setBmpPackage(Bitmap bmpPackage) {
        this.bmpPackage = bmpPackage;
    }

    public boolean isOpenPackage() {
        return isOpenPackage;
    }

    public void setOpenPackage(boolean openPackage) {
        isOpenPackage = openPackage;
    }
}
