package com.db2db.A2M;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class readTest {
	
	
	public static void readTest() throws Exception {
		Class.forName("com.hxtt.sql.access.AccessDriver");
		Connection conn= DriverManager.getConnection("jdbc:Access:///F:/Other_D/dbf/NacuesCUniv.mdb"); 
	    Statement stmt = conn.createStatement();
	    ResultSet rs = stmt.executeQuery("select * from TD_ZZMMDM"); 
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
