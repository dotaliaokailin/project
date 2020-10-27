package com.liao.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liao.system.pojo.TbRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author liao
 * @since 2020-09-28
 */
public interface TbRoleMapper extends BaseMapper<TbRole> {
    @Select("select * from tb_role where id = #{id}")
    public TbRole selectById(Long id);

    Page<TbRole> findRolePage(Page<TbRole> tbRolePage, QueryWrapper<TbRole> queryWrapper);

    @Select("select r.id, r.role_name, r.status, r.remark from tb_role r where r.role_name = #{roleName}")
    TbRole findRoleByRoleName(String roleName);
}
