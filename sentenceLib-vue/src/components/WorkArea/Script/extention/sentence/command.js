import PlainArea from "../../model/PlainArea";
class Command {
  constructor() {
    this.name = "command";
    this.props = {
      type: "sentence",
      sentenceType: "command",
    };
    this.handlers = {
      onInputareaChanged: this.onInputareaChanged,
    };
  }
  onInputareaChanged(e, target, currentTarget) {
    if (target.props.types.indexOf("action") !== -1) {
      let idx = -1;
      for (let index in currentTarget.props.inputAreas) {
        if (currentTarget.props.inputAreas[index] === target) {
          idx = index;
        }
      }
      currentTarget.children.push(
        new PlainArea({
          class: "plainarea",
          props: {},
          children: [
            {
              class: "text",
              content: "在",
            },
          ],
        })
      );
    }
  }
}

export default Command;
