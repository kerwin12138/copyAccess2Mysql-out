package com.db2db.A2M;

import java.sql.Connection;  
import java.sql.DatabaseMetaData;  
import java.sql.DriverManager;  
import java.sql.ResultSet;  
import java.sql.Statement;  
  
  
public class ConTableName {  
    public static void main(String args[]) throws Exception{   //采集表名   
          Class.forName("com.hxtt.sql.access.AccessDriver");      
       //   String dburl ="jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=d:\\NacuesCUniv.mdb";//此为NO-DSN方式      
          String dburl ="jdbc:Access:///D:/NacuesCUniv.mdb";
          Connection conn=DriverManager.getConnection("jdbc:Access:///D:/NacuesCUniv.mdb");      
          Statement stmt=conn.createStatement();          
          DatabaseMetaData  dbmd=conn.getMetaData();    
          ResultSet  rs=dbmd.getTables(null, null, null, null);
          
          Connection conn1=DriverManager.getConnection("jdbc:Access:///D:/NacuesCUniv.mdb");      
          Statement stmt1=conn1.createStatement();          
          DatabaseMetaData  dbmd1=conn1.getMetaData();    
          ResultSet  rs1=dbmd1.getTables(null, null, null, null);
          
          
          int sum=0;
         
          while(rs.next()) {

        	  String TableName =null;
        	  TableName = rs.getString(3);
        	  if(rs.isLast())break;
        	  sum++;
        	  
        	  System.out.println("table-name:  "+rs.getString(3)); 
          }
          System.out.println(sum);
//          while(rs1.next()){   
//               System.out.println("table-name:  "+rs1.getString(3)); 
//               
//           
//          }    
//          stmt1.close();
          conn1.close();
          stmt.close();      
          conn.close();      
         }      
  
}  