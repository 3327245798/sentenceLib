import https from '../common/https';
import {getAccessToken, getBaseUrl, getCoreUrl} from "../common/env";
import {aid, getToolShopUrl} from "../common/constants";

export default class ShopService {

    // 获取当前帮区的当前工具的我的视图
    getTools(name) {
        return new Promise(async (resolve, reject)=> {
            https.doGet(getToolShopUrl(), {
                query: JSON.stringify({
                    name: {
                        has: name
                    }
                })
            }).then((res) => {
                if (res.data.rows && res.data.rows.length) {
                    resolve(res.data.rows)
                } else {
                    resolve(null)
                }
            })
        })
    }



    //根据帮区工具
    getAid() {
        let formData = new FormData();    
        formData.append("action", "getaid");
        return new Promise((resolve, reject) => {
            https.doPost(getBaseUrl(), formData).then((res) => {
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


    //根据帮区工具
    getBandTool(banId,aid) {
        return new Promise(async (resolve, reject)=> {
            console.log(aid);
            https.doGet(getCoreUrl()+"/core/v4/band/"+banId+"/tool", {
                "access_token": getAccessToken(),
                "aid":aid,
                "format" :"json",
                "gid" :banId
            }).then((res) => {
                if (res.data.rows && res.data.rows.length) {
                    resolve(res.data.rows)
                } else {
                    resolve(null)
                }
            })
        })
    }
};