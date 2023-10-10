import Vue from "vue";
import _ from "lodash";
import Utils from "common/utils";
import { EMPTY_OBJ } from "common/constants";
import { BIND_CURRENT, BIND_DEVICE, BIND_USER, BIND_TASK } from "./mutation_types.js";

export default {
  [BIND_CURRENT](state, current) {
    if (!current) {
      return;
    }
    if (current.organizationId) {
      Vue.set(state.ST_CURRENT, "organizationId", current.organizationId);
    }
    if (current.BAND_OBJId) {
      Vue.set(state.ST_CURRENT, "bandId", current.BAND_OBJId);
    }
    if (current.userID) {
      Vue.set(state.ST_CURRENT, "userId", current.userID);
    }
    if (current.userAccount) {
      Vue.set(state.ST_CURRENT, "userAccount", current.userAccount);
    }
    if (current.userName) {
      Vue.set(state.ST_CURRENT, "userName", current.userName);
    }
    if (current.accessToken) {
      Vue.set(state.ST_CURRENT, "accessToken", current.accessToken);
    }
    if (current.loading !== undefined) {
      Vue.set(state.ST_CURRENT, "loading", current.loading);
    }
  },
  [BIND_DEVICE](state, device) {
    if (!device) {
      return;
    }
    if (Utils.isValidVariable(device.isMobile)) {
      Vue.set(state.ST_DEVICE, "isMobile", device.isMobile);
    }
    if (Utils.isValidVariable(device.deviceId)) {
      Vue.set(state.ST_DEVICE, "deviceId", device.deviceId);
    }
    if (Utils.isValidVariable(device.isOnline)) {
      Vue.set(state.ST_DEVICE, "isOnline", device.isOnline);
    }
    if (Utils.isValidVariable(device.isTimeout)) {
      Vue.set(state.ST_DEVICE, "isTimeout", device.isTimeout);
    }
  },
  [BIND_USER](state, user) {
    if (!user) {
      return;
    }
    if (user.command === "reset") {
      Vue.set(state.ST_USER, "rows", []);
      return;
    }
    if (user.command === "delete") {
      if (!user.id) {
        console.warn("STORE_MUTATION_ERROR:未指定删除的目标用户缓存ID", user);
        return;
      }
      deleteObj(state.ST_USER.rows, state.ST_USER.rowMap, user.id);
      return;
    }
    if (user.command === "modify") {
      if (!user.id) {
        return;
      }
      const target = state.ST_USER.rowMap[user.id];
      if (!target) {
        console.warn("STORE_MUTATION_WARN:找不到修改信息的对应ID的目标用户缓存", user);
        return;
      }
      for (let key of _.keys(user)) {
        if (["command", "id"].indexOf(key) != -1) {
          continue;
        }
        Vue.set(target, key, user[key]);
      }
      return;
    }
    if (_.isArray(user)) {
      _.forEach(user, (item) => {
        pushObj(state.ST_USER.rows, state.ST_USER.rowMap, item);
      });
      return;
    }
    if (_.isObject(user)) {
      pushObj(state.ST_USER.rows, state.ST_USER.rowMap, user);
    }
  },
  [BIND_TASK](state, task) {
    if (!task) {
      return;
    }
    if (task.command === "reset") {
      Vue.set(state.ST_TASK, "rows", []);
      return;
    }
    if (task.command === "delete") {
      if (!task.id) {
        console.warn("STORE_MUTATION_ERROR:未指定删除的任务缓存ID", task);
        return;
      }
      deleteObj(state.ST_TASK.rows, state.ST_TASK.rowMap, task.id);
      delete state.ST_TASK.rowMap[task.id];
      return;
    }
    if (_.isArray(task)) {
      _.forEach(task, (item) => {
        pushObj(state.ST_TASK.rows, state.ST_TASK.rowMap, item);
      });
      return;
    }
    if (_.isObject(task)) {
      pushObj(state.ST_TASK.rows, state.ST_TASK.rowMap, task);
    }
  },
};

const pushObj = (rows, rowMap, obj, key = "id") => {
  const oldObj = rowMap[obj[key]];
  if (!oldObj || rows.indexOf(oldObj) == -1) {
    rows.push(obj);
    Vue.set(rowMap, obj[key], obj);
    return;
  }
  const oldIndex = rows.indexOf(oldObj);
  if (oldIndex != -1) {
    Vue.set(rows, oldIndex, obj);
  } else {
    rows.push(obj);
  }
  Vue.set(rowMap, obj[key], obj);
};

const deleteObj = (rows, rowMap, id) => {
  const oldObj = rowMap[id];
  if (!oldObj) {
    return;
  }
  const index = rows.indexOf(oldObj);
  if (index == -1) {
    return;
  }
  Vue.delete(rows, index);
};

const cloneEmpty = (name) => {
  if (!name || !EMPTY_OBJ[name]) {
    return;
  }
  const result = _.cloneDeep(EMPTY_OBJ[name]);
  delete result.$empty;
  return result;
};
