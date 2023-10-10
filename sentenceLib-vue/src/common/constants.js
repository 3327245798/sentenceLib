import _ from "lodash";
import { toast, notify, alert, getBandId, getAccessToken } from "common/env";
/**
 * 配置常量
 */
export const Config = {
  ROUTER_MODE: "hash",
  MIN_SAME_TASK_POST_INTERVEL: 0 /* 单位为毫秒, <=0不启用*/,
  MIN_PAGING_INTERVEL: 500 /* 单位为毫秒*/,
  TIMEOUT_INTERVEL: 60000 /* 单位为毫秒*/,
  CACHE_MISSED_NAME: "#加载中#",
  CACHE_NAME: "", //'MY_TOOL_NAME_CACHES_V1',
  CACHE_VERSION: "1",
  QUERY_PARAM_NAMES: ["query", "searches", "messageQuery"],
  COPY_PARAM_NAMES: ["query", "resources", "extend", "field", "sort"],
  SERIES_COPY_PARM_NAMES: ["_gid"],
  ACCEPT_IMG_TYPES: "image/png,image/gif,image/jpg,image/jpeg,.png,.gif,.jpg,.jpeg",
  MAX_IMG_SIZE: 1024 * 1024 * 10,
  MAX_FILE_SIZE: 1024 * 1024 * 50,
  CACHE_COPY_OPTIONS: {
    force: false,
    mergeAccept: (value, key, from, to) => {
      if (value instanceof File) {
        return false;
      }
      if (value instanceof HTMLElement) {
        return false;
      }
      const mergeAcceptFn = from.mergeAccept || to.mergeAccept;
      if (_.isFunction(mergeAcceptFn)) {
        /* from 新值，to 旧值 */
        const result = mergeAcceptFn(value, key, from, to);
        if (result === false) {
          return false;
        }
      }
      return true;
    },
    beforeMerge: (key, from, fromVal, to, toValue) => {
      // 如果对象有定义合并回调，则先执行
      const beforeMergeFn = from.beforeMerge || to.beforeMerge;
      if (_.isFunction(beforeMergeFn)) {
        /* from 新值，to 旧值 */
        const result = beforeMergeFn(key, from, fromVal, to, toValue);
        if (result === false) {
          return false;
        }
      }
      return fromVal != toValue;
    },
    arrayContains: (newArr, oldItem) => {
      // 没有id说明不是model, 直接判断是否在列表中并返回
      if (!_.isObject(oldItem)) {
        return newArr.indexOf(oldItem) != -1;
      }
      // 有ID则判断ID是否相同
      if (oldItem.id) {
        return _.find(newArr, (newItem) => newItem.id == oldItem.id) ? true : false;
      }
      // 没有则进行对象属性匹配
      return _.find(newArr, _.matches(oldItem)) ? true : false;
    },
  },
};

/**
 * 常用的验证器, 配合Utils.validate使用
 */
export const DefaultValidators = {
  length: {
    validate: function(val, rule) {
      if (typeof val != "string") {
        return rule.fail;
      }
      if (rule.min && val.length < rule.min) {
        return rule.fail;
      }
      if (rule.max && val.length > rule.max) {
        return rule.fail;
      }
      return true;
    },
  },
  format: {
    validate: function(val, rule) {
      if (typeof val != "string") {
        return rule.fail;
      }
      if (!rule.regex.test(val)) {
        return rule.fail;
      }
      return true;
    },
  },
};

/**
 * 后台错误信息转换和处理, 配合BaseService._handleError使用
 */
export const ErrorLanguages = {
  DEFAULT_HANDLER: function(json) {
    toast(json.msg || json.result.msg);
  },
  COMMON: [
    {
      msgPiece: "会话超时",
      handler: function() {
        console.error("当前用户已会话超时, 请重新登录后运行工具!");
        window.setUserTimeout && window.setUserTimeout();
      },
    },
    {
      msgPiece: "内部错误",
      handler: function() {
        notify("服务异常!");
      },
    },
  ],
  // serviceErrorTag: [{
  // 	msgPiece: '错误信息片段或全部',
  // 	handler: function() {
  // 		notify('作需要的全局处理!如需局部处理调用Service时使用onFail');
  // 	},
  // }],
};

/**
 * 时间段的毫秒数
 */
export const TIME_RANGE_MILS = {
  ONE_YEAR_MILS: 365 * 24 * 60 * 60 * 1000,
  ONE_MONTH_MILS: 30 * 24 * 60 * 60 * 1000,
  ONE_WEEK_MILS: 7 * 24 * 60 * 60 * 1000,
  ONE_DAY_MILS: 24 * 60 * 60 * 1000,
  ONE_HOUR_MILS: 60 * 60 * 1000,
  ONE_MIN_MILS: 60 * 1000,
  ONE_SEC_MILS: 1000,
};

/**
 * mutation.js中需要表示空对象定义处
 */
export const EMPTY_OBJ = {};

export const getCreateDispatchUrl = () => {
  console.log(window.tool);
  let param = {
    organizationID: window.tool.organizationID,
  };
  return (
    getWtbUrl() +
    "tre/runSystemTool?returnType=VALUE&action=createDispatch&isPublic=true&toolID=4389165&param=" +
    encodeURI(JSON.stringify(param)) +
    "&gid=" +
    getBandId() +
    "&accessToken=" +
    getAccessToken()
  );
};

export const buildDispatchToolUrl = (params) => {
  let param = {
    organizationID: window.tool.organizationID,
    dispatchID: params.dispatchID,
    needHeader: false,
    dispatchTime: new Date().getTime(),
    objID: params.objID,
    objParams: {
      param: params.param,
    },
  };

  let encodeP = encodeURI(JSON.stringify(param));
  return `${getWtbUrl()}wx/gotoRunTool?runType=runSystemTool&toolID=4389165&bandID=${getBandId()}&param=${encodeP}&headerTitle=${encodeURI(
    params.title
  )}&needBack=false`;
};

export const getWtbUrl = () => {
  // return getCoreUrl().indexOf("www") > -1 ? "https://www.wetoband.com/" : "https://test.wetoband.com/";
  return "https://www.wetoband.com/";
};
