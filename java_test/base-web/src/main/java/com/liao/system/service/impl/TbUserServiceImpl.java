package com.liao.system.service.impl;

import com.liao.system.pojo.TbUser;
import com.liao.system.mapper.TbUserMapper;
import com.liao.system.service.TbUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
