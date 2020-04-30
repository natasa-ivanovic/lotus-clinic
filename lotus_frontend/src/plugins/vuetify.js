import Vue from 'vue';
import Vuetify from 'vuetify/lib';

Vue.use(Vuetify);

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


export default new Vuetify({
});
