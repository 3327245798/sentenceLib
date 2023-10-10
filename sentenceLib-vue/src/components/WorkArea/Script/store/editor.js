import Vue from "vue";

// 通过Vue.observable创建一个可响应的对象
export const store = Vue.observable({
  curActiveSentenceIdx: -1,
  mention: {
    mentionVisible: false,
    mentionPosition: [-999, -999],
    envMentions: [],
    selectedMentions: [],
    envs: [],
    scriptTemplates: [],
  },
  menuRef: null,
  editor: null,
});

// 定义 mutations, 修改属性
export const mutations = {
  setCurActiveSentenceIdx(curActiveSentenceIdx) {
    store.curActiveSentenceIdx = curActiveSentenceIdx;
  },
  setMention(mention) {
    store.mention = Object.assign({}, store.mention, mention);
  },
  setMenuRef(menuRef) {
    store.menuRef = menuRef;
  },
  setEditor(editor) {
    store.editor = editor;
  },
};
