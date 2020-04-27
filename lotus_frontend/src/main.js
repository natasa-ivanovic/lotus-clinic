import Vue from 'vue'
import App from './App.vue'

import router from "./router/index"
import vuetify from './plugins/vuetify';


new Vue({
  router,

  data: {
  },

  vuetify,
  render: h => h(App)
}).$mount('#app')
