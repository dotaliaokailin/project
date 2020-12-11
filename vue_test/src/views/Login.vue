<template>
    <!-- 登录表单的容器-->
    <div class="login_container">
      <!-- 登录区域-->
      <div class="login_box">
        <!-- 头像-->
        <div class="avatar_box">
            <img src="../assets/image/logo.gif">
        </div>
        <!-- 表单-->
        <el-form :model="loginForm" :rules="rules" ref="loginForm" label-width="0px" class="login_form">
          <el-form-item prop="username">
            <el-input v-model="loginForm.username" prefix-icon="el-icon-user-solid" placeholder="用户名"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input type="password" v-model="loginForm.password" prefix-icon="el-icon-lock" placeholder="密码"></el-input>
          </el-form-item>
          <el-form-item prop="verificationCode">
            <div class="verificationCode_box">
              <el-input v-model="loginForm.verificationCode" prefix-icon="el-icon-mobile-phone" placeholder="验证码" class="verficationCode"></el-input>
              <!--<img src="../assets/image/verificationCode.gif" class="verficationCode_img">-->
              <img style="margin-left: 50px;" alt="验证码" onclick="this.src='http://localhost:9091/defaultKaptcha?d=' + new Date()*1" src="http://localhost:9091/defaultKaptcha" />
            </div>
          </el-form-item>
          <el-form-item class="login_btn">
            <el-button type="primary" @click="submitForm('loginForm')" icon="el-icon-mobile-phone">登录</el-button>
            <el-button @click="resetForm('loginForm')" icon="el-icon-refresh">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
</template>

<script>
    import {login, checkCode} from "../api/userApi";

    export default {
      name: "Login",
      data() {
        return {
          loginForm: {
            username: '',
            password: '',
            verificationCode: ''
          },
          rules: {
            username: [
              { required: true, message: '请输入账户名', trigger: 'blur' },
              { min: 3, max: 16, message: '长度在 3 到 16 个字符', trigger: 'blur' }
            ],
            password: [
              { required: true, message: '请输入密码', trigger: 'blur' },
              { min: 3, max: 16, message: '长度在 3 到 16 个字符', trigger: 'blur' }
            ],
            verificationCode: [
              { required: true, message: '请输入验证码', trigger: 'blur' }
            ],
          }
        };
      },
      methods: {
        //校验验证码
        async checkCode(){
          const {data} = await checkCode(this.loginForm.verificationCode);
          if(data.status){
            this.toLogin();
          }else{
            this.$message.error(data.message);
          }
        },
        //登陆方法
        async toLogin(){
          await login(this.loginForm.username, this.loginForm.password);
        },
        //提交表单
        submitForm(formName) {
          this.$refs[formName].validate((valid) => {
            if (valid) {
              this.checkCode();
            } else {
              return false;
            }
          });
        },
        //重置表单
        resetForm(formName) {
          this.$refs[formName].resetFields();
        }
      },
      created() {
        window.localStorage.removeItem("token");
        window.localStorage.removeItem("buttons");
      }
    }
</script>

<style lang="less" scoped>
  //登录容器
  .login_container{
    background-image: url(../assets/image/starrySky.jpg);
    height: 100%;
    background-color: #000003;
    //登录盒子
    .login_box{
      height: 400px;
      width: 500px;
      background-color: #a7ffc0;
      border-radius: 3px;
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%,-50%);
      //头像盒子
      .avatar_box{
        height: 120px;
        width: 120px;
        border-radius: 50%;
        border: 1px solid #EEEEEE;
        background-color: #FFFFFF;
        position: absolute;
        left: 50%;
        transform: translate(-50%,-50%);
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
        padding: 5px;
        //头像图片
        img{
          width: 100%;
          height: 100%;
          border-radius:50%;
          background-color: #EEEEEE;
        }
      }
    }
    //表单
    .login_form{
      position: absolute;
      bottom: 0px;
      width: 100%;
      padding: 0px 50px;
      box-sizing: border-box;
      //表单按钮
      .login_btn{
        display: flex;
        justify-content: flex-end;
      }
      //验证码
      .verificationCode_box{
        display: flex;
        .verficationCode{
          width: 60%;
          justify-content: left;
        }
        .verficationCode_img{
          width: 30%;
          justify-content: flex-end;
          height: 40px;
          margin-left: 10%;
        }
      }
    }
  }
</style>
