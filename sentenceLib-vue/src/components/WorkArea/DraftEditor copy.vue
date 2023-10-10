<template>
  <div class="draft-editor-wrapper">
    <div ref="draftRef" class="draft-editor" :contenteditable="true">
      <div
        v-for="(sentence, sentenceIndex) in sentences"
        :key="sentenceIndex"
        :class="[
          'se-sentence',
          {
            active: curActiveSentenceIdx === sentenceIndex,
            standard: sentence.standard,
            empty: !sentence.standard && sentence.unknownText == '',
          },
        ]"
        :contenteditable="false"
      >
        <!-- <span class="se-line-number">
          {{ sentenceIndex + 1 }}
        </span> -->

        <template v-if="sentence.standard">
          <div :class="`se-${word.type}`" v-for="(word, wordIndex) in sentence.children" :key="'we' + wordIndex">
            <template v-if="word.type === 'identifier'">
              <!-- <span v-if="word.text === ''" class="identifier-placeholder">{{ word.placeholder }}</span> -->
              <span
                :class="['identifier-input', { 'has-env': word.env !== null }, `identifier-${word.tag}`]"
                :data-placeholder="word.placeholder"
                :contenteditable="true"
                v-text="word.text"
                @input="handleInput($event, { type: word.tag, word })"
                @focus="handleIdentifierFocus($event, { type: word.tag, sentenceIndex })"
                @blur="handleIdentifierBlur($event, { sentenceIndex, wordIndex, word })"
                @keydown="handleKeydown($event, { sentenceIndex, type: word.tag, word })"
              >
              </span>
              <template v-if="word.params && word.env !== null">
                <template v-for="(param, paramIndex) in word.env.params">
                  <span>，{{ paramIndex === 0 ? "其中：" : "" }}</span>
                  <span class="action-param">{{ param.paramName }}</span>
                  <span>为</span>
                  <span
                    class="identifier-input identifier-param"
                    :data-placeholder="param.paramName"
                    :contenteditable="true"
                    v-text="param.value"
                    @input="handleInput($event, { type: 'param', param })"
                    @focus="handleParamFocus($event, { sentenceIndex })"
                    @blur="handleParamBlur($event, { param })"
                    @keydown="handleKeydown($event, { sentenceIndex, type: 'param', param })"
                    @compositionstart="handleCompositionStart()"
                    @compositionend="handleCompositionEnd($event, { type: 'param', param })"
                  >
                  </span>
                </template>
              </template>
            </template>
            <template v-else>
              <span>{{ word.text }}</span>
            </template>
          </div>
        </template>
        <template v-else>
          <span
            class="se-unknown"
            @input="handleInput($event, { type: 'scriptTemplate' })"
            @focus="handleUnknownFocus($event, { sentenceIndex })"
            @blur="handleUnknownBlur($event, { sentenceIndex })"
            @keydown="handleKeydown($event, { sentenceIndex, type: 'scriptTemplate' })"
            :contenteditable="true"
            v-html="sentence.unknownText"
          ></span>
        </template>
        <div class="sentence-menu-wrapper">
          <a-popover trigger="click" placement="right">
            <template slot="content">
              <ul class="sentence-tools">
                <li class="sentence-tool">调试</li>
                <li
                  v-if="sentenceIndex !== sentences.length - 1"
                  class="sentence-tool"
                  @click="handleDeleteCurSentence($event, { sentenceIndex })"
                >
                  删除
                </li>
                <li
                  v-if="canAddSentenceBefore(sentenceIndex)"
                  class="sentence-tool"
                  @click="handleAddSentenceBefore($event, { sentenceIndex })"
                >
                  在上方插入
                </li>
                <li
                  v-if="canAddSentenceAfter(sentenceIndex)"
                  class="sentence-tool"
                  @click="handleAddSentenceAfter($event, { sentenceIndex })"
                >
                  在下方插入
                </li>
              </ul>
            </template>
            <a-tooltip placement="top">
              <template slot="title">
                <span>打开列表</span>
              </template>
              <div class="sentence-menu">
                <HamburgerButton theme="outline" size="18" fill="var(--tool-color)" />
              </div>
            </a-tooltip>
          </a-popover>
        </div>
      </div>
    </div>
    <!-- <div
      class="mention-wrapper"
      v-if="mention.mentionVisible"
      :style="{
        left: `calc(${mention.mentionPosition[0]}px + 0.25rem)`,
        top: `calc(${mention.mentionPosition[1]}px + 0.25rem)`,
      }"
    >
      <div class="mention-header">
        语境列表
      </div>
      <div class="mention-list" ref="menuRef">
        <div
          :class="['mention-item', { active: mention.selectedMentions[0].id === item.id }]"
          v-for="item in mention.envMentions"
          :key="item.id"
        >
          <span class="mention-item-type"> {{ item.label }}</span>
          <span class="mention-item-name">{{ item.name }} </span>
          <span class="mention-item-key"> {{ item.id }}</span>
        </div>
      </div>
    </div> -->
  </div>
</template>
<script>
import { PlayOne, DeleteFour, HamburgerButton } from "@icon-park/vue";
import { parseSentence } from "./model/parse";
import Utils from "common/utils";
import { customEnvs } from "./model/env";
export default {
  name: "DraftEditor",
  components: { PlayOne, DeleteFour, HamburgerButton },
  props: {
    sentences: {
      type: Array,
      default: [],
    },
    envs: {
      type: Array,
      default: [],
    },
    variables: {
      type: Array,
      default: [],
    },
    scriptTemplates: {
      type: Array,
      default: [],
    },
    curActiveSentenceIdx: {
      type: Number,
      default: -1,
    },
  },
  data() {
    return {
      mention: {
        mentionVisible: false,
        mentionPosition: [-1000, -1000],
        selectedMentions: [],
        envMentions: [],
      },
      // #嵌入字符串的语境处理
      integration: {
        status: false,
        content: "",
        lock: false,
      },
      nonstandard: {
        text: "",
        lastStartOffset: -1,
        lastEndOffset: -1,
        lastInput: "",
        standard: false,
      },
    };
  },
  computed: {},
  beforeCreate() {},
  created() {},
  mounted() {},
  methods: {
    initNonstandard() {
      this.nonstandard = {
        text: "",
        lastStartOffset: -1,
        lastEndOffset: -1,
        lastInput: "",
        standard: false,
      };
    },
    // initEnvMentions(tag, input) {
    //   // 处理语境提示
    //   if (!this.envs.length) return;
    //   this.mention.envMentions = [];
    //   this.mention.envMentions = customEnvs(tag, this.envs, this.scriptTemplates, input);
    //   if (this.mention.envMentions.length == 0) {
    //     // 没有满足的就不显示
    //     this.mention.mentionVisible = false;
    //   } else {
    //     // 默认选中第一个
    //     this.mention.selectedMentions = [this.mention.envMentions[0]];
    //     // 显示mention
    //     if (!this.mention.mentionVisible) {
    //       this.mention.mentionVisible = true;
    //     }
    //   }
    // },
    canAddSentenceBefore(sentenceIndex) {
      const sentence = this.sentences[sentenceIndex];
      if (!sentence.standard && sentence.unknownText === "") {
        return false;
      }
      if (sentenceIndex > 0) {
        const lastSentence = this.sentences[sentenceIndex - 1];
        if (!lastSentence.standard && lastSentence.unknownText === "") {
          return false;
        }
      }
      return true;
    },
    canAddSentenceAfter(sentenceIndex) {
      const sentence = this.sentences[sentenceIndex];
      if (!sentence.standard && sentence.unknownText === "") {
        return false;
      }
      if (sentenceIndex < this.sentences.length - 1) {
        const nextSentence = this.sentences[sentenceIndex + 1];
        if (!nextSentence.standard && nextSentence.unknownText === "") {
          return false;
        }
      }
      return true;
    },
    addSentence(script, standard, sentenceIndex) {
      let sentence = null;
      if (standard) {
        sentence = Object.assign(parseSentence(script), { standard: true });
      } else {
        sentence = { type: "sentence", unknownText: script, standard: false };
      }
      this.sentences[sentenceIndex] = sentence;
      if (this.sentences.length - 1 === sentenceIndex && script !== "")
        this.sentences.push({ type: "sentence", unknownText: "", standard: false });
      this.$emit("update:sentences", [...this.sentences]);
    },
    handleDeleteCurSentence(e, { sentenceIndex }) {
      this.sentences.splice(sentenceIndex, 1);
      this.$emit("update:sentences", [...this.sentences]);
    },
    handleParamFocus(e, { sentenceIndex }) {
      this.$emit("update:curActiveSentenceIdx", sentenceIndex);
    },
    handleParamBlur(e, { param }) {
      param.value = param.temp;
      this.$emit("update:curActiveSentenceIdx", -1);

      // 关闭嵌入语境
      this.integration.status = false;
      this.integration.content = "";
    },
    handleKeydown(e, { sentenceIndex, type, word, param }) {
      switch (e.key) {
        // case "ArrowUp":
        //   this.handleKeydownArrowUp(e);
        //   break;
        // case "ArrowDown":
        //   this.handleKeydownArrowDown(e);
        //   break;
        case "Enter":
          this.handleKeydownEnter(e, { sentenceIndex, type, word, param });
          break;
        // case " ":
        //   this.handleKeydownSpace(e, { sentenceIndex, type, word });
        //   break;
        default:
          break;
      }
    },
    // handleIdentifierFocus(e, { type, sentenceIndex }) {
    //   this.$emit("update:curActiveSentenceIdx", sentenceIndex);
    //   if (e.target.textContent !== "") {
    //     let rect = e.target.getBoundingClientRect();
    //     this.mention.mentionPosition = [rect.x - 4, rect.y + rect.height + 4];
    //     this.initEnvMentions(type, e.target.textContent);
    //   }
    // },
    handleIdentifierBlur(e, { sentenceIndex, wordIndex, word }) {
      // this.mention.mentionVisible = false;
      // 设置句 变量没有对重复的进行处理
      if (word.temp !== "" && (word.tag === "variable" || word.tag === "value")) {
        let variableWord = null;
        let valueWord = null;
        this.sentences[sentenceIndex].children.forEach((item) => {
          if (item.type === "identifier" && item.tag === "variable") {
            variableWord = item;
          }
          if (item.type === "identifier" && item.tag === "value") {
            valueWord = item;
          }
        });
        let index = this.variables.findIndex((item) => item.key === variableWord.temp);
        console.log(index);
        if (index >= 0) {
          this.variables.splice(index, 1);
        }
        this.variables.push({ key: variableWord.temp, value: valueWord.temp, type: "static" });
        this.$emit("update:variables", [...this.variables]);
      }
      // 使用$set，解决修改二维对象数组的对象的属性，无法触发视图更新
      // 先 设置为 空，再设置为temp的值，防止text值不更新，导致视图不更新
      // this.$set(word, "text", "");
      // this.$nextTick(() => {
      //   this.$set(word, "text", word.temp);
      // });
      // this.$emit("update:sentences", [...this.sentences]);
      // this.$emit("update:curActiveSentenceIdx", -1);
    },
    handleUnknownFocus(e, { sentenceIndex }) {
      this.$emit("update:curActiveSentenceIdx", sentenceIndex);
    },
    handleUnknownBlur(e, { sentenceIndex }) {
      if (this.nonstandard.standard) {
        this.addSentence(this.mention.selectedMentions[0].content, true, sentenceIndex);
        this.mention.mentionVisible = false;
      } else {
        this.addSentence(e.target.textContent, false, sentenceIndex);
        this.mention.mentionVisible = false;
      }
      this.initNonstandard();
      this.$emit("update:curActiveSentenceIdx", -1);
    },
    // 上下键切换mention-list
    // changeMenuItem(op) {
    //   const activeId = this.mention.selectedMentions[0].id;
    //   let idx = this.mention.envMentions.findIndex((item) => item.id === activeId);
    //   if (idx < 0) return;
    //   const len = this.mention.envMentions.length;
    //   if (op == 1) {
    //     idx = (idx + 1) % len;
    //   } else {
    //     idx = (idx + len - 1) % len;
    //   }
    //   this.mention.selectedMentions = [this.mention.envMentions[idx]];
    //   // 特殊处理 第一个和最后一个元素的显示
    //   if (idx == 0) {
    //     this.$refs.menuRef.scrollTop = 0;
    //   } else if (idx === len - 1) {
    //     this.$refs.menuRef.scrollTop = this.$refs.menuRef.scrollHeight;
    //   }
    //   // 显示当前选中的mention-item
    //   // scrollIntoView使用 true 父元素滚到头部对齐，使用false 父元素滚到底部对齐
    //   else {
    //     this.$refs.menuRef.querySelector(".active").scrollIntoView(op > 0);
    //   }
    // },
    handleInput(e, { type, word, param }) {
      if (this.integration.lock) {
        return;
      }
      // let newInput = e.target.textContent;
      if (type === "scriptTemplate") {
        // 非规范句 选择语境内容
        this.nonstandard.text = newInput;
        //
        let range = window.getSelection().getRangeAt(0);
        this.nonstandard.lastStartOffset = range.startOffset;
        this.nonstandard.lastEndOffset = range.endOffset;
        this.nonstandard.lastInput = e.data;
        if (e.data !== null) {
          newInput = e.data;
        }
      } else if (type === "param") {
        param.temp = e.target.textContent;
        console.log("last");
        if (e.data && e.data.endsWith("#")) {
          this.integration.status = true;
          this.integration.content = "";
          return;
        }
        if (!this.integration.status) {
          return;
        }
        if (e.data) {
          // 输入值是正常的，并且已经激活#开始输入语境内容时
          this.integration.content += e.data;
          newInput = this.integration.content;
        } else if (!e.data && e.inputType === "deleteContentBackward") {
          // 输入内容为空时退格，关闭嵌入语境
          if (this.integration.content === "") {
            this.integration.status = false;
          }
          // 使用退格，并且已经开启嵌入语境
          this.integration.content = this.integration.content.slice(0, -1);
          newInput = this.integration.content;
        }
      } else {
        word.temp = newInput;
        word.env = null;
      }
      // if (newInput === "") {
      //   this.mention.mentionVisible = false;
      //   return;
      // }
      // let rect = e.target.getBoundingClientRect();
      // this.mention.mentionPosition = [rect.x - 4, rect.y + rect.height + 4];
      // this.initEnvMentions(type, newInput);
    },
    setFocusToNextInputArea(curInputArea, sentenceIndex) {
      const sentenceNodes = this.$refs.draftRef.querySelectorAll(".se-sentence");
      const identifierNodes = sentenceNodes[sentenceIndex].querySelectorAll(".identifier-input");
      // 当unknown选中句型时，会替换节点，传入的curInputArea执行findIndex结果为-1，直接定位第一个(0)的位置
      const curInputAreaIndex = [...identifierNodes].findIndex((item) => item === curInputArea);
      // 找到当前句下一个输入域
      if (curInputAreaIndex !== identifierNodes.length - 1) {
        identifierNodes[curInputAreaIndex + 1].focus();
      }
      // 找到下一规范句的第一个输入域
      else if (sentenceIndex !== this.sentences.length - 1 && this.sentences[sentenceIndex + 1].standard) {
        const nextIdentifierNodes = sentenceNodes[sentenceIndex + 1].querySelectorAll(".identifier-input");
        nextIdentifierNodes[0].focus();
      }
      // 找到下一非规范句的第一个输入域
      else if (sentenceIndex !== this.sentences.length - 1 && !this.sentences[sentenceIndex + 1].standard) {
        sentenceNodes[sentenceIndex + 1].querySelector(".se-unknown").focus();
      } else {
        return;
      }
    },
    handleKeydownEnter(e, { sentenceIndex, type, word, param }) {
      if (this.mention.mentionVisible) {
        // 非规范选择语境
        if (type === "scriptTemplate" && this.mention.selectedMentions[0].type === "scriptTemplate") {
          this.nonstandard.standard = true;
          e.preventDefault();
          e.target.blur();
          // 选择句型之后，sentences就会修改，视图就会修改，使用setTimeout宏任务会比视图渲染慢，
          // 保证视图修改之后再获取dom
          setTimeout(() => {
            this.setFocusToNextInputArea(e.target, sentenceIndex);
          }, 0);
        }
        // 非规范选择语境
        else if (type === "scriptTemplate") {
          this.mention.mentionVisible = false;
          const temp = this.nonstandard.text.split("");
          const startOffset = this.nonstandard.lastStartOffset - this.nonstandard.lastInput.length;
          const lastInputLength = this.nonstandard.lastInput.length;
          const selectedMentionName = this.mention.selectedMentions[0].name;
          temp.splice(startOffset, lastInputLength, selectedMentionName);
          e.target.innerHTML = temp.join("");
          console.log([e.target]);
          e.preventDefault();
          // 插入语境后，设置光标位置
          const selection = window.getSelection();
          const range = document.createRange();
          range.setStart(e.target.firstChild, startOffset + selectedMentionName.length);
          range.setEnd(e.target.firstChild, startOffset + selectedMentionName.length);
          selection.removeAllRanges();
          selection.addRange(range);
        }
        // 规范句型中参数输入域的嵌入语境选择
        else if (type === "param") {
          this.mention.mentionVisible = false;
          let range = window.getSelection().getRangeAt(0);
          const startOffset = range.startOffset;
          const endOffset = range.endOffset;
          const selectedMentionName = this.mention.selectedMentions[0].name;
          // 选区区间长度为0
          if (startOffset === endOffset) {
            const temp = e.target.textContent.split("");
            temp.splice(
              startOffset - this.integration.content.length,
              this.integration.content.length,
              selectedMentionName + "#"
            );
            // innerHtml无效
            e.target.innerHTML = temp.join("");
            param.temp = temp.join("");
          }
          // 插入语境后，设置光标位置
          const selection = window.getSelection();
          const newRange = document.createRange();
          newRange.setStart(
            e.target.firstChild,
            startOffset - this.integration.content.length + selectedMentionName.length + 1
          );
          newRange.setEnd(
            e.target.firstChild,
            startOffset - this.integration.content.length + selectedMentionName.length + 1
          );
          selection.removeAllRanges();
          selection.addRange(newRange);
          e.preventDefault();
          // e.target.blur();
        } else {
          word.temp = this.mention.selectedMentions[0].name;
          word.env = this.mention.selectedMentions[0];
          e.preventDefault();
          e.target.blur();
          setTimeout(() => {
            this.setFocusToNextInputArea(e.target, sentenceIndex);
          }, 0);
        }
      } else {
        if (type === "scriptTemplate") {
          this.nonstandard.standard = false;
        } else if (type === "param") {
          setTimeout(() => {
            this.setFocusToNextInputArea(e.target, sentenceIndex);
          }, 0);
        } else {
          word.env = null;
          setTimeout(() => {
            this.setFocusToNextInputArea(e.target, sentenceIndex);
          }, 0);
        }
        e.preventDefault();
        e.target.blur();
      }
    },
    // handleKeydownArrowUp(e) {
    //   if (this.mention.mentionVisible) {
    //     this.changeMenuItem(-1);
    //     e.preventDefault();
    //   }
    // },
    // handleKeydownArrowDown(e) {
    //   if (this.mention.mentionVisible) {
    //     this.changeMenuItem(1);
    //     e.preventDefault();
    //   }
    // },
    // handleKeydownSpace(e) {
    //   e.preventDefault();
    // },
    handleAddSentenceBefore(e, { sentenceIndex }) {
      const sentence = { type: "sentence", unknownText: "", standard: false };
      this.sentences.splice(sentenceIndex, 0, sentence);
      this.$emit("update:sentences", [...this.sentences]);
    },
    handleAddSentenceAfter(e, { sentenceIndex }) {
      const sentence = { type: "sentence", unknownText: "", standard: false };
      this.sentences.splice(sentenceIndex + 1, 0, sentence);
      this.$emit("update:sentences", [...this.sentences]);
    },
    handleCompositionStart() {
      this.integration.lock = true;
    },
    handleCompositionEnd(e, ctx) {
      this.integration.lock = false;
      // 在调用
      this.handleInput(e, ctx);
    },
  },
  beforeDestroy() {},
};
</script>
<style lang="scss" scoped>
.draft-editor-wrapper {
  width: 100%;
  position: relative;
  overflow-y: auto;
  height: 100%;
  padding: 0.5rem 0;
  &::-webkit-scrollbar {
    width: 0px;
  }
}
.draft-editor {
  border: 0;
  caret-color: #000;
  font-size: 0.875rem;
  background: transparent;
  outline: none;
  word-wrap: break-word;
  word-break: break-all;
  color: #2a2b2e;
  line-height: 1.6rem;
  letter-spacing: 0.125rem;
  min-height: 5rem;
  padding: 0;
  height: 100%;
}
.action-param {
  font-style: italic;
}
</style>
