/*部门api*/
import request from '../utils/request'

export const findDeptAndCount = () => {
  return request({
    url: '/system/tb-department/findDeptAndCount',
    method: 'GET',
    headers: {'showLoading': false} //请求头加上这个不显示loading
  });
}
