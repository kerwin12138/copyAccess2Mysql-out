package com.db2db.A2M;

import java.sql.*;  

public class CreateTable  //建表
{  
    public static void main(String[] args) throws Exception  
    {  
    	
    	
    	try{
        Class.forName("com.mysql.jdbc.Driver");  
           
        //一开始必须填一个已经存在的数据库  
        String url ="jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8";     
        Connection conn = DriverManager.getConnection(url, "root", "root");  
        Statement stat = conn.createStatement();  
           
        //创建数据库access  
        stat.executeUpdate("create database access");  
           
        //打开创建的数据库  
        stat.close();   
        conn.close();  
        url = "jdbc:mysql://localhost:3306/access?useUnicode=true&characterEncoding=utf-8";  
        conn = DriverManager.getConnection(url, "root", "root");  
        stat = conn.createStatement();  
           
        //创建表test  
        stat.executeUpdate("create table test(id int, name varchar(80))");  
        
           
        //添加数据  
       // stat.executeUpdate("insert into test values(1, '张三')");  
        //stat.executeUpdate("insert into test values(2, '李四')");  
           
        //查询数据  
//        ResultSet result = stat.executeQuery("select * from test");  
//        while (result.next())  
//        {  
//            System.out.println(result.getInt("id") + " " + result.getString("name"));  
//        }  
           
        //关闭数据库  
 //       result.close();  
        stat.close();  
        conn.close();  
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