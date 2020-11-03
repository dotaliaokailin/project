/*菜单api*/
import request from '../utils/request'

/**
 * 菜单树
 * @returns {AxiosPromise}
 */
export const menuTree = () => {
  return request({
    url: '/system/tb-menu/menuTree',
    method: 'GET',
  });
}

/**
 *菜单按钮树
 */
export const menuButtonTree = () =>{
  return request({
    url: '/system/tb-menu/menuButtonTree',
    method: 'GET',
  });
}

/**
 * 根据ID查询菜单信息
 */
export const getMenuById = (id) => {
  return request({
    url: '/system/tb-menu/getMenuById',
    method: 'GET',
    params:{
      id
    },
    headers: {'showLoading': false} //请求头加上这个不显示loading
  });
}

/**
 * 新增修改菜单信息
 */
export const saveOrUpdate = (tbMenu) => {
  return request({
    url: '/system/tb-menu/saveOrUpdate',
    method: 'POST',
    data: tbMenu
  });
}
