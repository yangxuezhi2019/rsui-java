package org.rs.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.rs.core.beans.RsMenu;
import org.rs.core.key.RsKeyGenerator;
import org.rs.core.mapper.RsMenuMapper;
import org.rs.core.page.IPage;
import org.rs.core.service.RsMenuService;
import org.rs.core.utils.MenuTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.druid.util.StringUtils;

@Service
public class RsMenuServiceImpl implements RsMenuService{

	@Autowired
	private RsMenuMapper menuMapper;
	@Autowired
	private RsKeyGenerator key;
	
	@Override
	public String genMenuBh() {
		
		return "M"+key.getZipKey(MENU_KEY_NAME, 9);
	}
	@Override
	public int insertBean(RsMenu bean) {
		
		if( StringUtils.isEmpty(bean.getCdbh()) ) {
			bean.setCdbh(genMenuBh());
		}
		if("root".equals(bean.getPcdbh())) {
			
			bean.setPcdbh("M000000000");
		}
		return menuMapper.insertBean(bean);
	}

	@Override
	public int updateBean(RsMenu bean) {
		
		RsMenu oldBean = menuMapper.queryBeanById(bean.getCdbh());
		if( oldBean != null ) {
			oldBean.setCdmc(bean.getCdmc());
			oldBean.setCdxh(bean.getCdxh());
			oldBean.setCdlx(bean.getCdlx());
			oldBean.setIcon(bean.getIcon());
			oldBean.setSfhc(bean.getSfhc());
			
			if( bean.getCdlx().equals(0) ) {
				oldBean.setCdlj("/");
				oldBean.setCdzt(0);
			}else {
				oldBean.setCdlj(bean.getCdlj());
				oldBean.setCdzt(bean.getCdzt());
			}
			
			if("root".equals(bean.getPcdbh())) {
				
				bean.setPcdbh("M000000000");
			}else {
				oldBean.setPcdbh(bean.getPcdbh());
			}
			return menuMapper.updateBean(oldBean);
		}
		return 0;
	}

	@Override
	public int deleteBean(String cdbh) {
		
		return internalDeleteMenu(cdbh);
	}
	
	@Override
	public int deleteBeans(List<String> ids) {
		
		int result = 0;
		for(String cdbh:ids ) {
			
			result += internalDeleteMenu(cdbh);
		}		
		return result;
	}
	
	private int internalDeleteMenu( String cdbh ) {
		
		int result = 0;
		
		if( "root".equals(cdbh)) {
			//delete all menus
			result = menuMapper.deleteBeanByWhereCause("1=1");
		}else {
			Map<String,Object> param = new HashMap<>();
			param.put("pcdbh", cdbh);
			List<RsMenu> list = menuMapper.queryBeanList(param, null);
			if( list.size() > 0 ) {
				
				List<String> ids = list.stream().map(item->item.getCdbh()).collect(Collectors.toList());
				result += deleteBeans(ids);
			}
			result += menuMapper.deleteBeanById(cdbh);
		}
		return result;
	}

	@Override
	public RsMenu queryBean(String cdbh) {
		
		return menuMapper.queryBeanById(cdbh);
	}

	@Override
	public List<RsMenu> queryBeans(Map<String, Object> param, IPage paramPage) {
		
		return menuMapper.queryBeanList(param, paramPage);
	}
	
	@Override
	public List<Map<String,Object>> queryAllMenus() {
		
		List<Map<String,Object>> result = new ArrayList<>();
		Map<String,Object> root = new HashMap<>();
		root.put("cdbh", "root");
		root.put("cdmc", "菜单树");
		root.put("cdlj", "/");
		root.put("cdlx", 0);
		root.put("icon", "rs-icon-home");
		root.put("cdxh", 0);
		result.add(root);
		
		Map<String,Object> param = new HashMap<>();
		param.put("orderCause", "ORDER BY PCDBH,CDXH");
		List<RsMenu> menus = menuMapper.queryBeanList(param, null);
		try {
			
			List<Map<String,Object>> menuList = MenuTools.constructMenuTree(menus, menuMapper);
			root.put("menus", menuList);
		}catch(Exception ex) {
			
		}
		return result;
	}
}
