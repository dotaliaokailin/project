import request from '../utils/request'

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
