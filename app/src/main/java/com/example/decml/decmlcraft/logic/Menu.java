package com.example.decml.decmlcraft.logic;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

/**
 * Created by Decml on 2017/4/3.
 */

public class Menu{

    private Resources       res;
    //菜单坐标
    private int             MenuX,MenuY;
    //菜单宽高
    private int             MenuW,MenuH;
    //菜单资源
    private Bitmap          bmpMenu;
    //地图宽高
    private int             screenW,screenH;
    //是否被打开
    private boolean         isOpenMenu;
    private KeyBoardClick   buttonMenuStart, buttonMenuExit, buttonReturn;

    public Menu(Resources res, Bitmap bmpMenu,KeyBoardClick buttonMenuStart,KeyBoardClick buttonMenuExit,KeyBoardClick buttonReturn, int screenW, int screenH){
        this.res                =   res;
        this.bmpMenu            =   bmpMenu;
        this.screenH            =   screenH;
        this.screenW            =   screenW;
        MenuH                   =   bmpMenu.getHeight();
        MenuW                   =   bmpMenu.getWidth();
        MenuX                   =   0;
        MenuY                   =   0;
        this.buttonMenuStart    =   buttonMenuStart;
        this.buttonMenuExit     =   buttonMenuExit;
        this.buttonReturn       =   buttonReturn;
        isOpenMenu              =   false;
    }

    public void draw(Canvas canvas, Paint paint){
        if(isOpenMenu){
            canvas.drawBitmap(bmpMenu,MenuX,MenuY,paint);
            buttonMenuStart.draw(canvas,paint);
            buttonMenuExit.draw(canvas,paint);
            buttonReturn.draw(canvas,paint);
        }
    }

    public void logic(){}

    public void onTouchEvent(MotionEvent event){
        if(isOpenMenu){
            buttonMenuStart.onTouchEvent(event);
            buttonMenuExit.onTouchEvent(event);
            buttonReturn.onTouchEvent(event);
        }
    }

    public boolean isOpenMenu() {
        return isOpenMenu;
    }

    public void setOpenMenu(boolean openMenu) {
        isOpenMenu = openMenu;
    }

    public Bitmap getBmpMenu() {
        return bmpMenu;
    }

    public void setBmpMenu(Bitmap bmpMenu) {
        this.bmpMenu = bmpMenu;
    }

    public int getMenuH() {
        return MenuH;
    }

    public void setMenuH(int menuH) {
        MenuH = menuH;
    }

    public int getMenuW() {
        return MenuW;
    }

    public void setMenuW(int menuW) {
        MenuW = menuW;
    }

    public int getMenuY() {
        return MenuY;
    }

    public void setMenuY(int menuY) {
        MenuY = menuY;
    }

    public int getMenuX() {
        return MenuX;
    }

    public void setMenuX(int menuX) {
        MenuX = menuX;
    }

    public Resources getRes() {
        return res;
    }

    public void setRes(Resources res) {
        this.res = res;
    }

}
