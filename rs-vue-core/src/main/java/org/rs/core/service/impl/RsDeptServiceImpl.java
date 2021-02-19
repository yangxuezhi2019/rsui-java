package org.rs.core.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.rs.core.beans.RsDept;
import org.rs.core.beans.RsDeptUser;
import org.rs.core.beans.model.RsDeptModel;
import org.rs.core.key.RsKeyGenerator;
import org.rs.core.mapper.RsDeptMapper;
import org.rs.core.mapper.RsDeptModelMapper;
import org.rs.core.mapper.RsDeptUserMapper;
import org.rs.core.service.RsDeptService;
import org.rs.core.utils.DeptTools;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.rs.core.page.IPage;

@Service
public class RsDeptServiceImpl implements RsDeptService {

	@Autowired
	private RsDeptMapper deptMapper;
	@Autowired
	private RsDeptModelMapper deptModelMapper;
	@Autowired
	private RsKeyGenerator key;
	@Autowired
	private RsDeptUserMapper deptUserMapper;
	
	@Override
	public String genDeptBh() {
		
		return "D"+key.getZipKey(DEPT_KEY_NAME, 8);
	}

	@Override
	public int insertBean( RsDept bean ) {

		if( StringUtils.isEmpty(bean.getBmbh())) {
			bean.setBmbh(genDeptBh());
		}
		
		if( StringUtils.isEmpty(bean.getBmdm()) ) {
			bean.setBmdm(bean.getBmbh());
		}
		
		if( !ROOT_KEY.equals(bean.getBmpbh())) {
			RsDept parent = deptMapper.queryBeanById(bean.getBmpbh());		
			bean.setBmcj(parent.getBmcj() + 1);
			bean.setBmlj(parent.getBmlj() + "," + bean.getBmbh());
		}
		return deptMapper.insertBean( bean );
	}

	@Override
	public int updateBean( RsDept bean ) {
		
		RsDept dbBean = deptMapper.queryBeanById(bean.getBmbh());
		if( dbBean != null ) {
			dbBean.setBmmc(bean.getBmmc());
			dbBean.setBmxh(bean.getBmxh());
			//dbBean.setBmzt(bean.getBmzt());
			return deptMapper.updateBean( dbBean );
		}
		return 0;
	}

	@Override
	public int deleteBean( String bmbh ) {
		
		Map<String,Object> param = new HashMap<>();
		param.put("bmpbh", bmbh);
		List<RsDept> list = deptMapper.queryBeanList(param,null);
		for( RsDept item: list ) {
			
			deleteBean(item.getBmbh());
		}
		deptUserMapper.deleteDeptUserByBmbh(bmbh);
		return deptMapper.deleteBeanById( bmbh );
	}

	@Override
	public RsDept queryBean( String bmbh ) {

		 return deptMapper.queryBeanById( bmbh );
	}

	@Override
	public List<RsDeptModel> queryBeans( Map<String,Object> param, IPage paramPage ) {

		 return deptModelMapper.queryBeanList( param, paramPage );
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> queryDeptTree(Map<String,Object> param, List<String> bmljList, boolean isSysAdmin){
		
		param.put("orderCause", "ORDER BY BMCJ,BMXH,BMBH");
		List<RsDept> list = deptMapper.queryBeanList(param, null);
		List<Map<String,Object>> result = DeptTools.constructMenuTree(list, bmljList, deptMapper);
		if( !isSysAdmin ) {
			
			Map<String,Object> root = result.get(0);
			Object deptsObj = null;
			if( root != null ) {
				deptsObj = root.get("depts");
			}
			if( deptsObj == null ) {
				result = new ArrayList<>();
			}else {
				result = (List<Map<String, Object>>) deptsObj;
			}
		}
		return result;
	}
	
	@Override
	public int addDeptUser(RsDeptUser ...beans) {
		
		List<RsDeptUser> roleList = Arrays.asList(beans);
		return addDeptUser(roleList);
	}
	
	@Override
	public int addDeptUser(List<RsDeptUser> beans) {
		
		int result = 0;
		for( RsDeptUser bean: beans) {
			
			result += deptUserMapper.insertDeptUser(bean);
		}		
		return result;
	}
	
	@Override
	public int delDeptUser(RsDeptUser ...beans) {
		List<RsDeptUser> roleList = Arrays.asList(beans);
		return delDeptUser(roleList);
	}
	@Override
	public int delDeptUser(List<RsDeptUser> beans) {
		int result = 0;
		for( RsDeptUser bean: beans) {
			
			result += deptUserMapper.deleteDeptUser(bean);
		}		
		return result;
	}
}
