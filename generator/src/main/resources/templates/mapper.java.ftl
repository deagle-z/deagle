package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};

/**
 * <p>
 * ${table.comment!} Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
 @Mapper
interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
 @Mapper
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {

}
</#if>
