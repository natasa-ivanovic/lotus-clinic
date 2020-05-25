import Vue from 'vue'
import VueAxios  from 'vue-axios'
import Axios from 'axios'
import App from './App.vue'

import router from "./router/index"
import vuetify from './plugins/vuetify';
import { store } from "./store/store"

// global variables
// role is neccessary to be a global variable to enable menu bar reactivity

const role = Vue.observable({ role: localStorage.getItem('role') })

Object.defineProperty(Vue.prototype, '$role', {
  get () {
    return role.role;
  },
  set (value) {
    role.role = value;
  }
})

// axios
// cfg
Vue.use(VueAxios, Axios);

Vue.axios.defaults.headers['Authorization'] = localStorage.getItem('authKey');
Vue.axios.defaults.headers['Access-Control-Allow-Origin'] = '*';
Vue.axios.defaults.baseURL = "https://lotus-clinic-api.herokuapp.com"

// all errors pass through this function before being catched in individual catch functions
Vue.axios.interceptors.response.use(response => {
  return response;
}, error => {
  if (401 === error.response.status) {
    // automatic check for timed out sessions
    store.commit('showSnackbar', {text: "Your session has expired! Please log in again.", color: "error", })
    Vue.prototype.$role = null;
    localStorage.removeItem('authKey');
    Vue.axios.defaults.headers['Authorization'] = "";
    localStorage.removeItem('role');
    router.push({ name: "login" });
    return Promise.reject(error);
  } else if (403 === error.response.status) {
    // automatic check for forbidden access
    store.commit('showSnackbar', {text: "Unauthorized to visit entered link!", color: "error", })
    router.push({name: 'home'});
    return Promise.reject(error);
  } else {
    return Promise.reject(error);
  }
})

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
