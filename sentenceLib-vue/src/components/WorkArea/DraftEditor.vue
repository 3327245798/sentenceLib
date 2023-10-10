<template>
  <div class="draft-editor-container">
    <div class="mention-wrapper" style="transform: translateX(-50%)">
      <div class="mention-header">用户</div>
      <div class="mention-list" ref="menuRef">
        <div
          :class="[
            'mention-item',
            { active: mention.selectedMentions.length && mention.selectedMentions[0].id === item.id },
          ]"
          v-for="(item, index) in mention.envMentions"
          :key="item.id"
          @click="handleAddFirmware(item, index)"
        >
          <span class="mention-item-type"> {{ item.label }}</span>
          <span class="mention-item-name">{{ item.name }} </span>
          <!-- <span class="mention-item-key"> {{ item.id }}</span> -->
        </div>
      </div>
    </div>
    <a-modal title="选择发布模式" v-model="isSelectReleaseMode" @ok="handleRelease()">
      <a-spin :spinning="isReleasing">
        <a-radio-group v-model="releaseMode">
          <a-radio value="previewlink">以文本模式发布</a-radio>
          <a-radio value="previewtool">以标准模式发布</a-radio>
        </a-radio-group>
      </a-spin>
    </a-modal>
  </div>
</template>

<script>
import { getBandId, shareToWx } from "common/env";
import { buildDispatchToolUrl } from "common/constants";
import { mapState } from "vuex";
import { Editor, EditorContent } from "@tiptap/vue-2";
import Link from "@tiptap/extension-link";
import StarterKit from "@tiptap/starter-kit";
import { CubeThree, Share, Save, LinkOne, People } from "@icon-park/vue";
import BubbleMenu from "@tiptap/extension-bubble-menu";
import Firmware from "./model/Firmware";
import User from "./model/User";
import { Color } from '@tiptap/extension-color'
import Text from '@tiptap/extension-text'
import TextStyle from '@tiptap/extension-text-style'
import { customEnvs } from "./model/env";
import EditorService from "@/service/EditorService.js";
import ShareService from '@/service/share_service.js';
const editorService = new EditorService();
const shareSevice = new ShareService();
export default {
  components: {
    EditorContent,
    CubeThree,
    Share,
    Save,
    LinkOne,
    People,
  },
  props: {
    contexts: {
      type: Array,
      default: [],
    },
    words: {
      type: Array,
      default: [],
    },
    draft: {
      type: Object,
      default: {},
    },
    onRefresh: {
      type: Function,
    },
  },
  watch: {
    draft: {
      handler(cur, prev) {
        const regS = new RegExp("<p><br></p>", "g");
        if (this.editor && cur !== prev && cur.serialization) {
          this.editor.commands.setContent(cur.serialization.replace(regS, "<p></p>"));
          console.log(cur.serialization);
          console.log(this.editor.commands.setContent("hello"));
        }
      },
      immediate: true,
      deep: true,
    },
  },
  data() {
    return {
      editor: null,
      mention: {
        envMentions: [],
        selectedMentions: [],
        mentionType: "",
      },
      isSaving: false,
      isReleasing: false,
      isSelectReleaseMode: false,
      releaseMode: "previewlink",
    };
  },
  computed: {
    ...mapState(["ST_CURRENT"]),
  },
  methods: {
    initEnvMentions(type) {
      this.mention.envMentions = customEnvs([type], null, { words: this.words, scriptTemplates: [] }, "beginning");
      this.mention.selectedMentions = [this.mention.envMentions[0]];
      console.log(this.mention);
    },
    handleAddFirmware(item, index) {
      let url = "";
      let markType = "";
      if (this.mention.mentionType === "ToolWord") {
        markType = "firmware";
        url = `https://www.wetoband.com/tre//runToolWithToolShopToolID?gid=${getBandId()}&toolID=${
          item.value
        }&bandID=${getBandId()}&param=`;
        this.editor.chain().extendMarkRange(markType).setFirmware({ href: url, "data-toolname": item.name }).run();
      } else if (this.mention.mentionType === "UserWord") {
        markType = "user";
        let param = {
          userId: item.value,
        };
        let encodeP = encodeURI(JSON.stringify(param));
        const toolId = "4389207";
        url = `https://www.wetoband.com/tre//runToolWithToolShopToolID?gid=${getBandId()}&toolID=${toolId}&bandID=${getBandId()}&param=${encodeP}`;
        this.editor.chain().extendMarkRange(markType).setUser({ href: url }).run();
      }

      const { from } = this.editor.state.selection;
      this.editor.chain().setTextSelection({ from: from, to: from }).extendMarkRange(markType).focus().run();
      this.mention.selectedMentions = [this.mention.envMentions[index]];
    },
    handleSelectReleaseMode() {
      this.isSelectReleaseMode = true;
    },
    async handleRelease() {
      if (!this.draft.draftChapterId) {
        this.$message.warn("请先编写剧本并保存！");
        return;
      }
      const title = this.draft.title;
      const description = this.draft.description;
      const imgUrl = "";
      const param = {
        draftChapterId: this.draft.draftChapterId,
        previewMode: this.releaseMode,
      };
      this.isReleasing = true;
      console.log(shareSevice);
      shareSevice.createShareInCore(
        {
          bandId: getBandId(),
          title: title,
          description: title,
          roleName: "普通剧本发布角色",
        },
        {
          onSuccess: (result) => {
            console.log(result);
            console.log("发布成功，即将进入微信分享");
            console.log(result[0].data.dispatchID);
            let url = buildDispatchToolUrl({
              dispatchID: result[0].data.dispatchID,
              organizationID: result[0].data.organizationID,
              objID: result[0].data.objID,
              param: param,
              title: title,
              bandID: getBandId(),
            });
            console.log(url);
            shareToWx({
              url: url,
              title: title,
              content: description,
              imgUrl: imgUrl,
              text: title,
            });
            // this.openShareWx = false;
            this.isReleasing = false;
          },
          onFail: (err) => {
            console.log("分享失败，请再次尝试分享。", err);
            this.isReleasing = false;
            this.$message.error("分享失败，请再次尝试分享。");
          },
        }
      );
    },
    handleSave() {
      this.isSaving = true;
      const regS = new RegExp("<p></p>", "g");
      console.log(this.draft);
      if (this.draft.draftChapterId) {
        editorService.updateDraftChapter(
          {
            serialization: this.editor.getHTML().replace(regS, "<p><br></p>"),
            draftId: this.draft.id,
            id: this.draft.draftChapterId,
            content: this.editor.getText(),
          },
          {
            onSuccess: (model, fModel, json) => {
              // console.log(json);
              this.isSaving = false;
              // this.onRefresh();
              this.$message.success("剧本保存成功。");
            },
            onFail: (msg, json) => {
              console.log(msg, json);
              this.$message.error("保存失败，请在此尝试保存。");
            },
          }
        );
      } else {
        this.$message.error("保存失败，请将数据备份，然后刷新页面重试。");
      }
    },
    handleAddLink() {
      const previousUrl = this.editor.getAttributes("link").href;
      const url = window.prompt("URL", previousUrl);

      if (url === null) return;
      if (url === "") {
        this.editor.chain().focus().extendMarkRange("link").unsetLink().run();
        return;
      }
      this.editor.chain().focus().extendMarkRange("link").setLink({ href: url }).run();
    },
    handleCompile() {
      console.log(this.editor.getText());
      const text = "黄威执行开灯工具";
      const label = "B-user I-user O O B-tool I-tool I-tool I-tool"
      // const arr = ["黄威-user", "执行-o", "开灯工具-tool"]
      const arr = ["黄威-blue", "执行-black", "开灯工具-red"]
      let content = ``
      arr.forEach(item => {
        let t = item.split('-')[0]
        let l = item.split('-')[1]
        content += `<span style='color:${l}'>${t}</span>`
      });
      content += `<span style='{color:red}'>test</span>`
      content += `<span style='color:#123'>test</span>`

      content += `<br><br><br><span>标注说明：</span><br>`
      content += `<span style='color:blue'>蓝色：用户</span><br>`
      content += `<span style='color:red'>红色：工具</span><br>`
      content += `<span style='color:black'>黑色：其他</span><br>`

      console.log("editor:", this.editor);
      this.editor.commands.setContent(content);
      // this.editor.chain().setColor('#ff0000');
      // console.log(this.editor.content);
      console.log(this.editor.getHTML());
      console.log(this.editor.getText());
      console.log(this.draft);
      // if (this.draft.draftChapterId) {
      //   editorService.updateDraftChapter(
      //     {
      //       serialization: this.editor.getHTML().replace(regS, "<p><br></p>"),
      //       draftId: this.draft.id,
      //       id: this.draft.draftChapterId,
      //       content: this.editor.getText(),
      //     },
      //     {
      //       onSuccess: (model, fModel, json) => {
      //         // console.log(json);
      //         this.isSaving = false;
      //         // this.onRefresh();
      //         this.$message.success("剧本保存成功。");
      //       },
      //       onFail: (msg, json) => {
      //         console.log(msg, json);
      //         this.$message.error("保存失败，请在此尝试保存。");
      //       },
      //     }
      //   );
      // } else {
      //   this.$message.error("保存失败，请将数据备份，然后刷新页面重试。");
      // }
    },
  },
  beforeDestroy() {
    this.editor.destroy();
  },
  mounted() {
    this.editor = new Editor({
      element: document.querySelector(".draft-editor-container"),
      editorProps: {
        attributes: {
          class: "draft-editor",
        },
      },
      content: "xxx",
      extensions: [
        StarterKit,
        Firmware,
        User,
        Color,
        Text,
        TextStyle,
        BubbleMenu.configure({
          // from: 选中文本的开始下标（从1开始），to: 选中文本的结束下标
          shouldShow: ({ editor, view, state, oldState, from, to }) => {
            console.log(view);
            console.log(state);
            console.log(oldState);
            if (editor.isActive("firmware")) {
              const mentionType = "ToolWord";
              this.mention.mentionType = mentionType;
              this.initEnvMentions(mentionType);
            } else if (editor.isActive("user")) {
              const mentionType = "UserWord";
              this.mention.mentionType = mentionType;
              this.initEnvMentions(mentionType);
            } else {
              return false;
            }
            this.$nextTick(() => {
              const dom = this.$refs.menuRef.querySelector(".active");
              // console.log(dom);
              if (dom) {
                dom.scrollIntoView();
              }
            });
            return this.mention.envMentions.length;
          },
          element: document.querySelector(".mention-wrapper"),
          tippyOptions: {
            placement: 'bottom-end',
          },
        }),
        Link.configure({
          openOnClick: false,
        }),
      ],
      onSelectionUpdate({ editor }) {},
      onFocus({ editor, event }) {},
    });
    this.$root.bus.$on("handleCompile", this.handleCompile);
    this.$root.bus.$on("handleAddLink", this.handleAddLink);
    this.$root.bus.$on("handleAddFirmware", () => {
      this.editor.chain().focus().toggleFirmware().run();
    });
    this.$root.bus.$on("handleAddUser", () => {
      this.editor.chain().focus().toggleUser().run();
    });

    this.$root.bus.$on("handleSelectReleaseMode", this.handleSelectReleaseMode);
    this.$root.bus.$on("handleSave", this.handleSave);
  },
};
</script>
<style lang="scss">
.test {
  color: #0ca678;
}
.draft-editor-container {
  width: 100%;
  height: 100%;
  background-color: #fff;
  display: flex;
}
.support-tabs .support-tabs-item.active {
    opacity: 1;
    background: #0aa9e3;
    padding: 5px 9px;
    border-radius: 23px;

    span{
      color: white !important;
    }
}
.draft-editor {
  padding: 0 20px;
  outline: 0;
  border: 0;
  caret-color: #000;
  font-size: 1.125rem;
  background: transparent;
  outline: none;
  word-wrap: break-word;
  word-break: break-all;
  color: #2a2b2e;
  line-height: 1.6rem;
  letter-spacing: 0.125rem;
  min-height: 5rem;
  width: 100%;
  height: 100%;
  overflow: auto;
}
a {
  &,
  &:link,
  &:visited,
  &:active,
  &:focus,
  &:hover {
    color: #1890ff;
    text-decoration: underline;
  }
  &.firmware[href] {
    color: #f03e3e !important;
    text-decoration: underline !important;
    font-style: italic !important;
  }
  &.user[href] {
    color: #0ca678 !important;
    text-decoration: underline !important;
    font-style: italic !important;
  }
}
.has-focus {
  border-radius: 3px;
  box-shadow: 0 0 0 3px #68cef8;
}
.mention-wrapper {
  position: fixed;
  max-width: 500px;
  max-height: 200px;
  border-radius: 0.5rem;
  box-shadow: 0 0 0.5rem #ced4da !important;
  z-index: 400;
  min-width: 300px;
  padding: 0.5rem 1rem;
  background: #fff;
  display: flex;
  flex-direction: column;
  .mention-header {
    height: 2rem;
    line-height: 2rem;
    color: #adb5bd;
    font-size: 0.875rem;
  }
  .mention-list {
    height: calc(100% - 2rem);
    overflow: auto;
    &::-webkit-scrollbar {
      width: 0px;
    }
  }
  .mention-list .mention-item {
    display: flex;
    flex-direction: row;
    align-items: center;
    font-size: 0.875rem;
    height: 2rem;
    padding: 0.25rem;
    border-radius: 0.25rem;
    opacity: 0.8;
    &:hover,
    &.active {
      background-color: #e7f5ff;
      opacity: 1;
    }
    &-type {
      font-size: 0.75rem;
      margin: 0 0.5rem;
      background-color: #74c0fc;
      padding: 0.125rem;
      border-radius: 4px;
      color: #fff;
    }
    &-name {
      flex: 1;
      color: #000;
    }
    &-source {
      color: #4dabf7;
      margin: 0 0.5rem;
      font-size: 9px;
    }
  }
}
.ProseMirror-hideselection *::selection {
  background: none !important;
}
</style>
