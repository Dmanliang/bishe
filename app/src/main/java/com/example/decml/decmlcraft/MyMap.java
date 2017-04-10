package com.example.decml.decmlcraft;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;


import com.example.decml.decmlcraft.base.Constants;
import com.example.decml.decmlcraft.logic.Back;
import com.example.decml.decmlcraft.logic.Menu;
import com.example.decml.decmlcraft.logic.Monster;
import com.example.decml.decmlcraft.logic.Package;
import com.example.decml.decmlcraft.logic.background.BackGround;
import com.example.decml.decmlcraft.logic.background.BackGroundTwo;
import com.example.decml.decmlcraft.logic.blocks.Block;
import com.example.decml.decmlcraft.logic.blocks.GrassBlock;
import com.example.decml.decmlcraft.logic.blocks.SoilBlock;
import com.example.decml.decmlcraft.logic.KeyBoardClick;
import com.example.decml.decmlcraft.logic.Boss;
import com.example.decml.decmlcraft.logic.Muzzle;
import com.example.decml.decmlcraft.logic.Player;
import com.example.decml.decmlcraft.logic.items.FoodApple;
import com.example.decml.decmlcraft.logic.items.FoodChicken;
import com.example.decml.decmlcraft.logic.items.FoodDrumstick;
import com.example.decml.decmlcraft.logic.items.GuardArmet;
import com.example.decml.decmlcraft.logic.items.GuardArms;
import com.example.decml.decmlcraft.logic.items.GuardCloths;
import com.example.decml.decmlcraft.logic.items.GuardPants;
import com.example.decml.decmlcraft.logic.items.GuardShoes;
import com.example.decml.decmlcraft.logic.items.Item;

import java.util.ArrayList;
import java.util.Vector;

import static com.example.decml.decmlcraft.base.Constants.GAME_WIN;

/**
 * 
 * @author Himi
 *
 */
public class MyMap extends SurfaceView implements Callback, Runnable,View.OnTouchListener {
	private SurfaceHolder sfh;
	private Paint paint;
	private Thread th;
	private boolean flag;
	private Canvas canvas;
	public static int screenW, screenH;
	//声明一个Resoruces实例便于加载图片
	private Resources res=this.getResources();
	//游戏状态
	private int Game_State= Constants.GAME_PLAY;
	//所以物体移动速度
	public static final int speed=5;
	//声明游戏需要的图片资源
	public Bitmap 			bmpBackGround;											//游戏背景
	public Bitmap 			bmpBackGroundTwo;										//游戏背景2
	public Bitmap 			bmpPlayer;												//游戏主角
	public Bitmap 			bmpPlayerHp;											//主角血量
	public Bitmap 			bmpPlayerPt;											//主角护甲
	public Bitmap 			bmpBoss;												//游戏怪物
	public Bitmap 			bmpKeyBoardUp;											//游戏键盘向上键
	public Bitmap 			bmpKeyBoardDown;										//游戏键盘向上键
	public Bitmap 			bmpKeyBoardLeft;										//游戏键盘向上键
	public Bitmap 			bmpKeyBoardRight;										//游戏键盘向上键
	public Bitmap 			bmpKeyBoardUpPress;										//游戏键盘向上键点击
	public Bitmap 			bmpKeyBoardDownPress;									//游戏键盘向上键点击
	public Bitmap 			bmpKeyBoardLeftPress;									//游戏键盘向上键点击
	public Bitmap 			bmpKeyBoardRightPress;									//游戏键盘向上键点击
	public Bitmap 			bmpButtonMenu;											//游戏菜单
	public Bitmap 			bmpButtonActtack;										//游戏攻击键
	public Bitmap 			bmpButtonOk;											//游戏确定键
	public Bitmap 			bmpButtonBack;											//游戏返回键
	public Bitmap 			bmpButtonPackage;										//游戏背包键
	public Bitmap 			bmpButtonJump;											//游戏跳跃键
	public Bitmap 			bmpButtonMenuPress;										//游戏菜单点击
	public Bitmap 			bmpButtonActtackPress;									//游戏攻击键点击
	public Bitmap 			bmpButtonOkPress;										//游戏确定键点击
	public Bitmap 			bmpButtonBackPress;										//游戏返回键点击
	public Bitmap 			bmpButtonPackagePress;									//游戏背包键点击
	public Bitmap 			bmpButtonJumpPress;										//游戏跳跃键点击
	public Bitmap   		bmpMenuStart;											//游戏开始
	public Bitmap 			bmpMenuExit;											//游戏退出
	public Bitmap   		bmpMenuReturn;											//游戏返回
	public Bitmap   		bmpMenuStartPress;										//游戏开始点击
	public Bitmap 			bmpMenuExitPress;										//游戏退出点击
	public Bitmap   		bmpMenuReturnPress;										//游戏返回点击
	public Bitmap 			bmpBoardBlock,bmpBoxBlock,bmpBrickBlock,bmpCrucibleBlock,bmpDiamondBlock,bmpGoldStoneBlock,bmpGrassBlock,bmpIronStoneBlock,bmpLeavesBlock,
							bmpMagmaBlock,bmpRockBlock,bmpSandBlock,bmpSoilBlock;	//各个方块
	public Bitmap			bmpFoodApple,bmpFoodChicken,bmpFoodDrumstick,bmpGuardArms, bmpGuardArmet,
							bmpGuardCloths,bmpGuardPants,bmpGuardShoes;				//各个道具
	public Bitmap 			bmpPackage;												//游戏背包界面
	public Bitmap			bmpMenu;												//游戏菜单界面
	public Bitmap 			bmpBack;												//游戏返回界面
	//声明游戏对象
	public Player 			player;													//游戏主角
	public Boss 			boss;													//游戏怪物
	public BackGround 		backGround;												//游戏背景
	public BackGroundTwo 	backGroundTwo;											//游戏背景
	public Package			mpackage;												//游戏背包
	public Menu				menu;													//游戏菜单
	public Back 			back;													//游戏返回
	public Block 			block;													//游戏方块
	public Item				item;													//游戏道具
	public KeyBoardClick 	keyBoardUp,keyBoardDown,keyBoardLeft,keyBoardRight,buttonMenu,buttonActtack,buttonOk,buttonBack,buttonPackage,buttonJump,buttonMenuStart,buttonMenuExit,buttonReturn;//游戏按钮对象
	public static ArrayList<Block> 	blocks		=	new ArrayList<>();
	public static Vector<Muzzle> 	vcMuzzle	=	new Vector<>();
	public static Vector<Monster> 	vcMonster 	=	new Vector<>();
	public MyMap(Context context) {
		super(context);
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setColor(Color.RED);
		paint.setAntiAlias(true);
		setFocusable(true);
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		screenW = this.getWidth();
		screenH = this.getHeight();
		initGame();
		flag = true;
		th = new Thread(this);
		th.start();
	}

	//初始化游戏对象
	public void initGame()
	{
		bmpBackGround			= 	BitmapFactory.decodeResource(res,R.drawable.background);
		bmpBackGroundTwo		= 	BitmapFactory.decodeResource(res,R.drawable.background2);
		bmpBackGround			=	comp(bmpBackGround,bmpBackGround.getWidth(),screenH);
		bmpBackGroundTwo		=	comp(bmpBackGroundTwo,bmpBackGroundTwo.getWidth(),2*bmpBackGroundTwo.getHeight()/3);
		bmpPackage				=	BitmapFactory.decodeResource(res,R.drawable.quick_backpack);
		bmpMenu 				=	BitmapFactory.decodeResource(res,R.drawable.menu_background);
		bmpBack					=	BitmapFactory.decodeResource(res,R.drawable.menu_background);
		bmpPlayer				=	BitmapFactory.decodeResource(res,R.drawable.character_moving_right_01);
		bmpPlayerHp				=	BitmapFactory.decodeResource(res,R.drawable.hp);
		bmpPlayerPt				=	BitmapFactory.decodeResource(res,R.drawable.defent);
		bmpBoss					=	BitmapFactory.decodeResource(res,R.drawable.boss_walk_right_0);
		bmpKeyBoardUp			=	BitmapFactory.decodeResource(res,R.drawable.keyborad_up);
		bmpKeyBoardDown			=	BitmapFactory.decodeResource(res,R.drawable.keyborad_down);
		bmpKeyBoardLeft			=	BitmapFactory.decodeResource(res,R.drawable.keyborad_left);
		bmpKeyBoardRight		=	BitmapFactory.decodeResource(res,R.drawable.keyborad_right);
		bmpButtonMenu			=	BitmapFactory.decodeResource(res,R.drawable.button_menu);
		bmpButtonActtack		=	BitmapFactory.decodeResource(res,R.drawable.button_attack);
		bmpButtonOk				=	BitmapFactory.decodeResource(res,R.drawable.button_ok);
		bmpButtonBack			=	BitmapFactory.decodeResource(res,R.drawable.button_back);
		bmpButtonPackage		=	BitmapFactory.decodeResource(res,R.drawable.button_package);
		bmpButtonJump			=	BitmapFactory.decodeResource(res,R.drawable.button_jump);
		bmpKeyBoardUpPress		=	BitmapFactory.decodeResource(res,R.drawable.keyborad_up_click);
		bmpKeyBoardDownPress	=	BitmapFactory.decodeResource(res,R.drawable.keyborad_down_click);
		bmpKeyBoardLeftPress	=	BitmapFactory.decodeResource(res,R.drawable.keyborad_left_click);
		bmpKeyBoardRightPress	=	BitmapFactory.decodeResource(res,R.drawable.keyborad_right_click);
		bmpButtonMenuPress		=	BitmapFactory.decodeResource(res,R.drawable.button_menu_click);
		bmpButtonActtackPress	=	BitmapFactory.decodeResource(res,R.drawable.button_attack_click);
		bmpButtonOkPress		=	BitmapFactory.decodeResource(res,R.drawable.button_ok_click);
		bmpButtonBackPress		=	BitmapFactory.decodeResource(res,R.drawable.button_back_click);
		bmpButtonPackagePress	=	BitmapFactory.decodeResource(res,R.drawable.button_package_click);
		bmpButtonJumpPress		=	BitmapFactory.decodeResource(res,R.drawable.button_jump_click);
		bmpMenuStart			=	BitmapFactory.decodeResource(res,R.drawable.menu_start);
		bmpMenuExit				=	BitmapFactory.decodeResource(res,R.drawable.menu_exit);
		bmpMenuReturn			=	BitmapFactory.decodeResource(res,R.drawable.menu_return);
		bmpMenuStartPress		=	BitmapFactory.decodeResource(res,R.drawable.menu_start_clicked);
		bmpMenuExitPress		=	BitmapFactory.decodeResource(res,R.drawable.menu_exit_clicked);
		bmpMenuReturnPress		=	BitmapFactory.decodeResource(res,R.drawable.menu_return_clicked);
		bmpBoardBlock			=	BitmapFactory.decodeResource(res,R.drawable.board_block);
		bmpBoxBlock				=	BitmapFactory.decodeResource(res,R.drawable.box_block);
		bmpBrickBlock			=	BitmapFactory.decodeResource(res,R.drawable.brick_block);
		bmpCrucibleBlock		=	BitmapFactory.decodeResource(res,R.drawable.crucible_block);
		bmpDiamondBlock			=	BitmapFactory.decodeResource(res,R.drawable.diamond__block);
		bmpGoldStoneBlock		=	BitmapFactory.decodeResource(res,R.drawable.gold_stone_block);
		bmpGrassBlock			=	BitmapFactory.decodeResource(res,R.drawable.grass_block);
		bmpIronStoneBlock		=	BitmapFactory.decodeResource(res,R.drawable.iron_stone_block);
		bmpLeavesBlock			=	BitmapFactory.decodeResource(res,R.drawable.leaves_block);
		bmpMagmaBlock			=	BitmapFactory.decodeResource(res,R.drawable.magma_block);
		bmpRockBlock			=	BitmapFactory.decodeResource(res,R.drawable.rock__block);
		bmpSandBlock			=	BitmapFactory.decodeResource(res,R.drawable.sand_block);
		bmpSoilBlock			=	BitmapFactory.decodeResource(res,R.drawable.soil_block);
		bmpFoodApple			=	BitmapFactory.decodeResource(res,R.drawable.food_apple);
		bmpFoodChicken			=	BitmapFactory.decodeResource(res,R.drawable.food_chicken);
		bmpFoodDrumstick		=	BitmapFactory.decodeResource(res,R.drawable.food_drumstick);
		bmpGuardArms			=	BitmapFactory.decodeResource(res,R.drawable.guard_arms);
		bmpGuardArmet			=	BitmapFactory.decodeResource(res,R.drawable.guard_armet);
		bmpGuardCloths			=	BitmapFactory.decodeResource(res,R.drawable.guard_clothes);
		bmpGuardPants			=	BitmapFactory.decodeResource(res,R.drawable.guard_pants);
		bmpGuardShoes			=	BitmapFactory.decodeResource(res,R.drawable.guard_shoes);
		backGround				=	new BackGround(res,bmpBackGround,screenW,screenH);
		backGroundTwo			=	new BackGroundTwo(res,bmpBackGroundTwo,screenW,screenH);
		mpackage				=	new Package(res,bmpPackage,screenW,screenH);
		player					=	new Player(res,bmpPlayer,bmpPlayerHp,bmpPlayerPt,backGround,backGroundTwo,screenW,screenH);
		boss 					=	new Boss(res,bmpBoss,screenW,screenH);
		keyBoardUp				=	new KeyBoardClick(res,bmpKeyBoardUp,bmpKeyBoardUpPress,bmpKeyBoardUp.getWidth()+15,screenH-bmpKeyBoardUp.getHeight()*3+15,screenW,screenH);
		keyBoardDown			=	new KeyBoardClick(res,bmpKeyBoardDown,bmpKeyBoardDownPress,bmpKeyBoardDown.getWidth()+15,screenH-bmpKeyBoardDown.getHeight()-15,screenW,screenH);
		keyBoardLeft			=	new KeyBoardClick(res,bmpKeyBoardLeft,bmpKeyBoardLeftPress,20,screenH-bmpKeyBoardLeft.getHeight()*2,screenW,screenH);
		keyBoardRight			=	new KeyBoardClick(res,bmpKeyBoardRight,bmpKeyBoardRightPress,bmpKeyBoardRight.getWidth()*2+10,screenH-bmpKeyBoardRight.getHeight()*2,screenW,screenH);
		buttonBack				=	new KeyBoardClick(res,bmpButtonBack,bmpButtonBackPress,10,10,screenW,screenH);
		buttonActtack			=	new KeyBoardClick(res,bmpButtonActtack,bmpButtonActtackPress,screenW-bmpButtonActtack.getWidth()-15,screenH-bmpButtonActtack.getHeight()-20,screenW,screenH);
		buttonJump				=	new KeyBoardClick(res,bmpButtonJump,bmpButtonJumpPress,screenW-bmpButtonJump.getWidth()-15,screenH-bmpButtonJump.getHeight()*2-30,screenW,screenH);
		buttonOk				=	new KeyBoardClick(res,bmpButtonOk,bmpButtonOkPress,screenW-bmpButtonOk.getWidth()*2-25,screenH-bmpButtonOk.getHeight()-20,screenW,screenH);
		buttonPackage			=	new KeyBoardClick(res,bmpButtonPackage,bmpButtonPackagePress,screenW/3,10,screenW,screenH);
		buttonMenu				=	new KeyBoardClick(res,bmpButtonMenu,bmpButtonMenuPress,2*screenW/3-bmpButtonMenu.getWidth(),10,screenW,screenH);
		buttonMenuStart 		=	new KeyBoardClick(res,bmpMenuStart,bmpMenuStartPress,screenW/2-bmpMenuStart.getWidth()/2,screenH/5,screenW,screenH);
		buttonMenuExit			=	new KeyBoardClick(res,bmpMenuExit,bmpMenuExitPress,screenW/2-bmpMenuExit.getWidth()/2,screenH/5+bmpButtonMenu.getHeight()+10,screenW,screenH);
		buttonReturn			=	new KeyBoardClick(res,bmpMenuReturn,bmpMenuReturnPress,screenW/2-bmpMenuReturn.getWidth()/2,screenH/5+bmpButtonMenu.getHeight()*2+10,screenW,screenH);
		menu					=	new Menu(res,bmpMenu,buttonMenuStart,buttonMenuExit,buttonReturn,screenW,screenH);
		createBlocks();
		createItems();
	}

	public void createItems(){
		for(int i=0;i<10;i++){
			item = new FoodChicken(res,bmpFoodChicken,0,0,0,screenW,screenH);
			mpackage.addItems(item);
		}
		for(int i=0;i<20;i++){
			item = new FoodApple(res,bmpFoodApple,1,0,0,screenW,screenH);
			mpackage.addItems(item);
		}
		for(int i=0;i<60;i++){
			item = new FoodDrumstick(res,bmpFoodDrumstick,2,0,0,screenW,screenH);
			mpackage.addItems(item);
		}
		for(int i=0;i<1;i++){
			item = new GuardArms(res,bmpGuardArms,3,0,0,screenW,screenH);
			mpackage.addItems(item);
		}
		for(int i=0;i<3;i++){
			item = new GuardArmet(res,bmpGuardArmet,4,0,0,screenW,screenH);
			mpackage.addItems(item);
		}
		for(int i=0;i<1;i++){
			item = new GuardCloths(res,bmpGuardCloths,5,0,0,screenW,screenH);
			mpackage.addItems(item);
		}
		for(int i=0;i<2;i++){
			item = new GuardPants(res,bmpGuardPants,6,0,0,screenW,screenH);
			mpackage.addItems(item);
		}
		for(int i=0;i<3;i++){
			item = new GuardShoes(res,bmpGuardShoes,7,0,0,screenW,screenH);
			mpackage.addItems(item);
		}
	}

	//方块创建
	public void createBlocks()
	{
		for(int i=0;i<3;i++)
		{
			block	=	new SoilBlock(res,bmpSoilBlock,screenW/2+i*bmpBoardBlock.getWidth(),screenH/2,1);
			blocks.add(block);
		}
		for(int i=0;i<50;i++)
		{
			block	=	new SoilBlock(res,bmpSoilBlock,300+i*bmpBoardBlock.getWidth(),screenH-bmpBoardBlock.getHeight(),1);
			blocks.add(block);
		}
		for(int i=0;i<50;i++)
		{
			block	=	new GrassBlock(res,bmpGrassBlock,300+i*bmpBoardBlock.getWidth(),screenH-bmpBoardBlock.getHeight()*2,2);
			blocks.add(block);
		}

	}


/*	//怪物创建
	public void createBoss(){
		//敌机逻辑
		for(int i = 0; i< vcMonster.size(); i++)
		{
			Monster m= vcMonster.elementAt(i);
			//因为容器不断添加敌机,那么对敌机isDead判定,如果已死亡那么就从容器中删除,对容器起到优化的作用
			if(m.isDead)
			{
				vcMonster.removeElementAt(i);
			}
			else
			{
				m.logic();
			}
		}
	}


	//怪物与主角子弹碰撞逻辑
	public void isCollsionMonsterWithMuzzle(){
		Muzzle 		mz;
		Monster 	ms;
		for(int i=0;i<vcMuzzle.size();i++)
		{
			mz=vcMuzzle.elementAt(i);
			for(int j = 0; j< vcMonster.size(); j++){
				ms= vcMonster.elementAt(j);
				if(ms.isCollisonWithPlayer(mz))
				{
					if(ms.getMonsterHp()<=0)
					{
						//怪物死亡
						ms.setDead(true);
					}
					else
					{
						//及时删除本次碰撞的子弹,防止重复判定此子弹与Boss碰撞
						mz.setDead(true);
						//怪物血量-1
						ms.setMonsterHp(ms.getMonsterHp()-player.getPlayerAttack());
					}
				}
			}

		}
	}

	//怪物绘制
	public void MonsterDraw(){
		for(int i = 0; i< vcMonster.size(); i++){
			vcMonster.elementAt(i).draw(canvas,paint);
		}
	}

	//主角与怪物碰撞
	public void PlayerCollisionWithBoss(){
		//处理主角与怪物碰撞
		Monster monster;
		for(int i = 0; i< vcMonster.size(); i++)
		{
			monster  =	vcMonster.elementAt(i);
			if(player.isCollsionWithBoss(boss))
			{
				//发生碰撞，主角血量-1
				if(player.getPlayerPt()>0){
					player.setPlayerHp(player.getPlayerHp()- boss.getbossAttack()+1);
					player.setPlayerPt(player.getPlayerPt()-1);
				}else{
					player.setPlayerHp(player.getPlayerHp()- boss.getbossAttack());
				}

				//当主角血量小于0,判断游戏失败
				if(player.getPlayerHp()<= 0)
				{
					Game_State=Constants.GAME_LOST;
				}
			}
		}
	}*/

	//Boss碰撞逻辑
	public void isCollsionBossWithMuzzle(){
		//Boss被主角子弹击中
		for(int i=0;i<vcMuzzle.size();i++)
		{
			Muzzle m= vcMuzzle.elementAt(i);
			if(boss.isCollisonWithPlayer(m))
			{
				if(boss.getbossHp()<=0)
				{
					//游戏胜利
					Game_State=GAME_WIN;
				}
				else
				{
					//及时删除本次碰撞的子弹,防止重复判定此子弹与Boss碰撞
					m.setDead(true);
					//Boss血量-1
					boss.setbossHp(boss.getbossHp()-1);
				}
			}
		}
	}


	//主角子弹绘制
	public void PlayerMuzzleDraw(){
		for(int i=0;i<vcMuzzle.size();i++){
			vcMuzzle.elementAt(i).draw(canvas,paint);
		}
	}

	//主角子弹逻辑
	public void PlayerMuzzleLogic(){
		//处理主角子弹逻辑
		for(int i=0;i<vcMuzzle.size();i++)
		{
			Muzzle m=vcMuzzle.elementAt(i);
			if(m.isDead())
			{
				m.StopMuzzleChange();
				vcMuzzle.removeElement(m);
			}
			else
			{
				m.logic();
			}
		}
	}



	//主绘制函数
	public void myDraw() {
		try {
			canvas = sfh.lockCanvas();
			if (canvas != null) {
				//游戏绘制函数根据游戏状态不同进行不同的绘制
				switch (Game_State)
				{
					case Constants.GAME_MENU:
						break;
					case Constants.GAME_PLAY:
						canvas.drawColor(Color.WHITE);
						backGround.draw(canvas,paint);
						backGroundTwo.draw(canvas,paint);
						mpackage.draw(canvas,paint);
						menu.draw(canvas,paint);
						for(int i=0;i<blocks.size();i++)
						{
							blocks.get(i).draw(canvas,paint);
						}
						player.draw(canvas,paint);
						PlayerMuzzleDraw();
						boss.draw(canvas,paint);
						keyBoardUp.draw(canvas,paint);
						keyBoardDown.draw(canvas,paint);
						keyBoardLeft.draw(canvas,paint);
						keyBoardRight.draw(canvas,paint);
						buttonMenu.draw(canvas,paint);
						buttonBack.draw(canvas,paint);
						buttonOk.draw(canvas,paint);
						buttonActtack.draw(canvas,paint);
						buttonJump.draw(canvas,paint);
						buttonPackage.draw(canvas,paint);
						break;
					case GAME_WIN:
						break;
					case Constants.GAME_LOST:
						break;
					case Constants.GAME_PAUSE:
						break;
					case Constants.GAME_SETTING:
						break;
					case Constants.GAME_BACK:
						break;
					case Constants.GAME_EXIT:
						break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);
		}
	}

	//主触屏监听函数
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (Game_State)
		{
			case Constants.GAME_MENU:
				break;
			case Constants.GAME_PLAY:
				keyBoardUp.onTouchEvent(event);
				keyBoardDown.onTouchEvent(event);
				keyBoardLeft.onTouchEvent(event);
				keyBoardRight.onTouchEvent(event);
				buttonMenu.onTouchEvent(event);
				buttonActtack.onTouchEvent(event);
				buttonOk.onTouchEvent(event);
				buttonBack.onTouchEvent(event);
				buttonPackage.onTouchEvent(event);
				buttonJump.onTouchEvent(event);
				mpackage.onTouchEvent(event);
				menu.onTouchEvent(event);
				player.onTouchEvent(event,bmpKeyBoardUp,bmpKeyBoardDown,bmpKeyBoardLeft,bmpKeyBoardRight,bmpButtonMenu,bmpButtonActtack,bmpButtonOk,bmpButtonBack,bmpButtonPackage,
						bmpButtonJump,keyBoardUp,keyBoardDown,keyBoardLeft,keyBoardRight,buttonMenu,buttonActtack,buttonOk,buttonBack,buttonPackage,buttonJump,mpackage,menu);
				/*for(int i=0;i<blocks.size();i++)
				{
					blocks.get(i).onTouchEvent(event);
				}*/
				break;
			case GAME_WIN:
				break;
			case Constants.GAME_LOST:
				break;
			case Constants.GAME_PAUSE:
				break;
			case Constants.GAME_PACKAGE:
				break;
			case Constants.GAME_BOX:
				break;
			case Constants.GAME_TABLE:
				break;
			case Constants.GAME_CRUCIBL:
				break;
			case Constants.GAME_SETTING:
				break;
			case Constants.GAME_BACK:
				break;
			case Constants.GAME_EXIT:
				break;
		}
		return true;
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}


	//主逻辑函数
	private void logic() {
		switch (Game_State)
		{
			case Constants.GAME_MENU:
				break;
			case Constants.GAME_PLAY:
				player.logic();
				boss.logic();
				PlayerMuzzleLogic();
				isCollsionBossWithMuzzle();
				mpackage.logic();
				menu.logic();
				break;
			case GAME_WIN:
				break;
			case Constants.GAME_LOST:
				break;
			case Constants.GAME_PAUSE:
				break;
			case Constants.GAME_PACKAGE:
				break;
			case Constants.GAME_BOX:
				break;
			case Constants.GAME_TABLE:
				break;
			case Constants.GAME_CRUCIBL:
				break;
			case Constants.GAME_SETTING:
				break;
			case Constants.GAME_BACK:
				break;
			case Constants.GAME_EXIT:
				break;
		}
	}

	//主线程函数
	@Override
	public void run() {
		while (flag) {
			long start = System.currentTimeMillis();
			myDraw();
			logic();
			long end = System.currentTimeMillis();
			try {
				if (end - start < 50) {
					Thread.sleep(50 - (end - start));
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


	//图片按比例缩放
	public static Bitmap comp(Bitmap image,int sW,int sH)
	{
		int w=image.getWidth();
		int h=image.getHeight();
		float sx=(float)sW/w;
		float sy=(float)sH/h;
		Matrix matrix=new Matrix();
		matrix.postScale(sx,sy);
		Bitmap resizeBmp=Bitmap.createBitmap(image,0,0,w,h,matrix,true);
		return resizeBmp;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		flag = false;
		holder.getSurface().release();
	}

	@Override
	public boolean onTouch(View view, MotionEvent motionEvent) {
		return false;
	}
}
