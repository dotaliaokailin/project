
export default {
  //message:消息内容，type:消息类型, this_:传入当前this
  message(message, type, this_){
    this_.$message({
      showClose: true,
      type: type,
      message: message
    });
  },
  messageType: {
    info : 'info',
    warning : 'warning',
    success : 'success',
    error : 'error'
  }
};
