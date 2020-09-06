package ${package}.service.dto;

import lombok.Data;
<#if queryHasTimestamp>
import java.sql.Timestamp;
</#if>
<#if queryHasBigDecimal>
import java.math.BigDecimal;
</#if>
<#if betweens??>
import java.util.List;
</#if>
<#if queryColumns??>
import com.mm.admin.common.annotation.TDQuery;
</#if>

/**
* @author ${author}
* @date ${date}
**/
@Data
public class ${className}QueryCriteria{
<#if queryColumns??>
    <#list queryColumns as column>

<#if column.queryType = '='>
    /** 精确 */
    @TDQuery
    private ${column.columnType} ${column.changeColumnName};
</#if>
<#if column.queryType = 'Like'>
    /** 模糊 */
    @TDQuery(type = TDQuery.Type.INNER_LIKE)
    private ${column.columnType} ${column.changeColumnName};
</#if>
<#if column.queryType = '!='>
    /** 不等于 */
    @TDQuery(type = TDQuery.Type.NOT_EQUAL)
    private ${column.columnType} ${column.changeColumnName};
</#if>
<#if column.queryType = 'NotNull'>
    /** 不为空 */
    @TDQuery(type = TDQuery.Type.NOT_NULL)
    private ${column.columnType} ${column.changeColumnName};
</#if>
<#if column.queryType = '>='>
    /** 大于等于 */
    @TDQuery(type = TDQuery.Type.GREATER_THAN)
    private ${column.columnType} ${column.changeColumnName};
</#if>
<#if column.queryType = '<='>
    /** 小于等于 */
    @TDQuery(type = TDQuery.Type.LESS_THAN)
    private ${column.columnType} ${column.changeColumnName};
</#if>
    </#list>
</#if>
<#if betweens??>
    <#list betweens as column>
    /** BETWEEN */
    @TDQuery(type = TDQuery.Type.BETWEEN)
    private List<${column.columnType}> createTime;
    </#list>
</#if>
}
