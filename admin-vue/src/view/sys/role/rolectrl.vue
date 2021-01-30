<template>

	<div class="rolectrl">
		<Card :bordered="false">
	         <div slot="title">
	        	<div class="des-cardTittle-box">
	        		<div class="leftFlex">
	        			<Divider orientation="left">角色管理</Divider>
	        		</div>
	        		<div class="rightFLex">
	        			<Button type="primary" @click="addRoleFunc">添加角色</Button>
	        		</div>
	        	</div>
	        </div>
			<Table :loading="tableLoading" border :columns="roleListColumns" :data="roleListData"></Table>
		</Card>

		<!--modal区域-->
	   	<Modal
	        title="添加角色"
	        v-model="addRole"
	        :mask-closable="false">
		        <Form ref="roleForm" :model="addroleModel" :rules="addroleRules" :label-width="80">
			    	<FormItem label="角色名称:" prop="roleName">
			            <Input v-model="addroleModel.roleName" placeholder="角色名称"></Input>
			        </FormItem>
			        <FormItem label="角色编码:" prop="roleCode">
			            <Input v-model="addroleModel.roleCode" placeholder="角色编码"></Input>
			        </FormItem>

			        <FormItem label="备注:" prop="roleRemark">
		        	 	<Input v-model="addroleModel.roleRemark" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="备注"></Input>
			        </FormItem>
		        </Form>
		     	<div slot="footer">
		            <Button @click="cencleAddRole">取消</Button>
					<Button type="primary" @click="sureAddRole">提交</Button>
		        </div>
	    </Modal>
	    <!-- 角色赋值菜单 -->
		<Modal
			v-model="userToMenu"
			title="角色赋值菜单"
		  >
			<Tree ref="menuTree" :data="menuList" show-checkbox></Tree>
			<div slot="footer">
	            <Button @click="userToMenu=false;">取消</Button>
				<Button type="primary" @click="postUserMenu">提交</Button>
	        </div>
		</Modal>
	</div>

</template>

<script>
//引入api
import api from "../../../api/index.js"
export default {
    name: 'rolectrl',
    components: {
    },
    data () {
        return {

        	//table加载
        	tableLoading:true,

        	roleListColumns: [
                {
                    title: '角色名称',
                    key: 'roleName',
                },
                {
                    title: '角色编码',
                    key: 'roleCode'
                },
                {
                    title: '创建时间',
                    key: 'createTime'
                },
                {
                    title: '修改时间',
                    key: 'updateTime'
                },
                {
                    title: '状态',
                    key: 'roleState',
                    render:(h, params)=>{
                    	if(params.row.roleState==1){
                    		return h('span',"正常")
                    	}else{
                    		return h('span',"禁用")
                    	}
                    }
                },
                {
                    title: '操作',
                    width: 200,
                    align: 'center',
                    render: (h, params) => {
                    	//禁用启用判断
                    	var rendText="启用";
                    	var rendStyle="info";
                    	if(params.row.roleState==1){
                    		rendText="禁用"
                    		rendStyle="warning";
                    	}
                        return h('div', [
                        	h('Button', {
                                props: {
                                    type:"success",
                                    size: 'small'
                                },
                                style: {
                                    marginRight: '5px'
                                },
                                on: {
                                    click: () => {
                                    	//赋值角色caid
                                    	this.getMenuList(params.row.roleId);
                                    }
                                }
                            },"赋值菜单"),
                            h('Button', {
                                props: {
                                    type:rendStyle,
                                    size: 'small'
                                },
                                style: {
                                    marginRight: '5px'
                                },
                                on: {
                                    click: () => {
                                    	this.$Modal.confirm({
                                    		title:"系统提示",
                                    		content:"是否"+rendText+"角色",
                                    		onOk:()=>{
                                    			this.$Spin.show();
												api.getData("sys/updateRoleState?roleId="+params.row.roleId,this).then((res)=>{
									    			if(res.state==0){
									    				this.$Notice.error({
										                    title: '提示',
										                    desc:res.msg,
										                });
									    			}else{
									    				this.getRoleList();
									    			}
									    			this.$Spin.hide();
									    		})
                                    		}
                                    	})
                                    }
                                }
                            },rendText),
                            h('Button', {
                                props: {
                                    type: 'error',
                                    size: 'small'
                                },
                                on: {
                                    click: () => {
                                    	this.$Modal.confirm({
                                    		title:"系统提示",
                                    		content:"是否删除角色",
                                    		onOk:()=>{
                                    			this.$Spin.show();
												api.getData("sys/removeRole?roleId="+params.row.roleId,this).then((res)=>{
									    			if(res.state==0){
									    				this.$Notice.error({
										                    title: '提示',
										                    desc:res.msg,
										                });
									    			}else{
									    				this.getRoleList();
									    			}
									    			this.$Spin.hide();
									    		})
                                    		}
                                    	})

                                    }
                                }
                            }, '删除')
                        ]);
                    }
                }
            ],
            roleListData:[],//角色数据
            //添加角色
            addRole:false,
            //添加角色数据
            addroleModel:{
            	roleName:"",
            	roleCode:"",
            	roleRemark:"",
            },
            //添加角色数据规则
            addroleRules:{
            	roleName:[
                    { required: true,message: '请输入角色名称', trigger: 'blur' },
                    { pattern:/^[A-Za-z0-9\u4e00-\u9fa5]+$/,message: '角色名称格式不正确', trigger: 'blur' }
                ],
                roleCode:[
                	{ required: true,message: '请输入角色编码', trigger: 'blur' },
                	{ pattern:/([^\s])/g,message: '角色编码格式不正确',trigger: 'blur'}
                ],
                roleRemark:[
                    { pattern:/([^\s])/g,message: '备注格式不正确',trigger: 'blur'}
                ]
            },
			//角色赋值菜单
			userToMenu:false,
			menuList: [],
			staticRoleId:"",
        };
    },
    created(){
    	this.getRoleList();
    },
    methods: {
    	//打开添加模态框
    	addRoleFunc(){
    		this.addRole=true;
    	},
    	//取消添加
    	cencleAddRole(){
    		for(var i in this.addroleModel){
    			this.addroleModel[i]="";
    		}
    		this.addRole=false;
    	},
    	//保存角色
    	sureAddRole(){
    		this.$refs.roleForm.validate((valid) => {
				if(valid){
					this.$Spin.show();
					api.postData("sys/saveRole",this.addroleModel,this).then((res)=>{
		    			if(res.state==0){
		    				this.$Notice.error({
			                    title: '提示',
			                    desc:res.msg,
			                });
		    			}else{
		    				this.$Notice.success({
			                    title: '提示',
			                    desc:"操作成功",
			                });
		    				this.getRoleList();
		    				this.addRole=false;
		    			}
		    			this.$Spin.hide();
		    		})
				}
    		})
    	},
    	//获取角色
    	getRoleList(){
    		this.tableLoading=true;
    		api.getData("sys/getRoleList",this).then((res)=>{
    			if(res.state==0){
    				this.$Notice.error({
	                    title: '提示',
	                    desc:res.msg,
	                });
    			}else{
    				this.roleListData=res.data;
    			}
    			this.tableLoading=false;
    		})
    	},

    	//获取可以赋值到角色的菜单
    	getMenuList(roleId){
    		//存储roleid
			this.staticRoleId=roleId;
			this.menuList=[];
    		api.getData("sys/getRoleMenu?roleId="+roleId,this).then((res)=>{
				if(res.state==0){
					this.$Notice.error({
						title: '来自系统的提示',
						desc:res.msg
					})
				}else{
					//处理数据
					for(var i=0;i<res.data.length;i++){
						if(res.data[i].children.length>0){
							for(var j=0;j<res.data[i].children.length;j++){
								if(res.data[i].children[j].checked>0){
									res.data[i].children[j].checked=true;
								}else{
									res.data[i].children[j].checked=false;
								}
							}
							res.data[i].checked=false;
						}else{
							res.data[i].checked=true;
						}
					}
					this.menuList=res.data;
					this.userToMenu=true;
				}
			})
    	},
    	/**
    	 *提交角色菜单赋值
    	 */
    	postUserMenu(){
			var postArray=[];
			var allSelect=this.$refs.menuTree.getCheckedNodes();
			//获取到checkbox对的
			for(var i=0;i<allSelect.length;i++){

				var basicData=allSelect[i];
				if(basicData.children.length==0&&basicData.parentId>0){
					//获取选中id
					var childMenuId=basicData.menuId;
					var childJson={
						menuId:childMenuId,
						roleId:this.staticRoleId
					}
					postArray.push(childJson);

					var parMenuId=basicData.parentId;
					var parJson={
						menuId:parMenuId,
						roleId:this.staticRoleId
					}
					postArray.push(parJson);

				}else{
					//获取选中id
					var childMenuId=basicData.menuId;
					var childJson={
						menuId:childMenuId,
						roleId:this.staticRoleId
					}
					postArray.push(childJson);
				}
			}
			//提交去重
			var clearPost=this.clearData(postArray);
			//解决角色赋值菜单不能为空的清空
			var newJson={
				roleId:this.staticRoleId,
				list:clearPost
			}
			//提交菜单数据
			this.$Spin.show();
			api.postData("sys/menuToRole",newJson,this).then((res)=>{
				if(res.state==0){
					this.$Notice.error({
						title: '来自系统的提示',
						desc:res.msg
					})
				}else{
					this.$Notice.success({
						title: '来自系统的提示',
						desc:"操作成功"
					});
				}
				this.userToMenu=false;
				this.$Spin.hide();
			})
		},
		//数组去重
		clearData(arrObj){
			var arr = arrObj,
			result = [],
			i,
			j,
			len = arr.length;
			for(i = 0; i < len; i++){
				for(j = i + 1; j < len; j++){
				if(arr[i].menuId === arr[j].menuId){
					j = ++i;
				}
				}
				result.push(arr[i]);
			}
			return result;
		},

    }
};
</script>

<style>
</style>
