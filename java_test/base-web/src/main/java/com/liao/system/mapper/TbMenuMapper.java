package com.liao.system.mapper;

import com.liao.system.pojo.TbMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author liao
 * @since 2020-09-28
 */
public interface TbMenuMapper extends BaseMapper<TbMenu> {
    /**
     * 根据父级菜单ID拿到所有的子菜单
     * @param parentId
     * @return
     */
    @Select("select * from tb_menu where parent_id = #{parentId} order by order_num")
    public List<TbMenu> findMenuByParentId(Long parentId);
}
