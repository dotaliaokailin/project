/*角色api*/
import request from '../utils/request';
import { Message} from 'element-ui';

export const findRolePage = (currentPage, pageSize, roleName) => {
  return request({
    url: '/system/tb-role/findRolePage',
    method: 'POST',
    params:{
      currentPage,
      pageSize,
      roleName
    }
  });
}
