import BaseService from "./base_service";

export default class UnitService extends BaseService{
    /**
     * 固件表、
     * 
     * */
     //词汇绑定固件，可以理解为createUnit
 bindUnit(params,callbacks){
    return this.queryTemplate(params,callbacks, {
        ajaxParams:{
            action: 'addUnit',
            unit:params
        },
        errorTag: 'addUnit',
        errorMsg: '绑定固件失败',
        preventCache: true,
    })
}
    /*更新绑定固件，可以理解为updateUnit*/
    updateBindUnit(params,callbacks){
        console.log(params)
        return this.queryTemplate(params,callbacks, {
            ajaxParams:{
                action: 'updateUnit',
                unit:params,
            },
            errorTag: 'updateUnit',
            errorMsg: '修改固件操作失败',
            preventCache: true,
        })
    }
    //解除绑定固件,可以理解为deleteUnit
unBindUnit(params,callbacks){
    return this.queryTemplate(params,callbacks, {
        ajaxParams:{
            action: 'deleteUnit',
            unit:params,
        },
        errorTag: 'deleteUnit',
        errorMsg: '解除绑定固件的操作失败',
        preventCache: true,
    })

}
//获取所有固件，固件都是绑定过词汇的
getAllUnitsByWordID(params,callbacks){
    return this.queryTemplate(params,callbacks, {
        ajaxParams:{
            action: 'getAllUnitsByWordID',
            wordId:params.wordId

        },
        errorTag: 'getAllUnitsByWordID',
        errorMsg: '根据词汇id查询绑定的固件,失败',
        preventCache: true,
    })
}
//根据工具id获取该工具的所有部件
getUnitsByToolId(params,callbacks){
    return this.queryTemplate(params,callbacks, {
        ajaxParams:{
            action: 'getUnitsByToolId',
            toolId:params.toolId

        },
        errorTag: 'getUnitsByToolId',
        errorMsg: '根据工具id获取该工具的所有部件,失败',
        preventCache: true,
    })
}
//根据固件id获取固件的相关信息
getUnitById(params,callbacks){
    return this.queryTemplate(params,callbacks, {
        ajaxParams:{
            action: 'getUnitById',
            unitId:params.unitId

        },
        errorTag: 'getUnitById',
        errorMsg: '根据固件id获取固件的相关信息(主要为了获取输入参数),失败',
        preventCache: true,
    })
}
//根据工具id和部件action获取固件的相关信息
getUnitByToolIdAndUnitAction(params,callbacks){
    return this.queryTemplate(params,callbacks, {
        ajaxParams:{
            action: 'getUnitByToolIdAndUnitAction',
            toolId:params.toolId,
            unitAction:params.unitAction,

        },
        errorTag: 'getUnitByToolIdAndUnitAction',
        errorMsg: '根据工具id和部件action获取固件的相关信息(主要为了获取输入参数),失败',
        preventCache: true,
    })
}
}