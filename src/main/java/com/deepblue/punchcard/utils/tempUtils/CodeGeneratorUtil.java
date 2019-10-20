package com.deepblue.punchcard.utils.tempUtils;

import com.deepblue.punchcard.constant.ProjectConstant;
import com.google.common.base.CaseFormat;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import jdk.nashorn.internal.objects.annotations.Where;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * 代码生成器工具类
 */
public class CodeGeneratorUtil {

    //jdbc配置
    private final static String JDBC_URL="jdbc:mysql://localhost:0710/test";
    private final static String JDBC_USERNAME ="root";
    private final static String JDBC_PASSWORD ="123456";
    private final static String JDBC_DRIVER ="com.mysql.jdbc.Driver";
    //模板位置
    private final static String TEMPLATE_FILE_PATH="src/main/resources/templates";
    private static final String JAVA_PATH = "src/main/java"; // java文件路径
    private static final String RESOURCES_PATH = "src/main/resources";// 资源文件路径
    //生成实体类的位置
    private static final String PACKAGE_PATH_POJO = packageConvertPath(ProjectConstant.MODEL_PACKAGE);
    //生成mapper.xml文件的位置
    private static final String PACKAGE_PATH_MAPPER = packageConvertPath(RESOURCES_PATH+"/mapper");
    //生成dao接口的文件位置
    private static final String PACKAGE_PATH_DAO = packageConvertPath(ProjectConstant.MAPPER_PACKAGE);
    //生成Service接口的位置
    private static final String PACKAGE_PATH_SERVICE = packageConvertPath(ProjectConstant.SERVICE_PACKAGE);
    //生成ServiceImpl的位置
    private static final String PACKAGE_PATH_SERVICE_IMPL = packageConvertPath(ProjectConstant.SERVICE_IMPL_PACKAGE);
    // @author
    private static final String AUTHOR = "向一";
    // @date
    private static final String DATE = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date());


    public static void main(String[] args) {
        genCode("system_log");
    }


    //通过数据表名称生成代码，Model 名称通过解析数据表名称获得，下划线转大驼峰的形式。 如输入表名称 "t_user_detail" 将生成TUserDetail、TUserDetailMapper、TUserDetailService ...
    public static void genCode(String... tableNames) {
        for (String tableName : tableNames) {
            genCode(tableName);
        }
    }

    //通过数据表名称生成代码，Model 名称通过解析数据表名称获得，下划线转大驼峰的形式。 如输入表名称 "t_user_detail" 将生成TUserDetail、TUserDetailMapper、TUserDetailService ...(重载)
    public static void genCode(String tableName) {
        //genService(tableName);
        genModel();
    }
    //生成mapper
    private static void genMapper(String tableName) {

    }
    //生成service和serviceImpl
    private static void genService(String tableName) {
        try {
            Configuration cfg = getConfiguration();
            //模板所需要的参数
            Map<String, Object> data = new HashMap<>();
            data.put("date", DATE);
            data.put("author", AUTHOR);
            String modelNameUpperCamel = tableNameConvertUpperCamel(tableName);
            data.put("modelNameUpperCamel", modelNameUpperCamel);
            data.put("modelNameLowerCamel", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelNameUpperCamel));
            data.put("basePackage", ProjectConstant.BASE_PACKAGE);
            data.put("basePackageService", ProjectConstant.SERVICE_PACKAGE);
            data.put("basePackageServiceImpl", ProjectConstant.SERVICE_IMPL_PACKAGE);
            data.put("basePackageModel", ProjectConstant.MODEL_PACKAGE);
            data.put("basePackageDao", ProjectConstant.MAPPER_PACKAGE);
            //生成service接口
            File file = new File(JAVA_PATH + PACKAGE_PATH_SERVICE + modelNameUpperCamel + "Service.java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("service.ftl").process(data, new FileWriter(file));
            System.out.println(modelNameUpperCamel + "Service.java 生成成功");
            //生成serviceImpl实现类
            File file1 = new File(JAVA_PATH + PACKAGE_PATH_SERVICE_IMPL + modelNameUpperCamel + "ServiceImpl.java");
            if (!file1.getParentFile().exists()) {
                file1.getParentFile().mkdirs();
            }
            cfg.getTemplate("serviceImpl.ftl").process(data, new FileWriter(file1));
            System.out.println(modelNameUpperCamel + "ServiceImpl.java 生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成Service失败", e);
        }
    }
    //表名大写
    private static String tableNameConvertUpperCamel(String tableName) {
         return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName.toLowerCase());
    }

    //生成Model
    private static void genModel() {

        try {
            //获取表信息以及字段信息
            List<Table> tables = collectionDB(JDBC_URL, JDBC_DRIVER, JDBC_USERNAME, JDBC_PASSWORD);
            for (Table table : tables) {
                System.out.println("表名"+table.getTableName());
                System.out.println("表注释"+table.getComment());
                List<Cloumn> cloumns = table.getCloumns();
                for (Cloumn cloumn : cloumns) {
                    System.out.println("字段名："+cloumn.getCloumnName());
                    System.out.println("字段类型"+cloumn.getCloumnType());
                    System.out.println("字段注释"+cloumn.getComment());
                    System.out.println("字段java注释"+cloumn.getJavaType());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }



    //连接数据库 返回表和字段信息
    private static List<Table> collectionDB(String url,String driver,String username,String password) {
        DatabaseMetaData dmd = null;
        Connection conn = null;
        List<Table> tables = new ArrayList<Table>();
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
            dmd = conn.getMetaData();
            ResultSet resultSet = dmd.getTables(null, null, null, new String[]{"TABLE"});
            while(resultSet.next()){
                Table table = new Table();
                table.setCloumns(new ArrayList<Cloumn>());
                //获取表名
                String tableName = resultSet.getString("TABLE_NAME");
                table.setTableName(tableName);
                //获取表注释
                String comment = getCommentByTableName(tableName);
                table.setComment(comment);
                //根据表名获取字段信息
                ResultSet tableColumns = dmd.getColumns(null, "%", tableName, "%");
                    //根据表明获取字段信息
                    while (tableColumns.next()){
                        Cloumn cloumn = new Cloumn();
                        cloumn.setCloumnName(tableColumns.getString("COLUMN_NAME"));
                        cloumn.setComment(tableColumns.getString("REMARKS"));
                        cloumn.setCloumnType(tableColumns.getString("TYPE_NAME"));
                        table.getCloumns().add(cloumn);
                    }
                tables.add(table);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return tables;
    }

    //获取表的注释信息
    private static String getCommentByTableName(String tableName) throws Exception {
        Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SHOW CREATE TABLE " + tableName);
        String comment = null;
        if (rs != null && rs.next()) {
            comment = parse(rs.getString(2));
        }
        rs.close();
        stmt.close();
        conn.close();
        return comment;
    }
    //格式化表的注释信息
    private static String parse(String all) {
        String comment = null;
        int index = all.indexOf("COMMENT='");
        if (index < 0) {
            return "";
        }
        comment = all.substring(index + 9);
        comment = comment.substring(0, comment.length() - 1);
        return comment;
    }
    //freemaker 配置bean
    private static Configuration getConfiguration() throws IOException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setDirectoryForTemplateLoading(new File(TEMPLATE_FILE_PATH));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        return cfg;
    }

    //格式化文件路径. 转换为\\.
    private static String packageConvertPath(String packageName) {
        return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
    }
}
