package com.liao.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liao.handler.BusinessException;
import com.liao.response.ResultCodeEnum;
import com.liao.system.enu.role.RoleStatusEnum;
import com.liao.system.pojo.TbRole;
import com.liao.system.mapper.TbRoleMapper;
import com.liao.system.service.TbRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liao.system.vo.RoleVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author liao
 * @since 2020-09-28
 */
@Service
public class TbRoleServiceImpl extends ServiceImpl<TbRoleMapper, TbRole> implements TbRoleService {
    @Override
    public TbRole selectById(Long id) {
        return this.baseMapper.selectById(id);
    }

    /**
     * 分页查询
     *
     * @param currentPage 当前页码
     * @param pageSize    页码容量
     * @param roleName    角色名
     * @return
     */
    @Override
    public Page<TbRole> findRolePage(Integer currentPage, Integer pageSize, String roleName) {
        QueryWrapper<TbRole> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(roleName)){
            queryWrapper.like("role_name", roleName);
        }
        return this.baseMapper.selectPage(new Page<TbRole>(currentPage, pageSize), queryWrapper);
    }

    @Override
    public Boolean saveOrUpdateRole(TbRole role) {
        //根据角色名查询
        TbRole tbRole = null;
        try{
            tbRole = this.baseMapper.findRoleByRoleName(role.getRoleName());
        }catch (Exception e){
            throw new BusinessException(ResultCodeEnum.ROLE_NOT_FOUND_PAGE.getCode(), ResultCodeEnum.ROLE_NOT_FOUND_PAGE.getMessage());
        }
        if(null != role.getId() && role.getId() >= 0){//修改
            TbRole selectById = this.baseMapper.selectById(role.getId());
            //修改时角色信息不存在
            if(null == selectById){
                throw new BusinessException(ResultCodeEnum.ROLE_NOT_FOUND_PAGE.getCode(), ResultCodeEnum.ROLE_NOT_FOUND_PAGE.getMessage());
            }
            if(null != tbRole && selectById.getId().compareTo(tbRole.getId()) != 0){
                throw new BusinessException(ResultCodeEnum.ROLE_ALREADY_EXIST.getCode(), ResultCodeEnum.ROLE_ALREADY_EXIST.getMessage());
            }
        }else {//新增
            if(null != tbRole){
                throw new BusinessException(ResultCodeEnum.ROLE_ALREADY_EXIST.getCode(), ResultCodeEnum.ROLE_ALREADY_EXIST.getMessage());
            }
            role.setStatus(RoleStatusEnum.ENABLE.getStatus());
        }
        return this.saveOrUpdate(role);
    }

    @Override
    public TbRole findRoleByRoleName(String roleName) {
        return this.baseMapper.findRoleByRoleName(roleName);
    }

    /**
     * 导出excel
     *
     * @return
     */
    @Override
    public List<TbRole> exportExcel() {
        return this.baseMapper.exportExcel();
    }
}
