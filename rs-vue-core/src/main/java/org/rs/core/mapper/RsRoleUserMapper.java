package org.rs.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.rs.core.beans.RsRole;
import org.rs.core.beans.RsRoleUser;

public interface RsRoleUserMapper {

	@Select({"SELECT A.* FROM RS_ROLE A INNER JOIN RS_ROLE_USER B ON A.JSBH=B.JSBH WHERE B.YHBH = #{yhbh}"})
	@ResultMap(value="org.rs.core.mapper.RsRoleMapper.result_map")
	List<RsRole> queryUserRoles(@Param("yhbh") String yhbh);
	
	@Insert("INSERT INTO RS_ROLE_USER(JSBH,YHBH,CJSJ,CJRBH,CJRMC) VALUES(#{jsbh},#{yhbh},#{cjsj,jdbcType=TIMESTAMP},#{cjrbh},#{cjrmc})")
	int insertRoleUser(RsRoleUser bean);
	
	@Delete("DELETE FROM RS_ROLE_USER WHERE JSBH=#{jsbh} AND YHBH=#{yhbh}")
	int deleteRoleUser(@Param("jsbh")String jsbh,@Param("yhbh")String yhbh);

	@Delete("DELETE FROM RS_ROLE_USER WHERE JSBH=#{jsbh}")
	int deleteRoleUserByJsbh(@Param("jsbh")String jsbh);

	@Delete("DELETE FROM RS_ROLE_USER WHERE YHBH=#{yhbh}")
	int deleteRoleUserByYhbh(@Param("yhbh")String yhbh);
}
