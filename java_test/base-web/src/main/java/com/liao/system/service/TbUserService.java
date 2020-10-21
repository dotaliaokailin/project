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
    /**
     * 分页查询用户
     * @param currentPage
     * @param pageSize
     * @param userVo
     * @return IPage<TbUser>
     */
    public IPage<TbUser> findUserPage(Integer currentPage, Integer pageSize, UserVo userVo);

    /**
     * 导出用户
     * @return List<TbUser>
     */
    public List<TbUser> exportUsers();

    /**
     * 根据用户名查询用户
     * @param username
     * @return TbUser
     */
    public TbUser selectByUsername(String username);

    /**
     * 根据ID查询用户
     * @param id
     * @return TbUser
     */
    public TbUser selectById(Long id);

    /**
     * 新增或者修改
     * @param tbUser
     * @return Boolean
     */
    public Boolean saveOrUpdateUser(TbUser tbUser);
}
