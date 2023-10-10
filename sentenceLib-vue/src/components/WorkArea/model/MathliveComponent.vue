<template>
  <main>
    <h2>MathLive with Vue.js</h2>
    <mathlive-mathfield
      id="mf"
      ref="mathfield"
      :options="{ smartFence: false, virtualKeyboardMode: 'onfocus', fontsDirectory: '../assets/mathlive-fonts' }"
      @focus="ping"
      :on-keystroke="displayKeystroke"
      v-model="formula"
      >f(x)=</mathlive-mathfield
    >
    <div>
      <label>Keystroke:&nbsp;</label><span>{{ keystroke }}</span>
    </div>
    <div class="output">LaTeX: {{ formula }}</div>
    <div class="output">Spoken text: {{ asSpokenText() }}</div>
    <button v-on:click="sayIt">Say It</button>
    <button v-on:click="setIt">Set It</button>
  </main>
</template>
<script>
export default {
  name: "MathliveComponent",
  data: {
    formula: "",
    keystroke: "",
  },
  mounted() {
    console.log(this.$mathlive);
  },
  methods: {
    sayIt: function(event) {
      console.log(this.$refs.mathfield);
      this.$refs.mathfield.perform(["speak", "all"]);
    },
    setIt: function(event) {
      this.formula = "x=-b\\pm \\frac {\\sqrt{b^2-4ac}}{2a}";
    },
    ping: function() {
      console.log("ping");
    },
    displayKeystroke: function(keystroke, _ev) {
      this.keystroke = keystroke;
      return true;
    },
    asSpokenText: function() {
      return this.$refs.mathfield ? this.$refs.mathfield.getValue("spoken") : "";
    },
  },
};
</script>
<style lang="scss"></style>
