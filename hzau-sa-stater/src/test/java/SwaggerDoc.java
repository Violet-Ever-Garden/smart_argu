import hzau.sa.stater.Application;
import io.github.swagger2markup.GroupBy;
import io.github.swagger2markup.Language;
import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;
import org.asciidoctor.cli.AsciidoctorInvoker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;
import java.nio.file.Paths;

/**
 * @author haokai
 * @create 2020-9-20
 */


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,classes = Application.class)
public class SwaggerDoc {

    /*
    导出静态资源的使用方式（为了更好维护，一个模块一个模块导出）
    1、导出前检查该模块中的SwaggerConfig中 配置的groupName，如果包含中文，替换成同义英文（在导出结束后还原即可）
    2、修改generateAsciiDocs方法下Swagger2MarkupConverter.from(new URL())中group参数为配置的配置的groupName
    3、运行generateAsciiDocs方法，在docs/asciidoc/generated目录下生成adoc文件
    4、打开maven工具栏，在stater下plugin中选中asciidoctor，双击下面的process-asciidoc
        将生成的adoc文件转化成html文件，至此将生成的html复制打包成文件夹保存下来。
     */
    @Test
    public void generateAsciiDocs() throws Exception {
        //    输出Ascii格式
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.ASCIIDOC)//设置生成格式
                .withOutputLanguage(Language.ZH)//设置语言中文还是其他语言
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();
        //设置swagger-api的json来源
        Swagger2MarkupConverter.from(new URL("http://localhost:8080/v2/api-docs?group=HZAU-Smart-Agriculture-log"))
                .withConfig(config)
                .build()
                .toFolder(Paths.get("./docs/asciidoc/generated"));//设置生成文件的路径
    }

    @Test
    public void generatePDF() {
        //样式
        String style = "pdf-style=E:\\themes\\theme.yml";
        //字体
        String fontsdir = "pdf-fontsdir=E:\\fonts";
        //需要指定adoc文件位置
        String adocPath = "E:\\all.adoc";
        AsciidoctorInvoker.main(new String[]{"-a",style,"-a",fontsdir,"-b","pdf",adocPath});
    }

    /**
     * 生成AsciiDocs格式文档
     * @throws Exception
     */


    /**
     * 生成Markdown格式文档
     * @throws Exception
     */
    @Test
    public void generateMarkdownDocs() throws Exception {
        //    输出Markdown格式
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.MARKDOWN)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();

        Swagger2MarkupConverter.from(new URL("http://localhost:8080/v2/api-docs?group=HZAU-Smart-Agriculture-log"))
                .withConfig(config)
                .build()
                .toFolder(Paths.get("/src/docs/markdown/generated"));
    }
    /**
     * 生成Confluence格式文档
     * @throws Exception
     */
    @Test
    public void generateConfluenceDocs() throws Exception {
        //    输出Confluence使用的格式
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.CONFLUENCE_MARKUP)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();

        Swagger2MarkupConverter.from(new URL("http://localhost:8080/v2/api-docs"))
                .withConfig(config)
                .build()
                .toFolder(Paths.get("./docs/confluence/generated"));
    }

    /**
     * 生成AsciiDocs格式文档,并汇总成一个文件
     * @throws Exception
     */
    @Test
    public void generateAsciiDocsToFile() throws Exception {
        //    输出Ascii到单文件
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.ASCIIDOC)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();

        Swagger2MarkupConverter.from(new URL("http://localhost:8080/v2/api-docs"))
                .withConfig(config)
                .build()
                .toFile(Paths.get("./docs/asciidoc/generated/all"));
    }

    /**
     * 生成Markdown格式文档,并汇总成一个文件
     * @throws Exception
     */
    @Test
    public void generateMarkdownDocsToFile() throws Exception {
        //    输出Markdown到单文件
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.MARKDOWN)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();

        Swagger2MarkupConverter.from(new URL("http://localhost:8080/v2/api-docs"))
                .withConfig(config)
                .build()
                .toFile(Paths.get("./docs/markdown/generated/all"));
    }

}
