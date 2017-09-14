package com.eyck.fxreading.model.entity;

import java.util.List;

/**
 * Created by Eyck on 2017/9/2.
 */

public class LauncherEntity {
    private String status;
    private int time;
    private int count;
    private List<String> images;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
