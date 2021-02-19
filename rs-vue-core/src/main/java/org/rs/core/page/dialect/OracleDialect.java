package org.rs.core.page.dialect;

import org.rs.core.page.IDialect;
import org.rs.core.page.IPage;

public class OracleDialect implements IDialect {
	private static final String ORACLE_PAGEDSQL_FORMATTER = "SELECT * FROM (SELECT A.*, ROWNUM RN FROM (@_z_#) A WHERE ROWNUM <= @_x_#) WHERE RN >= @_y_#";

	public StringBuilder bulildPageSql(String sql, IPage page) {
		StringBuilder ret = new StringBuilder();
		int offset = page.getOffset() + 1;
		int endset = offset + page.getPageSize() - 1;
		String rs = ORACLE_PAGEDSQL_FORMATTER.replaceFirst("@_y_#", offset + "").replaceFirst("@_x_#", endset + "").replaceFirst("@_z_#", sql);
		ret.append(rs);
		return ret;
	}

	public boolean supportsPaged() {
		return true;
	}

	public String getSelectCountSql(String sql) {
		return "select count(1) from (" + sql + ") t";
	}
}
