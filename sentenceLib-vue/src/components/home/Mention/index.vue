<template>
  <div class="script-card" @click="onSelectChange">
    <div id="toolbar" class="toolbar">
      <a-tooltip placement="bottom" v-for="item in workTools" :key="item.key">
        <template slot="title">
          <span>{{ item.tooltip }}</span>
        </template>
        <div class="toolbar-button" @click="item.callback && item.callback(script.text, usedEnvs)">
          <component :is="item.iconName" theme="filled" size="1rem" fill="rgba(0,0,0,0.75)"></component>
          <!-- <play-two theme="filled" size="1rem" fill="rgba(0,0,0,0.75)" /> -->
        </div>
      </a-tooltip>
    </div>
    <div class="toolbar-shadow"></div>
    <quill-editor
      ref="sectionRef"
      v-model="script.html"
      :options="editorOption"
      @blur="onEditorBlur($event)"
      @change="onEditorChange($event)"
      @focus="onEditorFocus($event)"
    >
    </quill-editor>
    <a-menu
      class="mention-wrapper"
      v-if="mentionVisible"
      :defaultSelectedKeys="selectedKeys"
      :selectedKeys="selectedKeys"
      :style="{
        left: `calc(${mentionPosition[0]}px + 0.25rem)`,
        top: `calc(${mentionPosition[1]}px + 0.25rem)`,
        position: 'fixed',
      }"
      ref="menuRef"
    >
      <a-menu-item v-for="item in envMentions" :key="item.id">
        <a-tag color="cyan">{{ item.type === "word" ? "词汇" : "句型" }}</a-tag
        ><span>{{ item.content }} ( id : {{ item.id }} )</span>
      </a-menu-item>
    </a-menu>
  </div>
</template>
<script>
import { TreeDiagram, PlayTwo, SaveOne, Bookmark, Bill, Terminal } from "@icon-park/vue";
import ScriptService from "@/service/script_service";
const scriptService = new ScriptService();
// import { Quill } from "vue-quill-editor";
// let Delta = Quill.import("delta");
export default {
  name: "Mention",
  components: {
    PlayTwo,
    SaveOne,
    TreeDiagram,
    Bookmark,
    Bill,
    Terminal,
  },
  props: {
    script: {
      type: Object,
      default: () => {
        return { text: "", html: "" };
      },
    },
    currentSelectIndex: {
      type: Number,
      default: () => -1,
    },
    currentIndex: {
      type: Number,
      default: () => NaN,
    },
    workTools: {
      type: Array,
      default: () => [],
    },
    envList: {
      type: Array,
      default: () => [],
    },
  },
  created() {
    scriptService.pageConditionG4List(
      {
        g4: {},
      },
      {
        onSuccess: (model, fModel, json) => {
          this.sentenceList = json.data.rows;
        },
        onFail: (msg, json) => {
          console.log(msg, json);
        },
      }
    );
  },
  mounted() {
    //获取编辑器
    this.editor = this.$refs.sectionRef.quill;
  },
  watch: {
    envList: {
      handler(cur, prev) {
        this.initEnvMentions();
        if (this.envMentions.length) {
          this.selectedKeys = [this.envMentions[0].id];
        }
        // 获取当前剧本中使用的语境
        const envChunks = this.script.text.split(" ");
        this.usedEnvs = new Map();
        this.envList.forEach((item) => {
          if (!this.usedEnvs.has(item.title) && envChunks.indexOf(item.title) != -1) {
            this.usedEnvs.set(item.title, item.key);
          }
        });
      },
      immediate: true,
      deep: true,
    },
  },
  data() {
    return {
      mentionVisible: false,
      mentionPosition: [-1000, -1000],
      selectedKeys: [],
      envMentions: [
        // { id: "1", content: "句型1", type: "sentence" },
        // { id: "2", content: "句型2", type: "sentence" },
        // { id: "3", content: "句型3", type: "sentence" },
      ],
      sentenceList: [],
      usedEnvs: null,
      editor: null,
      editorOption: {
        modules: {
          toolbar: "#toolbar",
          keyboard: {
            bindings: {
              enter: {
                key: 13,
                handler: (range, context) => {
                  if (this.mentionVisible) {
                    let idx = -1;
                    let len = this.envMentions.length;
                    for (let i = 0; i < len; i++) {
                      if (this.envMentions[i].id == this.selectedKeys[0]) {
                        idx = i;
                        break;
                      }
                    }
                    if (idx < 0) return false;
                    // if (this.envMentions[idx].type == "sentence") {
                    // } else {
                    // }

                    // let message = document.createTextNode("hello world");
                    // this.$refs.sectionRef.$el.appendChild(message);
                    let newContent = this.editor.getContents().ops;
                    let text = newContent[newContent.length - 1].insert;
                    let lastIndex = Math.max(
                      text.lastIndexOf("。"),
                      text.lastIndexOf(" "),
                      text.lastIndexOf(","),
                      text.lastIndexOf("，")
                    );
                    if (lastIndex == -1) {
                      newContent[newContent.length - 1].insert = this.envMentions[idx].content;
                    } else {
                      newContent[newContent.length - 1].insert =
                        newContent[newContent.length - 1].insert.slice(0, lastIndex + 1) +
                        this.envMentions[idx].content;
                    }
                    this.editor.setContents(newContent);
                    this.moveCursorToEnd();
                    this.mentionVisible = false;
                  } else {
                    this.editor.blur();
                  }
                },
              },
              up: {
                key: 38,
                handler: () => {
                  if (this.mentionVisible) {
                    this.changeMenuItem(-1);
                  }
                },
              },
              down: {
                key: 40,
                handler: () => {
                  if (this.mentionVisible) {
                    this.changeMenuItem(1);
                  }
                },
              },
            },
          },
        },
        placeholder: null,
        // placeholder:
        //   "请在此编辑您的剧本，布局：两边顶到上格，中间https://www.zcool.com.cn/work/ZMzQ5ODgzMTY=.html https://shimo.im/docs/PTdhrcpXHxTPXkvv",
      },
    };
  },
  methods: {
    initEnvMentions() {
      if (!this.envList.length) return;
      this.envMentions = [];
      this.envList.forEach((item) => {
        this.envMentions.push({ id: item.key, content: item.title, type: "word" });
      });
      this.sentenceList.forEach((item) => {
        this.envMentions.push({ id: item.id, content: item.content, type: "sentence" });
      });
    },
    moveCursorToEnd() {
      let length = this.editor.getLength();
      this.editor.setSelection(length + 1);
    },
    changeMenuItem(op) {
      let len = this.envMentions.length;
      if (len <= 1) return;
      let idx = 0;
      for (let i = 0; i < len; i++) {
        if (this.envMentions[i].id == this.selectedKeys[0]) {
          idx = i;
          break;
        }
      }
      if (op == 1) {
        idx = (idx + 1) % len;
        this.selectedKeys = [this.envMentions[idx].id];
      } else {
        idx = (idx + len - 1) % len;
        this.selectedKeys = [this.envMentions[idx].id];
      }
      this.$refs.menuRef.$el.querySelector(".ant-menu-item-selected").scrollIntoView();
      if (idx == 0) {
        this.$refs.menuRef.$el.scrollTop = 0;
      }
      // this.$refs.menuRef.$el.scrollTop = 40 * idx;
    },
    onEditorChange(delta) {
      // 获取当前剧本中使用的语境
      const envChunks = delta.text.split(" ");
      this.usedEnvs = new Map();
      this.envList.forEach((item) => {
        if (!this.usedEnvs.has(item.title) && envChunks.indexOf(item.title) != -1) {
          this.usedEnvs.set(item.title, item.key);
        }
      });

      if (!this.editor.getSelection()) return;
      let index = this.editor.getSelection().index;
      let rect = this.editor.getBounds(index);
      let parentRect = this.editor.root.getBoundingClientRect();
      this.mentionPosition = [rect.right, parentRect.y + rect.bottom];
      if (!this.mentionVisible) this.mentionVisible = true;
      if (delta.text.trim() == "") this.mentionVisible = false;
      // 模糊匹配，找到最后一个特殊符号后面的字符
      let idx = Math.max(
        delta.text.lastIndexOf("。"),
        delta.text.lastIndexOf(" "),
        delta.text.lastIndexOf(","),
        delta.text.lastIndexOf("，")
      );

      let fuzzyInput = "";
      // 没有特殊符号，从头匹配
      if (idx === -1) {
        fuzzyInput = delta.text;
      } else {
        fuzzyInput = delta.text.slice(idx + 1);
      }
      fuzzyInput = fuzzyInput.trim();
      if (fuzzyInput === "") {
        this.envMentions = [];
      } else {
        this.initEnvMentions();
        this.envMentions = this.envMentions.filter((item) => {
          return item.content.indexOf(fuzzyInput) !== -1;
        });
      }
      if (this.envMentions.length) {
        this.selectedKeys = [this.envMentions[0].id];
      }

      this.$emit("update:script", { html: delta.html, text: delta.text });
    },
    onEditorBlur() {
      this.mentionVisible = false;
    },
    onEditorFocus() {},
    onSelectChange() {},
    onKeyDown(e) {
      // 方向上，就选择上一个提醒
      if (this.mentionVisible && e.keyCode == 38) {
        this.changeMenuItem(-1);
        e.preventDefault();
      }
      // 方向下，就选择下一个提醒
      else if (this.mentionVisible && e.keyCode == 40) {
        this.changeMenuItem(1);
        e.preventDefault();
      }
      return false;
    },
  },
};
</script>
<style lang="scss">
.script-card {
  position: relative;
  height: 100%;
}
.mention-wrapper {
  overflow: auto;
  max-width: 500px;
  max-height: 200px;
  border-radius: 0.5rem;
  box-shadow: 0 0 0.5rem #ced4da !important;
  z-index: 1000;
}
.quill-editor {
  width: 100%;
  height: calc(100% - 3rem);
  padding: 0.5rem;
  position: relative;
  box-sizing: border-box;
  overflow-x: hidden;
  overflow-y: auto;
  padding-top: 2px;
}
// .quill-editor::-webkit-scrollbar {
// display: none;
// }
.ql-container.ql-snow {
  border: 0;
  min-height: 2rem;
  .ql-editor {
    padding: 1rem;
    font-size: 1rem;
    line-height: 1.25rem;
    word-break: break-all;
    background-color: #fff;
    box-shadow: 0 1px 5px #ddd;
    max-width: 863px;
    min-height: 1200px;
    margin: 0 auto;
  }
}
.ql-toolbar.ql-snow {
  border: none;
}

.scroll .toolbar-shadow {
  visibility: visible;
  position: absolute;
  top: 2.9rem;
  width: 100%;
  z-index: 200;
  height: 0.1rem;
  background: #e9ecef;
  box-shadow: 0 0.1rem 0.1rem #e9ecef;
}
.toolbar {
  display: flex;
  height: 3rem;
  width: 100%;
  justify-content: center;
  align-items: center;
  .toolbar-button {
    opacity: 0.8;
    display: flex;
    justify-content: center;
    align-items: center;
    width: 1.5rem;
    height: 1.5rem;
    margin: 0 0.5rem;
    &:hover {
      opacity: 1;
      background: rgba(0, 0, 0, 0.08);
      border-radius: 0.25rem;
    }
  }
  .toolbar-group {
    display: flex;
    justify-content: center;
    align-items: center;
    .toolbar-group-button {
      opacity: 0.8;
      display: flex;
      justify-content: center;
      align-items: center;
      height: 1.5rem;
      &:hover {
        opacity: 1;
        background: rgba(0, 0, 0, 0.08);
        border-radius: 0.25rem;
      }
    }
    .left-button {
      width: 1.5rem;
    }
    .right-button {
      width: 1rem;
    }
  }
  .toolbar-group-active {
    opacity: 1;
    background: rgba(0, 0, 0, 0.08);
    border-radius: 0.25rem;
    .toolbar-group-button {
      opacity: 1;
      background: rgba(0, 0, 0, 0.08);
    }
    .left-button {
      border-radius: 0.25rem 0 0 0.25rem !important;
    }
    .right-button {
      border-radius: 0 0.25rem 0.25rem 0 !important;
    }
  }
}
</style>
