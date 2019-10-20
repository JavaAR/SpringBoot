package ${basePackageService};
import ${basePackageModel}.${modelNameUpperCamel};
import java.util.List;
import java.util.Map;

/**
*@Description: ${modelNameUpperCamel}Service接口
* @author ${author}
*@date ${date}
*/
public interface ${modelNameUpperCamel}Service {

    public ${modelNameUpperCamel} get${modelNameUpperCamel}ById(Long id)throws Exception;

    public List<${modelNameUpperCamel}>	get${modelNameUpperCamel}ListByMap(Map<String,Object> param)throws Exception;

    public Integer get${modelNameUpperCamel}CountByMap(Map<String,Object> param)throws Exception;

    public Integer Add${modelNameUpperCamel}(${modelNameUpperCamel} ${modelNameLowerCamel})throws Exception;

    public Integer Modify${modelNameUpperCamel}(${modelNameUpperCamel} ${modelNameLowerCamel})throws Exception;

    public Integer Delete${modelNameUpperCamel}ById(Long id)throws Exception;


}
