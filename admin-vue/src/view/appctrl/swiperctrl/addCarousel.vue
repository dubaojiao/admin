<template>
  <div class="addCarousel">
    <Row>
      <Col :span="24">
        <Card shadow>
          <p slot="title">轮播信息</p>
          <Form ref="formData" :model="formData" :rules="ruleValidate" :label-width="150">
            <FormItem label="活动名称:" prop="title">
              <Input v-model.trim="formData.title" placeholder="活动名称" style="max-width:500px"></Input>
            </FormItem>
            
            <!-- <FormItem label="广告点击跳转类型：" prop="type">
              <RadioGroup v-model="formData.type">
                <Radio :label="1">
                  <span>App页面</span>
                </Radio>
                <Radio :label="2">
                  <span>网页链接</span>
                </Radio>
              </RadioGroup>
            </FormItem>

            <FormItem label="跳转地址：" v-if="formData.type == 2" prop="url">
              <Input style="max-width:500px" v-model="formData.url">
               
              </Input>
            </FormItem> -->

            <FormItem label="广告图：" prop="img">
              <Input v-show="false" v-model="formData.img"></Input>
              <div class="des-upload-list" v-if="formData.img">
                <img :src="showUrlBase+formData.img">
              </div>
              <Upload
                ref="uplod"
                name="file"
                accept="image/jpg,image/jpeg,image/png"
                :max-size="2048"
                :on-success="uplodSuccess"
                :on-format-error="uplodError"
                :before-upload="uplodBeforeUpload"
                :data="{type:'banner'}"
                :with-credentials="true"
                :show-upload-list="false"
                :format="['jpg','jpeg','png']"
                :on-exceeded-size="uplodMaxSize"
                type="drag"
                :action="uploadUrl"
                style="display: inline-block;width:58px;"
              >
                <div style="width: 58px;height:58px;line-height: 58px;">
                  <Icon type="ios-camera" size="20"></Icon>
                </div>
              </Upload>
            </FormItem>


            <FormItem>
              <Button type="primary" style="margin-right:10px;" @click="submit('formData')">确定</Button>
              <Button @click="backPage">返回</Button>
            </FormItem>
          </Form>
        </Card>
      </Col>
    </Row>

  </div>
</template>

<script>
import api from '../../../api/index.js';
export default {
    data () {
        return {
            uploadUrl:api.baseUrl+'member/img/upload',
            formData: {
                id:'',
                title: '',
                type:1,
                url: '',
                img: ''
            },

            //图片显示url
            showUrlBase:"",

            //  验证规则
            ruleValidate: {
                title: [
                { required: true, message: '请输入活动名称' }
                ],
                type: [
                { required: true, message: '选项不能为空' }
                ],
                url: [
                { required: true, message: '请输入地址' },
                { pattern: /^(?:http(s)?:\/\/)?[\w.-]+(?:\.[\w\.-]+)+[\w\-\._~:/?#[\]@!\$&'\*\+,;=.]+$/, message: '请输入正确地址格式' }
                ],
                img: [
                { required: true, message: '请上传广告图' }
                ]
            }
        };
    },
    props: [
        'changeData'
    ],
    created () {
        if (this.changeData !== '') {
            for(var i in this.formData){
                this.formData[i]=this.changeData[i];
            }
            this.showUrlBase=this.changeData.basePath;
        }
    },
    methods: {
        // 广告图上传之前
        uplodBeforeUpload (res) {
            this.$refs.uplod.clearFiles();
            this.$Spin.show();
        },

        // 广告图上传成功
        uplodSuccess (res, file) {
            this.$Spin.hide();
            this.formData.img = res.data.img;
            this.showUrlBase=res.data.basePath;
            this.$Notice.success({
                title: '提示',
                desc: '文件上传成功'
            });
        },

        // 广告图上传失败
        uplodError (file) {
            this.$Spin.hide();
            this.$Notice.error({
                title: '提示',
                desc: '文件上传失败'
            });
        },

        // 上传尺寸过大
        uplodMaxSize (file) {
            this.$Spin.hide();
            this.$Notice.warning({
                title: '提示',
                desc: '图片大小不能超过2M'
            });
        },

        // 背景图上传之前
        handleBeforeUpload (res) {
            this.$Spin.show();
            this.$refs.uplodRef.clearFiles();
        },

        // 背景图上传成功
        handleSuccess (res, file) {
            this.$Spin.hide();
            this.backGroundId = res.data.id;
            this.$Notice.success({
                title: '提示',
                desc: '文件上传成功'
            });
        },

        // 背景图上传失败
        handleError (file) {
            this.$Spin.hide();
            this.$Notice.error({
                title: '提示',
                desc: '文件上传失败'
            });
        },

        // 上传尺寸过大
        handleMaxSize (file) {
            this.$Spin.hide();
            this.$Notice.warning({
                title: '提示',
                desc: '图片大小不能超过2M'
            });
        },

        // 提交
        submit (name) {
        this.$refs[name].validate((valid) => {
            if (valid) {
                this.$Spin.show();
                api.postData('application/save/banner', this.formData, this).then((res) => {
                    if (res.state == 0) {
                        this.$Notice.error({
                            title: '提示',
                            desc: res.msg
                        });
                    } else {
                        this.$Notice.success({
                            title: '提示',
                            desc: '保存成功'
                        });
                        this.$emit('changePageShow',"");
                    }
                    this.$Spin.hide();
                });
            }
        });
        },
        backPage () {
        this.$emit('changePageShow',"");
        }
    }
};
</script>

<style lang="less">
.addCarousel {
	.des-upload-list {
		display: inline-block;
		width: 60px;
		height: 60px;
		text-align: center;
		line-height: 60px;
		border: 1px solid transparent;
		border-radius: 4px;
		overflow: hidden;
		background: #fff;
		position: relative;
		box-shadow: 0 1px 1px rgba(0, 0, 0, 0.2);
		margin-right: 4px;
	}
	.des-upload-list img {
		width: 100%;
		height: 100%;
	}
}

</style>
