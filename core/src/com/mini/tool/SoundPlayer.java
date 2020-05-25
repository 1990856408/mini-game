package com.mini.tool;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.mini.game.MiniGame;

/**
 * 音效播放器
 */
public final class SoundPlayer {

    private Music music;

    private Sound sound;

    // 播放音乐
    public void playMusic(String path) {
        music = MiniGame.assetManager.get(path);
        music.setLooping(true);
        music.setVolume(0.4f);
        music.play();
    }

    // 停止播放音乐
    public void stopMusic() {
        if (music != null) {
            music.stop();
        }
    }

    // 播放音效（短暂的）
    public void playSound(String path) {
        sound = MiniGame.assetManager.get(path, Sound.class);
        sound.play(0.04f);
    }

    public void dispose() {
        if (music != null)
            music.dispose();
        if (sound != null)
            sound.dispose();
    }
}