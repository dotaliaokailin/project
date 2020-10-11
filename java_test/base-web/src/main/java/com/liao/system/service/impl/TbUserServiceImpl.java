package com.liao.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liao.system.pojo.TbUser;
import com.liao.system.mapper.TbUserMapper;
import com.liao.system.service.TbUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liao.system.vo.UserVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author liao
 * @since 2020-09-28
 */
@Service
public class TbUserServiceImpl extends ServiceImpl<TbUserMapper, TbUser> implements TbUserService {
    @Override
    public TbUser selectByUsername(String username) {
        return this.baseMapper.selectByUsername(username);
    }

    @Override
    public TbUser selectById(Long id) {
        return this.baseMapper.selectById(id);
    }

    @Override
    public IPage<TbUser> findUserPage(Integer currentPage, Integer pageSize, UserVo userVo) {
        QueryWrapper<TbUser> queryWrapper = new QueryWrapper<>();
        if(null != userVo){
            if(null != userVo.getDepartmentId()){
                queryWrapper.eq("department_id", userVo.getDepartmentId());
            }
            if(!StringUtils.isEmpty(userVo.getUsername())){
                queryWrapper.eq("username", userVo.getUsername());
            }
            if(!StringUtils.isEmpty(userVo.getEmail())){
                queryWrapper.eq("email", userVo.getEmail());
            }
            if(!StringUtils.isEmpty(userVo.getNickname())){
                queryWrapper.eq("nickname", userVo.getNickname());
            }
            if(null != userVo.getSex()){
                queryWrapper.eq("sex", userVo.getSex());
            }
        }
        return this.baseMapper.findUserPage(new Page<TbUser>(currentPage, pageSize), queryWrapper);
    }

    @Override
    public List<TbUser> exportUsers() {
        return this.baseMapper.exportUsers(new QueryWrapper<TbUser>());
    }
}
