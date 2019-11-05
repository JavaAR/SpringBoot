package com.deepblue.punchcard.entity;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import javax.persistence.Column;
/**
*@Description: SysPerm实体类
*@author 向一
*@date 2019/10/30 22:04
*/
@Data
public class SysPerm implements Serializable {
        //
        @Column(name = "id")
        private Integer id;
        //权限名称
        @Column(name = "perm_name")
        private String permName;
        //权限描述
        @Column(name = "perm_desc")
        private String permDesc;
        //权限值
        @Column(name = "perm_value")
        private String permValue;
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
