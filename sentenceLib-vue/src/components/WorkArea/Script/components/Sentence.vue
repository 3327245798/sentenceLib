<template>
  <div
    :class="[
      'se-sentence',
      {
        active: curActiveSentenceIdx === sentenceIndex,
        standard: sentence.props.standard,
        empty: !sentence.props.standard && sentence.unknownText == '',
      },
    ]"
    :contenteditable="false"
  >
    <span class="se-line-number">
      {{ sentenceIndex + 1 }}
    </span>
    <template v-if="sentence.props.standard">
      <div :class="`se-${area.class}`" v-for="(area, areaIndex) in sentence.children" :key="areaIndex">
        <template v-if="area.class === 'inputarea'">
          <InputArea
            :area="sentence.props.inputAreas[areaIndex]"
            @focus="handleInputAreaFocus"
            @blur="handleInputAreaBlur"
          ></InputArea>
        </template>
        <template v-if="area.class === 'plainarea'">
          <PlainArea :area="area"></PlainArea>
        </template>
      </div>
    </template>
    <div class="sentence-menu-wrapper">
      <a-popover trigger="click" placement="right">
        <template slot="content">
          <ul class="sentence-tools">
            <li class="sentence-tool">
              调试
            </li>
            <li class="sentence-tool">
              <!-- v-if="sentenceIndex !== sentences.length - 1" -->
              删除
            </li>
            <li class="sentence-tool">
              在上方插入
            </li>
            <li class="sentence-tool">
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
</template>
<script>
import PlainArea from "./PlainArea.vue";
import InputArea from "./InputArea.vue";
import { store, mutations } from "../store/editor";
import { HamburgerButton } from "@icon-park/vue";

export default {
  name: "Sentence",
  components: { PlainArea, InputArea, HamburgerButton },
  props: {
    sentence: {
      type: Object,
      default: {},
    },
    sentenceIndex: {
      type: Number,
      default: -1,
    },
  },
  data() {
    return {};
  },
  methods: {
    handleInputAreaFocus({ event, area }) {
      mutations.setCurActiveSentenceIdx(this.sentenceIndex);
    },
    handleInputAreaBlur({ event, area }) {
      mutations.setCurActiveSentenceIdx(-1);
      this.editor.extensionManager.emitExtentionHandler("onInputareaChanged", {
        event: event,
        target: area,
        currentTarget: this.sentence,
      });
    },
  },
  created() {},
  computed: {
    curActiveSentenceIdx() {
      return store.curActiveSentenceIdx;
    },
    editor() {
      return store.editor;
    },
  },
};
</script>
<style lang="scss" scoped>
.se-sentence {
  position: relative;
  padding-left: 2rem;
  padding-right: 2rem;
  margin-bottom: 0.125rem;
  &.active,
  &:hover {
    background-color: #f1f3f5;
    outline: #ced4da 1px solid;
    // padding: 2rem 0 1rem 2rem;

    .se-line-number {
      color: #495057;
      font-weight: 600;
    }
    .sentence-menu-wrapper {
      visibility: visible;
    }
  }
  .se-line-number {
    position: absolute;
    left: 1rem;
    line-height: 2rem;
    transform: translateX(-50%);
    color: #868e96;
  }
  .sentence-menu-wrapper {
    display: flex;
    visibility: hidden;
    align-items: center;
    justify-content: flex-end;
    height: 2rem;
    line-height: 2rem;
    position: absolute;
    top: 0;
    right: 0.25rem;
    .sentence-menu {
      height: 1.5rem;
      width: 1.5rem;
      margin-left: 0.5rem;
      opacity: 0.8;
      display: flex;
      align-items: center;
      justify-content: center;
      background-color: #dee2e6;
      border-radius: 0.25rem;
      cursor: pointer;
      --tool-color: rgba(47, 48, 53, 0.7);
    }
    .sentence-menu:hover {
      opacity: 1;
    }
  }
  & div {
    display: inline;
    line-height: 2rem;
    // span 有换行会在页面导致空白，
    // 父级元素使用font-size:0,子级元素使用font-size：正常大小可以解决
    font-size: 0;
  }
}

.sentence-tools {
  padding: 0.5rem 0;
  line-height: 1.875rem;
  font-size: 0.875rem;
  .sentence-tool {
    display: flex;
    align-items: center;
    min-width: 5rem;
    padding: 0 0.83em 0 1.67em;
    user-select: none;
    cursor: pointer;
    white-space: nowrap;
    &:hover {
      background: #eff0f3;
    }
  }
}
</style>
