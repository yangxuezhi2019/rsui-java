package org.rs.core.page;

public class DbPage implements IPage{

	private boolean queryCount = true;
	private int pageSize = 15;
	private int currentPage = 1;
	private int totalCount = 0;
	
	public DbPage() {}
	public DbPage( int page, int pageSize ) {
		
		this.currentPage = page;
		this.pageSize = pageSize;
	}
	
	@Override
	public int getCurrentPage() {
		
		return currentPage;
	}

	@Override
	public int getPageSize() {
		
		return pageSize;
	}

	@Override
	public int getTotalCount() {
		return totalCount;
	}

	@Override
	public boolean isQueryCount() {
		
		return queryCount;
	}

	@Override
	public void setCurrentPage(int currentPage) {
		
		this.currentPage = currentPage;
	}

	@Override
	public void setPageSize(int pageSize) {
		
		this.pageSize = pageSize;
	}

	@Override
	public void setQueryCount(boolean queryCount) {
		
		this.queryCount = queryCount;
	}

	@Override
	public void setTotalCount(int totalCount) {
		
		this.totalCount = totalCount;
	}

	@Override
	public int getOffset() {
		
		return (getCurrentPage() - 1) * getPageSize();
	}

}
