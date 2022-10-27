package com.yijiang.model;

import com.jfinal.plugin.activerecord.Model;

public class UserModel extends Model<UserModel> {
    private static final long serialVersionUID = 1L;
    public static final UserModel dao = new UserModel();
}
