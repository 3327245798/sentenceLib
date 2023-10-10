import "@babel/polyfill";
import "common/register";

import FastClick from "fastclick";
import Vue from "vue";
import VueRouter from "vue-router";
import { mapMutations } from "vuex";
import VueCropper from 'vue-cropper';
Vue.use(VueCropper)

import hl from 'highlight.js' // 导入代码高亮文件
import 'highlight.js/styles/a11y-dark.css' // 导入代码高亮样式
// 自定义一个代码高亮指令
Vue.directive('highlight', function (el) {
  const blocks = el.querySelectorAll('pre code')
  blocks.forEach((block) => {
    //hl.highlightBlock(block)
    hl.highlightAll(block)
  })
})


import { getRunToolParam, getClientType, getConfigs, getBandId } from "common/env";

import { Config } from "common/constants";
import routes from "router/router";
import store from "./store";

import BandService from "@/service/band_service";
const bandService = new BandService();

Vue.config.devtools = true;//解决控制台报这个提示:You are running Vue in development mode.

// 解决Navigation cancelled from "/" to "/home" with a new navigation.
const originalPush = VueRouter.prototype.push;
const originalReplace = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch((err) => err);
};
VueRouter.prototype.replace = function replace(location) {
  return originalReplace.call(this, location).catch((err) => err);
};

// 添加FastClick处理
if ("addEventListener" in document) {
  document.addEventListener(
    "DOMContentLoaded",
    function() {
      FastClick.attach(document.body);
    },
    false
  );
}
// 注册SPA的Vue路由
Vue.use(VueRouter);

/* 根据平台选择类型 */

/* 创建路由 */
const router = new VueRouter({
  routes,
  mode: Config.ROUTER_MODE,
  strict: process.env.NODE_ENV !== "production",
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition;
    } else {
      if (from.meta.keepAlive) {
        from.meta.savedPosition = document.body.scrollTop;
      }
      return { x: 0, y: to.meta.savedPosition || 0 };
    }
  },
});

router.beforeEach((to, from, next) => {
  const runToolParam = getRunToolParam() || "";
  if (to.path === "/previewlink" || to.path === "/previewtool") return next();
  if (/^\{.*\}$/.test(runToolParam)) {
    const runToolParamJson = JSON.parse(runToolParam);

    if (runToolParamJson.draftChapterId && runToolParamJson.previewMode) {
      // router.replace({
      //   path: runToolParamJson.previewMode,
      //   query: {
      //     draftChapterId: runToolParamJson.draftChapterId,
      //   },
      // }); // 跳转到该路由
      next(`/${runToolParamJson.previewMode}`);
      return;
    } else {
      next();//调到404页面
    }
  }
  next();
});

/* 初始化注入模块 */
/* 创建根组件 */
new Vue({
  router,
  store,
  methods: {
    ...mapMutations(["BIND_CURRENT", "BIND_DEVICE"]),
    setAccessToken() {
      const regExp = /(\b|;)access_token\=[0-9a-z]+(;|$)/g;
      const accessTokenCookieStr = document.cookie.match(regExp);
      if (!accessTokenCookieStr) {
        return window.g_accessToken;
      }
      const accessTokenCookie = accessTokenCookieStr[0];
      const accessToken = accessTokenCookie.substring(accessTokenCookie.indexOf("=") + 1, accessTokenCookie.length - 1);
      return (window.g_accessToken = accessToken);
    },
  },
  created() {
    this.BIND_CURRENT({ BAND_OBJId: window.g_bandId, userID: window.g_userId, userName: window.g_userName });
    window.g_treUrl = null;
    window.g_webUrl = null;
    window.g_wxUrl = null;
    //window.g_userId = null;
    // window.g_userName = null;
    //window.g_userAccount = null;
    this.setAccessToken();

    /* 解析工具运行参数 */
    /* 路由控制 */
    // this.$router.go(-1);//后退
    /* 获取基础信息 */
    // getConfigs({
    //   onSuccess: (config) => {
    //     config.asComponent = window.g_asComponent;
    //     config.accessToken = window.g_accessToken;

    //     window.g_config = config;
    //     window.g_treUrl = config.treUrl;
    //     window.g_webUrl = config.webUrl;
    //     window.g_wxUrl = config.wxUrl;
    //     window.g_userId = config.userID;
    //     window.g_userName = config.userName;
    //     window.g_userAccount = config.userAccount;

    //     this.BIND_CURRENT(config);
    //     this.BIND_DEVICE({
    //       isOnline: true,
    //     });
    //   },
    //   onFail: (errorMsg) => {
    //     this.BIND_DEVICE({
    //       isTimeout: errorMsg.indexOf("会话超时") != -1,
    //     });
    //   },
    //   onError: () => {
    //     this.BIND_DEVICE({
    //       isOnline: false,
    //     });
    //   },
    // });
  },
  async mounted() {
    const runToolParam = getRunToolParam() || "";

    if (/^\{.*\}$/.test(runToolParam)) {
      const runToolParamJson = JSON.parse(runToolParam);
      if (runToolParamJson.draftChapterId && runToolParamJson.previewMode) {
        console.log(runToolParam);
        router.replace({
          path: `/${runToolParamJson.previewMode}`,
          query: {
            draftChapterId: runToolParamJson.draftChapterId,
          },
        }); // 跳转到该路由
      }
    }

    setTimeout(() => {
      bandService.getToolInfo(
        {
          toolName: "帮语平台",
          //toolName: "句型工具",
          bandId: getBandId(),
        },
        {
          onSuccess: (model, fModel, json) => {
            window.tool = json.data.tool;
            console.log(window.tool);
          },
          onFail: (msg, json) => {
            console.log(msg, json);
          },
        }
      );
    }, 1000);
  },
}).$mount("#app");
