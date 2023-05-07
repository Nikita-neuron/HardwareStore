import axios from "axios";

import authHeader from "./auth-header";

const API_URL = "http://localhost:8075/api/admin/";

class AdminService {
    getUsers() {
        return axios.get(API_URL + `users`, {
            headers: authHeader()
        });
    }
    getUserById(id) {
        return axios.get(API_URL + `users/${id}`, {
            headers: authHeader()
        });
    }
    deleteUserById(id) {
        return axios.delete(API_URL + `users/${id}`, {
            headers: authHeader()
        });
    }
}

export default new AdminService();