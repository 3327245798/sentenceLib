import PlainArea from "./PlainArea";
import InputArea from "./InputArea";

class Sentence {
  constructor({ props, children }) {
    this.class = "sentence";
    this.props = {
      // 是否符合标准句型
      standard: props.standard || false,
      // 句型的类型：sentence
      type: props.type || "",
      // 使用到的依赖（语境、变量等）
      params: {},
      // 输入域
      inputAreas: {},
      // 输入域是否全部填写完成
      completed: props.completed || false,
      // 变量
      variables: [],
    };
    // 剧本结构
    this.children = [];
    this.createChildren(props, children);
  }
  createChildren(props, children) {
    for (let i = 0; i < children.length; i++) {
      const area = children[i];
      switch (area.class) {
        case "inputarea":
          this.children.push({ class: "inputarea" });
          this.props.inputAreas[i] = new InputArea(props.inputAreas[i]);
          break;
        case "plainarea":
          this.children.push(new PlainArea(area));
          break;
        default:
          break;
      }
    }
  }
}

export default Sentence;
