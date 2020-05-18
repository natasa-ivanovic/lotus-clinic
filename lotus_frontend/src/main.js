import Vue from 'vue'
import App from './App.vue'

import router from "./router/index"
import vuetify from './plugins/vuetify';


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
  data: {
  },

  vuetify,
  render: h => h(App)
}).$mount('#app')
