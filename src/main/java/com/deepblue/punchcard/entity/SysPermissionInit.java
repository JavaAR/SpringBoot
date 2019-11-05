package com.deepblue.punchcard.entity;
import java.io.Serializable;
import lombok.Data;

import javax.persistence.Column;

/**
*@Description: SysPermissionInit实体类
*@author 向一
*@date 2019/10/30 22:04
*/
@Data
public class SysPermissionInit implements Serializable {
        //
        @Column(name = "id")
        private Integer id;
        //程序对应url地址
        @Column(name = "url")
        private String url;
        //对应shiro权限
        @Column(name = "permission_init")
        private String permissionInit;
        //排序
        @Column(name = "sort")
        private Integer sort;
}
