import BaseService from "./base_service";

export default class DraftService extends BaseService {
  // 根据剧本id找对应的章节列表
  getDraftChapterListByDraftId(params, callbacks) {
    return this.queryTemplate(params, callbacks, {
      ajaxParams: {
        action: "getDraftChapterListByDraftId",
        draftId: params.draftId,
      },
      errorTag: "getDraftChapterListByDraftId",
      errorMsg: "根据剧本id找对应的章节列表,失败",
      preventCache: true,
    });
  }
 
 
}
