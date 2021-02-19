package org.rs.core.service;

import java.util.List;
import java.util.Map;
import org.rs.core.beans.RsRoleUser;
import org.rs.core.beans.RsUser;
import org.rs.core.beans.RsUserAuth;
import org.rs.core.beans.RsUserPassword;
import org.rs.core.beans.model.RsUserModel;
import org.rs.core.page.IPage;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface RsUserService extends UserDetailsService{

	static public final String ROOT_KEY = "U000000000";
	static public final String USER_KEY_NAME = "_RS_USER_KEY_";
	static public final String USER_AUTH_KEY_NAME = "_RS_USER_AUTH_KEY_";
	int insertBean( RsUser bean );
	int updateBean( RsUser bean );
	int deleteBean( String yhbh );
	RsUser queryBean( String yhbh );
	List<RsUserModel> queryBeans( Map<String,Object> param, IPage paramPage );
	
	int addRoles( RsRoleUser... beans);
	int addRoles( List<RsRoleUser> beans);
	int delUserRoles(String yhbh, List<String> jsbhList);
	int delRoleUsers(String jsbh, List<String> yhbhList);
	int updatePassword(RsUserPassword password);
	RsUserPassword queryUserPassword(String yhbh);
	
	int updateUsersBmbh(List<String> yhbhList, String bmbh );
	
	int saveUserAuth(RsUserAuth auth);
	
	//RsLoginUser loadRsLoginUserByYhbh(String yhbh) throws UsernameNotFoundException;
	
	/*int addDepts(RsDeptUser... beans);
	int addDepts(List<RsDeptUser> beans);
	int delUserDepts(String yhbh, List<String> bmbhList);
	int delDeptUsers(String bmbh, List<String> yhbhList);*/
}
