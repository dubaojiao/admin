
<template>

	<div class="organ-userCtrl">
		<Card :bordered="false">
			<div slot="title">
				<Row>
					<Col :xs="24" :sm="24" :md="10" :lg="10">
						<Divider orientation="left">用户管理</Divider>
					</Col>
					<Col :xs="24" :sm="24" :md="14" :lg="14">
					<Form :label-width="80">
						<div class="des-cardTittle-box">
							<FormItem label="用户类型" class="leftFlex">
								<Select v-model.trim="searchType">
									<Option value="0">全部</Option>
									<Option value="1">后台用户</Option>
								</Select>
							</FormItem>
							<FormItem label="关键词" class="leftFlex">
								<Input v-model.trim="term" placeholder="关键词" />
							</FormItem>
							<Button type="info" style="margin-left:20px;" @click="searchData">查询</Button>
							<Button type="primary" style="margin-left:20px;" @click="changeAddUserModel(true)">添加用户</Button>
						</div>
					</Form>
					</Col>
				</Row>
			</div>

			<Table :loading="tableLoading" border :columns="userListCol" :data="userList"></Table>
			<!--分页数据-->
			<div style="text-align:center;padding-top:10px;" v-if="userList.length>0">
				<Page :current="index" :total="total" :page-size="20" @on-change="goPageData"></Page>
			</div>
		</Card>

		<!--添加用户模态框-->
		<Modal title="添加用户" v-model="addModal" width="500">
			<Form ref="addUser" :model="addUser" :rules="addAdminrules" :label-width="100">


				<FormItem label="用户名:" prop="name">
					<Input type="text" v-model="addUser.name" placeholder="用户名"></Input>
				</FormItem>

				<FormItem label="手机号:" prop="phone">
					<Input type="text" v-model="addUser.phone" placeholder="手机号"></Input>
				</FormItem>



				<FormItem label="密码:" prop="pwd">
					<Input type="password" v-model.trim="addUser.pwd" placeholder="密码"></Input>
				</FormItem>

			</Form>
			<div slot="footer">
				<Button @click="changeAddUserModel(false)" style="margin-left: 8px">取消</Button>
				<Button @click="saveUserInfo" type="primary">保存</Button>
			</div>
		</Modal>


		<!--赋职弹框-->
		<Modal title="给予用户角色" v-model="saveUserRole" width="500">
			<Form :label-width="100">
				<FormItem label="角色名称:" vertical>
					<div  @click="reloadRole">
						<RadioGroup v-model="selectRole">
							<Radio v-for="roleItem in userRoleList" :key="roleItem.roleId" :label="roleItem.roleId">{{roleItem.roleName}}</Radio>
						</RadioGroup>
					</div>
				</FormItem>
			</Form>
			<div slot="footer">
				<Button @click="saveUserRole=false">取消</Button>
				<Button type="primary" @click="postAddUserRole">确定</Button>
			</div>
		</Modal>



		<!-- 用户修改 -->
		<Modal title="修改信息" v-model="changeInfoModel" width="500">
			<Form ref="changeInfoRef" :model="changeInfoItem" :label-width="100">

				<FormItem
					label="用户名:"
					:rules="[{required: true,message:'用户名'}]"
					prop="nickname"
				>
					<Input type="text" v-model.trim="changeInfoItem.nickname" placeholder="用户名"></Input>
				</FormItem>

				<FormItem
					label="手机号:"
					prop="phone"
					:rules="[{
							min:11,
							max:11,
							message: '手机号格式错误'
						},{
							pattern: /^[0-9]*$/,
							message: '手机号输入错误'
						}]"
				>
					<Input type="text" v-model.trim="changeInfoItem.phone" placeholder="用户名称"></Input>
				</FormItem>

				<FormItem
					label="密码:"
					prop="showPwd"
				>
					<Input type="password" v-model.trim="changeInfoItem.showPwd" placeholder="密码"></Input>
				</FormItem>

				<FormItem
					label="身份号:"
					prop="idCard"
				>
					<Input type="password" v-model.trim="changeInfoItem.idCard" placeholder="身份号"></Input>
				</FormItem>

				<FormItem
					label="性别:"
					prop="gender"
				>
					<RadioGroup v-model="changeInfoItem.gender">
						<Radio label="男">男</Radio>
						<Radio label="女">女</Radio>
					</RadioGroup>
				</FormItem>

				<FormItem
					label="生日:"
					prop="birthday"
				>
					<DatePicker v-model="changeInfoItem.birthday" :editable="false" :clearable="false" type="date" placeholder="生日" style="width:100%"></DatePicker>
				</FormItem>


				<FormItem
					label="身高:"
					prop="height"
					:rules="[{
						pattern: /^[0-9]*$/,
						message: '身高格式错误'
					}]"
				>
					<Input type="text" v-model.trim="changeInfoItem.height" placeholder="身高">
						<span slot="append">M</span>
					</Input>
				</FormItem>

				<FormItem
					label="年龄:"
					prop="age"
				>
					<Input type="text" v-model.trim="changeInfoItem.age" placeholder="年龄">
						<span slot="append">岁</span>
					</Input>
				</FormItem>


				<FormItem
					label="备注:"
					prop="remark"
				>
					<Input type="textarea" v-model.trim="changeInfoItem.remark" placeholder="备注"></Input>
				</FormItem>
			</Form>
			<div slot="footer">
				<Button @click="changeInfoModel=false;" style="margin-left: 8px">取消</Button>
				<Button @click="saveChnageInfo" type="primary">保存</Button>
			</div>
		</Modal>
		<!-- 用户修改 -->



	</div>

</template>

<script>
	//引入md5
	var md5 = require("js-md5");
	import userInfo from "./userinfo.vue";
	import api from "../../../api/index.js";
	import moment from "moment";
	export default {
		components: {

		},
		data() {
			return {
				tableLoading: false,

				//筛选用户信息
				index: 1,
				term: "",
				searchType:"",
				total: 0,

				//用户列表
				userList: [],
				userListCol: [
					{
                        type: 'expand',
                        width: 50,
                        render: (h, params) => {
                            return h(userInfo, {
                                props: {
                                    row: params.row
                                }
                            })
                        }
                    },
					{
	                    title: '#',
	                    type: 'index'
	                },
					{
						title: '昵称',
						key: 'nickname',
						render:(h, params)=>{
	                    	return h('span',params.row.nickname?params.row.nickname:"无")
	                    }
					},
					{
						title: '用户姓名',
						key: 'name',
						render:(h, params)=>{
	                    	return h('span',params.row.name?params.row.name:"无")
	                    }
					},

					{
						title: '手机号',
						key: 'phone'
					},

					{
						title: '用户类型',
						key: 'userType',
						render:(h, params)=>{
							if(params.row.userType==1){
								return h('span',"后台用户");
							}
	                    }
					},
					{
						title: '用户角色',
						key: 'roleName',
						render:(h, params)=>{
	                    	return h('span',params.row.roleName?params.row.roleName:"未给予角色")
	                    }
					},

					{
						title: '创建时间',
						key: 'createdTime'
					},
					{
						title: '登录次数',
						key: 'loginNum'
					},
					{
						title: '登录时间',
						key: 'loginTime',
						render:(h, params)=>{
	                    	if(params.row.loginTime){
	                    		return h('span',params.row.loginTime)
	                    	}else{
	                    		return h('span',"未登录过系统")
	                    	}
	                    }
					},
					{
	                    title: '状态',
	                    key: 'state',
	                    render:(h, params)=>{
	                    	if(params.row.state==1){
	                    		return h('span',"正常")
	                    	}else{
	                    		return h('span',"禁用")
	                    	}
	                    }
					},
					{
						title: '操作',
						width: 300,
						align: 'center',
						render: (h, params) => {
							//禁用启用判断
							var rendText = "启用";
							var rendStyle = "info";
							if(params.row.state == 1) {
								rendText = "禁用"
								rendStyle = "warning";
							}
							//渲染数组
							var renderArray = [];
							renderArray.push(
								h('Button', {
									props: {
										type: rendStyle,
										size: 'small'
									},
									style: {
										marginLeft: "5px"
									},
									on: {
										click: () => {
											this.$Modal.confirm({
												title: "提示",
												content: "是否修改用户状态",
												onOk: () => {
													this.$Spin.show();
													api.getData("user/update/state?id="+params.row.id,this).then((res) => {
														if(res.state == 0) {
															this.$Notice.error({
																title: '提示',
																desc: res.msg,
															});
														} else {
															this.getUserInfoList();
														}
												-		this.$Spin.hide();
													})
												}
											})

										}
									}
								}, rendText)
							);
							renderArray.push(
								h('Button', {
									props: {
										type: 'primary',
										size: 'small'
									},
									style: {
										marginLeft: "5px"
									},
									on: {
										click: () => {
											this.cacheUid=params.row.id;
											this.getRoleByUid(params.row.id);
										}
									}
								}, '赋值角色')
							)


							renderArray.push(
								h('Button', {
									props: {
										type: 'info',
										size: 'small'
									},
									style: {
										marginLeft: "5px"
									},
									on: {
										click: () => {
											this.$Spin.show();
											api.getData("user/get/"+params.row.id,this).then((res) => {
												if(res.state == 0) {
													this.$Notice.error({
														title: '提示',
														desc: res.msg,
													});
												} else {

													//重置
													for(var i in this.changeInfoItem){
														this.changeInfoItem[i]="";
													}
													this.$refs.changeInfoRef.resetFields();

													//赋值
													for(var i in this.changeInfoItem){
														if(res.data[i]){
															//密码不重写
															if(i!='pwd'){
																this.changeInfoItem[i]=res.data[i]
															}
														}
													}
													this.changeInfoModel=true;
												}
										-		this.$Spin.hide();
											})

										}
									}
								}, '修改信息')
							)


							return h("div", renderArray)
						}
					}

				],

				//添加用户数据
				addAdminrules: {
					phone: [
						{
							min:11,
							max:11,
							message: '手机号格式错误'
						},
						{
							pattern: /^[0-9]*$/,
							message: '手机号输入错误'
						},
					],
					name:[
						{
							required: true,
							message: '请输入用户名'
						}
					],
					pwd: [
						{
							required: true,
							message: '请输入密码'
						}
					]
				},
				addUser: {
					phone: "",
					name:"",
					pwd:""
				},
				addModal: false,

				//用户角色赋值
				saveUserRole: false,
				userRoleList:[],
				selectRole:0,



				//修改用户信息
				changeInfoModel:false,
				changeInfoItem:{
					id:"",
					/** 用户名称 */
					nickname:"",
					/** 用户名称 */
					name:"",
					/** 用户密码 */
					pwd:"",
					showPwd:"",
					/** 用户身份证号. */
					idCard:"",
					/** 性别 */
					 gender:"",
					/** 生日. */
					birthday:"",
					/** 备注. */
					remark:"",
					/**
					 * 身高
					 */
					height:"",
					/**
					 * 年龄
					 */
					age:""
				}


			}
		},
		methods: {

			//用户信息列表
			getUserInfoList() {
				this.tableLoading = true;
				api.getData('user/list?index='+ this.index + "&term=" + this.term+"&type="+this.searchType, this).then((res) => {
					if(res.state == 0) {
						this.$Notice.error({
							title: '提示',
							desc: res.msg,
						});
					} else {
						this.total = res.data.total;
						this.userList = res.data.rows;
					}
					this.tableLoading = false;
				})
			},
			//搜索数据
			searchData() {
				this.index = 1;
				this.getUserInfoList();
			},
			//分页数据
			goPageData(index) {
				this.index = index;
				this.getUserInfoList();
			},

			//打开或者关闭用户添加模态框
			changeAddUserModel(falge) {
				if(falge) {
					this.$refs.addUser.resetFields();
					this.addModal = falge;
				} else {
					this.addModal = falge;
				}
			},

			//保存用户
			saveUserInfo() {
				this.$refs.addUser.validate((valid) => {
					if(valid) {
						var newJson={
							phone:this.addUser.phone,
							pwd:md5(this.addUser.pwd),
							name:this.addUser.name
						}
						this.$Spin.show();
						api.postData('user/add/admin',newJson, this).then((res) => {
							if(res.state == 0) {
								this.$Notice.error({
									title: '提示',
									desc: res.msg,
								});
							} else {
								this.$Notice.success({
									title: '提示',
									desc: "保存成功",
								});
								this.changeAddUserModel(false);
								this.getUserInfoList();
							}
							this.$Spin.hide();
						})
					}
				})
			},

			//获取用户角色列表
			getRoleByUid(uid) {
				this.$Spin.show();
				api.getData("user/get/role/" + uid, this).then((res) => {
					if(res.state == 0) {
						this.$Notice.error({
							title: '来自系统的提示',
							desc: res.msg
						})
					}else {
						this.userRoleList=res.data;
						//判断选中角色
						this.selectRole=0;
						for(var i=0;i<res.data.length;i++){
							if(res.data[i].check){
								this.selectRole=res.data[i].roleId;
								break;
							}
						}

						this.saveUserRole=true;
					}
					this.$Spin.hide();
				})
			},

			//添加用户角色
			postAddUserRole() {
				var postNesJson = {
					uid: this.cacheUid,
					roleId:this.selectRole
				}
				this.$Spin.show();
				//提交数据
				api.postData("user/set/role", postNesJson, this).then((res) => {
					if(res.state == 0) {
						this.$Notice.error({
							title: '来自系统的提示',
							desc: res.msg
						})
					} else {
						this.tableData = res.data;
						this.$Notice.success({
							title: '来自系统的提示',
							desc: "保存成功"
						});
					}
					this.$Spin.hide();
					this.saveUserRole = false;
					//列出数据
					this.getUserInfoList();
				})
			},


			//重置用户角色
			reloadRole(){
				if(this.selectRole){
					this.selectRole=0;
				}
			},



			//保存修改的用户信息
			saveChnageInfo(){
				this.$refs.changeInfoRef.validate((valid) => {
					if(valid) {

						//密码md5
						this.changeInfoItem.pwd=md5(this.changeInfoItem.showPwd);
						//时间
						console.log(this.changeInfoItem.birthday);
						if(this.changeInfoItem.birthday){
							this.changeInfoItem.birthday=moment(this.changeInfoItem.birthday).format("YYYY-MM-DD");
						}

						this.$Spin.show();
						api.postData('user/update/info',this.changeInfoItem, this).then((res) => {
							if(res.state == 0) {
								this.$Notice.error({
									title: '提示',
									desc: res.msg,
								});
							} else {
								this.$Notice.success({
									title: '提示',
									desc: "保存成功",
								});
								this.changeInfoModel=false;
								this.getUserInfoList();
							}
							this.$Spin.hide();
						})
					}
				})
			}





		},
		created() {
			this.getUserInfoList();
		}
	}
</script>

<style lang="less">
.progress-flexBox{
	display: flex;
	.flex-item{
		flex: 1;
		display: flex;
		flex-direction: column;
		font{
			display: block;
		}
	}
	.flex-item:last-child{
		align-items: flex-end;
	}
}
.notActive{
    filter: grayscale(100%);
}
</style>
