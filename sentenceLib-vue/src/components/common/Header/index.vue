<template>
  <a-affix :offset-top="0">
    <div class="blp-header">
      <a-row>
        <a-col :span="8">
          <router-link to="/home" class="logo-wrapper"> <img :src="logo" alt=""/></router-link>
        </a-col>
        <a-col :span="16" style="display:flex;align-items:center;flex-direction:row-reverse;">
          <a-avatar class="avatar" :size="32" icon="user" />
          <a-button class="entry_self" type="primary" @click="gotoDashboard">个人工作区</a-button>
          <a-menu class="menu-site" v-model="current" mode="horizontal" @click="onMenuClick">
            <a-menu-item key="home"> 首页 </a-menu-item>
            <a-menu-item key="appCaseList"> 业务应用库 </a-menu-item>
          </a-menu>
        </a-col>
      </a-row>
    </div>
  </a-affix>
</template>


<script>
import { Button } from "ant-design-vue";
import logo from "assets/logo.svg";
import avatar from "assets/avatar.jpg";
export default {
  components: { Button },
  data() {
    return {
      current: [],
      bid: "",
      logo,
      avatar,
    };
  },
  created() {
    if (this.$route.name === "Home") {
      this.current = ["home"];
    } else if (this.$route.name === "AppCaseList") {
      this.current = ["appCaseList"];
    } else {
      this.current = [];
    }
  },
  watch: {
    "$route.name"(next, prev) {
      if (next === "Home") {
        this.current = ["home"];
      } else if (next === "AppCaseList") {
        this.current = ["appCaseList"];
      } else {
        this.current = [];
      }
    },
  },
  methods: {
    onMenuClick({ item, key, keyPath }) {
      if (key === "appCaseList") this.$router.push({ name: "AppCaseList" });
      else if (key === "home") this.$router.push({ name: "Home" });
    },
    gotoDashboard() {
      this.$router.push({ name: "Dashboard" });
    },
  },
};
</script>
<style lang="scss">
.blp-header {
  width: 100%;
  border-bottom: solid 1px #d3d3d3;
  box-shadow: 0 3px 2px #f1f3f5;
  background: #fff;
  position: relative;
  z-index: 9;
}
.scroll .blp-header {
  box-shadow: none;
}
.logo-wrapper {
  width: 100%;
  overflow: hidden;
  float: left;
  height: 4rem;
  line-height: 4rem;
  text-decoration: none;
  white-space: nowrap;
  text-align: center;
  img {
    margin: 0 auto;
    height: 3rem;
    vertical-align: middle;
  }
}
.entry_self {
  margin-right: 2rem;
  margin-top: 2px;
}

.avatar {
  background-color: #91d5ff;
  margin-left: 1rem;
  margin-right: 2.5rem;
  margin-top: 2px;
}

.menu-site.ant-menu {
  color: #000;
}
.menu-site.ant-menu-horizontal {
  border-bottom: none;
  height: 4rem;
  float: right;
  margin-right: 2rem;
}
.menu-site > .ant-menu-submenu,
.menu-site > .ant-menu-item {
  height: 4rem;
  line-height: 4rem;
  min-width: 4.5rem;
  text-align: center;
  border-top: 2px solid transparent;
}
.menu-site.ant-menu-horizontal > .ant-menu-item-selected,
.menu-site.ant-menu-horizontal > .ant-menu-submenu:hover,
.menu-site.ant-menu-horizontal > .ant-menu-item:hover {
  border-top: 2px solid #1890ff;
  border-bottom: 2px solid transparent;
}
</style>
