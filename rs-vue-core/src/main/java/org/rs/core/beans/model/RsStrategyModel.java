package org.rs.core.beans.model;

import java.util.List;

import org.rs.core.beans.RsStrategy;

public class RsStrategyModel extends RsStrategy{

	private List<RsStrategyInfoModel> clList;

	public List<RsStrategyInfoModel> getClList() {
		return clList;
	}

	public void setClList(List<RsStrategyInfoModel> clList) {
		this.clList = clList;
	}
}
