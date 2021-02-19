package org.rs.core.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface RsRefreshMapper {

	@Insert("INSERT INTO RS_REFRESH_MILLISECONDS(REFRESH_ID,MILLISECONDS) VALUES(#{refresh_id},#{milliseconds,jdbcType=BIGINT})")
	int insertRefresh(@Param("refresh_id")String refresh_id,@Param("milliseconds")Long milliseconds);
	@Update("UPDATE RS_REFRESH_MILLISECONDS SET MILLISECONDS=#{milliseconds,jdbcType=BIGINT} WHERE REFRESH_ID=#{refresh_id}")
	int updateRefresh(@Param("refresh_id")String refresh_id,@Param("milliseconds")Long milliseconds);
	@Select("SELECT MILLISECONDS FROM RS_REFRESH_MILLISECONDS WHERE REFRESH_ID=#{refresh_id}")
	Long getRefresh(@Param("refresh_id")String refresh_id);
}
