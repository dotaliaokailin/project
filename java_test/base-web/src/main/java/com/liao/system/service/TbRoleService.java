package com.liao.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liao.system.pojo.TbRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author liao
 * @since 2020-09-28
 */
public interface TbRoleService extends IService<TbRole> {
    /**
     * 根据ID查询
     * @param id
     * @return
     */
    public TbRole selectById(Long id);

    /**
     * 分页查询
     * @param currentPage   当前页码
     * @param pageSize  页码容量
     * @param roleName  角色名
     * @return
     */
    Page<TbRole> findRolePage(Integer currentPage, Integer pageSize, String roleName);
}
