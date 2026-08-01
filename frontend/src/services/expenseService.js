import api from "./api";

export const getExpenses = () => {
    return api.get("/expenses");
};

export const addExpense = (expense) => {
    return api.post("/expenses", expense);
};

export const getExpenseById = (id) => {
    return api.get(`/expenses/${id}`);
};

export const updateExpense = (id, expense) => {
    return api.put(`/expenses/${id}`, expense);
};

export const deleteExpense = (id) => {
    return api.delete(`/expenses/${id}`);
};

export const getDashboard = () => {
    return api.get("/expenses/dashboard");
};

export const getTotalExpense = () => {
    return api.get("/expenses/total");
};

export const getHighestExpense = () => {
    return api.get("/expenses/highest");
};

export const getCategorySummary = () => {
    return api.get("/expenses/category-summary");
};