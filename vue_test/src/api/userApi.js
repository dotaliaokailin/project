/*用户api*/
import request from '../utils/request';
import { Message} from 'element-ui';

export const login = (username, password) => {
  return request({
    url: '/login',
    method: 'POST',
    params: {
      username,
      password
    },
    xhrFields: {withCredentials: true},
    crossDomain: true,
    headers: {"Access-Control-Allow-Origin": "*", "Access-Control-Allow-Credentials":true}
  });
}
/**
 * (已弃用)
 * 分页查询用户
 * @param currentPage 当前页面
 * @param pageSize  页面容量
 * @returns {AxiosPromise}
 */
export const userPage = (currentPage, pageSize) => {
    return request({
      url: '/system/tb-user/usersPage',
      method: 'GET',
      params: {
        currentPage,
        pageSize
      }
    });
};

/**
 * 条件分页查询用户信息及部门名称
 * 字段都对应接口的名称，这样子就用ES6的语法直接currentPage，就不需要currentPage : currentPage
 * @param currentPage 前页面
 * @param pageSize 页面容量
 * @param userVo 用户viewObject
 * @returns {AxiosPromise}
 */
export const findUserPage = (currentPage, pageSize ,userVo) => {
  return request({
    url: '/system/tb-user/findUserPage',
    method: 'POST',
    params: {
      currentPage,
      pageSize
    },
    data: userVo
  });
}

export const findUserById = (id) =>{
  return request({
    url: '/system/tb-user/findUserById',
    method: 'GET',
    params:{
      id
    }
  });
}


/**
 * 新增修改用户
 * @param userVo
 * @returns {AxiosPromise}
 */
export const saveOrUpdate = (tbUser) => {
  return request({
    url: '/system/tb-user/saveOrUpdate',
    method: 'POST',
    data: tbUser
  });
}

/**
 * 删除用户
 * @param id
 * @returns {AxiosPromise}
 */
export const deleteUser = (id) => {
  return request({
    url: '/system/tb-user/deleteUser',
    method: 'GET',
    params: {
      id
    }
  });
}

/**
 * 导出用户列表
 * @returns {Promise<AxiosResponse<any> | never>}
 */
export const exportUsers = () => {
  return request({
    url: '/system/tb-user/download',
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

/**
 * 查询用户权限及所有用户列表
 * @param id
 * @returns {AxiosPromise}
 */
export const userRoles = (id) => {
  return request({
    url: '/system/tb-user/userRoles',
    method: 'GET',
    params: {
      id
    },
    headers: {'loadingTarget': '.el-dialog__body'}//指定某元素区域+loading
  });
}

export const addUserRoles = (list, id) => {
  return request({
    url: '/system/tb-user/addUserRoles',
    method: 'POST',
    params: {
      id
    },
    data: list
  });
}
