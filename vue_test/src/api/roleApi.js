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

/**
 * 根据ID删除角色
 * @param id
 * @returns {AxiosPromise}
 */
export const deleteRole = (id) => {
  return request({
    url: '/system/tb-role/deleteRoleById',
    method: 'GET',
    params: {
      id
    }
  });
}

/**
 * 分配角色菜单按钮api
 * @param id
 * @returns {AxiosPromise}
 */
export const getMenuByRoleId = (id) => {
  return request({
    url: '/system/tb-role-menu/getMenuByRoleId',
    method: 'GET',
    params: {
      id
    },
    headers: {'loadingTarget': '.el-dialog__body'}//指定某元素区域+loading
  });
}

/**
 * 保存角色的菜单按钮api
 * @param menuIds
 * @param roleId
 * @returns {AxiosPromise}
 */
export const save = (menuIds, roleId) => {
  return request({
    url: '/system/tb-role-menu/save',
    method: 'POST',
    params: {
      roleId
    },
    data:menuIds
  });
}

/**
 * 导出角色列表
 * @returns {Promise<AxiosResponse<any> | never>}
 */
export const exportRoles = () => {
  return request({
    url: '/system/tb-role/exportExcel',
    method: 'GET',
    responseType: 'blob',
  }).then((response) => {
    let url = window.URL.createObjectURL(new Blob([response.data]));
    let link = document.createElement("a");
    link.style.display = "none";
    link.href = url;
    link.setAttribute("download",  decodeURI(response.headers['filename']));//浏览器下载文件，decodeURI解决中文乱码
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
  }).catch(function (error) {
    Message.error(error);
  });
}
