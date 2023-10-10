<template>
  <div class="appcase-container">
    <div class="appcase-header-wrapper">
      <div class="appcase-info-wrapper">
        <div class="appcase-title-wrapper">
          <h2 class="appcase-title">{{ curApplicationCase.name }}</h2>
          <a-tag color="blue" style="margin:0 0.625rem;"> {{ curApplicationCase.businessName }} </a-tag>
        </div>
        <div class="appcase-tags">
          <div class="appcase-tag">
            <div class="icon-wrapper">
              <peoples theme="filled" size="0.7rem" fill="#FFF" />
            </div>
            <span class="appcase-tag-text">{{ curApplicationCase.creatorName }}</span>
          </div>
          <div class="appcase-tag">
            <div class="icon-wrapper">
              <preview-open theme="multi-color" size="0.88rem" :fill="['#FFF', '#FFF', '#86909c', '#86909c']" />
            </div>
            <span class="appcase-tag-text">{{ curApplicationCase.views }}</span>
          </div>
          <div class="appcase-tag">
            <div class="icon-wrapper">
              <schedule theme="multi-color" size="0.76rem" :fill="['#fff', '#fff', '#86909c', '#86909c']" />
            </div>
            <span class="appcase-tag-text">{{ curApplicationCase.updateTime }}</span>
          </div>
        </div>
      </div>
      <div class="appcase-operation-wrapper">
        <div class="appcase-operation">
          <like theme="outline" size="0.875rem" fill="#233041" />
          <span>点赞</span>
          <a-divider type="vertical" style="background:'rgba(0,0,0,0.12)'" />
          <span>{{ curApplicationCase.likes }}</span>
        </div>
        <div class="appcase-operation" :style="{ color: '#fff', background: '#1890ff' }">
          <copy theme="outline" size="0.875rem" fill="#fff" />
          <span>立即部署</span>
        </div>
      </div>
    </div>
    <a-tabs class="appcase-tabs" v-model="activeKey" @change="onTabClick" :animated="{ tabPane: false }">
      <a-tab-pane key="scripts" tab="剧本集">
        <a-list :grid="{ gutter: 16, column: 4 }" :data-source="draftList">
          <a-list-item slot="renderItem" slot-scope="item, index">
            <!-- target="_blank" 跳转页面后就不是工具调用了 -->
            <router-link :to="`/appcase/${applicationCaseId}/${item.id}`">
              <a-card class="app-card" hoverable>
                <img
                  slot="cover"
                  alt="example"
                  src="https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png"
                />
                <h3 class="draft-title">{{ item.name }}</h3>
                <div class="draft-description">
                  {{ item.description }}
                </div>
                <a-tag color="blue">
                  {{ item.creatorName }}
                </a-tag>
                <a-tag color="orange">
                  {{ item.createTime }}
                </a-tag>
              </a-card>
            </router-link>
          </a-list-item>
        </a-list>
      </a-tab-pane>
      <a-tab-pane key="talk" tab="讨论">
        讨论
      </a-tab-pane>
    </a-tabs>
    <add-one
      v-if="activeKey === 'scripts'"
      class="add-script"
      theme="filled"
      size="3rem"
      fill="#1890ff"
      strokeLinecap="square"
      @click="onCreateScript"
    />
    <a-modal v-model="createVisible" title="创建业务应用" width="50%">
      <template slot="footer">
        <a-button key="back" @click="handleCancel">
          取消
        </a-button>
        <a-button key="submit" type="primary" @click="handleCreateScript">
          创建
        </a-button>
      </template>
      <a-form :form="scriptForm" :labelCol="{ span: 5 }" :wrapperCol="{ span: 19 }">
        <a-form-item label="业务剧本名称">
          <a-input
            v-decorator="['name', { rules: [{ required: true, message: '请输入业务剧本名称！' }] }]"
            placeholder="请输入业务剧本名称"
          />
        </a-form-item>
        <a-form-item label="业务剧本描述">
          <a-input
            v-decorator="['description', { rules: [{ required: true, message: '请输入业务剧本描述！' }] }]"
            placeholder="请输入业务剧本描述"
          />
        </a-form-item>
        <a-form-item label="封面">
          <a-upload
            :remove="handleRemove"
            :before-upload="beforeUpload"
            :accept="getAccept(['image'])"
            :file-list="fileList"
            name="cover"
            list-type="picture"
            v-decorator="[
              'fileList',
              {
                rules: [{ message: '请添加剧本封面！' }],
              },
            ]"
          >
            <a-button> <a-icon type="upload" /> 请上传剧本封面 </a-button>
          </a-upload>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>
<script>
import { Peoples, Like, PreviewOpen, Schedule, AddOne, Copy } from "@icon-park/vue";
import { mapState } from "vuex";
import Utils from "common/utils";
import ApplicationService from "@/service/application_service";
const applicationService = new ApplicationService();
export default {
  name: "AppCase",
  components: { Peoples, Like, PreviewOpen, Schedule, AddOne, Copy },
  data() {
    return {
      applicationCaseId: null,
      curApplicationCase: {},
      activeKey: "scripts",
      draftList: [],
      createVisible: false,
      scriptForm: this.$form.createForm(this, { name: "scriptForm" }),
      fileList: [],
    };
  },
  computed: {
    ...mapState(["ST_CURRENT"]),
  },
  beforeCreate() {},
  created() {
    this.applicationCaseId = this.$route.params.id;
    this.freshData();
  },
  methods: {
    getAccept: Utils.getAccept,
    beforeUpload: function(file) {
      this.fileList = [file];
      return false;
    },
    onTabClick: function(activeKey) {
      this.activeKey = activeKey;
    },
    addScript: function() {},
    onCreateScript: function() {
      this.createVisible = true;
    },
    handleCancel() {
      this.createVisible = false;
    },
    handleRemove: function(file) {
      const index = this.fileList.indexOf(file);
      const newFileList = this.fileList.slice();
      newFileList.splice(index, 1);
      this.fileList = newFileList;
    },
    handleCreateScript: function() {
      this.scriptForm.validateFields((err, formData) => {
        if (!err) {
          // this.draftList.push({ name: formData.name });
          this.createVisible = false;
          applicationService.addDraft(
            {
              draft: {
                name: formData.name,
                description: formData.description,
                applicationCaseId: this.applicationCaseId,
                creatorId: this.ST_CURRENT.userId,
                creatorName: this.ST_CURRENT.userName,
              },
            },
            {
              onSuccess: (model, fModel, json) => {
                this.freshData();
              },
              onFail: (msg, json) => {
                console.log(msg, json);
              },
            }
          );
        }
      });
      // this.data.push({name:});
    },
    freshData() {
      applicationService.getApplicationCaseById(
        {
          applicationCaseId: this.applicationCaseId,
        },
        {
          onSuccess: (model, fModel, json) => {
            console.log(json.data.rows);
            this.curApplicationCase = json.data.rows;
          },
          onFail: (msg, json) => {
            console.log(msg, json);
          },
        }
      );
      applicationService.getDraft(
        {
          draft: {
            applicationCaseId: this.applicationCaseId,
            name: "",
          },
          pageInfo: {
            pageSize: 20,
            currentPage: 1,
          },
        },
        {
          onSuccess: (model, fModel, json) => {
            this.draftList = json.data.rows;
          },
          onFail: (msg, json) => {
            console.log(msg, json);
          },
        }
      );
    },
  },
};
</script>
<style lang="scss">
.appcase-container {
  max-width: 1300px;
  width: 95%;
  margin: 1rem auto;
  height: calc(100% - 2rem);
  overflow: hidden;
  .appcase-header-wrapper {
    display: flex;
    justify-content: space-between;
    align-items: center;
    flex-wrap: nowrap;
    .appcase-operation-wrapper {
      display: flex;
      flex: 0 0 auto;
      padding-left: 3rem;
      height: 2.6rem;
      font-size: 0.875rem;
      overflow: hidden;

      .appcase-operation {
        display: flex;
        justify-content: center;
        align-items: center;
        flex-shrink: 0;
        margin-right: 0.8rem;
        border-radius: 0.375rem;
        line-height: 2.6rem;
        padding: 0.75rem 1.2rem;
        font-size: 0.875rem;
        font-weight: 700;
        background: rgba(0, 0, 0, 0.06);
        cursor: pointer;
        .ant-divider.ant-divider-vertical {
          background: rgba(0, 0, 0, 0.12);
        }
        .i-icon {
          margin-right: 0.5rem;
        }
        &:hover {
          background: rgba(0, 0, 0, 0.08);
        }
      }
    }
    .appcase-info-wrapper {
      display: flex;
      flex-direction: column;
      .appcase-title-wrapper {
        display: flex;
        overflow: hidden;
        align-items: center;
        h2.appcase-title {
          font-weight: 700;
          font-size: 1.5rem;
          line-height: 2.25rem;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
        }
      }
    }
    .appcase-tags {
      display: flex;
      align-items: center;
      margin-top: 0.75rem;
      .appcase-tag {
        display: flex;
        align-items: center;
        color: #86909c;
        font-size: 1rem;
        .icon-wrapper {
          background: #86909c;
          border-radius: 0.15rem;
          width: 1rem;
          height: 1rem;
          display: flex;
          align-items: center;
          justify-content: center;
          margin-right: 0.5rem;
        }
        span.appcase-tag-text {
          font-size: 0.8rem;
        }
        margin-right: 0.8rem;
      }
    }
  }
  .appcase-tabs {
    margin-top: 0.8rem;
  }
}
.add-script {
  position: fixed;
  bottom: 2rem;
  left: 50%;
  transform: translateX(-50%);
  background: radial-gradient(#1890ff, #3d96e9);
  border-radius: 50%;
  opacity: 0.8;
  cursor: pointer;
  &:hover {
    box-shadow: 0 0 1rem rgba(24, 144, 255, 0.4);
    opacity: 1;
  }
}
.draft-title,
.draft-description {
  margin-bottom: 0.5rem;
}
</style>
