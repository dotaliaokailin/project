package com.liao.system.mapper;

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
}
