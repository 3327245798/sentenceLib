import { getBandId, getUserId } from "../common/env";
import { getCreateDispatchUrl } from "../common/constants";
import BaseService from "./base_service";

export default class ShareService extends BaseService {
  // 创建发布
  createShareInCore(params, callbacks) {
    return this.queryTemplate(params, callbacks, {
      ajaxParams: {
        action: "createShareInCore",
        ...params,
      },
      errorTag: "createShareInCore",
      errorMsg: "创建发布链接失败",
      preventCache: true,
    });
  }

  // createShareInCore(callback, errCallback, params) {
  //   https
  //     .doPost(getCreateDispatchUrl(), {
  //       bandID: getBandId(),
  //       dispatch: JSON.stringify({
  //         dispatchName: params.name,
  //         description: params.name,
  //         fromOrganizationID: window.tool.organizationID,
  //         toOrganizationID: window.tool.organizationID,
  //       }),
  //       bandRole: JSON.stringify({ bandID: getBandId(), roleID: window.role.roleID, roleName: window.role.roleName }),
  //       dispatchItems: JSON.stringify([
  //         {
  //           objName: "照片存储分享工具",
  //           objID: window.tool.objID + "",
  //           realObjID: window.tool.realObjID,
  //           objType: "TOOL",
  //           permissionNames: ["browse", "run"],
  //         },
  //       ]),
  //       targetUserIDs: JSON.stringify([getUserId()]),
  //     })
  //     .then((res) => {
  //       if (res.data.result && res.data.result.result) {
  //         callback(res.data.result.result);
  //       } else {
  //         errCallback && errCallback();
  //       }
  //     })
  //     .catch((e) => {
  //       console.log(e);
  //       errCallback && errCallback();
  //     });
  // }
}
