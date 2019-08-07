package com.hsia.databasetool;

import com.hsia.databasetool.generator.ExcelGenerator;
import com.hsia.databasetool.generator.MarkdownGenerator;
import com.hsia.databasetool.generator.PDFGenerator;
import com.hsia.databasetool.model.ColumnInfo;
import com.hsia.databasetool.model.GeneratorRequest;
import com.hsia.databasetool.model.TableInfo;

import java.sql.*;
import java.util.*;

/**
 * @author hsia
 */
public class App {
    /**
     * 文档类型pdf
     */
    private static String DOC_PDF = "pdf";
    /**
     * 文档类型excel
     */
    private static String DOC_EXCEL = "excel";
    /**
     * 文档类型markdown
     */
    private static String DOC_MD = "markdown";
    /**
     * 生成文档类型 default excel
     */
    private static String GENERATE_DOC_TYPE = "markdown";
    /**
     * mysql host
     */
    private static String MSQL_HOST = "127.0.0.1";

    /**
     * mysql port
     */
    private static String MSQL_PORT = "3306";
    /**
     * mysql user
     */
    private static String MYSQL_USER = "root";
    /**
     * mysql pwd
     */
    private static String MYSQL_PASSWD = "";
    /**
     * database name
     */
    private static String DB_NAME = "";

    static {
        try {
            // 加载MySql的驱动类
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("找不到驱动程序类 ，加载驱动失败！");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        scanInput();
        GeneratorRequest request = new GeneratorRequest();
        request.setDataBaseName(DB_NAME);
        request.setDocType(GENERATE_DOC_TYPE);
        // 获取数据库表信息
        List<TableInfo> tableInfoList = getDataBaseInfo();
        request.setTableInfo(tableInfoList);
        // 生成文档
        invokeGenerator(request);
    }

    private static void printWelcomeFlag() {
        System.out.println(" ......................欢迎使用......................\n");

    }

    /**
     * 标准输入
     */
    private static void scanInput() {
        printWelcomeFlag();
        Scanner sc = new Scanner(System.in);
        printString("*********请输入数据库Mysql Host 默认localhost*********");
        String host = sc.nextLine();
        if (!"".equals(host)) {
            MSQL_HOST = host;
        }
        printString("*********请输入数据库Mysql Port 默认3306*********");
        String port = sc.nextLine();
        if (!"".equals(port)) {
            MSQL_PORT = port;
        }
        printString("*********请输入数据库Mysql User 默认root*********");
        String user = sc.nextLine();
        if (!"".equals(user)) {
            MYSQL_USER = user;
        }
        boolean flag = true;
        while (flag) {
            printString("*********请输入数据库Mysql Password*********");
            MYSQL_PASSWD = sc.nextLine();
            if (!"".equals(MYSQL_PASSWD)) {
                flag = false;
            }
        }
        flag = true;
        while (flag) {
            printString("*********请输入数据库Mysql DatabaseName*********");
            DB_NAME = sc.nextLine();
            if (!"".equals(DB_NAME)) {
                flag = false;
            }
        }
        printString("*********请输入生成文档类型 markdown,pdf,excel 默认markdown*********");
        String docType = sc.nextLine();
        if (!"".equals(docType)) {
            GENERATE_DOC_TYPE = docType;
        }
    }

    /**
     * 生成文档
     *
     * @param request
     */
    private static void invokeGenerator(GeneratorRequest request) {
        if (request.getDocType().equalsIgnoreCase(DOC_PDF)) {
            PDFGenerator pdfGenerator = new PDFGenerator();
            pdfGenerator.generate(request);
        } else if (request.getDocType().equalsIgnoreCase(DOC_EXCEL)) {
            ExcelGenerator excelGenerator = new ExcelGenerator();
            excelGenerator.generate(request);
        } else {
            MarkdownGenerator generator = new MarkdownGenerator();
            generator.generate(request);
        }
    }

    private static void printString(String msg) {
        System.out.println(msg);
    }

    /**
     * 组装jdbc url
     *
     * @return
     */
    private static String getUrl() {
        String url = "jdbc:mysql://" + MSQL_HOST + ":" + MSQL_PORT + "/" + DB_NAME + "?useUnicode\\=true&characterEncoding\\=utf8&autoReconnect\\=true&zeroDateTimeBehavior\\=convertToNull&allowMultiQueries\\=true";
        System.out.println(url);
        return url;
    }


    /**
     * 获取数据库表字段信息
     *
     * @return
     */
    private static List<TableInfo> getDataBaseInfo() {
        List<TableInfo> tableInfoList = new ArrayList<TableInfo>();
        String url = getUrl();
        try {
            Connection con = DriverManager.getConnection(url, MYSQL_USER, MYSQL_PASSWD);
            DatabaseMetaData dbmd = con.getMetaData();
            ResultSet resultSet = dbmd.getTables(null, "%", "%", new String[]{"TABLE"});
            // 获取所有表注释
            Map<String, String> tablecommentMap = getAllTableComment(con);
            while (resultSet.next()) {
                TableInfo tableInfo = new TableInfo();
                List<ColumnInfo> columnInfoList = new ArrayList<ColumnInfo>();
                String tableName = resultSet.getString("TABLE_NAME");
                tableInfo.setTableName(tableName);
                // 获取表注释
                tableInfo.setRemarks(tablecommentMap.get(tableName));
                ResultSet rs = dbmd.getColumns(null, "%", tableName, "%");
                System.out.println("表名：" + tableName + "\t\n表字段信息：");
                while (rs.next()) {
                    String colunmName = rs.getString("COLUMN_NAME");
                    String remarks = rs.getString("REMARKS");
                    String typeName = rs.getString("TYPE_NAME");
                    String columnSize = rs.getString("COLUMN_SIZE");
                    ColumnInfo columnInfo = new ColumnInfo();
                    System.out.println("字段名：" + colunmName + "\t字段注释：" + remarks + "\t字段数据类型：" + typeName + "\t字段大小" + columnSize);
                    columnInfo.setColumnName(colunmName);
                    columnInfo.setColumnRemark(remarks);
                    columnInfo.setColumnType(typeName);
                    columnInfo.setColumnSize(columnSize);
                    columnInfoList.add(columnInfo);
                }
                tableInfo.setColumnInfoList(columnInfoList);
                tableInfoList.add(tableInfo);
            }
            return tableInfoList;
        } catch (SQLException se) {
            System.out.println("数据库连接失败");
            se.printStackTrace();
        }
        return tableInfoList;
    }

    /**
     * 查询
     *
     * @param con
     * @return
     */
    private static Map getAllTableComment(Connection con) throws SQLException {
        Map<String, String> tableCommentMap = new HashMap<String, String>();
        String sql = "select TABLE_NAME ,TABLE_COMMENT from INFORMATION_SCHEMA.TABLES where table_schema = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, DB_NAME);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            tableCommentMap.put(rs.getString("TABLE_NAME"), rs.getString("TABLE_COMMENT"));
        }
        return tableCommentMap;
    }
}
