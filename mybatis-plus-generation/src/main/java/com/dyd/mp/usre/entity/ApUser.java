package com.dyd.mp.usre.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * APP用户信息表
 * </p>
 *
 * @author dyd
 * @since 2023-11-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ApUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 密码、通信等加密盐
     */
    private String salt;

    /**
     * 用户名
     */
    private String name;

    /**
     * 密码,md5加密
     */
    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 头像
     */
    private String image;

    /**
     * 0 男
     * 1 女
     * 2 未知
     */
    private Boolean sex;

    /**
     * 0 未
     * 1 是
     */
    private Boolean isCertification;

    /**
     * 是否身份认证
     */
    private Boolean isIdentityAuthentication;

    /**
     * 0正常
     * 1锁定
     */
    private Boolean status;

    /**
     * 0 普通用户
     * 1 自媒体人
     * 2 大V
     */
    private Boolean flag;

    /**
     * 注册时间
     */
    private LocalDateTime createdTime;


}
