<template>
  <div class="analyse-editor-wrapper">
    <div ref="analyseRef" class="analyse-editor" :contenteditable="true">
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
        <span class="se-line-number">
          {{ sentenceIndex + 1 }}
        </span>
        <template v-if="sentence.standard">
          <div :class="`se-${word.type}`" v-for="(word, wordIndex) in sentence.children" :key="'we' + wordIndex">
            <template v-if="word.type === 'identifier'">
              <!-- <span v-if="word.text === ''" class="identifier-placeholder">{{ word.placeholder }}</span> -->
              <span
                :class="['identifier-input', { 'has-env': word.env !== null }]"
                :data-placeholder="word.placeholder"
                :contenteditable="true"
                v-text="word.text"
                @input="handleDebounceInput($event, word.tag, word)"
                @focus="handleIdentifierFocus($event, word.tag, sentenceIndex)"
                @blur="handleIdentifierBlur($event, word, sentenceIndex, wordIndex)"
                @keydown="handleKeydown($event, sentenceIndex, word.tag, word)"
              >
              </span>
              <template v-if="word.params && word.env !== null">
                <template v-for="(param, paramIdx) in word.env.params">
                  <span>，{{ param.paramName }}为</span>
                  <span
                    class="identifier-input"
                    :data-placeholder="param.paramName"
                    :contenteditable="true"
                    v-text="param.value"
                    @input="handleParamInput($event, param)"
                    @blur="handleParamBlur($event, param)"
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
            @input="handleDebounceInput($event, 'scriptTemplate')"
            @focus="handleUnknownFocus($event, 'scriptTemplate', sentenceIndex)"
            @blur="handleUnknownBlur($event, sentenceIndex)"
            @keydown="handleKeydown($event, sentenceIndex, 'scriptTemplate')"
            :contenteditable="true"
            v-html="sentence.unknownText"
          ></span>
        </template>
      </div>
    </div>
    <div
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
    </div>
  </div>
</template>
<script>
import { parseSentence } from "./model/parse";
import Utils from "common/utils";

export default {
  name: "AnalyseEditor",
  components: {},
  props: {
    sentences: {
      type: Array,
      default: [],
    },
    envs: {
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
    initEnvMentions(tag, input) {
      // 处理语境提示
      if (!this.envs.length) return;
      this.mention.envMentions = [];
      if (tag === "people" || tag === "scriptTemplate") {
        this.envs.forEach((env) => {
          env.bandUsers.forEach((item) => {
            this.mention.envMentions.push({ id: item.userId, name: item.userName, type: "user", label: "用户" });
          });
          env.bandRoles.forEach((item) => {
            this.mention.envMentions.push({ id: item.roleId, name: item.roleName, type: "role", label: "角色" });
          });
        });
      }
      if (tag === "band" || tag === "scriptTemplate") {
        this.envs.forEach((env) => {
          this.mention.envMentions.push({
            id: env.bandInfo[0].bandId,
            name: env.bandInfo[0].name,
            type: "band",
            label: "帮区",
          });
        });
      }
      if (tag === "tool" || tag === "scriptTemplate") {
        this.envs.forEach((env) => {
          env.bandTools.forEach((item) => {
            this.mention.envMentions.push({ id: item.toolId, name: item.toolName, type: "tool", label: "工具" });
          });
        });
      }
      if (tag === "action" || tag === "scriptTemplate") {
        this.envs.forEach((env) => {
          env.toolActions.forEach((item) => {
            this.mention.envMentions.push({
              id: item.actionId,
              name: item.actionName,
              type: "action",
              label: "部件",
              params: item.params.map((param) => ({ ...param, value: "", temp: "" })),
            });
          });
        });
      }
      if (tag === "scriptTemplate") {
        this.mention.envMentions.push(
          ...this.scriptTemplates.map((st) => ({
            id: st.id,
            name: st.description,
            content: st.content,
            example: st.example,
            type: "scriptTemplate",
            label: "句型",
          }))
        );
      }
      if (input !== "")
        this.mention.envMentions = this.mention.envMentions.filter((item) => item.name.indexOf(input) !== -1);
      if (this.mention.envMentions.length == 0) {
        // 没有满足的就不显示
        this.mention.mentionVisible = false;
      } else {
        // 默认选中第一个
        this.mention.selectedMentions = [this.mention.envMentions[0]];
        // 显示mention
        if (!this.mention.mentionVisible) {
          this.mention.mentionVisible = true;
        }
      }
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
    handleParamInput(e, param) {
      param.temp = e.target.textContent;
    },
    handleParamBlur(param) {
      param.value = param.temp;
    },
    handleKeydown(e, sentenceIndex, type, word) {
      if (this.mention.mentionVisible) {
        if (e.key === "ArrowUp") {
          this.changeMenuItem(-1);
          e.preventDefault();
        } else if (e.key === "ArrowDown") {
          this.changeMenuItem(1);
          e.preventDefault();
        } else if (e.key === "Enter") {
          // 不规范选择句型
          if (type === "scriptTemplate" && this.mention.selectedMentions[0].type === "scriptTemplate") {
            this.nonstandard.standard = true;
            e.preventDefault();
            e.target.blur();
          }
          // 不规范选择语境
          else if (type === "scriptTemplate") {
            this.mention.mentionVisible = false;
            const temp = this.nonstandard.text.split("");
            const startOffset = this.nonstandard.lastStartOffset - this.nonstandard.lastInput.length;
            const lastInputLength = this.nonstandard.lastInput.length;
            const selectedMentionName = this.mention.selectedMentions[0].name;
            temp.splice(startOffset, lastInputLength, selectedMentionName);
            e.target.innerHTML = temp.join("");
            e.preventDefault();
            // 插入语境后，设置光标位置
            const selection = window.getSelection();
            const range = document.createRange();
            range.setStart(e.target.firstChild, startOffset + selectedMentionName.length);
            range.setEnd(e.target.firstChild, startOffset + selectedMentionName.length);
            selection.removeAllRanges();
            selection.addRange(range);
          }
          // 规范句型中的语境选择
          else {
            word.temp = this.mention.selectedMentions[0].name;
            word.env = this.mention.selectedMentions[0];
            e.preventDefault();
            e.target.blur();
          }
        }
      } else {
        if (e.key === "Enter") {
          if (type === "scriptTemplate") {
            this.nonstandard.standard = false;
          } else {
            word.env = null;
          }
          e.preventDefault();
          e.target.blur();
        }
      }
    },
    handleIdentifierFocus(e, tag, sentenceIndex) {
      this.$emit("update:curActiveSentenceIdx", sentenceIndex);
    },
    handleIdentifierBlur(e, word, sentenceIndex, wordIndex) {
      this.mention.mentionVisible = false;
      // 添加参数列表
      this.$set(this.sentences[sentenceIndex].children[wordIndex], "text", "");
      this.$nextTick(() => {
        this.$set(this.sentences[sentenceIndex].children[wordIndex], "text", word.temp);
      });
      // setTimeout(() => {
      //   this.setFocusPosition(true, sentenceIndex, wordIndex + 1);
      // }, 0);
      this.$emit("update:sentences", [...this.sentences]);
      this.$emit("update:curActiveSentenceIdx", -1);
    },
    handleUnknownFocus(e, tag, sentenceIndex) {
      this.$emit("update:curActiveSentenceIdx", sentenceIndex);
    },
    handleUnknownBlur(e, sentenceIndex) {
      if (this.nonstandard.standard) {
        this.addSentence(this.mention.selectedMentions[0].content, true, sentenceIndex);
        this.mention.mentionVisible = false;
        // blur时候的e.target和最终获取的dom的不是一个，
        // 因为回车创建了一个有规范的句型，把sentences的值修改了，
        // 视图更新了。
        setTimeout(() => {
          // 把新创建的句型的第一个identifier聚焦。
          this.setFocusPosition(true, sentenceIndex, 0);
        }, 0);
      } else {
        const text = e.target.textContent;
        this.addSentence(e.target.textContent, false, sentenceIndex);
        this.mention.mentionVisible = false;
        if (text !== "") {
          setTimeout(() => {
            this.setFocusPosition(false, sentenceIndex + 1);
          }, 0);
        }
      }
      this.initNonstandard();
      this.$emit("update:curActiveSentenceIdx", -1);
    },
    // 上下键切换mention-list
    changeMenuItem(op) {
      const activeId = this.mention.selectedMentions[0].id;
      let idx = this.mention.envMentions.findIndex((item) => item.id === activeId);
      if (idx < 0) return;
      const len = this.mention.envMentions.length;
      if (op == 1) {
        idx = (idx + 1) % len;
      } else {
        idx = (idx + len - 1) % len;
      }
      this.mention.selectedMentions = [this.mention.envMentions[idx]];
      // 特殊处理 第一个和最后一个元素的显示
      if (idx == 0) {
        this.$refs.menuRef.scrollTop = 0;
      } else if (idx === len - 1) {
        this.$refs.menuRef.scrollTop = this.$refs.menuRef.scrollHeight;
      }
      // 显示当前选中的mention-item
      // scrollIntoView使用 true 父元素滚到头部对齐，使用false 父元素滚到底部对齐
      else {
        this.$refs.menuRef.querySelector(".active").scrollIntoView(op > 0);
      }
    },
    handleDebounceInput: Utils.debounce(function(e, type, word) {
      let newInput = e.target.textContent;
      if (type === "scriptTemplate") {
        this.nonstandard.text = newInput;
        let range = window.getSelection().getRangeAt(0);
        this.nonstandard.lastStartOffset = range.startOffset;
        this.nonstandard.lastEndOffset = range.endOffset;
        this.nonstandard.lastInput = e.data;
        if (e.data !== null) {
          newInput = e.data;
        }
      } else {
        word.temp = newInput;
        word.env = null;
      }
      if (newInput === "") {
        this.mention.mentionVisible = false;
        return;
      }
      let rect = e.target.getBoundingClientRect();
      this.mention.mentionPosition = [rect.x - 4, rect.y + rect.height + 4];
      this.initEnvMentions(type, newInput);
    }, 200),
    // 设置光标聚焦位置
    setFocusPosition(standard, sentenceIndex, wordIndex) {
      console.log(standard);
      if (standard) {
        // 使第sentenceIndex个句子的第wordIndex个identifier聚焦
        const sentenceDom = this.$refs.analyseRef.querySelectorAll(".se-sentence")[sentenceIndex];
        for (let i = wordIndex; i < sentenceDom.childNodes.length; i++) {
          const wordDom = sentenceDom.childNodes[i];
          if (wordDom.classList.contains("se-identifier")) {
            wordDom.childNodes[0].focus();
            break;
          }
        }
      } else {
        // 使下一句话聚焦
        const sentenceDom = this.$refs.analyseRef.querySelectorAll(".se-sentence")[sentenceIndex];
        sentenceDom.childNodes[0].focus();
      }
    },
  },
  beforeDestroy() {},
};
</script>
<style lang="scss" scoped>
.analyse-editor-wrapper {
  width: 100%;
  position: relative;
  overflow-y: auto;
  height: 100%;
  padding: 0.5rem 0;
  &::-webkit-scrollbar {
    width: 0px;
  }
}
.analyse-editor {
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
</style>
