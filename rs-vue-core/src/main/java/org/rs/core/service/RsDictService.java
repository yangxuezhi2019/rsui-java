package org.rs.core.service;

import java.util.List;
import java.util.Map;
import org.rs.core.beans.RsDict;
import org.rs.core.beans.RsDictInfo;
import org.rs.core.page.IPage;

public interface RsDictService {
	
	static public final String DICT_KEY_NAME = "_RS_DICT_KEY_";
	static public final String DICT_INFO_KEY_NAME = "_RS_DICT_INFO_KEY_";
	
	int insertBean( RsDict bean );
	int updateBean( RsDict bean );
	int deleteBean( String zdzj );
	RsDict queryBean( String zdzj );
	List<RsDict> queryBeans( Map<String,Object> param, IPage paramPage );
	
	String getDictKey();
	String getDictInfoKey();
	
	List<RsDictInfo> queryInfoBeans(Map<String,Object> param, IPage paramPage);
	int insertInfoBean(RsDictInfo bean);
	int updateInfoBean(RsDictInfo bean);
	int deleteInfoBean(String tmzj);
}
