<template>
  <div class="preview-container">
    <div class="preview-header">
      <h1 class="preview-header-title">{{ previewTitle }}</h1>
      <div class="preview-header-info">
        <div class="header-info-name">{{ creatorName }}</div>
        <div class="header-info-time">{{ updateTime }}</div>
      </div>
    </div>
    <div class="preview-content" v-html="previewContent"></div>
  </div>
</template>
<script>
import ScriptService from "@/service/script_service";
const scriptService = new ScriptService();
export default {
  name: "Preview",
  components: {},
  data() {
    return {
      previewContent: "",
      updateTime: null,
    };
  },
  created() {
    if (!this.$route.query.draftChapterId) {
      this.$error("不存在该剧本");
      return;
    }
    scriptService.getDraftChapterById(
      {
        draftChapterId: this.$route.query.draftChapterId,
      },
      {
        onSuccess: (model, fModel, json) => {
          console.log(json.data.rows);
          this.previewContent = json.data.rows.serialization;
          this.updateTime = json.data.rows.updateTime;
          this.previewTitle = json.data.rows.title;
          this.creatorName = json.data.rows.creatorName;
        },
        onFail: (msg, json) => {
          console.log(msg, json);
        },
      }
    );
  },
  methods: {},
};
</script>
<style lang="scss">
.preview-container {
  background-color: #fafafb;
  padding: 1rem 1rem 0;
  overflow-x: hidden;
  min-height: 100%;
  display: flex;
  flex-direction: column;
  .preview-header-title {
    margin-bottom: 20px;
    color: #212228;
    font-weight: 700;
    font-size: 26px;
    font-family: sans-serif;
    line-height: 36px;
    letter-spacing: 0;
    text-align: left;
    word-break: break-word;
  }
  .preview-content {
    font-size: 1.125rem;
    line-height: 2rem;
  }
  .preview-header-info {
    display: flex;
    .header-info-name {
      display: flex;
      font-weight: bold;
      color: #222a75;
      margin-right: 5px;
      height: 2rem;
      line-height: 2rem;
      position: relative;
      &::after {
        content: "";
        position: absolute;
        right: -8px;
        top: 55%;
        transform: translateY(-50%);
        width: 4px;
        height: 10px;
        border-radius: 2px;
        background-color: #1890ff;
      }
    }
    .header-info-time {
      flex: 0 0 auto;
      margin: 0 0.5rem;
      color: #a4acb6;
      line-height: 2rem;
      height: 2rem;
    }
  }
}

@media screen and (min-width: 900px) {
  .preview-container {
    width: 25.875rem;
    margin: 0 auto;
  }
}

.firmware {
  font-style: italic;
  text-decoration: underline;
  color: #f03e3e;
  &:link,
  &:visited,
  &:active,
  &:focus,
  &:hover {
    color: #f03e3e;
  }
}

.user {
  font-style: italic;
  text-decoration: underline;
  color: #0ca678;
  &:link,
  &:visited,
  &:active,
  &:focus,
  &:hover {
    color: #0ca678;
  }
}

/deep/ a {
  text-decoration: underline;
  font-style: italic;
}
</style>
