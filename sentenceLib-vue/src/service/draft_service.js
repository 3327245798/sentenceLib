import BaseService from "./base_service";

export default class DraftService extends BaseService {
     /*根据剧本id（剧本创建完成后只是个空壳子，只有name和description）获取草稿剧本（chapterList）和分析剧本（analyseList）*/
     getAllDraft( params,callbacks) {
    return this.queryTemplate(params,callbacks, {
      ajaxParams: {
        action: "getAllDraft",   
      },
      errorTag: "getAllDraft",
      errorMsg: "获取所有的剧本，包括剧本本身空壳子和草稿剧本list和分析剧本list,失败",
      preventCache: true,
    });
  }
  // 根据应用案例id找对应的剧本列表
  getDraftListByAppcaseId(params, callbacks) {
    return this.queryTemplate(params, callbacks, {
      ajaxParams: {
        action: "getDraftListByAppcaseId",
        appcaseId: params.appcaseId,
      },
      errorTag: "getDraftListByAppcaseId",
      errorMsg: "根据应用案例id获取剧本,失败",
      preventCache: true,
    });
  }
//根据应用案例id获取所有的剧本章节，目前剧本与剧本章节是一对一
getDraftchapterListOneToOneByAppcaseId(params, callbacks) {
  return this.queryTemplate(params, callbacks, {
    ajaxParams: {
      action: "getDraftchapterListOneToOneByAppcaseId",
      appcaseId: params.appcaseId,
    },
    errorTag: "getDraftchapterListOneToOneByAppcaseId",
    errorMsg: "根据应用案例id获取剧本及章节,失败",
    preventCache: true,
  });
}
 
}
