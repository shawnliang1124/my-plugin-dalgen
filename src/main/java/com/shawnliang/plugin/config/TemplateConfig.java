package com.shawnliang.plugin.config;

import org.apache.maven.plugins.annotations.Parameter;

public class TemplateConfig {
  @Parameter(defaultValue = "/template/entity.java.vm")
  private String entity;

  @Parameter(defaultValue = "/template/service.java.vm")
  private String service;

  @Parameter(defaultValue = "/template/serviceImpl.java.vm")
  private String serviceImpl;

  @Parameter(defaultValue = "/template/mapper.java.vm")
  private String mapper;

  @Parameter(defaultValue = "/template/mapper.xml.vm")
  private String xml;

  @Parameter(defaultValue = "/template/controller.java.vm")
  private String controller;

  @Parameter(defaultValue = "/template/ienum.java.vm")
  private String ienum;

  public String getIenum() {
      if (this.ienum == null) {
          return "/template/ienum.java.vm";
      }
    return this.ienum;
  }

  public String getEntity() {
      if (this.entity == null) {
          return "/template/entity.java.vm";
      }
    return this.entity;
  }

  public String getService() {
      if (this.service == null) {
          return "/template/service.java.vm";
      }
    return this.service;
  }

  public String getServiceImpl() {
      if (this.serviceImpl == null) {
          return "/template/serviceImpl.java.vm";
      }
    return this.serviceImpl;
  }

  public String getMapper() {
      if (this.mapper == null) {
          return "/template/mapper.java.vm";
      }
    return this.mapper;
  }

  public String getXml() {
      if (this.xml == null) {
          return "/template/mapper.xml.vm";
      }
    return this.xml;
  }

  public String getController() {
      if (this.controller == null) {
          return "/template/controller.java.vm";
      }
    return this.controller;
  }
}
