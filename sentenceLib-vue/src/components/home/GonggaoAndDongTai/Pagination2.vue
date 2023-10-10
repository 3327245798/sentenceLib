<template>
	<a-list size="large" :pagination="{ ...paginationProps, current: currentPage }">
  		<a-list-item :key="index" v-for="(item, index) in showData">
    		<a-list-item-meta description="">
            	<a-avatar slot="avatar" size="large" shape="square" :src="item.avatar"/>
             	<a slot="title">{{ item.enterprise_name }}</a>
            </a-list-item-meta>
            <div slot="actions">
              <a @click="manageFiles(item)">管理文件</a>
            </div>
            <div class="list-content-item">
              <span>平台注册时间</span>
              <p>{{ item.registered_datetime | moment }}</p>
            </div>
            <div class="list-content-item">
              <span>文件状态</span>
              <div>
                <a-badge :status="item.files_state | statusTypeFilter" :text="item.files_state | statusFilter" />
              </div>
        	</div>
    	</a-list-item>
	</a-list>
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
const data = []
for (let i = 0; i < 50; i++) {
  data.push({
    avatar: avatarArr[i % 4],
    enterprise_name: `${enterpriseArr[i % 4]}${Math.floor(i / 4 + 1)}`,
    registered_datetime: new Date(),
    files_state: i % 2
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
</script>

<style  scoped>
.list-content-item {
	color: rgba(0, 0, 0, .45);
    display: inline-block;
    vertical-align: middle;
    font-size: 14px;
    margin-left: 40px;
    flex: 1;
    
}
.list-content-item span {
    	line-height: 20px;
    }
.list-content-item p {
    	margin-top: 4px;
      margin-bottom: 0;
      line-height: 22px;
    }
</style>
