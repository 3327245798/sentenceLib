import { Mark, mergeAttributes } from "@tiptap/vue-2";
const InputArea = Mark.create({
  name: "inputarea",
  priority: 1001,
  addOptions() {
    return {
      HTMLAttributes: {
        class: "inputarea",
        "data-useType": "",
        "data-semanticType": "",
      },
    };
  },
  addAttributes() {
    return {
      class: {
        default: this.options.HTMLAttributes.class,
      },
      "data-useType": {
        default: this.options.HTMLAttributes["data-useType"],
      },
      "data-semanticType": {
        default: this.options.HTMLAttributes["data-semanticType"],
      },
    };
  },
  parseHTML() {
    return [
      {
        tag: "span.inputarea",
      },
    ];
  },

  renderHTML({ HTMLAttributes }) {
    return ["span", mergeAttributes(HTMLAttributes), 0];
  },
  addCommands() {
    return {
      setInputArea:
        (attributes) =>
        ({ commands }) => {
          return commands.setMark(this.name, attributes);
        },
      toggleInputArea:
        (attributes) =>
        ({ commands }) => {
          return commands.toggleMark(this.name, attributes);
  
        },
      unsetInputArea:
        () =>
        ({ commands }) => {
          return commands.unsetMark(this.name);
        },
    };
  },
});
export default InputArea;
