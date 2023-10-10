import Vue from "vue";
import Vuex from "vuex";
import mutations from "./mutations";

Vue.use(Vuex);

const state = {
  ST_CURRENT: {
    organizationId: "",
    bandId: "",
    userId: "",
    userAccount: "",
    userName: "",
    accessToken: "",
    loading: false,
  },
  ST_DEVICE: {
    isOnline: true,
    isTimeout: false,
    isMobile: false,
    deviceId: "",
  },
  ST_USER: {
    rows: [],
    rowMap: {},
  },
  ST_TASK: {
    rows: [],
    rowMap: {},
  },
};

export default new Vuex.Store({
  state,
  mutations,
});
