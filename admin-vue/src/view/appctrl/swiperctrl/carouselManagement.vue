<template>
  <div class="advertisingManagement">
    <div class="mian" v-if="pageShow==''">
      <Card bordered>
        <div slot="title" class="article-search-top">
          <Row>
            <Col :xs="24" :sm="24" :md="14" :lg="14">
              <Divider orientation="left">暂未使用</Divider>
            </Col>
            <Col :xs="24" :sm="24" :md="10" :lg="10">
                <Form :label-width="80">
                  <div class="des-cardTittle-box">
                    <div class="leftFlex">
                      <FormItem label="关键词:">
                        <Input type="text" placeholder="搜索" v-model="queryText"> </Input>
                      </FormItem>
                    </div>
                    <div class="rightFlex">
                        <Button type="info" style="margin-left:20px;" @click="query">查询</Button>
                    </div>
                    <div class="rightFlex">
                      <Button type="primary" @click="add" style="marginLeft:10px;">添加轮播图</Button>
                    </div>
                  </div>
                </Form>
            </Col>
          </Row>
        </div>
          <!-- 表格 -->
          <Table border :columns="tableTitle" :data="tableData"></Table>
      </Card>

        <Card bordered style="margin-top:10px;">
        <div slot="title" class="article-search-top">
          <Row>
            <Col :span="24">
              <Divider orientation="left">使用中</Divider>
            </Col>
          </Row>
        </div>
        <!-- 表格 -->
          <Row>
            <Col :xs="24" :sm="24" :md="bannerTable.length>0?16:24" :lg="bannerTable.length>0?16:24">
              <Table :columns="bannerTitle" :data="bannerTable"></Table>
            </Col>
            <Col :xs="24" :sm="24" :md="8" :lg="8" style="padding-left:20px;">
              <Carousel autoplay loop v-if="bannerTable.length>0">
                <CarouselItem v-for="(item,index) in bannerList" :key="index">
                    <div class="demo-carousel">
                      <img style=" width:100%; height:auto; display: block;" :src="item" />
                    </div>
                </CarouselItem>
            </Carousel>
            </Col>
        </Row>

      </Card>
    </div>
    <addCarousel v-if="pageShow=='add' " v-on:changePageShow="this.changePageShow"  :changeData="changeData"></addCarousel>
    <bannerDetails v-if="pageShow=='see' " v-on:changePageShow="this.changePageShow"  :changeData="changeData"></bannerDetails>

  </div>
</template>

<script>
import addCarousel from './addCarousel'
import bannerDetails from './bannerDetails'
import api from '../../../api/index.js'
import moment from 'moment'
import { async } from 'q'
export default {
  data () {
    return {
      pageShow: '',
      showPage: true,
      tableData: [], // 未使用缩略图
      bannerTable: [], // 已使用轮播图
      changeData: '',
      bannerList: '', // 使用中的广告图
      queryText: '', // 模糊查询
      tableTitle: [
        {
          title: '#',
          type: 'index'
        },
        {
          title: '缩略图',
          key: 'avatar',
          columns: {
            height: '50px'
          },
          render: (h, params) => {
            return h('div', [
              h('img', {
                attrs: {
                  src: params.row.basePath+params.row.img
                },
                style: {
                  width: '100px',
                  height: '40px',
                  marginTop: '5px'
                }
              })
            ])
          }
        },
        // {
        //   title: '背景图',
        //   key: 'avatar',
        //   columns: {
        //     height: '50px'
        //   },
        //   render: (h, params) => {
        //     return h('div', [
        //       h('img', {
        //         attrs: {
        //           src: params.row.backGroundUrl
        //         },
        //         style: {
        //           width: '100px',
        //           height: '40px',
        //           marginTop: '5px'
        //         }
        //       })
        //     ])
        //   }
        // },
        {
          title: '标题',
          key: 'title'
        },
        {
          title: '添加人',
          key: 'addUser'
        },
        {
          title: '创建时间',
          key: 'gmtCreate',
          render: (h, params) => {
            return h('span', moment(params.row.gmtCreate).format('YYYY-MM-DD HH:mm:ss'))
          }
        },
        {
          title: '状态',
          key: 'enable',
          render: (h, params) => {
            return h('div', [
              h('span', {
                style: {
                  color: params.row.enable == 1 ? '#000' : '#f00'
                }
              }, params.row.enable == 1 ? '正常' : '停用')
            ])
          }
        },
        {
          title: '操作',
          key: 'action',
          width: 200,
          align: 'center',
          render: (h, params) => {
            return h('div', [
              h('Button', {
                props: {
                  type: 'info',
                  size: 'small'
                },
                on: {
                  click: async () => {
                    var getArticleInfo = await this.changeBanner(params.row.id)
                    if (getArticleInfo) {
                      this.pageShow = 'add'
                    }
                  }
                }
              }, '修改'),
              h('Button', {
                props: {
                  type: 'primary',
                  size: 'small'
                },
                style: {
                  marginLeft: '5px'
                },
                on: {
                  click: () => {
                    this.$Modal.confirm({
                      title: '系统提示',
                      content: '是否启用？',
                      onOk: () => {
                        this.changeState(params.row.id)
                      }
                    })
                  }
                }
              }, '启用'),
              h('Button', {
                props: {
                  type: 'error',
                  size: 'small'
                },
                style: {
                  marginLeft: '5px'
                },
                on: {
                  click: () => {
                    this.$Modal.confirm({
                      title: '系统提示',
                      content: '是否删除？',
                      onOk: () => {
                        this.deleteBanner(params.row.id)
                      }
                    })
                  }
                }
              }, '删除')
            ])
          }
        }
      ],
      // 使用中的轮播图
      bannerTitle: [
        {
          title: '#',
          type: 'index'
        },
        {
          title: '标题',
          key: 'title'
        },
        {
          title: '添加人',
          key: 'addUser'
        },
        {
          title: '创建时间',
          key: 'gmtCreate',
          render: (h, params) => {
            return h('span', moment(params.row.gmtCreate).format('YYYY-MM-DD HH:mm:ss'))
          }
        },
        {
          title: '状态',
          key: 'enable',
          render: (h, params) => {
            return h('div', [
              h('Icon', {
                props: {
                  type: 'person'
                }
              }),
              h('strong', params.row.enable ? '启用' : '禁用')
            ])
          }
        },
        {
          title: '操作',
          key: 'action',
          width: 130,
          align: 'center',
          render: (h, params) => {
            return h('div', [
              h('Button', {
                props: {
                  type: 'info',
                  size: 'small'
                },
                on: {
                  click: async () => {
                    var getArticleInfo = await this.changeBanner(params.row.id)
                    if (getArticleInfo) {
                      this.pageShow = 'see'
                    }
                  }
                }
              }, '详情'),
              h('Button', {
                props: {
                  type: 'warning',
                  size: 'small'
                },
                style: {
                  marginLeft: '5px'
                },
                on: {
                  click: () => {
                    this.$Modal.confirm({
                      title: '系统提示',
                      content: '是否禁用？',
                      onOk: () => {
                        this.changeState(params.row.id)
                      }
                    })
                  }
                }
              }, '禁用')
            ])
          }
        }
      ]
    }
  },

  components: {
    addCarousel,
    bannerDetails
  },

  created () {
    this.getDataList()
  },

  methods: {

    // 获取表格数据
    getDataList () {
      this.$Spin.show()
      api.getData('application/get/banner/list', this).then((res) => {
        if (res.state == 0) {
          this.$Notice.error({
            title: '提示',
            desc: res.msg
          })
        } else {
          this.bannerTable = res.data[0] // 使用中
          this.tableData = res.data[1] // 暂未使用

          let arr = []
          for (let i in this.bannerTable) {
            let a = this.bannerTable[i].basePath+this.bannerTable[i].img;
            arr.push(a)
          }
          this.bannerList = arr
          console.log(this.bannerList);
        }
        this.$Spin.hide()
      })
    },

    // 关键字查询
    query () {
      this.$Spin.show()
      api.getData('application/get/banner/word?keyWord=' + this.queryText, this).then((res) => {
        if (res.state == 0) {
          this.$Notice.error({
            title: '提示',
            desc: res.msg
          })
        } else {
          this.bannerTable = res.data[0] // 使用中
          this.tableData = res.data[1] // 暂未使用
          let arr = []
          for (let i in this.bannerTable) {
            let a = this.bannerTable[i].thumbnailUrl
            arr.push(a)
          }
          this.bannerList = arr
        }
        this.$Spin.hide()
      })
    },

    // 修改/详情 根据id查询
    changeBanner (id) {
      return new Promise((resolve, reject) => {
        this.$Spin.show()
        api.getData('application/get/banner/' + id, this).then((res) => {
          if (res.state == 0) {
            this.$Notice.error({
              title: '提示',
              desc: res.msg
            })
            resolve(false)
          } else {
            this.changeData = res.data
            resolve(true)
          }
          this.$Spin.hide()
        })
      })
    },

    // 状态更改
    changeState (id) {
      this.$Spin.show()
      api.getData('application/update/banner/state/' + id, this).then((res) => {
        if (res.state == 0) {
          this.$Notice.error({
            title: '提示',
            desc: res.msg
          })
        } else {
          this.getDataList()
          this.$Notice.success({
            title: '提示',
            desc: '状态修改成功'
          })
        }
        this.$Spin.hide()
      })
    },

    // 删除
    deleteBanner (id) {
      api.getData('application/remove/banner/' + id, this).then((res) => {
        if (res.state == 0) {
          this.$Notice.error({
            title: '提示',
            desc: res.msg
          })
        } else {
          this.getDataList()
          this.$Notice.success({
            title: '提示',
            desc: '删除成功'
          })
          this.getDataList()
        }
      })
    },

    // 添加
    add () {
      this.changeData = ''
      this.pageShow = 'add'
    },

    // 关闭页面
    changePageShow (i) {
      this.getDataList()
      this.pageShow = i
    }
  }
}
</script>

<style scoped lang="less">
.article-search-top .ivu-form-item{
  margin-bottom:0px;
}
.demo-carousel {
  height: 100%;
}
.demo-carousel img {
  height: 100%;
}
</style>
