<template>
  <div class="total">
    <div class="nav">
      <sapn class="nav-content">吾托帮平台</sapn>
      <sapn class="nav-content">开发平台</sapn>
      <span class="nav-content">
        <a-icon
          type="user"
          theme="outlined"
          :style="{ fontSize: '3em', color: 'bluepink' }"
        ></a-icon>
         <a class="ant-dropdown-link" @click="showLoginModal">
      登录 
    </a>
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
      <a class="login-form-register" @click="register">
        现在注册!
      </a>
    </a-form-item>
  </a-form>
      </a-tab-pane>
      
    </a-tabs>
     
    </a-modal>
      </span>
    </div>
    <div class="header">
      <img :src="blpLogo" class="blpLogo" />
      <a-input-search
        class="search"
        placeholder="请输入内容"
        enter-button="搜索"
        size="large"
        @search="onSearch"
      ></a-input-search>
    </div>
    <div class="lunbo"></div>
    <!-- 暂时没写轮播，后续需要的话会加上 -->
    <div class="maodian" id="anchor">
      <a href="#talk" title="论坛" class="archorA">论坛</a>
      <a href="#appcase" title="案例"  class="archorA">案例</a>
      <a href="#word" title="词汇"  class="archorA">词汇</a>
      <a href="#sentence" title="句型"  class="archorA">句型</a>
  
    </div>
    <div class="content" ref="anchorContent" id="anchorContent">
      <div class="talk-nav" id="talk">
        <span class="talk-nav-one">帮语论坛</span>
        <span class="talk-nav-two more-btn" >更多>></span>
      </div>
      <div class="talk-content">
        <a-list item-layout="vertical" size="large" :data-source="listData">
          <a-list-item
            slot="renderItem"
            key="item.title"
            slot-scope="item, index"
            class="talklistitem"
            :style="talkItemStyle"
            @mouseenter="mouseenterTalk"   
            @mouseleave="mouseleaveTalk"
          >
            <a-list-item-meta>
              <!--  <a-avatar slot="avatar" :src="item.avatar" /> -->
              <a slot="title" :class="talkListTitle" :href="item.href">{{
                item.title
              }}</a>
            </a-list-item-meta>
            <div class="talk-list">
              <div class="talk-list-content">{{ item.content }}</div>
              <div style="padding: 1rem">
                <span
                  style="margin-right: 8px"
                  v-for="{ type, text } in actions"
                  :key="type"
                  @click="handleClick(type)"
                >
                  <a-icon :type="type" style="margin-right: 8px" />
                  {{ text }}
                </span>
              </div>
            </div>

            <!--  点击评论图标，如果有人已发表评论，展示列表，如果没人发表评论，则“还没有评论，发表第一个评论吧” -->
            <div>
              <!-- 这个div与template同级 -->
              <div class="myComment" v-show="showComment">
                <a-avatar slot="avatar" :src="touxiang1" alt="Han Solo" />

                <div class="myComment-content">
                  <a-textarea
                    class="myCommentText"
                    :rows="1"
                    :value="myCommentValue"
                    @change="handleMyCommentChange"
                    placeholder="写下你的评论..."
                  />
                  <a-button
                    html-type="submit"
                    :loading="submittingMyComment"
                    type="primary"
                    @click="handleSubmitMyComment"
                  >
                    发布
                  </a-button>
                </div>
              </div>
            </div>
            <div class="otherComment" v-show="showComment">
              <div class="otherCommentNav">
                <span>1条评论</span>
                <span :style="{ float: 'right', 'margin-right': '50px' }">
                  <input type="button" value="默认" />
                  <input type="button" value="最新" />
                </span>
              </div>
              <a-divider></a-divider>
              <div class="otherCommentContent" :style="{ display: 'flex' }">
                <a-avatar slot="avatar" :src="touxiang2" alt="Han Solo" />
                <div>
                  <div
                    style=" font-size:15px;font-weight:bold;text-decoration:blink;color:black:width:100%"
                  >
                    王哲
                  </div>
                  <div
                    style="
                      font-size: 15px;
                      color: black;
                      display: flex;
                      width: 80%;
                    "
                  >
                    这里是我对这个观点的评论
                  </div>
                  <div
                    style="
                      font-size: 15px;
                      color: #808080;
                      display: flex;
                      justify-content: space-between;
                    "
                  >
                    <div class="date-location" style="margin-right: 50px">
                      2022年10月10日
                    </div>
                    <div class="reply-like">
                      <a-icon type="message" style="margin-right: 10px" />回复
                      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                      <a-icon type="like-o" style="margin-right: 10px" />点赞
                    </div>
                  </div>
                </div>
              </div>
              <div v-show="showReply">
                <a-textarea
                  :rows="1"
                  :value="replyComment"
                  @change="handleMyCommentChange"
                  placeholder="回复@王哲"
                />
                <a-button
                  html-type="submit"
                  :loading="submittingMyComment"
                  type="primary"
                  @click="handleSubmitMyComment"
                >
                  发布
                </a-button>
              </div>
            </div>
          </a-list-item>
        </a-list>
      </div>

      <div class="applicase-nav" id="appcase">
        <span class="applicase-nav-one" >案例库</span>
        <span class="applicase-nav-two more-btn" >更多>></span>
      </div>
      <div class="applicase-content">
        <a-table
         :pagination="pagination"
          bordered
          :columns="appcaseColumn"
          :data-source="appcaseData"
          :rowKey="
            (record, index) => {
              return index;
            }
          "
          @expand="handleExpandDraft"
        >
          <a slot="name" slot-scope="text">{{ text }}</a>
          <span slot="customTitle"><a-icon type="smile-o" /> 案例应用名称</span>
          <span slot="tags" slot-scope="tags">
            <a-tag
              v-for="tag in tags"
              :key="tag"
              :color="
                tag === 'loser'
                  ? 'volcano'
                  : tag.length > 5
                  ? 'geekblue'
                  : 'green'
              "
            >
              {{ tag.toUpperCase() }}
            </a-tag>
          </span>
          <span slot="action" slot-scope="text, record">
            <a>Invite 一 {{ record.name }}</a>
            <a-divider type="vertical" />
            <a>Delete</a>
            <a-divider type="vertical" />
            <a class="ant-dropdown-link">
              More actions <a-icon type="down" />
            </a>
          </span>
          <a-table
            slot="expandedRowRender"
            slot-scope="text"
            :columns="innerDraftColumns"
            :data-source="record.innerData"
            :pagination="false"
            :rowKey="
              (record, index) => {
                return index;
              }
            "
          >
            <span slot="status" slot-scope="text">
              <a-badge status="success" />Finished
            </span>
            <span slot="operation" slot-scope="text" class="table-operation">
              <a>Pause</a>
              <a>Stop</a>
              <a-dropdown>
                <a-menu slot="overlay">
                  <a-menu-item> Action 1 </a-menu-item>
                  <a-menu-item> Action 2 </a-menu-item>
                </a-menu>
                <a> More <a-icon type="down" /> </a>
              </a-dropdown>
            </span>
          </a-table>
        </a-table>
      </div>
      <div class="word-nav" id="word">
        <span class="word-nav-one">词汇库</span>
        <span class="word-nav-two more-btn"  >更多>></span>
      </div>
      <div class="word-content">
        <a-table 
        :pagination="pagination"
        :columns="wordColumn" 
        :data-source="wordData"
        :rowKey="
              (record, index) => {
                return index;
              }
            "
            bordered
        >
        
        </a-table>
      </div>
      <div class="sentence-nav" id="sentence">
        <span class="sentence-nav-one">句型库</span>
        <span class="sentence-nav-two more-btn" >更多>></span>
      </div>
      <div class="sentence-content">
        <a-table 
        :pagination="pagination"
        :columns="sentenceColumn" 
        :data-source="sentenceData"
         :rowKey="
              (record, index) => {
                return index;
              }
            "
            bordered
        >
          
        </a-table>
      </div>
    </div>
    <div class="footer">
      <span>020-62793083 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
      <span>xiaoxiao@scut.mail.edu.cn</span>
      <p>广州市华南理工大学B8实验室</p>
    </div>
  </div>
</template>
<script>
import { Like } from "@icon-park/vue";
import touxiang1 from "assets/touxiang1.jpg";
import touxiang2 from "assets/touxiang2.png";
import blpLogo from "assets/blpLogo.png";
import moment from "moment";
import ApplicationService from "@/service/application_service";
import ReleaseService from "@/service/release_service";
import BandService from "@/service/band_service";
import WordService from"@/service/word_service";
import SentenceService from "@/service/sentence_service";
const sentenceService = new SentenceService();
const wordService=new WordService();
const applicationService = new ApplicationService();
const releaseService = new ReleaseService();
const bandService = new BandService();
import { getUserName } from "@/common/env";


const columns = [
  {
    dataIndex: "name",
    key: "name",
    slots: { title: "customTitle" },
    scopedSlots: { customRender: "name" },
  },
  {
    title: "Age",
    dataIndex: "age",
    key: "age",
  },
  {
    title: "Address",
    dataIndex: "address",
    key: "address",
  },
  {
    title: "Tags",
    key: "tags",
    dataIndex: "tags",
    scopedSlots: { customRender: "tags" },
  },
  {
    title: "Action",
    key: "action",
    scopedSlots: { customRender: "action" },
  },
];

const data = [
  {
    key: "1",
    name: "John Brown",
    age: 32,
    address: "New York No. 1 Lake Park",
    tags: ["nice", "developer"],
  },
  {
    key: "2",
    name: "Jim Green",
    age: 42,
    address: "London No. 1 Lake Park",
    tags: ["loser"],
  },
  {
    key: "3",
    name: "Joe Black",
    age: 32,
    address: "Sidney No. 1 Lake Park",
    tags: ["cool", "teacher"],
  },
];
const listData = [
  {
    href: "https://www.antdv.com/",
    title: `《最强大脑》的水有多深`,
    content:
      "认识一个学姐,在康奈尔大学,比较喜欢发微博,然后最强大脑就通过微博联系她,邀请参加,根本不问她的专业和特长,只要她是世界名校就邀请。还有B站上的洪千辰,在帝国理工读研，就被邀请参加了。最强大脑邀请的时候就是只看学校，国外名校海归优先，次之则是国内几所顶尖大学。然后通过一些糊弄观众的小游戏，让观众感觉，哇，不愧是名校学生，高智商！然而那就是设计好的套路。",
  },
  {
    href: "https://www.antdv.com/",
    title: `疫情三年为什么觉得今年格外难`,
    content: `因为过了三年，有些事情就可以看清楚了。能让你对未来充满希望，乐观自信的，不是核酸检测能力有多强，不是看能把人限制多少天，而是要有心理的强大。`,
  },
];

const appcaseColumn = [
  {
    title: "案例应用名称",
    dataIndex: "name",
    key: "name",
    slots: { title: "customTitle" },
    
  },
  { title: "描述", dataIndex: "description", key: "description" },
  { title: "业务名称", dataIndex: "businessName", key: "businessName" },
  { title: "编辑者", dataIndex: "creatorName", key: "creatorName" },
  { title: "创建时间", dataIndex: "updateTime", key: "updateTime" },
];
const innerDraftColumns = [
  { title: "剧本名", dataIndex: "name", key: "name" },
  { title: "描述", dataIndex: "description", key: "description" },
  { title: "创建者", dataIndex: "creatorName", key: "creatorName" },
  //{ title: 'Upgrade Status', dataIndex: 'upgradeNum', key: 'upgradeNum' },
  {
    title: "Action",
    dataIndex: "operation",
    key: "operation",
    scopedSlots: { customRender: "operation" },
  },
];
const wordColumn = [
  { title: "词汇", dataIndex: "name", key: "name",width:80 },
  { title: "描述", dataIndex: "description", key: "description",width:160,ellipsis:true },
  { title: "编辑者", dataIndex: "creatorName", key: "creatorName" ,width:80},
  { title: "种类", dataIndex: "toolName", key: "toolName",width:80 },
  { title: "部件", dataIndex: "unitName", key: "unitName",width:120 },
 { title: "更新时间", dataIndex: "updateTime", key: "updateTime" ,width:160},
 /*  {
    title: "状态",
    dataIndex: "status",
    key: "status",
    scopedSlots: { customRender: "statu" },
    width:160
  },
 
  {
    title: "操作",
    dataIndex: "",
    key: "x",
    scopedSlots: { customRender: "action" },
    width:160
  }, */
];
const sentenceColumn = [
  {
    title: "句型名字",
    dataIndex: "name",
    key: "name",
    width: 100,
    scopedSlots: { customRender: "name" },
    algin:'center',
  },
  { title: "描述", dataIndex: "description", key: "description" },
  { title: "例子", dataIndex: "example", key: "example" },
  { title: "序列化表示", dataIndex: "serialization", key: "serialization" },
  { title: "编辑者", dataIndex: "creatorName", key: "creatorName", width: 100,algin:'center', },
  { title: "创建时间",
   dataIndex: "updateTime", 
   key: "updateTime" ,
   //slots:{title:"customUpdateTimeTitle"},
   algin:'center',},
 /*  {
    title: "类型",
    dataIndex: "type",
    key: "type",
    scopedSlots: { customRender: "types" },
    algin:'center',
  },
  {
    title: "状态",
    dataIndex: "status",
    key: "status",
    scopedSlots: { customRender: "statu" },
    algin:'center',
  },
  {
    title: "操作",
    dataIndex: "",
    key: "x",
    scopedSlots: { customRender: "action" },
    width: 120,
    algin:'center',
  }, */
];

export default {
  data() {
    return {
      listData,
      pagination: {
        onChange: (page) => {
          console.log(page);
        },
        pageSize: 3,
      },
      actions: [
        { type: "star-o", text: "156" },
        { type: "like-o", text: "156" },
        { type: "message", text: "1条评论" },
      ],
      data,
      columns,
      touxiang1,
      touxiang2,
      blpLogo,
      talkItemStyle:'',//鼠标移入或移除论坛项时的样式变量名
     moreTalkStyle:'',
     moreAppcaseStyle:'',//鼠标移入或移除"更多"字体时的样式变量名，控制“更多”所在盒子的的样式
     moreWordStyle:'',
     moreSentenceStyle:'',
     appcaseData: [],
      appcaseColumn,
      wordColumn,
      wordData:[],
      sentenceColumn,
      sentenceData:[{
        name:'计算型',
         description:"计算 (公式名称) ，其中 (变量) = (数值)，结果为 (结果名称) 。",
          example:"计算 (add) ，其中 (a) = (1), (b) = (2)，结果为 (3)",
           serialization:"计算# ，其中$ =%，结果为*。" ,
           creatorName:'王哲' ,
           updateTime:"2022-10-13 10:23:24",

      }],
      innerDraftColumns,
      innerDraftData: [],
      
      userName: getUserName(),
      userRoles:[],
      confirmLoginLoading:false,
      loginvisible:false,

      info:{
            userAccount: '',
            password: ''
                },
          accErrText: '',
          pwdErrText: '',
          logining: false,

      appcaseId: 1,
      showComment: false,
      showReply: false,
      myCommentValue: "",
      submittingMyComment: false,
      likes: 0,
      dislikes: 0,
      likeAction: null,
      moment,
    };
  },
  created() {
    this.getApplicationCase();
    this.refreshAllWord();
    this.refreshAllSentence();

  },
  methods: {
    getApplicationCase() {
      applicationService.getApplicationCase(
        {
          applicationCase: { isPrivate: 0 },
          pageInfo: { pageSize: 20, currentPage: 1 },
        },
        {
          onSuccess: (model, fModel, json) => {
            console.log(json);
            this.appcaseData = json.data.rows;
            /* .map((item) => {
                item.isLiked = false;
                // console.log(item) item是blp_application_case数据表中的记录
                return item;
              })
              .sort((a, b) => {
                //根据业务应用案例创建的先后顺序进行展示
                const date1 = new Date(a.updateTime).getTime();
                const date2 = new Date(b.updateTime).getTime();
                return date2 - date1;
              }); */
          },
          onFail: (msg, json) => {
            console.log(msg, json);
          },
        }
      );
    },
    handleExpandDraft(expanded, record) {
      applicationService.getDraftListByAppcaseId(
        { appcaseId: record.id },
        {
          onSuccess: (model, fModel, json) => {
            console.log(json);
            //this.appcaseData = json.data.rows
            this.innerDraftData = json.data.rows;
            record.innerData = json.data.rows;

            console.log(record);
          },
          onFail: (msg, json) => {
            console.log(msg, json);
          },
        }
      );
    },
    handleClick(type) {
      console.log(type);
      if (type == "message" && this.actions[2].text != "收起评论") {
        this.showComment = true;
        this.actions[2].text = "收起评论";
      } else {
        this.showComment = false;
        this.actions[2].text = "1条评论";
      }
    },
    handleMyCommentChange(e) {
      this.myCommentValue = e.target.value;
    },
    handleSubmitMyComment() {
      if (!this.myCommentValue) {
        return;
      }

      this.submittingMyComment = true;
      //然后在处理一些异步请求，向后台提交数据，例子是setTimeOut
    },
    like() {
      this.likes = 1;
      this.dislikes = 0;
      this.likeAction = "liked";
    },
    dislike() {
      this.likes = 0;
      this.dislikes = 1;
      this.likeAction = "disliked";
    },
       refreshAllWord(){
      wordService.getAllPublicWord({
       onSuccess: (model, fModel, json) => {
          console.log(json);
          console.log("这是帮语网站中所有的词汇");
          this.wordData = json.data.rows.word
           .sort((a, b) => {
                //根据词汇创建的先后顺序进行展示
                const date1 = new Date(a.updateTime).getTime();
                const date2 = new Date(b.updateTime).getTime();
                return date2 - date1;
              });
        },
        onFail: (msg, json) => {
          console.log(msg, json);
        },
    })
    },
    //
     refreshAllSentence() {
      sentenceService.getAllSentence({
        onSuccess: (model, fModel, json) => {

          console.log("调用了方法获取全部句型");
          console.log(json);
          this.sentenceData = json.data.rows.sentence
          .sort((a, b) => {
                //根据词汇创建的先后顺序进行展示
                const date1 = new Date(a.updateTime).getTime();
                const date2 = new Date(b.updateTime).getTime();
                return date2 - date1;
              });
        },
        onFail: (msg, json) => {
          console.log(msg, json);
        },
      });
    }, 
    /*处理登录开始 */
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
                 window.location.href = window.location.origin+"/tre/runToolWithToolShopToolID?toolID="+window.g_thisToolId+"&bandID="+window.g_bandId+"&gid="+window.g_bandId;
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
            },
    /*处理登陆结束 */

    mouseenterTalk(){
//this.talkItemStyle = 'background-color: rgb(235,235,235);border-radius:10px';
this.talkItemStyle = 'background-color: #fff;border-radius:10px';

    },
    mouseleaveTalk(){
this.talkItemStyle = '';},

  

  },
};
</script>
<style lang="scss" scoped>
.ant-dropdown-link{
color:rgb(21, 20, 20);
}

#components-form-demo-normal-login .login-form {
  max-width: 300px;
}
#components-form-demo-normal-login .login-form-forgot {
  color:rgb(247, 128, 128);
  float: right;
}
#components-form-demo-normal-login .login-form-register {
  color:rgb(247, 128, 128);
  
}
#components-form-demo-normal-login .login-form-button {
  width: 100%;
}
/deep/.ant-tabs-ink-bar {
   
   background:rgb(237, 170, 170);
}
/deep/.ant-tabs-nav .ant-tabs-tab-active {
    color: rgb(237, 170, 170);
    text-shadow: 0 0 0.25px currentColor;
}
.total {
  margin: 0 auto;
  min-width: 915px;
   //background: rgb(225, 225, 225);
   
   background:rgb(234, 225, 225);
  .nav {
    background: rgb(237, 170, 170);
    
    width: 100%;
    height: 60px;
    font-size: 20px;
    margin-bottom: 10px;
    .nav-content {
      margin-left: 70px;
      margin-right: 20px;
    }
  }
  .header {
    //border: solid rgb(248, 218, 251) 5px;
    width: 100%;
    min-width: 70%;
    padding-left: 50px;
    font-size: 20px;
    margin-bottom: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
    .blpLogo {
      width: 300px;
      height: 60px;
    }
    .search {
      width: 60%;
      margin-left: 100px;
      margin-right: 50px;
    }
  }
  .maodian {
   // border: solid rgb(248, 218, 251) 5px;
    width: 100%;
    display: flex;
    justify-content:space-between;
    margin-top: 20px;
     margin-bottom: 10px;
    padding-left:60px;
    padding-right: 60px;
    .archorA{
      display: flex;
      width: 180px;
      height:80px;
      //background: #9e05b2;
      background: rgb(237, 170, 170);
      color: #f0f8ff;
      font-size: 18px;
      align-items: center;
      justify-content: center;
      border-radius: 10px;
      

    }
.archorA:hover{
    display: flex;
      width: 180px;
      height:80px;
     background: rgb(248, 215, 215);
      color: #d51990;
      font-size: 18px;
      align-items: center;
      justify-content: center;
      border-radius: 10px;
       

      }

  }
  .content {
    border: solid rgb(207, 197, 197) 2px;
    border-radius: 5px;
    width: 90%;
    margin: 0 auto;
    font-size: 20px;
    margin-bottom: 10px;
    height: auto;
    padding-left: 10px;
    padding-right: 10px;
    .talk-nav {
      
      margin-bottom: 16px;
      .talk-nav-one {
        float: left;
        color:#060506;
      }
      .talk-nav-two {
        float: right;
        
      }
    }
    .ant-list-item-meta-title > a {
      color: rgba(0, 0, 0, 0.65);
      -webkit-transition: all 0.3s;
      transition: all 0.3s;
      font-weight: 700;
      font-size: 20px;
    }
    .talk-content {
      padding: 10px 20px 20px 10px;
      .talklistitem{
       // border: solid rgb(157, 9, 9) 2px;
        margin-bottom: 5px;
      }

      .talk-list {
        display: flex;
        flex-direction: column;
        .talk-list-content {
          font-size: 16px;
          margin-left: 16px;
        }
      }
    }

    .applicase-nav {
      
      margin-bottom: 16px;
      .applicase-nav-one {
        float: left;
        color:#060506;
      }
      .applicase-nav-two {
        float: right;
      }
    }
    .word-nav {
      
      margin-bottom: 16px;
      .word-nav-one {
        float: left;
        color:#060506;
      }
      .word-nav-two {
        float: right;
      }
    }
    .sentence-nav {
   
      margin-bottom: 16px;
      .sentence-nav-one {
        float: left;
        color:#060506;
      }
      .sentence-nav-two {
        float: right;
      }
    }
  }

  .footer {
    width: 100%;
    font-size: 15px;
    padding-top: 10px;
    // margin-bottom: 10px;
    // border: solid red 2px;
    //background: #123456;
   // background: #b89fefca;
    background:rgb(237, 170, 170);
    height: 100px;
    text-align: center;
    color:black;
  }
  .myComment {
    display: flex;
    padding-left: 10px;

    .myComment-content {
      display: flex;
      width: 100%;
      font-size: 12px;
      margin-left: 10px;
      margin-right: 10px;
      .myCommentText {
        width: 80%;
      }
    }
  }
  .otherComment {
    padding: 10px 5px;
    border: 1px solid #e0e4e8;
    border-radius: 10px;
  }
}
.more-btn{
  transition: all 0.3s;
}
.more-btn:hover{
  color:rgba(255, 0, 0, 0.633);
  margin-right: 5px;
}

/deep/.ant-table-tbody > tr:hover:not(.ant-table-expanded-row):not(.ant-table-row-selected) > td {
    background: #fff !important;
}

/deep/.ant-input:hover {
    border-color: red;
    border-right-width: 1px !important;
}
</style>
