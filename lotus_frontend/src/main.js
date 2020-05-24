import Vue from 'vue'
import VueAxios  from 'vue-axios'
import Axios from 'axios'
import App from './App.vue'

import router from "./router/index"
import vuetify from './plugins/vuetify';
import { store } from "./store/store"

// global variables

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

Vue.use(VueAxios, Axios);

Vue.axios.defaults.headers['Authorization'] = localStorage.getItem('authKey');

Vue.axios.defaults.baseURL = "http://localhost:9001"
// check da li ovo radi 

Vue.axios.interceptors.response.use(response => {
  return response;
}, error => {
  if (401 === error.response.status) {
    store.commit('showSnackbar', {text: "Your session has expired! Please log in again.", color: "error", })
    Vue.prototype.$role = null;
    localStorage.removeItem('authKey');
    Vue.axios.defaults.headers['Authorization'] = "";
    localStorage.removeItem('role');
    router.push({ name: "login" });
    return Promise.reject(error);
  } else if (403 === error.response.status) {
    store.commit('showSnackbar', {text: "Unauthorized to visit entered link!", color: "error", })
    router.push({name: 'home'});
    return Promise.reject(error);
  } else {
    return Promise.reject(error);
  }
})

/*const authKey = Vue.observable({ authKey: localStorage.getItem('authKey') })

Object.defineProperty(Vue.prototype, '$authKey', {
  get () {
    return authKey.authKey;
  },
  set (value) {
    authKey.authKey = value;
  }
})*/


new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
