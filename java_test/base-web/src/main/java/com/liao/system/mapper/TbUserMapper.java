package com.liao.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liao.system.pojo.TbUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author liao
 * @since 2020-09-28
 */
public interface TbUserMapper extends BaseMapper<TbUser> {
    public IPage<TbUser> findUserPage(Page<TbUser> page, @Param(Constants.WRAPPER) QueryWrapper<TbUser> wrapper);

    public List<TbUser> exportUsers(@Param(Constants.WRAPPER) QueryWrapper<TbUser> wrapper);
}
