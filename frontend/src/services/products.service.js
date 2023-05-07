import axios from "axios";

import authHeader from "./auth-header";

const API_URL = "http://localhost:8075/api/products/";

class ProductsService {
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
    save(title, price, categories) {
        return axios.post(API_URL, {
            "title": title,
            "price": price,
            "categories": categories
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

export default new ProductsService();