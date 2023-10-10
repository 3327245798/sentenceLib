import { Mark, mergeAttributes } from "@tiptap/vue-2";
const Firmware = Mark.create({
  name: "firmware",
  priority: 1001,
  addOptions() {
    return {
      HTMLAttributes: {
        class: "firmware",
        target: "_blank",
        rel: "noreferrer nofollow",
        "data-toolname": "",
      },
    };
  },
  addAttributes() {
    return {
      href: {
        default: null,
      },
      target: {
        default: this.options.HTMLAttributes.target,
      },
      class: {
        default: this.options.HTMLAttributes.class,
      },
      "data-toolname": {
        default: this.options.HTMLAttributes["data-toolname"],
      },
    };
  },
  parseHTML() {
    return [
      {
        tag: 'a.firmware[href]:not([href *= "javascript:" i])',
      },
    ];
  },

  renderHTML({ HTMLAttributes }) {
    return ["a", mergeAttributes(HTMLAttributes), 0];
  },
  addCommands() {
    return {
      setFirmware: (attributes) => ({ commands }) => {
        return commands.setMark(this.name, attributes);
      },
      toggleFirmware: () => ({ commands }) => {
        return commands.toggleMark(this.name);
      },
      unsetFirmware: () => ({ commands }) => {
        return commands.unsetMark(this.name);
      },
    };
  },
});
export default Firmware;
