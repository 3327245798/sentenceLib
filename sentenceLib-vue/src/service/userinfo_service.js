import BaseService from "./base_service";
import axios from "axios";
import qs from "qs";
import { getBaseUrl } from "../common/env";

export default class UserInfoService extends BaseService {
    /**
     * 论坛的用户信息表
     * 
     * */

    //更新用户信息，主要是更新用户头像
    updateUserTalkInfo(form) {
        return new Promise((resolve, reject) => {
            axios
                .post(getBaseUrl() + "&action=updateUserTalkInfo", form).then((data) => {
                    resolve(data)
                })
                .catch((err) => {
                    reject(err)
                });
        })
    }
    /*获取用户信息 */
 /*    getAllPosts() {
        return new Promise((resolve, reject) => {
            axios
                .get(getBaseUrl() + "&action=getAllPosts").then((data) => {
                    resolve(data)
                })
                .catch((err) => {
                    reject(err)
                });
        })
    } */
 


}