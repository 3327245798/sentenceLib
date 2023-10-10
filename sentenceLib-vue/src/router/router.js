import App from "components/App";
//路由组件
/* 最后一个参数就是分出来的模块名称, 根据需要定义数量 */

const SentencePage = (r) => require.ensure([], () => r(require("../views/SentencePage")), "sentencePage");
export default [
  {
    path: "/",
    component: App, //顶层路由，对应index.html
    children: [
      //二级路由。对应App.vue
      {
        path: '',
        redirect: '/sentencePage2'
      },

      {
        path: "/sentencePage2",
        name: "SentencePage",
        component: SentencePage,
      },
    ],
  },


];
