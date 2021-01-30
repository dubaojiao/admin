<style lang="less">
    @import "./home.less";
</style>
<template>
    <div class="home-main">
		<Row>
	        <Col :xs="24" :sm="24" :md="12" :lg="8" style="padding-right:10px;padding-left:10px;padding-top:10px;">
		    	<Card :bordered="false">
		    		
		    		<div class="user-face">
		    			<Avatar style="background: #619fe7;" :src="avatorPath" size="large" />
		    			<span v-if="returnUnserInfo" v-text="returnUnserInfo.userName"></span>
		    		</div>
		    		
		    		<!--用户基本信息-->
		    		<div v-if="returnUnserInfo" class="user-info" style="margin-top:20px;">
		    			<p>
		    				<Icon type="ios-clock-outline" size="20"/>
		    				<span style="color:#2d8cf0" v-text="'登录时间:'+returnUnserInfo.loginTime"></span>
		    			</p>
		    			<p>
		    				<Icon type="ios-phone-portrait" size="20"/>
		    				<span style="color:#2d8cf0" v-text="'手机号:'+returnUnserInfo.userPhone"></span>
		    			</p>
		    		</div>
	    		 	<Divider orientation="left">标签</Divider>
		    		<div>
		    			 <Tag color="primary">{{returnUnserInfo.roleName}}</Tag>
		    		</div>
		        </Card>
	        </Col>
	        <Col :xs="24" :sm="24" :md="12" :lg="16" style="padding-right:10px;padding-left:10px;padding-top:10px;">
		    	<Card :bordered="false">
					<Tabs v-model="homeTab">
						<TabPane label="闲庭信步" name="name1">
					    	<lottie
					    		v-if="homeTab=='name1'"
								:options="defaultOptions" 
								:height="500" 
								:width="500" 
							/>
				        </TabPane>
				        <TabPane label="密码修改" name="name2">
				        	<Form 
				        		v-if="homeTab=='name2'"
				        		ref="editPasswordForm" :model="editPasswordForm" :label-width="100" label-position="right" :rules="passwordValidate">
				                <FormItem label="原密码" prop="oldPass">
				                    <Input v-model="editPasswordForm.oldPass" type="password" placeholder="请输入现在使用的密码" ></Input>
				                </FormItem>
				                <FormItem label="新密码" prop="newPass">
				                    <Input v-model="editPasswordForm.newPass" type="password" placeholder="请输入新密码，至少6位字符" ></Input>
				                </FormItem>
				                <FormItem label="确认新密码" prop="rePass">
				                    <Input v-model="editPasswordForm.rePass" type="password" placeholder="请再次输入新密码" ></Input>
				                </FormItem>
				                <FormItem>
				            	 	<Button type="primary" @click="saveEditPass">保存</Button>
				                </FormItem>
				            </Form>
				        </TabPane>
				    </Tabs>
		        </Card>
	        </Col>
	    </Row>
    </div>
</template>

<script>
import Cookies from 'js-cookie';
import api from "../../api/index.js"
//引入md5
var md5 = require('js-md5');
//加载组件
import Lottie from 'vue-lottie';
import * as animationData from './lottitjson/deadpool.json';
export default {
    name: 'home',
    components: {
		Lottie
    },
    data () {
	 	const valideRePassword = (rule, value, callback) => {
            if (value !== this.editPasswordForm.newPass) {
                callback(new Error('两次输入密码不一致'));
            } else {
                callback();
            }
        };
        return {
            homeTab:'name1',
            //用户信息
            userInfo:"",
            editPasswordForm:{
                oldPass: '',
                newPass: '',
                rePass: ''
            },
            passwordValidate: {
                oldPass: [
                    { required: true, message: '请输入原密码', trigger: 'blur' }
                ],
                newPass: [
                    { required: true, message: '请输入新密码', trigger: 'blur' },
                    { min: 6, message: '请至少输入6个字符', trigger: 'blur' },
                    { max: 15, message: '最多输入15个字符', trigger: 'blur' }
                ],
                rePass: [
                    { required: true, message: '请再次输入新密码', trigger: 'blur' },
                    { validator: valideRePassword, trigger: 'blur' }
                ]
            },
        	//loading
        	defaultOptions: {
				animationData: animationData.default
			},
			animationSpeed:1,
			anim: {},
			avatorPath:require("../../assets/images/user-face.png")
        };
    },
    computed: {
    	returnUnserInfo () {
            return this.$store.state.user.userInfo;
        }
    },
    created(){
    },
    methods: {
        saveEditPass () {
        	
            this.$refs["editPasswordForm"].validate((valid) => {
            	
                if (valid) {
                	var newJson={
                		phone:Cookies.get('userphone'),
                		pwd1:md5(this.editPasswordForm.oldPass),
                		pwd2:md5(this.editPasswordForm.newPass)
                	}
                	this.$Spin.show();
                	api.postData("updatePwd",newJson,this).then((res)=>{
		    			if(res.state==0){
		    				this.$Notice.error({
			                    title: '提示',
			                    desc:res.msg,
			                });
		    			}else{
		    				this.$Notice.success({
			                    title: '提示',
			                    desc:"修改成功"
			                });
		    				this.$refs.editPasswordForm.resetFields();
		    			}
		    			this.$Spin.hide();
		    		})
                }
            });
        },
    }
};
</script>
