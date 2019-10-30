package ${basePackageModel};
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import javax.persistence.Column;
/**
*@Description: ${modelNameUpperCamel}实体类
*@author ${author}
*@date ${date}
*/
@Data
public class ${modelNameUpperCamel} implements Serializable {
        <#list table.cloumns as cloumn>
        //${cloumn.comment}
        @Column(name = "${cloumn.cloumnName}")
        private ${cloumn.javaType} ${cloumn.fieldName};
        </#list>
}
