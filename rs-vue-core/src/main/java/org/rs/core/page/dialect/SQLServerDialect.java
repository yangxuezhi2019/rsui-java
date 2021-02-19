package org.rs.core.page.dialect;

import org.rs.core.page.IDialect;
import org.rs.core.page.IPage;

public class SQLServerDialect implements IDialect {
	public boolean supportsPaged() {
		return true;
	}

	public StringBuilder bulildPageSql(String sql, IPage page) {
		int currentPage = page.getCurrentPage();
		int pageSize = page.getPageSize();
		int offset = page.getOffset();
		int top = pageSize * currentPage;
		String orderStr = null;
		StringBuilder pagingSelect = new StringBuilder();
		if (hasOrder(sql)) {
			orderStr = sql.substring(
					sql.lastIndexOf("ORDER BY") != -1 ? sql.lastIndexOf("ORDER BY") : sql.lastIndexOf("order by"));
			pagingSelect.append("select top " + pageSize + " * from (select top 100 percent ROW_NUMBER() over ("
					+ orderStr + ") temprownumber,* ");
			pagingSelect.append("from (" + addTop(sql) + ") t1 ) t2");
			pagingSelect.append(" where temprownumber>" + offset + ";");
		} else {
			pagingSelect.append("select * from (select row_number() over (order by tempcolumn) temprownumber,* ");
			pagingSelect.append("from (select top " + top + " tempcolumn=0,* from (" + sql + ") t1) t2)");
			pagingSelect.append("t3 where temprownumber>" + offset + ";");
		}
		return pagingSelect;
	}

	private static String addTop(String sql) {
		int startOfSelect = sql.toLowerCase().indexOf("select");
		int startOfDistinct = sql.toLowerCase().indexOf("distinct");
		StringBuilder pagingSelect = new StringBuilder();
		if (!hasDistinct(sql)) {
			pagingSelect.append("select top 100 percent ");
			pagingSelect.append(sql.substring(startOfSelect + 6));
		} else {
			pagingSelect.append("select distinct top 100 percent ");
			pagingSelect.append(sql.substring(startOfDistinct + 8));
		}
		return pagingSelect.toString();
	}

	public String getSelectCountSql(String sql) {
		String countSql = "";
		if (hasOrder(sql)) {
			countSql = addCountSqlTop(sql);
		} else {
			countSql = "select count(1) from (" + sql + ") t";
		}
		return countSql;
	}

	public String addCountSqlTop(String sql) {
		int startOfSelect = sql.trim().toLowerCase().indexOf("select");
		int startOfDistinct = sql.trim().toLowerCase().indexOf("distinct");
		StringBuilder pagingSelect = new StringBuilder();
		if (!hasDistinct(sql)) {
			pagingSelect.append("select top 100 percent ");
			pagingSelect.append(sql.substring(startOfSelect + 6));
		} else {
			pagingSelect.append("select distinct top 100 percent ");
			pagingSelect.append(sql.substring(startOfDistinct + 8));
		}
		return "select count(1) from (" + pagingSelect.toString() + ") t";
	}

	private static boolean hasOrder(String sql) {
		return sql.toLowerCase().indexOf("order by") >= 0;
	}

	private static boolean hasDistinct(String sql) {
		return sql.toLowerCase().indexOf("distinct") >= 0;
	}
}
