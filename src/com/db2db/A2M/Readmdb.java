package com.db2db.A2M;

import java.sql.*;  
import java.util.HashMap;  
import java.util.Map;  
  
public class Readmdb {  
    public static void main(String[] args) {  //读取表数据
        Map<Integer, Object[]> map = new HashMap<Integer, Object[]>();  
        String[] column ;  
        try {  
             Class.forName("com.hxtt.sql.access.AccessDriver");  //url--access
             Class.forName("com.mysql.jdbc.Driver");  				//url3--mysql
            //d盘下access文件  
            String url="jdbc:Access:///D:/NacuesCUniv.mdb";
            String user="";  
            String password="";  
            Connection con=null;  
            Statement st=null;  
             ResultSet rs=null;  
             ResultSet rs2=null; 
            con=DriverManager.getConnection(url, user, password);   
            st=con.createStatement();  
            
            //mysql
            String url3 ="jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8";     
            Connection conn3 = DriverManager.getConnection(url3, "root", "root");  
            Statement stat3 = conn3.createStatement();  
            
            //创建数据库access  
            stat3.executeUpdate("create database intest");
            stat3.close();   
            conn3.close(); 
            url3 = "jdbc:mysql://localhost:3306/access?useUnicode=true&characterEncoding=utf-8";  
            conn3 = DriverManager.getConnection(url, "root", "root");  
            stat3 = conn3.createStatement();  
            
            
            ResultSetMetaData metaDate = rs2.getMetaData(); 
            int number = metaDate.getColumnCount();    
            rs2=st.executeQuery("select * from t_jhk");  

            int a = 0;  
            //输出数据  
            while(rs.next()){  
              String sql2="create table"+" ";
        	  String sql3="insert into"+" ";
              String TableName = rs.getString(3);
              if(rs.isLast())break; 
              sql2+=TableName+" "+"(";
              rs2=st.executeQuery("select * from "+TableName);
              ResultSetMetaData data=rs2.getMetaData(); 
 
              
              for(int i = 1 ; i<= data.getColumnCount(); i++){   
                	// 获得所有列的数目及实际列数
                	int columnCount = data.getColumnCount();
                	// 获得指定列的列名
                	String columnName = data.getColumnName(i);
                	// 获得指定列的列值
                	int columnType = data.getColumnType(i);
                	// 获得指定列的数据类型名
                	String columnTypeName = data.getColumnTypeName(i);
                	// 在数据库中类型的最大字符个数
                	int columnDisplaySize = data.getColumnDisplaySize(i);
                	// 默认的列的标题
                	String columnLabel = data.getColumnLabel(i);              
                	// 获取某列对应的表名
                	String tableName = data.getTableName(i);
                	sql2+=columnName+" "+"varchar("+columnDisplaySize+")";
                	 if(i<data.getColumnCount()) {sql2+=",";}
                	 
                } 
                sql2+=")";
//                System.out.println(sql2);
//                System.out.println("------\n");
                stat3.executeUpdate(sql2);

                a++;  
                Object[] rss = new Object[number];  
                for(int i = 0;i < rss.length; i++){  
                    rss[i] = rs.getString(i + 1);    
                    System.out.print(rss[i]+"\t");  
                }  
                System.out.println();  
                map.put(a, rss) ;   
                

            }
            
            
            
            stat3.close();  
            conn3.close(); 
            System.out.println("成功");
            
        } catch (SQLException e) {  
        	System.out.println("建表失败");
            e.printStackTrace();  
        }  
        catch (ClassNotFoundException e) {  
            e.printStackTrace();  
        }         
          
    }  
} 