import BaseService from "./base_service";
import axios from "axios";
import qs from "qs";
import { getBaseUrl } from "../common/env";

export default class CommentService extends BaseService {
    /**
     * 论坛的贴子进行评论的评论表
     * 
     * */

    //对帖子进行评论
    addComment(params) {
        return new Promise((resolve, reject) => {
            let formData = new FormData()
            formData.append('postId', params.postId)
            formData.append( 'commentContent', params.commentContent)
            axios
                .post(getBaseUrl() + "&action=addComment",formData).then((data) => {
                    resolve(data)  
                })                        
                .catch((err) => {    
                                     
                    reject(err)
                });
        })
    }
    /*获取对某一帖子的所有评论 */
    getCommentByPostId(id) {
        return new Promise((resolve, reject) => {
            axios
                .get(getBaseUrl() + "&action=getCommentByPostId&postId=" + id ).then((data) => {
                    resolve(data)
                })
                .catch((err) => {
                    reject(err)
                });
        })
    }


}