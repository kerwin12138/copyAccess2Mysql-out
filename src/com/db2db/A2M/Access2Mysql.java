package com.db2db.A2M;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;  

  
  
public class Access2Mysql {  
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
             
          //创建数据库access  
          stat.executeUpdate("create database accesstest");  
          //打开创建的数据库  
          stat.close();  
          conn1.close();  
          url = "jdbc:mysql://localhost:3306/accesstest?useUnicode=true&characterEncoding=utf-8";  
          conn1 = DriverManager.getConnection(url, "root", "root");  
          stat = conn1.createStatement();       
          
          while(rs.next()){    
        	  String sql2="create table"+" ";
        	  String sql3="insert into"+" ";
              String url2="jdbc:Access:///D:/NacuesCUniv.mdb";
              String user="";  
              String password="";  
              Connection con=null;  
              Statement st=null;  
              ResultSet rs2=null; 
              con=DriverManager.getConnection(url2, user, password);  
              st=con.createStatement();              
              String TableName = rs.getString(3);
//              if(rs.isLast())break;   				//  跳出查询1
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
//              	sql2+=columnName+" "+"varchar("+columnDisplaySize+")";
              	String typename1=columnTypeName;
              	if(typename1=="LONGVARCHAR")typename1="VARCHAR";//错误类型转换
//              	if(typename1=="DOUBLE")typename1="VARCHAR";
              	
              	sql2+=columnName+" "+typename1+"("+columnDisplaySize+")";
              	 if(i<data.getColumnCount()) {sql2+=",";}
              	 
              } 
              sql2+=",nf varchar(10),yxdm varchar(10),ssdm varchar(10)";
              sql2+=")";
              System.out.println(sql2);

//           	System.out.println("-----"+rs.getString(3)+"------");
//              stat.executeUpdate(sql2);
            //  stat.executeUpdate("create table test(id varchar(80), name varchar(80))");
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