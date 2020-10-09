package com.liao.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liao.system.pojo.TbUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liao.system.vo.UserVo;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author liao
 * @since 2020-09-28
 */
public interface TbUserService extends IService<TbUser> {
    public IPage<TbUser> findUserPage(Integer currentPage, Integer pageSize, UserVo userVo);

}
