<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${basePackageDao}.${modelNameUpperCamel}Mapper">

    <select id="get${table.className}ById" resultType="${basePackageModel}.${modelNameUpperCamel}" >
        select
        <#list table.cloumns as cloumn>
            <#if cloumn_has_next>
                ${cloumn.cloumnName} as ${cloumn.fieldName},
            <#else>
                ${cloumn.cloumnName} as ${cloumn.fieldName}
            </#if>
        </#list>
        from ${table.tableName}
        <trim prefix="where" prefixOverrides="and | or">
            <if test="id != null">
                and id=${r"#{id}"}
            </if>
        </trim>
    </select>

    <select id="get${table.className}ListByMap" resultType="${basePackageModel}.${modelNameUpperCamel}" parameterType="java.util.Map">
        select
        <#list table.cloumns as cloumn>
            <#if cloumn_has_next>
                ${cloumn.cloumnName} as ${cloumn.fieldName},
            <#else>
                ${cloumn.cloumnName} as ${cloumn.fieldName}
            </#if>
        </#list>
        from ${table.tableName}
        <trim prefix="where" prefixOverrides="and | or">
            <#list table.cloumns as cloumn>
                <#if cloumn_has_next>
                    <if test="${cloumn.fieldName} != null and ${cloumn.fieldName}!=''">
                        and ${cloumn.cloumnName}=${r"#{"}${cloumn.fieldName}}
                    </if>
                </#if>
            </#list>
        </trim>
        <if test="beginPos != null and beginPos!='' and pageSize != null  and pageSize !='' ">
            limit ${r"#{"}beginPos},${r"#{"}pageSize}
        </if>
    </select>

    <select id="get${table.className}CountByMap" resultType="Integer"  parameterType="java.util.Map">
        select count(*) from ${table.tableName}
        <trim prefix="where" prefixOverrides="and | or">
        <#list table.cloumns as cloumn>
            <#if cloumn_has_next>
                <if test="${cloumn.fieldName} != null and ${cloumn.fieldName}!=''">
                    and ${cloumn.cloumnName}=${r"#{"}${cloumn.fieldName}}
                </if>
            <#else>
                <if test="${cloumn.fieldName} != null and ${cloumn.fieldName}!=''">
                    and ${cloumn.cloumnName}=${r"#{"}${cloumn.fieldName}}
                </if>
            </#if>
        </#list>
        </trim>
    </select>

    <insert id="insert${table.className}" parameterType="${basePackageModel}.${modelNameUpperCamel}">
        insert into ${table.tableName}(
        <#list table.cloumns as cloumn>
                <#if cloumn_has_next>
                    <#if  cloumn.cloumnName!='id'>
                        ${cloumn.cloumnName},
                    </#if>
                <#else>
                    <#if  cloumn.cloumnName!='id'>
                        ${cloumn.cloumnName})
                    </#if>
                </#if>
        </#list>
        values(
        <#list table.cloumns as cloumn>
            <#if cloumn_has_next>
                <#if  cloumn.cloumnName!='id'>
                     ${r"#{"}${cloumn.fieldName}},
                </#if>
            <#else>
                <#if  cloumn.cloumnName!='id'>
                    ${r"#{"}${cloumn.fieldName}})
                </#if>
            </#if>
        </#list>
    </insert>

    <update id="update${table.className}" parameterType="${basePackageModel}.${modelNameUpperCamel}">
        update ${table.tableName}
        <trim prefix="set" suffixOverrides="," suffix="where id=${r"#{"}id}">
            <#list table.cloumns as cloumn>
                <#if cloumn_has_next>
                    <if test="${cloumn.fieldName} != null and ${cloumn.fieldName}!=''">
                        ${cloumn.cloumnName}=${r"#{"}${cloumn.fieldName}},
                    </if>
                <#else>
                    <if test="${cloumn.fieldName} != null and ${cloumn.fieldName}!=''">
                        ${cloumn.cloumnName}=${r"#{"}${cloumn.fieldName}}
                    </if>
                </#if>
            </#list>
        </trim>
    </update>

    <delete id="delete${table.className}ById" parameterType="Long">
        delete from ${table.tableName} where id = ${r"#{"}id}
    </delete>
</mapper>