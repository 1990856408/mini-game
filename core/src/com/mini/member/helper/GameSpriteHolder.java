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
 * 为了解决在渲染期间创建物理世界刚体线程不安全的问题
 */
public final class GameSpriteHolder {

    // 创建的游戏精灵所在的世界
    private World world;

    // 创建任务
    private LinkedList<CreateTask> createTasks = new LinkedList<>();

    // 每次可创建时的最大创建数，避免创建任务大量堆积时的游戏卡顿问题
    private int maxCreateCount = 2;

    // 当前已创建的游戏精灵
    private List<GameSprite> gameSprites = Lists.newArrayList();

    public GameSpriteHolder(World world) {
        this.world = world;
    }

    public synchronized void pushCreateTask(CreateTask createTask) {
        createTasks.addLast(createTask);
    }

    private synchronized void create() {
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
     * 建议在该函数末尾调用
     *
     * @param batch 游戏画刷
     * @param delta 游戏时间
     * @see GameSprite#renderCustom(SpriteBatch, float)
     */
    public void render(SpriteBatch batch, float delta) {
        create();
        gameSprites.forEach(gameSprite -> gameSprite.render(batch, delta));
    }

    /**
     * 创建任务
     */
    public interface CreateTask {

        GameSprite getGameSprite();

        float getInitX();

        float getInitY();

        default CreateAction getCreateAction() {
            return null;
        }
    }

    /**
     * 创建动作，在创建完成后执行
     */
    public interface CreateAction {
        void execute(GameSprite gameSprite);
    }
}
