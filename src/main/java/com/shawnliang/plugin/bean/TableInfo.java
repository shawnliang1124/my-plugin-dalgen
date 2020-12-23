package com.shawnliang.plugin.bean;

import com.shawnliang.plugin.util.StringUtils;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TableInfo {
  private String name;

  private String comment;

  private String entityName;

  private String doName;

  private String mapperName;

  private String xmlName;

  private String serviceName;

  private String serviceImplName;

  private String controllerName;

  private String enumName;

  private List<TableField> fields;

  private String fieldNames;

  private boolean hasDate;

  private boolean hasEnum;

  private Set<String> timeTypes;

  public boolean getHasEnum() {
      if (this.fields == null) {
          return false;
      }
    for (TableField field : this.fields) {
        if (field.getIsEnum()) {
            return true;
        }
    }
    return false;
  }

  public String getName() {
    return this.name;
  }

  public String getDoName() {
    return this.entityName + "DO";
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getComment() {
    return this.comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getEntityName() {
    return this.entityName;
  }

  public void setEntityName(String entityName) {
    this.entityName = entityName;
  }

  public String getMapperName() {
    return this.mapperName;
  }

  public void setMapperName(String mapperName) {
    this.mapperName = mapperName;
  }

  public String getXmlName() {
    return this.xmlName;
  }

  public void setXmlName(String xmlName) {
    this.xmlName = xmlName;
  }

  public String getServiceName() {
    return this.serviceName;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  public String getServiceImplName() {
    return this.serviceImplName;
  }

  public void setServiceImplName(String serviceImplName) {
    this.serviceImplName = serviceImplName;
  }

  public String getEnumName() {
    return this.enumName;
  }

  public void setEnumName(String enumName) {
    this.enumName = enumName;
  }

  public String getControllerName() {
    return this.controllerName;
  }

  public void setControllerName(String controllerName) {
    this.controllerName = controllerName;
  }

  public List<TableField> getFields() {
    return this.fields;
  }

  public void setFields(List<TableField> fields) {
    this.fields = fields;
  }

  public String getFieldNames() {
    if (StringUtils.isBlank(this.fieldNames)) {
      StringBuilder names = new StringBuilder();
      for (int i = 0; i < this.fields.size(); i++) {
        TableField fd = this.fields.get(i);
        if (i == this.fields.size() - 1) {
          names.append(cov2col(fd));
        } else {
          names.append(cov2col(fd)).append(", ");
        }
      }
      this.fieldNames = names.toString();
    }
    return this.fieldNames;
  }

  public boolean isHasDate() {
    for (TableField fieldInfo : this.fields) {
      if (fieldInfo.getPropertyType().equals("LocalDateTime") || fieldInfo
        .getPropertyType().equals("LocalDate") || fieldInfo
        .getPropertyType().equals("LocalTime")) {
        this.hasDate = true;
        break;
      }
    }
    return this.hasDate;
  }

  public Set<String> getTimeTypes() {
    Set<String> set = new HashSet<>();
    for (TableField fieldInfo : this.fields) {
        if (fieldInfo.getPropertyType().equals("LocalDateTime") || fieldInfo
                .getPropertyType().equals("LocalDate") || fieldInfo
                .getPropertyType().equals("LocalTime")) {
            set.add(fieldInfo.getPropertyType());
        }
    }
    return set;
  }

  public void setTimeType(Set<String> timeTypes) {
    this.timeTypes = timeTypes;
  }

  private String cov2col(TableField field) {
      if (null != field) {
          return field.isConvert() ? (field.getName() + " AS " + field.getPropertyName())
                  : field.getName();
      }
    return "";
  }
}
