/*部门api*/
import request from '../utils/request';
import { Message} from 'element-ui';

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

/**
 * 分页查询部门
 * @param currentPage
 * @param pageSize
 * @param name
 * @returns {AxiosPromise}
 */
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

/**
 * 新增修改
 * @param tbDept
 * @returns {AxiosPromise}
 */
export const saveOrUpdate = (tbDept) =>{
  return request({
    url: '/system/tb-department/saveOrUpdate',
    method: 'POST',
    data: tbDept
  });
}

/**
 * 根据ID查询部门信息
 * @param id
 * @returns {AxiosPromise}
 */
export const getDeptById = (id) => {
  return request({
    url: '/system/tb-department/getDeptById',
    method: 'GET',
    params: {
      id
    },
    headers: {'loadingTarget': '.el-dialog__body'}//指定某元素区域+loading
  });
}

/**
 * 根据ID删除部门信息
 * @param id
 * @returns {AxiosPromise}
 */
export const deleteById = (id) => {
  return request({
    url: '/system/tb-department/delete',
    method: 'GET',
    params: {
      id
    },
  });
}

/**
 * 导出部门列表
 * @returns {Promise<AxiosResponse<any> | never>}
 */
export const exportDept = () => {
  return request({
    url: '/system/tb-department/exportExcel',
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
