package cn.${package}.pojo;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
/**
*@Description: ${modelNameUpperCamel}Service接口
*@author ${author}
*@date ${date}
*/
@Data
public class ${modelNameUpperCamel} implements Serializable {
        <#list table.cloumns as cloumn>
        //${cloumn.comment}
        private ${cloumn.javaType} ${cloumn.fieldName};
        </#list>
}
