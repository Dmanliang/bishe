package com.example.decml.decmlcraft.base;

public class Constants {

    //人物移动方向
    public static final int MOVE_RIGHT=0;   //向右移动
    public static final int MOVE_LEFT=1;    //向左移动
    public static final int JUMP_UP=2;      //向上跳跃
    public static final int JUMP_DOWN=3;    //向下跳跃
    public static final int STOP=4;         //停止

    //菜单选项
    public static final int GAME_MENU=0;    //游戏菜单
    public static final int GAME_PLAY=1;    //游戏开始
    public static final int GAME_WIN=2;     //游戏胜利
    public static final int GAME_LOST=3;    //游戏失败
    public static final int GAME_PAUSE=4;   //游戏暂停
    public static final int GAME_PACKAGE=5; //游戏背包
    public static final int GAME_BOX=6;     //游戏箱子
    public static final int GAME_TABLE=7;   //游戏工作台
    public static final int GAME_CRUCIBL=8; //游戏熔炉
    public static final int GAME_SETTING=9; //游戏设置
    public static final int GAME_BACK=10;   //游戏返回
    public static final int GAME_EXIT=11;   //游戏退出

    //怪物状态
    public static final int IDLE=0;         //空闲状态
    public static final int WALK_RIGHT=1;   //向右行走
    public static final int WALK_LEFT=2;    //向左行走
    public static final int DEAD_RIGHT=3;   //右边死亡
    public static final int DEAD_LEFT=4;    //左边死亡
    public static final int ATTACK_RIGHT=5; //右边攻击
    public static final int ATTACK_LEFT=6;  //左边攻击

    //子弹方向
    public static final int DIR_LEFT=1;     //左边子弹
    public static final int DIR_RIGHT=2;    //右边子弹
}