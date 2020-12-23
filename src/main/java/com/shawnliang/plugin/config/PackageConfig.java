package com.shawnliang.plugin.config;

import com.shawnliang.plugin.util.StringUtils;
import org.apache.maven.plugins.annotations.Parameter;

public class PackageConfig {
  @Parameter(defaultValue = "com.shawnliang")
  private String parent;

  @Parameter(defaultValue = "entity")
  private String entity;

  @Parameter(defaultValue = "enums")
  private String enums;

  @Parameter(defaultValue = "service")
  private String service;

  @Parameter(defaultValue = "service.impl")
  private String serviceImpl;

  @Parameter(defaultValue = "mapper")
  private String mapper;

  @Parameter(defaultValue = "mapper.xml")
  private String xml;

  private String modelName;

  @Parameter(defaultValue = "controller")
  private String controller;

  public String getParent() {
      if (StringUtils.isBlank(this.parent)) {
          return "com.shawnliang";
      }
    return this.parent;
  }

  public String getEntity() {
      if (StringUtils.isBlank(this.entity)) {
          return "dataobject";
      }
    return this.entity;
  }

  public String getEnums() {
      if (StringUtils.isBlank(this.enums)) {
          return "enums";
      }
    return this.enums;
  }

  public String getService() {
      if (StringUtils.isBlank(this.service)) {
          return "repository";
      }
    return this.service;
  }

  public String getServiceImpl() {
      if (StringUtils.isBlank(this.serviceImpl)) {
          return "repository";
      }
    return this.serviceImpl;
  }

  public String getMapper() {
      if (StringUtils.isBlank(this.mapper)) {
          return "mapper";
      }
    return this.mapper;
  }

  public String getXml() {
      if (StringUtils.isBlank(this.xml)) {
          return "mapper";
      }
    return this.xml;
  }

  public String getController() {
      if (StringUtils.isBlank(this.controller)) {
          return "controller";
      }
    return this.controller;
  }

  public String getModelName() {
    return this.modelName;
  }

  public void setModelName(String modelName) {
    this.modelName = modelName;
  }
}
