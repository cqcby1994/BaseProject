package com.jack.project.root.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;


import com.jack.project.root.AppApplication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;


/**
 * SharedPreferences工具类
 *
 * @author hch
 */
public class SharedPreferencesUtils {

    private static final String TAG = SharedPreferencesUtils.class.getSimpleName();
    Context context;
    String name;

    public SharedPreferencesUtils(Context context, String name) {
        this.context = context;
        this.name = name;
    }
    public SharedPreferencesUtils(Context context) {
        this.context = context;
        this.name = AppApplication.USER_UP_BEAN_KEY;
    }
    /**
     * 根据key和预期的value类型获取value的值
     *
     * @param key   键
     * @param clazz class
     * @return 键对应的值
     */
    public <T> T getValue(String key, Class<T> clazz) {
        if (context == null) {
            throw new RuntimeException("请先调用带有context，name参数的构造！");
        }
        SharedPreferences sp = this.context.getSharedPreferences(this.name, Context.MODE_PRIVATE);
        return getValue(key, clazz, sp);
    }

    public Set<String> getStringSet(String key) {
        if (context == null) {
            throw new RuntimeException("请先调用带有context，name参数的构造！");
        }
        SharedPreferences sp = this.context.getSharedPreferences(this.name, Context.MODE_PRIVATE);
        return new HashSet<>(sp.getStringSet(key, new HashSet<String>()));
    }

    /**
     * 对于外部不可见的过渡方法
     *
     * @param key   键
     * @param clazz class
     * @param sp    SharedPreferences
     * @return 键对应的值
     */
    @SuppressWarnings("unchecked")
    private <T> T getValue(String key, Class<T> clazz, SharedPreferences sp) {

        if (clazz.isAssignableFrom(Integer.class)) {
            return (T) Integer.valueOf(sp.getInt(key, 0));
        } else if (clazz.isAssignableFrom(String.class)) {
            return (T) sp.getString(key, null);
        } else if (clazz.isAssignableFrom(Boolean.class)) {
            return (T) Boolean.valueOf(sp.getBoolean(key, false));
        } else if (clazz.isAssignableFrom(Long.class)) {
            return (T) Long.valueOf(sp.getLong(key, 0L));
        } else if (clazz.isAssignableFrom(Float.class)) {
            return (T) Float.valueOf(sp.getFloat(key, 0L));
        } else if (clazz.isAssignableFrom(String[].class)) {
            return (T) sp.getStringSet(key,new LinkedHashSet<String>()).toArray();
        }
        return null;
    }

    /**
     * 针对复杂类型存储<对象>
     *
     * @param key    键
     * @param object 值
     * @return 是否提交成功
     */
    public boolean setObject(String key, Object object) {
        boolean isSuccess = false;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(baos);
            out.writeObject(object);
            String objectVal = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            isSuccess = setValue(key, objectVal);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                baos.close();
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return isSuccess;
    }

    public boolean setStringSet(String key, Set<String> value) {
        SharedPreferences sp = this.context.getSharedPreferences(this.name, Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putStringSet(key, value);
        return editor.commit();
    }

    public <T> boolean setValue(String key, T value) {
        SharedPreferences sp = this.context.getSharedPreferences(this.name, Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        if (value.getClass().isAssignableFrom(Integer.class)) {
            editor.putInt(key, (Integer) value);
        } else if (value.getClass().isAssignableFrom(String.class)) {
            editor.putString(key, (String) value);
        } else if (value.getClass().isAssignableFrom(Boolean.class)) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value.getClass().isAssignableFrom(Long.class)) {
            editor.putLong(key, (Long) value);
        } else if (value.getClass().isAssignableFrom(Float.class)) {
            editor.putFloat(key, (Float) value);
        } else if (value.getClass().isAssignableFrom(String[].class)) {
            editor.putStringSet(key, new LinkedHashSet<>(new LinkedHashSet<>(Arrays.asList((String[]) value))));
        } else {
            throw new RuntimeException("Not support type.Please ensure type is in [Integer,String,Boolean,Long,Float]");
        }
        return editor.commit();

    }

    @SuppressWarnings("unchecked")
    public <T> T getObject(String key, Class<T> clazz) {
        SharedPreferences sp = this.context.getSharedPreferences(this.name, Context.MODE_PRIVATE);
        if (sp.contains(key)) {
            String objectVal = sp.getString(key, "");
            byte[] buffer = Base64.decode(objectVal, Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
       ObjectInputStream ois = null;
            try {
        ois = new ObjectInputStream(bais);
        return (T) ois.readObject();
    } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
    } finally {
        try {
            bais.close();
            if (ois != null) {
                ois.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
        return null;
                }

public void delete(String key) {
        SharedPreferences sp = this.context.getSharedPreferences(this.name, Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.remove(key);
        editor.apply();
        }

}
