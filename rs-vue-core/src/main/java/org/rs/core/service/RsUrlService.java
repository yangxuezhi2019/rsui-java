package org.rs.core.service;

import java.util.List;
import java.util.Map;

import org.rs.core.beans.RsUrl;
import org.rs.core.beans.model.RsUrlRolesModel;
import org.rs.core.page.IPage;

public interface RsUrlService {

	static public final String URL_KEY_NAME = "_RS_URL_KEY_";
	void refreshUrls();
	List<RsUrl> queryBeans(Map<String,Object> param, IPage paramPage);
	void saveUrl( String url, String urlmc, int urllx);
	List<RsUrlRolesModel> queryUrlRolesList(Map<String,Object> param,IPage paramPage);
	List<RsUrlRolesModel> queryUrlRolesAllList();
}
