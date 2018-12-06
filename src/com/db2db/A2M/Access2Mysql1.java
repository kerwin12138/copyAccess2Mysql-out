package com.db2db.A2M;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;  

  
  
public class Access2Mysql1 {
	
    public static void main(String args[]) throws Exception{      
    	 Map<Integer, Object[]> map = new HashMap<Integer, Object[]>();  
         String[] column ;  
    	
      	try{
            Class.forName("com.hxtt.sql.access.AccessDriver"); 
            Class.forName("com.mysql.jdbc.Driver");
            //   String dburl ="jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=d:\\NacuesCUniv.mdb";//此为NO-DSN方式      
               String dburl ="jdbc:Access:///D:/NacuesCUniv.mdb";
               Connection conn=DriverManager.getConnection("jdbc:Access:///D:/NacuesCUniv.mdb");      
               Statement stmt=conn.createStatement();   
               DatabaseMetaData  dbmd=conn.getMetaData();    
               ResultSet  rs=dbmd.getTables(null, null, null, null); 	             
          //一开始必须填一个已经存在的数据库  
          String url ="jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8";     
          Connection conn1 = DriverManager.getConnection(url, "root", "root");  
          Statement stat = conn1.createStatement();  

          //创建数据库intest  
          stat.executeUpdate("create database intest");  
             
          //打开创建的数据库  
          stat.close();  
          conn1.close();  
          url = "jdbc:mysql://localhost:3306/intest?useUnicode=true&characterEncoding=utf-8";  
          conn1 = DriverManager.getConnection(url, "root", "root");  
          stat = conn1.createStatement();       
          
          while(rs.next()){    
        	  String sql2="create table"+" ";
              String url2="jdbc:Access:///D:/NacuesCUniv.mdb";
              String user="";  
              String password="";  
              Connection con=null;  
              Statement st=null;  
              ResultSet rs2=null; 
              con=DriverManager.getConnection(url2, user, password);  
              st=con.createStatement();              
              String TableName = rs.getString(3);
              sql2+=TableName+" "+"(";
         //     String TableName1 =rs.getString(3)+" ("	;
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
//              System.out.println(sql2);
//              System.out.println("------\n");
              stat.executeUpdate(sql2);
//      以上为建表
            }
//添加数据  
// stat.executeUpdate("insert into test ()values(1, '张三')");    
          
          ResultSet  rs4=dbmd.getTables(null, null, null, null); 
          while(rs4.next()) {
              String url3="jdbc:Access:///D:/NacuesCUniv.mdb";
              String user3="";  
              String password3="";  
              Connection con3=null;  
              Statement st3=null;  
               ResultSet rs3=null;   
              con3=DriverManager.getConnection(url3, user3, password3);  
              st3=con3.createStatement();  
              
          String TableName = rs4.getString(3);

          while(rs3.next()){ 
              rs3=st3.executeQuery("select * from "+TableName);  
              ResultSetMetaData metaDate = rs3.getMetaData();     
              int a = 0; 

              //输出数据  
 //            System.out.println(TableName);
              int number = metaDate.getColumnCount();     
              column = new String[number];     
              String insqlname3="";//字段名
              for (int j = 0;j < column.length; j++){  
            	  
                  column[j] = metaDate.getColumnName(j + 1); 
                  insqlname3+=column[j];
                  if(j<column.length-1)insqlname3+=",";
              }     
//              System.out.println(insqlname3);
//                 System.out.println(TableName);
        	  String insql3="insert into"+" ";
        	  insql3+=TableName+"(";
        	  insql3+=insqlname3;
        	  insql3+=") "+"values (";
              a++;  
              Object[] rss = new Object[number]; 
              for(int i = 0;i < rss.length; i++){  
                  rss[i] = rs3.getString(i + 1);    
                  insql3+="'"+rss[i]+"'";
                  if(i<rss.length-1)insql3+=",";
//                 System.out.print(rss[i]);            
              }  
              map.put(a, rss) ;   
              
              insql3+=")";
              

//              stat.executeUpdate(insql3);
            System.out.println(insql3);
          }
//          System.out.println(TableName);
         }
     
          stmt.close();      
          conn.close();   
          stat.close();  
          conn1.close(); 
          System.out.println("成功");
      	}  
      	catch(SQLException e) {
          	System.out.println("建表失败");
          	e.printStackTrace();
          }
          catch (Exception e) {  
              e.printStackTrace();  
          }
      	
      
    } 
    
  
}  