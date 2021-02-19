package org.rs.core.service.impl;

import java.util.List;
import java.util.Map;
import org.rs.core.beans.RsDict;
import org.rs.core.beans.RsDictInfo;
import org.rs.core.key.RsKeyGenerator;
import org.rs.core.mapper.RsDictInfoMapper;
import org.rs.core.mapper.RsDictMapper;
import org.rs.core.service.RsDictService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.rs.core.page.IPage;

@Service
public class RsDictServiceImpl implements RsDictService {

	@Autowired
	private RsDictMapper dictMapper;
	@Autowired
	private RsDictInfoMapper dictInfoMapper;
	@Autowired
	private RsKeyGenerator key;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public String getDictKey() {
		
		return "D"+key.getZipKey(DICT_KEY_NAME, 8);
	}
	
	@Override
	public String getDictInfoKey() {
		return "I"+key.getZipKey(DICT_KEY_NAME, 8);
	}

	@Override
	public int insertBean( RsDict bean ) {

		bean.setZdzj(getDictKey());
		return dictMapper.insertBean( bean );
	}

	@Override
	public int updateBean( RsDict bean ) {
		
		RsDict dbBean = dictMapper.queryBeanById(bean.getZdzj());
		if( dbBean != null ) {
			
			String oldZdbh = dbBean.getZdbh();
			dbBean.setZdmc(bean.getZdmc());
			dbBean.setZdbh(bean.getZdbh());
			dbBean.setZdlx(bean.getZdlx());
			dbBean.setZdxh(bean.getZdxh());
			dictMapper.updateBean( dbBean );
			
			if( !oldZdbh.equals(dbBean.getZdbh())) {
				
				jdbcTemplate.update("update rs_dict_info set zdbh=? where zdbh=?",new Object[] {dbBean.getZdbh(),oldZdbh});
			}
		}
		return 0;
	}

	@Override
	public int deleteBean( String zdzj ) {
		
		RsDict dbBean = dictMapper.queryBeanById(zdzj);
		dictInfoMapper.deleteBeanByWhereCause("zdbh='" + dbBean.getZdbh() + "'");
		return dictMapper.deleteBeanById( zdzj );
	}

	@Override
	public RsDict queryBean( String zdzj ) {

		 return dictMapper.queryBeanById( zdzj );
	}

	@Override
	public List<RsDict> queryBeans( Map<String,Object> param, IPage paramPage ) {

		 return dictMapper.queryBeanList( param, paramPage );
	}

	@Override
	public List<RsDictInfo> queryInfoBeans(Map<String,Object> param, IPage paramPage) {
		
		return dictInfoMapper.queryBeanList( param, paramPage );
	}

	@Override
	public int insertInfoBean(RsDictInfo bean) {
		bean.setTmzj(getDictInfoKey());
		return dictInfoMapper.insertBean( bean );
	}

	@Override
	public int updateInfoBean(RsDictInfo bean) {
		
		RsDictInfo dbBean = dictInfoMapper.queryBeanById(bean.getTmzj());
		if( dbBean != null ) {
			dbBean.setTmmc(bean.getTmmc());
			dbBean.setTmbh(bean.getTmbh());
			dbBean.setTmpbh(bean.getTmpbh());
			dbBean.setTmxh(bean.getTmxh());
			dictInfoMapper.updateBean( dbBean );
		}
		return 0;
	}

	@Override
	public int deleteInfoBean(String tmzj) {
		return dictInfoMapper.deleteBeanById(tmzj);
	}
	
	
}
