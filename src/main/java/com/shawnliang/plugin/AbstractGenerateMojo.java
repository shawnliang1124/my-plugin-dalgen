package com.shawnliang.plugin;

import com.shawnliang.plugin.config.DataSourceConfig;
import com.shawnliang.plugin.config.PackageConfig;
import com.shawnliang.plugin.config.StrategyConfig;
import com.shawnliang.plugin.config.TemplateConfig;
import java.io.File;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Parameter;

public abstract class AbstractGenerateMojo extends AbstractMojo {
  @Parameter(required = true)
  private DataSourceConfig dataSource;

  @Parameter
  private StrategyConfig strategy;

  @Parameter
  private PackageConfig packageInfo;

  @Parameter
  private TemplateConfig template;

  @Parameter(defaultValue = "${project.basedir}")
  private String baseDir;

  @Parameter(defaultValue = "${artifactId}")
  private String projectName;

  @Parameter(defaultValue = "true")
  private boolean fileOverride;

  @Parameter(defaultValue = "false")
  private boolean open;

  @Parameter(defaultValue = "false")
  private boolean enableCache;

  @Parameter(defaultValue = "author")
  private String author;

  @Parameter(defaultValue = "false")
  private boolean activeRecord;

  protected ConfigBuilder config;

  protected Log log = getLog();

  protected void initConfig() {
    if (null == this.config) {
        if (this.packageInfo == null) {
            this.packageInfo = new PackageConfig();
        }
      this.packageInfo.setModelName(getProjectNameToUnderLine());
        if (this.strategy == null) {
            this.strategy = new StrategyConfig();
        }
      this.config = new ConfigBuilder(this.packageInfo, this.dataSource, this.strategy, this.template, getBaseDir());
    }
  }

  public String getBaseDir() {
    return this.baseDir + File.separator + "src" + File.separator + "main";
  }

  public String getAuthor() {
    return this.author;
  }

  public boolean isFileOverride() {
    return this.fileOverride;
  }

  public boolean isOpen() {
    return this.open;
  }

  public boolean isEnableCache() {
    return this.enableCache;
  }

  public boolean isActiveRecord() {
    return this.activeRecord;
  }

  public String getProjectName() {
    return this.projectName;
  }

  public String getProjectNameToUnderLine() {
      if (this.projectName.startsWith("huixian-")) {
          this.projectName = this.projectName.replace("huixian-", "");
      }
    return this.projectName.replaceAll("-", ".");
  }
}
