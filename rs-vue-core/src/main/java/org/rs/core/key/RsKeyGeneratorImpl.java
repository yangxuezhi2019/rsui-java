package org.rs.core.key;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import javax.sql.DataSource;


public class RsKeyGeneratorImpl implements RsKeyGenerator{
	static private String KEY_TABLE = "RS_KEY_TABLE";
	private DataSource dataSource;
	private Map<String,KeyObject> keyMap = new ConcurrentHashMap<>();
	private Connection getConnection() {
		
		try {
			Connection connection = dataSource.getConnection();
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public RsKeyGeneratorImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public String getUUIDKey() {
		
		return UUID.randomUUID().toString().replaceAll("\\-", "");
	}
	
	public String getZipKey( String keyName, int keyLength ) {
		
		KeyObject keyObject = getKeyObject(keyName);
		return generatorZipKey(keyObject,keyLength);
	}

	public String getDateKey( String keyName, int keyLength ) {
		
		KeyObject keyObject = getKeyObject(keyName);
		return generatorDateKey(keyObject,keyLength);
	}
	
	public String getDateKeyStepOne( String keyName, int keyMinLength ) {
		
		KeyObject keyObject = getKeyObject(keyName,1);
		return generatorDateKeyMinLength(keyObject,keyMinLength);
	}
	
	public String getPayReqBm() {
		
		KeyObject keyObject = getKeyObject("_pay_req_ysbm_key_",1);
		String bh = generatorDateKeyMinLength(keyObject,3);
		return bh.substring(2);
	}
	
	public String getQuotaBm( String year ) {
		
		KeyObject keyObject = getKeyObject("_quota_" + year + "_key_",1);
		String bh = year + generatorMinLength(keyObject,6);
		return bh.substring(2);
	}
	
	public String getKey( String keyName, int keyLength ) {
		
		KeyObject keyObject = getKeyObject(keyName);
		return generatorKey(keyObject,keyLength);
	}
	
	public long getIndexKey( String keyName ) {
		
		KeyObject keyObject = getKeyObject(keyName,1);
		return generatorIndexKey(keyObject);
	}
	
	private KeyObject getKeyObject( String keyName ) {
		return getKeyObject(keyName,10L);
	}
	private KeyObject getKeyObject( String keyName,long keyStep ) {
		
		KeyObject keyObject = keyMap.get(keyName);
		if( keyObject == null ) {
			
			synchronized (this) {
				
				keyObject = keyMap.get(keyName);
				if( keyObject == null ) {
					
					Connection conn = getConnection();
					try {
						keyObject = getKeyObjectForUpdate(conn, keyName);
						if( keyObject == null ) {
							
							keyObject = new KeyObject();
							keyObject.keyName = keyName;
							keyObject.keyValue = 1L;
							keyObject.maxValue = 1L;
							keyObject.keyStep = keyStep;
							insertKey(conn, keyObject);
						}else {
							//需要重新计数
							keyObject.keyValue = keyObject.maxValue;
						}
						keyMap.put(keyName, keyObject);
					}finally {
						closeDbObject(conn);
					}
				}				
			}
		}
		return keyObject;
	}
	
	private String generatorZipKey(KeyObject keyObject, int keyLength) {
		
		String v = "";
		synchronized (keyObject) {
			
			if(keyObject.keyValue >= keyObject.maxValue ) {
				
				//重新加载keyObject
				reLoadKeyObject(keyObject,false);
			}
			String strResult = convertLongTo36(keyObject.keyValue++);
			v = String.format("%0"+(keyLength - strResult.length()) +"d", 0) + strResult;
		}
		return v;
	}
	
	private String generatorDateKey(KeyObject keyObject, int keyLength) {
		
		String v = "";
		synchronized (keyObject) {
			
			if(keyObject.keyValue >= keyObject.maxValue || keyObject.dateIsChange() ) {
				
				//重新加载keyObject
				reLoadKeyObject(keyObject,true);
			}
			v = keyObject.prefix + String.format("%0"+keyLength+"d", keyObject.keyValue++);
		}
		return v;
	}
	
	private String generatorDateKeyMinLength(KeyObject keyObject, int keyMinLength) {
		String v = "";
		synchronized (keyObject) {
			
			if( keyObject.keyValue >= keyObject.maxValue || keyObject.dateIsChange() ) {
				
				//重新加载keyObject
				reLoadKeyObject(keyObject,true);
			}
			
			String keyValue = String.format("%d", keyObject.keyValue++);
			if( keyValue.length() >= keyMinLength ) {
				v = keyObject.prefix + keyValue;
			}else {
				v = keyObject.prefix;// + String.format("%0"+keyLength+"d", keyObject.keyValue++);
				for( int i = 0; i < (keyMinLength-keyValue.length()); i ++ ) {
					v += "0";
				}
				v += keyValue;
			}
		}
		return v;
	}
	
	private String generatorMinLength(KeyObject keyObject, int keyMinLength) {
		String v = "";
		synchronized (keyObject) {
			
			if( keyObject.keyValue >= keyObject.maxValue ) {
				
				//重新加载keyObject
				reLoadKeyObject(keyObject,false);
			}
			
			String keyValue = String.format("%d", keyObject.keyValue++);
			if( keyValue.length() >= keyMinLength ) {
				v = keyValue;
			}else {
				for( int i = 0; i < (keyMinLength-keyValue.length()); i ++ ) {
					v += "0";
				}
				v += keyValue;
			}
		}
		return v;
	}
	
	private long generatorIndexKey(KeyObject keyObject) {
		
		long value = 0;
		synchronized (keyObject) {
			
			if(keyObject.keyValue >= keyObject.maxValue ) {
				
				//重新加载keyObject
				reLoadKeyObject(keyObject,true);
			}
			value = keyObject.keyValue++;
		}
		return value;
	}
	
	private String generatorKey(KeyObject keyObject, int keyLength) {
		
		String v = "";
		synchronized (keyObject) {
			
			if(keyObject.keyValue >= keyObject.maxValue ) {
				
				//重新加载keyObject
				reLoadKeyObject(keyObject,false);
			}
			v = String.format("%0"+keyLength+"d", keyObject.keyValue++);
		}
		return v;
	}
	
	private KeyObject getKeyObjectForUpdate(Connection conn,String keyName) {
		
		KeyObject result = null;
		try {
			PreparedStatement psQuery = 
					conn.prepareStatement("select KEYNAME,KEYMAXSEQ,KEYSTEP,KEYDATE,NOW() as DBDATE from " 
							+ KEY_TABLE + " where keyNAME=? FOR UPDATE");
			psQuery.setString(1, keyName);		
			ResultSet set = psQuery.executeQuery();
			if(set.next()) {
			
				result = new KeyObject();
				result.keyName = keyName;
				result.maxValue = set.getLong(2);
				result.keyStep = set.getLong(3);
				result.keyDate = new Date(set.getTimestamp(4).getTime());//new DateTime(set.getTimestamp(4).getTime());
				result.dbDate = new Date(set.getTimestamp(5).getTime());//new DateTime(set.getTimestamp(5).getTime());
			}
			set.close();
			psQuery.close();
		}catch(Exception err) {
			err.printStackTrace();
		}
		return result;
	}
	
	private void updateKeyObject(Connection conn,KeyObject keyObject) {
		
		PreparedStatement psUpdate = null;
		try {
			psUpdate = conn.prepareStatement("update " + KEY_TABLE + " set KEYMAXSEQ=?,KEYDATE=? where keyNAME=?");
			psUpdate.setLong(1, keyObject.maxValue);
			psUpdate.setTimestamp(2, new Timestamp(keyObject.keyDate.getTime()));
			psUpdate.setString(3, keyObject.keyName);
			psUpdate.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeDbObject(psUpdate);
		}
	}
	
	private void insertKey(Connection conn, KeyObject keyObject) {
		
		PreparedStatement psInsert = null;
		try {
			psInsert = conn.prepareStatement("insert into " + KEY_TABLE + "(KEYNAME,KEYMAXSEQ,KEYSTEP,KEYDATE) value(?,?,?,NOW())");
			psInsert.setString(1, keyObject.keyName);
			psInsert.setLong(2, keyObject.maxValue);
			psInsert.setLong(3, keyObject.keyStep);
			psInsert.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			closeDbObject(psInsert);
		}
	}
	
	private void reLoadKeyObject(KeyObject keyObject,boolean isDateKey) {
		
		//重新加载keyObject
		Connection conn = getConnection();
		boolean oldCommitStatus = false;
		try {
			if( conn != null ) {
				
				oldCommitStatus = conn.getAutoCommit();
				conn.setAutoCommit(false);
				//首先锁定对象
				KeyObject tmpKeyObject = getKeyObjectForUpdate(conn,keyObject.keyName);
				if( tmpKeyObject == null )
					throw new RuntimeException(keyObject.keyName + " : 不存在");
				
				keyObject.keyValue = tmpKeyObject.maxValue;
				keyObject.maxValue = keyObject.keyValue + keyObject.keyStep;
				keyObject.keyDate = tmpKeyObject.keyDate;
				keyObject.dbDate = tmpKeyObject.dbDate;
				
				if( isDateKey ) {
					
					if( keyObject.dateIsChange() ) {
						keyObject.keyDate = keyObject.dbDate;
						keyObject.keyValue = 1L;
						keyObject.maxValue = keyObject.keyValue + keyObject.keyStep;
					}
					SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");					
					keyObject.prefix = fmt.format(keyObject.keyDate);
				}
				updateKeyObject(conn, keyObject);
				conn.commit();
			}
		}
		catch(Exception err) {
			err.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		finally {
			try {
				conn.setAutoCommit(oldCommitStatus);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			closeDbObject(conn);
		}
	}
	
	private void closeDbObject( AutoCloseable c) {
		
		if( c != null ) {
			try {
				c.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	static private long getCharValue(char c) {
		
		if( c >= '0' && c <= '9')
			return (c - 48);
		else if( c >= 'A' && c <= 'Z')
			return (c-55);
		else if( c >= 'a' && c <= 'z')
			return (c - 87);
		return 0;
	}
	
	static public long convert36ToLong( String strValue) {
		
		long result = 0;
		char[] arr = strValue.toCharArray();
		int power = arr.length - 1;
		for( int i = 0; i < arr.length; i ++) {
			
			long cValue = getCharValue(arr[i]);
			result = (long) (result + cValue*Math.pow(36, power));
			power --;
		}	
		return result;
	}
	static private String val = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static public String convertLongTo36(long value) {
		
		String result = "";
		while( value >= 36 ) {
			
			long mod = value % 36;
			result = val.charAt((int)mod) + result;
			value /= 36;
		}
		result = val.charAt((int)value) + result;
		return result;
	}
	
	static class KeyObject{
		public String keyName;
		public String prefix;
		public Long keyValue;
		public Long maxValue;
		public Long keyStep;
		public Date keyDate;
		public Date dbDate;
		
		public boolean dateIsChange() {
			
			Calendar c1 = Calendar.getInstance();
			c1.setTime(keyDate);
			int c1Year = c1.get(Calendar.YEAR);
			int c1Month= c1.get(Calendar.MONTH);
			int c1Day = c1.get(Calendar.DAY_OF_MONTH);
			Calendar c2 = Calendar.getInstance();
			c2.setTime(dbDate);
			int c2Year = c2.get(Calendar.YEAR);
			int c2Month= c2.get(Calendar.MONTH);
			int c2Day = c2.get(Calendar.DAY_OF_MONTH);
			
			return (c1Day != c2Day || c1Month != c2Month || c1Year != c2Year);
		}
	}
}
