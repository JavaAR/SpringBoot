package com.deepblue.punchcard.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Data
public class UserInfo implements Serializable {

    @Id
    private String id;
    /**
     * 用户名
     */
    @Column(name = "user_name")
    private String userName;
    private String password;

}
