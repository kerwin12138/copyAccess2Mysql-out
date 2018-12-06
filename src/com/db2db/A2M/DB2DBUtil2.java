package com.db2db.A2M;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DB2DBUtil2 {
       
    public void startImport(String SourceDB,String DestDB,String fromTable ) throws Exception {
        // 创建到两个数据库的连接
        ComboPooledDataSource cds_Dest = new ComboPooledDataSource(DestDB);
        ComboPooledDataSource cds_Source = new ComboPooledDataSource("access");
        /* 使用c3p0-config 的数据库
        cds_Source.setDriverClass("net.ucanaccess.jdbc.UcanaccessDriver");
        cds_Source.setJdbcUrl("jdbc:ucanaccess://D:/java/NACS.mdb");
        cds_Source.setUser("admin");
        cds_Source.setPassword("!@#$NACS%^&*");
        cds_Source.setMinPoolSize(1);
        cds_Source.setAcquireIncrement(1); 
        cds_Source.setMaxPoolSize(3);
        cds_Source.setCheckoutTimeout(50000);
        */
        Connection connDest = cds_Dest.getConnection();
        Connection connSource = cds_Source.getConnection();

        
        // 查询出当前用户下面的所有表，依次处理（如果有外键，建议人工输入各表，以保证顺序；如果没有外键，直接运行下面程序即可。）
        try {                  
            // 方式二：人工输入各表名（需要保证顺序，以确保有外键的表在主表之后插入数据）
            importTable(SourceDB,connSource, connDest, fromTable);
        } finally {
            // 自动关闭数据库资源？
            connDest.close();
            connSource.close();
        }
    }
    
	
	public String getLastUpdate(String DestDB,String querysql) throws SQLException{
		ComboPooledDataSource cds_Dest = new ComboPooledDataSource(DestDB);
	    Connection connDest = cds_Dest.getConnection();
	    
		Statement stmt = null;
		String last_date = null;
		String sql = querysql;
		try {	
			stmt = connDest.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()){
				if(rs.getString(1)==null){
					DateFormat df=new SimpleDateFormat("yyyyMMddHHmmss"); 
					Calendar c = Calendar.getInstance();
					c.add(Calendar.MONTH, -6);
					last_date = df.format(c.getTime());
				} else {
					last_date = rs.getString(1);
				}
				
			} else {
				last_date = rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            // 自动关闭数据库资源？
            connDest.close();
		}
		return last_date;
	}
	
    private void importTable(String Site,Connection connSource, Connection connDest, String fromTableSQL ) throws Exception {
        Statement stmt = null;
        PreparedStatement pstmt = null;

        Timestamp datetime;
        String data_flag,remark;
        int day_night;
        final String SITE = Site;
        int rscount = 0;
        
        try {
            // 给PreparedStatement赋值，然后更新；如果是大数量的情况，可以考虑Batch处理。因为这里的数据量小，直接单条更新了。
            // 打开源数据库中相关表
            StringBuilder insertSQL = new StringBuilder();
            insertSQL.append("insert ignore into esd_data (GID,name,super_gid,super_name,card_num,esd_time,esd_msg,data_flag,remark,Site) values (?,?,?,?,?,?,?,?,?,?)");
            stmt= connSource.createStatement();
            ResultSet rs = stmt.executeQuery(fromTableSQL);
            
            // 先计算目标数据库的PreparedStatement的SQL语句
            //ResultSetMetaData rsmd = rs.getMetaData();
 
            //System.out.println(insertSQL.toString());
            
            // 计数
            int count = 0;
            // 每多少条记录提交一次，以提高效率
            int batchCount = 100;
            pstmt = connDest.prepareStatement(insertSQL.toString());
     
            while(rs.next()) { 
            	pstmt.clearParameters();
				datetime = rs.getTimestamp(6);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
				String dt = sdf.format(datetime);
				remark = "";
				//System.out.print(dt + "-->");
				
				day_night = Integer.parseInt(dt.substring(8, 10));
				//System.out.println(day_night);
				if (day_night > 12) {
					data_flag = "Night";
				} else {
					data_flag = "Day";
				}
				
				pstmt.setString(1, rs.getString(1));
				pstmt.setString(2, rs.getString(2));
				pstmt.setString(3, rs.getString(3));
				pstmt.setString(4, rs.getString(4));
				pstmt.setString(5, rs.getString(5));
				pstmt.setString(6, dt);
				pstmt.setString(7, rs.getString(7));
				pstmt.setString(8, data_flag);
				pstmt.setString(9, remark);
				pstmt.setString(10, SITE);
	
				pstmt.addBatch();
				// 输出统计信息
				count++;
				rscount++;
				
				if (count % batchCount == 0) {
					// 若干条提交一次
					System.out.println();
					System.out.print("正在更新数据库...");
					pstmt.executeBatch();
					System.out.println(count);
				}
            }
            

            if(count % batchCount != 0) {
                // 最后提交一次
                System.out.println();
                System.out.print("正在更新数据库...");
                pstmt.executeBatch();
                System.out.println(count);
                System.out.println("完成更新数据库！");
            }
            
            String insertlog = "insert ignore into esddata_log (dataTime,dataAmount,shift,site) values(?,?,?,?)";
            pstmt = connDest.prepareStatement(insertlog);
            DateFormat df=new SimpleDateFormat("yyyyMMddHHmmss"); 
			Calendar c = Calendar.getInstance();
			String dt = df.format(c.getTime());
			day_night = Integer.parseInt(dt.substring(8, 10));
			//System.out.println(day_night);
			if (day_night > 12) {
				data_flag = "Night";
			} else {
				data_flag = "Day";
			}
            pstmt.setString(1, df.format(c.getTime()).substring(0,8));
            pstmt.setInt(2, rscount);
            pstmt.setString(3, data_flag);
            pstmt.setString(4, SITE);
            pstmt.executeUpdate();
          
            rs.close();
        } finally {
            if(stmt != null) stmt.close();
            if(pstmt != null) pstmt.close();
        }
    }
    

}


