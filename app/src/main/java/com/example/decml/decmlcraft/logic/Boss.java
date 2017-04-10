package com.example.decml.decmlcraft.logic;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.decml.decmlcraft.MyMap;
import com.example.decml.decmlcraft.R;
import com.example.decml.decmlcraft.base.Constants;
import com.example.decml.decmlcraft.logic.blocks.Block;

/**
 * Created by Decml on 2017/3/9.
 */
//怪物类
public class Boss {

    private Resources res;
    //按钮坐标
    private int     pointx,pointy;
    //Boss坐标
    private int     bossX,bossY;
    //Boss的宽高
    private int     bossW,bossH;
    //Boss血量
    private int     bossHp       = 20;
    //Boss攻击
    private int     bossAttack   = 2;
    //Boss资源图片
    private Bitmap  bmpBoss;
    //Boss移动速度
    private int     speed        = 5;
    //Boss移动方向
    private boolean isUp,isDown,isRight,isLeft;
    private double  gtime        = 0;
    private int     screenW,screenH;
    //方块坐标
    private int     blockX,blockY;
    //方块宽高
    private int     blockW,blockH;
    //判断碰撞标识
    private boolean isCollision=false;
    //判断Boss方向
    public static int direction=Constants.WALK_RIGHT;
    //行走动画
    private int[] bossRightWalk={R.drawable.boss_walk_right_0,R.drawable.boss_walk_right_1,R.drawable.boss_walk_right_2,R.drawable.boss_walk_right_3,R.drawable.boss_walk_right_4,
            R.drawable.boss_walk_right_5,R.drawable.boss_walk_right_6,R.drawable.boss_walk_right_7,R.drawable.boss_walk_right_8,R.drawable.boss_walk_right_9};
    private int[] bossLeftWalk={R.drawable.boss_walk_left_0,R.drawable.boss_walk_left_1,R.drawable.boss_walk_left_2,R.drawable.boss_walk_left_3,R.drawable.boss_walk_left_4,
            R.drawable.boss_walk_left_5,R.drawable.boss_walk_left_6,R.drawable.boss_walk_left_7,R.drawable.boss_walk_left_8,R.drawable.boss_walk_left_9};
    //攻击动画
    private int[] bossRightAttack={R.drawable.boss_attack_right_0,R.drawable.boss_attack_right_1,R.drawable.boss_attack_right_2,R.drawable.boss_attack_right_3,R.drawable.boss_attack_right_4,
            R.drawable.boss_attack_right_5,R.drawable.boss_attack_right_6,R.drawable.boss_attack_right_7,R.drawable.boss_attack_right_8,R.drawable.boss_attack_right_9};
    private int[] bossLeftAttack={R.drawable.boss_attack_left_0,R.drawable.boss_attack_left_1,R.drawable.boss_attack_left_2,R.drawable.boss_attack_left_3,R.drawable.boss_attack_left_4,
            R.drawable.boss_attack_left_5,R.drawable.boss_attack_left_6,R.drawable.boss_attack_left_7,R.drawable.boss_attack_left_8,R.drawable.boss_attack_left_9};
    //死亡动画
    private int[] bossRightDead={R.drawable.boss_dead_right_0,R.drawable.boss_dead_right_1,R.drawable.boss_dead_right_2,R.drawable.boss_dead_right_3,R.drawable.boss_dead_right_4,
            R.drawable.boss_dead_right_5,R.drawable.boss_dead_right_6,R.drawable.boss_dead_right_7,R.drawable.boss_dead_right_8,R.drawable.boss_dead_right_9};
    private int[] bossLeftDead={R.drawable.boss_dead_left_0,R.drawable.boss_dead_left_1,R.drawable.boss_dead_left_2,R.drawable.boss_dead_left_3,R.drawable.boss_dead_left_4,
            R.drawable.boss_dead_left_5,R.drawable.boss_dead_left_6,R.drawable.boss_dead_left_7,R.drawable.boss_dead_left_8,R.drawable.boss_dead_left_9};
    //空闲动画
    private int[] bossIdle={R.drawable.boss_idle_right_0,R.drawable.boss_idle_right_1,R.drawable.boss_idle_right_2,R.drawable.boss_idle_right_3,R.drawable.boss_idle_right_4,
            R.drawable.boss_idle_right_5,R.drawable.boss_idle_right_6,R.drawable.boss_idle_right_7,R.drawable.boss_idle_right_8,R.drawable.boss_idle_right_9,
            R.drawable.boss_walk_right_0,R.drawable.boss_walk_right_1,R.drawable.boss_walk_right_2,R.drawable.boss_walk_right_3,
            R.drawable.boss_walk_left_0,R.drawable.boss_walk_left_1,R.drawable.boss_walk_left_2,R.drawable.boss_walk_left_3,
            R.drawable.boss_idle_left_0,R.drawable.boss_idle_left_1,R.drawable.boss_idle_left_2,R.drawable.boss_idle_left_3,R.drawable.boss_idle_left_4,
            R.drawable.boss_idle_left_5,R.drawable.boss_idle_left_6,R.drawable.boss_idle_left_7,R.drawable.boss_idle_left_8,R.drawable.boss_idle_left_9};

    //判断怪物是否死亡
    public boolean isDead;
    private int walkRightIndex=0,walkLeftIndex=0,deadRightIndex=0,deadLeftIndex=0,attackRightIndex=0,attackLeftIndex=0,idleIndex=0;
    private boolean     isOpenGravity;
    private boolean     isOpenRunnable;

    public Boss(Resources res, Bitmap bmpBoss, int screenW, int screenH){
        this.res         =   res;
        this.bmpBoss     =   bmpBoss;
        this.screenH     =   screenH;
        this.screenW     =   screenW;
        isDead           =   false;
        isOpenGravity    =   false;
        isOpenRunnable   =   false;
        bossX            =   3*screenW/4-bmpBoss.getWidth();
        bossY            =   0;
        bossH            =   bmpBoss.getHeight();
        bossW            =   bmpBoss.getWidth();
    }

    public void draw(Canvas canvas, Paint paint){
        //绘制怪物
        canvas.drawBitmap(bmpBoss,bossX,bossY,paint);
    }


    public void StartAction(int isAction)
    {

        switch (isAction)
        {
            case Constants.IDLE:
                StopAction(direction);
                direction=Constants.IDLE;
                Idle();
                break;
            case Constants.ATTACK_LEFT:
                StopAction(direction);
                direction=Constants.ATTACK_LEFT;
                Attack();
                break;
            case Constants.ATTACK_RIGHT:
                StopAction(direction);
                direction=Constants.ATTACK_RIGHT;
                Attack();
                break;
            case Constants.DEAD_LEFT:
                StopAction(direction);
                direction=Constants.DEAD_LEFT;
                Dead();
                break;
            case Constants.DEAD_RIGHT:
                StopAction(direction);
                direction=Constants.DEAD_RIGHT;
                Dead();
                break;
            case Constants.WALK_LEFT:
                StopAction(direction);
                direction=Constants.WALK_LEFT;
                Walk();
                break;
            case Constants.WALK_RIGHT:
                StopAction(direction);
                direction=Constants.WALK_RIGHT;
                Walk();
                break;
        }
    }

    public void StopAction(int isAction){
        switch (isAction){
            case Constants.IDLE:
                StopIdle();
                break;
            case Constants.ATTACK_LEFT:
                StopAttack();
                break;
            case Constants.ATTACK_RIGHT:
                StopAttack();
                break;
            case Constants.DEAD_LEFT:
                StopDead();
                break;
            case Constants.DEAD_RIGHT:
                StopDead();
                break;
            case Constants.WALK_LEFT:
                StopWalk();
                break;
            case Constants.WALK_RIGHT:
                StopWalk();
                break;
            default:
                break;
        }
    }

    //闲置动画
    public void Idle(){
        idleHandler.post(idleRunnable);
    }

    //停止闲置
    public void StopIdle(){
        idleHandler.removeCallbacksAndMessages(null);
        idleHandler.removeCallbacks(idleRunnable);
    }

    //开始行走
    public void Walk()
    {
        walkHandler.post(walkRunnable);
    }

    //停止行走
    public void StopWalk()
    {
        walkHandler.removeCallbacksAndMessages(null);
        walkHandler.removeCallbacks(walkRunnable);
        StartAction(Constants.IDLE);
    }

    //开始攻击动画
    public void Attack(){

        attackHandler.post(attackRunnable);
    }

    //停止攻击动画
    public void StopAttack(){
        attackHandler.removeCallbacksAndMessages(null);
        attackHandler.removeCallbacks(attackRunnable);
        StartAction(Constants.IDLE);
    }

    //开始死亡动画
    public void Dead(){
        deadHandler.post(deadRunnable);
    }

    //停止死亡动画
    public void StopDead(){
        deadHandler.removeCallbacksAndMessages(null);
        deadHandler.removeCallbacks(deadRunnable);
        isDead  =   true;
    }

    private Handler idleHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            idleIndex=(idleIndex+1)%bossIdle.length;
            bossY=screenH-bmpBoss.getHeight();
            bmpBoss= BitmapFactory.decodeResource(res,bossIdle[idleIndex]);
            idleHandler.postDelayed(idleRunnable,300);
        }
    };

    private Runnable idleRunnable=new Runnable() {
        @Override
        public void run() {
            Message message=new Message();
            message.what=direction;
            idleHandler.sendMessage(message);
        }
    };

    private Handler walkHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int direction=msg.what;
            switch (direction)
            {
                case Constants.WALK_LEFT:
                    walkLeftIndex=(walkLeftIndex+1)%bossLeftWalk.length;
                    bossY=screenH-bmpBoss.getHeight();
                    bmpBoss= BitmapFactory.decodeResource(res,bossLeftWalk[walkLeftIndex]);
                    break;
                case Constants.WALK_RIGHT:
                    walkRightIndex=(walkRightIndex+1)%bossLeftWalk.length;
                    bossY=screenH-bmpBoss.getHeight();
                    bmpBoss= BitmapFactory.decodeResource(res,bossRightWalk[walkRightIndex]);
                    break;
                case Constants.IDLE:
                    if(direction==0)
                    {
                        bmpBoss= BitmapFactory.decodeResource(res,R.drawable.boss_walk_left_0);
                    }
                    else if(direction==1)
                    {
                        bmpBoss= BitmapFactory.decodeResource(res,R.drawable.boss_walk_right_0);
                    }
                    break;
                default:
                    break;
            }
            walkHandler.postDelayed(walkRunnable,100);
        }
    };

    private Runnable walkRunnable=new Runnable() {
        @Override
        public void run() {
            Message message=new Message();
            message.what=direction;
            walkHandler.sendMessage(message);
        }
    };

    private Handler attackHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int direction=msg.what;
            switch (direction) {
                case Constants.ATTACK_LEFT:
                    attackLeftIndex = (attackLeftIndex + 1) % bossLeftAttack.length;
                    bossY = screenH - bmpBoss.getHeight();
                    bmpBoss = BitmapFactory.decodeResource(res, bossLeftAttack[attackLeftIndex]);
                    break;
                case Constants.ATTACK_RIGHT:
                    attackRightIndex = (attackRightIndex + 1) % bossLeftAttack.length;
                    bossY = screenH - bmpBoss.getHeight();
                    bmpBoss = BitmapFactory.decodeResource(res, bossRightAttack[attackRightIndex]);
                    break;
                case Constants.IDLE:
                    if (direction == 0) {
                        bmpBoss = BitmapFactory.decodeResource(res, R.drawable.boss_walk_left_0);
                    } else if (direction == 1) {
                        bmpBoss = BitmapFactory.decodeResource(res, R.drawable.boss_walk_right_0);
                    }
                    break;
                default:
                    break;
            }
            attackHandler.postDelayed(attackRunnable,80);
        }
    };

    private Runnable attackRunnable=new Runnable() {
        @Override
        public void run() {
            Message message=new Message();
            message.what=direction;
            attackHandler.sendMessage(message);
        }
    };

    private Handler deadHandler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int direction = msg.what;
            switch (direction) {
                case Constants.DEAD_LEFT:
                    deadLeftIndex ++;
                    bossY = screenH - bmpBoss.getHeight();
                    bmpBoss = BitmapFactory.decodeResource(res, bossLeftDead[deadLeftIndex]);
                    break;
                case Constants.DEAD_RIGHT:
                    deadRightIndex ++;
                    bossY = screenH - bmpBoss.getHeight();
                    bmpBoss = BitmapFactory.decodeResource(res, bossRightDead[deadRightIndex]);
                    break;
                default:
                    break;
            }
            if(deadLeftIndex==bossLeftDead.length-1 || deadRightIndex==bossRightDead.length-1){
                StopDead();
            }
            else{
                deadHandler.postDelayed(deadRunnable,80);
            }
        }
    };

    private Runnable deadRunnable=new Runnable() {
        @Override
        public void run() {
            Message message=new Message();
            message.what=direction;
            deadHandler.sendMessage(message);
        }
    };

    //Boss逻辑
    public void logic() {

        //判断Boss是否无碰撞
        if(isFalling()){
            isOpenGravity   = true;
        }else if(!isFalling()){
            isOpenGravity   = false;
            StopGravity();
            isOpenRunnable  = false;
          //  StartAction(Constants.WALK_RIGHT);
        }
        if(isOpenGravity && !isOpenRunnable) {
            StartGravity();
        }

        if(bossHp == 0){
            isDead =true;
        }
        //处理Boss移动
        if (direction==Constants.WALK_LEFT ){
            if(!isDead && !isCollisionWithBlock()){
                bossX   -=  speed;
            }else if(!isDead && isCollisionWithBlock()){
                bossX   =   blockX+blockW;
            //    StopAction(Constants.WALK_LEFT);
            }
        }
        if(!isDead && !isCollisionWithBlock()){
            bossX   +=  speed;
        }else if(!isDead && isCollisionWithBlock()){
            bossX   =   blockX-bossW;
         //   StopAction(Constants.WALK_RIGHT);
        }
        if (direction==Constants.JUMP_UP) {

        }
        if (direction==Constants.JUMP_DOWN) {

        }

       /* //判断屏幕y边界
        if (bossX + bmpBoss.getHeight() >= screenH) {
            bossY = screenH - bossH;
        } else if (bossY <= 0) {
            bossY= 0;
        }*/
    }


    //判断碰撞(Boss被主角子弹击中)
    public boolean isCollisonWithPlayer(Muzzle muzzle)
    {
        int x2  =   muzzle.getMuzzleX();
        int y2  =   muzzle.getMuzzleY();
        int w2  =   muzzle.getMuzzleW();
        int h2  =   muzzle.getMuzzleH();
        if(bossX>=x2 && bossX>=x2+w2)
        {
            return false;
        }
        else if(bossX<=x2 && bossX+bossW<=x2)
        {
            return false;
        }
        else if(bossY>=y2 && bossY>=y2+h2)
        {
            return false;
        }
        else if(bossY<=y2 && bossY+bossH<=y2)
        {
            return false;
        }
        return true;
    }

    public boolean isFalling(){
        for(int i=0;i<MyMap.blocks.size();i++){
            Block   block = MyMap.blocks.get(i);
            if(block.getBlockType() != 2){
                if( (bossY+bossH) >= block.getBlockY() && bossY <= (block.getBlockY()+block.getBlockH()) && bossX < (block.getBlockX() + block.getBlockW()) && (bossX + bossW) > block.getBlockX()) {
                    setBoss(block);
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
        bossY = blockY-bossH;
        gtime = 0;
        if(direction    ==  Constants.WALK_LEFT){
            bmpBoss=BitmapFactory.decodeResource(res,R.drawable.boss_walk_left_0);
        }else if(direction  ==  Constants.WALK_RIGHT){
            bmpBoss=BitmapFactory.decodeResource(res,R.drawable.boss_walk_right_0);
        }
    }

    private Handler gravityHandler  =   new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(direction    ==  Constants.WALK_LEFT){
                bmpBoss=BitmapFactory.decodeResource(res,R.drawable.boss_dead_left_5);
            }else if(direction  ==  Constants.WALK_RIGHT){
                bmpBoss=BitmapFactory.decodeResource(res,R.drawable.boss_dead_right_5);
            }
            gravityHandler.postDelayed(gravityRunable,100);
        }
    };

    private Runnable gravityRunable   =  new Runnable() {
        @Override
        public void run() {
            Message message=new Message();
            gtime=gtime+0.25;
            bossY=bossY+(int)(0.5*10*gtime*gtime);
            gravityHandler.sendMessage(message);
        }
    };

    public void setBoss(Block block){
        blockX  =   block.getBlockX();
        blockY  =   block.getBlockY();
        blockW  =   block.getBlockW();
        blockH  =   block.getBlockH();
    }

    //判断碰撞（Boss与方块）
    public boolean isCollisionWithBlock(){
        if(direction    ==  Constants.WALK_LEFT){
            for(int i = 0; i< MyMap.blocks.size(); i++){
                Block block = MyMap.blocks.get(i);
                if(block.getBlockType() != 2){
                    if( (bossY+bossH) > block.getBlockY() && bossY < (block.getBlockY()+block.getBlockH()) && bossX < (block.getBlockX() + block.getBlockW()) && (bossX + bossW) > block.getBlockX()) {
                        setBoss(block);
                        return false;
                    }
                }
            }
            return  true;
        }else if(direction  ==  Constants.WALK_RIGHT){
            for(int i = 0; i< MyMap.blocks.size(); i++){
                Block block = MyMap.blocks.get(i);
                if(block.getBlockType() != 2){
                    if( (bossY+bossH) > block.getBlockY() && bossY < (block.getBlockY()+block.getBlockH()) && bossX < (block.getBlockX() + block.getBlockW()) && (bossX + bossW) > block.getBlockX()) {
                        setBoss(block);
                        return false;
                    }
                }
            }
            return  true;
        }
        return false;
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

    public int getbossX() {
        return bossX;
    }

    public void setbossX(int bossX) {
        this.bossX = bossX;
    }

    public int getbossY() {
        return bossY;
    }

    public void setbossY(int bossY) {
        this.bossY = bossY;
    }

    public Bitmap getbmpBoss() {
        return bmpBoss;
    }

    public void setbmpBoss(Bitmap bmpBoss) {
        this.bmpBoss = bmpBoss;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isUp() {
        return isUp;
    }

    public void setUp(boolean up) {
        isUp = up;
    }

    public boolean isDown() {
        return isDown;
    }

    public void setDown(boolean down) {
        isDown = down;
    }

    public boolean isRight() {
        return isRight;
    }

    public void setRight(boolean right) {
        isRight = right;
    }

    public boolean isLeft() {
        return isLeft;
    }

    public void setLeft(boolean left) {
        isLeft = left;
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

    public boolean isCollision() {
        return isCollision;
    }

    public void setCollision(boolean collision) {
        isCollision = collision;
    }

    public static int getDirection() {
        return direction;
    }

    public static void setDirection(int direction) {
        Boss.direction = direction;
    }

    public int getbossW() {
        return bossW;
    }

    public void setbossW(int bossW) {
        this.bossW = bossW;
    }

    public int getbossH() {
        return bossH;
    }

    public void setbossH(int bossH) {
        this.bossH = bossH;
    }

    public int getbossHp() {
        return bossHp;
    }

    public void setbossHp(int bossHp) {
        this.bossHp = bossHp;
    }

    public int getbossAttack() {
        return bossAttack;
    }

    public void setbossAttack(int bossAttack) {
        this.bossAttack = bossAttack;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

}
