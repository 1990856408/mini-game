package com.mini.tool;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.mini.constant.EnumSound;
import com.mini.game.MiniGame;

/**
 * 音效播放器
 */
public class SoundPlayer {

    private Music music;

    private Sound sound;

    // 播放音乐
    public void playMusic(String path) {
        if (music != null) {
            music.dispose();
        }

        music = MiniGame.assetManager.get(path);
        music.setLooping(true);
        music.setVolume(0.2f);
        music.play();
    }

    // 停止播放音乐
    public void stopMusic() {
        if (music != null) {
            music.stop();
        }
    }

    // 播放音效（短暂的）
    public void playSound(EnumSound enumSound) {
        if (sound != null) {
            sound.dispose();
        }

        sound = MiniGame.assetManager.get(EnumSound.path(enumSound), Sound.class);
        sound.play(0.04f);
    }

    public void dispose() {
        if (music != null)
            music.dispose();
        if (sound != null)
            sound.dispose();
    }
}
