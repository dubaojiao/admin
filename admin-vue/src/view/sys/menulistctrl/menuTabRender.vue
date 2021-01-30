<style scoped>
    .expand-row{
        margin-bottom: 16px;
    }
</style>
<template>
	<div>
	    <Row class="expand-row" v-for="childItem in row">
	        <Col span="6">
	            <span class="expand-key" style="color:#2d8cf0">菜单名称:</span>
	            <span class="expand-value" v-text="childItem.title"></span>
	        </Col>
	        <Col span="6">
	            <span class="expand-key" style="color:#2d8cf0">路由:</span>
	            <span class="expand-value" v-text="childItem.path"></span>
	        </Col>
	        <Col span="6">
	            <span class="expand-key" style="color:#2d8cf0">名称:</span>
	            <span class="expand-value" v-text="childItem.name"></span>
	        </Col>
	        <Col span="6">
	            <Button type="error" size="small" @click="removeMenu(childItem)">删除</Button>
	            
	            <Button style="margin-left:10px;" v-if="childItem.att.menuState==1" type="warning" size="small" @click="chengeMenuState(childItem)">禁用</Button>
	            <Button style="margin-left:10px;" v-if="childItem.att.menuState!=1" type="info" size="small" @click="chengeMenuState(childItem)">启用</Button>
	            
	        </Col>
	    </Row>
	</div>
</template>

<script>
//引入api
import api from "../../../api/index.js"
export default {
    props: {
        row: Array
    },
    methods: {
    	//添加菜单
    	removeMenu(childItem){
    		this.$Modal.confirm({
        		title:"系统提示",
        		content:"是否删除菜单",
        		onOk:()=>{
        			this.$Spin.show();
					api.getData("sys/removeMenu?menuId="+childItem.menuId,this).then((res)=>{
		    			if(res.state==0){
		    				this.$Notice.error({
			                    title: '提示',
			                    desc:res.msg,
			                });
		    			}else{
		    				//子组件发送广播，重载数据
		    				this.$emit('refreshList');
		    			}
		    			this.$Spin.hide();
		    		})
        		}
        	})
    	},
    	/**
    	 *修改菜单状态 
    	 */
    	chengeMenuState(childItem){
    		this.$Modal.confirm({
        		title:"系统提示",
        		content:"是否修改菜单状态",
        		onOk:()=>{
        			this.$Spin.show();
					api.getData("updateMenuState?menuId="+childItem.menuId,this).then((res)=>{
		    			if(res.state==0){
		    				this.$Notice.error({
			                    title: '提示',
			                    desc:res.msg,
			                });
		    			}else{
		    				//子组件发送广播，重载数据
		    				this.$emit('refreshList');
		    			}
		    			this.$Spin.hide();
		    		})
        			
        		}
        	})
    	}
    	
    	
    	
    }
};
</script>
</script>

<style>
</style>