import request from '../utils/request';
import { Message} from 'element-ui';

//删除图片
export const deleteImg = (oldImg) => {
  return request({
    url: '/oss/deleteImgFile',
    method: 'POST',
    headers: {'showLoading': false}, //请求头加上这个不显示loading
    params:{
      imgUrl : oldImg
    }
  })
}
