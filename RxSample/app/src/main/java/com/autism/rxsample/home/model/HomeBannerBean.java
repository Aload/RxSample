package com.autism.rxsample.home.model;

/**
 * Author：Autism on 2017/3/2 15:54
 * Used:
 */
public class HomeBannerBean {

    /**
     * entity_id : 1
     * image_url : 图片链接
     * type : 2
     * target_url : 跳转的url或ID
     */

    private int entity_id;
    private String image_url;
    private int type;
    private String target_url;

    public int getEntity_id() {
        return entity_id;
    }

    public void setEntity_id(int entity_id) {
        this.entity_id = entity_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTarget_url() {
        return target_url;
    }

    public void setTarget_url(String target_url) {
        this.target_url = target_url;
    }
}
