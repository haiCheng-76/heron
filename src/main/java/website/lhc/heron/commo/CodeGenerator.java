package website.lhc.heron.commo;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: heron
 * @Package: webiste.lhc.heron.commo
 * @ClassName: CodeGenerator
 * @Author: lhc
 * @Description: TODO
 * @Date: 2020/8/16 下午 12:08
 */
public class CodeGenerator {
    public static void main(String[] args) throws InterruptedException, SQLException, IOException, InvalidConfigurationException, XMLParserException {
        doCode("role_info", "id");
    }

    /**
     * @param tableName 表名
     * @param key       主键
     * @throws InvalidConfigurationException
     * @throws InterruptedException
     * @throws SQLException
     * @throws IOException
     */
    private static void doCode(String tableName, String key) throws InvalidConfigurationException, InterruptedException, SQLException, IOException {
//        ResourceBundle resourceBundle = ResourceBundle.getBundle("application.yml");
        List<String> warnings = new ArrayList<>();
        Configuration config = new Configuration();
        // context配置
        Context context = new Context(ModelType.FLAT);
        context.setTargetRuntime("MyBatis3Simple");
        context.setId("MBG");
        context.addProperty("beginningDelimiter", "'");
        context.addProperty("endingDelimiter", "'");

        // 通用Mapper插件配置
        PluginConfiguration mapperConfiguration = new PluginConfiguration();
        mapperConfiguration.setConfigurationType("tk.mybatis.mapper.generator.MapperPlugin");

        mapperConfiguration.addProperty("mappers", "website.lhc.heron.framework.mybatis.BaseMapper");
        // 序列化插件配置
        PluginConfiguration serializConfiguration = new PluginConfiguration();
        serializConfiguration.setConfigurationType("org.mybatis.generator.plugins.SerializablePlugin");

        context.addPluginConfiguration(mapperConfiguration);
        context.addPluginConfiguration(serializConfiguration);
        // 注释配置
        CommentGeneratorConfiguration commentGeneratorConfiguration = new CommentGeneratorConfiguration();
        commentGeneratorConfiguration.addProperty("suppressAllComments", "true");
        commentGeneratorConfiguration.addProperty("suppressDate", "true");
        context.setCommentGeneratorConfiguration(commentGeneratorConfiguration);
        // jdbc配置
        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setConnectionURL("jdbc:mysql://localhost:3306/heron?characterEncoding=utf-8&useSSL=false");
        jdbcConnectionConfiguration.setUserId("lhc");
        jdbcConnectionConfiguration.setPassword("123456");
        jdbcConnectionConfiguration.setDriverClass("com.mysql.jdbc.Driver");
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);
        // 生成实体类的存放路径
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetPackage("website.lhc.heron.model");
        javaModelGeneratorConfiguration.setTargetProject("src/main/java");
        javaModelGeneratorConfiguration.addProperty("enableSubPackages", "true");
        javaModelGeneratorConfiguration.addProperty("trimStrings", "true");
        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);
        // 生成mapping.xml的存放路径
        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        sqlMapGeneratorConfiguration.setTargetPackage("/mapper");
        sqlMapGeneratorConfiguration.setTargetProject("src/main/resources");
        sqlMapGeneratorConfiguration.addProperty("enableSubPackages", "true");
        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);
        // 生成接口和实现类的存放路径;type指定sql文件是注解形式、混合模式或XML模式
        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
        javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
        javaClientGeneratorConfiguration.setTargetPackage("website.lhc.heron.mapper");
        javaClientGeneratorConfiguration.setTargetProject("src/main/java");
        javaClientGeneratorConfiguration.addProperty("enableSubPackages", "true");
        context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);
        TableConfiguration tableConfiguration = new TableConfiguration(context);
        tableConfiguration.setTableName(tableName);
        // 主键自增
        GeneratedKey generatedKey = new GeneratedKey(key, "mysql", Boolean.TRUE, null);
        tableConfiguration.setGeneratedKey(generatedKey);
        // PermissionMapper
        context.addTableConfiguration(tableConfiguration);
        config.addContext(context);
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
        System.out.println("### 成功 ###");
    }
}
