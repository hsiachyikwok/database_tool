package com.hsia.databasetool.model;

/**
 * @author hsia
 */
public class ColumnInfo {
    /**
     * 字段名
     */
    private String columnName;
    /**
     * 字段注释
     */
    private String columnRemark;
    /**
     * 字段类型
     */
    private String columnType;
    /**
     * 字段大小
     */
    private String columnSize;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnRemark() {
        return columnRemark;
    }

    public void setColumnRemark(String columnRemark) {
        this.columnRemark = columnRemark;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(String columnSize) {
        this.columnSize = columnSize;
    }
}
