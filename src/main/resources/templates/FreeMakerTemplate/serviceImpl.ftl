package ${basePackageServiceImpl};
import ${basePackageDao}.${modelNameUpperCamel}Mapper;
import ${basePackageModel}.${modelNameUpperCamel};
import ${basePackageService}.${modelNameUpperCamel}Service;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
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
    @Override
    public ${modelNameUpperCamel} get${modelNameUpperCamel}ById(Long id)throws Exception{
        return ${modelNameLowerCamel}Mapper.get${modelNameUpperCamel}ById(id);
    }
    @Override
    public List<${modelNameUpperCamel}>	get${modelNameUpperCamel}ListByMap(Map<String,Object> param)throws Exception{
        return ${modelNameLowerCamel}Mapper.get${modelNameUpperCamel}ListByMap(param);
    }
    @Override
    public Integer get${modelNameUpperCamel}CountByMap(Map<String,Object> param)throws Exception{
        return ${modelNameLowerCamel}Mapper.get${modelNameUpperCamel}CountByMap(param);
    }
    @Override
    public Integer insert${modelNameUpperCamel}(${modelNameUpperCamel} ${modelNameLowerCamel})throws Exception{
            return ${modelNameLowerCamel}Mapper.insert${modelNameUpperCamel}(${modelNameLowerCamel});
    }
    @Override
    public Integer update${modelNameUpperCamel}(${modelNameUpperCamel} ${modelNameLowerCamel})throws Exception{
        return ${modelNameLowerCamel}Mapper.update${modelNameUpperCamel}(${modelNameLowerCamel});
    }
    @Override
    public Integer delete${modelNameUpperCamel}ById(Long id)throws Exception{
        return ${modelNameLowerCamel}Mapper.delete${modelNameUpperCamel}ById(id);
    }



}
