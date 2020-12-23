package com.shawnliang.plugin.config;

import com.shawnliang.plugin.rule.IdStrategy;
import com.shawnliang.plugin.rule.NamingStrategy;
import com.shawnliang.plugin.util.StringUtils;
import org.apache.maven.plugins.annotations.Parameter;

public class StrategyConfig {
  @Parameter(defaultValue = "underline_to_camel")
  private NamingStrategy naming;

  private NamingStrategy fieldNaming;

  @Parameter
  private String tablePrefix;

  @Parameter(defaultValue = "ID_WORKER")
  private IdStrategy idGenType;

  @Parameter(defaultValue = "com.baomidou.mybatisplus.extension.activerecord.Model")
  private String superEntityClass;

  @Parameter(defaultValue = "com.baomidou.mybatisplus.core.mapper.BaseMapper")
  private String superMapperClass;

  @Parameter(defaultValue = "com.baomidou.mybatisplus.extension.service.impl.ServiceImpl")
  private String superServiceClass;

 // @Parameter(defaultValue = "com.baomidou.mybatisplus.extension.service.impl.ServiceImpl")
  private String superServiceImplClass;

  @Parameter
  private String superControllerClass;

  @Parameter
  private String[] include = null;

  @Parameter
  private String[] exclude = null;

  public void setSuperEntityClass(String superEntityClass) {
    this.superEntityClass = superEntityClass;
  }

  public NamingStrategy getNaming() {
      if (this.naming == null) {
          return NamingStrategy.underline_to_camel;
      }
    return this.naming;
  }

  public NamingStrategy getFieldNaming() {
      if (this.fieldNaming == null) {
          return NamingStrategy.underline_to_camel;
      }
    return this.fieldNaming;
  }

  public String getTablePrefix() {
    return this.tablePrefix;
  }

  public IdStrategy getIdGenType() {
    return this.idGenType;
  }

  public String[] getInclude() {
    return this.include;
  }

  public String[] getExclude() {
    return this.exclude;
  }

  public String getSuperServiceClass() {
    return StringUtils.isBlank(this.superServiceClass) ? this.superServiceClass : "com.baomidou.mybatisplus.extension.service.impl.ServiceImpl";
  }

  public String getSuperEntityClass() {
    return StringUtils.isBlank(this.superEntityClass) ? this.superEntityClass : "com.baomidou.mybatisplus.extension.activerecord.Model";
  }

  public String getSuperMapperClass() {
    return StringUtils.isBlank(this.superMapperClass) ? this.superMapperClass : "com.baomidou.mybatisplus.core.mapper.BaseMapper";
  }

  public String getSuperServiceImplClass() {
    return StringUtils.isBlank(this.superServiceImplClass) ? this.superServiceImplClass : "";
  }

  public String getSuperControllerClass() {
    return StringUtils.isBlank(this.superControllerClass) ? this.superControllerClass : "";
  }
}
