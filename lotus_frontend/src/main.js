import Vue from 'vue'
import Axios from 'axios'
import VueAxios from 'vue-axios'
import App from './App.vue'

import router from "./router/index"
import vuetify from './plugins/vuetify';

Vue.use(VueAxios, Axios);

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

const overlay = Vue.observable({overlay: false})

Object.defineProperty(Vue.prototype, '$overlay', {
  get () {
    return overlay.overlay;
  },
  set (value) {
    overlay.overlay = value;
  }
})


Vue.axios.defaults.headers['Authorization'] = localStorage.getItem('authKey');

new Vue({
  router,
  data: {
  },

  vuetify,
  render: h => h(App)
}).$mount('#app')
