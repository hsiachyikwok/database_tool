package com.hsia.databasetool.model;

import java.util.List;

/**
 * @author hsia
 */
public class GeneratorRequest {
    private String dataBaseName;

    private String docType = "excel";
    /**
     * 表信息
     */
    private List<TableInfo> tableInfo;

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getDataBaseName() {
        return dataBaseName;
    }

    public void setDataBaseName(String dataBaseName) {
        this.dataBaseName = dataBaseName;
    }

    public List<TableInfo> getTableInfo() {
        return tableInfo;
    }

    public void setTableInfo(List<TableInfo> tableInfo) {
        this.tableInfo = tableInfo;
    }
}
