/*部门api*/
import request from '../utils/request'

/**
 * 查询部门和总数
 * @returns {AxiosPromise}
 */
export const findDeptAndCount = () => {
  return request({
    url: '/system/tb-department/findDeptAndCount',
    method: 'GET',
    headers: {'showLoading': false} //请求头加上这个不显示loading
  });
}

export const getDeptPage = (currentPage, pageSize, name) => {
  return request({
    url: '/system/tb-department/getDeptPage',
    method: 'GET',
    params: {
      currentPage,
      pageSize,
      name
    }
  });
}
