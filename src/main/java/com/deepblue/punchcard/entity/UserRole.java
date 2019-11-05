package com.deepblue.punchcard.entity;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import javax.persistence.Column;
/**
*@Description: UserRole实体类
*@author 向一
*@date 2019/10/30 22:04
*/
@Data
public class UserRole implements Serializable {
        //
        @Column(name = "id")
        private Integer id;
        //用户ID
        @Column(name = "user_id")
        private Integer userId;
        //角色id
        @Column(name = "role_id")
        private Integer roleId;
}
