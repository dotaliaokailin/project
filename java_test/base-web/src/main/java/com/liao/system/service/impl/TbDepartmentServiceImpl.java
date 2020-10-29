package com.liao.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liao.handler.BusinessException;
import com.liao.response.ResultCodeEnum;
import com.liao.system.enu.role.RoleStatusEnum;
import com.liao.system.pojo.TbDepartment;
import com.liao.system.mapper.TbDepartmentMapper;
import com.liao.system.pojo.TbRole;
import com.liao.system.service.TbDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liao
 * @since 2020-09-28
 */
@Service
public class TbDepartmentServiceImpl extends ServiceImpl<TbDepartmentMapper, TbDepartment> implements TbDepartmentService {

    /**
     * 查询部门和人数
     */
    @Override
    public List<TbDepartment> findDeptAndCount() {
        return this.baseMapper.findDeptAndCount();
    }

    /**
     * 部门分页查询
     */
    @Override
    public IPage<TbDepartment> getDeptPage(Integer currentPage, Integer pageSize, String name) {
        QueryWrapper<TbDepartment> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(name)){//部门名称
            queryWrapper.like("name", name);
        }
        return this.baseMapper.getDeptPage(new Page<TbDepartment>(currentPage, pageSize), queryWrapper);
    }

    /**
     * 新增修改部门信息
     *
     * @param tbDepartment
     * @return
     */
    @Override
    public boolean savoOrUpdateDept(TbDepartment tbDepartment) {
        if(null == tbDepartment || StringUtils.isEmpty(tbDepartment.getAddress()) || StringUtils.isEmpty(tbDepartment.getName()) || StringUtils.isEmpty(tbDepartment.getPhone())){
            throw new BusinessException(ResultCodeEnum.PARAM_NOT_COMPLETE.getCode(), ResultCodeEnum.PARAM_NOT_COMPLETE.getMessage());
        }
        //根据角色名查询
        TbDepartment dept = null;
        try{
            dept = this.baseMapper.findDeptByName(tbDepartment.getName());
        }catch (Exception e){
            throw new BusinessException(ResultCodeEnum.DEPARTMENT_NOT_EXIST.getCode(), ResultCodeEnum.DEPARTMENT_NOT_EXIST.getMessage());
        }
        if(null != tbDepartment.getId() && tbDepartment.getId() > 0){//修改
            TbDepartment selectById = this.baseMapper.selectById(tbDepartment.getId());
            //修改时部门信息不存在
            if(null == selectById){
                throw new BusinessException(ResultCodeEnum.DEPARTMENT_NOT_EXIST.getCode(), ResultCodeEnum.DEPARTMENT_NOT_EXIST.getMessage());
            }
            if(null != dept && selectById.getId().compareTo(dept.getId()) != 0){
                throw new BusinessException(ResultCodeEnum.DEPARTMENT_ALREADY_EXIST.getCode(), ResultCodeEnum.DEPARTMENT_ALREADY_EXIST.getMessage());
            }
        }else {//新增
            if(null != dept){
                throw new BusinessException(ResultCodeEnum.DEPARTMENT_ALREADY_EXIST.getCode(), ResultCodeEnum.DEPARTMENT_ALREADY_EXIST.getMessage());
            }
        }
        tbDepartment.setCreateTime(null);
        tbDepartment.setModifiedTime(null);
        return this.saveOrUpdate(tbDepartment);
    }

    /**
     * 导出部门信息Excel
     *
     * @return
     */
    @Override
    public List<TbDepartment> exportExcel() {
        return this.baseMapper.exportExcel();
    }
}
