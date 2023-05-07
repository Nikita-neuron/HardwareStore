import axios from "axios";

import authHeader from "./auth-header";

const API_URL = "http://localhost:8075/api/categories/";

class CategoryService {
    getAll() {
        return axios.get(API_URL, {
            headers: authHeader()
        });
    }
    getById(id) {
        return axios.get(API_URL + `id/${id}`, {
            headers: authHeader()
        });
    }
    getByTitle(title) {
        return axios.get(API_URL + `title/${title}`, {
            headers: authHeader()
        });
    }
    save(title) {
        return axios.post(API_URL, {
            "title": title
        }, {
            headers: authHeader()
        });
    }
    deleteById(id) {
        return axios.delete(API_URL + `id/${id}`, {
            headers: authHeader()
        });
    }
}

export default new CategoryService();