import BaseService from "./base_service";

export default class FirmwareService extends BaseService{
    /**
     * 2022年11月15日修改后的固件表、
     * 
     * */
    // 获取工具部件类型的固件，就根据工具id来进行获取
    getFirmwaresByToolId(params,callbacks){
    return this.queryTemplate(params,callbacks, {
        ajaxParams:{
            action: 'getFirmwaresByToolId',
            toolId:params.toolId,
        },
        errorTag: 'getFirmwaresByToolId',
        errorMsg: '获取工具部件类型的固件，就根据工具id来进行获取,失败',
        preventCache: true,
    })
}
 /*根据词汇id获取firmware表中的工具id,工具名称，部件id,部件名称及参数表中的参数信息 ，
   输入输出参数类型，是否必填，参数中英文名称，父参数id*/

getToolInfoFirmwareInfoParamInfoByWordId(params,callbacks){
    return this.queryTemplate(params,callbacks, {
        ajaxParams:{
            action: 'getToolInfoFirmwareInfoParamInfoByWordId',
            wordId:params.wordId,
        },
        errorTag: 'getToolInfoFirmwareInfoParamInfoByWordId',
        errorMsg: '根据词汇id获取固件信息及参数信息,失败',
        preventCache: true,
    })
}

/*根据工具id和部件action与param表连表查询获取参数 */
getInputParamsByToolIdAndApiAction(params,callbacks){
    console.log("调用了获取参数的方法");
    return this.queryTemplate(params,callbacks, {
        ajaxParams:{
            action: 'getInputParamsByToolIdAndApiAction',
            toolId:params.toolId,
            apiAction:params.apiAction,

        },
        errorTag: 'getInputParamsByToolIdAndApiAction',
        errorMsg: '根据工具id和部件action获取输入参数列表,失败',
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