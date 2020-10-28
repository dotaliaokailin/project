package com.liao.system.mapper;

import com.liao.system.pojo.TbRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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

    @Select("select r.id, r.role_name, r.status, r.remark from tb_role r where r.role_name = #{roleName}")
    TbRole findRoleByRoleName(String roleName);

    @Select("select *, case when status = 1 then '可用' else '不可用' end as statusName  from tb_role")
    List<TbRole> exportExcel();
}
