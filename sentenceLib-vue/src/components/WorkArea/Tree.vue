<template>
  <div class="tree-wrapper">
    <div class="tree" v-for="(item, key) in treeData" :key="key">
      <div class="tree-parent">
        <div class="tree-parent-line" @click.stop="handleClickParentLine(key, item.children.length)">
          <component
            v-if="item.icon"
            :is="item.icon"
            theme="outline"
            size="1.25rem"
            :strokeWidth="4"
            fill="#2a3033"
            class="tree-line-icon"
          />
          <div v-if="item.name" class="tree-line-name">
            {{ item.name }}
          </div>
          <down
            v-if="item.children.length"
            class="tree-line-down"
            size="1.25rem"
            fill="#2a3033"
            :strokeWidth="4"
          ></down>
        </div>
        <div class="tree-child" v-if="openKeys.indexOf(key) !== -1">
          <div v-for="child in item.children" class="tree-child-line">
            <span class="tree-line-name">{{ child[`${key}Name`] }}</span>
            <span class="tree-line-key">{{ child[`${key}Id`] }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { Down } from "@icon-park/vue";
export default {
  name: "Tree",
  components: { Down },
  props: {
    treeData: {
      type: Array,
      default: () => [],
    },
  },
  data() {
    return {
      openKeys: [""],
    };
  },
  beforeCreate() {},
  created() {},
  methods: {
    handleClickParentLine(key, len) {
      if (!len) return;
      if (this.openKeys.indexOf(key) !== -1) {
        this.openKeys = this.openKeys.filter((item) => item !== key);
      } else {
        this.openKeys.push(key);
      }
    },
  },
};
</script>
<style lang="scss" scoped>
.tree-wrapper {
  display: flex;
  flex-direction: column;
  flex: 1;
  justify-content: center;
  .tree-parent {
    display: flex;
    flex-direction: column;
    .tree-parent-line {
      display: flex;
      flex-direction: row;
      justify-content: space-between;
      align-items: center;
      padding: 0.25rem 0.5rem;
      height: 2rem;
      line-height: 2rem;
      border-radius: 4px;
      opacity: 0.8;
      &:hover {
        background: #efefef;
        opacity: 1;
      }
    }
    .tree-child {
      margin: 0.25rem 0 0.25rem 2rem;
      &-line {
        height: 2rem;
        line-height: 2rem;
        border-radius: 4px;
        padding: 0.25rem 0.75rem;
        display: flex;
        align-items: center;
        position: relative;
        opacity: 0.8;
        color: #2a3033;

        &:hover {
          background: #efefef;
          opacity: 1;
        }
        &::before {
          display: block;
          content: "";
          position: absolute;
          width: 3px;
          background: #efefef;
          height: 100%;
          left: -1rem;
        }
        &:last-of-type::before {
          display: block;
          content: "";
          position: absolute;
          width: 3px;
          background: #efefef;
          transform: translateY(-50%);
          height: 50%;
          left: -1rem;
        }
        &::after {
          display: block;
          content: "";
          position: absolute;
          background: #efefef;
          width: 1rem;
          height: 3px;
          left: -1rem;
        }
      }
    }
  }
}
.tree-parent-line .tree-line {
  &-icon {
    margin-right: 1rem;
  }
  &-name {
    flex: 1;
    color: #2a3033;
  }
}
.tree-child-line .tree-line {
  &-name {
    flex: 1;
    color: #2a3033;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
  &-key {
    font-size: 0.85rem;
    background: #efefef;
    padding: 0 4px;
    height: 1.25rem;
    line-height: 1.25rem;
    border: 1px solid #d9d9d9;
    border-radius: 4px;
  }
}
</style>
