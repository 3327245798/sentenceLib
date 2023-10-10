<template>
  <div class="preview-container">
    <div class="cover-bg">
      <div class="preview-header">
        <h1 class="preview-header-title">{{ previewTitle }}</h1>
        <div class="preview-header-info">
          <div class="header-info-name">
            <id-card-h theme="outline" size="1rem" fill="#a4acb6" class="info-icon" />
            <span>{{ creatorName }}</span>
          </div>
          <div class="header-info-time">
            <Time theme="outline" size="1rem" fill="#a4acb6" class="info-icon" />
            <span>{{ updateTime }}</span>
          </div>
        </div>
      </div>
      <div class="preview-content" v-html="previewContent"></div>
    </div>
    <a-row type="flex" justify="space-around" class="preview-tools-container">
      <a-col v-for="(item, index) in firmwareList" :key="index" :span="8">
        <a class="tool-wrapper" :href="item.toolUrl" target="_blank">
          <div class="tool-icon-wrapper"><tool theme="outline" size="1.5rem" fill="#fff" /></div>
          <span class="tool-icon-label">{{ item.toolName }}</span>
        </a>
      </a-col>
    </a-row>
  </div>
</template>
<script>
import ScriptService from "@/service/script_service";
import { IdCardH, Time, Tool } from "@icon-park/vue";
const scriptService = new ScriptService();
export default {
  name: "Preview",
  components: { IdCardH, Time, Tool },
  data() {
    return {
      previewContent: "",
      updateTime: null,
      firmwareList: [],
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
          this.previewContent = json.data.rows.content;
          this.updateTime = json.data.rows.updateTime;
          this.previewTitle = json.data.rows.title;
          this.creatorName = json.data.rows.creatorName;

          this.refreshTools(json.data.rows.serialization);
        },
        onFail: (msg, json) => {
          console.log(msg, json);
        },
      }
    );
  },
  methods: {
    refreshTools(htmlStr) {
      // contentRef.querySelectorAll
      const el = document.createElement("div");
      el.innerHTML = htmlStr;
      el.querySelectorAll(".firmware").forEach((item) => {
        this.firmwareList.push({ toolName: item.getAttribute("data-toolname") || "", toolUrl: item.href });
      });
    },
  },
};
</script>
<style lang="scss">
.cover-bg {
  background-image: linear-gradient(180deg, #8bc5ec92 0%, #9599e284 100%);
}
.preview-container {
  background-color: #fafafb;
  overflow: hidden;
  min-height: 100%;
  display: flex;
  flex-direction: column;
  padding-bottom: 2rem;
  .preview-header-title {
    padding: 1rem 1rem 0;

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
    padding: 1rem 1rem 0;

    font-size: 1.125rem;
    line-height: 2rem;
  }
  .preview-header-info {
    padding: 1rem 1rem 0;

    display: flex;
    font-size: 0.85rem;
    .info-icon {
      margin-right: 0.25rem;
    }
    .header-info-name {
      display: flex;
      align-items: center;
      color: #a4acb6;
      margin-right: 1rem;
      height: 2rem;
      line-height: 2rem;
      position: relative;
    }
    .header-info-time {
      display: flex;
      align-items: center;
      flex: 0 0 auto;
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

.preview-tools-container {
  margin-top: 2rem;

  .tool-wrapper {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    margin-top: 1rem;
    .tool-icon-wrapper {
      width: 2rem;
      height: 2rem;
      display: flex;
      align-items: center;
      justify-content: center;
      border-radius: 6px;
      background: #74c0fc;
      box-shadow: 3px 3px 6px #d9d9d9, -3px -3px 6px #ffffff;
    }
    .tool-icon-label {
      margin-top: 1rem;
    }
  }
}

/deep/ a {
  text-decoration: underline;
  font-style: italic;
}
</style>
