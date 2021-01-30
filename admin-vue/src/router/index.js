import Vue from 'vue'
import Router from 'vue-router'
import routes from './routers'
import store from '@/store'
import iView from 'iview'
import { setToken, getToken, canTurnTo, setTitle } from '@/libs/util'
import config from '@/config'
const { homeName } = config

Vue.use(Router)
const router = new Router({
  routes,
  mode: 'hash'
})
const LOGIN_PAGE_NAME = 'login'
//路由权限判断
function checkUser(name){
    //判断页面是否在路由白名单
    var notCheckUser=store.state.app.notCheckMenulist.indexOf(name);
     if(notCheckUser> -1){
       return "next";
     }else{
      //不在白名单，判断是否拥有权限
      //判断验证是否透过
      if(localStorage.getItem("menulist")){
        var allowMneuList=(localStorage.getItem("menulist")).split("|");
        if(localStorage.getItem("menulist")==""||allowMneuList.length==0){
          //退出登录
          setToken("");
          localStorage.clear();
          return "login";
        }else{
          var allowNext=allowMneuList.indexOf(name);
          if(allowNext> -1){
            return "next";
          }else{
            return "not";
          }
        }
      }else{
        //退出登录
        setToken("");
        localStorage.clear();
        return "login";
      }
    }
}
router.beforeEach((to, from, next) => {
  iView.LoadingBar.start()
  const token = getToken()
  if (!token && to.name !== LOGIN_PAGE_NAME) {
    // 未登录且要跳转的页面不是登录页
    next({
      name: LOGIN_PAGE_NAME
    })
  } else if (!token && to.name === LOGIN_PAGE_NAME) {
    // 未登陆且要跳转的页面是登录页
    next()
  } else if (token && to.name === LOGIN_PAGE_NAME) {
    // 已登录且要跳转的页面是登录页
    next({
      name: homeName 
    })
  } else {

    //进入其他页面
    //判断是否登录
    //判断是否权限过滤
    if(!token){
      next({
        name: 'login'
      })
    }else{
      //检查权限是否通过
      if(checkUser(to.name)=="login"){
        next({
          name: 'login'
        })
      }
      else if(checkUser(to.name)=="next"){
        next();
      }
      else{
        next({ replace: true, name: 'error_401' })
      }
    }
  }
})
router.afterEach(to => {
  setTitle(to, router.app)
  iView.LoadingBar.finish()
  window.scrollTo(0, 0)
})

export default router
