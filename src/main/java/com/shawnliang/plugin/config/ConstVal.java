package com.shawnliang.plugin.config;

import java.io.File;
import java.nio.charset.Charset;

public class ConstVal {
  public static final String MODULENAME = "ModuleName";

  public static final String ENTITY = "Entity";

  public static final String ENUM = "Enum";

  public static final String SERIVCE = "Repository";

  public static final String SERVICEIMPL = "Repository";

  public static final String MAPPER = "Mapper";

  public static final String XML = "Xml";

  public static final String CONTROLLER = "Controller";

  public static final String ENTITY_PATH = "entity_path";

  public static final String ENUM_PATH = "enum_path";

  public static final String SERIVCE_PATH = "serivce_path";

  public static final String SERVICEIMPL_PATH = "serviceimpl_path";

  public static final String MAPPER_PATH = "mapper_path";

  public static final String XML_PATH = "xml_path";

  public static final String CONTROLLER_PATH = "controller_path";

  public static final String JAVA_TMPDIR = "java.io.tmpdir";

  public static final String UTF8 = Charset.forName("UTF-8").name();

  public static final String UNDERLINE = "_";

  public static final String JAVA_SUFFIX = ".java";

  public static final String XML_SUFFIX = ".xml";

  public static final String TEMPLATE_ENTITY = "/template/entity.java.vm";

  public static final String TEMPLATE_MAPPER = "/template/mapper.java.vm";

  public static final String TEMPLATE_XML = "/template/mapper.xml.vm";

  public static final String TEMPLATE_SERVICE = "/template/service.java.vm";

  public static final String TEMPLATE_SERVICEIMPL = "/template/serviceImpl.java.vm";

  public static final String TEMPLATE_CONTROLLER = "/template/controller.java.vm";

  public static final String TEMPLATE_ENUM = "/template/ienum.java.vm";

  public static final String ENTITY_NAME = File.separator + "%s" + ".java";

  public static final String DO_NAME = File.separator + "%sDO" + ".java";

  public static final String ENUM_NAME = File.separator + "%s" + "Enum" + ".java";

  public static final String MAPPER_NAME = File.separator + "%s" + "Mapper" + ".java";

  public static final String XML_NAME = File.separator + "%s" + "Mapper" + ".xml";

  public static final String SERVICE_NAME = File.separator + "%s" + "Repository" + ".java";

  public static final String SERVICEIMPL_NAME = File.separator + "%s" + "Repository" + ".java";

  public static final String CONTROLLER_NAME = File.separator + "%s" + "Controller" + ".java";

  public static final String VM_LOADPATH_KEY = "file.resource.loader.class";

  public static final String VM_LOADPATH_VALUE = "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader";

  public static final String SUPERD_MAPPER_CLASS = "com.baomidou.mybatisplus.core.mapper.BaseMapper";

  public static final String SUPERD_ENTITY_CLASS = "com.baomidou.mybatisplus.extension.activerecord.Model";

  public static final String SUPERD_ENUM_CLASS = "com.baomidou.mybatisplus.core.enums.IEnum";

  public static final String SUPERD_SERVICE_CLASS = "com.baomidou.mybatisplus.extension.service.impl.ServiceImpl";

  public static final String SUPERD_SERVICEIMPL_CLASS = "com.baomidou.mybatisplus.extension.service.impl.ServiceImpl";

  public static final String SUPERD_CONTROLLER_CLASS = "";

  public static final String JAVA_SUB_DIR = "java";

  public static final String RESOURCE_SUB_DIR = "resources";
}
