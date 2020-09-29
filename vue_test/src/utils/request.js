import axios from 'axios';

//const s6语法
const instance = axios.create({
  baseURL: 'http://localhost:8081',
  timeout: 3000,
});

//暴露出去
export default instance;
