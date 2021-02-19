package org.rs.core.utils;

import java.sql.Connection;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

@Intercepts({@Signature(type= StatementHandler.class,method = "prepare",args = {Connection.class,Integer.class})})
public class MybatisInercepter implements Interceptor{

	private String dbType;
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		
		StatementHandler statementHandler = (StatementHandler)invocation.getTarget();
		MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
		
		MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
		if (SqlCommandType.SELECT != mappedStatement.getSqlCommandType()
		    || StatementType.CALLABLE == mappedStatement.getStatementType()) {
		    return invocation.proceed();
		}
		
		int limit =0,offset=0;
		BoundSql boundSql = statementHandler.getBoundSql();
		Object paramObject = boundSql.getParameterObject();
		if( paramObject != null && paramObject instanceof Map){
			
			Map<?,?> map = (Map<?,?>)paramObject;
			if( map.containsKey("offset") && map.containsKey("limit")){
				limit = Integer.parseInt(map.get("limit").toString());
				offset = Integer.parseInt(map.get("offset").toString());
			}
		}
		
		if( limit > 0 && offset >= 0 ){
			
			metaObject.setValue("delegate.rowBounds", null);
			String sql = boundSql.getSql();
			sql = limitString.getLimitString(sql, offset, limit);
			metaObject.setValue("delegate.boundSql.sql", sql);
		}		
		return invocation.proceed();
	}

	public interface LimitString{
		String getLimitString(String sql, int offset, int limit);
	}
	private LimitString limitString;
	
	@Override
	public Object plugin(Object target) {
		//System.out.println(target);
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		System.out.println(properties);
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
		//System.out.println("setDbType:" + dbType);
		dbType = dbType.toUpperCase();
		if( "MYSQL".equals(dbType)){
			limitString = new MySqlLimitString();
		}
		else if( "ORACLE".equals(dbType) ){
			limitString = new OracleLimitString();
		}
		else{
			limitString = new OracleLimitString();
		}
	}
	public static class OracleLimitString implements LimitString{

		@Override
		public String getLimitString(String sql, int offset, int limit) {

			sql = sql.trim();
			StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
			pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
			     
			pagingSelect.append(sql);
			pagingSelect.append(" ) row_ ) where rownum_ > ").append(offset).append(" and rownum_ <= ").append(offset + limit);
			     
			return pagingSelect.toString();
		}
		
	}
	
	public static class MySqlLimitString implements LimitString{

		@Override
		public String getLimitString(String sql, int offset, int limit) {

			return new StringBuilder(sql).append(" limit ").append(offset).append(",").append(limit).toString();
		}		
	}
	
	/*protected String buildMysqlPageSql(String sql, int offset, int limit) {  

        return new StringBuilder(sql).append(" limit ").append(offset).append(",").append(limit).toString();
    }
	
	protected String buildOraclePageSql(String sql, int offset, int limit) {  
        // 计算第一条记录的位置，Oracle分页是通过rownum进行的，而rownum是从1开始的   
        StringBuilder sb = new StringBuilder(sql);  
        sb.insert(0, "select u.*, rownum r from (").append(") u where rownum < ").append(offset + limit);  
        sb.insert(0, "select * from (").append(") where r >= ").append(offset);  
        return sb.toString();  
    }*/
}

