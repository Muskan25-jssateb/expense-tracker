import api from "./api";

export const login = (user) => {
    return api.post("/auth/login", user);
};

export const register = (user) => {
    return api.post("/auth/register", user);
};