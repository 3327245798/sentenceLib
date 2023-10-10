<template>
  <div class="support-tabs">
    <div :class="`support-tabs-item${supportMode === 'env' ? ' active' : ''}`" @click="handleClickEnv">
      <span class="support-title">语境</span>
      <div class="support-count">{{ envsLength }}</div>
    </div>
    <!-- <div :class="`support-tabs-item${supportMode === 'variable' ? ' active' : ''}`" @click="handleClickVariable">
      <span class="support-title">变量</span>
      <div class="support-count">{{ variablesLength }}</div>
    </div> -->
    <div :class="`support-tabs-item${supportMode === 'sentence' ? ' active' : ''}`" @click="handleClickSentence">
      <span class="support-title">句型</span>
      <div class="support-count">{{ scriptTemplatesLength }}</div>
    </div>
     <div :class="`support-tabs-item${supportMode === 'word' ? ' active' : ''}`" @click="handleClickWord">
      <span class="support-title">词汇库</span>
      <div class="support-count">{{ wordsLength }}</div>
  </div>
  </div>
</template>
<script>
export default {
  name: "SupportTabs",
  components: {},
  props: {
    supportMode: {
      type: String,
      default: "env",
    },
    scriptTemplatesLength: {
      type: Number,
      default: 0,
    },
    envsLength: { 
      type: Number,
     default: 0 },
    //variablesLength: { type: Number, default: 0 },
     wordsLength:{ 
      type: Number,
       default: 0
        },
  },
  data() {
    return {};
  },
  beforeCreate() {},
  created() {},
  methods: {
    onMenuItemSelect: function({ key }) {
      this.selectedKeys = [key];
    },
    handleClickEnv: function() {
      if (this.supportMode !== "env") this.$emit("update:supportMode", "env");
    },
    handleClickSentence: function() {
      if (this.supportMode !== "sentence") this.$emit("update:supportMode", "sentence");
    },
    /* handleClickVariable: function() {
      if (this.supportMode !== "variable") this.$emit("update:supportMode", "variable");
    }, */
     handleClickWord:function(){
      if (this.supportMode !== "word") this.$emit("update:supportMode", "word");
    }
  },
};
</script>
<style lang="scss">
.support-tabs {
  position: fixed;
  top: 0;
  z-index: 3;
  padding: 1rem 0 0.125rem 2.5rem;
  height: 4.25rem;
  display: flex;
  align-items: center;
  box-sizing: border-box;
  background: #f7f9fc;
  box-shadow: 0 0px 10px #f7f9fc;
  width: 30rem;
  .support-tabs-item {
    display: flex;
    align-items: center;
    margin-right: 1.25rem;
    cursor: pointer;
    opacity: 0.7;
    transition: opacity 0.3s ease;

    &.active {
      opacity: 1;
    }

    .support-title {
      color: #2a2b2e;
      transition: color 0.3s ease;
      font-weight: 600;
      font-size: 1rem;
    }

    .support-count {
      color: #fff;
      background: #9695a8;
      border-radius: 0.9375rem;
      margin-left: 0.25rem;
      padding: 0 0.5rem;
      font-size: 0.75rem;
      line-height: 1.25rem;
      min-width: 1.75rem;
      text-align: center;
    }
  }
}
</style>
