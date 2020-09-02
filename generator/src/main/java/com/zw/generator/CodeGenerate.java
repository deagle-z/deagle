package com.zw.generator;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;

public class CodeGenerate {
    private static final String TABLE_NAMES = "sys_file";
    private static final String ENTITY_NAME = "SysFile";
    private static final String PARENT_MODULE_NAME = "com.zw.file";
    public static final String MAPPER_NAME = "SysFile";

    private static final String AUTHOR = "zw";

    private static final String DIR = "D:\\generate";

    private static final String MODULE_NAME = "business";

    private static final String DATASOURCE = "124.70.222.176:3306";
    private static final String DBNAME = "deagle";
    private static final String DB_USER = "deaglez";
    private static final String DB_PASSWORD = "321!@#";

    public static void main(String[] args) {

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        System.out.println("----------------开始生成----------------");
        gc.setOutputDir(DIR + "/src/main/java");
        gc.setAuthor(AUTHOR);
        gc.setOpen(false);
        gc.setSwagger2(true);
//        gc.setDateType(DateT)
        gc.setEntityName(ENTITY_NAME + "Entity");
        gc.setServiceName(ENTITY_NAME + "Service");
        gc.setServiceImplName(ENTITY_NAME + "ServiceImpl");
        gc.setControllerName(ENTITY_NAME + "Controller");
        gc.setMapperName(MAPPER_NAME + "Mapper");
        gc.setXmlName(MAPPER_NAME + "Mapper.xml");
        //gc.setSwagger2(true); //实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://" + DATASOURCE + "/" + DBNAME + "?useUnicode=true&useSSL=false&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername(DB_USER);
        dsc.setPassword(DB_PASSWORD);
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(MODULE_NAME);
        pc.setParent(PARENT_MODULE_NAME);
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();

        //  开启自定义输出
        //  自定义配置会被优先输出
        final FileOutConfig fileOutConfig = new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return DIR + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + MAPPER_NAME + "Mapper" + StringPool.DOT_XML;
            }
        };
        System.out.println(fileOutConfig.toString()+"------------------------");
        focList.add(fileOutConfig);


        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录，自定义目录用");
                if (fileType == FileType.MAPPER) {
                    // 已经生成 mapper 文件判断存在，不想重新生成返回 false
                    return !new File(filePath).exists();
                }
                // 允许生成模板文件
                return true;
            }
        });
        */
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
         templateConfig.setEntity("templates/entity.java");
         templateConfig.setService("templates/service.java");
         templateConfig.setController("templates/controller.java");
         templateConfig.setServiceImpl("templates/serviceImpl.java");
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);




        StrategyConfig strategy = new StrategyConfig();

        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父类
//        strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
        // 写于父类中的公共字段
//        strategy.setSuperEntityColumns("id");
        strategy.setInclude(TABLE_NAMES);
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");


        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
