package com.jack.project.root.bean;

import java.io.Serializable;

public class UserBean implements Serializable, Cloneable {
    @Override
    public UserBean clone() {
        try {
            return (UserBean) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return this;
    }
}
