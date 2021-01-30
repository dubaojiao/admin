import axios from 'axios';
import { getToken } from '@/libs/util'
//引入配置文件
import config from '@/config';
axios.defaults.withCredentials=true;
axios.defaults.headers['Content-Type'] = 'application/json;charset=UTF-8';

// api地址
const base = process.env.NODE_ENV === 'production'
	? config.baseUrl.pro
	: config.baseUrl.dev;

//文件上传基础地址
export const baseUrl = base;
/*
axios.interceptors.request.use((config) => {
  let token = getToken();
  if(token){
    config.headers['k']= token
  }
  return config;
},(error) =>{
  return Promise.reject(error);
});*/

//get
export const getData = (getUrl,globeVue) => {
 	return Promise.race([
		new Promise((resolve, reject) => {
			axios.get(base+getUrl)
			.then((response)=>{

				//无法进入
				if(response.data.code!=200){
					if(response.data.code==401){
						// 退出登录
						globeVue.$store.commit('logOut');
						this.$router.replace({
							name: 'login'
						});
						resolve({
							state:0,
							msg:"重新登陆"
						});
					}else{
						var timerOut={
			                "state":0,
			                "msg":response.data.message
			            }
						resolve(timerOut);
					}
				}
				else{
					var timerOut={
		                "state":1,
		                "msg":response.data.message,
		                "data":response.data.data
		            }
					resolve(timerOut);
				}



			})
			.catch((error)=>{
				resolve({
					state:0,
					msg:"网络错误，请联系管理员"
				});
			});
		}),
	 	new Promise((resolve, reject)=>{
            var timerOut={
                "state":0,
                "msg":"请求超时"
            }
            setTimeout(() => {
            	resolve(timerOut);
            },50000);
        })
 	])
}
//post
//globeVue 全局vue对象
export const postData = (postUrl,param,globeVue) => {

	return Promise.race([
		new Promise((resolve, reject) => {
			axios.post(base+postUrl,param)
			.then((response)=>{
				//无法进入
				if(response.data.code!=200){
					if(response.data.code==401){
						// 退出登录
						globeVue.$store.commit('logOut');
						globeVue.$router.push({
							name: 'login'
						});
	                    resolve({
							state:0,
							msg:"重新登陆"
						});
					}else{
						var timerOut={
			                "state":0,
			                "msg":response.data.message
			            }
						resolve(timerOut);
					}
				}
				else{
					resolve(response.data);
				}
			})
			.catch((error,response)=>{
                console.log(base+postUrl);
                console.log(param);
				resolve({
					state:0,
					msg:"网络错误，请联系管理员"
				});
			});
		}),
		new Promise((resolve, reject)=>{
	        var timerOut={
                "state":0,
                "msg":"请求超时"+postUrl
            }
            setTimeout(() => {
            	resolve(timerOut);
            },50000);
	    })
	])
}
