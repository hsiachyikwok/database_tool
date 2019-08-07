package com.hsia.databasetool.generator;

import com.hsia.databasetool.model.ColumnInfo;
import com.hsia.databasetool.model.GeneratorRequest;
import com.hsia.databasetool.model.TableInfo;
import com.hsia.databasetool.util.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author hsia
 */
public class MarkdownGenerator implements DocGenerator {
    /**
     * 铺满
     * # table_name
     * |   |   |
     * | ------------ | ------------ |
     * |   |   |
     * 左对齐
     * # table_name
     * |   |   |
     * | :------------ | :------------ |
     * |   |   |
     *
     * 居中
     * # table_name
     * |   |   |
     * | :------------: | :------------: |
     * |   |   |
     * 右对齐
     * # table_name
     * |   |   |
     * | ------------ :| ------------ :|
     * |   |   |
     *
     */
    /**
     * 竖
     */
    private static String COLUMN = "|";
    /**
     * 横
     */
    private static String ROW = "-";
    /**
     * 冒号
     */
    private static String COLON = ":";
    /**
     * 对齐方式 默认 左对齐
     */
    private String typeSetting = "left";
    /**
     * 文件
     */
    private static File file = null;
    /**
     * 文件输出流
     */
    private static FileOutputStream outputStream = null;
    /**
     * 文件是否写完
     */
    private static boolean isFileEnd = false;



    public void generate(GeneratorRequest request) {
        System.out.println("********开始生成markdown文档***********");
        file = FileUtil.createFile();
        outputStream = FileUtil.createFileOutPutStream(file);
        // 数据库名
        printHx(1);
        printValue(request.getDataBaseName());
        DateFormat format = new SimpleDateFormat("yyyy-MMdd-HH:mm:ss");
        String currentDate = format.format(new Date());
        printLineBreak();
        printHx(3);
        printValue("生成时间:" + currentDate);
        printLineBreak();
        for (TableInfo tableInfo : request.getTableInfo()) {
            // 表名
            printHx(2);
            printValue(tableInfo.getTableName());
            printValue("\t" + "备注:" + tableInfo.getRemarks());
            printLineBreak();
            // 表头
            printTableHead();
            // markdown列标志
            typeSettingLeft(4);
            // 表的列数据
            for (ColumnInfo columnInfo : tableInfo.getColumnInfoList()) {
                printColumnAndRightSpace();
                printValue(columnInfo.getColumnName());
                printColumnAndRightSpace();
                printValue(columnInfo.getColumnRemark());
                printColumnAndRightSpace();
                printValue(columnInfo.getColumnType());
                printColumnAndRightSpace();
                printValue(columnInfo.getColumnSize());
                printColumn();
                printLineBreak();
            }
            printLineBreak();
        }
        isFileEnd = true;
        System.out.println("********生成markdown文档结束***********");
    }

    /**
     * 表头
     */
    private void printTableHead() {
        printColumnAndRightSpace();
        printValue("字段");
        printColumnAndRightSpace();
        printValue("备注");
        printColumnAndRightSpace();
        printValue("数据类型");
        printColumnAndRightSpace();
        printValue("字段大小");
        printColumn();
        printLineBreak();
    }

    private void printColumnAndRightSpace() {
        System.out.print("| ");
        FileUtil.write2File("| ",outputStream,isFileEnd);
    }

    private void printColumn() {
        System.out.print("|");
        FileUtil.write2File("|",outputStream,isFileEnd);
    }

    private void printRow12() {
        System.out.print("------------ ");
        FileUtil.write2File("------------ ",outputStream,isFileEnd);
    }

    private void printColon() {
        System.out.print(":");
        FileUtil.write2File(":",outputStream,isFileEnd);
    }

    private void printValue(String value) {
        System.out.print(value);
        FileUtil.write2File(value,outputStream,isFileEnd);
    }

    private void printHx(int i) {
        if (i < 0) {
            i = 0;
        }
        for (int j = 0; j < i; j++) {
            System.out.print("#");
            FileUtil.write2File("#",outputStream,isFileEnd);
        }
        printSpace(1);
    }

    private void printSpace(int i) {
        if (i < 0) {
            i = 0;
        }
        for (int j = 0; j < i; j++) {
            System.out.print(" ");
            FileUtil.write2File(" ",outputStream,isFileEnd);
        }
    }

    private void printLineBreak() {
        System.out.print("\n");
        FileUtil.write2File("\n",outputStream,isFileEnd);
    }

    /**
     * 左对齐
     *
     * @param colNum 列数
     */
    private void typeSettingLeft(int colNum) {
        if (colNum < 0) {
            colNum = 1;
        }
        for (int i = 0; i < colNum; i++) {
            printColumnAndRightSpace();
            printColon();
            printRow12();
        }
        printColumn();
        printLineBreak();
    }

    /**
     * 右对齐
     *
     * @param colNum 列数
     */
    private void typeSettingRight(int colNum) {
        if (colNum < 0) {
            colNum = 1;
        }
        for (int i = 0; i < colNum; i++) {
            printColumnAndRightSpace();
            printRow12();
            printColon();
        }
        printColumn();
        printLineBreak();
    }

    /**
     * 居中
     *
     * @param colNum 列数
     */
    private void typeSettingCenter(int colNum) {
        if (colNum < 0) {
            colNum = 1;
        }
        for (int i = 0; i < colNum; i++) {
            printColumnAndRightSpace();
            printColon();
            printRow12();
            printColon();
        }
        printColumn();
        printLineBreak();
    }

    /**
     * 平铺
     *
     * @param colNum 列数
     */
    private void typeSettingFill(int colNum) {
        if (colNum < 0) {
            colNum = 1;
        }
        for (int i = 0; i < colNum; i++) {
            printColumnAndRightSpace();
            printRow12();
        }
        printColumn();
        printLineBreak();
    }
}

