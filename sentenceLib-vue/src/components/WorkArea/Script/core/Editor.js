import Sentence from "../model/Sentence";
import ExtensionManager from "../core/ExtentionManager";

class Editor {
  constructor(options) {
    this.setOptions(options);
    this.createState();
    this.createExtensions();
  }
  createState() {
    this.state = [];
    const sentences = [...this.options.sentences];
    for (let i = 0; i < sentences.length; i++) {
      const sentence = sentences[i];
      if (sentence.props.standard) {
        const newSentence = new Sentence(sentence);
        this.state.push(newSentence);
      }
    }
  }
  createExtensions() {
    this.extensionManager = new ExtensionManager(this.options.extensions || []);
  }
  setOptions(options) {
    const defaultOptions = {};
    this.options = Object.assign({}, defaultOptions, options);
  }
}

export default Editor;
