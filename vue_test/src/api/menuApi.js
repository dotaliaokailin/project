/*菜单api*/
import request from '../utils/request'
import {Message} from "element-ui";

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

/**
 * 根据ID删除菜单
 */
export const deleteById = (id) => {
  return request({
    url: '/system/tb-menu/deleteById',
    method: 'GET',
    params:{
      id
    }
  });
}

/**
 * 导出菜单列表
 * @returns {Promise<AxiosResponse<any> | never>}
 */
export const exportMenus = () => {
  return request({
    url: '/system/tb-menu/exportExcel',
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
