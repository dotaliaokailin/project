<template>
  <div id="login_body">
    <div style="height: 30%"></div>
    <div style="height: 65%">
      <el-card class="box-card" >
      <div slot="header" class="clearfix">
        <span>欢迎登录</span>
      </div>
      <el-form status-icon label-width="100px" >
        <el-form-item label="账号" prop="username">
          <el-input @focus="focus_blur(1)" @blur="focus_blur(2)" type="text" v-model="username" name="username" autocomplete="off" placeholder="请输入账号"><i slot="prefix" :class="username_input"></i></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input @focus="focus_blur(3)" @blur="focus_blur(4)" type="password" v-model="password" name="password"  autocomplete="off" placeholder="请输入密码" show-password><i slot="prefix" :class="password_input"></i></el-input>
        </el-form-item>
        <el-form-item>
          <el-checkbox v-model="checked">记住我</el-checkbox><a id="forgetPassword" @click="forgetPassword" href="javascript:void(0);">忘记密码</a>
        </el-form-item>
        <el-form-item class="btn-class">
          <el-button type="primary" @click="login">登录</el-button>
          <el-button @click="register">注册</el-button>
        </el-form-item>
      </el-form>
    </el-card>
    </div>
    <Foot></Foot>
  </div>
</template>

<script>
  import Foot from '../components/Foot.vue';
  export default {
    name: "Login",
    data() {
      return {
        username: '',
        password: '',
        checked: false,
        username_input: 'el-icon-user',
        password_input: 'el-icon-lock',
      }
    },
    components:{
      'Foot': Foot
    },
    methods: {
      login() {
        let this_ = this;
        let data = this_.$qs.stringify({//封装json数据
          username: this.username,
          password: this_.$md5(this.password)
        });
        //POST登录
        this_.axios.post('/user/login', data)
          .then(function (response) {
            console.log(response)
            if(response.data != ""){
              //使用vue-router 路由到指定页面，该方法称之为编程式导航
              sessionStorage.setItem("username", response.data)
              this_.$router.push("/main/"+this_.username);
            }else{
              this_.$message.error("账号密码错误");
            }
          })
          .catch(function (error) {
            console.log(error);
          });
      },
      register() {
        alert('register!');
      },
      forgetPassword(){
        alert('forgetPassword');
      },
      focus_blur(i){
        if(i == 1){
          this.username_input = 'el-icon-user-solid';
        }else if(i == 2){
          this.username_input = 'el-icon-user';
        }else if(i == 3){
          this.password_input = 'el-icon-unlock';
        }else if(i == 4){
          this.password_input = 'el-icon-lock';
        }
      }
    }
  }
</script>

<style scoped>

  #login_body {
    width: 100%;
    height: 100%;
    background-image: url("../assets/images/sky.jpg");
    background-size: cover;
  }

  .clearfix {
    text-align: center;
    font-size: 20px;
  }

  .box-card {
    width: 480px;
    margin: auto;
  }

  .btn-class {
    margin-left: 40px;
  }

  .el-icon-lock,.el-icon-user-solid,.el-icon-unlock,.el-icon-user {
    font-size: 20px;
  }

  .el-input{
    width: 80%;
  }

  #forgetPassword {
    margin-left: 150px;
    text-decoration: none;
    color: #606266;
  }
  #forgetPassword:hover{
    color: red;
    text-decoration: underline;
  }
</style>
