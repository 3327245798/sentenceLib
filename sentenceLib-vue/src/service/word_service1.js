import BaseService from "./base_service";

export default class WordService extends BaseService{
    /**
     * 词汇表、词汇语义表（针对已注册词汇）
     * 
     * */
    
    // wz查询所有词汇
    getAllWords(callbacks){
        return this.queryTemplate(callbacks, {
            ajaxParams:{
                action: 'getAllWords'
            },
            errorTag: 'getAllWords',
            errorMsg: '查询所有词汇失败',
            preventCache: true,
        })
    }
    //wz获取所有机构的信息
    getAllOrganization(params, callbacks){
        return this.queryTemplate(params, callbacks, {
            ajaxParams:{
                action: 'getAllOrganization',
                objId:params.objId,
                
            },
            errorTag: 'getAllOrganization',
            errorMsg: '获取所有机构的信息，获取失败了',
            preventCache: true,
        })
    

    }
    
//wz根据机构id获取该机构下的所有帮区
getFirstBandsByOrganizationId(params, callbacks){
    return this.queryTemplate(params, callbacks, {
        ajaxParams:{
            action: 'getFirstBandsByOrganizationId',
            organizationId:params.organizationId,      
        },
        errorTag: 'getFirstBandsByOrganizationId',
        errorMsg: '根据机构id获取该机构下的一级帮区,获取失败了',
        preventCache: true,
    })


}

     // 注册新词汇
     addWord(params, callbacks){
        return this.queryTemplate(params, callbacks, {
            ajaxParams:{
                action: 'getAllWords',
                wordName: params.wordName,
                // 
                wordType: params.wordType,
            },
            errorTag: 'getAllWords',
            errorMsg: '查询所有词汇失败'
        })
    }



}