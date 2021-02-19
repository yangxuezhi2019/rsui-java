package org.rs.core.page.dialect;

import org.rs.core.page.IDialect;
import org.rs.core.page.IPage;

public class MySQLDialect implements IDialect {
	private static final String SQL_END_DELIMITER = ";";

	public StringBuilder bulildPageSql(String sql, IPage page) {
		sql = trim(sql);
		StringBuilder sb = new StringBuilder();
		sb.append(sql);
		int offset = page.getOffset();
		if (offset > 0) {
			sb.append(" limit ").append(offset).append(',').append(page.getPageSize()).append(SQL_END_DELIMITER);
		} else {
			sb.append(" limit ").append(page.getPageSize()).append(SQL_END_DELIMITER);
		}
		return sb;
	}

	public boolean supportsPaged() {
		return true;
	}

	private String trim(String sql) {
		sql = sql.trim();
		if (sql.endsWith(SQL_END_DELIMITER)) {
			sql = sql.substring(0, sql.length() - 1 - SQL_END_DELIMITER.length());
		}
		return sql;
	}

	public String getSelectCountSql(String sql) {
		return "select count(1) from (" + sql + ") t";
	}
}
