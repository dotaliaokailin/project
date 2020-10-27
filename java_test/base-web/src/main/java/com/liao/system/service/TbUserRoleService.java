package com.liao.system.service;

import com.liao.system.pojo.TbUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户角色关联表 服务类
 * </p>
 *
 * @author liao
 * @since 2020-09-28
 */
public interface TbUserRoleService extends IService<TbUserRole> {
    /**
     * 根据用户ID查询用户角色数据
     * @param userId
     * @return
     */
    public List<TbUserRole> listByUserId(Long userId);

    /**
     * 根据用户ID删除用户角色数据
     * @param userId
     */
    public void removeByUserId(Long userId);

    /**
     * 根据角色ID删除信息
     * @param id
     */
    void deleteByRoleId(Long roleId);
}
