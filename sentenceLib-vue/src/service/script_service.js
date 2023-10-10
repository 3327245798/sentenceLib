import BaseService from "./base_service";

export default class ScriptService extends BaseService {
  // 查询草稿剧本对应的分析剧本
  getAnalyseByDraftChapterId(params, callbacks) {
    return this.queryTemplate(params, callbacks, {
      ajaxParams: {
        action: "getAnalyseByDraftChapterId",
        draftChapterId: params.draftChapterId,
      },
      errorTag: "getAnalyseByDraftChapterId",
      errorMsg: "查询草稿剧本对应的分析剧本失败",
      preventCache: true,
    });
  }

  // 运行分析剧本
  deBugScripts(params, callbacks) {
    return this.queryTemplate(params, callbacks, {
      ajaxParams: {
        action: "deBugScripts",
        scripts: params.scripts,
      },
      errorTag: "deBugScripts",
      errorMsg: "运行分析剧本失败",
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

  // 分页条件查询g4规则模板列表
  pageConditionG4List(params, callbacks) {
    return this.queryTemplate(params, callbacks, {
      ajaxParams: {
        action: "pageConditionG4List",
        g4: params.g4,
      },
      errorTag: "pageConditionG4List",
      errorMsg: "分页条件查询g4规则模板列表",
      preventCache: true,
    });
  }

  // 根据草稿剧本章节id查询
  getDraftChapterById(params, callbacks) {
    return this.queryTemplate(params, callbacks, {
      ajaxParams: {
        action: "getDraftChapterById",
        draftChapterId: params.draftChapterId,
      },
      errorTag: "getDraftChapterById",
      errorMsg: "根据草稿剧本章节id查询失败",
      preventCache: true,
    });
  }

  // 新建草稿剧本章节
  addDraftChapter(params, callbacks) {
    return this.createTemplate(params, callbacks, {
      ajaxParams: {
        action: "addDraftChapter",
        draftChapter: {
          ...params,
          parentId: 0,
        },
      },
      errorTag: "addDraftChapter",
      errorMsg: "新建草稿剧本章节失败",
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
}
