import axios from "axios";
import {getStorage} from "./storage";

const requests = axios.create({
    baseURL:"http://127.0.0.1:88",
    timeout: 5000,
});

requests.interceptors.request.use(function (config) {
    return config;
}, function (error) {
    return Promise.reject(error);
});

requests.interceptors.response.use(function (response) {

    return response;
}, function (error) {
    return Promise.reject(error);
});

export default requests;
