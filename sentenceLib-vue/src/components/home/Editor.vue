<template>
  <codemirror ref="scriptCm" v-model="scripts" :options="cmOptions" @ready="onCmReady"></codemirror>
</template>

<script>
import { CodeMirror, codemirror } from "vue-codemirror";
import "codemirror/addon/hint/show-hint.css";
require("codemirror/addon/hint/show-hint");
import "codemirror/addon/display/autorefresh";
export default {
  name: "Editor",
  props: {
    envs: {
      type: Array,
      default: () => [],
    },
  },
  watch: {
    envs(next, prev) {},
  },
  components: { codemirror },
  data() {
    return {
      CodeMirrorEditor: null,
      cmOptions: {
        value: "",
        lineNumbers: true, //显示行号
        smartIndent: true,
        autofocus: true,
        autoRefresh: true,
        historyEventDelay: 500,
        indentWithTabs: true,
        tabSize: 4,
        indentUnit: 4,
        line: true,
        matchBrackets: true,
        autoCloseBrackets: true,
        styleActiveLine: true,
        foldGutter: true,
        lint: false,
        gutters: [
          "CodeMirror-foldgutter",
          "CodeMirror-lint-markers",
          {
            className: "CodeMirror-checkbox",
            style: "width:20px;left:0px",
          },
          {
            className: "CodeMirror-linenumbers",
            style: "width:20px;left:20px",
          },
        ],
        extraKeys: { "Ctrl-Space": "autocomplete" }, //自定义快捷键
        // extraKeys: {"Ctrl": "autocomplete"},//ctrl可以弹出选择项
        hintOptions: {
          completeSingle: false,
          alignWithWord: false,
          hint: this.getHints,
        },
        lineWrapping: true,
      },
      scripts: `令招标工具参数的用户ID为发包方跟单员的用户ID，公司ID为发包方业务区的公司ID。\n令招投标工具的招标书填写部件的运行参数为招标工具参数。\n令工具按钮的链接是招投标工具的招标书填写部件，文字提示是{填写招标书}，显示方式是{当前窗口显示}。\n令剧情工具运行参数的收集数据是{招标书编号、项目编号}，点击事件是{剧情状态由招标书填写:未填写转化为招标书填写:已填写}。\n令剧情跳转工具的运行参数是剧情工具运行参数。\n令剧情按钮的链接是剧情跳转工具，文字提示是{填写招标书已完成}，显示方式是{当前窗口显示}。\n令消息的文字内容为{请发包方跟单员在招标平台中创建项目，填写招标书。`,
      errorWidgetList: [],
      isWaiting: false,
      selectedScripts: [],
      selection: { from: {}, to: {} },
    };
  },
  computed: {
    hintCode() {
      //return [].concat(this.codeHints, this.hintColumnCode)
      const codeHints = [];
      for (let i = 0; i < this.envs[0].children.length; i++) {
        const item = this.envs[0].children[i];
        codeHints.push({
          text: item.title,
          displayText: item.title,
        });
      }
      for (let i = 0; i < this.envs[1].children.length; i++) {
        const item = this.envs[1].children[i];
        codeHints.push({
          text: item.title,
          displayText: item.title,
        });
      }

      return codeHints;
    },
  },
  methods: {
    makeCheckBox(className, status) {
      let checkbox = document.createElement("input");
      checkbox.type = "checkbox";
      checkbox.classList.add(className);
      checkbox.style.margin = "0 0 0 5px";
      checkbox.style.verticalAlign = "-2px";
      checkbox.checked = status;
      return checkbox;
    },
    makeError(content) {
      let error = document.createElement("div");
      error.innerHTML = content;
      error.className = "inline-editor-error";
      return error;
    },
    onCmReady() {
      this.CodeMirrorEditor = this.$refs.scriptCm.codemirror;
      const cmed = this.CodeMirrorEditor.doc;
      cmed.eachLine(0, cmed.lastLine() + 1, (line) => {
        const gutter = "CodeMirror-checkbox";
        const status = cmed.lineInfo(line).gutterMarkers ? cmed.lineInfo(line).gutterMarkers[gutter].checked : true;
        cmed.setGutterMarker(line, gutter, this.makeCheckBox("debugCheckbox", status));
      });
      // 当输入内容发生变化,更新gutter
      this.CodeMirrorEditor.on("change", (cm, { from }) => {
        cm.doc.eachLine(from.line, cm.doc.lastLine() + 1, (line) => {
          const gutter = "CodeMirror-checkbox";
          const status = cm.doc.lineInfo(line).gutterMarkers
            ? cm.doc.lineInfo(line).gutterMarkers[gutter].checked
            : true;
          cm.doc.setGutterMarker(line, gutter, this.makeCheckBox("debugCheckbox", status));
        });
      });

      this.CodeMirrorEditor.on("cursorActivity", (cm) => {
        this.CodeMirrorEditor.showHint();
        this.selection.to = cm.getCursor();
        if (this.CodeMirrorEditor.getSelection().length === 0) {
          this.selection.from = cm.getCursor();
        }
        //获取所选的词汇
        var A1 = this.CodeMirrorEditor.getCursor().line;
        var A2 = this.CodeMirrorEditor.getCursor().ch;
        var B1 = this.CodeMirrorEditor.findWordAt({ line: A1, ch: A2 }).anchor.ch;
        var B2 = this.CodeMirrorEditor.findWordAt({ line: A1, ch: A2 }).head.ch;
        let selectWord = this.CodeMirrorEditor.getRange({ line: A1, ch: B1 }, { line: A1, ch: B2 });
        //console.log(selectWord)
        // SET_SCRIPT_SELECTED:(state,selectionInfo)=>{
        //     state.scriptSelected.isSelected=selectionInfo.isSelected;
        //     state.scriptSelected.text=selectionInfo.text;
        // },
        let selectionInfo = {
          isSelected: true,
          text: selectWord,
        };
        console.log(selectionInfo);
        // this.$store.commit("SET_SCRIPT_SELECTED", selectionInfo);
        // console.log(this.$store.getters.getScriptSelected.text);
      });
    },
    //获取提示
    getHints(cm, option) {
      return new Promise((resolve) => {
        // console.log(option);
        //console.log("开始获取提示！")
        setTimeout(() => {
          //识别中文
          let WORD = /^[\u4E00-\u9FA5]{1,5}$/;
          //当没有使用ESLINT时，需要加\
          //let WORD = /[\w\[\]\"\.$]+/
          let cursor = cm.getCursor(),
            curLine = cm.getLine(cursor.line);
          let start = cursor.ch,
            end = cursor.ch;
          while (start && WORD.test(curLine.charAt(start - 1))) {
            console.log(start);
            --start;
          }
          let curWord = start != end && curLine.slice(start, end);
          console.log(curWord);
          let list = [],
            isDefinedObj = false;
          if (curWord) {
            //开始获取提示
            if (list.length === 0) {
              let dotIndex = curWord.lastIndexOf(".");
              let memberStr = curWord.slice(dotIndex + 1);
              let comList = this.hintCode;

              for (let j = 0; j < comList.length; j++) {
                if (comList[j].displayText.toLowerCase().lastIndexOf(memberStr.toLowerCase(), 0) === 0) {
                  list.push(comList[j]);
                }
              }
              isDefinedObj = true;
            }
          }
          console.log(list);
          if (isDefinedObj) {
            return resolve({
              list: list,
              from: CodeMirror.Pos(cursor.line, start),
              to: CodeMirror.Pos(cursor.line, end),
            });
          } else {
            return resolve({
              list: list,
              from: CodeMirror.Pos(cursor.line, start + 1),
              to: CodeMirror.Pos(cursor.line, end),
            });
          }
        }, 100);
      });
    },
  },
  mounted() {
    // this.CodeMirrorEditor.doc.setGutterMarker(0, "CodeMirror-checkbox", this.makeCheckBox("debugCheckbox", false));
  },
};
</script>

<style lang="scss">
.vue-codemirror {
  height: 100%;
  width: 100%;
  font-size: 1.125rem;
  line-height: 1.5rem;
  .CodeMirror {
    width: 100%;
    height: 100%;
    overflow: hidden;
  }
  .CodeMirror-gutters {
    background: #fff;
  }
}
.CodeMirror-hints {
  min-width: 18.75rem !important;
  padding: 0.25rem 0 !important;
  .CodeMirror-hint {
    border-radius: 2px;
    height: 1.5rem;
    line-height: 1.5rem;
    white-space: pre;
    color: black;
    cursor: pointer;
  }
}
li.CodeMirror-hint-active {
  background: #e6f7ff !important;
  color: rgba(0, 0, 0, 0.65);
  font-weight: 600;
}
</style>
