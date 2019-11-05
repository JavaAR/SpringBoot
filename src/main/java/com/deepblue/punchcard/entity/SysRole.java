package com.deepblue.punchcard.entity;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import javax.persistence.Column;
/**
*@Description: SysRole实体类
*@author 向一
*@date 2019/10/30 22:04
*/
@Data
public class SysRole implements Serializable {
        //角色id
        @Column(name = "id")
        private Integer id;
        //角色名称，用于显示
        @Column(name = "role_name")
        private String roleName;
        //角色描述
        @Column(name = "role_desc")
        private String roleDesc;
        //角色值，用于权限判断
        @Column(name = "role_value")
        private String roleValue;
        //
        @Column(name = "create_time")
        private Date createTime;
        //
        @Column(name = "update_time")
        private Date updateTime;
        //是否禁用
        @Column(name = "is_disable")
        private Integer isDisable;
}
