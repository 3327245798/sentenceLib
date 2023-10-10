<template>
  <div class="draft-editor-wrapper">
    <div ref="draftRef" class="draft-editor">
      <Sentence
        v-for="(sentence, sentenceIndex) in editor.state"
        :key="sentenceIndex"
        :sentence="sentence"
        :sentenceIndex="sentenceIndex"
      />
    </div>
    <div
      class="mention-wrapper"
      v-show="mention.mentionVisible"
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
import { mutations, store } from "./store/editor";
import Sentence from "./components/Sentence.vue";
import Editor from "./core/Editor";
import Assign from "./extention/sentence/assign";
import Command from "./extention/sentence/command";
export default {
  name: "ScriptEditor",
  components: { Sentence },
  props: {
    // sentences: {
    //   type: Array,
    //   default: [],
    // },
    envs: {
      type: Array,
      default: [],
    },
    scriptTemplates: {
      type: Array,
      default: [],
    },
  },
  data() {
    return {
      sentences: [
        {
          class: "sentence",
          props: {
            complete: false,
            standard: true,
            type: "command",
            params: {
              123: {
                env: "",
              },
            },
            inputAreas: {
              0: {
                class: "inputarea",
                props: { types: ["user", "role"], name: "角色/用户", unit: true },
                children: [
                  {
                    class: "user",
                    props: {
                      env: {
                        id: 123,
                      },
                    },
                    content: "梁杰潮",
                  },
                ],
              },
              2: {
                class: "inputarea",
                props: { types: ["band"], name: "帮区", unit: true },
                children: [
                  {
                    class: "band",
                    name: "帮区",
                    content: "",
                    env: null,
                  },
                ],
              },
              4: {
                class: "inputarea",
                props: { types: ["action"], name: "部件", unit: true },
                children: [
                  {
                    class: "action",
                    name: "部件",
                    content: "",
                    env: null,
                  },
                ],
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
                children: [
                  {
                    class: "text",
                    content: "123",
                  },
                ],
              },
              3: {
                class: "inputarea",
                props: { types: ["value"], name: "值", unit: false },
                children: [{ class: "text", content: "" }],
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
    };
  },
  created() {
    mutations.setMention({
      envs: this.envs,
      scriptTemplates: this.scriptTemplates,
    });
    mutations.setEditor(new Editor({ sentences: this.scriptTemplates, extensions: [new Assign(), new Command()] }));
  },
  updated() {},
  computed: {
    mention() {
      return store.mention;
    },
    editor() {
      return store.editor;
    },
  },
  mounted() {
    mutations.setMenuRef(this.$refs.menuRef);
  },
};
</script>
<style lang="scss" scoped>
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
.mention-wrapper {
  position: fixed;
  max-width: 500px;
  max-height: 200px;
  border-radius: 0.5rem;
  box-shadow: 0 0 0.5rem #ced4da !important;
  z-index: 400;
  min-width: 200px;
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
    &-key {
      color: #4dabf7;
      margin: 0 0.5rem;
    }
  }
}
</style>
