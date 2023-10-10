<template>
  <div class="listv2-root">
    <a-list
      :itemLayout="itemLayout"
      :size="encoding.size?encoding.size:size"
      :pagination="encoding.pagination !== false ?encoding.pagination?encoding.pagination:pagination : false"
      :dataSource="data"
    >
      <a-list-item slot="renderItem" slot-scope="item, index" :key="index">{{ item.content }}</a-list-item>
    </a-list>
  </div>
</template>
 
<script>
import Vue from "vue";
import { List } from "ant-design-vue";
import "ant-design-vue/dist/antd.css";
 
Vue.use(List);
 
export default {
  name: "Listv2",
  props: {
    data: {
      type: Array,
      required: true
    },
    encoding: {
      type: Object,
      default: () => ({})
    }
  },
  data() {
    return {
      itemLayout: "vertical",
      size: "default",
      pagination: {
        onChange: page => {
          console.log("page", page);
        },
        pageSize: 3
      }
    };
  }
};
</script>
 
<style lang="scss" scoped>
.listv2-root {
  width: 100%;
  height: 100%;
  position: relative;
}
</style>