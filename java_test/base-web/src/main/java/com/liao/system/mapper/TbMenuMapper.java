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

    @Select("select * from tb_menu m where m.id in (select rm.menu_id from tb_role_menu rm where rm.role_id = #{roleId}) and m.available = 1 and m.perms is not null and perms != ''")
    List<TbMenu> getMenuByRoleId(Long roleId);

    @Select("select *, (select menu_name  from tb_menu where id = m.parent_id) as parentName from tb_menu m where m.id = #{id}")
    TbMenu getMenuById(Long id);

    @Select("select m.id, m.menu_name, m.parent_id, m.perms, m.available, m.order_num, m.icon, m.open, m.type, m.url from tb_menu m where menu_name = #{menuName} limit 1")
    TbMenu getMenuByName(String menuName);

    @Select("select *,(select m.menu_name from tb_menu where id = m.parent_id) as parentName from tb_menu m")
    List<TbMenu> exportExcel();
}
