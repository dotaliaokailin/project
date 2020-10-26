package com.liao.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liao.system.enu.User.UserStatusEnum;
import com.liao.system.enu.User.UserTypeEnum;
import com.liao.handler.BusinessException;
import com.liao.response.ResultCodeEnum;
import com.liao.system.pojo.TbUser;
import com.liao.system.mapper.TbUserMapper;
import com.liao.system.service.TbUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liao.system.vo.UserVo;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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
//    @Autowired
//    private PasswordEncoder passwordEncoder;
    @Override
    public TbUser selectByUsername(String username) {
        return this.baseMapper.selectByUsername(username);
    }

    @Override
    public TbUser selectById(Long id) {
        return this.baseMapper.selectById(id);
    }

    /**
     * 新增或者修改
     *
     * @param tbUser
     * @return Boolean
     */
    @Override
    public Boolean saveOrUpdateUser(TbUser tbUser) {
        //根据用户名查询用户
        TbUser selectUser = null;
        try{
            selectUser = this.selectByUsername(tbUser.getUsername());
        }catch (Exception e){//之前老数据有重复账户，点击更新的时候会报这个错误
            throw new BusinessException(ResultCodeEnum.USER_ACCOUNT_ALREADY_EXIST.getCode(), ResultCodeEnum.USER_ACCOUNT_ALREADY_EXIST.getMessage());
        }
        if(null != tbUser.getId()){//修改
            TbUser selectById = this.baseMapper.selectById(tbUser.getId());
            if(null == selectById){//修改时：账户不存在
                throw new BusinessException(ResultCodeEnum.USER_ACCOUNT_NOT_EXIST.getCode(), ResultCodeEnum.USER_ACCOUNT_NOT_EXIST.getMessage());
            }
            if(null != selectUser && selectUser.getId().compareTo(selectById.getId()) != 0){//修改时：ID 与查询用户名拿到的ID比较，如果不相同，代表用户名已存在
                throw new BusinessException(ResultCodeEnum.USER_ACCOUNT_ALREADY_EXIST.getCode(), ResultCodeEnum.USER_ACCOUNT_ALREADY_EXIST.getMessage());
            }
        }else{//新增
            if(null != selectUser){//新增时：用户已存在
                throw new BusinessException(ResultCodeEnum.USER_ACCOUNT_ALREADY_EXIST.getCode(), ResultCodeEnum.USER_ACCOUNT_ALREADY_EXIST.getMessage());
            }
            //账户有效
            tbUser.setStatus(UserStatusEnum.VALID_STATUS.getStatus());
            //用户类型
            tbUser.setType(UserTypeEnum.USER_TYPE.getType());
            //盐，没用，默认用UUID生成32位
            tbUser.setSalt(UUID.randomUUID().toString().substring(0, 32));
            //密码使用 spring security加密方式
            //tbUser.setPassword(passwordEncoder.encode(tbUser.getPassword()));
        }
        return this.saveOrUpdate(tbUser);
    }

    /**
     * 查询用户
     *
     * @param id
     */
    @Override
    public TbUser findUserById(Long id) {
        return this.baseMapper.findUserById(id);
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
