import { Mark, mergeAttributes } from "@tiptap/vue-2";
const User = Mark.create({
  name: "user",
  priority: 1001,
  addOptions() {
    return {
      HTMLAttributes: {
        class: "user",
        target: "_blank",
        rel: "noreferrer nofollow",
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
    };
  },
  parseHTML() {
    return [
      {
        tag: 'a.user[href]:not([href *= "javascript:" i])',
        class: "user",
      },
    ];
  },

  renderHTML({ HTMLAttributes }) {
    return ["a", mergeAttributes(HTMLAttributes), 0];
  },
  addCommands() {
    return {
      setUser: (attributes) => ({ commands }) => {
        return commands.setMark(this.name, attributes);
      },
      toggleUser: () => ({ commands }) => {
        return commands.toggleMark(this.name);
      },
      unsetUser: () => ({ commands }) => {
        return commands.unsetMark(this.name);
      },
    };
  },
});
export default User;
