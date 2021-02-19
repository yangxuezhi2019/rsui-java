package org.rs.core.service;

import java.util.List;
import java.util.Map;
import org.rs.core.beans.RsRole;
import org.rs.core.beans.model.RsRoleModel;
import org.rs.core.page.IPage;

public interface RsRoleService {
	
	static public final String ROLE_KEY_NAME = "_RS_ROLE_KEY_";
	
	int insertBean( RsRole bean );
	int updateBean( RsRole bean );
	int deleteBean( String jsbh );
	RsRole queryBean( String jsbh );
	List<RsRoleModel> queryBeans( Map<String,Object> param, IPage paramPage );
}
