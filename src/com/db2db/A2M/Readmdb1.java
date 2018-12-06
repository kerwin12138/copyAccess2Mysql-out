package com.db2db.A2M;

import java.sql.*;  
import java.util.HashMap;  
import java.util.Map;  
  
public class Readmdb1 {  
    public static void main(String[] args) {  
        Map<Integer, Object[]> map = new HashMap<Integer, Object[]>();  
        String[] column ;  
        try {  
             Class.forName("com.hxtt.sql.access.AccessDriver");  
            String url3="jdbc:Access:///D:/NacuesCUniv.mdb";
            String user3="";  
            String password3="";  
            Connection con3=null;  
            Statement st3=null;  
             ResultSet rs3=null;  
            con3=DriverManager.getConnection(url3, user3, password3);  
            st3=con3.createStatement();  
               
             rs3=st3.executeQuery("select * from t_jhk");  
             ResultSetMetaData metaDate = rs3.getMetaData();     
             int number = metaDate.getColumnCount();     
             column = new String[number];     
             String sql ="";
             for (int j = 0;j < column.length; j++){     
                 column[j] = metaDate.getColumnName(j + 1);     
//                 System.out.print(column[j]+",");  
                 sql+=column[j]+",";

             }   
             System.out.println(sql);
             System.out.println();  
             //添加数据  
             // stat.executeUpdate("insert into test values(1, '张三')");  


            int a = 0;  
            //输出数据  
            while(rs3.next()){  
                a++;  
                Object[] rss = new Object[number];  
                for(int i = 0;i < rss.length; i++){  
                    rss[i] = rs3.getString(i + 1);  
                    System.out.print(rss[i]+"\t");  
                }  
                System.out.println();  
                map.put(a, rss) ;                 
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
            System.out.println("建表失败");
        }  
        catch (ClassNotFoundException e) {  
            e.printStackTrace();  
        }         
          
    }  
}  