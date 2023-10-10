<template>
  <div class="workarea-container">
    <div
      class="draft-container"
      :style="{
        left: draftOptions.draftVisible ? 0 : '-22rem',
        display: draftOptions.draftVisible ? 'block' : 'none',
      }"
    >
      <a-tabs default-active-key="info">
        <a-tab-pane key="info" tab="业务剧本信息">
          <a-descriptions layout="vertical " :column="1">
            <a-descriptions-item label="名称">
              {{ draftOptions.draft.name }}
            </a-descriptions-item>
            <a-descriptions-item label="描述">
              {{ draftOptions.draft.description }}
            </a-descriptions-item>
            <a-descriptions-item label="创建者">
              {{ draftOptions.draft.creatorName }}
            </a-descriptions-item>
            <a-descriptions-item label="更新时间">
              {{ draftOptions.draft.updateTime }}
            </a-descriptions-item>
          </a-descriptions>
        </a-tab-pane>
        <a-tab-pane key="drafts" tab="草稿剧本">
          <a-list :data-source="draftOptions.draftList">
            <a-list-item slot="renderItem" slot-scope="item, index">
              {{ item }}
            </a-list-item>
          </a-list>
        </a-tab-pane>
      </a-tabs>
    </div>
    <mention :script.sync="script" :workTools="workTools" :envList="envOptions.envList"></mention>
    <div
      class="env-container"
      :style="{ right: envOptions.envVisible ? 0 : '-22rem', display: envOptions.envVisible ? 'block' : 'none' }"
    >
      <div class="env-header">
        <h2 class="col-title">
          语境
        </h2>
        <add theme="filled" size="1rem" fill="rgba(0,0,0,0.75)" @click="handleNewEnv" />
      </div>

      <a-input-search placeholder="搜索" @change="onEnvSearchChange" />
      <a-tree
        show-icon
        :multiple="true"
        :expanded-keys="envOptions.expandedKeys"
        :tree-data="treeData"
        :selectedKeys="envOptions.selectedKeys"
        :auto-expand-parent="envOptions.autoExpandParent"
        @expand="onExpand"
      >
        <a-icon slot="appcase" type="project" />
        <a-icon slot="scripts" type="book" />
      </a-tree>
    </div>
    <a-modal v-model="envOptions.addEnvVisible" title="添加剧本语境" @ok="handleAddEnv" cancelText="取消" okText="添加">
      <a-form :form="envOptions.envForm" :labelCol="{ span: 4 }" :wrapperCol="{ span: 20 }">
        <a-form-item label="语境名称">
          <a-input
            v-decorator="['key', { rules: [{ required: true, message: '请输入剧本语境名称！' }] }]"
            placeholder="请输入剧本语境名称"
          />
        </a-form-item>
        <a-form-item label="语境内容">
          <a-input
            v-decorator="['value', { rules: [{ required: true, message: '请输入剧本语境内容！' }] }]"
            placeholder="请输入剧本语境内容"
          />
        </a-form-item>
      </a-form>
    </a-modal>
    <a-modal v-model="consoleVisible" title="控制台" :footer="null"
      ><a-timeline :pending="pending">
        <a-timeline-item v-for="item in runningLogFilter" :key="item.id">
          {{ item.content }}{{ item.result }}
        </a-timeline-item>
      </a-timeline>
    </a-modal>
  </div>
</template>
<script>
import { mapState } from "vuex";
import { Add } from "@icon-park/vue";
import Mention from "@/components/home/Mention";
import ScriptService from "@/service/script_service";
import BandService from "@/service/band_service";
import ApplicationService from "@/service/application_service";
// import Worker from "@/common/monitor.worker.js";
const scriptService = new ScriptService();
const bandService = new BandService();
const applicationService = new ApplicationService();

const getParentKey = (key, tree) => {
  let parentKey;
  for (let i = 0; i < tree.length; i++) {
    const node = tree[i];
    if (node.children) {
      if (node.children.some((item) => item.key === key)) {
        parentKey = node.key;
      } else if (getParentKey(key, node.children)) {
        parentKey = getParentKey(key, node.children);
      }
    }
  }
  return parentKey;
};
const generateList = (set, data) => {
  let result = [];
  for (let i = 0; i < data.length; i++) {
    const node = data[i];
    const key = node.key;
    if (!node.disableMention && !set.has(key)) {
      set.add(key);
      result.push({ key, title: node.title });
    }
    if (node.children) {
      const children = generateList(set, node.children);
      result = [...result, ...children];
    }
  }
  return result;
};

export default {
  name: "WorkArea",
  watch: {
    consoleVisible(cur, prev) {
      if (!this.globalId) {
        this.$message.warning("请先运行剧本！");
        return;
      }
      if (cur == true) {
        // let worker = new Worker();
        // worker.postMessage({ gid: 123 });
        /* this.timer = setInterval(() => {
          scriptService.getRunningDetailsByGid(
            {
              globalId: this.globalId,
            },
            {
              onSuccess: (model, fModel, json) => {
                this.runningLog = json.data.rows;
              },
              onFail: (msg, json) => {
                console.log(msg, json);
              },
            }
          );
        }, 5000); */
        console.log("cur是=》"+cur);
      } else {
        clearInterval(this.timer);
        this.timer = null;
      }
    },
  },
  computed: {
    ...mapState(["ST_CURRENT"]),
    runningLogFilter() {
      let result = [];
      for (let i = 0; i < this.runningLog.length; i++) {
        result.push(this.runningLog[i]);
        if (this.runningLog[i].status == 0) {
          break;
        }
      }
      if (this.runningLog.length && result.length === this.runningLog.length) {
        clearInterval(this.timer);
        this.timer = null;
        this.pending = false;
      }
      return result;
    },
  },
  components: {
    Mention,
    Add,
  },

  data() {
    return {
      globalId: null,
      timer: null,
      runningLog: [],
      pending: "等待执行中...",
      script: { text: "", html: "" },
      applicationCaseId: null,
      // {
      //   html:
      //     "<p>当在 B8_102 帮区监听到 学生请假 工具的 审批 的 审批成功信息 ，则 帮区机器人 在 B8_102 帮区执行消息板工具，消息板为 B8_102消息板 ，接收者为 学生 ，消息内容为 审批成功 。</p>",
      //   text:
      //     "当在 B8_102 帮区监听到 学生请假 工具的 审批 的 审批成功信息 ，则 帮区机器人 在 B8_102 帮区执行消息板工具，消息板为 B8_102消息板 ，接收者为 学生 ，消息内容为 审批成功 。",
      //   id: 1,
      //   isDeveloped: false,
      // },
      mentionVisible: false,
      toolOptions: {
        fontColorActive: false,
        fontColor: "#000",
      },
      consoleVisible: false,
      currentSelectIndex: -1,
      workTools: [
        {
          tooltip: "运行",
          iconName: "play-two",
          key: "run",
          callback: (script, usedEnvs) => {
            const scripts = script
              .split("。")
              .filter((item) => item != "")
              .map((item, index) => {
                return { line: index, script: item.trimStart() + "。" };
              });
            console.log(scripts);
            const param = [...usedEnvs.entries()].reduce((obj, [key, value]) => ((obj[key] = value), obj), {});
            scriptService.deBugScripts(
              {
                scripts: {
                  scripts: scripts,
                  draftId: this.draftOptions.draftId,
                  userId: this.ST_CURRENT.userId,
                  param: param,
                },
              },
              {
                onSuccess: (model, fModel, json) => {
                  console.log(json);
                  this.$message.success("剧本运行成功，请查看控制台！");
                  this.globalId = json.data.globalId;
                  this.pending = "等待执行中...";
                },
                onFail: (msg, json) => {
                  console.log(msg, json);
                  this.$message.success("剧本运行失败，请查看剧本是否符合规范！");
                },
              }
            );
          },
        },
        {
          tooltip: "语法树",
          iconName: "tree-diagram",
          key: "tree",
        },
        {
          tooltip: "保存",
          iconName: "save-one",
          key: "save",
        },
        {
          tooltip: "语境",
          iconName: "bookmark",
          key: "env",
          callback: () => {
            this.envOptions.envVisible = !this.envOptions.envVisible;
          },
        },
        {
          tooltip: "草稿",
          iconName: "bill",
          key: "draft",
          callback: () => {
            this.draftOptions.draftVisible = !this.draftOptions.draftVisible;
          },
        },
        {
          tooltip: "控制台",
          iconName: "terminal",
          key: "console",
          callback: () => {
            // this.draftOptions.draftVisible = !this.draftOptions.draftVisible;
            this.consoleVisible = !this.consoleVisible;
          },
        },
      ],
      envOptions: {
        envVisible: true,
        addEnvVisible: false,
        envForm: this.$form.createForm(this, { name: "envForm" }),
        autoExpandParent: true,
        expandedKeys: [],
        selectedKeys: [],
        envList: [],
      },
      draftOptions: {
        draftVisible: true,
        draft: {},
        draftId: null,
        draftList: [],
      },
      treeData: [
        {
          title: "业务应用语境",
          key: "1",
          disableMention: true,
          slots: {
            icon: "appcase",
          },
          children: [],
        },
        {
          title: "剧本语境",
          key: "2",
          disableMention: true,
          slots: {
            icon: "scripts",
          },
          children: [],
        },
      ],
    };
  },
  created() {
    this.draftOptions.draftId = this.$route.params.scriptId;
    this.applicationCaseId = this.$route.params.id;
    scriptService.getDraftChapter(
      {
        draftChapter: {
          draftId: this.draftOptions.draftId,
        },
      },
      {
        onSuccess: (model, fModel, json) => {
          this.draftOptions.draftList = [];
          json.data.rows.forEach((item) => {
            const arr = item.content
              .split("。")
              .filter((s) => s !== "")
              .map((s) => s + "。");
            this.draftOptions.draftList = this.draftOptions.draftList.concat(arr);
            scriptService.getAnalyseByDraftChapterId(
              {
                draftChapterId: item.id,
              },
              {
                onSuccess: (model, fModel, json) => {
                  this.script = { text: json.data.rows.code, html: `<p>${json.data.rows.code}</p>` };
                },
                onFail: (msg, json) => {
                  console.log(msg, json);
                },
              }
            );
          });
          // json.data.rows.map((item) => {
          //   item.html = item.content;
          //   return item;
          // });
        },
        onFail: (msg, json) => {
          console.log(msg, json);
        },
      }
    );
    applicationService.getDraft(
      {
        draft: {
          id: this.draftOptions.draftId,
        },
        pageInfo: {
          pageSize: 20,
          currentPage: 1,
        },
      },
      {
        onSuccess: (model, fModel, json) => {
          let result = json.data.rows;
          console.log(result[0]);
          // if (result.length) {
          //   this.draftOptions.draft = result[0];
          // }
        },
        onFail: (msg, json) => {
          console.log(msg, json);
        },
      }
    );
    applicationService.getApplicationCaseById(
      {
        applicationCaseId: this.applicationCaseId,
      },
      {
        onSuccess: (model, fModel, json) => {
          bandService.getRelationBandsEnvironment(
            {
              bandList: json.data.rows.bandList.map((item) => item.bandId),
            },
            {
              onSuccess: (model, fModel, json) => {
                this.initEnvironments(json.data.rows);
              },
              onFail: (msg, json) => {
                console.log(msg, json);
              },
            }
          );
        },
        onFail: (msg, json) => {
          console.log(msg, json);
        },
      }
    );
  },
  methods: {
    initEnvironments(envs) {
      for (let i = 0; i < envs.length; i++) {
        let env = envs[i];
        let bandInfo = env.bandInfo.rows[0];
        let bandEnvs = [];
        bandEnvs.push({
          key: `${bandInfo.objID}-chatrooms`,
          title: "消息板",
          disableMention: true,
          children: env.bandChatrooms.map((item) => {
            return { key: item.chatroomId, title: item.chatroomName };
          }),
        });
        bandEnvs.push({
          key: `${bandInfo.objID}-documents`,
          title: "文档",
          disableMention: true,
          children: env.bandDocuments.map((item) => {
            return { key: item.documentId, title: item.documentName };
          }),
        });
        bandEnvs.push({
          key: `${bandInfo.objID}-Roles`,
          title: "角色",
          disableMention: true,
          children: env.bandRoles.map((item) => {
            return { key: item.roleId, title: item.roleName };
          }),
        });
        bandEnvs.push({
          key: `${bandInfo.objID}-Tools`,
          title: "工具",
          disableMention: true,
          children: env.bandTools.map((item) => {
            return { key: item.toolId, title: item.toolName };
          }),
        });
        bandEnvs.push({
          key: `${bandInfo.objID}-Users`,
          title: "用户",
          disableMention: true,
          children: env.bandUsers.map((item) => {
            return { key: item.userId, title: item.userName };
          }),
        });
        this.treeData[0].children.push({ key: bandInfo.objID, title: bandInfo.name, children: bandEnvs });
      }
      this.envOptions.envList = generateList(new Set(), this.treeData);
    },
    changeSelectionColor(e) {
      let sln = window.getSelection();
    },
    onFontColorVisibleChange(visible) {
      this.toolOptions.fontColorActive = visible;
    },
    handleNewEnv() {
      this.envOptions.addEnvVisible = true;
    },
    handleAddEnv() {
      this.envOptions.envForm.validateFields((err, formData) => {
        if (!err) {
          this.envOptions.addEnvVisible = false;
          this.treeData[1].children.push({
            title: `${formData.key}: ${formData.value}`,
            name: formData.key,
            value: formData.value,
            key: `${this.treeData[1].key}-${this.treeData[1].children.length}`,
          });
          this.envOptions.envList = generateList(new Set(), this.treeData);
        }
      });
    },
    onExpand(expandedKeys) {
      this.envOptions.expandedKeys = expandedKeys;
      this.envOptions.autoExpandParent = false;
    },
    onEnvSearchChange: function(e) {
      const value = e.target.value;
      if (value === "") {
        this.envOptions.selectedKeys = [];
        return;
      }
      this.envOptions.expandedKeys = [];
      this.envOptions.selectedKeys = [];
      const expandedKeys = this.envOptions.envList
        .map((item) => {
          if (item.title.indexOf(value) > -1) {
            this.envOptions.selectedKeys.push(item.key);
            return getParentKey(item.key, this.treeData);
          }
          return null;
        })
        .filter((item, i, self) => item && self.indexOf(item) === i);
      Object.assign(this.envOptions, {
        expandedKeys,
        searchValue: value,
        autoExpandParent: true,
      });
    },
  },
  beforeDestroy() {
    clearInterval(this.timer);
    this.timer = null;
  },
};
</script>
<style lang="scss">
.workarea-container {
  height: 100%;
  width: 100%;
  overflow: hidden;
}
.ant-tooltip-hidden {
  display: none;
}

.draft-container,
.env-container {
  height: calc(100% - 4rem);
  width: 20rem;
  position: fixed;
  top: 4.5rem;
  box-shadow: 0 1px 5px #ced4da;
  background: #f8f8f8;
  z-index: 300;
  transition: right 0.2s ease 0s;
  overflow: hidden;
  padding: 0 1rem;
}
.env-container .env-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  .col-title {
    padding-left: 0 0 0 1rem;
    height: 3rem;
    line-height: 3rem;
    font-size: 1rem;
    font-weight: 700;
  }
  .i-icon {
    opacity: 0.8;
  }
  .i-icon:hover {
    opacity: 1;
  }
}
.env-container {
  .ant-tree {
    height: calc(100% - 6rem);
    overflow-y: auto;
    margin-bottom: 2rem;
  }
}
</style>
