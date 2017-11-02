package com.model.message.resp;

/**
 * 音乐消息
 * Created by ZP on 2017/10/20.
 */
public class MusicMessage extends BaseMessage {

    // 音乐   
    private Music Music;

    public Music getMusic() {
        return Music;
    }

    public void setMusic(Music music) {
        Music = music;
    }
}
