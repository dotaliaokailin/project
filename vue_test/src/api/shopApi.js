import request from '../utils/request';

/**
 * 搜索
 * @param keyword
 * @param pageIndex
 * @param pageSize
 * @returns {AxiosPromise}
 */
export const search = (keyword, pageIndex, pageSize) => {
  return request({
    url: '/es/search',
    method: 'GET',
    params: {
      keyword,
      pageIndex,
      pageSize
    }
  });
}

/**
 * 同步入库
 */
export const parse = (keyword) => {
  return request({
    url: '/es/parse/' + keyword,
    method: 'GET'
  });
}

/**
 * 删除商品
 */
export const del = (id) => {
  return request({
    url: '/es/del',
    method: 'POST',
    params: {
      id
    }
  });
}
