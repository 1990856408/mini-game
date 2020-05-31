package com.mini.member.helper;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.google.common.collect.Lists;
import com.mini.member.GameSprite;

import java.util.LinkedList;
import java.util.List;

/**
 * 游戏精灵持有者
 * <p>
 * 为了解决在渲染期间创建、销毁物理世界刚体线程不安全的问题，但本身不维护线程安全
 */
public final class GameSpriteHolder {

    // 创建的游戏精灵所在的世界
    private World world;

    // 创建任务
    private LinkedList<CreateTask> createTasks = new LinkedList<>();

    // 销毁任务
    private LinkedList<DestroyBodyTask> destroyBodyTasks = new LinkedList<>();

    // 每次可创建时的最大创建数，避免创建任务大量堆积时的游戏卡顿问题
    private int maxCreateCount = 2;

    private int maxDestroyBodyCount = 2;

    // 当前已创建的游戏精灵
    private List<GameSprite> gameSprites = Lists.newArrayList();

    public GameSpriteHolder(World world) {
        this.world = world;
    }

    public void pushCreateTask(CreateTask task) {
        createTasks.addLast(task);
    }

    public void pushDestroyBodyTask(DestroyBodyTask task) {
        destroyBodyTasks.addLast(task);
    }

    /**
     * 创建即消费${createTasks}中的task，创建的元素将添加至${gameSprites}
     */
    private void create() {
        if (world.isLocked()) {
            return;
        }

        int createCount = 0;
        while (createCount < maxCreateCount) {
            CreateTask createTask = createTasks.pollFirst();
            if (createTask == null) {
                break;
            }

            GameSprite gameSprite = createTask.getGameSprite();
            gameSprite.create(world, createTask.getInitX(gameSprite), createTask.getInitY(gameSprite));
            CreateAction createAction = createTask.getCreateAction(gameSprite);
            if (createAction != null) {
                createAction.execute(gameSprite);
            }

            gameSprites.add(gameSprite);

            createCount++;
        }
    }

    private void destroyBody() {
        if (world.isLocked()) {
            return;
        }

        int destroyBodyCount = 0;
        while (destroyBodyCount < maxDestroyBodyCount) {
            DestroyBodyTask destroyBodyTask = destroyBodyTasks.pollFirst();
            if (destroyBodyTask == null) {
                break;
            }

            GameSprite gameSprite = destroyBodyTask.getGameSprite();
            gameSprite.destroyBody();
            DestroyBodyAction destroyBodyAction = destroyBodyTask.getDestroyBodyAction();
            if (destroyBodyAction != null) {
                destroyBodyAction.execute(gameSprite);
            }

            destroyBodyCount++;
        }
    }

    /**
     * 建议在该函数末尾调用
     *
     * @param batch 游戏画刷
     * @param delta 游戏时间
     * @see GameSprite#renderCustom(SpriteBatch, float)
     */
    public void render(SpriteBatch batch, float delta) {
        // 创建
        create();

        // 依次渲染各个精灵
        gameSprites.forEach(gameSprite -> gameSprite.render(batch, delta));

        // 销毁刚体
        destroyBody();
    }

    /**
     * 创建任务
     */
    public interface CreateTask {

        GameSprite getGameSprite();

        float getInitX(GameSprite gameSprite);

        float getInitY(GameSprite gameSprite);

        // 回调函数
        default CreateAction getCreateAction(GameSprite gameSprite) {
            return null;
        }
    }

    public interface DestroyBodyTask {

        GameSprite getGameSprite();

        default DestroyBodyAction getDestroyBodyAction() {
            return null;
        }
    }

    /**
     * 创建动作，在创建完成后执行
     */
    public interface CreateAction {
        void execute(GameSprite gameSprite);
    }

    public interface DestroyBodyAction {
        void execute(GameSprite gameSprite);
    }
}
