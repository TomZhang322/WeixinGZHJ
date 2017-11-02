package com.model.message.resp;

/**
 * 音乐model
 * Created by ZP on 2017/10/20.
 */
public class Music {

    // 音乐名称   
    private String Title;

    // 音乐描述   
    private String Description;

    // 音乐链接   
    private String MusicUrl;

    // 高质量音乐链接，WIFI环境优先使用该链接播放音乐   
    private String HQMusicUrl;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getMusicUrl() {
        return MusicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        MusicUrl = musicUrl;
    }

    public String getHQMusicUrl() {
        return HQMusicUrl;
    }

    public void setHQMusicUrl(String HQMusicUrl) {
        this.HQMusicUrl = HQMusicUrl;
    }
}