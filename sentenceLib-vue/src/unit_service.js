import {getBandId, getBandObjId, getBaseUrl, getCallToolUrl, getUserId, pageBack, toast, jsonToFormData} from "../common/env";
import https from '../common/https';
import axiosIns from 'axios';
import {getCreateDispatchUrl} from "../common/constants";


export default class UnitService {
    createUnit(teacher, action) {
        let formData = new FormData();;
        formData.append("action", action);
        formData.append(" unit", JSON.stringify(teacher));

        return new Promise((resolve, reject) => {
            https.doPost(getBaseUrl(), formData).then((res) => {
                if (res.data.result){
                    resolve(res.data);
                }else {
                    reject(res.data)
                }
            }).catch(e => {
                reject(e)
            })
        })
    }

    getUnits(params) {
        params.action = 'getUnits'
        return new Promise((resolve, reject) => {
            https.doGet(getBaseUrl(),params).then((res) => {
                if (res.data.rows){
                    resolve(res.data);
                }else {
                    reject(res.data)
                }
            }).catch(e => {
                reject(e)
            })
        })
    }

    getTools() {
        return new Promise((resolve, reject) => {
            https.doGet(getBaseUrl(),{action : 'gettool'}).then((res) => {
                console.log(res);
                if (res.data.result){
                    resolve(res.data.result);
                }else {
                    reject(res.data)
                }
            }).catch(e => {
                reject(e)
            })
        })
    }

    deleteUnit(id) {
        return new Promise((resolve, reject) => {
            https.doGet(getBaseUrl(),{
                action : 'deleteUnit',
                id: id
            }).then((res) => {
                if (res.data.result){
                    resolve(res.data);
                }else {
                    reject(res.data.msg)
                }
            }).catch(e => {
                reject(e)
            })
        })
    }
};
