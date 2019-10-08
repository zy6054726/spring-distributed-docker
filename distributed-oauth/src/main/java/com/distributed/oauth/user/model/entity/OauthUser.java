package com.distributed.oauth.user.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Data
@Table(name = "oauth_user")
public class OauthUser implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 用户名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 昵称
     */
    @Column(name = "user_nickName")
    private String userNickname;

    /**
     * 密码
     */
    @Column(name = "user_password")
    private String userPassword;

    /**
     * 创建时间
     */
    @Column(name = "user_createDate")
    private Date userCreatedate;

    /**
     * 是否删除（0-否  1-是）
     */
    @Column(name = "is_delete")
    private Integer isDelete;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userName=").append(userName);
        sb.append(", userNickname=").append(userNickname);
        sb.append(", userPassword=").append(userPassword);
        sb.append(", userCreatedate=").append(userCreatedate);
        sb.append(", isDelete=").append(isDelete);
        sb.append("]");
        return sb.toString();
    }
}