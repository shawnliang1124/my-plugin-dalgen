package com.shawnliang.plugin.rule;

public enum QuerySQL {
  MYSQL("mysql", "show tables", "show table status", "show full fields from %s", "NAME", "COMMENT", "FIELD", "TYPE", "COMMENT", "KEY", "EXTRA"),
  ORACLE("oracle", "SELECT * FROM USER_TABLES", "SELECT * FROM USER_TAB_COMMENTS", "SELECT A.COLUMN_NAME, CASE WHEN A.DATA_TYPE='NUMBER' THEN (CASE WHEN A.DATA_PRECISION IS NULL THEN A.DATA_TYPE WHEN NVL(A.DATA_SCALE, 0) > 0 THEN A.DATA_TYPE||'('||A.DATA_PRECISION||','||A.DATA_SCALE||')' ELSE A.DATA_TYPE||'('||A.DATA_PRECISION||')' END) ELSE A.DATA_TYPE END DATA_TYPE, B.COMMENTS,DECODE(C.POSITION, '1', 'PRI') KEY FROM USER_TAB_COLUMNS A INNER JOIN USER_COL_COMMENTS B ON A.TABLE_NAME = B.TABLE_NAME AND A.COLUMN_NAME = B.COLUMN_NAME LEFT JOIN USER_CONSTRAINTS D ON D.TABLE_NAME = A.TABLE_NAME AND D.CONSTRAINT_TYPE = 'P' LEFT JOIN USER_CONS_COLUMNS C ON C.CONSTRAINT_NAME = D.CONSTRAINT_NAME AND C.COLUMN_NAME=A.COLUMN_NAME WHERE A.TABLE_NAME = '%s' ", "TABLE_NAME", "COMMENTS", "COLUMN_NAME", "DATA_TYPE", "COMMENTS", "KEY", "");

  private final String dbType;

  private final String tablesSql;

  private final String tableCommentsSql;

  private final String tableFieldsSql;

  private final String tableName;

  private final String tableComment;

  private final String fieldName;

  private final String fieldType;

  private final String fieldComment;

  private final String fieldKey;

  private final String extra;

  QuerySQL(String dbType, String tablesSql, String tableCommentsSql, String tableFieldsSql, String tableName, String tableComment, String fieldName, String fieldType, String fieldComment, String fieldKey, String extra) {
    this.dbType = dbType;
    this.tablesSql = tablesSql;
    this.tableCommentsSql = tableCommentsSql;
    this.tableFieldsSql = tableFieldsSql;
    this.tableName = tableName;
    this.tableComment = tableComment;
    this.fieldName = fieldName;
    this.fieldType = fieldType;
    this.fieldComment = fieldComment;
    this.fieldKey = fieldKey;
    this.extra = extra;
  }

  public String getDbType() {
    return this.dbType;
  }

  public String getTablesSql() {
    return this.tablesSql;
  }

  public String getTableCommentsSql() {
    return this.tableCommentsSql;
  }

  public String getTableFieldsSql() {
    return this.tableFieldsSql;
  }

  public String getTableName() {
    return this.tableName;
  }

  public String getTableComment() {
    return this.tableComment;
  }

  public String getFieldName() {
    return this.fieldName;
  }

  public String getFieldType() {
    return this.fieldType;
  }

  public String getFieldComment() {
    return this.fieldComment;
  }

  public String getFieldKey() {
    return this.fieldKey;
  }

  public String getExtra() {
    return this.extra;
  }
}
