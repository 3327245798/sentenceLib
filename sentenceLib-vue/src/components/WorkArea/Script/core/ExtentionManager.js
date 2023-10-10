class ExtensionManager {
  constructor(extensions) {
    this.extensions = new Map();
    this.registerExtensions(extensions);
  }
  registerExtensions(extensions) {
    for (let i = 0; i < extensions.length; i++) {
      const newExtension = {
        props: extensions[i].props || {},
        handlers: extensions[i].handlers || {},
      };
      this.extensions.set(extensions[i].name, newExtension);
    }
  }
  // target 触发元素
  // currentTarget 绑定元素
  emitExtentionHandler(handler, { event, target, currentTarget }) {
    if (currentTarget.class === "inputarea") {
      this.extensions.forEach((item) => {
        if (
          currentTarget.props.types.indexOf(item.props.areaType) !== -1 &&
          typeof item.handlers[handler] === "function"
        ) {
          item.handlers[handler](event, target, currentTarget);
        }
      });
    } else if (currentTarget.class === "sentence") {
      this.extensions.forEach((item) => {
        if (currentTarget.props.type === item.props.sentenceType && typeof item.handlers[handler] === "function") {
          item.handlers[handler](event, target, currentTarget);
        }
      });
    }
  }
}

export default ExtensionManager;
