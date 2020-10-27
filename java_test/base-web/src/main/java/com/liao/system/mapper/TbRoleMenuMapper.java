package com.liao.system.mapper;

import com.liao.system.pojo.TbRoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;

/**
 * <p>
 * 角色菜单关联表 Mapper 接口
 * </p>
 *
 * @author liao
 * @since 2020-09-28
 */
public interface TbRoleMenuMapper extends BaseMapper<TbRoleMenu> {

    @Delete("delete from tb_role_menu where role_id = #{roleId}")
    void deleteByRoleId(Long roleId);
}
