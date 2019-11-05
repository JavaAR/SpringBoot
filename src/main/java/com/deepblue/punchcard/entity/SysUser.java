package com.deepblue.punchcard.entity;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import lombok.Data;
import javax.persistence.Column;
/**
*@Description: SysUser实体类
*@author 向一
*@date 2019/10/23 23:29
*/
@Data
public class SysUser implements Serializable {

        public SysUser(){}

        public SysUser( String userName, String password, String salt) {
                this.userName = userName;
                this.password = password;
                this.salt = salt;
        }

        //
        @Column(name = "id")
        private Integer id;
        //用户名
        @Column(name = "user_name")
        private String userName;
        //密码
        @Column(name = "password")
        private String password;
        //用于加密MD5
        @Column(name = "salt")
        private String salt;

        //用户角色
        private Set<String> sysRoles;
        //用户权限
        private Set<String> sysPerms;





}
