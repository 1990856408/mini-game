package com.custom.screen;

import com.badlogic.gdx.physics.box2d.*;
import com.mini.member.MiniUserData;

import java.util.ArrayList;
import java.util.Arrays;

public class NormalScreenContactListener implements ContactListener {
//    //两个力，用来使主角受到伤害时产生一个效果
//    private static final float x_force = 42.0f;
//    private static final float y_force = NormalScreen.WORLD_GRAVITY * 21.0f;
//    private boolean onFoot = false;//主角是否有踩踏物
//    private ContackValues conValues;//为了处理多重碰撞特意做的
//    private Vector<Body> removeBodies;//指定发生碰撞的刚体集合
//    private Vector<Body> bulletHarmBodys;    //受到主角子弹伤害的集合
//    private Vector<Body> chunliHarmBodys;    //受到春丽伤害的集合
//    private Vector<Body> ballHarmBodys;    //受到春丽气功掌的伤害的集合
//    private NormalScreen screen;
//
//    public NormalScreenContactListener(NormalScreen screen) {
//        super();
//        this.screen = screen;
//        removeBodies = new Vector<Body>();
//        bulletHarmBodys = new Vector<Body>();
//        chunliHarmBodys = new Vector<Body>();
//        ballHarmBodys = new Vector<Body>();
//        conValues = new ContackValues();
//    }

    @Override
    public void beginContact(Contact contact) {
//        Fixture fixtureA = contact.getFixtureA();
//        Fixture fixtureB = contact.getFixtureB();
//
//        if (fixtureA.getUserData() != null && fixtureB.getUserData() != null) {
//            MiniUserData dataA = (MiniUserData) fixtureA.getUserData();
//            MiniUserData dataB = (MiniUserData) fixtureB.getUserData();
//            //判断是否着地
//            this.judgeOnFoot(dataA, dataB);
//            //判断主角与鸭子的碰撞
//            this.judgeDuck(dataA, dataB);
//            //与火焰的碰撞
//            this.judgeProtaContactHarmA(dataA, dataB, ConstantNames.FLAME);
//            //与恐龙的碰撞
//            this.judgeProtaContactHarmA(dataA, dataB, ConstantNames.DINO);
//            //与刺猬的碰撞
//            this.judgeProtaContactHarmA(dataA, dataB, ConstantNames.HEDGE);
//            //判断与主角子弹的碰撞
//            this.judgeBullet(dataA, dataB);
//            //春丽伤害过于强大
//            this.judgeChunLi(dataA, dataB);
//            //与春丽气功掌的碰撞
//            this.judgeBall(dataA, dataB);
//            //判断触碰到蛋
//            this.judgeEgg(dataA, dataB);
//            //与机器球的子弹的碰撞
//            this.judgeRobotBall(dataA, dataB);
//        }
    }

//    //处理那些与主角碰撞后会对主角造成伤害且自己不会消失的敌人
//    private void judgeProtaContactHarmA(MiniUserData dataA, MiniUserData dataB, String harmName) {
//        if (!dataA.name.equals(ConstantNames.MARIO) && !dataB.name.equals(ConstantNames.MARIO))
//            return;
//        if (dataA.name.equals(harmName) && dataB.name.equals(ConstantNames.MARIO)) {
//            this.protaSufferHarm(harmName);
//        }
//        if (dataA.name.equals(ConstantNames.MARIO) && dataB.name.equals(harmName)) {
//            this.protaSufferHarm(harmName);
//        }
//    }
//
//    private void judgeRobotBall(MiniUserData dataA, MiniUserData dataB) {
//        if (dataA.name.equals(ConstantNames.ROBOTBALL) && dataB.name.equals(ConstantNames.MARIO)) {
//            this.removeBodies.add(dataA.body);
//            this.protaSufferHarm(ConstantNames.ROBOTBALL);
//        }
//        if (dataB.name.equals(ConstantNames.ROBOTBALL) && dataA.name.equals(ConstantNames.MARIO)) {
//            this.removeBodies.add(dataB.body);
//            this.protaSufferHarm(ConstantNames.ROBOTBALL);
//        }
//    }
//
//    private void judgeBall(MiniUserData dataA, MiniUserData dataB) {
//        if (!dataA.name.equals(ConstantNames.BALL) && !dataB.name.equals(ConstantNames.BALL))
//            return;
//        if (conValues.ball.contains(dataA.name))
//            ballHarmBodys.add(dataA.body);
//        if (conValues.ball.contains(dataB.name))
//            ballHarmBodys.add(dataB.body);
//    }
//
//    /*
//     * 与地面接触、离开函数
//     */
//    public void judgeOnFoot(MiniUserData dataA, MiniUserData dataB) {
//        if (!dataA.name.equals(ConstantNames.FOOT) && !dataB.name.equals(ConstantNames.FOOT))
//            return;
//
//        if (conValues.foot.contains(dataA.name) || conValues.foot.contains(dataB.name)) {
//            onFoot = true;
//        }
//    }
//
//    /*
//     * 与鸭子接触，鸭子就会死亡，将鸭子的刚体添加到移除刚体集合中，并使主角的当前跳跃次数
//     * 达到最大，这样就会使鸭子充当一个跳板的作用
//     */
//    public void judgeDuck(MiniUserData dataA, MiniUserData dataB) {
//        if (!dataA.name.equals(ConstantNames.DUCK) && !dataB.name.equals(ConstantNames.DUCK))
//            return;
//        if (conValues.duck.contains(dataB.name)) {
//            removeBodies.add(dataA.body);
//            screen.prota.setJumpCurCount(NormalScreen.jump_max_count);
//        }
//        if (conValues.duck.contains(dataA.name)) {
//            removeBodies.add(dataB.body);
//            screen.prota.setJumpCurCount(NormalScreen.jump_max_count);
//        }
//    }
//
//    public void judgeBullet(MiniUserData dataA, MiniUserData dataB) {
//        if (dataA.name.equals(ConstantNames.BULLET)) {
//            removeBodies.add(dataA.body);
//            if (conValues.bullet.contains(dataB.name)) {    //再将恐龙的刚体加进伤害刚体集合中
//                bulletHarmBodys.add(dataB.body);
//            }
//        }
//        if (dataB.name.equals(ConstantNames.BULLET)) {
//            removeBodies.add(dataB.body);
//            if (conValues.bullet.contains(dataA.name)) {
//                bulletHarmBodys.add(dataA.body);
//            }
//        }
//    }
//
//    private void judgeChunLi(MiniUserData dataA, MiniUserData dataB) {
//        if (!dataA.name.equals(ConstantNames.CHUNLI) && !dataB.name.equals(ConstantNames.CHUNLI))
//            return;
//        if (conValues.chunli.contains(dataA.name))
//            chunliHarmBodys.add(dataA.body);
//        if (conValues.chunli.contains(dataB.name))
//            chunliHarmBodys.add(dataB.body);
//    }
//
//    private void judgeEgg(MiniUserData dataA, MiniUserData dataB) {
//        if (!dataA.name.equals(ConstantNames.EGG) && !dataB.name.equals(ConstantNames.EGG))
//            return;
//
//        if (conValues.egg.contains(dataB.name)) {
//            removeBodies.add(dataA.body);
//            NormalScreen.soundPlayer.playSound(EnumSound.SCORE);
//        }
//        if (conValues.egg.contains(dataA.name)) {
//            removeBodies.add(dataB.body);
//            NormalScreen.soundPlayer.playSound(EnumSound.SCORE);
//        }
//    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getUserData() != null && fixtureB.getUserData() != null) {
            MiniUserData dataA = (MiniUserData) fixtureA.getUserData();
            MiniUserData dataB = (MiniUserData) fixtureB.getUserData();
//            this.endOnFoot(dataA, dataB);
        }

    }

//    public void endOnFoot(MiniUserData dataA, MiniUserData dataB) {
//        if ((dataA.name.equals("mario") || dataA.name.equals("chunli")) && dataB.name.equals("foot")) {
//            if (onFoot)
//                onFoot = false;
//        }
//        if (dataA.name.equals("foot") && (dataB.name.equals("mario") || dataB.name.equals("chunli"))) {
//            if (onFoot)
//                onFoot = false;
//        }
//    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    //用来实现主角受到不同伤害时血量的变化
    public void protaSufferHarm(String type) {
//        Protagonist prota = screen.prota;
//        switch (type) {
//            case ConstantNames.FLAME:
//                prota.currentHP -= ConstantHarms.FLAME_HARM;
//                break;
//            case ConstantNames.DINO:
//                prota.currentHP -= ConstantHarms.DION_HARM;
//                break;
//            case ConstantNames.ROBOTBALL:
//                prota.currentHP -= ConstantHarms.ROBOTBALL_HARM;
//                prota.currentMP -= ConstantHarms.ROBOTBALL_HARM;
//                break;
//            case ConstantNames.HEDGE:
//                prota.currentHP -= ConstantHarms.HEDGE_HAEM;
//                break;
//        }
//        //产生受到伤害时力的效果
//        Body bo = prota.getBody();
//        bo.setLinearVelocity(0, 0);
//        switch (prota.direction) {
//            case 'D':
//                bo.applyForceToCenter(-x_force, y_force, true);
//                break;
//            case 'A':
//                bo.applyForceToCenter(x_force, y_force, true);
//                break;
//        }
//        NormalScreen.soundPlayer.playSound(EnumSound.SUFFER_HARM);
    }

    //用来移除物理世界中的刚体的夹具
    public void update() {
        //销毁所有刚体的夹具
        /*
         * 这里是如果移除夹具以后，再根据是否有夹具来判断其是否死亡
         */
//        for (int i = 0; i < removeBodies.size(); i++) {
//            Body bo = removeBodies.get(i);
//            if (bo.getFixtureList().size > 0) {
//                bo.destroyFixture(bo.getFixtureList().first());
//                if (((MiniUserData) (bo.getUserData())).name.equals("duck"))
//                    screen.curScore += ConstantValue.DUCK;
//                if (((MiniUserData) (bo.getUserData())).name.equals("egg"))
//                    screen.curScore += ConstantValue.EGG;
//            }
//        }
//        removeBodies.clear();
    }
}

class ContackValues {
    public ArrayList<String> foot;
    public ArrayList<String> duck;
    public ArrayList<String> egg;
    public ArrayList<String> bullet;
    public ArrayList<String> ball;
    public ArrayList<String> chunli;

    public ContackValues() {
        this.init();
    }

    private void init() {
//        this.foot = getNeededList(ConstantNames.MARIO, ConstantNames.CHUNLI);
//        this.duck = getNeededList(ConstantNames.MARIO, ConstantNames.CHUNLI);
//        this.egg = getNeededList(ConstantNames.MARIO, ConstantNames.CHUNLI);
//        this.bullet = getNeededList(ConstantNames.DINO, ConstantNames.ROBOT, ConstantNames.HEDGE);
//        this.ball = getNeededList(ConstantNames.DINO, ConstantNames.ROBOT, ConstantNames.HEDGE);
//        this.chunli = getNeededList(ConstantNames.DINO, ConstantNames.ROBOT, ConstantNames.HEDGE);
    }

    public ArrayList<String> getNeededList(String... strings) {
        return new ArrayList<String>(Arrays.asList(strings));
    }
}
