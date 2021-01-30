<style scoped>
</style>
<template>
    <div>
        <Table border :columns="col" :data="[row]"></Table>
    </div>
</template>
<script>
    export default {
        props: {
            row: Object
		},
		data(){
			return{
				col:[
					// {
					// 	title:"是否关注公众号",
					// 	key:"followState",
					// 	render:(h, params)=>{
	                //     	return h('span',params.row.followState==1?"关注":"未关注")
	                //     }
					// },
					// {
					// 	title:"小程序openId",
					// 	key:"openId",
					// 	render:(h, params)=>{
	                //     	return h('span',params.row.openId?params.row.openId:"无")
	                //     }
					// },
					{
						title:"公众号openId",
						key:"openId2",
						render:(h, params)=>{
	                    	return h('span',params.row.openId2?params.row.openId2:"无")
	                    }
					},
					{
						title:"用户unionid",
						key:"unionid",
						render:(h, params)=>{
	                    	return h('span',params.row.unionid?params.row.unionid:"无")
	                    }
					},
					{
						title:"身份证号",
						key:"idCard",
						render:(h, params)=>{
	                    	return h('span',params.row.idCard?params.row.idCard:"无")
	                    }
					},
					{
						title:"性别",
						key:"gender",
						render:(h, params)=>{
	                    	return h('span',params.row.gender?params.row.gender:"无")
	                    }
					},
					{
						title:"生日",
						key:"birthday",
						render:(h, params)=>{
	                    	return h('span',params.row.birthday?params.row.birthday:"无")
	                    }
					},
					{
						title:"备注",
						key:"remark",
						render:(h, params)=>{
	                    	return h('span',params.row.remark?params.row.remark:"无")
	                    }
					}
				]
			}
		}
    };
</script>