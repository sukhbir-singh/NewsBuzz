package com.newsbuzz;

/**
 * Created by sukhbir on 2/4/16.
 */
public class ItemStaggered {
    private String title;
    private int image_url;

    public ItemStaggered(){

    }

    public ItemStaggered(String title, int image_url) {
        this.title = title;
        this.image_url = image_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage_url() {
        return image_url;
    }

    public void setImage_url(int image_url) {
        this.image_url = image_url;
    }
}
