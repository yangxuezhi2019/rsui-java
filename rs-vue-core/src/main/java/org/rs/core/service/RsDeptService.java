package org.rs.core.service;

import java.util.List;
import java.util.Map;
import org.rs.core.beans.RsDept;
import org.rs.core.beans.RsDeptUser;
import org.rs.core.beans.model.RsDeptModel;
import org.rs.core.page.IPage;

public interface RsDeptService {
	static public final String ROOT_KEY = "D00000000";
	static public final String NO_KEY = "FFFFFFFFF";
	static public final String DEPT_KEY_NAME = "_RS_DEPT_KEY_";
	
	String genDeptBh();
	int insertBean( RsDept bean );
	int updateBean( RsDept bean );
	int deleteBean( String bmbh );
	RsDept queryBean( String bmbh );
	List<RsDeptModel> queryBeans( Map<String,Object> param, IPage paramPage );
	
	List<Map<String,Object>> queryDeptTree(Map<String,Object> param, List<String> bmljList, boolean isSysAdmin);
	
	int addDeptUser(RsDeptUser ...beans);
	int addDeptUser(List<RsDeptUser> beans);
	int delDeptUser(RsDeptUser ...beans);
	int delDeptUser(List<RsDeptUser> beans);
	//int addUserToDept(String bmbh, List<String> ids);
}
