package com.hsia.databasetool.model;


import java.util.List;

/**
 * @author hsia
 */
public class TableInfo {
    /**
     * 表名
     */
    private String tableName;
    /**
     * 备注
     */
    private String remarks;

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * 列信息
     */
    private List<ColumnInfo> ColumnInfoList;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<ColumnInfo> getColumnInfoList() {
        return ColumnInfoList;
    }

    public void setColumnInfoList(List<ColumnInfo> columnInfoList) {
        ColumnInfoList = columnInfoList;
    }
}
