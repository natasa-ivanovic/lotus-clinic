import Vue from 'vue'
import VueAxios  from 'vue-axios'
import Axios from 'axios'
import App from './App.vue'

import router from "./router/index"
import vuetify from './plugins/vuetify';
import { store } from "./store/store"

// axios

Vue.use(VueAxios, Axios);

Vue.axios.defaults.headers['Authorization'] = localStorage.getItem('authKey');

// TODO later - set base URL to localhost/



// global variables
const authKey = Vue.observable({ authKey: localStorage.getItem('authKey') })

Object.defineProperty(Vue.prototype, '$authKey', {
  get () {
    return authKey.authKey;
  },
  set (value) {
    authKey.authKey = value;
  }
})

const role = Vue.observable({ role: localStorage.getItem('role') })

Object.defineProperty(Vue.prototype, '$role', {
  get () {
    return role.role;
  },
  set (value) {
    role.role = value;
  }
})

const apiURL = Vue.observable({ apiURL: "http://localhost:9001"})

Object.defineProperty(Vue.prototype, '$apiURL', {
  get () {
    return apiURL.apiURL;
  }
})

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
