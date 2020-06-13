import Vue from 'vue'
import VueAxios  from 'vue-axios'
import Axios from 'axios'
import App from './App.vue'

import router from "./router/index"
import vuetify from './plugins/vuetify';
import { store } from "./store/store"

import 'leaflet/dist/leaflet.css';

import {Icon} from 'leaflet';

delete Icon.Default.prototype._getIconUrl;

Icon.Default.mergeOptions({
  iconRetinaUrl: require('leaflet/dist/images/marker-icon-2x.png'),
  iconUrl: require('leaflet/dist/images/marker-icon.png'),
  shadowUrl: require('leaflet/dist/images/marker-shadow.png')
});


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

// axios config
Vue.use(VueAxios, Axios);

Vue.axios.defaults.headers['Authorization'] = localStorage.getItem('authKey');
Vue.axios.defaults.headers['Access-Control-Allow-Origin'] = '*';

// keep on heroku for deploy and localhost for testing




//Vue.axios.defaults.baseURL = "https://lotus-clinic-api.herokuapp.com"
Vue.axios.defaults.baseURL = "http://localhost:9001";


// all errors pass through this function before being catched in individual catch functions
Vue.axios.interceptors.response.use(response => {
  return response;
}, error => {
  if (307 === error.response.status) {
    store.commit('showSnackbar', {text: "Please change your password before logging in!", color: "info"});
    Vue.axios.defaults.headers['Authorization'] = "Bearer " + error.response.data.accessToken;
    router.push({name: "editPassword"});
    return Promise.reject(error);
  }
  else if (401 === error.response.status) {
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
    console.log(error.request.responseText);
    store.commit('showSnackbar', {text: error.request.responseText, color: "error", });
    return Promise.reject(error);
  }
})

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
