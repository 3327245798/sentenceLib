import BaseService from "./base_service";
export default class ScriptConllectionService extends BaseService {
  // 根据帮区id获取应用案例列表
  getApplicationCase(params, callbacks) {
    return this.queryTemplate(params, callbacks, {
      ajaxParams: {
        action: "getApplicationCase",
        applicationCase: {
          isPrivate: 0,
          realBandId: params.realBandId,
        },
        pageInfo: params.pageInfo,
      },
      errorTag: "getApplicationCase",
      errorMsg: "根据帮区id获取剧本集列表失败",
      preventCache: true,
    });
  }

  // 根据draftChapterId获取剧本信息
  getDraft(params, callbacks) {
    return this.queryTemplate(params, callbacks, {
      ajaxParams: {
        action: "getDraft",
        ...params,
      },
      errorTag: "getDraft",
      errorMsg: "根据帮区id获取剧本列表失败",
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
      errorMsg: "查询所有业务类型失败",
      preventCache: true,
    });
  }

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
//根据当前帮区获取帮区路径
getBandsPath(params, callbacks) {
  return this.queryTemplate(params, callbacks, {
    ajaxParams: {
      action: "getBandsPath",
      bandId: params.bandId,
    },
    errorTag: "getBandsPath",
    errorMsg: "获取帮区路径失败",
    preventCache: true,
  });
}
//根据帮区id获取该帮区的词汇
getOnlyWords(params, callbacks){
  return this.queryTemplate(params, callbacks,{
    ajaxParams: {
      action: "getOnlyWords",
      bandId: params.bandId,
    },
    errorTag: "getOnlyWords",
    errorMsg: "获取帮区工具中的词汇失败",
    preventCache: true,
  })

}
  // 添加剧本集
  addApplicationCase(params, callbacks) {
    return this.queryTemplate(params, callbacks, {
      ajaxParams: {
        action: "addApplicationCase",
        applicationCase: params.applicationCase,
      },
      errorTag: "addApplicationCase",
      errorMsg: "添加剧本集失败",
      preventCache: true,
    });
  }

  // 更新剧本集
  updateApplicationCase(params, callbacks) {
    return this.updateTemplate(params, callbacks, {
      ajaxParams: {
        action: "updateApplicationCase",
        applicationCase: params.applicationCase,
      },
      errorTag: "updateApplicationCase",
      errorMsg: "更新剧本集失败",
      preventCache: true,
    });
  }

  // 删除剧本集
  deleteApplicationCase(params, callbacks) {
    return this.deleteTemplate(params, callbacks, {
      ajaxParams: {
        action: "deleteApplicationCase",
        applicationCase: params.applicationCase,
      },
      errorTag: "deleteApplicationCase",
      errorMsg: "删除剧本集失败",
      preventCache: true,
    });
  }

  // 根据应用案例Id查询
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

  // 根据草稿剧本Id查询 草稿剧本章节 和 对应的分析剧本
  getDraftChapterAndAnalyse(params, callbacks) {
    return this.queryTemplate(params, callbacks, {
      ajaxParams: {
        action: "getDraftChapterAndAnalyse",
        ...params,
      },
      errorTag: "getDraftChapterAndAnalyse",
      errorMsg: "根据草稿剧本Id查询 草稿剧本章节 和 对应的分析剧本失败",
      preventCache: true,
    });
  }

  // 保存草稿剧本章节
  updateDraftChapter(params, callbacks) {
    return this.updateTemplate(params, callbacks, {
      ajaxParams: {
        action: "updateDraftChapter",
        draftChapter: {
          ...params,
          parentId: 0,
        },
      },
      errorTag: "updateDraftChapter",
      errorMsg: "保存草稿剧本章节失败",
      preventCache: true,
    });
  }

  // 查看运行状态
  getRunningDetailsByGid(params, callbacks) {
    return this.queryTemplate(params, callbacks, {
      ajaxParams: {
        action: "getRunningDetailsByGid",
        globalId: params.globalId,
      },
      errorTag: "getRunningDetailsByGid",
      errorMsg: "查看运行状态失败",
      preventCache: true,
    });
  }
}
