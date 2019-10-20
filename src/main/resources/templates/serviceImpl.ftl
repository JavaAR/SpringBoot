package ${basePackageServiceImpl};
import ${basePackageDao}.${modelNameUpperCamel}Mapper;
import ${basePackageModel}.${modelNameUpperCamel};
import ${basePackageService}.${modelNameUpperCamel}Service;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
*@Description: ${modelNameUpperCamel}Service接口
* @author ${author}
*@date${date}
*/
@Service
public class ${modelNameUpperCamel}ServiceImpl implements ${modelNameUpperCamel}Service {

    @Resource
    private ${modelNameUpperCamel}Mapper ${modelNameLowerCamel}Mapper;

    public ${modelNameUpperCamel} get${modelNameUpperCamel}ById(Long id)throws Exception{
        return ${modelNameLowerCamel}Mapper.get${modelNameUpperCamel}ById(id);
    }

    public List<${modelNameUpperCamel}>	get${modelNameUpperCamel}ListByMap(Map<String,Object> param)throws Exception{
        return ${modelNameLowerCamel}Mapper.get${modelNameUpperCamel}ListByMap(param);
    }

    public Integer get${modelNameUpperCamel}CountByMap(Map<String,Object> param)throws Exception{
        return ${modelNameLowerCamel}Mapper.get${modelNameUpperCamel}CountByMap(param);
    }

    public Integer Add${modelNameUpperCamel}(${modelNameUpperCamel} ${modelNameLowerCamel})throws Exception{
            ${modelNameLowerCamel}.setCreationDate(new Date());
            return ${modelNameLowerCamel}Mapper.insert${modelNameUpperCamel}(${modelNameLowerCamel});
    }

    public Integer Modify${modelNameUpperCamel}(${modelNameUpperCamel} ${modelNameLowerCamel})throws Exception{
        ${modelNameLowerCamel}.setModifyDate(new Date());
        return ${modelNameLowerCamel}Mapper.update${modelNameUpperCamel}(${modelNameLowerCamel});
    }

    public Integer Delete${modelNameUpperCamel}ById(Long id)throws Exception{
        return ${modelNameLowerCamel}Mapper.delete${modelNameUpperCamel}ById(id);
    }



}
