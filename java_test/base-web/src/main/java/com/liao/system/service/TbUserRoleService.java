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
    public List<TbUserRole> listByUserId(Long userId);
}
