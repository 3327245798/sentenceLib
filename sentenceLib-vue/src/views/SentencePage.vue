<template>
  <div>
  <div class="sentencepage-container">
    <div class="sentencelib-container">
      <div class="topbuttoon">
      <a-button class="a" type="primary"  @click="openAddSentence">
        新增句型
      </a-button>
         <a-input-group
        compact
        class="a"
        style="display: flex; justify-content: flex-start; width: 340px"
      >
        <a-select
          style="width: 40%"
          default-value="编辑者"
          v-model="searchType"
        >
          <a-select-option value="编辑者"> 编辑者 </a-select-option>
          <a-select-option value="句型状态"> 句型状态 </a-select-option>
        </a-select>
        <a-input-search
          placeholder="根据条件进行搜索"
          enter-button
          v-model="searchName"
          @search="onSearch"
        >
        </a-input-search>
      </a-input-group>
      <a-button class="a" type="primary" @click="refresh">
          刷新
        </a-button>
      <!-- <a-input-search
      class="a"
       placeholder="请输入创建者名字进行搜索" 
       enter-button 
       v-model="searchName"
       @search="onSearch" >
      </a-input-search> -->
      </div>

      <a-table
        bordered
        :rowKey="
          (record, index) => {
            return index;
          }
        "
        :loading="tableLoading"
        @change="handleTableChange"
        :columns="columns"
        :data-source="sentenceData"
      >
        <a slot="name" slot-scope="text" style="font-size:15px">{{ text }}</a>
        <span slot="customTitle" ><a-icon type="smile-o" /> 名称</span>

      
        <span slot="creatorName" slot-scope="text">
          <a-tag 
          style="font-size:15px"
          color="purple">
            {{ text }}
          </a-tag>
        </span>
        <span slot="type" slot-scope="type">
          <a-tag
          style="font-size:15px"
            v-for="tag in type"
            :key="tag"
            :color="tag === '1' ? 'green' : 'geekblue'"
          >
            {{ tag === "1" ? "标准型句型库" : "用户自定义句型库" }}
          </a-tag>
        </span>
        <span slot="sentenceStatus" slot-scope="text" >
          <a-tag
          style="font-size:15px"
          v-if="text=='0'"
           :key="tag"
          color='orange'>
            {{"审核中"}}
          </a-tag>
          <a-tag
          style="font-size:15px"
          v-else-if="text=='1'"
           :key="tag"
          color='purple'>
            {{"审核未通过"}}
          </a-tag>
           <a-tag
           style="font-size:15px"
          v-else-if="text=='2'"
           :key="tag"
          color='cyan'>
            {{"审核通过开发中"}}
          </a-tag>
           <a-tag
           style="font-size:15px"
          v-else-if="text=='3'"
           :key="tag"
          color='green'>
            {{"开发完成启用中"}}
          </a-tag>
           <a-tag
           style="font-size:15px"
          v-else-if="text=='4'"
           :key="tag"
          color='red'>
            {{"禁用"}}
          </a-tag>
        </span>
        <span slot="action" slot-scope="text, record">
          <a-button
            @click="openModifySentence(record)"
            type="primary"
            size="small"
            ghost
          >
            编辑
          </a-button>
          <a-divider type="vertical" />
          <a-popconfirm
            cancelText="取消"
            okText="确定"
            title="确认删除?"
            @confirm="deleteSentence(record)"
          >
            <a-button type="danger" size="small" ghost> 删除 </a-button>
          </a-popconfirm>
        </span>
      </a-table>
      <a-modal
        :title="title"
        :visible="addSentenceVisible"
        :confirm-loading="confirmLoading"
        okText="提交"
        cancelText="取消"
        @ok="handleOk('sentenceForm')"
        @cancel="handleCancel"
        :mask-closable="false"
      >
        <a-form-model
          ref="sentenceForm"
          :model="form"
          :label-col="labelCol"
          :wrapper-col="wrapperCol"
        >
          <a-form-model-item
            label="句型库类型"
            prop="type"
            :rules="[
              { required: true, message: '请输入内容', trigger: 'blur' },
            ]"
          >
            <a-radio-group
              name="sentenceLibType"
              v-model="form.type"
              :default-value="1"
            >
              <a-radio value="1"> 标准型句型库 </a-radio>
              <a-radio value="2"> 用户自定义型句型库 </a-radio>
            </a-radio-group>
          </a-form-model-item>

          <a-form-model-item
            label="句型名称"
            prop="name"
            
            :rules="[
              { required: true, message: '请选择句型名称', trigger: 'blur' },
            ]"
          >
            <a-select
              v-model="form.name"
              placeholder="请选择句型名称"
            >
              <!-- 
              :filter-option="filterOption"
                :show-arrow="false"
                show-search
                 :default-active-first-option="true"
                :not-found-content="null" -->

              <a-select-option
                :key="st.sentenceNameId"
                v-for="st in sentenceNames"
                :value="st.name"
              >
                {{ st.name }}
              </a-select-option>
            </a-select>
          </a-form-model-item>

          <a-form-model-item
            label="句型"
            prop="description"
             placeholder="请输入句型"
            :rules="[
              { required: true, message: '请输入句型', trigger: 'blur' },
            ]"
          >
            <!-- <a-input v-model="form.description" /> -->

            <a-textarea
              v-model="form.description"
              placeholder="请输入句型"
              :auto-size="{ minRows: 2, maxRows: 6 }"
            />
          </a-form-model-item>
          <!-- <a-form-model-item
            label="句型序列化表示"
            prop="serialization"
            placeholder="请输入序列化表示"
            :rules="[
              { required: true, message: '序列化表示', trigger: 'blur' },
            ]"
          >
            <a-input v-model="form.serialization" />
          </a-form-model-item> -->

          <a-form-model-item label="句型实例" prop="example">
            <a-textarea
              v-model="form.example"
              placeholder="请为句型写一个实例"
              :auto-size="{ minRows: 2}"
            />
          </a-form-model-item>
        
        </a-form-model>
        <template slot="footer">
          <a-button @click="handleCancel">取消</a-button>
          <a-button type="primary" @click="handleSaveToLocale('sentenceForm')">保存到本地</a-button>
          <a-button type="primary" @click="handleOk('sentenceForm')"
            >提交到数据库</a-button
          >
        </template>
        </a-modal>
      
    </div>
  </div>
  <div class="other-container"></div>
    </div>
</template>



<script>
import SentenceService from "@/service/sentence_service";
const sentenceService = new SentenceService();
const columns = [
  {
    //title: "名称",
    dataIndex: "name",
    key: "name",
    slots: { title: "customTitle" },
    scopedSlots: { customRender: "name" },
    align: "center",
    width: 100,
    filters: [
      { text: "循环型", value: "循环型" },
      { text: "定时型", value: "定时型" },
      { text: "监听型", value: "监听型" },
      { text: "条件型", value: "条件型" },
      { text: "赋值型", value: "赋值型" },
      { text: "命令型", value: "命令型" },
      { text: "计算型", value: "计算型" },
    ],
    onFilter: (value, record) => record.name.indexOf(value) === 0,
  },
  {
    title: "描述",
    dataIndex: "description",
    key: "description",
    align: "center",
  },

  {
    title: "例子",
    dataIndex: "example",
    key: "example",
    align: "center",
  },
  {
    title: "序列化表示",
    dataIndex: "serialization",
    key: "serialization",
    align: "center",
  },

  {
    title: "句型状态",
    dataIndex: "status",
    key: "status",
    align: "center",
    scopedSlots: { customRender: "sentenceStatus" },

  },
  {
    title: "类型",
    key: "type",
    dataIndex: "type",
    scopedSlots: { customRender: "type" },
    align: "center",
    filters: [
      { text: "用户自定义型", value: "2" },
      { text: "标准型", value: "1" },
    ],
    onFilter: (value, record) => record.type.indexOf(value) === 0,
    /* onFilter: (value, record) => {
        if(record.type!=null&&record.type.indexOf(value)>-1){
        record.type.indexOf(value) === 0
      }
        } 这个方法没用，过滤出来的是空的，没数据,加个return就有数据了*/
  },
  {
    title: "编辑者",
    key: "creatorName",
    dataIndex: "creatorName",
    scopedSlots: { customRender: "creatorName" },
    align: "center",
    width: 80,
   /*  根据创建者姓名进行搜索，前台实现，不过现在不用了，因为加了个搜索框，用后台来实现了
   filters: [
      { text: "王哲", value: "王哲" },
      { text: "黄威", value: "黄威" },
      { text: "李泽权", value: "李泽权" },
      { text: "梁杰潮", value: "梁杰潮" },
      { text: "梁子文", value: "梁子文" },
      { text: "李金辉", value: "李金辉" },
    ], */

  /*   onFilter: (value, record) => {
      if (
        record.creatorName != null &&
        record.creatorName.indexOf(value) > -1
      ) {
        return record.creatorName.indexOf(value) === 0;
      }
    }, */
  },
  /*  {
    title: "创建时间",
    key: "createTime",
    dataIndex: "createTime",
    align: "center",
    width: 120,
    sorter: (a, b) =>
      new Date(a.createTime).getTime() - new Date(b.createTime).getTime(),
   
  }, */
  {
    title: "修改时间",
    key: "updateTime",
    dataIndex: "updateTime",
    align: "center",
    width: 120,
    sorter: (a, b) =>
      new Date(a.updateTime).getTime() - new Date(b.updateTime).getTime(),
    //sortOrder: sortedInfo.columnKey === 'updateTime' && sortedInfo.order,
    // ellipsis: true,
  },

  {
    title: "操作",
    key: "action",
    scopedSlots: { customRender: "action" },
    align: "center",
    width: 80,
  },
];
const sentenceTamplate = JSON.stringify({
  name: "",
  description: "",
  //action: "",
  serialization: "",
  example: "",
  type: "1",
  status:0,
  creatorName: "",
  updateTime: "",
  creatorId: "",
  createTime: "",
});

export default {
  name: "SentencePage",
  components: {},
  data() {
    return {
      searchName:"",
      searchType: "编辑者",
      sentenceNames: [
        { sentenceNameId: 1, name: "赋值型" },
        { sentenceNameId: 2, name: "循环型" },
        { sentenceNameId: 3, name: "监听型" },
        { sentenceNameId: 4, name: "定时型" },
        { sentenceNameId: 5, name: "条件型" },
        { sentenceNameId: 6, name: "绑定型" },
        { sentenceNameId: 7, name: "命令型" },
        { sentenceNameId: 8, name: "计算型" },
      ],
      sentenceData: [],
      columns,
      pagination: {
        page: 1,
        count: 10,
        total: 0,
      },
      tableLoading: false,
      searchStr: "",
      Visible: false,
      addSentenceVisible: false,
      confirmLoading: false,
      labelCol: { span: 6 },
      wrapperCol: { span: 12 },
      title: "",
      actionType: "ADD",
      form: JSON.parse(sentenceTamplate),
      rules: {
        name: [{ required: true, message: "请输入内容", trigger: "blur" }],
      },
      sortedInfo: null,
      
    };
  },

  computed: {},
  beforeCreate() {},
  created() {
    this.refresh();
  },
  watch: {},
 
  methods: {
    refresh() {
      sentenceService.getAllSentence(
        {
          //type: 1,
        },
        {
          onSuccess: (model, fModel, json) => {
            console.log(json);

            this.sentenceData = json.data.rows.sentence;
          
          },
          onFail: (msg, json) => {
            console.log(msg, json);
          },
          
        }
      );
    },
     toHome() {
      this.$router.push({ name: "Home" });
    },
    openAddSentence() {
      this.actionType = "ADD";
      this.title = "添加句型信息";
      this.addSentenceVisible = true;
      this.form = JSON.parse(sentenceTamplate);
    },

    openModifySentence(sentence) {
      this.actionType = "MODIFY";
      this.addSentenceVisible = true;
      this.title = "修改句型信息";
      this.form = sentence;
      let has =
        this.sentenceNames.findIndex(
          (t) => t.sentenceNameId == sentence.sentenceNameId
        ) > -1;

      if (!has) {
        this.sentenceNames.push({
          sentenceNameId: sentence.sentenceNameId,
          name: sentence.name,
        });
      }
    },
    async submit() {
      this.confirmLoading = true;

      try {
        let result;
        if (this.actionType == "ADD") {
        
          result = await sentenceService.createSentence(this.form);
        } else {
          result = await sentenceService.updateSentence(this.form);
        }

        this.confirmLoading = false;
        this.addSentenceVisible = false;
        this.refresh();
      } catch (msg) {
        this.confirmLoading = false;
        console.log(msg);
      }
    },
    async handleOk(formName) {
      this.$refs[formName].validate((valid) => {
        if (!valid) {
          return false;
        }
        console.log("数据校验成功，字段都是合理的,可以提交保存了");
        this.submit();
      });
    },
    handleCancel() {
      this.addSentenceVisible = false;
      //this.addPropsVisible = false;
    },
     async handleSaveToLocale(){
      let content = ""
     Object.entries( this.form).forEach(e => {
     // console.log(e);
      content += e[0] + ":" + e[1] + "\n";
     })

    this.doSave(content, "txt", this.form.name);
    },
    doSave(value, type, name) {
        var blob;
        if (typeof window.Blob == "function") {
          blob = new Blob([value], {
            type: type
          });
        } else {
          var BlobBuilder = window.BlobBuilder || window.MozBlobBuilder || window.WebKitBlobBuilder || window.MSBlobBuilder;
          var bb = new BlobBuilder();
          bb.append(value);
          blob = bb.getBlob(type);
        }
        var URL = window.URL || window.webkitURL;
        var bloburl = URL.createObjectURL(blob);
        var anchor = document.createElement("a");
        if ('download' in anchor) {
          anchor.style.visibility = "hidden";
          anchor.href = bloburl;
          anchor.download = name;
          document.body.appendChild(anchor);
          var evt = document.createEvent("MouseEvents");
          evt.initEvent("click", true, true);
          anchor.dispatchEvent(evt);
          document.body.removeChild(anchor);
        } else if (navigator.msSaveBlob) {
          navigator.msSaveBlob(blob, name);
        } else {
          location.href = bloburl;
        }

        this.addSentenceVisible = false;

      },

    handleTableChange(pagination, filters, sorter) {
      //<a-table>里面有一个 @change="handleTableChange"
      console.log(pagination);
      const pager = { ...this.pagination };
      pager.page = pagination.current;
      this.pagination = pager;
      this.fetch({
        count: pagination.pageSize,
        page: pagination.current,
        sortField: sorter.field,
        sortOrder: sorter.order,
        ...filters,
      });
    },
    filterOption(input, option) {
      return (
        option.componentOptions.children[0].text
          .toLowerCase()
          .indexOf(input.toLowerCase()) >= 0
      );
    },
    
    handleSelectSentenceNameChange(name) {
      this.form.name = this.sentenceNames.filter((t) => t.name == name)[0].name;
    },
    async fetch(params = {}) {
      this.tableLoading = true;
      //query:{id:{eq:3243}}
      //params.query = { wordid: { eq: this.id } };
      const result = await sentenceService.getAllSentence();
      this.data = result.rows;

      this.pagination.total = result.total;
      this.tableLoading = false;
    },
    async deleteSentence(id) {
      await sentenceService.deleteSentence(id);

      this.fetch(this.pagination);
      this.refresh();
    },
   /*  onSearch(value){
      console.log(value);
      sentenceService.getAllSentenceByCreatorName(
        {
          creatorName: value,
        },
        {
          onSuccess: (model, fModel, json) => {
            console.log(json);
            this.sentenceData = json.data.rows.sentence;
          
          },
          onFail: (msg, json) => {
            console.log(msg, json);
          },
          
        }
      );


    }, */
    onSearch() {
      if (this.searchType == "编辑者") {
        this.onSearchByCreatorName(this.searchName);
      } else {
        this.onSearchByStatus(this.searchName);
      }
    },
    onSearchByCreatorName(value) {
      console.log(value);
      if(value==''){this.refresh()};
      sentenceService.getAllSentenceByCreatorName(
        {
          creatorName: value.trim(),
        },
        {
          onSuccess: (model, fModel, json) => {
            console.log(json);
            this.sentenceData = json.data.rows.sentence;
          },
          onFail: (msg, json) => {
            console.log(msg, json);
          },
        }
      );
    },
    onSearchByStatus(value) {
      console.log(value);
      let status = 0; //句型有5种状态，0表示审核中，1表示审核未通过，2表示审核通过但未开发，3表示已开发并启用，4表示禁用
      if(value==''){this.refresh()};
      if (value.trim() == "审核中") {//0是审核中
        status = 0;
      } else if (value.trim() == "审核未通过") {//1是审核未通过
        status = 1;
      } else if (value.trim() == "审核通过开发中") {
        status = 2;
      } else if (value.trim() == "开发完成启用中") {
        status = 3;
      } else if (value.trim() == "禁用") {
        status = 4;
      }
      sentenceService.getAllSentenceByStatus(
        {
          status: status,
        },
        {
          onSuccess: (model, fModel, json) => {
            console.log(json);
            this.sentenceData = json.data.rows.sentence;
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
.sentencepage-container {
  height: 100%;
  display: flex;
  .sentencelib-container {
    flex: 1;
    position: relative;
    background: #fff;
    height: 100%;
    min-width: 1285px;
    padding-left: 30px;
    padding-right: 30px;
    padding-top: 10px;
    padding-left: 60px;
    padding-right: 80px;
  }
  /* .other-container {
    background: #f7f9fc;
    min-width: 30rem;
  }
 */
  .ant-form-item-control {
    position: relative;
    line-height: 40px;
    zoom: 1;
    border-block-color: #123456;
  }
  .topbuttoon{
    display: flex;
    .a{
    margin-right: 18px;
      }
  }

}
</style>