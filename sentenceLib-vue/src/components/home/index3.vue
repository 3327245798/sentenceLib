<template>
  <div>
    <!-- :style="{ height: '100%', overflowY: 'auto' }" 修wz改-->
    <!--   <a-carousel class="info-cotainer row" effect="fade" autoplay arrows>
      <div class="info-container">
        <h2 class="row-title">帮语网站平台</h2>
        <div class="row-description">
          聚集不同用户，以灵活开发、便捷使用为理念，共同丰富、完善不同业务类型剧本的开放平台。
        </div>
        <div class="info-img-wrapper">
          <img alt="p_info" :src="pinfo" />
        </div>
      </div>
      <div class="info-container">
        <h2 class="row-title">帮语网站平台</h2>
        <div class="row-description">
          <p class="">
            无需掌握复杂的编程知识，以灵活开发、便捷使用为理念，即学即用，不论背景，轻松上手。
          </p>
        </div>
        <div class="info-img-wrapper">
          <img alt="p_info" :src="pinfo" />
        </div>
      </div>
    </a-carousel> -->
    <div class="navigation">
      <!-- <div class="imgUrl" v-if="navigationuserIsLogin"> -->
      <div class="imgUrl">
        <div class="user" :style="{ float: 'right', 'margin-right': '50px' }">
          <a-icon
            type="user"
            theme="outlined"
            :style="{ fontSize: '3em', color: 'bluepink' }"
          ></a-icon>
          <a-dropdown>
            <a class="ant-dropdown-link" @click="(e) => e.preventDefault()">
              {{ userName }} <a-icon type="down" />
            </a>
            <a-menu slot="overlay">
              <a-menu-item>
                <!-- <a @click="$router.push('logout')">退出</a> -->
                <a @click="logout()">退出</a>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </div>
      </div>
      <!-- <div class="imgUrl" >
        <div class="user" :style="{'float':'right','margin-right':'50px'}">
        <a-icon type="user" theme ="outlined" :style="{'fontSize':'3em','color':'bluepink'}"></a-icon> 
      <span>游客</span> 
         <a class="ant-dropdown-link" @click="showLoginModal">
      登录 
    </a>
    </div>
      </div> -->
 <a-modal
      title="用户登录"
      :visible="loginvisible"
      :confirm-loading="confirmLoginLoading"
      :footer="!isDetail ? null : undefined"
      @cancel="handleLoginCancel"
    >
  <template slot="footer">
        <div>
          <a-button type="white" @click="handleLoginCancel">取消</a-button>
          <a-button type="primary" @click="handleOk">确定</a-button>
        </div>
      </template>

    <a-tabs default-active-key="1" @change="callback">
      <a-tab-pane key="1" tab="账号密码登录">
        <a-form
    id="components-form-demo-normal-login"
    :form="form"
    class="login-form"
    @submit="handleSubmitLogin"
  >
    <a-form-item>
      <a-input
        v-decorator="[
          'userAccount',
          { rules: [{ required: true, message: '请输入用户名' }] },
        ]"
        placeholder="用户名"
      >
        <a-icon slot="prefix" type="user" style="color: rgba(0,0,0,.25)" />
      </a-input>
    </a-form-item>
    <a-form-item>
      <a-input
        v-decorator="[
          'password',
          { rules: [{ required: true, message: '请输入密码!' }] },
        ]"
        type="password"
        placeholder="密码"
      >
        <a-icon slot="prefix" type="lock" style="color: rgba(0,0,0,.25)" />
      </a-input>
    </a-form-item>
    <a-form-item>
      <a-checkbox
        v-decorator="[
          'remember',
          {
            valuePropName: 'checked',
            initialValue: true,
          },
        ]"
      >
        记住我 
      </a-checkbox>
      <a class="login-form-forgot" @click="resetPwd">
        忘记密码
      </a>
      <a-button type="primary" html-type="submit" class="login-form-button">
        登录
      </a-button>
      或者
      <a  @click="register">
        现在注册!
      </a>
    </a-form-item>
  </a-form>
      </a-tab-pane>
      
    </a-tabs>
     
    </a-modal>
    </div>
    <div class="search-container row">
      <h2 class="row-title">剧本分享库</h2>
      <div class="row-description">搜索所想，精彩即现。</div>
      <a-input-search
        size="large"
        class="search-input"
        placeholder="搜索"
        v-model="searchName"
        @search="onSearch"
      />

      <a-list :grid="{ gutter: 16, column: 4 }" :data-source="appList">
        <a-list-item slot="renderItem" slot-scope="item, index">
          <a-card class="app-card" hoverable @click="onCardClick(item.id)">
            <img
              slot="cover"
              alt="example"
              src="https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png"
            />
            <div class="app-fav">
              <Like
                v-if="item.isLiked"
                theme="filled"
                size="0.75rem"
                fill="#ff5975"
              />
              <Like v-else theme="outline" size="0.75rem" fill="#233041" />
              <!-- <span class="app-fav-count" aria-label="收藏数">{{ item.isLiked }}</span> -->
              <span class="app-fav-count" aria-label="收藏数">{{
                item.likes
              }}</span>
            </div>
            <h3 class="app-title">{{ item.name }}</h3>
            <p class="app-desc">
              {{ item.description }}
            </p>
            <div class="app-tag-group">
              <a-tag color="blue"> {{ item.businessId }} </a-tag>
              <a-tag color="purple"> {{ item.creatorName }} </a-tag>
            </div>
          </a-card>
        </a-list-item>
      </a-list>
      <div class="search-footer" @click="onMoreClick">查看更多剧本</div>
    </div>
    <!-- 接收发布的剧本数据 -->
    <div style="width:80%; margin: 0 auto; display: flex">
      <a-table
      
        class="table"
        size="small"
        bordered
      
        :columns="columns"
        :dataSource="dataList"
        :scroll="{ y: 1200 }"
        :rowSelection="{
          selectedRowKeys: selectedRowKeys,
          onChange: onSelectChange,
        }"
      >
        <template slot="myTitle" slot-scope="title, record">
          <a href="#">{{ title }}</a>
        </template>
        <span slot="action" slot-scope="text, record">
          <a href="javascript:void(0);" @click="openDetailModal(record)"
            >详情</a
          >
          <a-divider type="vertical" />
          <a-dropdown>
            <a-menu slot="overlay">
              <a-menu-item>
                <a href="#" @click="openChangeModal(record)">修改</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm
                  title="是否删除这条数据？"
                  @confirm="remove(record)"
                  okText="是"
                  cancelText="否"
                  placement="topRight"
                >
                  <a class="txt-danger" href="#">删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
            <a href="#;">更多<a-icon type="down" /></a>
          </a-dropdown>
        </span>
      </a-table>
    </div>

    <!-- 公告和动态的修改开始 -->
    <div class="test" style="width: 80%; margin: 0 auto; display: flex">
      <div class="notice" style="width: 50%">
        <a-menu
          mode="horizontal"
          :default-selected-keys="['1']"
          :style="{ linHeight: '64px', background: '#fcfcfc' }"
        >
          <a-menu-item key="1">公告</a-menu-item>

          <a-menu-item key="2"
            ><a @click="$router.push('gongGaoMore')">更多>></a></a-menu-item
          >
        </a-menu>
        <a-list
          class="test"
          :grid="{ gutter: 16, column: 2 }"
          :data-source="gonggaodata"
        >
          <a-list-item
            slot="renderItem"
            slot-scope="item, index"
            @click="$router.push('noticeDetail')"
          >
            <a-card>
              <div slot="title">
                <div :style-="{ display: 'inline' }">
                  <a class="dated" @click="$router.push('noticeDetail')">
                    <p class="date">02</p>
                    <p class="month">四月</p>
                  </a>
                </div>
                <div :style-="{ display: 'inline', 'font-weight': 'bold' }">
                  <strong>{{ item.title }}</strong>
                </div>
              </div>
              <!--  <div class="grid-content bg-blue-light">
                <a class="dated" @click="$router.push('noticeDetail')">
                  <p class="date">02</p>
                  <p class="month">四月</p>
                </a>
              </div> -->
              <div>
                <p class="hidden">
                  {{ item.content }}
                </p>
              </div>
            </a-card>
          </a-list-item>
        </a-list>
      </div>
      <div
        class="gonggao-right"
        :style="{
          display: 'flex',
          'flex-wrap': 'wrap',
          'align-content': 'flex-start',
          color: '#123fff',
          width: '50%',
          margin: '0px 0px 20px 20px',
        }"
      >
        <div
          class="kuangkaung"
         
          @click="$router.push('checkWord')"
          
        ><!-- v-if="this.userRoles.includes('帮语审核员')" -->
          <!-- <img :src="cihui" /> -->
          <img :src="cjcheck" />
          <span>词汇库句型库审核入口</span>
        </div>
        <div
          class="kuangkaung"
         
          @click="$router.push('workerWordPage')"
        ><!-- v-if="this.userRoles.includes('帮语工者')" -->
          <!-- <img :src="cihui" /> -->
          <img :src="cjworker" />
          <span>词汇库句型库管理入口</span>
        </div>
        <div class="kuangkaung">
          <img :src="dongtai" />
          <span>业务场景</span>
        </div>
        <div class="kuangkaung">
          <img :src="businessScence" />
          <span>动态</span>
        </div>
        <div
          class="backimg"
          :style="{
            width: '600px',
            height: '310px',
            'background-color': '#f8f8e8',
            'background-size': 'cover',
            'background-position': 'center',
            'background-image': `url(${backgroud})`,
          }"
        >
          <!-- 背景图片 -->
        </div>
      </div>
    </div>
    <!--     <a-list :grid="{ gutter: 16, column: 4 }" :data-source="dongtaiData">
      <a-list-item slot="renderItem" slot-scope="item, index">
        <a-card :title="item.title">
          <div class="grid-content bg-blue-light">
            <a class="dated" @click="$router.push('dongtaiDetail')">
              <p class="date">02</p>
              <p class="month">四月</p>
            </a>
          </div>
        </a-card>
      </a-list-item>
    </a-list> -->
    <div
      :style="{
        background: '#123456',
        height: '100px',
        'text-align': 'center',
        color: 'white',
      }"
    >
      <span>020-62793083 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span
      ><span>xiaoxiao@scut.mail.edu.cn</span>
      <p>广州市华南理工大学B8实验室</p>
    </div>
  </div>
</template>
<script>
import { Like } from "@icon-park/vue";
import { getUserName } from "../../common/env";
import {getClientType} from "../../common/env";
import pinfo from "assets/1.gif";
import dongtai from "assets/dongtai.png";
import businessScence from "assets/businessScence.png";
import yuliao from "assets/yuliao.png";
import cihui from "assets/cihui.png";

import cjcheck from "assets/cjcheck.png";
import cjworker from "assets/cjworker.png";
import cjreader from "assets/cjreader.png";
import backgroud from "assets/backgroud.jpg";
import ApplicationService from "@/service/application_service";
import ReleaseService from "@/service/release_service";
import BandService from "@/service/band_service";
const applicationService = new ApplicationService();
const releaseService = new ReleaseService();
const bandService = new BandService();
/*wz修改 */

export default {
  components: { Like },
  data() {
    return {
      confirmLoginLoading:false,
      loginvisible:false,
      info:{
            userAccount: '',
            password: ''
                },
          accErrText: '',
          pwdErrText: '',
          logining: false,
    //navigationuserIsLogin:true,//首页导航栏=如果用户已登录，则展示一个下拉退出按钮；如果用户未登录，则展示登录字样
      userName: getUserName(),
      userRoles: [],
      draftRelease: {
        draftReleaseId: "",
        creatorId: "",
        name: "",
        createName: "",
        version: "",
        type: "",
      },
      columns: [
        {
          title: "序号",
          width: 80,
          dataIndex: "id",
          align: "center",
          fixed: "left",
        },

        {
          title: "标题",
          dataIndex: "name",
          align: "center",
          width:120,
          ellipsis:true,
          scopedSlots: {
            customRender: "myTitle",
          },
        },
        {
          title: "创建者",
          dataIndex: "creatorName",
          align: "center",
          /* scopedSlots: {
          customRender: "",
        } */
        },

        {
          title: "类型",
          dataIndex: "type",
          align: "center",
          customRender: (text, record, index) => {
            if (text === "1") return "律体";
            else if (text === "2") return "自由体";
            else return "混合体";
          },
        },
        {
          title: "版本号",
          dataIndex: "version",
          align: "center",
          /* scopedSlots: {
          customRender: "myTitle",
        } */
        },
        {
          title: "操作",
          width: 200,
          dataIndex: "action",
          align: "center",
          scopedSlots: {
            customRender: "action",
          },
          fixed: "right",
        },
      ],
      dataList: [],
      selectedRowKeys: [],

      bid: "",
      pinfo,
      businessScence,
      yuliao,
      dongtai,
      cihui,
      cjcheck,
      cjworker,
      cjreader,
      backgroud,
      searchName: "",
      appList: [
       
      ],
      gonggaodata: [
        {
          title: "关于举办Xbot夏令营的通知",
          content:
            " XbotPark将3周的创业体验计划和6周的创业实战计划并行,参加其中任意一类活动的同学,都将直观地体验产品设计的过程，学会挖掘用户潜在需求",
        },
        {
          title: "关于举行宋晓龙学术报告的通知",
          content: "地点是在B8报告厅,时间是下午2点,请各位准时参加",
        },
        {
          title: "关于元旦放假的通知",
          content: "元旦放假三天，不调休",
        },
        {
          title: "关于工厂客服",
          content: "工厂客服上线啦，大家可以测试一下下。",
        },
      ],
      dongtaiData: [
        {
          title: "小李创建了一个新剧本",
        },
        {
          title: "刘同学调用了喇叭工具",
        },
        {
          title: "小王同学上传了一个文件到帮区资料柜",
        },
        {
          title: "黄同学添加新业务应用实例到杰潮物流业务区",
        },
      ],
    };
  },
  created() {
    this.refreshData();
  },
  methods: {
    refreshData() {
      applicationService.getApplicationCase(
        {
          applicationCase: { isPrivate: 0 },
          pageInfo: { pageSize: 20, currentPage: 1 },
        },
        {
          onSuccess: (model, fModel, json) => {
            console.log(json);
            this.appList = json.data.rows
              .map((item) => {
                item.isLiked = false;
                // console.log(item) item是blp_application_case数据表中的记录
                return item;
              })
              .sort((a, b) => {
                //根据业务应用案例创建的先后顺序进行展示
                const date1 = new Date(a.updateTime).getTime();
                const date2 = new Date(b.updateTime).getTime();
                return date2 - date1;
              });
          },
          onFail: (msg, json) => {
            console.log(msg, json);
          },
        }
      );
      releaseService.getDraftRelease(
        {
          draftRelease: {
            id: this.draftRelease.draftId,
          },
          pageInfo: {
            pageSize: 20,
            currentPage: 1,
          },
        },
        {
          onSuccess: (model, fModel, json) => {
            console.log(json.data.rows);
            console.log(json.data.rows.length);
            json.data.rows.forEach((r, i) => {
              r.key = i;
            });
            this.dataList = json.data.rows;
          },
          onFail: (msg, json) => {
            console.log(msg, json);
          },
        }
      );
      bandService.getUserRoles(
        {
          bandId: g_bandId,
          userId: g_userId,
        },
        {
          onSuccess: (model, fModel, json) => {
            console.log(json);
            const newArr = json.data.userRoles.rows.map(
              (item) => item.roleName
            ); //对数组中的每一个元素处理，只截取它的某个属性
            if (newArr != null) {
              this.userRoles = newArr;
              console.log(this.userRoles);
            } else {
              console.log("帮区里该用户角色是空的");
            }
          },
          onFail: (msg, json) => {
            console.log(msg, json);
          },
        }
      );
    },
    getUserRolesInBand() {
      bandService.getUserRoles(
        {
          bandId: g_bandId,
          userId: g_userId,
        },
        {
          onSuccess: (model, fModel, json) => {
            console.log(json);
            const newArr = json.data.userRoles.rows.map(
              (item) => item.roleName
            );
            if (newArr != null) {
              this.userRoles = newArr;
              console.log(this.userRoles);
            } else {
              console.log("帮区里该用户角色是空的");
            }
          },
          onFail: (msg, json) => {
            console.log(msg, json);
          },
        }
      );
    },
    logout() {
     // this.navigationuserIsLogin=false;
      const date = new Date();
      date.setTime(date.getTime() - 10000);
      const keys = document.cookie.match(/[^ =;]+(?=\=)/g);
      console.log("需要删除的cookie名字:" + keys);
      if (keys) {
        for (var i = keys.length; i--; )
          document.cookie =
            keys[i] + "=0; expire=" + date.toGMTString() + "; path=/";
            console.log("在首页执行了退出方法")
      }

      //location.href = "https://www.wetoband.com/web/visitor/runTool?toolID=4389333&gid=7600030145&bandID=7600030145";
      this.$router.push('logout');
      //window.location.href = window.location.origin+"/tre/runToolWithToolShopToolID?toolID="+window.g_thisToolId+"&bandID="+window.g_bandId+"&gid="+window.g_bandId;
    //这种方法不行，会跳到吾托帮登录首页
    },

    onSearch: function (value) {
      applicationService.getApplicationCase(
        {
          applicationCase: { isPrivate: 0, name: value },
          pageInfo: { pageSize: 20, currentPage: 1 },
        },
        {
          onSuccess: (model, fModel, json) => {
            this.appList = json.data.rows.map((item) => {
              item.isLiked = false;
              return item;
            });
          },
          onFail: (msg, json) => {
            console.log(msg, json);
          },
        }
      );
    },
    onCardClick: function (id) {
      this.$router.push("/appcase/" + id);
    },
    onMoreClick: function () {
      this.$router.push({ name: "AppCaseList" });
    },
    openDetailModal: function (record) {
      let previewMode = "";
      if (record.type == "1") previewMode = "previewtool";
      else if (record.type == "2") previewMode = "previewlink";
      const param = {
        draftChapterId: record.draftChapterId,
        previewMode: previewMode,
      };
      let encodeParam = encodeURI(JSON.stringify(param));
      const url = `https://www.wetoband.com/tre//runSystemTool?gid=${g_bandId}&toolID=4389351&bandID=${g_bandId}&param=${encodeParam}`;
      window.open(url, "_blank");
    },
     showLoginModal(){
        this.loginvisible=true;
    },
       handleLoginOk(e) {
      this.ModalText = 'The modal will be closed after two seconds';
      this.confirmLoginLoading = true;
      setTimeout(() => {
        this.loginvisible = false;
        this.confirmLoginLoading = false;
      }, 2000);
    },
    handleLoginCancel(e) {
      console.log('点击取消按钮');
      this.loginvisible = false;
    },
     handleSubmitLogin(e) {
      e.preventDefault();
      this.form.validateFields((err, values) => {
        if (!err) {
          console.log('接收到的表单数据 ', values);
          const url = "https://www.wetoband.com/web//login/userLogin";

                let _this = this;
                _this.pwdErrText = '';
                _this.accErrText = '';
                this.logining = true;
                let form = new FormData()
                form.append("userAccount", values.userAccount)
                form.append("password", values.password)

                axiosIns.post(url, form).then(function (response) {
                    _this.logining = false;

                    if (!response.data.result){
                        if(response.data.msg.indexOf("密码") > -1){
                            _this.pwdErrText = response.data.msg
                        }else{
                            _this.accErrText = response.data.msg
                        }
                        return;
                    }

                  //  _this.$emit("success", response.data);
                   window.location.href = window.location.origin+"/tre/runToolWithToolShopToolID?toolID="+window.g_thisToolId+"&bandID="+window.g_bandId+"&gid="+window.g_bandId;
                   // 重新登录，刷新页面
                    //_this.$router.push('home');
                    

                }).catch(function (error) {
                    _this.logining = false;
                });

        }
      });
    },
    register(){
                let registorUrl = "https://www.wetoband.com/tre//runSystemTool?toolID=2920378&gid=145";
                this.openUrl(registorUrl)
            },
            resetPwd(){
                let registorUrl = "https://www.wetoband.com/web//login/gotoRetrievePassword";
                this.openUrl(registorUrl)
            },
            openUrl (url){
                if (getClientType() === '304' || getClientType() === '303') {
                    window.open(url, "_top");
                } else {
                    window.open(url);
                }
            }
  },
};
</script>
<style lang="scss" scoped>
#components-form-demo-normal-login .login-form {
  max-width: 300px;
}
#components-form-demo-normal-login .login-form-forgot {
  float: right;
}
#components-form-demo-normal-login .login-form-button {
  width: 100%;
}
.table{
  width:100%;
}
.ant-dropdown-link {
  color: #1c0202e1;
  font-size: 16px;
}
.ant-carousel .slick-dots li button {
  background: #000;
}
.ant-carousel .slick-dots li.slick-active button {
  width: 24px;
  background: #000;
  opacity: 1;
}
.navigation {
  display: flex;
  background: #123456;
  height: 80px;
  .imgUrl {
    //background: url(../../assets/navigationImg.png);
    background: url(../../assets/navImg.png);
    background-size: 100% 100%;
    /* 图片宽度 */
    width: 100%;
  }
}
.info-container {
  display: flex !important;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 0 15px;
  .info-img-wrapper {
    width: 80%;
    padding: 1rem 2rem 0;
    border-radius: 10px;
    margin: 0 auto;
    img {
      max-width: 100%;
      height: auto;
    }
  }
}

/* 动态部分，样式结束 */
/* .bg-blue {
  background-color: #f5f5f5;
} */

/* .bg-blue-light {
  background-color: #f5f5f5;
} */

.grid-content {
  min-height: 36px;
  /*  margin-top: 8px; */
  /*  margin-bottom: 8px; */
  text-align: center;
  padding: 16px 0;
  color: #e17e44;
}
/* .grid-content .dated {
  display: inline-block;
  width: 76px;
  height: 76px;
  margin-right: 8px;
  cursor: pointer;
  float: left;
}  */
.date {
  width: 32px;
  height: 16px;
  font-size: 12px;
  line-height: 16px;
  background: #0e94dd;
  text-align: center;
  margin-bottom: 0px;
  color: #ffffff;

  /* text-shadow: 0 1px 2px rgb(0 0 0 / 30%); */
}
.month {
  width: 32px;
  height: 16px;
  font-size: 12px;
  line-height: 16px;
  background: #09cef5;
  text-align: center;
  margin-bottom: 0px;
  color: #ffffff;

  /* text-shadow: 0 1px 2px rgb(0 0 0 / 30%); */
}
.grid-content .list_item .titles {
  font-size: 16px;
  color: #333333;
  line-height: 24px;
  margin-bottom: 4.7px;
}
.grid-content .list_item p {
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}
.grid-content .list_item .timed {
  font-size: 12px;
  color: #999999;
  line-height: 20px;
  margin-bottom: 0px;
}
.grid-content .list_item .location {
  font-size: 12px;
  color: #999999;
  line-height: 20px;
  margin-bottom: 0px;
  margin-top: 3px;
}
.gonggao-right {
  display: flex;
  flex-wrap: wrap;
  align-content: flex-start;
  color: #123fff;
  width: 50%;
  margin: 0px 0px 20px 20px;
}

.kuangkaung {
  width: 100px;
  height: 100px;
  background: #fff;
  margin: 0px 20px 5px 0px;
  text-align: center;
  border-radius: 6px;
  display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  img {
        width: 50px;
    height: 50px;
    margin-bottom: 4px;
  }
}
/*鼠标悬停，加上边框*/
.kuangkaung:hover {
  filter: brightness(0.8);
    box-shadow: 0px 3px 8px -3px grey;
}
.hidden {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
}
.app-desc {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 1;
}
.app-title {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 1;
}
</style>
