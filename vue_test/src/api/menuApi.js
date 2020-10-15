import request from '../utils/request'

export const menuTree = () => {
  return request({
    url: '/system/tb-menu/menuTree',
    method: 'GET',
  });
}
