package org.rs.core.page;

public abstract interface IDialect {
	public abstract StringBuilder bulildPageSql(String paramString, IPage paramIPage);

	public abstract boolean supportsPaged();

	public abstract String getSelectCountSql(String paramString);

}
