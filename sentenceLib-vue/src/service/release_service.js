import BaseService from "./base_service";

export default class ReleaseService extends BaseService {
    //获取已发布的草稿剧本列表
    getDraftRelease(params, callbacks,){
        return this.queryTemplate(params, callbacks, {
            ajaxParams: {
              action: "getDraftRelease",
              draftRelease:params.draftRelease,
              pageInfo: params.pageInfo,
          
            },
            errorTag: "getDraftRelease",
            errorMsg: "查询已发布到帮语网站的草稿剧本列表，查询失败",
            preventCache: true,
          });
    }
}