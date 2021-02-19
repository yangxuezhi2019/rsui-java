package org.rs.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.rs.ConfigTest;
import org.rs.core.beans.RsDept;
import org.rs.core.beans.RsDict;
import org.rs.core.beans.RsDictInfo;
import org.rs.core.beans.RsMenu;
import org.rs.core.beans.RsRole;
import org.rs.core.beans.RsRoleMenu;
import org.rs.core.beans.RsRoleUser;
import org.rs.core.beans.RsStrategy;
import org.rs.core.beans.RsStrategyDesc;
import org.rs.core.beans.RsStrategyInfo;
import org.rs.core.beans.RsUser;
import org.rs.core.service.RsAuthService;
import org.rs.core.service.RsDeptService;
import org.rs.core.service.RsDictService;
import org.rs.core.service.RsMenuService;
import org.rs.core.service.RsRoleService;
import org.rs.core.service.RsStrategyService;
import org.rs.core.service.RsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)   
@SpringBootTest(classes={ConfigTest.class},webEnvironment = WebEnvironment.NONE )
public class DbInitTest {

	@Autowired
	private RsUserService userService;
	@Autowired
	private RsDeptService deptService;
	@Autowired
	private RsRoleService roleService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private RsMenuService menuService;
	@Autowired
	private RsDictService dictService;
	@Autowired
	private RsStrategyService strategyService;
	@Autowired
	private RsAuthService authService;
	/*@Autowired
	private RsUrlService urlService;*/
	
	@Test
	public void initDb() {
		
		jdbcTemplate.execute("truncate table rs_key_table");
		jdbcTemplate.execute("truncate table rs_user");
		jdbcTemplate.execute("truncate table rs_user_auth");
		jdbcTemplate.execute("truncate table rs_role");
		jdbcTemplate.execute("truncate table rs_role_user");
		jdbcTemplate.execute("truncate table rs_dept");
		jdbcTemplate.execute("truncate table rs_dept_user");
		jdbcTemplate.execute("truncate table rs_menu");
		jdbcTemplate.execute("truncate table rs_role_menu");
		jdbcTemplate.execute("truncate table rs_dict");
		jdbcTemplate.execute("truncate table rs_dict_info");
		jdbcTemplate.execute("truncate table rs_strategy");
		jdbcTemplate.execute("truncate table rs_strategy_info");
		jdbcTemplate.execute("truncate table rs_strategy_desc");
		jdbcTemplate.execute("truncate table rs_access_log");
		jdbcTemplate.execute("truncate table rs_login_log");
		
		String rootBmbh = deptService.genDeptBh();
		RsDept dept = new RsDept();
		dept.setBmbh(rootBmbh);
		dept.setBmdm(rootBmbh);
		dept.setBmmc("系统根机构");
		dept.setBmlj(rootBmbh);
		dept.setBmpbh(RsDeptService.ROOT_KEY);
		dept.setBmxh(0);
		dept.setBmzt(0);
		dept.setBmcj(0);
		dept.setBmfh(0);
		dept.setCjrbh(RsUserService.ROOT_KEY);
		dept.setCjrmc("系统管理员");
		dept.setCjsj(new Date());
		deptService.insertBean(dept);
		
		/*dept = new RsDept();
		dept.setBmbh(RsDeptService.NO_KEY);
		dept.setBmdm(RsDeptService.NO_KEY);
		dept.setBmmc("暂无归属部门");
		dept.setBmlj(RsDeptService.NO_KEY);
		dept.setBmpbh(RsDeptService.ROOT_KEY);
		dept.setBmxh(65535);
		dept.setBmzt(0);
		dept.setBmcj(0);
		dept.setBmfh(0);
		dept.setCjrbh(RsUserService.ROOT_KEY);
		dept.setCjrmc("系统管理员");
		dept.setCjsj(new Date());
		deptService.insertBean(dept);*/
		
		String bmbh = deptService.genDeptBh();
		dept = new RsDept();
		dept.setBmbh(bmbh);
		dept.setBmdm(bmbh);
		dept.setBmmc("石家庄公司");
		dept.setBmlj(rootBmbh + "," + bmbh);
		dept.setBmpbh(rootBmbh);
		dept.setBmxh(0);
		dept.setBmzt(0);
		dept.setBmcj(1);
		dept.setBmfh(0);
		dept.setCjrbh(RsUserService.ROOT_KEY);
		dept.setCjrmc("系统管理员");
		dept.setCjsj(new Date());
		deptService.insertBean(dept);
		
		bmbh = deptService.genDeptBh();
		dept = new RsDept();
		dept.setBmbh(bmbh);
		dept.setBmdm(bmbh);
		dept.setBmmc("唐山公司");
		dept.setBmlj(rootBmbh + "," + bmbh);
		dept.setBmpbh(rootBmbh);
		dept.setBmxh(1);
		dept.setBmzt(0);
		dept.setBmcj(1);
		dept.setBmfh(0);
		dept.setCjrbh(RsUserService.ROOT_KEY);
		dept.setCjrmc("系统管理员");
		dept.setCjsj(new Date());
		deptService.insertBean(dept);
		
		bmbh = deptService.genDeptBh();
		dept = new RsDept();
		dept.setBmbh(bmbh);
		dept.setBmdm(bmbh);
		dept.setBmmc("保定公司");
		dept.setBmlj(rootBmbh + "," + bmbh);
		dept.setBmpbh(rootBmbh);
		dept.setBmxh(2);
		dept.setBmzt(0);
		dept.setBmcj(1);
		dept.setBmfh(0);
		dept.setCjrbh(RsUserService.ROOT_KEY);
		dept.setCjrmc("系统管理员");
		dept.setCjsj(new Date());
		deptService.insertBean(dept);
		
		bmbh = deptService.genDeptBh();
		dept = new RsDept();
		dept.setBmbh(bmbh);
		dept.setBmdm(bmbh);
		dept.setBmmc("邯郸公司");
		dept.setBmlj(rootBmbh + "," + bmbh);
		dept.setBmpbh(rootBmbh);
		dept.setBmxh(3);
		dept.setBmzt(0);
		dept.setBmcj(1);
		dept.setBmfh(0);
		dept.setCjrbh(RsUserService.ROOT_KEY);
		dept.setCjrmc("系统管理员");
		dept.setCjsj(new Date());
		deptService.insertBean(dept);
		
		RsUser sysAdmin = new RsUser();
		sysAdmin.setYhbh(RsUserService.ROOT_KEY);
		sysAdmin.setYhmc("系统管理员");
		sysAdmin.setDlmc("sysadmin");
		sysAdmin.setCqjc(0);
		sysAdmin.setSffh(0);
		sysAdmin.setYhzt(0);
		sysAdmin.setDlcs(0);
		sysAdmin.setYhlx(0);
		sysAdmin.setBmbh(rootBmbh);
		sysAdmin.setCjrbh(RsUserService.ROOT_KEY);
		sysAdmin.setCjrmc("系统管理员");
		sysAdmin.setCjsj(new Date());
		userService.insertBean(sysAdmin);
		
		RsUser sysUser = new RsUser();
		sysUser.setYhmc("普通用户");
		sysUser.setDlmc("sysuser");
		sysUser.setCqjc(0);
		sysUser.setSffh(0);
		sysUser.setYhzt(0);
		sysUser.setDlcs(0);
		sysUser.setYhlx(1);
		sysUser.setBmbh(rootBmbh);
		sysUser.setCjrbh(RsUserService.ROOT_KEY);
		sysUser.setCjrmc("系统管理员");
		sysUser.setCjsj(new Date());
		userService.insertBean(sysUser);
		
		/*RsDeptUser deptUser = new RsDeptUser();
		deptUser.setYhbh(RsUserService.ROOT_KEY);
		deptUser.setBmbh(rootBmbh);
		deptUser.setCjrbh(RsUserService.ROOT_KEY);
		deptUser.setCjrmc("系统管理员");
		deptUser.setCjsj(new Date());
		deptService.addDeptUser(deptUser);*/
		
		RsRole role = new RsRole();
		role.setJsnm("ROLE_SYSADMIN");
		role.setJsmc("系统管理员");
		role.setJslx(0);
		role.setJsxh(0);
		role.setJsjb(0);
		role.setJszt(0);
		role.setCjrbh(RsUserService.ROOT_KEY);
		role.setCjrmc("系统管理员");
		role.setCjsj(new Date());
		roleService.insertBean(role);
		
		String roleAdminBh = role.getJsbh();
		
		role.setJsnm("ROLE_USER");
		role.setJsmc("普通用户");
		role.setJslx(1);
		role.setJsxh(1);
		role.setJsjb(1);
		role.setJszt(0);
		role.setCjrbh(RsUserService.ROOT_KEY);
		role.setCjrmc("系统管理员");
		role.setCjsj(new Date());
		roleService.insertBean(role);
		
		String roleUserBh = role.getJsbh();
		
		RsRoleUser sysRoleAdmin = new RsRoleUser();
		sysRoleAdmin.setJsbh(roleAdminBh);
		sysRoleAdmin.setYhbh(sysAdmin.getYhbh());
		sysRoleAdmin.setCjrbh(RsUserService.ROOT_KEY);
		sysRoleAdmin.setCjrmc("系统管理员");
		sysRoleAdmin.setCjsj(new Date());
		userService.addRoles(sysRoleAdmin);
		
		RsRoleUser roleUser = new RsRoleUser();
		roleUser.setJsbh(roleUserBh);
		roleUser.setYhbh(sysUser.getYhbh());
		roleUser.setCjrbh(RsUserService.ROOT_KEY);
		roleUser.setCjrmc("系统管理员");
		roleUser.setCjsj(new Date());
		userService.addRoles(roleUser);
		
		//UserDetails loginUser = userService.loadUserByUsername("sysadmin");
		//System.out.println(loginUser);
		
		//urlService.saveUrl("/**", "全部URL", 0);
		//RsRoleUrl roleUrlbean = new RsRoleUrl();
		//roleUrlbean.setJsbh(roleAdminBh);
		//roleUrlbean.setUrl("/**/*");
		//roleUrlbean.setCjrbh(RsUserService.ROOT_KEY);
		//roleUrlbean.setCjrmc("系统管理员");
		//roleUrlbean.setCjsj(new Date());
		//authService.insertRoleUrl(roleUrlbean);
		
		List<String> menuBhList = new ArrayList<>();
		initMgmtMenus(menuBhList);
		//添加菜单权限
		initRoleMenus(roleAdminBh,menuBhList);
		
		initMenuDicts();
		initRoleDicts();
		
		initStrategyDesc();
		initStrategy();
	}
	
	private void initRoleMenus( String jsbh, List<String> menuBhList) {
		
		for( String cdbh : menuBhList ) {
			
			RsRoleMenu bean = new RsRoleMenu();
			bean.setJsbh(jsbh);
			bean.setCdbh(cdbh);
			bean.setCjrbh(RsUserService.ROOT_KEY);
			bean.setCjrmc("系统管理员");
			bean.setCjsj(new Date());
			
			authService.updateRoleMenu(bean);
		}
	}
	
	private void initMgmtMenus(List<String> menuBhList) {
		
		String rootBh = RsMenuService.ROOT_KEY;
		String mgmtCdbh = menuService.genMenuBh();
		RsMenu menu = createMenu(mgmtCdbh,"系统管理","rs-icon-mgmt","/",0,rootBh,0);
		menuService.insertBean(menu);
		menuBhList.add(mgmtCdbh);
		
		int pxh = 0;
		String cdbh = menuService.genMenuBh();
		menu = createMenu(cdbh,"用户管理","rs-icon-user", "/sys/user", 1, mgmtCdbh,pxh++);
		menuService.insertBean(menu);
		menuBhList.add(cdbh);
		
		cdbh = menuService.genMenuBh();
		menu = createMenu(cdbh,"机构管理","rs-icon-unit", "/sys/unit", 1, mgmtCdbh,pxh++);
		menuService.insertBean(menu);
		menuBhList.add(cdbh);
		
		cdbh = menuService.genMenuBh();
		menu = createMenu(cdbh,"角色管理","rs-icon-role", "/sys/role", 1, mgmtCdbh,pxh++);
		menuService.insertBean(menu);
		menuBhList.add(cdbh);
		
		cdbh = menuService.genMenuBh();
		menu = createMenu(cdbh,"菜单管理","el-icon-menu", "/sys/menu", 1, mgmtCdbh,pxh++);
		menuService.insertBean(menu);
		menuBhList.add(cdbh);
		
		cdbh = menuService.genMenuBh();
		menu = createMenu(cdbh,"权限管理","rs-icon-auth", "/sys/auth", 1, mgmtCdbh,pxh++);
		menuService.insertBean(menu);
		menuBhList.add(cdbh);
		
		String setCdbh = menuService.genMenuBh();
		menu = createMenu(setCdbh,"系统配置","rs-icon-setting", "/", 0, mgmtCdbh,pxh++);
		menuService.insertBean(menu);
		menuBhList.add(cdbh);
		
		cdbh = menuService.genMenuBh();
		menu = createMenu(cdbh,"字典配置","rs-icon-dict", "/sys/dict", 1, setCdbh,pxh++);
		menuService.insertBean(menu);
		menuBhList.add(cdbh);
		
		cdbh = menuService.genMenuBh();
		menu = createMenu(cdbh,"策略配置","rs-icon-anquan", "/sys/strategy", 1, setCdbh,pxh++);
		menuService.insertBean(menu);
		menuBhList.add(cdbh);
		
		mgmtCdbh = menuService.genMenuBh();
		menu = createMenu(mgmtCdbh,"管理信息","rs-icon-theme","/",0,rootBh,1);
		menuService.insertBean(menu);
		menuBhList.add(mgmtCdbh);
		
		pxh = 0;
		cdbh = menuService.genMenuBh();
		menu = createMenu(cdbh,"系统日志","rs-icon-auth", "/sys/log", 1, mgmtCdbh,pxh++);
		menuService.insertBean(menu);
		menuBhList.add(cdbh);
		
		cdbh = menuService.genMenuBh();
		menu = createMenu(cdbh,"系统信息","rs-icon-auth", "/sys/info", 1, mgmtCdbh,pxh++);
		menuService.insertBean(menu);
		menuBhList.add(cdbh);
		
		
		/*{path:'user', name:'UserPage',title:'用户管理',icon:'rs-icon-user',keepAlive:true},
		{path:'unit', name:'UnitPage',title:'机构管理',icon:'rs-icon-unit',keepAlive:true},
		{path:'role', name:'RolePage',title:'角色管理',icon:'rs-icon-role',keepAlive:true},
		{path:'menu', name:'MenuPage',title:'菜单管理',icon:'el-icon-menu',keepAlive:true},
		{path:'auth', name:'AuthPage',title:'权限管理',icon:'rs-icon-auth',keepAlive:true}*/
	}
	
	private RsMenu createMenu(String cdbh, String cdmc, String icon, String cdlj, int cdlx, String pcdbh, int pxh) {
		
		RsMenu menu = new RsMenu();
		menu.setCdbh(cdbh);
		menu.setCdmc(cdmc);
		menu.setCdlj(cdlj);
		menu.setPcdbh(pcdbh);
		menu.setCdxh(pxh);
		menu.setIcon(icon);
		menu.setCdlx(cdlx);
		menu.setSfhc(1);
		menu.setCdzt(0);
		menu.setCjrbh(RsUserService.ROOT_KEY);
		menu.setCjrmc("系统管理员");
		menu.setCjsj(new Date());		
		return menu;
	}
	
	private void initMenuDicts() {
		
		RsDict dict = new RsDict();
		dict.setZdbh("menuType");
		dict.setZdlx(0);
		dict.setZdmc("菜单类型");
		dict.setZdjg(0);
		dict.setZdxh(0);
		dict.setZdzt(0);
		dict.setCjrbh(RsUserService.ROOT_KEY);
		dict.setCjrmc("系统管理员");
		dict.setCjsj(new Date());	
		dictService.insertBean(dict);
		
		RsDictInfo info = new RsDictInfo();
		info.setZdbh("menuType");
		info.setTmpbh("");
		info.setTmbh("0");
		info.setTmmc("子菜单");
		info.setTmxh(0);
		info.setTmzt(0);
		info.setCjrbh(RsUserService.ROOT_KEY);
		info.setCjrmc("系统管理员");
		info.setCjsj(new Date());
		dictService.insertInfoBean(info);
		
		info = new RsDictInfo();
		info.setZdbh("menuType");
		info.setTmpbh("");
		info.setTmbh("1");
		info.setTmmc("路径");
		info.setTmxh(1);
		info.setTmzt(0);
		info.setCjrbh(RsUserService.ROOT_KEY);
		info.setCjrmc("系统管理员");
		info.setCjsj(new Date());
		dictService.insertInfoBean(info);
	}
	
	private void initRoleDicts() {
		
		RsDict dict = new RsDict();
		dict.setZdbh("roleType");
		dict.setZdlx(0);
		dict.setZdmc("角色类型");
		dict.setZdjg(0);
		dict.setZdxh(1);
		dict.setZdzt(0);
		dict.setCjrbh(RsUserService.ROOT_KEY);
		dict.setCjrmc("系统管理员");
		dict.setCjsj(new Date());	
		dictService.insertBean(dict);
		
		RsDictInfo info = new RsDictInfo();
		info.setZdbh("roleType");
		info.setTmpbh("");
		info.setTmbh("0");
		info.setTmmc("管理角色");
		info.setTmxh(0);
		info.setTmzt(0);
		info.setCjrbh(RsUserService.ROOT_KEY);
		info.setCjrmc("系统管理员");
		info.setCjsj(new Date());
		dictService.insertInfoBean(info);
		
		info.setTmbh("1");
		info.setTmmc("普通角色");
		info.setTmxh(1);
		dictService.insertInfoBean(info);
		
		dict.setZdbh("roleLevel");
		dict.setZdmc("角色级别");
		dict.setZdxh(2);
		dictService.insertBean(dict);
		
		info.setZdbh("roleLevel");
		info.setTmbh("0");
		info.setTmmc("级别0");
		info.setTmxh(0);
		dictService.insertInfoBean(info);
		
		info.setTmbh("1");
		info.setTmmc("级别1");
		info.setTmxh(1);
		dictService.insertInfoBean(info);
		
		info.setTmbh("2");
		info.setTmmc("级别2");
		info.setTmxh(2);
		dictService.insertInfoBean(info);
		
		info.setTmbh("3");
		info.setTmmc("级别3");
		info.setTmxh(3);
		dictService.insertInfoBean(info);
		
		info.setTmbh("4");
		info.setTmmc("级别4");
		info.setTmxh(4);
		dictService.insertInfoBean(info);
		
		info.setTmbh("5");
		info.setTmmc("级别5");
		info.setTmxh(5);
		dictService.insertInfoBean(info);
	}
	
	private void initStrategyDesc() {
		
		int msxh = 0;
		insertStrategyDesc("USER_SESSION","一个用户允许的会话数(0:不检测)",msxh++);
		insertStrategyDesc("IS_KICK_SESSION","一个用户达到最大会话数后是否允许登录",msxh++);
		insertStrategyDesc("MAX_SESSION_COUNT","系统允许最大会话总数(0:不检测)",msxh++);
		insertStrategyDesc("SESSION_CONTROL","会话控制(有:yes,没有:no)",msxh++);
		insertStrategyDesc("LOGIN_CHECK","登录是否需要检验码(是:yes,不是:no)",msxh++);
		insertStrategyDesc("IS_AUTO_ENABLE","是否自动解锁用户(是:yes,不是:no)",msxh++);
		insertStrategyDesc("AUTO_ENABLE_TIME","自动解锁用户时间（小时）",msxh++);
		insertStrategyDesc("DEFAULT_PASSWORD","系统默认密码",msxh++);
		insertStrategyDesc("PASSWORD_EXPIRE_DAYS","密码有效期（天）",msxh++);
		insertStrategyDesc("MAX_FAILED_LOGIN_COUNT","密码允许的最多错误次数(0:不检测)",msxh++);
		insertStrategyDesc("PASSWORD_MODIFIED_TIMES","密码不可与前几次历史密码重复(0:不检测)",msxh++);
		insertStrategyDesc("PASSWORD_MIN_LENGTH","密码最短的长度限制(0:不检测)",msxh++);
		insertStrategyDesc("ALLOW_LOGIN_FAIL_TIMES","登录出错门限(0:不检测)",msxh++);
		insertStrategyDesc("PASSWORD_LEVEL","密码强度等级",msxh++);
		insertStrategyDesc("FIRST_MODIFY_PASSWORD","首次登录修改密码(是:yes,不是:no)",msxh++);
	}
	
	private void initStrateyInfo( String clbh ) {
		
		int tmxh = 0;
		if(clbh.equals("level2")) {
			insertStrateyInfo(clbh,"USER_SESSION","2",tmxh++);
			insertStrateyInfo(clbh,"IS_KICK_SESSION","0",tmxh++);
			insertStrateyInfo(clbh,"MAX_SESSION_COUNT","0",tmxh++);
			insertStrateyInfo(clbh,"SESSION_CONTROL","0",tmxh++);
			insertStrateyInfo(clbh,"LOGIN_CHECK","0",tmxh++);
			insertStrateyInfo(clbh,"PASSWORD_LEVEL","1",tmxh++);
			insertStrateyInfo(clbh,"PASSWORD_EXPIRE_DAYS","30",tmxh++);
			insertStrateyInfo(clbh,"PASSWORD_MODIFIED_TIMES","1",tmxh++);
			insertStrateyInfo(clbh,"FIRST_MODIFY_PASSWORD","1",tmxh++);	
		}else if(clbh.equals("level3")) {
			insertStrateyInfo(clbh,"USER_SESSION","1",tmxh++);
			insertStrateyInfo(clbh,"IS_KICK_SESSION","0",tmxh++);
			insertStrateyInfo(clbh,"MAX_SESSION_COUNT","100",tmxh++);
			insertStrateyInfo(clbh,"SESSION_CONTROL","1",tmxh++);
			insertStrateyInfo(clbh,"LOGIN_CHECK","1",tmxh++);
			insertStrateyInfo(clbh,"PASSWORD_LEVEL","2",tmxh++);
			insertStrateyInfo(clbh,"PASSWORD_EXPIRE_DAYS","7",tmxh++);
			insertStrateyInfo(clbh,"PASSWORD_MODIFIED_TIMES","3",tmxh++);
			insertStrateyInfo(clbh,"FIRST_MODIFY_PASSWORD","1",tmxh++);	
		}else {
			insertStrateyInfo(clbh,"USER_SESSION","0",tmxh++);
			insertStrateyInfo(clbh,"IS_KICK_SESSION","1",tmxh++);
			insertStrateyInfo(clbh,"MAX_SESSION_COUNT","0",tmxh++);
			insertStrateyInfo(clbh,"SESSION_CONTROL","0",tmxh++);
			insertStrateyInfo(clbh,"LOGIN_CHECK","0",tmxh++);
			insertStrateyInfo(clbh,"PASSWORD_LEVEL","0",tmxh++);
			insertStrateyInfo(clbh,"PASSWORD_EXPIRE_DAYS","0",tmxh++);
			insertStrateyInfo(clbh,"PASSWORD_MODIFIED_TIMES","0",tmxh++);
			insertStrateyInfo(clbh,"FIRST_MODIFY_PASSWORD","0",tmxh++);
		}
		insertStrateyInfo(clbh,"IS_AUTO_ENABLE","0",tmxh++);
		insertStrateyInfo(clbh,"AUTO_ENABLE_TIME","0",tmxh++);
		insertStrateyInfo(clbh,"DEFAULT_PASSWORD","123456",tmxh++);
		insertStrateyInfo(clbh,"MAX_FAILED_LOGIN_COUNT","0",tmxh++);
		insertStrateyInfo(clbh,"PASSWORD_MIN_LENGTH","6",tmxh++);
		insertStrateyInfo(clbh,"ALLOW_LOGIN_FAIL_TIMES","0",tmxh++);	
	}
	
	private void insertStrategyDesc(String msbh, String msmc, int msxh) {
		
		RsStrategyDesc bean = new RsStrategyDesc();
		bean.setMsbh(msbh);
		bean.setMsmc(msmc);
		bean.setMsxh(msxh);
		strategyService.insertStrategyDesc(bean);
	}
	
	private void initStrategy() {
		
		RsStrategy bean = new RsStrategy();
		bean.setClbh("level1");
		bean.setClmc("一级策略");
		bean.setClxh(0);
		bean.setClzt(0);		
		strategyService.insertStrategy(bean);
		initStrateyInfo("level1");
		
		bean = new RsStrategy();
		bean.setClbh("level2");
		bean.setClmc("二级策略");
		bean.setClxh(1);
		bean.setClzt(1);
		strategyService.insertStrategy(bean);
		initStrateyInfo("level2");
		
		bean = new RsStrategy();
		bean.setClbh("level3");
		bean.setClmc("三级策略");
		bean.setClxh(2);
		bean.setClzt(1);
		strategyService.insertStrategy(bean);
		initStrateyInfo("level3");
		
		bean = new RsStrategy();
		bean.setClbh("level");
		bean.setClmc("自定义");
		bean.setClxh(3);
		bean.setClzt(1);
		strategyService.insertStrategy(bean);
		initStrateyInfo("level");
	}
	
	private void insertStrateyInfo(String clbh, String msbh, String tmz,int tmxh ) {
		
		RsStrategyInfo bean = new RsStrategyInfo();
		bean.setClbh(clbh);
		bean.setMsbh(msbh);
		bean.setTmxh(tmxh);
		bean.setTmz(tmz);
		bean.setTmzt(0);
		
		strategyService.insertStrategyInfo(bean);
	}
}
