class Assign {
  constructor() {
    this.name = "assign";
    this.props = {
      type: "sentence",
      sentenceType: "assign",
    };
    this.handlers = {
      onInputareaChanged: this.onInputareaChanged,
    };
  }
  onInputareaChanged(e, target, currentTarget) {
    console.log(currentTarget);
    if (!currentTarget.props.variables.length) {
      currentTarget.props.variables.push({
        type: "static",
        key: "",
        value: "",
      });
    }
    if (target.props.types.indexOf("variable") !== -1) {
      currentTarget.props.variables[0].key = target.children[0].content;
    }
    if (target.props.types.indexOf("value") !== -1) {
      currentTarget.props.variables[0].value = target.children[0].content;
    }
  }
}

export default Assign;
