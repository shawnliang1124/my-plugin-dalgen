package com.shawnliang.plugin.rule;

public enum DbType {
  MYSQL("mysql"),
  ORACLE("oracle");

  private String value;

  DbType(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }
}
