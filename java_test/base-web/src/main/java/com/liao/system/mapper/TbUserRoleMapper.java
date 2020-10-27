package com.liao.system.mapper;

import com.liao.system.pojo.TbUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 用户角色关联表 Mapper 接口
 * </p>
 *
 * @author liao
 * @since 2020-09-28
 */
public interface TbUserRoleMapper extends BaseMapper<TbUserRole> {

    @Select("select * from tb_user_role where user_id = #{userId}")
    public List<TbUserRole> listByUserId(Long userId);

    @Delete("delete from tb_user_role where user_id = #{userId}")
    public void removeByUserId(Long userId);

    @Delete("delete from tb_user_role where role_id = #{roleId}")
    void deleteByRoleId(Long roleId);
}
