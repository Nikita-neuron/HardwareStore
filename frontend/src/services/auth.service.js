import axios from "axios";

const API_URL = "http://localhost:8075/api/auth/";

class AuthService {
    login(email, password) {
        return axios
        .post(API_URL + "login", {
            "email": email,
            "password": password
        })
        .then(response => {
            if (response.data.token) {
                localStorage.setItem("user", JSON.stringify(response.data));
            }
            return response.data;
        });
    }

    logout() {
        localStorage.removeItem("user");
    }

    register(firstName, lastName, birthday, phone, email, password) {
        return axios.post(API_URL + "register", {
            "firstName": firstName,
            "lastName": lastName,
            "birthday": birthday,
            "phone": phone,
            "email": email,
            "password": password
        })
        .then(response => {
            if (response.data.token) {
                localStorage.setItem("user", JSON.stringify(response.data));
            }
            return response.data;
        });
    }

    getCurrentUser() {
        return JSON.parse(localStorage.getItem('user'));
    }
}

export default new AuthService();