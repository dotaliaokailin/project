import request from '../utils/request'

export const findDeptAndCount = () => {
  return request({
    url: '/system/tb-department/findDeptAndCount',
    method: 'GET'
  });
}
