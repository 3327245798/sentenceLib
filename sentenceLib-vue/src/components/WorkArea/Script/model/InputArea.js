class InputArea {
  constructor({ props, children }) {
    this.class = "inputarea";
    this.props = {
      // 域名称
      name: props.name || "",
      // 域类型（数组）
      types: props.types || [],
      // 输入域是否可拆分，（只包含一个语境或变量内容，或字符串类型可以嵌入语境、变量等）
      unit: props.unit || false,
      // 输入域输入值（便于contenteditable元素的处理逻辑）
      temp: [],
    };
    this.children = [];
    this.createChildren(props, children);
  }
  createChildren(props, children) {
    for (let i = 0; i < children.length; i++) {
      let child = children[i];
      this.children.push({
        class: child.class,
        props: {},
        content: child.content,
      });
    }
  }
  serialized() {
    // 序列化 需要存入数据库的属性
  }
}

export default InputArea;
