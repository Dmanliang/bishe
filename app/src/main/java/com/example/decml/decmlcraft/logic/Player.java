package com.example.decml.decmlcraft.logic;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;

import com.example.decml.decmlcraft.MyMap;
import com.example.decml.decmlcraft.R;
import com.example.decml.decmlcraft.base.Constants;
import com.example.decml.decmlcraft.logic.blocks.Block;
import com.example.decml.decmlcraft.logic.background.BackGround;
import com.example.decml.decmlcraft.logic.background.BackGroundTwo;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Decml on 2017/3/4.
 */

public class Player{

    //主角坐标和位图
    private int         playerX,playerY;
    //主角宽高
    private int         playerW,playerH;
    //主角血量
    private int         playerHp            =   10;
    //主角护甲
    private int         playerPt            =   2;
    //主角攻击力
    private int         playerAttack        =   3;
    //按钮坐标
    private int         pointx,pointy;
    //主角资源图片
    private Bitmap      bmpPlayer;
    //主角移动速度
    private int         speed               =   1;
    //主角移动标识
    private boolean     isUp,isDown,isRight,isLeft;
    private int         screenH,screenW;
    //碰撞后处于无敌时间
    //计时器
    private int         noCollisionCount    =   0;
    //无敌时间
    private int         noCollisionTime     =   60;
    //判断角色方向
    public static int   direction           =   0;
    //角色移动切换图片
    private int[] playerMoveRight={R.drawable.character_moving_right_02,R.drawable.character_moving_right_03,R.drawable.character_moving_right_04,
            R.drawable.character_moving_right_05,R.drawable.character_moving_right_06,R.drawable.character_moving_right_07,R.drawable.character_moving_right_08,R.drawable.character_moving_right_09,};
    private int[] playerMoveLeft={ R.drawable.character_moving_left_02,R.drawable.character_moving_left_03,R.drawable.character_moving_left_04,
            R.drawable.character_moving_left_05,R.drawable.character_moving_left_06,R.drawable.character_moving_left_07,R.drawable.character_moving_left_08,R.drawable.character_moving_left_09};
    private int[] playerJumpRight={R.drawable.character_jumping_right_01,R.drawable.character_jumping_right_02,R.drawable.character_jumping_right_03,R.drawable.character_jumping_right_04,
            R.drawable.character_jumping_right_05,R.drawable.character_jumping_right_05,R.drawable.character_moving_right_01};
    private int[] playerJumpLeft={R.drawable.character_jumping_left_01,R.drawable.character_jumping_left_02,R.drawable.character_jumping_left_03,R.drawable.character_jumping_left_04,
            R.drawable.character_jumping_left_05,R.drawable.character_jumping_left_05,R.drawable.character_moving_left_01};
    private int[] playerDown={R.drawable.character_stooping_right,R.drawable.character_stooping_left};
    private int[] playerStop={R.drawable.character_moving_right_01,R.drawable.character_moving_left_01};
    private int moveRightIndex=0,moveLeftIndex=0,dir=0;
    //是否碰撞的Boss标识
    private boolean     isCollisionBoss;
    private boolean     isCollisionBlock;
    private boolean     isOpenRunnable;
    private boolean     isJump;
    private ScheduledExecutorService scheduledExecutor;
    private Resources   res;
    private double      time=0,i=0,gtime=0;
    private BackGround  backGround;
    private BackGroundTwo backGroundTwo;
    private int         backX,backY;
    private int         blockX,blockY;
    private int         blockW,blockH;
    //子弹对象
    private Muzzle      muzzle;
    private Bitmap      bmpMuzzle;
    private Bitmap      bmpPlayerHp;
    private Bitmap      bmpPlayerPt;
    private boolean     isOpenGravity;

    public Player(Resources res, Bitmap bmpPlayer,Bitmap bmpPlayerHp,Bitmap bmpPlayerPt, BackGround backGround, BackGroundTwo backGroundTwo, int screenW, int screenH)
    {
        this.res            =   res;
        this.bmpPlayer      =   bmpPlayer;
        this.backGround     =   backGround;
        this.backGroundTwo  =   backGroundTwo;
        this.bmpPlayerHp    =   bmpPlayerHp;
        this.bmpPlayerPt    =   bmpPlayerPt;
        this.screenH        =   screenH;
        this.screenW        =   screenW;
        isCollisionBoss     =   false;
        isOpenGravity       =   false;
        isCollisionBlock    =   false;
        isOpenRunnable      =   false;
        playerX             =   screenW/2-bmpPlayer.getWidth()/2;
        playerY             =   screenH/2-bmpPlayer.getHeight();
        playerW             =   bmpPlayer.getWidth();
        playerH             =   bmpPlayer.getHeight();
        bmpMuzzle           =   BitmapFactory.decodeResource(res,R.drawable.muzzle_right_0);
    }


    //主角的绘制函数
    public void draw(Canvas canvas, Paint paint)
    {
        //绘制主角
        //当处于无敌时间时,让主角闪烁
        if(isCollisionBoss)
        {
            //每2次游戏循环,绘制一次主角
            if(noCollisionCount%2==0)
            {
                canvas.drawBitmap(bmpPlayer,playerX,playerY,paint);
            }
        }
        else
        {
            canvas.drawBitmap(bmpPlayer,playerX,playerY,paint);
        }
        //绘制主角血量
        for(int i=0;i<playerHp;i++)
        {
            canvas.drawBitmap(bmpPlayerHp, i*bmpPlayerHp.getWidth()+2*bmpPlayerHp.getWidth(),bmpPlayerHp.getHeight(), paint);
        }
        //绘制主角护甲
        for(int i=0;i<playerPt;i++)
        {
            canvas.drawBitmap(bmpPlayerPt, i*bmpPlayerPt.getWidth()+2*bmpPlayerPt.getWidth(),bmpPlayerPt.getHeight()*2, paint);
        }
        //绘制主角
        canvas.drawBitmap(bmpPlayer,playerX,playerY,paint);
    }

    public void onTouchEvent(MotionEvent event,
                             Bitmap         bmpKeyBoardUp,   Bitmap         bmpKeyBoardDown,    Bitmap          bmpKeyBoardLeft,    Bitmap          bmpKeyBoardRight,
                             Bitmap         bmpButtonMenu,   Bitmap         bmpButtonActtack,   Bitmap          bmpButtonOk,        Bitmap          bmpButtonBack,  Bitmap          bmpButtonPackage,   Bitmap          bmpButtonJump,
                             KeyBoardClick  keyBoardUp,      KeyBoardClick  keyBoardDown,       KeyBoardClick   keyBoardLeft,       KeyBoardClick   keyBoardRight,
                             KeyBoardClick  buttonMenu,      KeyBoardClick  buttonActtack,      KeyBoardClick   buttonOk,           KeyBoardClick   buttonBack,     KeyBoardClick   buttonPackage,      KeyBoardClick   buttonJump,
                             Package        mPackage,        Menu           menu) {

        switch (event.getAction())
        {
            case MotionEvent.ACTION_UP:
                if(direction== Constants.JUMP_UP)
                {
                    isUp=false;
                    if(dir==0)
                    {
                        bmpPlayer= BitmapFactory.decodeResource(res,playerStop[0]);
                    }
                    else if(dir==1)
                    {
                        bmpPlayer= BitmapFactory.decodeResource(res,playerStop[1]);
                    }
                }
                else if(direction==Constants.JUMP_DOWN)
                {
                    isDown=false;
                    if(dir==0)
                    {
                        bmpPlayer= BitmapFactory.decodeResource(res,playerStop[0]);
                    }
                    else if(dir==1)
                    {
                        bmpPlayer= BitmapFactory.decodeResource(res,playerStop[1]);
                    }
                }
                else if(direction==Constants.MOVE_LEFT)
                {
                    isLeft=false;
                    StopMove();
                    moveLeftIndex = 0;
                    this.bmpPlayer= BitmapFactory.decodeResource(res,playerStop[1]);
                }
                else if(direction==Constants.MOVE_RIGHT)
                {
                    isRight=false;
                    StopMove();
                    moveRightIndex = 0;
                    this.bmpPlayer= BitmapFactory.decodeResource(res,playerStop[0]);
                }
                break;
            case MotionEvent.ACTION_DOWN:
                pointx=(int)event.getX();
                pointy=(int)event.getY();
                if(pointx>=buttonJump.getButtonX()&&pointx<=(buttonJump.getButtonX()+bmpButtonJump.getWidth())&&pointy>=buttonJump.getButtonY()&&pointy<=(buttonJump.getButtonY()+bmpButtonJump.getHeight()))
                {
                    isUp=true;
                    if(!isJump){
                        StartJump();
                    }
                }
                else if(pointx>=keyBoardDown.getButtonX()&&pointx<=(keyBoardDown.getButtonX()+bmpKeyBoardDown.getWidth())&&pointy>=keyBoardDown.getButtonY()&&pointy<=(keyBoardDown.getButtonY()+bmpKeyBoardDown.getHeight()))
                {
                    isDown=true;
                    if(dir==0)
                    {
                        this.bmpPlayer= BitmapFactory.decodeResource(res,playerDown[0]);
                    }
                    else if(dir==1)
                    {
                        this.bmpPlayer= BitmapFactory.decodeResource(res,playerDown[1]);
                    }
                }
                else if(pointx>=keyBoardLeft.getButtonX()&&pointx<=(keyBoardLeft.getButtonX()+bmpKeyBoardLeft.getWidth())&&pointy>=keyBoardLeft.getButtonY()&&pointy<=(keyBoardLeft.getButtonY()+bmpKeyBoardLeft.getHeight()))
                {
                    isLeft=true;
                    direction=Constants.MOVE_LEFT;
                    dir=direction;
                    Move(direction);
                }
                else if(pointx>=keyBoardRight.getButtonX()&&pointx<=(keyBoardRight.getButtonX()+bmpKeyBoardRight.getWidth())&&pointy>=keyBoardRight.getButtonY()&&pointy<=(keyBoardRight.getButtonY()+bmpKeyBoardRight.getHeight()))
                {
                    isRight=true;
                    direction=Constants.MOVE_RIGHT;
                    dir=direction;
                    Move(direction);
                }
                else if(pointx>=buttonActtack.getButtonX()&&pointx<=(buttonActtack.getButtonX()+bmpButtonActtack.getWidth())&&pointy>=buttonActtack.getButtonY()&&pointy<=(buttonActtack.getButtonY()+bmpButtonActtack.getHeight())){
                    muzzle=new Muzzle(res,bmpMuzzle,this,screenW,screenH);
                    MyMap.vcMuzzle.add(muzzle);
                    muzzle.initMuzzle(dir);
                    muzzle.StartMuzzleChange();
                }
                else if(pointx>=buttonPackage.getButtonX()&&pointx<=(buttonPackage.getButtonX()+bmpButtonPackage.getWidth())&&pointy>=buttonPackage.getButtonY()&&pointy<=(buttonPackage.getButtonY()+bmpButtonPackage.getHeight()))
                {
                    mPackage.setOpenPackage(!mPackage.isOpenPackage());
                }
                else if(pointx>=buttonMenu.getButtonX()&&pointx<=(buttonMenu.getButtonX()+bmpButtonMenu.getWidth())&&pointy>=buttonMenu.getButtonY()&&pointy<=(buttonMenu.getButtonY()+bmpButtonMenu.getHeight()))
                {
                    menu.setOpenMenu(!menu.isOpenMenu());
                }
                break;
        }
    }

    public void StartJump()
    {
        isJump  =   true;
        jumpHandler.post(jumpRunnable);
    }
    Runnable jumpRunnable=new Runnable()
    {
        @Override
        public void run()
        {
            time=time+0.25;
            i=(i+0.25)%7;
            playerY=playerY-(int)(20*time-0.5*10*time*time);
            Message message=new Message();
            message.what=(int)i;
            jumpHandler.sendMessage(message);
        }
    };

    Handler jumpHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (direction)
            {
                case Constants.MOVE_LEFT:
                    if(time<=7)
                        bmpPlayer=BitmapFactory.decodeResource(res,playerJumpLeft[msg.what]);
                    break;
                case Constants.MOVE_RIGHT:
                    if(time<=7)
                        bmpPlayer=BitmapFactory.decodeResource(res,playerJumpRight[msg.what]);
                    break;
            }
            jumpHandler.postDelayed(jumpRunnable,40);
        }
    };

    public void Move(int duraction)
    {
        final int dct=duraction;
        scheduledExecutor= Executors.newSingleThreadScheduledExecutor();
        scheduledExecutor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                Message msg=new Message();
                msg.what=dct;
                movehandler.sendMessage(msg);
            }
        },0,50, TimeUnit.MILLISECONDS);
    }

    private Handler movehandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int direction=msg.what;
            switch (direction)
            {
                case Constants.JUMP_UP:
                    break;
                case Constants.JUMP_DOWN:
                    break;
                case Constants.MOVE_LEFT:
                    moveLeftIndex=(moveLeftIndex+1)%playerMoveLeft.length;
                    bmpPlayer= BitmapFactory.decodeResource(res,playerMoveLeft[moveLeftIndex]);
                    ObjectMoveLogic(direction);
                    break;
                case Constants.MOVE_RIGHT:
                    moveRightIndex=(moveRightIndex+1)%playerMoveRight.length;
                    bmpPlayer= BitmapFactory.decodeResource(res,playerMoveRight[moveRightIndex]);
                    ObjectMoveLogic(direction);
                    break;
                case Constants.STOP:
                    if(dir==0)
                    {
                        bmpPlayer= BitmapFactory.decodeResource(res,playerStop[0]);
                    }
                    else if(dir==1)
                    {
                        bmpPlayer= BitmapFactory.decodeResource(res,playerStop[1]);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    public void ObjectMoveLogic(int direction){
        if(!isCollisionBlock){
            backGround.logic(direction);
            backGroundTwo.logic(direction);
            for(int i = 0; i< MyMap.blocks.size(); i++)
            {
                MyMap.blocks.get(i).logic();
            }
        }
    }

    public void StopMove()
    {
        if(scheduledExecutor!=null)
        {
            scheduledExecutor.shutdownNow();
            scheduledExecutor=null;
            movehandler.removeCallbacksAndMessages(null);
        }
    }

    public void JumpStop()
    {
        jumpHandler.removeCallbacksAndMessages(null);
        jumpHandler.removeCallbacks(jumpRunnable);
        i       =   0;
        time    =   0;
        isJump  =   false;
    }

    //主角逻辑
    public void logic() {
        if(!isJump){
            //判断主角是否无碰撞
            if(isFalling()){
                isOpenGravity   = true;
            }else{
                isOpenGravity   = false;
                StopGravity();
                isOpenRunnable  = false;
            }
            if(isOpenGravity && !isOpenRunnable) {
                StartGravity();
            }
        }

        //处理主角移动
        if (isLeft) {
            if (isCollsionWithBlock()) {
                playerX = blockX+blockW;
                isCollisionBlock =  true;
            }else{
                /*playerX -= speed;*/
                isCollisionBlock =  false;
            }
        }
        if (isRight) {
            if (isCollsionWithBlock()) {
                playerX = blockX-playerW;
                isCollisionBlock =  true;
            }else{
               /* playerX += speed;*/
                isCollisionBlock =  false;
            }
        }
        if (isUp) {
            if (isCollsionWithBlock()) {
                playerY = blockY-playerH;
                JumpStop();
            }else{
             //   isOpenGravity = true;
            }
        }
        if (isDown) {

        }
        /*//判断屏幕x边界
        if (playerX + bmpPlayer.getWidth() >= screenW) {
            playerX = screenW - bmpPlayer.getWidth();
        } else if (playerX <= 0) {
            playerX = 0;
        }
        //判断屏幕y边界
        if (playerY + bmpPlayer.getHeight() >= screenH) {
            playerY = screenH - bmpPlayer.getHeight();
            JumpStop();
        } else if (playerY <= 0) {
            playerY= 0;
        }*/

        //处理无敌状态
        if(isCollisionBoss)
        {
            //计时器开始计时
            noCollisionCount++;
            if(noCollisionCount>=noCollisionTime)
            {
                //无敌时间过后,接触无敌状态及初始化计时器
                isCollisionBoss=false;
                noCollisionCount=0;
            }
        }
    }

    //主角与方块碰撞判断
    public boolean isCollsionWithBlock(){
       if(direction == Constants.JUMP_UP) {
           for(int i=0;i<MyMap.blocks.size();i++){
               Block   block = MyMap.blocks.get(i);
               if(block.getBlockType() != 2){
                   if( (playerY+playerH) > block.getBlockY() && playerY < (block.getBlockY()+block.getBlockH()) && playerX < (block.getBlockX() + block.getBlockW()) && (playerX + playerW) > block.getBlockX()) {
                       setPlayer(block);
                       StopGravity();
                       return false;
                   }
               }
           }
           return true;
       } else if(direction == Constants.JUMP_DOWN) {
          /* for(int i=0;i<MyMap.blocks.size();i++){
               Block   block = MyMap.blocks.get(i);
               if( (playerY+playerH) >= block.getBlockY() && playerY <(block.getBlockY()+block.getBlockH()) && playerX< (block.getBlockX()+block.getBlockW()) && (playerX+playerW)>block.getBlockX()){
                   playerY = block.getBlockY()+block.getBlockH();
                   return false;
               }
           }*/
       }else if(direction == Constants.MOVE_LEFT){
           for(int i=0;i<MyMap.blocks.size();i++){
               Block   block = MyMap.blocks.get(i);
               if(block.getBlockType() != 2){
                   if( (playerY+playerH) > block.getBlockY() && playerY <(block.getBlockY()+block.getBlockH()) && playerX < (block.getBlockX() + block.getBlockW()) && (playerX + playerW) > block.getBlockX()) {
                       setPlayer(block);
                       return true;
                   }
               }
           }
           return false;
       } else if(direction == Constants.MOVE_RIGHT){
            for(int i=0;i<MyMap.blocks.size();i++){
                Block   block = MyMap.blocks.get(i);
                if(block.getBlockType() != 2) {
                    if ((playerY + playerH) > block.getBlockY() && playerY < (block.getBlockY() + block.getBlockH()) && playerX < (block.getBlockX() + block.getBlockW()) && (playerX + playerW) > block.getBlockX()) {
                        setPlayer(block);
                        return true;
                    }
                }
            }
           return false;
        }
        return false;
    }

    public void setPlayer(Block block){
        blockX  =   block.getBlockX();
        blockY  =   block.getBlockY();
        blockW  =   block.getBlockW();
        blockH  =   block.getBlockH();
    }

    //判断碰撞(主角与怪物)
    public boolean isCollsionWithBoss(Boss boss){
        //是否处于无敌时间
        if(isCollisionBoss==false)
        {
            int x2  =   boss.getbossX();
            int y2  =   boss.getbossY();
            int w2  =   boss.getbossW();
            int h2  =   boss.getbossH();
            if(playerX>=x2 && playerX>=x2+w2)
            {
                return false;
            }
            else if(playerX<=x2 && playerX+bmpPlayer.getWidth()<=x2)
            {
                return false;
            }
            else if(playerY>=y2 && playerY>=y2+h2)
            {
                return false;
            }
            else if(playerY<=y2 && playerY+bmpPlayer.getHeight()<=y2)
            {
                return false;
            }
            //碰撞即进入无敌状态
            isCollisionBoss=true;
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean isFalling(){
        for(int i=0;i<MyMap.blocks.size();i++){
            Block   block = MyMap.blocks.get(i);
            if(block.getBlockType() != 2){
                if( (playerY+playerH) >= block.getBlockY() && playerY <= (block.getBlockY()+block.getBlockH()) && playerX < (block.getBlockX() + block.getBlockW()) && (playerX + playerW) > block.getBlockX()) {
                    setPlayer(block);
                    return false;
                }
            }
        }
        return true;
    }

    public void StartGravity(){
        isOpenRunnable  =   true;
        gravityHandler.post(gravityRunable);
    }

    public void StopGravity(){
        gravityHandler.removeCallbacksAndMessages(null);
        gravityHandler.removeCallbacks(gravityRunable);
        playerY = blockY-playerH;
        gtime   = 0;
        if(direction    ==  Constants.MOVE_LEFT){
            bmpPlayer=BitmapFactory.decodeResource(res,R.drawable.character_moving_left_01);
        }else if(direction  ==  Constants.MOVE_RIGHT){
            bmpPlayer=BitmapFactory.decodeResource(res,R.drawable.character_moving_right_01);
        }
    }

    private Handler gravityHandler  =   new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(direction    ==  Constants.MOVE_LEFT){
                bmpPlayer=BitmapFactory.decodeResource(res,R.drawable.character_jumping_left_05);
            }else if(direction  ==  Constants.MOVE_RIGHT){
                bmpPlayer=BitmapFactory.decodeResource(res,R.drawable.character_jumping_right_05);
            }
            gravityHandler.postDelayed(gravityRunable,80);
        }
    };

    private Runnable gravityRunable   =  new Runnable() {
        @Override
        public void run() {
            Message message=new Message();
            gtime=gtime+0.25;
            playerY=playerY+(int)(0.5*10*gtime*gtime);
            gravityHandler.sendMessage(message);

        }
    };


    public void setPlayerHp(int hp)
    {
        this.playerHp=hp;
    }

    public int getPlayerHp()
    {
        return	playerHp;
    }

    public Bitmap getBmpPlayer() {
        return bmpPlayer;
    }

    public void setBmpPlayer(Bitmap bmpPlayer) {
        this.bmpPlayer = bmpPlayer;
    }

    public int getPlayerX() {
        return playerX;
    }

    public void setPlayerX(int playerX) {
        this.playerX = playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public void setPlayerY(int playerY) {
        this.playerY = playerY;
    }

    public int getPointx() {
        return pointx;
    }

    public void setPointx(int pointx) {
        this.pointx = pointx;
    }

    public int getPointy() {
        return pointy;
    }

    public void setPointy(int pointy) {
        this.pointy = pointy;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Resources getRes() {
        return res;
    }

    public void setRes(Resources res) {
        this.res = res;
    }

    public int getBackX() {
        return backX;
    }

    public void setBackX(int backX) {
        this.backX = backX;
    }

    public int getBackY() {
        return backY;
    }

    public void setBackY(int backY) {
        this.backY = backY;
    }

    public BackGround getBackGround() {
        return backGround;
    }

    public void setBackGround(BackGround backGround) {
        this.backGround = backGround;
    }

    public int getPlayerW() {
        return playerW;
    }

    public void setPlayerW(int playerW) {
        this.playerW = playerW;
    }

    public int getPlayerH() {
        return playerH;
    }

    public void setPlayerH(int playerH) {
        this.playerH = playerH;
    }

    public static int getDirection() {
        return direction;
    }

    public static void setDirection(int direction) {
        Player.direction = direction;
    }

    public int getPlayerAttack() {
        return playerAttack;
    }

    public void setPlayerAttack(int playerAttack) {
        this.playerAttack = playerAttack;
    }

    public boolean isCollisionBoss() {
        return isCollisionBoss;
    }

    public void setCollisionBoss(boolean collision) {
        isCollisionBoss = collision;
    }


    public int getPlayerPt() {
        return playerPt;
    }

    public void setPlayerPt(int playerPt) {
        if(playerPt <= 0){
            playerPt    =   0;
        }else{
            this.playerPt = playerPt;
        }
    }
}