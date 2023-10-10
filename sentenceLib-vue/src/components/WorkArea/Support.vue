<template>
  <div class="support-wrapper">
    <div class="support-scroll">
      <div v-if="supportMode === 'env'" class="support-list support-env">
        <div
          :class="`support-item-wrapper${activeItemId === item.bandInfo[0].bandID ? ' active card-open' : ''}`"
          v-for="item in envs"
          :key="item.bandInfo[0].bandID"
          @click.stop="handleClickSupportItem(item.bandInfo[0].bandID)"
        >
          <bookshelf class="active-bg" theme="outline" size="4rem" fill="#e5dbff" />
          <div class="support-item env-card">
            <div class="env-card-bandName">{{ item.bandInfo[0].name }}</div>
            <div class="env-list-wrapper">
              <tree :treeData="transformedEnvs(item)" />
            </div>
          </div>
        </div>
      </div>
   <!--    <div v-else-if="supportMode === 'variable'" class="support-list support-variable">
        <div
          :class="`support-item-wrapper${activeItemId === index ? ' active card-open' : ''}`"
          v-for="(item, index) in variables"
          :key="index"
          @click.stop="handleClickSupportItem(index)"
        >
          <quote class="active-bg" theme="outline" size="4rem" fill="#dbe4ff" style="transform:rotate(180deg)" />
          <div class="support-item variable-card">
            <div class="variable-key">{{ item.key }}</div>
            <span class="variable-tag">#{{ item.type === "static" ? "静态变量" : "动态变量" }}</span>
            <div class="variable-value">{{ item.value }}</div>
          </div>
        </div>
      </div> -->
     <div v-else-if="supportMode === 'word'" class="support-list support-variable">
        <div
          :class="`support-item-wrapper${activeItemId === index ? ' active card-open' : ''}`"
           v-for="item in words"
          :key="item.id"
          @click.stop="handleClickSupportItem(item.id)"
        >
           <modify class="active-bg" theme="outline" size="4rem" fill="#d0ebff" />
          <div class="support-item sentence-card">
            <span class="word-name">
              {{ item.name }}
            </span>
            <span class="-description">
              {{ item.description }}
            </span>
        </div>
      </div>
      </div>
      <div v-else class="support-list support-sentence">
        <div
          :class="`support-item-wrapper${activeItemId === item.id ? ' active card-open' : ''}`"
          v-for="item in scriptTemplates"
          :key="item.id"
          @click.stop="handleClickSupportItem(item.id)"
        >
          <modify class="active-bg" theme="outline" size="4rem" fill="#d0ebff" />
          <div class="support-item sentence-card">
            <span class="sentence-name">
              {{ item.name }}
            </span>
            <span class="sentence-description">
              {{ item.description }}
            </span>
            <span class="sentence-example">
              {{ item.example }}
            </span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { Modify, User, Me, Tool, Puzzle, DocDetail, Message,Harm, Bookshelf, Quote } from "@icon-park/vue";
import Tree from "./Tree.vue";
export default {
  name: "Support",
  components: { Modify, Tree, Bookshelf, Quote },
  props: {
    supportMode: {
      type: String,
      default: "env",
    },
    scriptTemplates: {
      type: Array,
      default: () => [],
    },
    envs: {
      type: Array,
      default: () => [],
    },
    variables: {
      type: Array,
      default: () => [],
    },
    words:{
     type: Array,
      default: () => [], 
    }
  },
  data() {
    return {
      draft: {
        name: "协作分工型学习应用",
        userName: "梁子文",
        updateTime: "2022年5月5日 23:11",
        description: "协作分工型学习应用",
        content:
          "引导者分析学习任务，分解学习任务，制定任务分解方案。围绕任务分解方案，进行小组讨论，引导者需要确保其他小组成员明确任务分解方案。引导者根据讨论结果，优化任务分解方案。解决者根据任务分解方案，提出可行的解决问题的方法。围绕可行的任务解决方法，进行小组讨论，分析者提出质疑或完善解决方案。总结者根据讨论结果，总结经过小组成员一致同意的解决方案，形成阶段性成果，并给出小组协作方向。总结者制定最终解决方案。引导者根据最终解决方案，明确责任分工方案。执行者根据职责分工方案，完成指定的任务。总结者汇总分工任务，提交小组协作学习成果。",
      },
      activeItemId: "",
    };
  },
  beforeCreate() {},
  created() {},
  computed: {
    transformedEnvs() {
      return function(item) {
        return {
          user: {
            name: "用户",
            icon: User,
            children: item.bandUsers,
          },
          role: {
            name: "角色",
            icon: Me,
            children: item.bandRoles,
          },
          chatroom: {
            name: "消息板",
            icon: Message,
            children: item.bandChatrooms,
          },
          tool: {
            name: "工具",
            icon: Tool,
            children: item.bandTools,
          },
          action: {
            name: "部件",
            icon: Puzzle,
            children: item.toolActions,
          },
          document: {
            name: "文档",
            icon: DocDetail,
            children: item.bandDocuments,
          },
          variable: {
            name: "变量",
            icon: Harm,
            children: item.variables,
          },
        };
      };
    },
  },
  methods: {
    handleClickSupportItem: function(id) {
      this.activeItemId = id;
    },
  },
};
</script>
<style lang="scss">
.support-wrapper {
  position: absolute;
  top: 4.25rem;
  padding: 1rem 0 0;
  height: calc(100% - 4.25rem);
  overflow: hidden;
  background-color: #f7f8fa;
  .support-scroll {
    width: 30rem;
    position: relative;
    z-index: 1;
    padding: 0 2.5rem;
    height: 100%;
    overflow-y: auto;
    .support-list {
      position: relative;
      height: 100%;
    }
  }
}
.support-list {
  .support-item-wrapper {
    position: relative;
    mix-blend-mode: normal;
    width: 100%;
    margin-bottom: 1rem;
    position: relative;
    min-height: 3.125rem;

    .active-bg {
      display: none;
    }

    .support-item {
      position: relative;
      z-index: 3;
      padding: 1rem 1.5rem 1rem 2rem;
      border-radius: 0.625rem;
      border: 0.0625rem solid rgba(98, 100, 105, 0.1);
      background-color: #fff;

      &:hover {
        box-shadow: 0 0.3125rem 0.625rem rgba(96, 113, 158, 0.15);
      }
    }

    &::before {
      content: "";
      display: block;
      // background: #4dabf7;
      // box-shadow: 0 0 4px rgba(77, 171, 247, 0.2);
      height: calc(100% - 1rem);
      width: 4px;
      border-radius: 4px;
      position: absolute;
      top: 50%;
      left: 0.75rem;
      z-index: 5;
      transform: translateY(-50%);
    }
  }
}

.support-item-wrapper.active {
  .active-bg {
    display: block;
    position: absolute;
    z-index: 5;
    opacity: 0.2;
    top: 1rem;
    right: 1rem;
  }
  .env-card {
    box-shadow: 0 0.3125rem 0.625rem rgba(96, 113, 158, 0.15);
    display: flex;
    flex-direction: column;
    padding-left: 0;
    padding-right: 2rem;
    .env-card-bandName {
      position: relative;
      padding-left: 2rem;
      font-size: 0.875rem;
      line-height: 1.125rem;
      height: 1.125rem;
      color: #939599;
      cursor: default;
      &::before {
        content: "";
        display: block;
        background: #9775fa;
        box-shadow: 0 0 4px rgba(151, 117, 250, 0.2);
        height: 100%;
        width: 4px;
        border-radius: 4px;
        position: absolute;
        left: 0.75rem;
        z-index: 5;
      }
    }
    .env-list-wrapper {
      display: flex;
      flex-direction: column;
      padding-left: 2rem;
      margin-top: 1rem;
    }
  }
  .sentence-card {
    box-shadow: 0 0.3125rem 0.625rem rgba(96, 113, 158, 0.15);
    display: flex;
    flex-direction: column;
    padding-left: 0;
    padding-right: 4rem;
    .sentence-name {
      position: relative;
      padding-left: 2rem;
      font-size: 0.875rem;
      line-height: 1.125rem;
      height: 1.125rem;
      color: #939599;
      cursor: default;

      &::before {
        content: "";
        display: block;
        background: #4dabf7;
        box-shadow: 0 0 4px rgba(77, 171, 247, 0.2);
        height: 100%;
        width: 4px;
        border-radius: 4px;
        position: absolute;
        left: 0.75rem;
        z-index: 5;
      }
    }
    .sentence-description {
      padding-left: 2rem;
      line-height: 1.25rem;
      font-size: 0.875rem;
      color: #626469;
      margin: 1rem 0;
      position: relative;
    }
    .sentence-example {
      padding-left: 2rem;
      display: block;
      line-height: 1.25rem;
      font-size: 0.875rem;
      color: #626469;
    }
  }
  .variable-card {
    box-shadow: 0 0.3125rem 0.625rem rgba(96, 113, 158, 0.15);
    display: block;
    padding-left: 0;
    padding-right: 4rem;
    .variable-key {
      display: inline-block;
      position: relative;
      padding-left: 2rem;
      font-size: 0.875rem;
      line-height: 1.125rem;
      height: 1.125rem;
      color: #939599;
      cursor: default;

      &::before {
        content: "";
        display: block;
        background: #748ffc;
        box-shadow: 0 0 4px rgba(116, 143, 252, 0.2);
        height: 100%;
        width: 4px;
        border-radius: 4px;
        position: absolute;
        left: 0.75rem;
        z-index: 5;
      }
    }
    .variable-value {
      display: block;
      padding: 0.75rem 0 1rem 2rem;
      line-height: 1.25rem;
      color: #626469;
    }
  }
  &::before {
    display: none;
  }
}

.support {
  &-env .support-item-wrapper::before {
    background: #9775fa;
    box-shadow: 0 0 4px rgba(151, 117, 250, 0.2);
  }
  &-sentence .support-item-wrapper::before {
    background: #4dabf7;
    box-shadow: 0 0 4px rgba(77, 171, 247, 0.2);
  }
  &-variable .support-item-wrapper::before {
    background: #748ffc;
    box-shadow: 0 0 4px rgba(116, 143, 252, 0.2);
  }
}

.env-card {
  display: flex;
  flex-direction: row;
  .env-card-bandName {
    height: 1.5rem;
    line-height: 1.5rem;
    margin-right: 1rem;
    font-size: 1rem;
    color: #2a2b2e;
    vertical-align: middle;
    display: inline-block;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  }
  .env-list-wrapper {
    display: none;
  }
}

.sentence-card {
  display: flex;
  flex-direction: row;
  .sentence-name {
    height: 1.5rem;
    line-height: 1.5rem;
    margin-right: 1rem;
    font-size: 1rem;
    color: #2a2b2e;
    vertical-align: middle;
    display: inline-block;
    max-width: 7.5rem;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  }
  .sentence-description {
    font-size: 0.875rem;
    height: 1.5rem;
    line-height: 1.5rem;
    color: #939599;
    display: inline-block;
    vertical-align: middle;
    margin-right: 1.5rem;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  .sentence-example {
    display: none;
  }
}

.variable-card {
  display: flex;
  flex-direction: row;
  align-items: center;
  .variable-key {
    line-height: 1.5rem;
  }
  .variable-value {
    display: none;
  }
  .variable-tag {
    margin-left: 0.875rem;
    background: #edf2ff;
    color: #596480;
    font-size: 14px;
    line-height: 1.25rem;
    display: inline-block;
    height: 1.25rem;
    padding: 0 7px;
    font-size: 0.75rem;
    white-space: nowrap;
    border-radius: 0.25rem;
  }
}

.support-scroll::-webkit-scrollbar {
  width: 0px;
}

.card-open {
  animation: card-open 0.3s linear;
}
@keyframes card-open {
  0% {
    transform: scaleY(0);
    opacity: 0;
  }
  15% {
    transform: scaleY(0.15);
    opacity: 0.15;
  }
  25% {
    transform: scaleY(0.25);
    opacity: 0.25;
  }
  35% {
    transform: scaleY(0.35);
    opacity: 0.35;
  }
  45% {
    transform: scaleY(0.45);
    opacity: 0.45;
  }
  50% {
    transform: scaleY(0.55);
    opacity: 0.55;
  }
  65% {
    transform: scaleY(0.65);
    opacity: 0.65;
  }
  75% {
    transform: scaleY(0.75);
    opacity: 0.75;
  }
  85% {
    transform: scaleY(0.85);
    opacity: 0.85;
  }
  95% {
    transform: scaleY(0.95);
    opacity: 0.95;
  }
  100% {
    transform: scaleY(1);
    opacity: 1;
  }
}
</style>
