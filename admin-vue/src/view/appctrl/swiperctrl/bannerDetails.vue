<template>
  <div class="bannerDetails">
    <Row>
      <Col :span="24">
        <Card shadow v-if="formData">
          <p slot="title">轮播信息</p>
          <Form ref="formData" :model="formData" :label-width="150">
            <FormItem label="活动名称:" prop="title">
              <span style="color:#2d8cf0;">{{formData.title}}</span>
            </FormItem>

            <FormItem label="广告点击跳转类型：" prop="type">
              <span style="color:#2d8cf0;">
                {{formData.type == 1 ? 'App页面': '网页链接'}}
              </span>
            </FormItem>

            <FormItem label="跳转地址：" v-if="formData.type == 2" prop="url">
              <span style="color:#2d8cf0;">https:// {{formData.url}}</span>
            </FormItem>

            <FormItem label="广告图：" prop="thumbnailUrl">
              <div class="des-upload-list">
                <img :src="formData.basePath+formData.img" alt="">
              </div>
            </FormItem>

            <FormItem>
              <Button @click="backPage">返回</Button>
            </FormItem>
          </Form>
        </Card>
      </Col>
    </Row>

  </div>
</template>

<script>
export default {
    data () {
        return {
            changePageShow: '',
            formData:"",
        }
    },

    props: [
        'changeData'
    ],

    created () {
        console.log(this.changeData);
        if (this.changeData !== '') {
            this.formData = this.changeData
        }
    },

    // 关闭页面
    methods: {
        backPage () {
        this.$emit('changePageShow', this.changePageShow)
        }
    }
}
</script>

<style lang="less">
.bannerDetails {
	.des-upload-list {
		display: inline-block;
		width: 300px;
		height: auto;
	}
	.des-upload-list img {
		width: 100%;
		height: 100%;
	}
}

</style>
