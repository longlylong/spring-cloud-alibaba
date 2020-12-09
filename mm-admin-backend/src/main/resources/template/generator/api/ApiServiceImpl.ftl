package ${apiPackage}.service.impl;

import com.thatday.base.service.BaseServiceImpl;
import ${apiPackage}.dao.${className}Dao;
import ${apiPackage}.entity.${className};
import ${apiPackage}.service.${className}Service;

import org.springframework.stereotype.Service;

/**
* @description 服务实现
* @author ${author}
* @date ${date}
**/
@Service
public class ${className}ServiceImpl extends BaseServiceImpl<${className}, ${pkColumnType}, ${className}Dao> implements ${className}Service {

    /**
    *Long返回null主键默认是自增
    *String需要设置主键
    */
    @Override
    public Long customDatabaseId() {
        return null;
    }
}
