class Variable {
  constructor() {
    this.name = "variable";
    this.props = {
      type: "inputarea",
      inputareaType: "variable",
    };
    this.handlers = {
      handleKeydownEnter: this.handleKeydownEnter,
    };
  }
  handleKeydownEnter(e, target) {
    console.log("这是一个扩展");
  }
}

export default Variable;
