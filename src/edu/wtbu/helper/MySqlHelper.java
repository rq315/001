package edu.wtbu.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//���� MySQL ���ݿ�Ľ�������
public class MySqlHelper {
    private static Connection conn = null;
    private static PreparedStatement pstmt = null;
    private static ResultSet rs = null;

    // ���ݿ����� URL
    private static String url = "jdbc:mysql://localhost:3306/community?serverTimezone=GMT%2B8&useOldAliasMetadataBehavior=true";
    private static String user = "root";
    private static String password = "123456";

    static {
        try {
            // ���� MySQL JDBC ����
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //��ѯ �������ת��Ϊ List<HashMap<String, Object>> �ṹ
    public static List<HashMap<String, Object>> executeQuery(String sql, Object[] params) {
        List<HashMap<String, Object>> list = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(url, user, password);
            pstmt = conn.prepareStatement(sql);
            if (params != null) {
                for (int i = 0; i < params.length; ++i) {
                    if (params[i] == null) {
                        pstmt.setNull(i + 1, java.sql.Types.NULL);
                    } else {
                        String className = params[i].getClass().getName();
                        if (className.contains("String")) {
                            pstmt.setString(i + 1, params[i].toString());
                        } else if (className.contains("Integer")) {
                            pstmt.setInt(i + 1, Integer.parseInt(params[i].toString()));
                        }
                    }
                }
            }
            rs = pstmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int count = rsmd.getColumnCount();
            while (rs.next()) {
                HashMap<String, Object> map = new HashMap<>();
                for (int i = 0; i < count; ++i) {
                    map.put(rsmd.getColumnName(i + 1), rs.getObject(i + 1));
                }
                list.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return list;
    }

    //����
    public static int executeUpdate(String sql, Object[] params) {
        int result = 0;
        try {
            conn = DriverManager.getConnection(url, user, password);
            pstmt = conn.prepareStatement(sql);
            if (params != null) {
                for (int i = 0; i < params.length; ++i) {
                    if (params[i] == null) {
                        pstmt.setNull(i + 1, java.sql.Types.NULL);
                    } else {
                        String className = params[i].getClass().getName();
                        if (className.contains("String")) {
                            pstmt.setString(i + 1, params[i].toString());
                        } else if (className.contains("Integer")) {
                            pstmt.setInt(i + 1, Integer.parseInt(params[i].toString()));
                        }
                    }
                }
            }
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return result;
    }

    //�ر��������ݿ���Դ
    public static void closeAll() {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}