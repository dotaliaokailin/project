package com.liao.system.service.impl;

import com.liao.system.pojo.TbLog;
import com.liao.system.mapper.TbLogMapper;
import com.liao.system.service.TbLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志表 服务实现类
 * </p>
 *
 * @author liao
 * @since 2020-09-28
 */
@Service
public class TbLogServiceImpl extends ServiceImpl<TbLogMapper, TbLog> implements TbLogService {

}
