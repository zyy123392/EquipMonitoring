<template>

<el-form
    
    class="login-container"
    label-position="left"
    label-width="0px"
  >
    <h3 class="login_title">系统登录</h3>
    <el-form-item prop="account">
      <el-input type="text" v-model="loginData.username" auto-complete="off" placeholder="账号"></el-input>
    </el-form-item>
    <el-form-item prop="checkPass">
      <el-input type="password" v-model="loginData.password" auto-complete="off" placeholder="密码"></el-input>
    </el-form-item>
    <el-checkbox class="login_remember"  label-position="left">记住密码</el-checkbox>
    <el-form-item style="width: 100%">
      <el-button type="primary" style="width: 100%" @click="doLogin">登录</el-button>
    </el-form-item>
  </el-form>

  
</template>

<script>
import axios from "axios";

export default {
  name: "login",
  data() {
    return {
      loginData: {
        username: "",
        password: ""
      }
    };
  },
  methods: {
    doLogin() {
      var _this = this;
      axios
        .post("/api/user/login", this.loginData)
        .then(function(response) {
          if (response.data.code == 0) {
            var path = _this.$route.query.redirect;
            _this.$router.replace({
              path: path == "/" || path == undefined ? "/home" : path
            });
          } else {
            alert("登录失败");
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    }
  }
};
</script>

<style>
.login-container {
  border-radius: 15px;
  background-clip: padding-box;
  margin: 180px auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}
.login_title {
  margin: 0px auto 40px auto;
  text-align: center;
  color: #505458;
}
.login_remember {
  margin: 0px 0px 35px 0px;
  text-align: left;
}
</style>
