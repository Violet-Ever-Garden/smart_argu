import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author LvHao
 * @Description : MYSQL生成演示
 * @date 2020-08-08 13:14
 */
public class GeneratorService {

    /**
     * 基础配置
     * BASE_PACKAGE：包名开头
     * PACKAGE_PREFIX：项目前缀
     * CODE_LOCATION：代码存放目录
     * XML_LOCATION：mapper xml文件存放目录
     * AUTHOR：作者
     */
    private static final String BASE_PACKAGE = "hzau.sa";
    private static final String PACKAGE_PREFIX = "hzau-sa-";
    private static final String CODE_LOCATION = "\\src\\main\\java";
    private static final String XML_LOCATION = "\\src\\main\\resources\\mapper";
    private static final String AUTHOR = "wuyihu";

    /**
     * 数据库配置
     */
    private static final String JDBC_URL = "jdbc:mysql://101.200.215.126:3306/hzau?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "hk123456";
    private static final String JDBC_DIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";


    public static void main(String[] args) {

        /**
         * 代码生成的初始配置
         * moduleName: 模块名
         * tableName: 表名
         */
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入模块名称（已经有前缀hzau-sa-）：");
        String moduleName = scanner.nextLine();
        System.out.println(System.getProperty("user.dir") + File.separator + PACKAGE_PREFIX + moduleName);
        System.out.print("请输入表名（没有前缀）：");
        String tableName = scanner.nextLine();
        System.out.println("tableName:" + tableName);

        //--------------------------------------------------------

        AutoGenerator autoGenerator = new AutoGenerator();

        /**
         * 全局配置
         */
        String projectPath = System.getProperty("user.dir");
        String outputDir = projectPath + File.separator + PACKAGE_PREFIX + moduleName + CODE_LOCATION;
        String daoXmlDir = projectPath + File.separator + PACKAGE_PREFIX + moduleName + XML_LOCATION;

        GlobalConfig globalConfig = new GlobalConfig()
                .setOutputDir(outputDir)
                .setFileOverride(true)
                .setActiveRecord(true)
                .setEnableCache(false)
                .setBaseResultMap(false)
                .setBaseColumnList(false)
                .setOpen(false)
                .setAuthor(AUTHOR)
                .setMapperName("%sDao")
                .setXmlName("%sMapper")
                .setServiceName("%sService")
                .setControllerName("%sController");
        autoGenerator.setGlobalConfig(globalConfig);

        /**
         * 项目包配置
         */
        PackageConfig packageConfig = new PackageConfig()
                .setParent(BASE_PACKAGE)
                .setMapper("dao")
                .setEntity("entity")
                .setController("controller")
                .setService("service")
                .setModuleName(moduleName);
        autoGenerator.setPackageInfo(packageConfig);

        /**
         * 数据源配置
         */
        DataSourceConfig dataSourceConfig = new DataSourceConfig()
                .setDbType(DbType.MYSQL)
                .setDriverName(JDBC_DIVER_CLASS_NAME)
                .setUsername(JDBC_USERNAME)
                .setPassword(JDBC_PASSWORD)
                .setUrl(JDBC_URL);
        autoGenerator.setDataSource(dataSourceConfig);

        /**
         * 策略配置
         * setCapitalMode: 全局大写命名 ORACLE 注意
         * setTablePrefix: 此处可以修改为您的表前缀
         * etNaming: 表名生成策略
         * setInclude: 需要生成的表
         */
        StrategyConfig strategyConfig = new StrategyConfig()
                .setCapitalMode(true)
                .setTablePrefix("")
                .setNaming(NamingStrategy.underline_to_camel)
                .setInclude(tableName.split(","))
                .setEntityBuilderModel(true)
                .setEntityLombokModel(true);
        autoGenerator.setStrategy(strategyConfig);

        // 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】 实例： #{cfg.abc} 可取下面abc的设置的值
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                map.put("date", LocalDateTime.now());
                this.setMap(map);
            }
        };
        // 自定义 entity生成
        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
        focList.add(new FileOutConfig("entity.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return outputDir + File.separator + BASE_PACKAGE.replace(".", "\\")
                        + File.separator + moduleName.replace(".", "\\") + "\\entity"
                        + File.separator + tableInfo.getEntityName() + "VO.java";
            }
        });
        // 调整 xml 生成目录演示
        focList.add(new FileOutConfig("dao.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return daoXmlDir + File.separator + tableInfo.getEntityName() + "Mapper.xml";
            }
        });
        cfg.setFileOutConfigList(focList);
        autoGenerator.setCfg(cfg);

        TemplateConfig tc = new TemplateConfig().setController("controller.java.vm")
                .setEntity(StringUtils.isNotEmpty(outputDir) ? null : "entity.java.vm").setMapper("dao.java.vm")
                .setXml(StringUtils.isNotEmpty(daoXmlDir) ? null : "dao.xml.vm").setService("service.java.vm");
        autoGenerator.setTemplate(tc);

        // 执行生成
        autoGenerator.execute();
    }
}
