package org.rs.core.page;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.rs.core.page.dialect.MySQLDialect;
import org.rs.core.page.dialect.OracleDialect;
import org.rs.core.page.dialect.SQLServerDialect;

@Intercepts({@org.apache.ibatis.plugin.Signature(type = StatementHandler.class, method = "prepare", args = {
		Connection.class, Integer.class})})
public class RsPageInterceptor implements Interceptor{

	private IDialect dialectObj;
	
	public RsPageInterceptor(IDialect dialectObj){
		
		this.dialectObj = dialectObj;
	}
	
	static public RsPageInterceptor createInterceptor( String dbType ) {
		
		IDialect dialect = null;
		if( "ORACLE".equals(dbType)) {
			
			dialect = new OracleDialect();
		}else if( "SQLSERVER".equals(dbType)){
			dialect = new SQLServerDialect();
		}else {
			dialect = new MySQLDialect();
		}
		return new RsPageInterceptor(dialect);
	}
	
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
		
		boolean flag = metaStatementHandler.hasGetter("h");
		while (flag) {
			Object object = metaStatementHandler.getValue("h");
			metaStatementHandler = SystemMetaObject.forObject(object);
			flag = metaStatementHandler.hasGetter("h");
		}
		flag = metaStatementHandler.hasGetter("target");
		while (flag) {
			Object object = metaStatementHandler.getValue("target");
			metaStatementHandler = SystemMetaObject.forObject(object);
			flag = metaStatementHandler.hasGetter("target");
		}
		
		MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
		if (SqlCommandType.SELECT != mappedStatement.getSqlCommandType()
		    || StatementType.CALLABLE == mappedStatement.getStatementType()) {
		    return invocation.proceed();
		}

		BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
		Object parameterObject = boundSql.getParameterObject();
		if (parameterObject != null) {
			boolean hasPage = metaStatementHandler.hasGetter("delegate.boundSql.parameterObject.page");
			if (hasPage) {
				if ((metaStatementHandler.getValue("delegate.boundSql.parameterObject.page") instanceof IPage)) {
					IPage page = (IPage) metaStatementHandler.getValue("delegate.boundSql.parameterObject.page");
					if (page != null) {
						String sql = boundSql.getSql();
						//IDialect dialectObj = DialectFactory.getDialect(dialect);
						if (dialectObj.supportsPaged()) {
							StringBuilder pageSql = dialectObj.bulildPageSql(sql, page);
							metaStatementHandler.setValue("delegate.boundSql.sql", pageSql.toString());

							metaStatementHandler.setValue("delegate.rowBounds.offset", Integer.valueOf(0));
							metaStatementHandler.setValue("delegate.rowBounds.limit",
									Integer.valueOf(Integer.MAX_VALUE));
						} else {
							metaStatementHandler.setValue("delegate.boundSql.sql", sql);
							int offset = (page.getCurrentPage() - 1) * page.getPageSize();
							int limit = page.getPageSize();
							metaStatementHandler.setValue("delegate.rowBounds.offset", Integer.valueOf(offset));
							metaStatementHandler.setValue("delegate.rowBounds.limit", Integer.valueOf(limit));
						}
						Connection connection = (Connection) invocation.getArgs()[0];
						if (page.isQueryCount()) {
							String countSql = dialectObj.getSelectCountSql(sql);
							setPageParameter(countSql, connection, mappedStatement, boundSql, page);
						}
					}
				}
			} else {
				metaStatementHandler.setValue("delegate.rowBounds.offset", Integer.valueOf(0));
				metaStatementHandler.setValue("delegate.rowBounds.limit", Integer.valueOf(Integer.MAX_VALUE));
			}
		}
		return invocation.proceed();
	}
	
	private void setPageParameter(String countSql, Connection connection, MappedStatement mappedStatement,
			BoundSql boundSql, IPage page) {
		PreparedStatement countStmt = null;
		ResultSet rs = null;
		try {
			countStmt = connection.prepareStatement(countSql);

			setParameters(countStmt, mappedStatement, boundSql, boundSql.getParameterObject());
			rs = countStmt.executeQuery();
			int totalCount = 0;
			if (rs.next()) {
				totalCount = rs.getInt(1);
			}
			page.setTotalCount(totalCount);
			return;
		} catch (SQLException e) {
			//LOGGER.error("分页查询出错.");
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				//LOGGER.error("关闭ResultSet出错.");
			}
			try {
				if (countStmt != null) {
					countStmt.close();
				}
			} catch (SQLException e) {
				//LOGGER.error("关闭PreparedStatement出错.");
			}
		}
	}

	private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql,
			Object parameterObject) throws SQLException {
		ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
		parameterHandler.setParameters(ps);
	}
}
