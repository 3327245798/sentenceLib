<template>
  <div class="editor-top-bar">
    <div class="top-bar">
      <div class="site-tool-wrapper">
        <a href="#" class="site-tool tool-back">
          <arrow-left theme="outline" size="24" fill="#333" @click.stop="handleClickReturn()" />
        </a>
        <div class="site-tool tool-user">
          <user theme="outline" size="24" fill="#333" />
        </div>
        <div class="site-tool tool-divider">
          <div class="divider-line"></div>
        </div>
        <div class="info-wrapper">
          <div class="info-title">
            <span class="title"
              >剧本名称：<em>{{ draft.name }}</em></span
            >
            <span class="sub-title">
              剧本描述：<em>{{ draft.description }}</em>
            </span>
          </div>
          <div class="info-other">
            <span class="info-tag">
              创建者：<i style="font-style:italic;">{{ draft.userName }}</i>
            </span>
            <a-divider type="vertical" />
            <span v-if="bandList.length == 0">
              尚未选择语境库
            </span>
            <a-tooltip v-else placement="bottom">
              <template slot="title">
                <ul v-if="bandList.length > 1">
                  <li v-for="item in bandList">{{ item.bandName }}</li>
                </ul>
                <span v-else>
                  当前语境库
                </span>
              </template>
              <span class="info-tag" v-if="bandList.length > 1">
                语境库：<i style="font-style:italic;">{{ bandList[0].bandName }}</i> 等
              </span>
              <span class="info-tag" v-else>
                语境库：<i style="font-style:italic;">{{ bandList[0].bandName }}</i>
              </span>
            </a-tooltip>
          </div>
        </div>
      </div>
      <!-- <div class="editor-tool-wrapper">
        <a-tooltip placement="bottom">
          <template slot="title">
            <span>试运行</span>
          </template>
          <div class="editor-tool tool-run">
            <play-two theme="outline" size="24" fill="#333" />
          </div>
        </a-tooltip>
        <a-tooltip placement="bottom">
          <template slot="title">
            <span>保存</span>
          </template>
          <div class="editor-tool tool-save">
            <save theme="outline" size="24" fill="#333" />
          </div>
        </a-tooltip>
      </div> -->
    </div>
  </div>
</template>
<script>
import { ArrowLeft, User, PlayTwo, Save } from "@icon-park/vue";
export default {
  name: "EditorTopBar",
  components: {
    ArrowLeft,
    User,
    PlayTwo,
    Save,
  },
  watch: {},
  props: {
    draft: {
      type: Object,
      default: null,
    },
    bandList: {
      type: Array,
      default: () => [],
    },
  },
  data() {
    return {};
  },
  mounted() {},
  beforeCreate() {},
  created() {},
  methods: {
    handleClickReturn() {
      this.$router.go(-1);
    },
  },
};
</script>
<style lang="scss">
.editor-top-bar {
  position: sticky;
  z-index: 201;
  left: 0;
  top: 0;
  display: flex;
  width: 100%;
  box-shadow: 0 0px 10px #f7f9fc;
  .top-bar {
    position: relative;
    display: flex;
    flex: 1;
    justify-content: space-between;
    align-items: center;
    box-sizing: border-box;
    height: 4.25rem;
    padding: 0 3rem;
    background-color: #fff;
  }
}

.info-wrapper {
  display: flex;
  flex-direction: column;
  .info-title {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    outline: none;
    background: none;
    border: 0;
    position: relative;
    vertical-align: middle;
    line-height: 1.5rem;
    height: 1.5rem;
    margin-bottom: 0.25rem;
    .title {
      font-size: 1rem;
      font-weight: 600;
      color: #2a2b2e;
      margin-right: 8px;
    }
    .sub-title {
      font-size: 0.8rem;
    }
  }
  .info-other {
    height: 1.25rem;
    line-height: 1.25rem;
    font-size: 0.8rem;
  }
}

.site-tool-wrapper {
  position: relative;
  display: flex;

  .site-tool {
    display: flex;
    justify-content: flex-start;
    align-items: center;
    position: relative;
    margin-left: 1rem;
    &:first-child {
      margin-left: 0;
    }
  }

  // .tool-back {
  //   opacity: 0.8;
  //   position: absolute;
  //   left: 0;
  //   top: 50%;
  //   transform: translate(-200%, -50%);
  // }

  .tool-back,
  .tool-user {
    opacity: 0.8;
    &:hover {
      opacity: 1;
    }
  }

  .tool-divider {
    margin: 0 1.25rem;
    .divider-line {
      height: 1.75rem;
      width: 0;
      border-left: 0.0625rem solid #f0f0f0;
    }
  }
}
// .editor-tool-wrapper {
//   position: relative;
//   display: flex;

//   .editor-tool {
//     display: flex;
//     justify-content: flex-start;
//     align-items: center;
//     position: relative;
//     margin-left: 1rem;
//   }
//   .tool-save,
//   .tool-run {
//     opacity: 0.8;
//   }
//   .tool-save:hover,
//   .tool-run:hover {
//     opacity: 1;
//   }
// }
</style>
