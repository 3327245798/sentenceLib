<template>
  <a-layout>
    <a-layout-header :style="{ background: '#fcfcfc' }">
      <a-menu
        mode="horizontal"
        :default-selected-keys="['2']"
        :style="{ linHeight: '64px', background: '#fcfcfc' }"
      >
        <a-menu-item key="1">公告</a-menu-item>
        <a-menu-item key="2">数据说明</a-menu-item>
        <a-menu-item key="3">互动交流</a-menu-item>
      </a-menu>
    </a-layout-header>
    <a-layout>
      <a-layout-sider :style="{ background: '#f5f5f5', padding: '5px' }">
        <a-layout-content
          :style="{
            background: '#009999',
            height: '50px',
            color: '#fff',
            'font-size': '25px',
            'text-align': 'center',
            'padding-top': '13px',
            'margin-bottom': '10px',
          }"
        >
          最新发布
        </a-layout-content>
        <a-layout-content
          :style="{
            color: '#996b1f',
            'font-size': '2px',
            'padding-top': '13px',
          }"
        >
          <ul>
            <li :style="{ background: '#eefff0', 'margin-bottom': '10px' }">
              05-22 &nbsp;&nbsp;
              <a
                @click="$router.push('noticeDetail')"
                :style="{
                  //'white-space': 'nowrap',
                  overflow: 'hidden',
                  'text-overflow': 'ellipsis',

                  color: '#463900',
                }"
              >
                关于举行宋晓龙学术报告的通知
              </a>
            </li>
            <li :style="{ background: '#eefff0', 'margin-bottom': '10px' }">
              05-21 &nbsp;&nbsp;
              <a
                @click="$router.push('noticeDetail')"
                :style="{
                  overflow: 'hidden',
                  'text-overflow': 'ellipsis',
                  color: '#463900',
                }"
                >关于元旦放假的通知</a
              >
            </li>
            <li :style="{ background: '#eefff0', 'margin-bottom': '10px' }">
              05-18 &nbsp;&nbsp;
              <a
                @click="$router.push('noticeDetail')"
                :style="{
                  overflow: 'hidden',
                  'text-overflow': 'ellipsis',
                  color: '#463900',
                }"
                >关于工厂客服</a
              >
            </li>
            <li></li>
          </ul>
        </a-layout-content>
      </a-layout-sider>
      <a-layout :style="{ width: '45%', padding: '0 24px 24px' }">
        <a-breadcrumb style="margin: 16px 0" separator=">">
          <a-breadcrumb-item>主页</a-breadcrumb-item>
          <a-breadcrumb-item>公告</a-breadcrumb-item>
          <a-breadcrumb-item>公告列表展示</a-breadcrumb-item>
        </a-breadcrumb>
        <a-layout-content
          :style="{
            background: '#fff',
            'padding-top': '5px',
            margin: 0,
            minHeight: '280px',
          }"
        >
         <!--  <ul class="news_ul">
            <li
              class="news_li"
              :style="{ background: '#eefff0', 'margin-bottom': '10px' }"
            >
              <span class="news_title">
                <a
                  @click="$router.push('noticeDetail')"
                  target="_blank"
                  title="关于举行宋孝龙先生学术报告会的通知"
                  >关于举行宋孝龙先生学术报告会的通知</a
                >
              </span>
              <span class="news_meta" :style="{ 'margin-right': '60px' }"
                >2022-04-02</span
              >
            </li>

            <li
              class="news_li"
              :style="{ background: '#eefff0', 'margin-bottom': '10px' }"
            >
              <span class="news_title">
                <a @click="$router.push('pagination')"
                target="_blank"
                title="关于工厂客服"
                  >关于工厂客服</a
                >
              </span>
              <span class="news_meta">2021-12-17</span>
            </li>

            <li
              class="news_li"
              :style="{ background: '#eefff0', 'margin-bottom': '10px' }"
            >
              <span class="news_title">
                <a href="#" target="_blank" title="关于元旦放假的通知"
                  >关于元旦放假的通知</a
                >
              </span>
              <span class="news_meta">2020-11-18</span>
            </li>
          </ul> -->
         	<a-list size="small" :pagination="{ ...paginationProps, current: currentPage }">
  		<a-list-item :key="index" v-for="(item, index) in showData">
    		<!-- <a-list-item-meta description="">
            	<a-avatar slot="avatar" size="small" shape="square" :src="item.avatar"/>
             	<a slot="title">{{ item.enterprise_name }}</a>
        </a-list-item-meta> -->
            
            <div class="list-content-item">
              <!-- <span>公告标题</span> -->
              <a @click="$router.push('noticeDetail')"
                    target="_blank"
                    title="关于举行宋孝龙先生学术报告会的通知">{{ item.title }}</a>
            </div>
             <div class="list-content-item">
              <!-- <span>发布日期</span> -->
              <p>{{ item.date }}</p>
            </div>
          <!--   <div class="list-content-item">
              <span>状态</span>
              <div>
                <a-badge :status="item.files_state | statusTypeFilter" :text="item.files_state | statusFilter" />
              </div>
        	</div> -->
    	</a-list-item>
	</a-list>
          <div :style="{border:'2px solid red',height:'50px'}">
        
           
        </div >
        </a-layout-content>
        
      </a-layout>
    </a-layout>
    <a-layout-footer
      :style="{
        height: '50px',
        background: '#000',
        color: '#fff',
        'text-align': 'center',
      }"
    >
      <div>华南理工大学 版权所有 All Rights Reserved 粤ICP备05084312号</div>
    </a-layout-footer>
  </a-layout>
</template>
<script>
const statusMap = {
  0: {
    status: 'error',
    text: '未完善'
  },
  1: {
    status: 'success',
    text: '已完善'
  }
}
const avatarArr = [
  'https://gw.alipayobjects.com/zos/rmsportal/WdGqmHpayyMjiEhcKoVE.png',
  'https://gw.alipayobjects.com/zos/rmsportal/zOsKZmFRdUtvpqCImOVY.png',
  'https://gw.alipayobjects.com/zos/rmsportal/dURIMkkrRFpPgTuzkwnB.png',
  'https://gw.alipayobjects.com/zos/rmsportal/sfjbOqnsXXJgNCjCzDBL.png'
]
const enterpriseArr = [ '##1', '##2', '##3', '##4' ]
const titleArr=['关于举行宋孝龙先生学术报告会的通知','关于工厂客服','关于元旦放假的通知','关于华工国际校区搬宿舍的通知']
const dateArr=['2022-5-20','2022-5-21','2022-5-22','2022-5-23']
const data = []
for (let i = 0; i < 50; i++) {
  data.push({
    avatar: avatarArr[i % 4],
    enterprise_name: `${enterpriseArr[i % 4]}${Math.floor(i / 4 + 1)}`,
    registered_datetime: new Date(),
    files_state: i % 2,
    title:titleArr[i%4],
    date:dateArr[i%4]
  })
}
export default {
  name: 'Certification',
  data () {
    return {
      currentPage: 1, // 当前页
      originData: data,
      data
    }
  },
  computed: {
    paginationProps () {
      const _this = this
      return {
        showQuickJumper: true,
        pageSize: 4,
        total: this.data.length,
        onChange (page, pageSize) {
          _this.currentPage = page
        }
      }
    },
    showData () {
      const { pageSize, total } = this.paginationProps // 每页条数
      const res = []
      const start = (this.currentPage - 1) * pageSize
      const end = this.currentPage * pageSize < total ? (this.currentPage * pageSize) : total
      for (let i = start; i < end; i++) {
        res.push(this.data[i])
      }
      return res
    }
  },
  filters: {
    statusFilter (type) {
      return statusMap[type].text
    },
    statusTypeFilter (type) {
      return statusMap[type].status
    }
  }
}
/* export default {
  data() {
    return {
      pageSizeOptions: ["10", "20", "30", "40", "50"],
      current: 1,
      pageSize: 10,
      total: 50, 
      currentPage: 1, 
      originData: data,
      data
    };
  },
  methods: {
   onShowSizeChange(current, pageSize) {
      this.pageSize = pageSize;
    },
     onChange(pageNumber) {
        console.log('Page: ', pageNumber);
      }, 
  },
} */;
</script>
<style>
.list-content-item {
	color: rgba(0, 0, 0, .45);
    display: inline-block;
    vertical-align: middle;
    font-size: 14px;
    margin-left: 0px;
    flex: 1;
    
}
.list-content-item span {
    	line-height: 5px;
    }
.list-content-item p {
    	margin-top: 5px;
      margin-bottom: 5px;
      line-height: 5px;
    }
</style>