<template>
  <div class="workarea-container">
    <div class="editor-container">
      <editor-top-bar :draft="draft" :bandList="bandList" />
      <div class="editor-sub-container">
        <div class="draft-header">草稿剧本</div>
        <div class="analyse-header">分析剧本</div>
        <div class="draft-wrapper">
          <!-- <draft-editor :envs="envs" :scriptTemplates="scriptTemplates" /> -->
          <!-- :curActiveSentenceIdx.sync="curActiveSentenceIdx"
            :sentences.sync="sentences"
            :envs="envs"
            :variables.sync="variables"
            :scriptTemplates="scriptTemplates" -->
          <draft-editor :envs="envs" :draft="draft" :onRefresh="refresh" />
        </div>
        <a-divider type="vertical" dashed />
        <div class="analyse-wrapper">
          <!-- <math-component /> -->
          <!-- <mathlive-component /> -->
          <!-- <analyse-editor :sentences.sync="sentences" :envs="envs" :scriptTemplates="scriptTemplates" /> -->
        </div>
        <div class="tab-wrapper">
          <div class="tab-run tab-item" @click.stop="showRunResult = !showRunResult">
            <span class="tab-title">运行结果</span>
          </div>
        </div>
        <a-drawer
          class="result-wrapper"
          placement="bottom"
          :closable="false"
          :mask="false"
          :visible="showRunResult"
          :get-container="false"
          :wrap-style="{ position: 'absolute' }"
          :height="bottomSize"
          @close="showRunResult = false"
        >
          <div class="result-header" slot="title">
            <div class="result-title-wrapper">
              <span class="result-title">
                运行结果
              </span>
            </div>
            <div class="result-tools">
              <a-tooltip placement="bottom">
                <template slot="title">
                  <span>刷新</span>
                </template>
                <div class="result-tool tool-refresh" @click.stop="handleClickRefresh()">
                  <Refresh theme="outline" size="18" fill="#333" />
                </div>
              </a-tooltip>
              <a-tooltip placement="bottom">
                <template slot="title">
                  <span>关闭</span>
                </template>
                <div class="result-tool tool-close" @click.stop="showRunResult = false">
                  <CloseSmall theme="outline" size="24" fill="#333" />
                </div>
              </a-tooltip>
            </div>
          </div>
          <div class="bottom-border-resizer" @mousedown="resizeBottomSize($event)"></div>
          <a-timeline class="result-list" pending="Recording..." :reverse="reverse">
            <a-timeline-item v-for="item in runningLog" :key="item.id">
              <div>{{ item.updateTime }}</div>
              <div>{{ item.content }}</div>
              <div>{{ item.result }}</div>
            </a-timeline-item>
          </a-timeline>
        </a-drawer>
      </div>
    </div>
    <div class="support-container">
      <support-tabs
        :supportMode.sync="supportMode"
        :scriptTemplatesLength="scriptTemplates.length"
        :envsLength="envs.length"
        :variablesLength="variables.length"
        :wordsLength="words.length"
      />
      <support :supportMode="supportMode" :scriptTemplates="scriptTemplates" :envs="envs" :variables="variables" :words="words"/>
    </div>
  </div>
</template>
<script>
import { ArrowLeft, DownOne, User, PlayOne, CloseSmall, HamburgerButton, Refresh } from "@icon-park/vue";
import SupportTabs from "./SupportTabs.vue";
import Support from "./Support.vue";
import EditorTopBar from "./EditorTopBar.vue";
// import DraftEditor from "./DraftEditor.vue";
import DraftEditor from "./DraftEditor.vue";
// import AnalyseEditor from "./AnalyseEditor.vue";
import ApplicationService from "@/service/application_service";
import ScriptService from "@/service/script_service";
import BandService from "@/service/band_service";
import ReleaseService from "@/service/release_service";
import EditorService from "@/service/EditorService";

// import MathliveComponent from "./model/MathliveComponent.vue";
const applicationService = new ApplicationService();
const scriptService = new ScriptService();
const bandService = new BandService();
const releaseService=new ReleaseService();
const editorService = new EditorService();
export default {
  name: "WorkArea",
  components: {
    ArrowLeft,
    DownOne,
    User,
    PlayOne,
    SupportTabs,
    Support,
    EditorTopBar,
    CloseSmall,
    Refresh,
    HamburgerButton,
    DraftEditor,
    // MathliveComponent,
    // AnalyseEditor,
  },
  data() {
    return {
      applicationCaseId: "",
      draft: {
        draftId: "",
        name: "",
        userName: "",
        updateTime: "",
        description: "",
        content: "",
        list: [],
      },
      bandList: [],
      envs: [],
      scriptTemplates: [
        {
          class: "sentence",
          props: {
            complete: false,
            standard: true,
            type: "command",
            params: {},
            inputAreas: {
              0: {
                class: "inputarea",
                props: { types: ["user", "role"], name: "角色/用户", unit: true },
                children: [],
              },
              2: {
                class: "inputarea",
                props: { types: ["band"], name: "帮区", unit: true },
                children: [],
              },
              4: {
                class: "inputarea",
                props: { types: ["action"], name: "部件", unit: true },
                children: [],
              },
            },
          },
          children: [
            {
              class: "inputarea",
            },
            {
              class: "plainarea",
              props: {},
              children: [
                {
                  class: "text",
                  content: "在",
                },
              ],
            },
            {
              class: "inputarea",
            },
            {
              class: "plainarea",
              children: [
                {
                  class: "text",
                  content: "执行",
                },
              ],
            },
            {
              class: "inputarea",
            },
            {
              class: "plainarea",
              children: [
                {
                  class: "text",
                  content: "。",
                },
              ],
            },
          ],
        },
        {
          class: "sentence",
          props: {
            standard: true,
            type: "assign",
            params: {},
            inputAreas: {
              1: {
                class: "inputarea",
                props: { types: ["variable"], name: "变量名", unit: true },
                children: [],
              },
              3: {
                class: "inputarea",
                props: { types: ["value"], name: "值", unit: false },
                children: [],
              },
            },
          },
          children: [
            {
              class: "plainarea",
              props: {},
              children: [
                {
                  class: "text",
                  content: "将",
                },
              ],
            },
            {
              class: "inputarea",
            },
            {
              class: "plainarea",
              props: {},
              children: [
                {
                  class: "text",
                  content: "设置为",
                },
              ],
            },
            {
              class: "inputarea",
            },
            {
              class: "plainarea",
              props: {},
              children: [
                {
                  class: "text",
                  content: "。",
                },
              ],
            },
          ],
          complete: false,
        },
      ],
      words:[],
      variables: [
        { type: "dynamic", key: "工具执行结果" },
        { type: "static", key: "截止时间", value: "2022年5月24日" },
      ],
      bottomSize: 200,
      supportMode: "env",
      showDraft: true,
      isLayoutChanging: false,
      sentences: [{ type: "sentence", standard: false, unknownText: "" }],
      showRunResult: false,
    };
  },
  beforeCreate() {},
  created() {
    this.draft.draftId = this.$route.params.scriptId;
    this.applicationCaseId = this.$route.params.id;
    this.refresh();
  },
  watch: {},
  methods: {
    handleClickRefresh() {
    /*   scriptService.getRunningDetailsByGid(
        {
          globalId: 1,
        },
        {
          onSuccess: (model, fModel, json) => {
            this.runningLog = [...json.data.rows];
          },
          onFail: (msg, json) => {
            console.log(msg, json);
          },
        }
      ); */
    },
    resizeBottomSize(e) {
      let startY = e.clientY;
      e.target.classList.add("resizing");
      document.onmousemove = (e) => {
        let endY = e.clientY;
        let move = endY - startY;
        startY = endY;
        if (this.bottomSize - move <= 200) this.bottomSize = 200;
        else if (this.bottomSize - move >= 500) this.bottomSize = 500;
        else this.bottomSize -= move;
      };
      document.onmouseup = () => {
        document.onmousemove = null;
        document.onmouseup = null;
        e.target.classList.remove("resizing");
      };
    },
    refresh() {
      applicationService.getDraft(
        {
          draft: {
            id: this.draft.draftId,
          },
          pageInfo: {
            pageSize: 20,
            currentPage: 1,
          },
        },
        {
          onSuccess: (model, fModel, json) => {
            let [draft] = json.data.rows;
            if (draft) {
              this.draft = Object.assign({}, this.draft, {
                draftId: draft.id,
                name: draft.name,
                userName: draft.creatorName,
                updateTime: draft.updateTime,
                description: draft.description,
              });
            }
          },
          onFail: (msg, json) => {
            console.log(msg, json);
          },
        }
      );
      scriptService.getDraftChapterAndAnalyse(
        {
          draftId: this.draft.draftId,
        },
        {
          onSuccess: (model, fModel, json) => {
            if (json.data.draftChapter.draftId) {
              this.draft = Object.assign({}, this.draft, json.data.draftChapter);
            }
          },
          onFail: (msg, json) => {
            console.log(msg, json);
          },
        }
      );
           //根据帮区id获取该帮区的词汇
  editorService.getOnlyWords({
bandId:window.g_bandId
},{
  onSuccess:(odel, fModel, json)=>{
    console.log(json);
    console.log(json.data.bandVariableArray.rows.length);
    this.words=json.data.bandVariableArray.rows;
    console.log(this.words);


  },onFail: (msg, json) => {
            console.log(msg, json);
          },
});
      applicationService.getApplicationCaseById(
        {
          applicationCaseId: this.applicationCaseId,
        },
        {
          onSuccess: (model, fModel, json) => {
            const bandList = json.data.rows.bandList.map((item) => item.bandId);
            bandService.getWordAndSentenceEnvironment(
              {
                bandList,
                type: 1,
              },
              {
                onSuccess: (model, fModel, json) => {
                  this.scriptTemplates = json.data.rows.sentence;
                  this.envs = json.data.rows.word;
                },
                onFail: (msg, json) => {
                  console.log(msg, json);
                },
              }
            );
            this.bandList = [...json.data.rows.bandList];
          },
          onFail: (msg, json) => {
            console.log(msg, json);
          },
        }
      );
      scriptService.getRunningDetailsByGid(
        {
          globalId: 1,
        },
        {
          onSuccess: (model, fModel, json) => {
            this.runningLog = json.data.rows;
          },
          onFail: (msg, json) => {
            console.log(msg, json);
          },
        }
      );
      
    },
  },
};
</script>
<style lang="scss">
.workarea-container {
  height: 100%;
  display: flex;
  .editor-container {
    flex: 1;
    position: relative;
    background: #fff;
    height: 100%;
    .editor-sub-container {
      display: flex;
      position: relative;
      overflow: hidden;
      padding: 2rem 0;
      height: calc(100% - 4.25rem);
      .ant-divider.ant-divider-vertical {
        height: 100%;
        margin: 0;
        border-width: 0 0 0 0.125rem;
      }
    }
  }
  .support-container {
    background: #f7f9fc;
    min-width: 30rem;
  }
}

.result-wrapper {
  .ant-drawer-wrapper-body {
    overflow: hidden;
  }
  .ant-drawer-body {
    height: 100%;
    padding: 0;
  }
  .result-header {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    .result-title-wrapper {
      flex: 1;
    }
    .result-tools {
      display: flex;
      .result-tool {
        opacity: 0.8;
        margin-left: 0.5rem;
        display: flex;
        align-items: center;
        &:hover {
          opacity: 1;
        }
      }
    }
  }
  .result-list {
    height: calc(100% - 2rem);
    overflow-y: auto;
    padding: 1.5rem;
  }
}

.draft,
.analyse {
  &-wrapper {
    height: 100%;
    background-color: #fff;
    margin: 0 auto;
    flex: 1;
    overflow: hidden;
    display: flex;
  }
}
.draft-header,
.analyse-header {
  display: block;
  position: absolute;
  line-height: 1.25rem;
  font-size: 0.875rem;
  text-align: center;
  padding: 0.25rem;
  font-weight: 400;
  top: 0;
  font-weight: 600;
}
.draft-header {
  left: 25%;
  transform: translateX(-50%);
  border-radius: 0 0.25rem 0.25rem 0;
}
.analyse-header {
  right: 25%;
  transform: translateX(50%);
  border-radius: 0.25rem 0 0 0.25rem;
}

.tab-wrapper {
  position: absolute;
  width: 100%;
  bottom: 0.25rem;
  left: 2rem;
  height: 2rem;
  display: flex;
  flex-direction: row;
  align-items: center;
  font-size: 0.75rem;
  .tab-item {
    border-radius: 1.5rem;
    background-color: #eff0f3;
    height: 1.5rem;
    width: 5rem;
    cursor: pointer;
    text-align: center;
    opacity: 0.8;
    .tab-title {
      color: rgba(47, 48, 53, 0.7);
      font-size: 14px;
      font-weight: 600;
      line-height: 1.5rem;
    }
    &:hover {
      opacity: 1;
    }
  }
}

.ant-popover-inner-content {
  padding: 0;
}

.bottom-border-resizer {
  width: 100%;
  height: 4px;
  background-color: transparent;
  position: absolute;
  top: 0;
  cursor: n-resize;
  &.resizing,
  &:hover {
    background: #748ffc;
    box-shadow: 0 0 4px rgba(116, 143, 252, 0.2);
  }
}

// .se-sentence {

//   &:not(.standard):not(.empty).active {
//     background-color: rgba(247, 67, 77, 0.15);
//     .sentence-menu {
//       background-color: rgba(247, 67, 77, 0.5);
//       --tool-color: #fff;
//     }
//   }
//   &:not(.standard) .se-unknown:not(:empty) {
//     border-bottom: 2px rgba(247, 67, 77, 0.5) solid;
//   }
//   .se-unknown:empty {
//     display: block;
//   }
//   .se-unknown {
//     cursor: pointer;
//     outline: 0;
//     width: 100%;
//     line-height: 2rem;
//   }
//   // .se-plain {
//   //   font-weight: 600;
//   // }
//   // .se-identifier {
//   //   padding-left: 0.45rem;
//   // }

// }
// .se-sentence div {
//   display: inline;
//   line-height: 2rem;

//   .identifier-input {

//     &.has-env {
//       &:focus {
//         border-bottom-color: rgba(52, 101, 255, 0.5);
//       }
//       &.identifier-band {
//         font-weight: 600;
//         color: #3465ff;
//         border-bottom-color: transparent;
//       }
//       &.identifier-people {
//         font-weight: 400;
//         color: #2a2b2e;
//         border-bottom-color: rgba(42, 43, 46, 0.5);
//       }
//       &.identifier-action {
//         font-weight: 600;
//         color: #2a2b2e;
//         border-bottom-color: rgba(42, 43, 46, 0.5);
//       }
//       &.identifier-tool {
//         font-weight: 400;
//         color: #2a2b2e;
//         border-bottom-color: rgba(42, 43, 46, 0.5);
//       }
//     }
//     &:not(:empty) {
//       &.identifier-param {
//         font-weight: 400;
//         color: #2a2b2e;
//         border-bottom-color: rgba(42, 43, 46, 0.5);
//       }
//     }
//   }
// }
</style>
