package com.jkb.junbin.sharing.shadow;

import com.alibaba.android.arouter.facade.Postcard;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.RealObject;

import java.util.HashMap;

@Implements(Postcard.class)
public class ShadowPostCard {

    @RealObject
    public Postcard postcard;

    static HashMap<String, Class> map = new HashMap<>();

    static {
        try {
            map.put("/fileFeature/file", Class.forName("com.jkb.junbin.sharing.feature.file.FileFragment"));
            map.put("/messageFeature/message", Class.forName("com.jkb.junbin.sharing.feature.message.MessageFragment"));
            map.put("/accountFeature/account", Class.forName("com.jkb.junbin.sharing.feature.account.AccountFragment"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Implementation
    public Object navigation() {
        try {
            Class target = map.get(postcard.getPath());
            if (target != null) {
                return target.newInstance();
            }
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
