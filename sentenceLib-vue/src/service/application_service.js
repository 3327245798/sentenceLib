import BaseService from "./base_service";

export default class ApplicationService extends BaseService {
  //获取某一业务类型下的所有案例（根据业务Id获取所有应用案例）
  getAppcaseBybusTpyeId(params, callbacks) {
    return this.queryTemplate(params, callbacks, {
      ajaxParams: {
        action: "getAppcaseByBusinessTypeId", 
        businessTypeId: params.businessTypeId,
      },
      errorTag: "getAppcaseByBusinessTypeId",
      errorMsg: "获取该业务类型下的所有案例，获取失败",
      preventCache: true,
    });
  }
  // 查询业务应用列表失败
  getApplicationCase(params, callbacks) {
    return this.queryTemplate(params, callbacks, {
      ajaxParams: {
        action: "getApplicationCase",
        applicationCase: params.applicationCase,
        pageInfo: params.pageInfo,
      },
      errorTag: "getApplicationCase",
      errorMsg: "查询业务应用列表失败",
      preventCache: true,
    });
  }

  // 查询所有业务类型
  getAllBusinessType(params, callbacks) {
    return this.queryTemplate(params, callbacks, {
      ajaxParams: {
        action: "getAllBusinessType",
      },
      errorTag: "getAllBusinessType",
      errorMsg: "查询所有业务类型",
      preventCache: true,
    });
  }

  // 查询业务应用基本信息
  getApplicationCaseById(params, callbacks) {
    return this.queryTemplate(params, callbacks, {
      ajaxParams: {
        action: "getApplicationCaseById",
        applicationCaseId: params.applicationCaseId,
      },
      errorTag: "getApplicationCaseById",
      errorMsg: "查询应用案例基本信息",
      preventCache: true,
    });
  }
  //根据案例appcaseid获取一个个剧本
  getDraftListByAppcaseId(params, callbacks){
    return this.queryTemplate(params, callbacks, {
      ajaxParams: {
        action: "getDraftListByAppcaseId",
       appcaseId: params.appcaseId,
       
      },
      errorTag: "getDraftListByAppcaseId",
      errorMsg: "根据案例appcaseid获取一个个剧本,失败",
      preventCache: true,
    });
  }
  // 查询业务应用剧本集
  getDraft(params, callbacks) {
    return this.queryTemplate(params, callbacks, {
      ajaxParams: {
        action: "getDraft",
        draft: params.draft,
        pageInfo: params.pageInfo,
      },
      errorTag: "getDraft",
      errorMsg: "查询业务应用剧本集",
      preventCache: true,
    });
  }

  // 添加业务类型
  addApplicationCase(params, callbacks) {
    return this.queryTemplate(params, callbacks, {
      ajaxParams: {
        action: "addApplicationCase",
        applicationCase: params.applicationCase,
      },
      errorTag: "addApplicationCase",
      errorMsg: "查询所有业务类型",
      preventCache: true,
    });
  }

  // 添加业务剧本
  addDraft(params, callbacks) {
    return this.queryTemplate(params, callbacks, {
      ajaxParams: {
        action: "addDraft",
        draft: params.draft,
      },
      errorTag: "addDraft",
      errorMsg: "添加业务剧本",
      preventCache: true,
    });
  }
}
