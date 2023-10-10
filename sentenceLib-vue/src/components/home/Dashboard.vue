<template>
  <a-layout class="dashboard-container">
    <a-layout>
      <a-layout-sider width="14rem" style="background: #fff;padding:1rem">
        <a-menu mode="inline" :default-selected-keys="['1']" :style="{ height: '100%', borderRight: 0 }">
          <a-menu-item key="1">
            工作台
          </a-menu-item>

          <!-- <a-menu-item key="2">
            收藏
          </a-menu-item><a-menu-item key="3">
          </a-menu-item>
          <a-menu-item key="4">
          </a-menu-item> -->
        </a-menu>
      </a-layout-sider>
      <a-layout style="padding: 1rem ">
        <a-layout-content :style="{ background: '#fff', padding: '1rem', marginRight: '1rem', minHeight: '18rem' }">
        </a-layout-content>
        <a-layout-sider width="24rem" style="background: #fff;padding:1rem;overflow-y:auto"
          >最近动态
          <a-timeline style="margin-top:2rem;">
            <a-timeline-item>李金辉添加了业务应用 金辉物流业务</a-timeline-item>
            <a-timeline-item>梁杰潮修改了业务应用 金辉物流业务</a-timeline-item>
            <a-timeline-item>
              梁子文添加了业务应用 子文物流业务
            </a-timeline-item>
            <a-timeline-item>梁杰潮在杰潮物流业务添加了新剧本 管理员审批流程</a-timeline-item>
            <a-timeline-item>李金辉添加了业务应用 金辉物流业务</a-timeline-item>
            <a-timeline-item>梁杰潮修改了业务应用 金辉物流业务</a-timeline-item>
            <a-timeline-item>
              梁子文添加了业务应用 子文物流业务
            </a-timeline-item>
            <a-timeline-item>梁杰潮在杰潮物流业务添加了新剧本 管理员审批流程</a-timeline-item>
            <a-timeline-item>李金辉添加了业务应用 金辉物流业务</a-timeline-item>
            <a-timeline-item>梁杰潮修改了业务应用 金辉物流业务</a-timeline-item>
            <a-timeline-item>
              梁子文添加了业务应用 子文物流业务
            </a-timeline-item>
            <a-timeline-item>梁杰潮在杰潮物流业务添加了新剧本 管理员审批流程</a-timeline-item>
            <a-timeline-item>李金辉添加了业务应用 金辉物流业务</a-timeline-item>
            <a-timeline-item>梁杰潮修改了业务应用 金辉物流业务</a-timeline-item>
            <a-timeline-item>
              梁子文添加了业务应用 子文物流业务
            </a-timeline-item>
            <a-timeline-item>梁杰潮在杰潮物流业务添加了新剧本 管理员审批流程</a-timeline-item>
          </a-timeline>
        </a-layout-sider>
      </a-layout>
    </a-layout>
  </a-layout>
</template>
<script>
export default {
  name: "Dashboard",
  components: { EditorContent },
  data() {
    return {
      editor: null,
    };
  },
  mounted() {},
  beforeCreate() {},
  created() {},
  methods: {},
  beforeDestroy() {
    this.editor.destroy();
  },
};
</script>
<style lang="scss">
.dashboard-container {
  height: 100%;
  width: 100%;
  overflow: hidden;
  position: absolute;
}
</style>
