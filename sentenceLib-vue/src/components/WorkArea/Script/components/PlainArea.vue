<template>
  <div class="se-plain">{{ plainAreaContent(area.children) }}</div>
</template>
<script>
export default {
  name: "PlainArea",
  components: {},
  props: {
    area: {
      type: Object,
      default: {},
    },
  },
  data() {
    return {};
  },
  created() {},
  computed: {
    plainAreaContent() {
      return function(children) {
        let result = "";
        for (let i = 0; i < children.length; i++) {
          result += children[i].content;
        }
        return result;
      };
    },
  },
};
</script>
<style lang="scss" scoped>
.se-plain {
  font-size: 1rem !important;
}
</style>
