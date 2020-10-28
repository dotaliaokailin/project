package com.liao.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.liao.system.pojo.TbRoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liao.system.pojo.TbUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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

    @Select("select * from tb_role_menu where role_id = #{roleId}")
    List<TbRoleMenu> getMenuByRoleId(Long roleId);
}
