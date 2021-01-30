import { setToken, getToken } from '@/libs/util'
export default {
  state: {
    
    //保存用户信息
    userInfo:"",

    token: getToken(),
  },
  mutations: {
    
    //修改用户信息
    muChnageUserInfo(state,targetData){
      state.userInfo=targetData;
    },

    //用户登录标识
    setToken (state, token) {
      state.token = token
      setToken(token)
    },

    //用户退出登录
    logOut (state) {
      setToken("")
      localStorage.clear();
    }

  }
  
}
