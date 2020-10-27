/*角色api*/
import request from '../utils/request';
import { Message} from 'element-ui';

/**
 * 角色分页查询
 * @param currentPage
 * @param pageSize
 * @param roleName
 * @returns {AxiosPromise}
 */
export const findRolePage = (currentPage, pageSize, roleName) => {
  return request({
    url: '/system/tb-role/findRolePage',
    method: 'POST',
    params:{
      currentPage,
      pageSize,
      roleName
    }
  });
}

/**
 * 根据ID查询角色信息
 * @param id
 * @returns {AxiosPromise}
 */
export const findRoleById = (id) => {
  return request({
    url: '/system/tb-role/findRoleById',
    method: 'GET',
    params: {
      id
    },
    headers: {'showLoading': false} //请求头加上这个不显示loading
  });
}

/**
 * 新增或者修改
 * @param tbRole
 * @returns {AxiosPromise}
 */
export const saveOrUpdate = (tbRole) => {
  return request({
    url: '/system/tb-role/saveOrUpdate',
    method: 'POST',
    data: tbRole
  });
}


export const deleteRole = (id) => {
  return request({
    url: '/system/tb-role/deleteRoleById',
    method: 'GET',
    params: {
      id
    }
  });
}
