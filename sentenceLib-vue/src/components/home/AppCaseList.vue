<template>
  <div style="height:100%;">
    <div class="search-container row">
      <div class="search-top-wrapper">
        <img class="search-show" :src="searchImage" alt="search" />
        <div class="search-top">
          <h2 class="row-title search-title">业务应用库</h2>
          <a-input-search
            v-model="searchName"
            size="large"
            class="search-input"
            placeholder="搜索"
            @search="onSearch"
          />
          <div class="row-description search-description">
            搜索所想，精彩即现
          </div>
        </div>
      </div>

      <a-menu
        class="menu-sort"
        v-model="sortOption"
        mode="horizontal"
        style="text-align: start"
        @click="onMenuItemClick"
      >
        <a-sub-menu popupClassName="business-wrapper">
          <span slot="title">{{ curBusiness }}</span>
          <a-menu-item :key="item.id" v-for="item in businessList">
            {{ item.name }}
          </a-menu-item>
        </a-sub-menu>
        <a-menu-item key="op1"> 应用精选 </a-menu-item>
        <a-menu-item key="op2"> 最新发布 </a-menu-item>
        <a-menu-item key="op3">
          <fire
            theme="multi-color"
            size="1.125rem"
            :fill="['#ff4700', '#ff622f', '#ff622f', '#ff622f']"
            :style="{ verticalAlign: 'text-bottom', marginRight: '0.25rem' }"
          />最多使用
        </a-menu-item>
        <a-menu-item key="op4"> 我的关注 </a-menu-item>
        <a-button class="add-appcase" type="primary" @click="onCreateAppcase">创建应用</a-button>
      </a-menu>
      <a-list class="app-list" :grid="{ gutter: 16, column: 4 }" :data-source="appList">
        <a-list-item slot="renderItem" slot-scope="item, index">
          <a-card class="app-card" hoverable @click="onCardClick(item.id)">
            <img slot="cover" alt="example" src="https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png" />
            <div class="app-fav">
              <Like v-if="item.isLiked" theme="filled" size="0.75rem" fill="#ff5975" />
              <Like v-else theme="outline" size="0.75rem" fill="#233041" />
              <span class="app-fav-count" aria-label="收藏数">{{ item.likes }}</span>
            </div>
            <h3 class="app-title">{{ item.name }}</h3>
            <p class="app-desc">
              {{ item.description }}
            </p>
            <div class="app-tag-group">
              <a-tag color="blue"> {{ item.businessName }} </a-tag>
              <a-tag color="purple"> {{ item.creatorName }} </a-tag>
            </div>
          </a-card>
        </a-list-item>
      </a-list>
    </div>
    <a-modal v-model="createVisible" title="创建业务应用" width="50%">
      <template slot="footer">
        <a-button key="back" @click="handleCancel">
          取消
        </a-button>
        <a-button key="submit" type="primary" @click="handleCreateScript">
          创建
        </a-button>
      </template>
      <a-form :form="appCaseForm" :labelCol="{ span: 3 }" :wrapperCol="{ span: 21 }">
        <a-form-item label="名称">
          <a-input
            v-decorator="['name', { rules: [{ required: true, message: '请输入业务应用名称！' }] }]"
            placeholder="请输入业务应用名称"
          />
        </a-form-item>
        <a-form-item label="描述">
          <a-input
            v-decorator="['description', { rules: [{ required: true, message: '请输入业务应用描述！' }] }]"
            placeholder="请输入业务应用描述"
          />
        </a-form-item>
        <a-form-item label="行业类型">
          <a-select
            placeholder="请选择行业类型"
            v-decorator="['businessId', { rules: [{ required: true, message: '请选择行业类型！' }] }]"
          >
            <a-select-option v-for="item in businessList" :key="item.id">
              {{ item.name }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="业务区">
          <a-select
            placeholder="请选择业务区"
            mode="multiple"
            v-decorator="['bandList', { rules: [{ required: true, message: '请选择业务区！' }] }]"
            :maxTagCount="3"
            :filterOption="onSearchFilter"
          >
            <a-select-option v-for="item in bandList" :key="item.objID">
              {{ item.name }}( id : {{ item.objID }} )
            </a-select-option>
          </a-select>
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
                rules: [{ message: '请添加业务应用封面！' }],
              },
            ]"
          >
            <a-button> <a-icon type="upload" /> 上传业务应用封面 </a-button>
          </a-upload>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>
<script>
import { mapState } from "vuex";
import { Like, Fire } from "@icon-park/vue";
import searchImage from "assets/appcase.png";
import Utils from "common/utils";
import ApplicationService from "@/service/application_service";
import BandService from "@/service/band_service";
const applicationService = new ApplicationService();
const bandService = new BandService();
export default {
  name: "AppCaseList",
  components: { Like, Fire },
  computed: {
    ...mapState(["ST_CURRENT"]),
  },
  data() {
    return {
      curBusiness: "行业类型",
      businessList: [],
      sortOption: ["op1"],
      appList: [],
      createVisible: false,
      appCaseForm: this.$form.createForm(this, { name: "appCaseForm" }),
      fileList: [],
      searchName: "",
      bandList: [],
      bandMap: null,
      searchImage,
    };
  },
  created() {
    this.form = this.$form.createForm(this);
    applicationService.getAllBusinessType(
      {},
      {
        onSuccess: (model, fModel, json) => {
          this.businessList = json.data.rows;
        },
        onFail: (msg, json) => {
          console.log(msg, json);
        },
      }
    );
    bandService.getBandList(
      { bandId: this.ST_CURRENT.bandId },
      {
        onSuccess: (model, fModel, json) => {
          this.bandList = json.data.rows.bands;
          this.bandMap = new Map();
          this.bandList.forEach((element) => {
            this.bandMap.set(element.objID, element.name);
          });
        },
        onFail: (msg, json) => {
          console.log(msg, json);
        },
      }
    );
    this.refreshData();
  },
  methods: {
    getAccept: Utils.getAccept,
    beforeUpload: function(file) {
      this.fileList = [file];
      return false;
    },
    onCardClick: function(id) {
      this.$router.push("/appcase/" + id);
    },
    onSearch: function(value) {
      this.sortOption = ["op1"];
      this.curBusiness = "行业类型";
      
      applicationService.getApplicationCase(
        {
         //value:this.router.params.value,//wz修改
          applicationCase: { isPrivate: 0, name: value },
          pageInfo: { pageSize: 20, currentPage: 1 },
        },
        {
          onSuccess: (model, fModel, json) => {
            this.appList = json.data.rows.map((item) => {
              item.isLiked = false;
              return item;
            });
          },
          onFail: (msg, json) => {
            console.log(msg, json);
          },
        }
      );
    },
    onSearchFilter: function(inputValue, option) {
      const item = this.bandList.filter((item) => item.objID === option.key)[0];
      return item.name.indexOf(inputValue) !== -1;
    },
    onMenuItemClick: function({ key, keyPath }) {
      let selectOne = this.businessList.filter((item) => item.id === key)[0];
      if (keyPath && keyPath.length > 1) {
        this.curBusiness = selectOne.name;
        applicationService.getApplicationCase(
          {
            applicationCase: { businessId: selectOne.id, isPrivate: 0 },
            pageInfo: { pageSize: 20, currentPage: 1 },
          },
          {
            onSuccess: (model, fModel, json) => {
              this.appList = json.data.rows.map((item) => {
                item.isLiked = false;
                return item;
              });
            },
            onFail: (msg, json) => {
              console.log(msg, json);
            },
          }
        );
      } else {
        this.curBusiness = "行业类型";
        applicationService.getApplicationCase(
          {
            applicationCase: { isPrivate: 0 },
            pageInfo: { pageSize: 20, currentPage: 1 },
          },
          {
            onSuccess: (model, fModel, json) => {
              const newData = json.data.rows.map((item) => {
                item.isLiked = false;
                return item;
              });
              switch (key) {
                case "op1":
                  newData.sort((a, b) => {
                    return b.description.length - a.description.length;
                  });
                  break;
                case "op2":
                  newData.sort((a, b) => {
                    const date1 = new Date(a.updateTime).getTime();
                    const date2 = new Date(b.updateTime).getTime();
                    return date2 - date1;
                  });
                  break;
                case "op3":
                  newData.sort((a, b) => {
                    return b.likes - a.likes;
                  });
                  break;
                case "op4":
                  break;
                default:
                  break;
              }
              this.appList = newData;
            },
            onFail: (msg, json) => {
              console.log(msg, json);
            },
          }
        );
      }
    },
    onCreateAppcase: function() {
      this.createVisible = true;
    },
    handleCancel() {
      this.createVisible = false;
    },
    handleCreateScript() {
      this.appCaseForm.validateFields((err, formData) => {
        if (!err) {
          this.createVisible = false;
          applicationService.addApplicationCase(
            {
              applicationCase: {
                name: formData.name,
                description: formData.description,
                businessId: formData.businessId,
                creatorId: this.ST_CURRENT.userId,
                creatorName: this.ST_CURRENT.userName,
                isPrivate: 0,
                bandList: formData.bandList.map((item) => {
                  return { bandID: item, bandName: this.bandMap.get(item) };
                }),
              },
            },
            {
              onSuccess: (model, fModel, json) => {
                console.log(json);
                this.refreshData();
              },
              onFail: (msg, json) => {
                console.log(msg, json);
              },
            }
          );
        }
      });
    },
    handleRemove: function(file) {
      const index = this.fileList.indexOf(file);
      const newFileList = this.fileList.slice();
      newFileList.splice(index, 1);
      this.fileList = newFileList;
    },
    refreshData() {
      applicationService.getApplicationCase(
        {
          applicationCase: { isPrivate: 0 },
          pageInfo: { pageSize: 20, currentPage: 1 },
        },
        {
          onSuccess: (model, fModel, json) => {
            this.appList = [
              ...json.data.rows
                .map((item) => {
                  item.isLiked = false;
                  return item;
                })
                .sort((a, b) => {
                  return b.description.length - a.description.length;
                }),
            ];
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
.menu-sort {
  position: relative;
  .add-appcase {
    position: absolute;
    top: 50%;
    right: 1rem;
    transform: translateY(-50%);
  }
}
.menu-sort.ant-menu-horizontal {
  height: 3rem;
  margin-bottom: calc(1rem + 16px) !important;
}
.menu-sort > .ant-menu-submenu,
.menu-sort > .ant-menu-item {
  height: 3rem;
  line-height: 3rem;
  min-width: 3.5rem;
  text-align: center;
}
.search-top-wrapper {
  position: relative;
  margin-bottom: calc(1rem + 16px);
  border-radius: 0.5rem;
  overflow: hidden;
  .search-show {
    position: absolute;
    height: 100%;
    width: 100%;
    left: 0;
    top: 0;
    z-index: 0;
  }
  .search-top {
    position: relative;
    padding: 2rem;
    .search-title {
      color: #fff;
      line-height: 1.25;
      font-size: 1.5rem;
      margin-bottom: 0.25rem;
      padding-bottom: 0.5rem;
      font-weight: 500;
    }
    .search-description {
      font-size: 1rem;
      color: rgba(255, 255, 255, 0.85);
      margin-bottom: 0;
    }
    .search-input {
      width: 24rem;
      margin-bottom: 1rem !important;
    }
  }
}
.app-list {
  padding-bottom: calc(1rem + 16px) !important;
  .ant-row {
    margin: 0;
  }
}
.app-list .ant-col:nth-child(4n + 1) {
  clear: both;
}
.business-wrapper {
  height: 12rem;
  overflow-y: auto;
  overflow-x: hidden;
}
</style>
