<template>
  <div class="se-inputarea">
    <span
      :class="[
        'inputarea-input',
        { 'has-env': area.children.length && area.children[0]['class'] !== 'text' },
        areaClassName,
      ]"
      :data-placeholder="area.props.name"
      :contenteditable="true"
      @input="handleInput($event, area, areaIndex)"
      @focus="handleFocus($event, area, areaIndex)"
      @blur="handleBlur($event, area, areaIndex)"
      @keydown="handleKeydown($event, area)"
      ><template v-if="area.children.length === 0"></template
      ><template v-else-if="area.props.unit">{{ area.children[0].content }}</template
      ><template v-else
        ><template v-for="child in area.children"
          ><template v-if="child.class === 'text'">{{ child.content }}</template
          ><span v-else :class="`area-inner-block area-inner-${child.class}`" contentEditable="false">{{
            child.content
          }}</span>
        </template>
      </template>
    </span>
  </div>
</template>
<script>
import { store, mutations } from "../store/editor";
import { customEnvs } from "../model/env";
import { onMentionSelected } from "../extention/common";
export default {
  name: "InputArea",
  components: {},
  props: {
    area: {
      type: Object,
      default: {},
    },
  },
  data() {
    return {};
  },
  created() {},
  computed: {
    areaClassName() {
      if (this.area.children.length && this.area.children[0]["class"] !== "text") {
        return `inputarea-${this.area.children[0]["class"]}`;
      } else return "";
    },
    mention() {
      return store.mention;
    },
    menuRef() {
      return store.menuRef;
    },
    editor() {
      return store.editor;
    },
    inputAreaContent() {
      return function(children) {
        let result = "";
        for (let i = 0; i < children.length; i++) {
          result += children[i].content;
        }
        return result;
      };
    },
  },
  methods: {
    handleInput(e, area, areaIndex) {
      // 处理提醒mention相关逻辑
      let newInput = e.target.textContent;
      area.props.temp = [
        {
          class: "text",
          content: newInput,
        },
      ];
      // 清空children的值但是不能触发视图更新 如果赋值为[]会导致视图更新，
      // 触发computed
      // area.children = [];
      area.children.length = 0;
      if (newInput === "") {
        area.children = [];
        mutations.setMention({
          mentionVisible: false,
        });
        return;
      }
      this.initEnvPosition(e);
      this.initEnvMentions(area, newInput);
    },
    handleFocus(e, area, areaIndex) {
      this.$nextTick(() => {
        this.$emit("focus", { event: e, area });
      });
      // 输入域不可再分且内容非空时，提示语境
      if (area.props.unit && e.target.textContent !== "") {
        this.initEnvPosition(e);
        this.initEnvMentions(area, e.target.textContent);
      }
    },
    handleBlur(e, area, areaIndex) {
      e.target.innerHTML = "";
      this.$set(area, "children", []);
      if (area.props.unit) {
        // 使用$set，解决修改二维对象数组的对象的属性，无法触发视图更新
        // 先 设置为 空，再设置为temp的值，防止text值不更新，导致视图不更新
        this.$nextTick(() => {
          this.$set(area, "children", area.props.temp);
        });
      }
      mutations.setMention({
        mentionVisible: false,
      });
      this.$nextTick(() => {
        this.$emit("blur", { event: e, area });
      });
    },
    handleKeydown(e, area) {
      switch (e.key) {
        case "Enter":
          this.handleKeydownEnter(e, area);
          break;
        case "ArrowUp":
          this.handleKeydownArrowUp(e);
          break;
        case "ArrowDown":
          this.handleKeydownArrowDown(e);
          break;
        default:
          break;
      }
    },
    handleKeydownEnter(e, area) {
      // 先执行通用的操作
      if (this.mention.mentionVisible) {
        if (area.props.unit) {
          onMentionSelected(e, area, this.mention.selectedMentions[0]);
        } else {
        }
      } else {
        e.target.blur();
      }
      // 再执行 不同域扩展的操作
      // this.editor.extensionManager.emitExtentionHandler("handleKeydownEnter", { event: e, target: area,currentTarget:area });
      e.preventDefault();
    },
    handleKeydownArrowUp(e) {
      if (this.mention.mentionVisible) {
        this.changeMenuItem(-1);
        e.preventDefault();
      }
    },
    handleKeydownArrowDown(e) {
      if (this.mention.mentionVisible) {
        this.changeMenuItem(1);
        e.preventDefault();
      }
    },
    initEnvMentions(area, input) {
      if (!this.mention.envs.length) return;
      mutations.setMention({
        envMentions: customEnvs(area.props.types, input, { envs: this.mention.envs, scriptTemplates: [] }),
      });
      if (this.mention.envMentions.length === 0) {
        // 没有满足的就不显示
        mutations.setMention({
          mentionVisible: false,
        });
      } else {
        // 默认选中第一个
        let idx = 0;
        if (area.props.unit && area.props.temp.length && area.props.temp[0].id) {
          idx = this.mention.envMentions.findIndex((item) => item.id === area.props.temp[0].id);
          if (idx === -1) idx = 0;
        }
        mutations.setMention({
          selectedMentions: [this.mention.envMentions[idx]],
        });
        // 不加nextTick，会导致滚动的时候找不到active的menuitem
        this.$nextTick(() => {
          this.scrollIntoActiveMetion(idx, 1);
        });
        // 显示mention
        if (!this.mention.mentionVisible) {
          mutations.setMention({
            mentionVisible: true,
          });
        }
      }
    },
    initEnvPosition(e) {
      let rect = e.target.getBoundingClientRect();
      mutations.setMention({
        mentionPosition: [rect.x - 4, rect.y + rect.height + 4],
      });
    },
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
      mutations.setMention({
        selectedMentions: [this.mention.envMentions[idx]],
      });
      this.scrollIntoActiveMetion(idx, op);
    },
    scrollIntoActiveMetion(idx, op) {
      const len = this.mention.envMentions.length;
      // 特殊处理 第一个和最后一个元素的显示
      if (idx == 0) {
        this.menuRef.scrollTop = 0;
      } else if (idx === len - 1) {
        this.menuRef.scrollTop = this.menuRef.scrollHeight;
      }
      // 显示当前选中的mention-item
      // scrollIntoView使用 true 父元素滚到头部对齐，使用false 父元素滚到底部对齐
      else {
        this.menuRef.querySelector(".active").scrollIntoView(op > 0);
      }
    },
  },
};
</script>
<style lang="scss" scoped>
.inputarea-input {
  position: relative;
  // padding: 0 0 0.25rem;
  font-size: 1rem;
  outline: 0;
  cursor: pointer;
  border-bottom: 2px solid transparent;
  border-bottom-color: rgba(247, 67, 77, 0.5);
  transition: border-bottom-color 500ms linear, background-color 300ms linear;
  min-width: 2px;
  &:empty::before {
    content: attr(data-placeholder);
    color: #adb5bd;
  }
  &:focus:empty {
    caret-color: transparent;
    &::after {
      content: "";
      position: absolute;
      display: inline-block;
      width: 1px;
      height: 1.25rem;
      top: 0rem;
      left: 0;
      animation: blink 1s infinite steps(1, start);
    }
  }
  .area-inner-block {
    background: #adb5bd;
  }
  &.has-env {
    &:focus {
      border-bottom-color: rgba(52, 101, 255, 0.5) !important;
    }
    &.inputarea-band {
      font-weight: 600;
      color: #3465ff;
      border-bottom-color: transparent;
    }
    &.inputarea-user {
      font-weight: 400;
      color: #2a2b2e;
      border-bottom-color: rgba(42, 43, 46, 0.5);
    }
    &.inputarea-role {
      font-weight: 400;
      color: #2a2b2e;
      border-bottom-color: rgba(42, 43, 46, 0.5);
    }
    &.inputarea-action {
      font-weight: 600;
      color: #2a2b2e;
      border-bottom-color: rgba(42, 43, 46, 0.5);
    }
  }
  // &:not(:empty) {
  //   &.identifier-param {
  //     font-weight: 400;
  //     color: #2a2b2e;
  //     border-bottom-color: rgba(42, 43, 46, 0.5);
  //   }
  // }
}
@keyframes blink {
  0% {
    background-color: transparent;
  }
  50% {
    background-color: #2a2b2e;
  }
  100% {
    background-color: transparent;
  }
}
</style>
