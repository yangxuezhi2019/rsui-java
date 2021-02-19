package org.rs.core.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.rs.core.beans.RsDept;
import org.rs.core.mapper.RsDeptMapper;
import org.rs.core.service.RsDeptService;

public class DeptTools {

	static public List<Map<String,Object>> constructMenuTree(List<RsDept> list, List<String> bmljList, RsDeptMapper deptMapper ) {
		
		List<Map<String,Object>> treeList = new ArrayList<>();
		Map<String,Map<String,Object>> treeMap = new HashMap<>();
		
		for( RsDept item: list ) {
			
			Map<String,Object> wrap = new HashMap<>();
			BeanUtils.objectToMap(item, wrap);
			addTreeItems(list,wrap,treeList,treeMap,bmljList,deptMapper );
		}
		return treeList;
	}
	
	@SuppressWarnings("unchecked")
	static private void addTreeItems(
			List<RsDept> depts,
			Map<String,Object> wrap,
			List<Map<String,Object>> root,
			Map<String,Map<String,Object>> map,
			List<String> bmljList,
			RsDeptMapper deptMapper) {
		
		String bmpbh = MapUtils.getString(wrap, "bmpbh", "");
		String bmbh = MapUtils.getString(wrap, "bmbh", "");
		String bmlj = MapUtils.getString(wrap, "bmlj", "");
		if( RsDeptService.ROOT_KEY.equals(bmpbh)) {
			
			if( !map.containsKey(bmbh)) {
				
				root.add(wrap);
				map.put(bmbh, wrap);
			}
		}else {
			
			Map<String,Object> parentWrap = null;
			if( isMatch(bmljList,bmlj) ) {
				bmpbh = "D00000001";
			}
			parentWrap = map.get( bmpbh );
			List<Map<String,Object>> parentList = null;
			if( parentWrap == null ) {
				
				RsDept parentDept = loadRsDept(bmpbh,depts,deptMapper);
				parentWrap = new HashMap<>();
				BeanUtils.objectToMap(parentDept, parentWrap);
				parentList = new ArrayList<>();
				parentWrap.put("depts",parentList);
				addTreeItems(depts,parentWrap,root,map,bmljList,deptMapper);
			}else {
				
				Object deptsObj = MapUtils.getObject(parentWrap, "depts");
				if( deptsObj == null ) {
					parentList = new ArrayList<>();
					parentWrap.put("depts",parentList);
				}else {
					parentList = (List<Map<String, Object>>) deptsObj;
				}
			}
			parentList.add(wrap);
			map.put(bmbh, wrap);
		}
	}
	
	static private boolean isMatch(List<String> bmljList, String bmlj) {
		
		if( bmljList.size() == 0 )
			return false;
		for( String item : bmljList ) {
			
			if(item.equals(bmlj))
				return true;
		}
		return false;
	}
	
	static private RsDept loadRsDept( String bmbh, List<RsDept> list, RsDeptMapper deptMapper ) {
		
		RsDept dept = null;
		for( RsDept item: list ) {
			
			if(bmbh.equals(item.getBmbh())) {
				
				dept = item;
				break;
			}
		}
		if( dept == null ) {
			
			dept = deptMapper.queryBeanById(bmbh);
		}		
		return dept;
	}
}
