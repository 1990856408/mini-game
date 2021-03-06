package com.mini.member.helper;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.google.common.collect.Lists;
import com.mini.member.GameSprite;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * 游戏精灵持有者
 * <p>
 * 为了解决在渲染期间创建、销毁物理世界刚体线程不安全的问题，但本身不维护线程安全
 */
public class GameSpriteHolder {

    // 创建的游戏精灵所在的世界
    private World world;

    // 创建任务
    private LinkedList<CreateTask> createTasks = new LinkedList<>();

    // 销毁任务，可销毁其他世界中的刚体
    private LinkedList<DestroyBodyTask> destroyBodyTasks = new LinkedList<>();

    // 每次可创建时的最大创建数，避免创建任务大量堆积时的游戏卡顿问题
    private int maxCreateCount = 15;

    // 每次可销毁时的最大销毁数
    private int maxDestroyBodyCount = maxCreateCount * 3;

    // 当前已创建的游戏精灵
    private List<GameSprite> gameSprites = Lists.newArrayList();

    // 待移除的游戏精灵
    private List<GameSprite> gameSpritesRemoved = Lists.newArrayList();

    // 更新执行器，在持有的游戏精灵渲染前执行，可以在此处维护宿主与持有的游戏精灵之间的关系
    private GameSpriteHolderExecutor updateExecutor;

    public GameSpriteHolder(World world) {
        this.world = world;
    }

    /**
     * 移除精灵，由于此接口对外开放，故需要维护线程安全，
     * 保证在清理${gameSprites}过程中没有新的待移除精灵添加进来
     *
     * @param gameSprite
     */
    public synchronized void removeGameSprite(GameSprite gameSprite) {
        gameSpritesRemoved.add(gameSprite);
    }

    public void pushCreateTask(Collection<CreateTask> tasks) {
        tasks.forEach(this::pushCreateTask);
    }

    /**
     * 添加一个创建任务
     *
     * @param task
     */
    public void pushCreateTask(CreateTask task) {
        createTasks.addLast(task);
    }

    public void pushDestroyBodyTask(Collection<DestroyBodyTask> tasks) {
        tasks.forEach(this::pushDestroyBodyTask);
    }

    /**
     * 添加一个销毁任务
     *
     * @param task
     */
    public void pushDestroyBodyTask(DestroyBodyTask task) {
        destroyBodyTasks.addLast(task);
    }

    /**
     * 移除操作，移除待移除的游戏精灵
     *
     * @see GameSpriteHolder#removeGameSprite(GameSprite)
     */
    private synchronized void remove() {
        gameSprites.removeAll(gameSpritesRemoved);
        gameSpritesRemoved.clear();
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
            gameSprite.create(world, createTask.getInitX(), createTask.getInitY());
            CreateAction createAction = createTask.getCreateAction();
            if (createAction != null) {
                createAction.execute(gameSprite);
            }

            gameSprites.add(gameSprite);

            createCount++;
        }
    }

    /**
     * 销毁刚体
     */
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
        // 移除
        remove();
        // 创建
        create();

        // 依次渲染各个精灵
        gameSprites.forEach(gameSprite -> {
            if (updateExecutor != null) {
                updateExecutor.execute(gameSprite);
            }
            gameSprite.render(batch, delta);
        });

        // 销毁刚体
        destroyBody();
    }

    public void setMaxCreateCount(int maxCreateCount) {
        this.maxCreateCount = maxCreateCount;
    }

    public void setMaxDestroyBodyCount(int maxDestroyBodyCount) {
        this.maxDestroyBodyCount = maxDestroyBodyCount;
    }

    /**
     * 配置更新执行器
     *
     * @param updateExecutor
     */
    public void setUpdateExecutor(GameSpriteHolderExecutor updateExecutor) {
        this.updateExecutor = updateExecutor;
    }


    /**
     * 创建任务
     */
    public interface CreateTask {

        GameSprite getGameSprite();

        float getInitX();

        float getInitY();

        // 回调函数
        default CreateAction getCreateAction() {
            return null;
        }
    }

    /**
     * 销毁任务
     */
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

    /**
     * 销毁任务，在销毁完成后执行
     */
    public interface DestroyBodyAction {
        void execute(GameSprite gameSprite);
    }
}
