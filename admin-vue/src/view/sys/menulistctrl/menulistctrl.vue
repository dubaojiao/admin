<template>
	<div class="menulistctrl">

		<Card :bordered="false">
	        <div slot="title">
	        	<div class="des-cardTittle-box">
	        		<div class="leftFlex">
	        			<Divider orientation="left">菜单管理</Divider>
	        		</div>
	        		<div class="rightFLex">
	        			<Button type="primary" @click="addMenulist">添加菜单</Button>
	        		</div>
	        	</div>
	        </div>
			<Table :loading="tableLoading" border :columns="menuListColumns" :data="menulistData"></Table>
		</Card>

		<!--modal区域-->
	   	<Modal
	        title="添加菜单"
	        v-model="addMenuModal"
	        :mask-closable="false">

				<Tabs v-model="addType">
			        <TabPane label="父菜单:" name="menu1">
		        		<Form ref="roleForm" :model="parentMenu" :label-width="80">
					    	<FormItem label="路由:">
					            <Input v-model.trim="parentMenu.menuUrl" placeholder="路由"></Input>
					        </FormItem>
					        <FormItem label="名称:">
				        	 	<Input v-model.trim="parentMenu.menuName" placeholder="名称"></Input>
					        </FormItem>
					        <FormItem label="图标:">
				        	 	<Input v-model.trim="parentMenu.icon" placeholder="图标"></Input>
					        </FormItem>
					        <FormItem label="备注:">
				        	 	<Input v-model.trim="parentMenu.menuRemark" placeholder="备注"></Input>
					        </FormItem>
				        </Form>
			        </TabPane>
			        <TabPane label="子菜单" name="menu2">
			        	<Form ref="roleForm" :model="childMenu" :label-width="80">

					    	<FormItem label="父菜单:">
								<Select v-model="childMenu.parentId">
							        <Option v-for="parItem in parentList" :value="parItem.menuId">{{parItem.menuName}}</Option>
							    </Select>
					    	</FormItem>
					    	<FormItem label="路由:">
					            <Input v-model.trim="childMenu.menuUrl" placeholder="路由"></Input>
					        </FormItem>
					        <FormItem label="名称:">
				        	 	<Input v-model.trim="childMenu.menuName" placeholder="名称"></Input>
					        </FormItem>
					        <FormItem label="图标:">
				        	 	<Input v-model.trim="childMenu.icon" placeholder="图标"></Input>
					        </FormItem>
					        <FormItem label="备注:">
				        	 	<Input v-model.trim="childMenu.menuRemark" placeholder="备注"></Input>
					        </FormItem>
				        </Form>
			        </TabPane>
			    </Tabs>


		     	<div slot="footer">
		            <Button @click="cencleMenu">取消</Button>
					<Button type="primary" @click="sureAddMenu">提交</Button>
		        </div>
	    </Modal>


	</div>
</template>
<script>

//引入二级表格增强组件
import menuTabRender from "./menuTabRender";

//引入api
import api from "../../../api/index.js"
export default {
    name: 'menulistctrl',
    components: {
    	menuTabRender
    },
    data () {
        return {

        	//table加载
        	tableLoading:true,

        	menuListColumns: [
        		{
                    type: 'expand',
                    width: 50,
                    render: (h, params) => {
                        return h(menuTabRender, {
                            props: {
                                row: params.row.children,
                            },
                            on:{
                            	refreshList:this.getMenuTreeList
                            }
                        })
                    }
                },
                {
                    title: '菜单文本',
                    key: 'title',
                },
                {
                    title: '路由',
                    key: 'path'
                },
                {
                    title: '名称',
                    key: 'name'
                },
                {
                    title: '图标',
                    key: 'icon'
                },
                {
                    title: '菜单id',
                    key: 'menuId'
                },
                {
                    title: '操作',
                    width: 180,
                    align: 'center',
                    render: (h, params) => {
                    	//禁用启用判断
                    	var rendText="启用";
                    	var rendStyle="info";
                    	if(params.row.att.menuState==1){
                    		rendText="禁用"
                    		rendStyle="warning";
                    	}
                        return h('div', [
                            h('Button', {
                                props: {
                                    type: 'error',
                                    size: 'small'
                                },
                                on: {
                                    click: () => {
                                    	this.$Modal.confirm({
                                    		title:"系统提示",
                                    		content:"是否删除菜单",
                                    		onOk:()=>{
                                    			this.$Spin.show();
												api.getData("sys/removeMenu?menuId="+params.row.menuId,this).then((res)=>{
									    			if(res.state==0){
									    				this.$Notice.error({
										                    title: '提示',
										                    desc:res.msg,
										                });
									    			}else{
									    				this.getMenuTreeList();
									    			}
									    			this.$Spin.hide();
									    		})
                                    		}
                                    	})
                                    }
                                }
                            }, '删除'),
                            h('Button', {
                                props: {
                                    type:rendStyle,
                                    size: 'small'
                                },
                                style:{
                                	marginLeft:"5px"
                                },
                                on: {
                                    click: () => {
                                    	this.$Modal.confirm({
                                    		title:"系统提示",
                                    		content:"是否"+rendText+"菜单",
                                    		onOk:()=>{
                                    			this.$Spin.show();
												api.getData("sys/updateMenuState?menuId="+params.row.menuId,this).then((res)=>{
									    			if(res.state==0){
									    				this.$Notice.error({
										                    title: '提示',
										                    desc:res.msg,
										                });
									    			}else{
									    				this.getMenuTreeList();
									    			}
									    			this.$Spin.hide();
									    		})

                                    		}
                                    	})
                                    }
                                }
                            },rendText)
                        ]);
                    }
                }
            ],
            //菜单数据
            menulistData:[],
            //添加菜单modal
	        addMenuModal:false,
	        //父菜单数据
	        parentMenu:{
	        	parentId:"",//父级ID
	        	menuLevel:"",//层级
			 	    menuUrl:'',//菜单路由 path
		        menuName:'',//菜单名称 title
		        menuRemark:'',//备注 name
		        icon:"",//图标
	        },
	        //获取父菜单列表
	        parentList:[],
	        //子菜单数据
	        childMenu:{
	        	parentId:"",//父级id
	        	menuLevel:"",
		 		    menuUrl:'',//菜单路由 path
		        menuName:'',//菜单名称 title
		        menuRemark:'',//备注 name
		        icon:"",//图标
	        },
	        //获取添加的菜单类型
	        addType:"menu1"
        };

    },
    created(){
    	this.getMenuTreeList();
    },
    methods: {
    	//添加菜单
    	addMenulist(){
    		this.addMenuModal=true;
    		//获取菜单数据
    		this.getMenuListByParentId();
    	},

    	//取消添加父菜单
    	cencleMenu(){
    		for(var i in this.parentMenu){
    			this.parentMenu[i]="";
    		}
    		for(var i in this.childMenu){
    			this.childMenu[i]="";
    		}
    		this.addMenuModal=false;
    	},
    	//添加父菜单
    	sureAddMenu(){
    		//定义提交shuju
    		var postData="";

    		if(this.addType=="menu1"){
    			//添加父菜单
    			//然parentid为0
    			//添加层级为1
    			this.parentMenu.parentId=0;
    			this.parentMenu.menuLevel=1;
    			//父菜单添加/
    			this.parentMenu.menuUrl="/"+this.parentMenu.menuUrl;
 				//赋值提交数据
 				postData=this.parentMenu;
    		}else{
    			//添加父菜单
    			//添加层级为1
    			this.childMenu.menuLevel=2;
 				//赋值提交数据
 				 postData=this.childMenu;
    		}
    		//提交数据
    		this.$Spin.show();
    		api.postData("sys/saveMenu",postData,this).then((res)=>{
    			if(res.state==0){
    				this.$Notice.error({
	                    title: '提示',
	                    desc:res.msg,
	                });
    			}else{
    				//退出添加
    				//提示操作成功
					//重载数据
					this.cencleMenu();
					this.getMenuTreeList();
					this.$Notice.success({
	                    title: '提示',
	                    desc:"操作成功",
	                });
    			}
    			this.$Spin.hide();
    		})

    	},
    	//获取菜单列表
    	getMenuTreeList(){
    		this.tableLoading=true;
    		api.getData("sys/getMenuTreeList",this).then((res)=>{
    			if(res.state==0){
    				this.$Notice.error({
	                    title: '提示',
	                    desc:res.msg,
	                });
    			}else{
    				this.menulistData=res.data;
    			}
    			this.tableLoading=false;
    		})
    	},

    	//获取父菜单列表
    	getMenuListByParentId(){
    		this.$Spin.show();
    		api.getData("sys/getMenuListByParentId?pId=0",this).then((res)=>{
    			if(res.state==0){
    				this.$Notice.error({
	                    title: '提示',
	                    desc:res.msg,
	                });
    			}else{
    				this.parentList=res.data;
    			}
    			this.$Spin.hide();
    		})
    	}


    }
};
</script>

<style lang="less" scoped>
	@import 'menulistctrl.less';
</style>
