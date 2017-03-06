package com.autism.rxsample.utils.eventbus;

import java.io.Serializable;

/**
 * Author：Autism on 2017/3/6 14:34
 * Used:
 */
public class PostModel implements Serializable {

    private String eventBusAction;
    private Object eventBusObject;

    public PostModel() {
    }

    public PostModel(String eventBusAction, Object eventBusObject) {
        this.eventBusAction = eventBusAction;
        this.eventBusObject = eventBusObject;
    }

    public String getEventBusAction() {
        return eventBusAction;
    }

    public void setEventBusAction(String eventBusAction) {
        this.eventBusAction = eventBusAction;
    }

    public Object getEventBusObject() {
        return eventBusObject;
    }

    public void setEventBusObject(Object eventBusObject) {
        this.eventBusObject = eventBusObject;
    }

    @Override
    public String toString() {
        return "EventBusModel{" +
                "eventBusAction='" + eventBusAction + '\'' +
                ", eventBusObject=" + eventBusObject +
                '}';
    }
}
