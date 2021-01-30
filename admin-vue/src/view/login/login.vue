<style lang="less">
@import "./login.less";
@import "./animate.min.css";
</style>

<template>
  <div :class="loginClass">
    <div class="modal"></div>

    <div style="width:618px;background:#fff;display:none;">
      <img src="./img/1.jpg" style="display:block;width:100%;"/>
      <img src="./img/2.jpg" style="display:block;width:100%;"/>
    </div>


    <div class="login-box">
      <Form ref="loginForm" :model="form" :rules="rules" style="width:100%;">
        <div style="padding-bottom:20px;padding-top:60px;">
          <span style="padding-left:30px;font-size:16px;">账号登录</span>
        </div>
        <FormItem prop="phone">
          <Input v-model.trim="form.phone" placeholder="请输入用户名">
            <span slot="prepend">
              <Icon type="md-contact" size="16"/>
            </span>
          </Input>
        </FormItem>
        <FormItem prop="cacheValue">
          <Input type="password" v-model.trim="form.cacheValue" placeholder="请输入密码">
            <span slot="prepend">
              <Icon type="md-lock" size="14"/>
            </span>
          </Input>
        </FormItem>
        <div style="padding-left: 20px;">
          <Button style="width:40%;" shape="circle" @click="handleSubmit" type="primary">登录</Button>
        </div>
      </Form>
    </div>
    <div class="loading-login">
      <div class="loading-login-info animated fadeInLeft">
        <lottie :options="defaultOptions" :height="100" :width="200"/>
        <span>登录中</span>
      </div>
    </div>
  </div>
</template>

<script>
//加载组件
import Lottie from "vue-lottie";
import * as animationData from "./json/waiting.json";
//引入md5
var md5 = require("js-md5");
//引入api
import api from "../../api/index.js";
export default {
  components: {
    Lottie
  },
  data() {
    return {
      //loading
      defaultOptions: {
        animationData: animationData.default
      },
      //登录动画
      loginClass: "des-login",
      form: {
        phone: "",
        code: "",
        cacheValue: ""
      },
      rules: {
        phone: [
          {
            required: true,
            message: "请输入用户名"
          }
        ],
        cacheValue: [
          {
            required: true,
            message: "密码不能为空"
          }
        ]
      }
    };
  },
  methods: {
    /**
     *登陆
     */
    handleSubmit() {
      //md5加密密码
      this.$refs.loginForm.validate(valid => {
        if (valid && this.loginClass == "des-login") {
          var loginJson={
            code:md5(this.form.cacheValue),
            phone:this.form.phone
          }
          // //md5加密
          // this.form.code = md5(this.form.cacheValue);
          //登陆
          this.loginClass = "des-login activeLogin";
          setTimeout(() => {
            api.postData("user/login",loginJson, this).then(res => {
              if (res.state == 0) {
                this.$Notice.error({
                  title: "提示",
                  desc: res.msg
                });
              } else {
                this.$Notice.success({
                  title: "提示",
                  desc:"登录成功"
                });
                //登录成功
                this.$store.commit('setToken', res.data);
                this.$router.push({
										name: 'home'
                });
              }
              this.loginClass = "des-login";
            });
          }, 2000);
        }
      });
    }
  },
  created() {
    localStorage.clear();
  }
};
</script>



