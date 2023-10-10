<template>
  <div
    class="page-wrapper loading"
    :class="{ scroll: isScrolled }"
    data-content-max
    v-resize="onResize"
    v-loading="ST_CURRENT.loading"
  >
   <!--  <div class="page-header-wrapper">
      <Header />
    </div>
    <a-popover placement="topRight">
      <template slot="content">
        <a-menu>
          <a-menu-item>常见问题FAQ</a-menu-item>
          <a-menu-item>咨询在线客服</a-menu-item>
        </a-menu>
      </template>
      <div class="cs-wrapper"><a-avatar :src="customerServiceLogo" :size="60" /></div>
    </a-popover> -->
    <div v-show="!ST_CURRENT.loading" class="page-body-wrapper">
      <transition name="router-fade" mode="out-in">
        <keep-alive>
          <router-view v-if="$route.meta.keepAlive"></router-view>
        </keep-alive>
      </transition>
      <transition name="router-fade" mode="out-in">
        <router-view v-if="!$route.meta.keepAlive"></router-view>
      </transition>
    </div>
  </div>
</template>
<script>
import "style/common";
import { mapMutations, mapState } from "vuex";
import { pageBack } from "common/env";
// import {  installMoment } from "common/shim";
import "style/reset";
import Header from "components/common/Header";
import _ from "lodash";
import customerServiceLogo from "assets/customer-service.png";

export default {
  components: { Header },
  data() {
    return { customerServiceLogo, isScrolled: false };
  },
  computed: {
    ...mapState(["ST_CURRENT", "ST_DEVICE", "ST_USER"]),
  },
  watch: {},
  methods: {
    ...mapMutations(["BIND_CURRENT", "BIND_DEVICE", "BIND_USER"]),//mapState函数返回的是一个对象,...可以将多个对象合并为一个
    back() {
      pageBack();
    },
    changeDeviceType() {
      if (!window.innerWidth) {
        return;
      }
      if (window.innerWidth < 648) {
        this.BIND_DEVICE({ isMobile: true });
        return;
      }
      this.BIND_DEVICE({ isMobile: false });
    },
    /* 配合mixin中的rem+vw布局处理使用 */
    adjustBasicSize() {
      // 获取基准值
      const html = document.querySelector("html");
      const content = JSON.parse(window.getComputedStyle(html).getPropertyValue("content"));
      if (!content) {
        return;
      }
      const config = JSON.parse(content);
      const designWidth = parseFloat(config.design);
      const maxWidth = parseFloat(config.max);
      // 超出最大值的情况下不做处理
      if (window.innerWidth > maxWidth) {
        html.style.fontSize = "";
        return;
      }
      // 计算设计稿宽度相对当前窗口宽度的比例
      // font-size按该比例缩放即可达到设计稿比例大小
      const radio = designWidth / window.innerWidth;
      // 记录初始生成的font-size, 后面都用它来计算动态值
      if (!html.$basicFontSizeVW) {
        const fontSize = window.getComputedStyle(html).getPropertyValue("font-size");
        html.$basicFontSizeVW = window.innerWidth / parseFloat(fontSize);
      }
      // 一定要vw单位才能动态变化
      html.style.fontSize = html.$basicFontSizeVW * radio + "vw";
    },
    onResize() {
      // 调整基准比例
      this.adjustBasicSize();
      // 检查当前屏幕宽度, 做适配
      this.changeDeviceType();
    },
    gotoRouteByArgs() {
      if (this.$route.path && this.$route.path === "/") {
        this.$router.replace({ path: "/home" });
      }
      // this.$router.replace({ path: "/appcase/123/456" });
      // this.$router.replace({ path: "/dashboard" });
    },
  },
  created() {
    this.BIND_CURRENT({ loading: true });
  },
  async mounted() {
    // 设置一些通用控制方法
    window.setUserTimeout = () => {
      this.BIND_DEVICE({ isTimeout: true });
    };

    // 完成初始化，跳转路由
    this.gotoRouteByArgs();
    // 延迟一小段时间用于路由跳转或者界面类型切换显示
    _.delay(() => {
      this.BIND_CURRENT({ loading: false });
    }, 500);
    window.addEventListener(
      "scroll",
      (e) => {
        if (e.target.scrollTop === 0 && this.isScrolled == true) {
          this.isScrolled = false;
        } else if (e.target.scrollTop > 0 && this.isScrolled == false) {
          this.isScrolled = true;
        }
      },
      true
    );
    // this.$router.push("/appcase/12315");
  },
};
</script>
<style lang="scss">
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
html,
body {
  width: 100%;
  background: transparent !important;
  background-color: transparent !important;
  font-size: 16px;

  // font-family:
  //     /* personal for Chinese project */ Source Han Sans SC, /* system default */ system-ui,
  //     /* macOS 10.11-10.12 */-apple-system, BlinkMacSystemFont, PingFang SC, /* Windows 6+ */ Segoe UI, Microsoft YaHei,
  //     Source Han Sans SC, Noto Sans CJK SC, WenQuanYi Micro Hei, /* fall back */ Helvetica Neue, sans-serif;
  //   /* Change the font size in all browsers. */
}
.router-fade-enter-active,
.router-fade-leave-active {
  transition: opacity 0.3s;
}
.router-fade-enter,
.router-fade-leave-active {
  opacity: 0;
}
.page-wrapper {
  width: 100%;
  max-width: 100vw;
  min-height: 100vh;
  position: relative;
  margin: 0 auto;
  background: transparent;
  .page-header-wrapper {
    width: 100%;
    max-width: 100vw;
    height: 4rem;
  }
  .page-body-wrapper {
    width: 100%;
    max-width: 100vw;
    min-height: calc(100vh - 4rem);
    position: absolute;
    background: #f8f8f8;
  }
}
.i-icon {
  display: inline-flex;
  align-items: center;
  justify-content: center;
}
.cs-wrapper {
  position: fixed;
  right: 20px;
  bottom: 20px;
  z-index: 1;
}
.app-card.ant-card {
  position: relative;
  border-radius: 0.5rem;
  .ant-card-cover {
    padding: 0.5rem;
    img {
      border-radius: 0.5rem;
    }
  }
  h3 {
    font-size: 1rem;
    font-weight: 800;
  }
  .ant-card-body {
    text-align: start;
    padding: 0.5rem;
    .app-title,
    .app-desc {
      margin-bottom: 0.5rem;
    }
    .app-fav {
      display: flex;
      align-items: center;
      cursor: pointer;
      line-height: 12px;
      padding: 4px 6px;
      border-radius: 0 0 0 0.5rem;
      background: #fff;
      position: absolute;
      top: 8px;
      right: 8px;
      .app-fav-count {
        color: #252626;
        margin-left: 2px;
      }
    }
  }
}
.search-container {
  .search-input {
    margin-bottom: calc(1rem + 16px) !important;
  }
  .search-footer {
    margin: 1rem 0 1.5rem;
    font-weight: 700;
    font-size: 16px;
    display: block;
    background: #fff;
    text-align: center;
    border-radius: 8px;
    padding: 16px 0;
    cursor: pointer;
  }
}
.row {
  height: 100%;
  padding-top: 3rem !important;
  width: 80% !important;
  margin: 0 auto;
  text-align: center;
  .row-title {
    line-height: 1.25;
    font-size: 2rem;
    margin-bottom: 0.25rem;
    padding-bottom: 0.5rem;
    font-weight: bold;
  }
  .row-description {
    line-height: 1.75;
    font-size: 1.25rem;
    color: #444444;
    margin-bottom: 0;
    padding-bottom: 0.5rem;
  }
}
.ant-menu-vertical {
  border-right: none;
}
</style>
