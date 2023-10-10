import BaseService from "./base_service";

export default class WordService extends BaseService{
    /**
     * 词汇表、词汇语义表（针对已注册词汇）
     * 
     * */
     //新增词汇
 createWord(params,callbacks){
    return this.queryTemplate(params,callbacks, {
        ajaxParams:{
            action: 'addPublicWord',
            word:params
        },
        errorTag: 'addPublicWord',
        errorMsg: '添加词汇失败',
        preventCache: true,
    })
}
    /*更新词汇 */
    updateWord(params,callbacks){
        console.log(params)
        return this.queryTemplate(params,callbacks, {
            ajaxParams:{
                action: 'updateWord',
                word:params,
            },
            errorTag: 'updateWord',
            errorMsg: '修改词汇失败',
            preventCache: true,
        })
    }
    //删除词汇
deleteWord(params,callbacks){
    return this.queryTemplate(params,callbacks, {
        ajaxParams:{
            action: 'deleteWord',
            word:params,
        },
        errorTag: 'deleteWord',
        errorMsg: '删除句型失败',
        preventCache: true,
    })
}
    
//获取已绑定固件的词汇
getAllPublicWord(callbacks){
    return this.queryTemplate({},callbacks, {
        ajaxParams:{
            action: 'getAllPublicWord'
        },
        errorTag: 'getAllPublicWord',
        errorMsg: '查询所有公共词汇(指已经绑定好绑定固件的哪些词汇)失败',
        preventCache: true,
    })
}

//获取审核中的词汇，审核通过的状态为2

getInCheckingWord(callbacks){
    return this.queryTemplate({},callbacks, {
        ajaxParams:{
            action: 'getPublicWordsByStatus',
            status:2
        },
        errorTag: 'getPublicWordsByStatus',
        errorMsg: '查询审核中的公共词汇失败',
        preventCache: true,
    })
}
//获取审核通过的词汇，审核通过的状态为3

getCheckedWord(callbacks){
    return this.queryTemplate({},callbacks, {
        ajaxParams:{
            action: 'getCheckedWord',
            status:3
        },
        errorTag: 'getCheckedWord',
        errorMsg: '查询审核通过的公共词汇失败',
        preventCache: true,
    })
}
//根据审核状态查询公共词汇params
getPublicWordByStatus(params,callbacks){
    return this.queryTemplate(params,callbacks, {
        ajaxParams:{
            action: 'getPublicWordsByStatus',
            status:params.status
        },
        errorTag: 'getPublicWordsByStatus',
        errorMsg: '根据审核状态查询公共词汇,失败',
        preventCache: true,
    })
}
//根据固件种类查询公共词汇params
getPublicWordByUnitType(params,callbacks){
    return this.queryTemplate(params,callbacks, {
        ajaxParams:{
            action: 'getPublicWordByUnitType',
            unitType:params.unitType
        },
        errorTag: 'getPublicWordByUnitType',
        errorMsg: '根据固件种类查询公共词汇(绑定固件的),失败',
        preventCache: true,
    })
}

//搜索功能，根据创建者姓名查询公共词汇
getPublicWordByCreatorName(params,callbacks){
    return this.queryTemplate(params,callbacks, {
        ajaxParams:{
            action: 'getWordByCreatorName',
            creatorName:params.creatorName,
        },
        errorTag: 'getWordByCreatorName',
        errorMsg: '根据创建者姓名查询公共词汇(绑定固件的),查询失败',
        preventCache: true,
    })
}
//获取一个月内创建的词汇getAllWordsInMonth
getAllWordsInMonth(callbacks){
    return this.queryTemplate({},callbacks, {
        ajaxParams:{
            action: 'getAllWordsInMonth',
            
        },
        errorTag: 'getAllWordsInMonth',
        errorMsg: '获取一个月内创建的词汇(绑定固件的),获取失败',
        preventCache: true,
    })
}
//获取一周内创建的词汇getAllWordsInWeek
getAllWordsInWeek(callbacks){
    return this.queryTemplate({},callbacks, {
        ajaxParams:{
            action: 'getAllWordsInWeek',
            
        },
        errorTag: 'getAllWordsInWeek',
        errorMsg: '获取一周内创建的词汇(绑定固件的),获取失败',
        preventCache: true,
    })
}
//获取一天内创建的词汇getAllWordsInDay
getAllWordsInDay(callbacks){
    return this.queryTemplate({},callbacks, {
        ajaxParams:{
            action: 'getAllWordsInDay',
            
        },
        errorTag: 'getAllWordsInDay',
        errorMsg: '获取今天内创建的词汇(绑定固件的),获取失败',
        preventCache: true,
    })
}

//根据词汇id获取该词汇的所有版本
getAllWordVersionByWordId(params,callbacks){
    return this.queryTemplate(params,callbacks, {
        ajaxParams:{
            action: 'getAllWordVersionByWordId',
            wordId:params.wordId,
        },
        errorTag: 'getAllWordVersionByWordId',
        errorMsg: '根据词汇id获取该词汇的所有版本,获取失败',
        preventCache: true,
    })
}

    // wz查询所有词汇
    getAllWords(callbacks){
        return this.queryTemplate(callbacks, {
            ajaxParams:{
                action: 'getAllWords'
            },
            errorTag: 'getAllWords',
            errorMsg: '查询所有词汇失败'
        })
    }
  //////////////////这里是还未绑定固件的原始词汇////////////////////////////////////////////////////////////////////////

    //获取所有词汇，未绑定固件的也能获取到，即数据里面所有的词汇
    //获取所有还未绑定固件的词汇库wz
getAllPublicWordNoUnit(callbacks){
    return this.queryTemplate({},callbacks, {
        ajaxParams:{
            action: 'getAllPublicWordNoUnit'
        },
        errorTag: 'getAllPublicWordNoUnit',
        errorMsg: '查询所有公共词汇(还未绑定固件的)失败',
        preventCache: true,
    })
   
}
//会根据姓名
getAllWordsNoUnitByCreatorName(params,callbacks){
    return this.queryTemplate(params,callbacks, {
        ajaxParams:{
            action: 'getAllWordsNoUnitByCreatorName',
            creatorName:params.creatorName,
        },
        errorTag: 'getAllWordsNoUnitByCreatorName',
        errorMsg: '根据创建者姓名查询公共词汇(还未绑定固件的),查询失败',
        preventCache: true,
    })
}
//根据词汇状态
getAllWordsNoUnitByStatus(params,callbacks){
    return this.queryTemplate(params,callbacks, {
        ajaxParams:{
            action: 'getAllWordsNoUnitByStatus',
            status:params.status,
        },
        errorTag: 'getAllWordsNoUnitByStatus',
        errorMsg: '根据词汇状态查询公共词汇(还未绑定固件的),查询失败',
        preventCache: true,
    })
}
//根据一个月内
getAllWordsNoUnitInMonth(callbacks){
    return this.queryTemplate({},callbacks, {
        ajaxParams:{
            action: 'getAllWordsNoUnitInMonth',
            
        },
        errorTag: 'getAllWordsNoUnitInMonth',
        errorMsg: '获取一个月内创建的词汇(还未绑定固件的),获取失败',
        preventCache: true,
    })
}

//根据一周内
getAllWordsNoUnitInWeek(callbacks){
    return this.queryTemplate({},callbacks, {
        ajaxParams:{
            action: 'getAllWordsNoUnitInWeek',
            
        },
        errorTag: 'getAllWordsNoUnitInWeek',
        errorMsg: '获取一周内创建的词汇(还未绑定固件的),获取失败',
        preventCache: true,
    })
}

//根据一天内
getAllWordsNoUnitInDay(callbacks){
    return this.queryTemplate({},callbacks, {
        ajaxParams:{
            action: 'getAllWordsNoUnitInDay',
            
        },
        errorTag: 'getAllWordsNoUnitInDay',
        errorMsg: '获取一天内创建的词汇(还未绑定固件的),获取失败',
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
            errorMsg: '获取所有机构的信息，获取失败了'
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