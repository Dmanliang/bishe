package com.example.decml.decmlcraft.logic;

/**
 * Created by Decml on 2017/3/9.
 */
//属性类
public class Property {


    private String   name;       //名称
    private int      hp=0;       //生命值
    private int      exp=0;      //经验
    private int      level=0;    //角色等级
    private int      hunger=0;   //饥饿值
    private int      attack=0;   //攻击力
    private int      def=0;      //防御力
    private int      speed=0;    //速度值
    private int      oxygen=0;   //氧气
    private int      lucky=0;    //幸运值


    public Property(String name,int hp,int exp,int level,int hunger,int attack,int def,int speed,int oxygen,int lucky){
        this.name=name;
        this.hp=hp;
        this.exp=exp;
        this.level=level;
        this.hunger=hunger;
        this.attack=attack;
        this.def=def;
        this.speed=speed;
        this.oxygen=oxygen;
        this.lucky=lucky;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getOxygen() {
        return oxygen;
    }

    public void setOxygen(int oxygen) {
        this.oxygen = oxygen;
    }

    public int getLucky() {
        return lucky;
    }

    public void setLucky(int lucky) {
        this.lucky = lucky;
    }


}
