import Vue from 'vue';
import axios from 'axios'
Vue.prototype.axios = axios;
//axios.defaults.baseURL = 'http://localhost:8080'
axios.defaults.baseURL = 'http://47.104.139.106:8080'