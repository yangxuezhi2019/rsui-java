package org.rs.core.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.rs.core.beans.RsRoleUser;
import org.rs.core.beans.RsUser;
import org.rs.core.beans.RsUserAuth;
import org.rs.core.beans.RsUserPassword;
import org.rs.core.beans.model.RsUserAllModel;
import org.rs.core.beans.model.RsUserModel;
import org.rs.core.key.RsKeyGenerator;
import org.rs.core.mapper.RsDeptUserMapper;
import org.rs.core.mapper.RsRoleUserMapper;
import org.rs.core.mapper.RsUserAuthMapper;
import org.rs.core.mapper.RsUserMapper;
import org.rs.core.mapper.RsUserModelMapper;
import org.rs.core.mapper.RsUserPasswordMapper;
import org.rs.core.service.RsUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.rs.core.page.IPage;
import org.rs.core.security.authentication.RsLoginUser;

@Service
public class RsUserServiceImpl implements RsUserService {

	private final Logger logger = LoggerFactory.getLogger(RsUserServiceImpl.class);
	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
	
	@Autowired
	private RsUserMapper userMapper;
	@Autowired
	private RsUserModelMapper userModelMapper;
	@Autowired
	private RsKeyGenerator key;
	@Autowired
	private PasswordEncoder pwdEncoder;
	@Autowired
	private RsUserAuthMapper authMapper;
	@Autowired
	private RsRoleUserMapper roleUserMapper;
	@Autowired
	private RsDeptUserMapper deptUserMapper;
	@Autowired
	private RsUserPasswordMapper passwordMapper;

	@Override
	public int insertBean( RsUser bean ) {
		
		if( StringUtils.isEmpty(bean.getYhbh())) {
			bean.setYhbh("U"+key.getZipKey(USER_KEY_NAME, 9));
		}
		//添加用户默认密码
		RsUserPassword password = new RsUserPassword();
		password.setYhbh(bean.getYhbh());
		password.setDlmm("123456");
		password.setCjrbh(bean.getCjrbh());
		password.setCjrmc(bean.getCjrmc());
		password.setCjsj(bean.getCjsj());
		updatePassword(password);
		
		//添加授权类型
		RsUserAuth auth = new RsUserAuth();
		auth.setSqly(0);
		auth.setDlmc(bean.getDlmc());
		auth.setYhbh(bean.getYhbh());
		auth.setCjrbh(bean.getCjrbh());
		auth.setCjrmc(bean.getCjrmc());
		auth.setCjsj(bean.getCjsj());
		
		saveUserAuth(auth);
		return userMapper.insertBean( bean );
	}

	@Override
	public int updateBean( RsUser bean ) {
		
		RsUser dbBean = userMapper.queryBeanById(bean.getYhbh());
		if( dbBean != null ) {
			
			dbBean.setBmbh(bean.getBmbh());
			dbBean.setYhmc(bean.getYhmc());
			dbBean.setDlmc(bean.getDlmc());
			dbBean.setPhone(bean.getPhone());
			dbBean.setAddress(bean.getAddress());
			dbBean.setEmail(bean.getEmail());
			return userMapper.updateBean( dbBean );
		}
		return 0;
	}

	@Override
	public int deleteBean( String yhbh ) {
		
		deptUserMapper.deleteDeptUserByYhbh(yhbh);
		roleUserMapper.deleteRoleUserByYhbh(yhbh);
		authMapper.deleteBeanById(yhbh);
		return userMapper.deleteBeanById( yhbh );
	}

	@Override
	public RsUser queryBean( String yhbh ) {

		 return userMapper.queryBeanById( yhbh );
	}

	@Override
	public List<RsUserModel> queryBeans( Map<String,Object> param, IPage paramPage ) {

		 return userModelMapper.queryBeanList( param, paramPage );
	}
	
	@Override
	public int updateUsersBmbh(List<String> yhbhList, String bmbh ) {
		
		int result = 0;
		for( String yhbh: yhbhList ) {
			result += userModelMapper.updateUserDept(yhbh, bmbh);
		}
		return result;
	}

	@Override
	public int addRoles(RsRoleUser... roles) {
		List<RsRoleUser> roleList = Arrays.asList(roles);
		return addRoles(roleList);
	}

	@Override
	public int addRoles( List<RsRoleUser> roles) {
		
		int result = 0;
		for( RsRoleUser role : roles ) {
			
			result += roleUserMapper.insertRoleUser(role);
		}
		return result;
	}
	
	@Override
	public int delUserRoles(String yhbh, List<String> jsbhList) {
		
		int result = 0;
		for( String jsbh: jsbhList ) {
			
			result += roleUserMapper.deleteRoleUser(jsbh, yhbh);
		}
		return result;
	}
	
	@Override
	public int delRoleUsers(String jsbh, List<String> yhbhList) {
		
		int result = 0;
		for( String yhbh: yhbhList ) {
			
			result += roleUserMapper.deleteRoleUser(jsbh, yhbh);
		}
		return result;
	}

	@Override
	public int updatePassword(RsUserPassword password) {
		
		String encodeDlmm = pwdEncoder.encode(password.getDlmm());
		password.setDlmm(encodeDlmm);
		RsUserPassword dbPassword = passwordMapper.queryBeanById(password.getYhbh());
		if( dbPassword == null ) {
			return passwordMapper.insertBean(password);
		}else {
			dbPassword.setDlmm(password.getDlmm());
		}
		return passwordMapper.updateBean(dbPassword);
	}
	
	@Override
	public RsUserPassword queryUserPassword(String yhbh) {
		
		return passwordMapper.queryBeanById(yhbh);
	}
	
	@Override
	public int saveUserAuth(RsUserAuth auth) {
		
		RsUserAuth dbAuth = authMapper.queryBeanBySqlyAndDlmc(auth.getSqly(), auth.getDlmc());
		if( dbAuth == null ) {
			
			auth.setSqbh("A"+key.getZipKey(USER_AUTH_KEY_NAME, 9));
			return authMapper.insertBean(auth);
		}else {
			if( StringUtils.isEmpty(auth.getSqbh()))
				return 0;
			
			dbAuth = authMapper.queryBeanById(auth.getSqbh());
			if( dbAuth != null ) {
				dbAuth.setSqly(auth.getSqly());
				dbAuth.setDlmc(auth.getDlmc());
				dbAuth.setYhbh(auth.getYhbh());
				return authMapper.updateBean(dbAuth);
			}
		}
		return 0;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		RsUserAuth userAuth = authMapper.queryBeanBySqlyAndDlmc(0, username);
		RsUserAllModel dbBean = null;
		RsUserPassword dbPassword = null;
		if( userAuth != null ) {
			dbBean = userModelMapper.queryBeanHasDeptsByYhbh(userAuth.getYhbh());
			dbPassword = passwordMapper.queryBeanById(userAuth.getYhbh());
		}
		
		if( dbBean == null || dbPassword == null ) {
			this.logger.debug("Query returned no results for user '" + username + "'");

			throw new UsernameNotFoundException(
					this.messages.getMessage("JdbcDaoImpl.notFound",
							new Object[] { username }, "Username {0} not found"));
		}
		RsLoginUser loginUser = new RsLoginUser(dbBean,username,dbPassword.getDlmm(),userAuth.getSqly());
		return loginUser;
	}
}
