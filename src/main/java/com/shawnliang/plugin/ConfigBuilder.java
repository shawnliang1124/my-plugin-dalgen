package com.shawnliang.plugin;

import com.shawnliang.plugin.bean.TableField;
import com.shawnliang.plugin.bean.TableInfo;
import com.shawnliang.plugin.config.DataSourceConfig;
import com.shawnliang.plugin.config.PackageConfig;
import com.shawnliang.plugin.config.StrategyConfig;
import com.shawnliang.plugin.config.TemplateConfig;
import com.shawnliang.plugin.rule.DbType;
import com.shawnliang.plugin.rule.IdStrategy;
import com.shawnliang.plugin.rule.NamingStrategy;
import com.shawnliang.plugin.rule.QuerySQL;
import com.shawnliang.plugin.util.StringUtils;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConfigBuilder {
  private Connection connection;

  private QuerySQL querySQL;

  private String superEntityClass;

  private String superMapperClass;

  private String superServiceClass;

  private String superEnumClass;

  private String superServiceImplClass;

  private String superControllerClass;

  private String idType;

  private List<TableInfo> tableInfoList;

  private Map<String, String> packageInfo;

  private Map<String, String> pathInfo;

  private TemplateConfig template;

  public ConfigBuilder(PackageConfig packageConfig, DataSourceConfig dataSourceConfig, StrategyConfig strategyConfig, TemplateConfig template, String outputDir) {
    handlerPackage(outputDir, packageConfig, dataSourceConfig.getDBName());
    handlerDataSource(dataSourceConfig);
    handlerStrategy(strategyConfig);
    this.template = template;
  }

  public Map<String, String> getPackageInfo() {
    return this.packageInfo;
  }

  public Map<String, String> getPathInfo() {
    return this.pathInfo;
  }

  public String getSuperEntityClass() {
    return StringUtils.isNotBlank(this.superEntityClass) ? this.superEntityClass : "com.baomidou.mybatisplus.extension.activerecord.Model";
  }

  public String getSuperMapperClass() {
    return StringUtils.isNotBlank(this.superMapperClass) ? this.superMapperClass : "com.baomidou.mybatisplus.core.mapper.BaseMapper";
  }

  public String getSuperServiceClass() {
    return StringUtils.isNotBlank(this.superServiceClass) ? this.superServiceClass : "com.baomidou.mybatisplus.extension.service.impl.ServiceImpl";
  }

  public String getSuperEnumClass() {
    return StringUtils.isNotBlank(this.superEnumClass) ? this.superEntityClass : "com.baomidou.mybatisplus.core.enums.IEnum";
  }

  public String getSuperServiceImplClass() {
    return StringUtils.isNotBlank(this.superServiceImplClass) ? this.superServiceImplClass : "com.baomidou.mybatisplus.extension.service.impl.ServiceImpl";
  }

  public String getSuperControllerClass() {
    return StringUtils.isNotBlank(this.superControllerClass) ? this.superControllerClass : "";
  }

  public String getIdType() {
    return this.idType;
  }

  public List<TableInfo> getTableInfoList() {
    return this.tableInfoList;
  }

  public TemplateConfig getTemplate() {
    return (this.template == null) ? new TemplateConfig() : this.template;
  }

  private void handlerPackage(String outputDir, PackageConfig config, String dbName) {
    this.packageInfo = new HashMap<>();
    this.packageInfo.put("Enum", joinPackage(config.getParent(), config.getModelName(), config.getEnums()));
    this.packageInfo.put("Entity", joinPackage(config.getParent(), config.getModelName(), config.getEntity(), dbName));
    this.packageInfo.put("Mapper", joinPackage(config.getParent(), config.getModelName(), config.getMapper(), dbName));
    this.packageInfo.put("Xml", joinPackage(config.getParent(), config.getModelName(), config.getXml(), dbName));
    this.packageInfo.put("Repository", joinPackage(config.getParent(), config.getModelName(), config.getService(), dbName));
    this.packageInfo.put("Repository", joinPackage(config.getParent(), config.getModelName(), config.getServiceImpl(), dbName));
    this.packageInfo.put("Controller", joinPackage(config.getParent(), config.getModelName(), config.getController()));
    this.pathInfo = new HashMap<>();
    this.pathInfo.put("enum_path", joinPath(outputDir, "java", this.packageInfo.get("Enum")));
    this.pathInfo.put("entity_path", joinPath(outputDir, "java", this.packageInfo.get("Entity")));
    this.pathInfo.put("mapper_path", joinPath(outputDir, "java", this.packageInfo.get("Mapper")));
    this.pathInfo.put("xml_path", joinPath(outputDir, "resources", this.packageInfo.get("Xml")));
    this.pathInfo.put("serivce_path", joinPath(outputDir, "java", this.packageInfo.get("Repository")));
    this.pathInfo.put("serviceimpl_path", joinPath(outputDir, "java", this.packageInfo.get("Repository")));
    this.pathInfo.put("controller_path", joinPath(outputDir, "java", this.packageInfo.get("Controller")));
  }

  private void handlerDataSource(DataSourceConfig config) {
    this.connection = config.getConn();
    this.querySQL = getQuerySQL(config.getDbType());
  }

  private void handlerStrategy(StrategyConfig config) {
    processTypes(config);
    this.tableInfoList = getTablesInfo(config);
  }

  private void processTypes(StrategyConfig config) {
    this.superServiceClass = config.getSuperServiceClass();
    this.superServiceImplClass = config.getSuperServiceImplClass();
    this.superMapperClass = config.getSuperMapperClass();
    this.superEntityClass = config.getSuperEntityClass();
    this.superControllerClass = config.getSuperControllerClass();
    if (config.getIdGenType() == IdStrategy.auto) {
      this.idType = IdStrategy.auto.getValue();
    } else if (config.getIdGenType() == IdStrategy.input) {
      this.idType = IdStrategy.input.getValue();
    } else if (config.getIdGenType() == IdStrategy.uuid) {
      this.idType = IdStrategy.uuid.getValue();
    } else {
      this.idType = IdStrategy.id_worker.getValue();
    }
  }

  private List<TableInfo> processTable(List<TableInfo> tableList, NamingStrategy strategy, String tablePrefix) {
    for (TableInfo tableInfo : tableList) {
      tableInfo.setEntityName(NamingStrategy.capitalFirst(processName(tableInfo.getName(), strategy, tablePrefix)));
      tableInfo.setMapperName(tableInfo.getEntityName() + "Mapper");
      tableInfo.setXmlName(tableInfo.getMapperName());
      tableInfo.setServiceName(tableInfo.getEntityName() + "Repository");
      tableInfo.setServiceImplName(tableInfo.getEntityName() + "Repository");
      tableInfo.setControllerName(tableInfo.getEntityName() + "Controller");
      tableInfo.setEnumName(tableInfo.getEntityName() + "Enum");
    }
    return tableList;
  }

  private List<TableInfo> getTablesInfo(StrategyConfig config) {
    boolean isInclude = (null != config.getInclude() && (config.getInclude()).length > 0);
    boolean isExclude = (null != config.getExclude() && (config.getExclude()).length > 0);
    if (isInclude && isExclude)
      throw new RuntimeException("<strategy> <include> <exclude> ");
    List<TableInfo> tableList = new ArrayList<>();
    Set<String> notExistTables = new HashSet<>();
    NamingStrategy strategy = config.getNaming();
    PreparedStatement pstate = null;
    try {
      pstate = this.connection.prepareStatement(this.querySQL.getTableCommentsSql());
      ResultSet results = pstate.executeQuery();
      while (results.next()) {
        String tableName = results.getString(this.querySQL.getTableName());
        if (StringUtils.isNotBlank(tableName)) {
          String tableComment = results.getString(this.querySQL.getTableComment());
          TableInfo tableInfo = new TableInfo();
          if (isInclude) {
            for (String includeTab : config.getInclude()) {
              if (includeTab.equalsIgnoreCase(tableName)) {
                tableInfo.setName(tableName);
                tableInfo.setComment(tableComment);
              } else {
                notExistTables.add(includeTab);
              }
            }
          } else if (isExclude) {
            for (String excludeTab : config.getExclude()) {
              if (!excludeTab.equalsIgnoreCase(tableName)) {
                tableInfo.setName(tableName);
                tableInfo.setComment(tableComment);
              } else {
                notExistTables.add(excludeTab);
              }
            }
          } else {
            tableInfo.setName(tableName);
            tableInfo.setComment(tableComment);
          }
          if (StringUtils.isNotBlank(tableInfo.getName())) {
            List<TableField> fieldList = getListFields(tableInfo.getName(), strategy);
            tableInfo.setFields(fieldList);
            tableList.add(tableInfo);
          }
          continue;
        }
        System.err.println("");
      }
      for (TableInfo tabInfo : tableList)
        notExistTables.remove(tabInfo.getName());
      if (notExistTables.size() > 0)
        System.err.println("" + notExistTables + " ");
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (pstate != null)
          pstate.close();
        if (this.connection != null)
          this.connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return processTable(tableList, strategy, config.getTablePrefix());
  }

  private List<TableField> getListFields(String tableName, NamingStrategy strategy) throws SQLException {
    boolean havedId = false;
    PreparedStatement pstate = this.connection.prepareStatement(String.format(this.querySQL.getTableFieldsSql(), new Object[] { tableName }));
    ResultSet results = pstate.executeQuery();
    List<TableField> fieldList = new ArrayList<>();
    while (results.next()) {
      TableField field = new TableField();
      String key = results.getString(this.querySQL.getFieldKey());
      boolean isId = (StringUtils.isNotBlank(key) && key.toUpperCase().equals("PRI"));
      if (isId && !havedId) {
        field.setKeyFlag(true);
        havedId = true;
      } else {
        field.setKeyFlag(false);
      }
      field.setExtra(results.getString(this.querySQL.getExtra()));
      field.setName(results.getString(this.querySQL.getFieldName()));
      field.setType(results.getString(this.querySQL.getFieldType()));
      field.setPropertyName(processName(field.getName(), strategy));
      field.setPropertyType(processFiledType(field.getType()));
      field.setComment(results.getString(this.querySQL.getFieldComment()));
      field.setEnum(StringUtils.isNotBlank(field.getComment()));
      fieldList.add(field);
    }
    return fieldList;
  }

  private String joinPath(String parentDir, String subDir, String packageName) {
    if (StringUtils.isBlank(parentDir))
      parentDir = System.getProperty("java.io.tmpdir");
    parentDir = parentDir + File.separator + subDir;
    if (!parentDir.endsWith(File.separator))
      parentDir = parentDir + File.separator;
    packageName = packageName.replaceAll("\\.", "\\" + File.separator);
    return parentDir + packageName;
  }

  private String joinPackage(String parent, String modelName, String subPackage) {
    if (StringUtils.isBlank(parent))
      return subPackage;
    if (StringUtils.isNotBlank(modelName))
      parent = parent + "." + modelName;
    return parent + "." + subPackage;
  }

  private String joinPackage(String parent, String modelName, String subPackage, String dbName) {
    if (StringUtils.isBlank(parent))
      return subPackage;
    if (StringUtils.isNotBlank(modelName))
      parent = parent + "." + modelName;
    return parent + ".dmr." + dbName + "." + subPackage;
  }

  private String processFiledType(String type) {
    if (QuerySQL.MYSQL == this.querySQL)
      return processMySqlType(type);
    if (QuerySQL.ORACLE == this.querySQL)
      return processOracleType(type);
    return null;
  }

  private String processName(String name, NamingStrategy strategy) {
    return processName(name, strategy, null);
  }

  private String processName(String name, NamingStrategy strategy, String tablePrefix) {
    String propertyName = "";
    if (strategy == NamingStrategy.remove_prefix_and_camel) {
      propertyName = NamingStrategy.removePrefixAndCamel(name, tablePrefix);
    } else if (strategy == NamingStrategy.underline_to_camel) {
      propertyName = NamingStrategy.underlineToCamel(name);
    } else if (strategy == NamingStrategy.remove_prefix) {
      propertyName = NamingStrategy.removePrefix(name, tablePrefix);
    } else {
      propertyName = name;
    }
    return propertyName;
  }

  private String processMySqlType(String type) {
    String t = type.toLowerCase();
    if (t.contains("char") || t.contains("text"))
      return "String";
    if (t.contains("bigint"))
      return "Long";
    if (t.contains("int")) {
      if (t.contains("tinyint(1)"))
        return "Boolean";
      return "Integer";
    }
    if (t.contains("date") || t.contains("time") || t.contains("year")) {
      if ("date".equals(t))
        return "LocalDate";
      if ("time".equals(t))
        return "LocalTime";
      return "LocalDateTime";
    }
    if (t.contains("text"))
      return "String";
    if (t.contains("bit"))
      return "Boolean";
    if (t.contains("decimal"))
      return "BigDecimal";
    if (t.contains("blob"))
      return "byte[]";
    if (t.contains("float"))
      return "Float";
    if (t.contains("double"))
      return "Double";
    if (t.contains("json") || t.contains("enum"))
      return "String";
    return "String";
  }

  private String processOracleType(String type) {
    String t = type.toUpperCase();
    if (t.contains("CHAR"))
      return "String";
    if (t.contains("DATE") || t.contains("TIMESTAMP"))
      return "Date";
    if (t.contains("NUMBER")) {
      if (t.matches("NUMBER\\(+\\d{1}+\\)"))
        return "Integer";
      if (t.matches("NUMBER\\(+\\d{2}+\\)"))
        return "Long";
      return "Double";
    }
    if (t.contains("FLOAT"))
      return "Float";
    if (t.contains("BLOB"))
      return "Object";
    if (t.contains("RAW"))
      return "byte[]";
    return "String";
  }

  private QuerySQL getQuerySQL(DbType dbType) {
    for (QuerySQL qs : QuerySQL.values()) {
      if (qs.getDbType().equals(dbType.getValue()))
        return qs;
    }
    return QuerySQL.MYSQL;
  }
}
