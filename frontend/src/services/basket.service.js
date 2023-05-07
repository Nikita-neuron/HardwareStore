import axios from "axios";

import authHeader from "./auth-header";

const API_URL = "http://localhost:8075/api/baskets/";

class BasketService {
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
    save(userId, products) {
        return axios.post(API_URL, {
            "userId": userId,
            "products": products
        }, {
            headers: authHeader()
        });
    }
    findByUserId(userId) {
        return axios.get(API_URL + `user/${userId}`, {
            headers: authHeader()
        });
    }
    deleteById(id) {
        return axios.delete(API_URL + `del/id/${id}`, {
            headers: authHeader()
        });
    }
    addProductByBasketId(basketId, productId) {
        return axios.get(API_URL + `id/${basketId}/addProduct/${productId}`, {
            headers: authHeader()
        });
    }
    deleteProductById(basketId, productId) {
        return axios.get(API_URL + `id/${basketId}/deleteProduct/${productId}`, {
            headers: authHeader()
        });
    }
    deleteAllProducts(basketId) {
        return axios.delete(API_URL + `id/${basketId}/deleteAllProducts`, {
            headers: authHeader()
        });
    }
}

export default new BasketService();