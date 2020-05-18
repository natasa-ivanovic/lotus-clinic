import Vue from 'vue'
import Axios from 'axios'
import VueAxios from 'vue-axios'


Vue.use(VueAxios, Axios);

Vue.axios.defaults.headers['Authorization'] = localStorage.getItem('authKey');

// TODO later - set base URL to localhost/

/*Vue.axios.interceptors.reponse.use( (response) => {
    return response;
}, (error) => {
    if (error.response.status !== 401) {
        return new Promise((resolve, reject) => {
          reject(error);
        });
      }
    if (error.config.url == '/auth/refresh' || error.response.message == 'Account is disabled.') {
        Vue.$role = null;
        Vue.$authKey = null;
        localStorage.removeItem('authKey');
        Vue.axios.defaults.headers['Authorization'] = "";
        localStorage.removeItem('role');

        router.push({ name: 'login' });

        return new Promise((resolve, reject) => {
            reject(error);
        });
    }
    Vue.axios ... unfinished part, salji zahtev za refresh, ako ovo proradi

        // New request with new token
        const config = error.config;
        config.headers['Authorization'] = `Bearer ${token}`;

        return new Promise((resolve, reject) => {
          axios.request(config).then(response => {
            resolve(response);
          }).catch((error) => {
            reject(error);
          })
        });

      })
      .catch((error) => {
      	Promise.reject(error);
      });
  });
})*/