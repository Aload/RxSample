package com.autism.rxsample.utils.eventbus;

import org.greenrobot.eventbus.EventBus;

import java.util.Objects;

/**
 * Author：Autism on 2017/3/6 14:23
 * Used:
 */
public class EventBusUtils {
    /**
     * EventBus注册
     *
     * @param mSubscriber
     */
    public static void onRegisterEventBus(Object mSubscriber) {
        boolean registered = EventBus.getDefault().isRegistered(mSubscriber);
        if (!registered) {
            EventBus.getDefault().register(mSubscriber);
        }
    }

    /**
     * EventBus解除绑定
     *
     * @param mSubscriber
     */
    public static void onUnRegisterEnventBus(Object mSubscriber) {
        boolean registered = EventBus.getDefault().isRegistered(mSubscriber);
        if (registered) EventBus.getDefault().unregister(mSubscriber);
    }

    /**
     * 数据传递
     *
     * @param model
     */
    public static void onPostEvent(PostModel model) {
        EventBus.getDefault().post(model);
    }
}
