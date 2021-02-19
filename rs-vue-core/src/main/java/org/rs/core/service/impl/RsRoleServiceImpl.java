package org.rs.core.service.impl;

import java.util.List;
import java.util.Map;
import org.rs.core.beans.RsRole;
import org.rs.core.beans.model.RsRoleModel;
import org.rs.core.event.RsSecurityEventPublisher;
import org.rs.core.key.RsKeyGenerator;
import org.rs.core.mapper.RsRoleMapper;
import org.rs.core.mapper.RsRoleMenuMapper;
import org.rs.core.mapper.RsRoleModelMapper;
import org.rs.core.mapper.RsRoleUrlMapper;
import org.rs.core.mapper.RsRoleUserMapper;
import org.rs.core.service.RsRoleService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.rs.core.page.IPage;

@Service
public class RsRoleServiceImpl implements RsRoleService {

	@Autowired
	private RsRoleMapper roleMapper;
	@Autowired
	private RsRoleModelMapper roleModelMapper;
	@Autowired
	private RsKeyGenerator key;
	@Autowired
	private RsRoleUrlMapper roleUrlMapper;
	@Autowired
	private RsRoleMenuMapper roleMenuMapper;
	@Autowired
	private RsRoleUserMapper roleUserMapper;
	@Autowired(required=false)
	private RsSecurityEventPublisher rsSecurityEventPublisher;

	@Override
	public int insertBean( RsRole bean ) {

		bean.setJsbh("R"+key.getZipKey(ROLE_KEY_NAME, 8));
		return roleMapper.insertBean( bean );
	}

	@Override
	public int updateBean( RsRole bean ) {
		
		RsRole dbBean = roleMapper.queryBeanById(bean.getJsbh());
		if( dbBean != null ) {
			
			dbBean.setJsbh(bean.getJsbh());
			dbBean.setJsmc(bean.getJsmc());
			dbBean.setJslx(bean.getJslx());
			dbBean.setJsjb(bean.getJsjb());
			dbBean.setJsxh(bean.getJsxh());
			return roleMapper.updateBean( bean );
		}
		return 0;
	}

	@Override
	public int deleteBean( String jsbh ) {
		
		roleMenuMapper.deleteRoleMenuByJsbh(jsbh);
		roleUrlMapper.deleteRoleUrlByJsbh(jsbh);
		roleUserMapper.deleteRoleUserByJsbh(jsbh);
		
		int result = roleMapper.deleteBeanById( jsbh );
		//重新加载授权
		if( rsSecurityEventPublisher != null ) {
			rsSecurityEventPublisher.refreshMetadataSource();
		}
		return result;
	}

	@Override
	public RsRole queryBean( String jsbh ) {

		 return roleMapper.queryBeanById( jsbh );
	}

	@Override
	public List<RsRoleModel> queryBeans( Map<String,Object> param, IPage paramPage ) {

		 return roleModelMapper.queryBeanList( param, paramPage );
	}
}
