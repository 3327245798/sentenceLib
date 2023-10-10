import BaseService from "./base_service";

export default class SentenceService extends BaseService{
    /**
     * 句型表
     * 
     * */
    //y已废弃，开发者根据用户给出的句型进行开发，开发后的句型信息
     getSentenceInfoByUserSentenceId(params,callbacks){
        return this.queryTemplate(params,callbacks, {
            ajaxParams:{
                action: 'getSentenceInfoByUserSentenceId',
                sentenceIdFromUser:params.sentenceIdFromUser,
            },
            errorTag: 'getSentenceInfoByUserSentenceId',
            errorMsg: '根据用户给出的句型id获取开发者开发Hound句型信息,获取失败 ',
            preventCache: true,
        })
     }
    // wz查询所有句型
    getAllSentence(params,callbacks){
        return this.queryTemplate(params,callbacks, {
            ajaxParams:{
                action: 'getAllSentence',
               // type:params.type,
            },
            errorTag: 'getAllSentence',
            errorMsg: '查询所有句型失败',
            preventCache: true,
        })
    }
    //根据句型id获取句型信息
    getSentenceById(params,callbacks){
        return this.queryTemplate(params,callbacks, {
            ajaxParams:{
                action: 'getSentenceById',
                sentenceId:params.sentenceId,
            },
            errorTag: 'getSentenceById',
            errorMsg: '根据句型id获取句型信息,失败',
            preventCache: true,
        })
    }
    //根据用户输入的搜索关键字查询句型，只要句型，句型实例，编辑者与输入的额关键字相关，这一行都会被检索到
    getAllSentencesByuserInputKeyword(params,callbacks){
        return this.queryTemplate(params,callbacks, {
            ajaxParams:{
                action: 'getAllSentencesByuserInputKeyword',
                userInputKeyword:params.userInputKeyword,
            },
            errorTag: 'getAllSentencesByuserInputKeyword',
            errorMsg: '根据用户输入关键字查询句型，成功',
            preventCache: true,
        })
    }
    //根据编辑者姓名查询句型
    getAllSentenceByCreatorName(params,callbacks){
        return this.queryTemplate(params,callbacks, {
            ajaxParams:{
                action: 'getAllSentenceByCreatorName',
                creatorName:params.creatorName,
            },
            errorTag: 'getAllSentenceByCreatorName',
            errorMsg: '根据创建者查询句型，成功',
            preventCache: true,
        })
    }
        //getAllSentenceByStatus根据句型状态查询句型
        getAllSentenceByStatus(params,callbacks){
            return this.queryTemplate(params,callbacks, {
                ajaxParams:{
                    action: 'getAllSentenceByStatus',
                    status:params.status,
                },
                errorTag: 'getAllSentenceByStatus',
                errorMsg: '根据句型状态查询句型，成功',
                preventCache: true,
            })
        }
        //getAllSentenceInChecking查询处于审核中的句型，为前台帮语审核员服务
        getAllSentenceInChecking(callbacks){
            return this.queryTemplate({},callbacks, {
                ajaxParams:{
                    action: 'getAllSentenceInChecking',
                   
                },
                errorTag: 'getAllSentenceInChecking',
                errorMsg: '查询处于审核中的句型，成功',
                preventCache: true,
            })
        }
        //获取当天创建的句型getAllSentenceInDay
        getAllSentenceInDay(callbacks){
            return this.queryTemplate({},callbacks, {
                ajaxParams:{
                    action: 'getAllSentenceInDay',
                    
                },
                errorTag: 'getAllSentenceInDay',
                errorMsg: '获取当天创建的句型,获取失败',
                preventCache: true,
            })
        }
        
         //获取一周内创建的句型getAllSentenceInWeek
         getAllSentenceInWeek(callbacks){
            return this.queryTemplate({},callbacks, {
                ajaxParams:{
                    action: 'getAllSentenceInWeek',
                    
                },
                errorTag: 'getAllSentenceInWeek',
                errorMsg: '获取一周内创建的句型,获取失败',
                preventCache: true,
            })
        }
        //获取一个月内创建的句型getAllSentenceInMonth
    
        getAllSentenceInMonth(callbacks){
            return this.queryTemplate({},callbacks, {
                ajaxParams:{
                    action: 'getAllSentenceInMonth',
                    
                },
                errorTag: 'getAllSentenceInMonth',
                errorMsg: '获取一个月内创建的句型,获取失败',
                preventCache: true,
            })
        }
 //新增句型
 createSentence(params,callbacks){
    return this.queryTemplate(params,callbacks, {
        ajaxParams:{
            action: 'addSentence',
            sentence: params
        },
        errorTag: 'addSentence',
        errorMsg: '添加句型失败',
        preventCache: true,
    })
}
//修改句型
updateSentence(params,callbacks){
    return this.queryTemplate(params,callbacks, {
        ajaxParams:{
            action: 'updateSentence',
            sentence:params,
        },
        errorTag: 'updateSentence',
        errorMsg: '修改句型失败',
        preventCache: true,
    })
}
//删除句型
deleteSentence(params,callbacks){
    return this.queryTemplate(params,callbacks, {
        ajaxParams:{
            action: 'deleteSentence',
            sentence:params,
        },
        errorTag: 'deleteSentence',
        errorMsg: '删除句型失败',
        preventCache: true,
    })
}






}