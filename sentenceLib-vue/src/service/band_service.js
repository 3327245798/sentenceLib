import BaseService from "./base_service";

export default class BandService extends BaseService {
  // 根据当前帮区id获取所在机构所有帮区
  getBandList(params, callbacks) {
    return this.queryTemplate(params, callbacks, {
      ajaxParams: {
        action: "getBandList",
        bandId: params.bandId,
      },
      errorTag: "getBandList",
      errorMsg: "获取所在机构所有帮区失败",
      preventCache: true,
    });
  }
  getWordAndSentenceEnvironment(params, callbacks) {
    return this.queryTemplate(params, callbacks, {
      ajaxParams: {
        action: "getWordAndSentenceEnvironment",
        bandList: params.bandList,
        type: params.type,
      },
      errorTag: "getWordAndSentenceEnvironment",
      errorMsg: "获取相关帮区元素信息失败",
      preventCache: true,
    });
  }
  getUserRoles(params, callbacks){
    return this.queryTemplate(params, callbacks, {
      ajaxParams: {
        action: "getUserRoles",
        //bandId: params.bandId,
        //userId:params.userId,
      },
      errorTag: "getUserRoles",
      errorMsg: "获取用户角色，失败",
      preventCache: true,
    });
  }
  //获取当前帮区下的所有工具
  getToolsInBand(callbacks){
    return this.queryTemplate({}, callbacks, {
      ajaxParams: {
        action: "getToolsInBand",
        //bandId: params.bandId,
       
      },
      errorTag: "getToolsInBand",
      errorMsg: "获取该帮区下所有工具，获取失败",
      preventCache: true,
    });
  }
  getToolInfo(params, callbacks) {
    return this.queryTemplate(params, callbacks, {
      ajaxParams: {
        action: "getToolInfo",
        toolName: params.toolName,
        bandId: params.bandId,
      },
      errorTag: "getToolInfo",
      errorMsg: "获取工具信息失败",
      preventCache: true,
    });
  }
}
