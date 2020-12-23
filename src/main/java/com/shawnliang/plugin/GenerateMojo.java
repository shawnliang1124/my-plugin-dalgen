package com.shawnliang.plugin;

import com.shawnliang.plugin.bean.TableInfo;
import com.shawnliang.plugin.config.ConstVal;
import com.shawnliang.plugin.config.TemplateConfig;
import com.shawnliang.plugin.util.StringUtils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;

@Mojo(name = "code", threadSafe = true)
public class GenerateMojo extends AbstractGenerateMojo { private VelocityEngine engine;

    private Map<String, String> outputFiles;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        this.log.info("==========================");
                initConfig();
        initOutputFiles();
        mkdirs(this.config.getPathInfo());
        Map<String, VelocityContext> ctxData = analyzeData(this.config);
        for (Map.Entry<String, VelocityContext> ctx : ctxData.entrySet())
            batchOutput(ctx.getKey(), ctx.getValue());
        this.log.info("==========================");
    }

    private Map<String, VelocityContext> analyzeData(ConfigBuilder config) {
        List<TableInfo> tableList = config.getTableInfoList();
        Map<String, String> packageInfo = config.getPackageInfo();
        Map<String, VelocityContext> ctxData = new HashMap<>();
        String superEntityClass = getSuperClassName(config.getSuperEntityClass());
        String superMapperClass = getSuperClassName(config.getSuperMapperClass());
        String superServiceClass = getSuperClassName(config.getSuperServiceClass());
        String superServiceImplClass = getSuperClassName(config.getSuperServiceImplClass());
        String superEnumClass = getSuperClassName(config.getSuperEnumClass());
        String superControllerClass = getSuperClassName(config.getSuperControllerClass());
        String date = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
        for (TableInfo tableInfo : tableList) {
            VelocityContext ctx = new VelocityContext();
            ctx.put("package", packageInfo);
            ctx.put("table", tableInfo);
            ctx.put("entity", tableInfo.getEntityName());
            ctx.put("addTabeName", Boolean.valueOf(false));
            ctx.put("idGenType", config.getIdType());
            ctx.put("superEntityClassPackage", config.getSuperEntityClass());
            ctx.put("superEntityClass", superEntityClass);
            ctx.put("superMapperClassPackage", config.getSuperMapperClass());
            ctx.put("superMapperClass", superMapperClass);
            ctx.put("superServiceClassPackage", config.getSuperServiceClass());
            ctx.put("superServiceClass", superServiceClass);
            ctx.put("superServiceImplClassPackage", config.getSuperServiceImplClass());
            ctx.put("superServiceImplClass", superServiceImplClass);
            ctx.put("superEnumClassPackage", config.getSuperEnumClass());
            ctx.put("superEnumClass", superEnumClass);
            ctx.put("superControllerClassPackage", config.getSuperControllerClass());
            ctx.put("superControllerClass", superControllerClass);
            ctx.put("enableCache", Boolean.valueOf(isEnableCache()));
            ctx.put("author", System.getProperty("user.name"));
            ctx.put("activeRecord", Boolean.valueOf(isActiveRecord()));
            ctx.put("date", date);
            ctxData.put(tableInfo.getEntityName(), ctx);
        }
        return ctxData;
    }

    private String getSuperClassName(String classPath) {
        if (StringUtils.isBlank(classPath))
            return null;
        return classPath.substring(classPath.lastIndexOf(".") + 1);
    }

    private void mkdirs(Map<String, String> pathInfo) {
        for (Map.Entry<String, String> entry : pathInfo.entrySet()) {
            File dir = new File(entry.getValue());
            if (!dir.exists()) {
                boolean result = dir.mkdirs();
                if (result)
                    this.log.info("[" + (String)entry.getValue() + "]");
            }
        }
    }

    private void initOutputFiles() {
        this.outputFiles = new HashMap<>();
        Map<String, String> pathInfo = this.config.getPathInfo();
        this.outputFiles.put("Enum", (String)pathInfo.get("enum_path") + ConstVal.ENUM_NAME);
        this.outputFiles.put("Entity", (String)pathInfo.get("entity_path") + ConstVal.DO_NAME);
        this.outputFiles.put("Mapper", (String)pathInfo.get("mapper_path") + ConstVal.MAPPER_NAME);
        this.outputFiles.put("Xml", (String)pathInfo.get("xml_path") + ConstVal.XML_NAME);
        this.outputFiles.put("Repository", (String)pathInfo.get("serivce_path") + ConstVal.SERVICE_NAME);
        this.outputFiles.put("Repository", (String)pathInfo.get("serviceimpl_path") + ConstVal.SERVICEIMPL_NAME);
        this.outputFiles.put("Controller", (String)pathInfo.get("controller_path") + ConstVal.CONTROLLER_NAME);
    }

    private void batchOutput(String entityName, VelocityContext context) {
        try {
            String entityFile = String.format(this.outputFiles.get("Entity"), new Object[] { entityName });
            String enumFile = String.format(this.outputFiles.get("Enum"), new Object[] { entityName });
            String mapperFile = String.format(this.outputFiles.get("Mapper"), new Object[] { entityName });
            String xmlFile = String.format(this.outputFiles.get("Xml"), new Object[] { entityName });
            String serviceFile = String.format(this.outputFiles.get("Repository"), new Object[] { entityName });
            String implFile = String.format(this.outputFiles.get("Repository"), new Object[] { entityName });
            String controllerFile = String.format(this.outputFiles.get("Controller"), new Object[] { entityName });
            TemplateConfig template = this.config.getTemplate();
            TableInfo tableInfo = (TableInfo)context.get("table");
            if (isCreate(entityFile, false))
                vmToFile(context, template.getEntity(), entityFile);
            if (isCreate(mapperFile, false))
                vmToFile(context, template.getMapper(), mapperFile);
            if (isCreate(xmlFile, false))
                vmToFile(context, template.getXml(), xmlFile);
            if (isCreate(implFile, false))
                vmToFile(context, template.getServiceImpl(), implFile);
            if (isCreate(enumFile, false) && tableInfo.getHasEnum())
                vmToFile(context, template.getIenum(), enumFile);
        } catch (IOException e) {
        }
    }

    private void vmToFile(VelocityContext context, String templatePath, String outputFile) throws IOException {
        VelocityEngine velocity = getVelocityEngine();
        Template template = velocity.getTemplate(templatePath, ConstVal.UTF8);
        FileOutputStream fos = new FileOutputStream(outputFile);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, ConstVal.UTF8));
        template.merge((Context)context, writer);
        writer.close();
        this.log.info("+ templatePath + "+ outputFile);
    }

    private VelocityEngine getVelocityEngine() {
        if (this.engine == null) {
            Properties p = new Properties();
            p.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            p.setProperty("file.resource.loader.path", "");
            p.setProperty("ISO-8859-1", ConstVal.UTF8);
            p.setProperty("input.encoding", ConstVal.UTF8);
            p.setProperty("output.encoding", ConstVal.UTF8);
            p.setProperty("file.resource.loader.unicode", "true");
            this.engine = new VelocityEngine(p);
        }
        return this.engine;
    }

    private boolean isCreate(String filePath, boolean isFileOverride) {
        File file = new File(filePath);
        return (!file.exists() || isFileOverride);
    }
}

