import Vue from 'vue'
import App from './App.vue'

import router from "./router/index"


new Vue({
  router,
  data: {
    Util
  },
  render: h => h(App),
}).$mount('#app')

const Util = {
  dateToString(date) {
    var month = date.getMonth() + 1;
    if (month < 10)
      month = "0" + month;
    var day = date.getDate();
    if (day < 10)
      day = "0" + day;
    return date.getFullYear() + "-" + month + "-" + day;
  }
}