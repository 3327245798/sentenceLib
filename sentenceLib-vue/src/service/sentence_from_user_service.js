import BaseService from "./base_service";

export default class SentenceFromUserService extends BaseService{
    /**
     * 这里是普通用户创建的句型，不太正确，需要开发者去开发(即理解用户的意图，选择工具部件)
     * 
     * */
    
    // wz查询所有普通用户创建的句型句型
    getAllSentenceFromCommonUser(params,callbacks){
        return this.queryTemplate(params,callbacks, {
            ajaxParams:{
                action: 'getAllSentencesFromCommonUser',
               
            },
            errorTag: 'getAllSentencesFromCommonUser',
            errorMsg: '查询普通用户创建的句型，失败',
            preventCache: true,
        })
    }
   
}