import BaseService from "./base_service";
import axios from "axios";
import qs from "qs";
import { getBaseUrl } from "../common/env";

export default class PostService extends BaseService {
    /**
     * 论坛的发帖子表
     * 
     * */

    //发布帖子
    addPost(params) {
        return new Promise((resolve, reject) => {
            axios
                .post(getBaseUrl() + "&action=addPost", params).then((data) => {
                    resolve(data)
                })
                .catch((err) => {
                    reject(err)
                });
        })
    }
    /*获取所有帖子 */
    getAllPosts() {
        return new Promise((resolve, reject) => {
            axios
                .get(getBaseUrl() + "&action=getAllPosts").then((data) => {
                    resolve(data)
                })
                .catch((err) => {
                    reject(err)
                });
        })
    }
   /*  getImage(params) {
        return new Promise((resolve, reject) => {
            axios
                .get(getBaseUrl() + "&action=getImage", params).then((data) => {
                    resolve(data)
                })
                .catch((err) => {
                    reject(err)
                });
        })
    } */


}