package org.rs.core.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.rs.core.beans.RsDeptUser;

public interface RsDeptUserMapper {
	
	@Select("SELECT * FROM RS_DEPT_USER WHERE BMBH=#{bmbh} AND YHBH=#{yhbh}")
	@Results(id = "result_map",value = {
		@Result(column="BMBH", property="bmbh", jdbcType=JdbcType.VARCHAR),
		@Result(column="YHBH", property="yhbh", jdbcType=JdbcType.VARCHAR),
		@Result(column="CJRBH", property="cjrbh", jdbcType=JdbcType.VARCHAR),
		@Result(column="CJRMC", property="cjrmc", jdbcType=JdbcType.VARCHAR),
		@Result(column="CJSJ", property="cjsj", jdbcType=JdbcType.TIMESTAMP)
	})
	RsDeptUser queryBeanById(@Param("bmbh") String bmbh,@Param("yhbh") String yhbh);

	@Insert("INSERT INTO RS_DEPT_USER(BMBH,YHBH,CJSJ,CJRBH,CJRMC) VALUES(#{bmbh},#{yhbh},#{cjsj,jdbcType=TIMESTAMP},#{cjrbh},#{cjrmc})")
	int insertDeptUser(RsDeptUser bean);
	
	@Delete("DELETE FROM RS_DEPT_USER WHERE BMBH=#{bmbh} AND YHBH=#{yhbh}")
	int deleteDeptUser(RsDeptUser bean);

	@Delete("DELETE FROM RS_DEPT_USER WHERE YHBH=#{yhbh}")
	int deleteDeptUserByYhbh(@Param("yhbh") String yhbh);
	
	@Delete("DELETE FROM RS_DEPT_USER WHERE BMBH=#{bmbh}")
	int deleteDeptUserByBmbh(@Param("bmbh") String bmbh);
}
