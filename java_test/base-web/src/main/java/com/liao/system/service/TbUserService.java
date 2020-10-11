package com.liao.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.liao.system.pojo.TbUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liao.system.vo.UserVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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

    public List<TbUser> exportUsers();

    public TbUser selectByUsername(String username);

    public TbUser selectById(Long id);
}
