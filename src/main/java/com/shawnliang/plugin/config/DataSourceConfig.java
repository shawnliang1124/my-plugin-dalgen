package com.shawnliang.plugin.config;

import com.shawnliang.plugin.rule.DbType;
import com.shawnliang.plugin.util.StringUtils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.maven.plugins.annotations.Parameter;

public class DataSourceConfig {
  @Parameter(defaultValue = "mysql")
  private DbType dbType;

  @Parameter(required = true)
  private String url;

  @Parameter(required = true)
  private String driverName;

  @Parameter(required = true)
  private String username;

  @Parameter(required = true)
  private String password;

  @Parameter
  private String dbName;

  public DbType getDbType() {
      if (null == this.dbType) {
          if (this.driverName.contains("mysql")) {
              this.dbType = DbType.MYSQL;
          } else if (this.driverName.contains("oracle")) {
              this.dbType = DbType.ORACLE;
          }
      }
    return this.dbType;
  }

  public Connection getConn() {
    Connection conn = null;
    try {
      Class.forName(this.driverName);
      conn = DriverManager.getConnection(this.url, this.username, this.password);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return conn;
  }

  public String getDBName() {
      if (StringUtils.isBlank(this.dbName)) {
          return this.url.substring(this.url.lastIndexOf("/") + 1);
      }
    return this.dbName;
  }
}
