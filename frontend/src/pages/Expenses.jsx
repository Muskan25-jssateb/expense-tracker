import { useEffect, useState } from "react";
import {
    getExpenses,
    addExpense,
    deleteExpense,
    updateExpense
} from "../services/expenseService";

function Expenses() {

    const [expenses, setExpenses] = useState([]);

    const [form, setForm] = useState({
        category: "",
        amount: "",
        description: "",
        date: ""
    });

    const [error, setError] = useState("");

    const [editingId, setEditingId] = useState(null);

    const loadExpenses = async () => {
        try {
            const response = await getExpenses();
            setExpenses(response.data);
        } catch (err) {
            console.error(err);
            setError("Unable to load expenses");
        }
    };

    useEffect(() => {
        loadExpenses();
    }, []);

    const handleChange = (e) => {
        setForm({
            ...form,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {
    e.preventDefault();

    setError("");

    const expenseData = {
        category: form.category,
        amount: Number(form.amount),
        description: form.description,
        date: form.date
    };

    try {

        if (editingId) {

            await updateExpense(editingId, expenseData);

            setEditingId(null);

        } else {

            await addExpense(expenseData);

        }

        setForm({
            category: "",
            amount: "",
            description: "",
            date: ""
        });

        loadExpenses();

    } catch (err) {
        console.error(err);
        setError("Unable to save expense");
    }
};

    const handleEdit = (expense) => {

    setEditingId(expense.id);

    setForm({
        category: expense.category,
        amount: expense.amount,
        description: expense.description || "",
        date: expense.date
    });

    window.scrollTo({
        top: 0,
        behavior: "smooth"
    });
};

    const handleDelete = async (id) => {

        try {
            await deleteExpense(id);
            loadExpenses();
        } catch (err) {
            console.error(err);
            setError("Unable to delete expense");
        }
    };

    return (
        <div className="container mt-4">

            <h2 className="mb-4">Expenses</h2>

            {error && (
                <div className="alert alert-danger">
                    {error}
                </div>
            )}

            <div className="card shadow-sm p-4 mb-4">

                <h5 className="mb-3"> {editingId ? "Edit Expense" : "Add Expense"}</h5>

                <form onSubmit={handleSubmit}>

                    <div className="row g-3">

                        <div className="col-md-3">
                            <label className="form-label">
                                Category
                            </label>

                            <input
                                type="text"
                                name="category"
                                className="form-control"
                                value={form.category}
                                onChange={handleChange}
                                required
                            />
                        </div>

                        <div className="col-md-3">
                            <label className="form-label">
                                Amount
                            </label>

                            <input
                                type="number"
                                name="amount"
                                className="form-control"
                                value={form.amount}
                                onChange={handleChange}
                                min="0.01"
                                step="0.01"
                                required
                            />
                        </div>

                        <div className="col-md-3">
                            <label className="form-label">
                                Description
                            </label>

                            <input
                                type="text"
                                name="description"
                                className="form-control"
                                value={form.description}
                                onChange={handleChange}
                            />
                        </div>

                        <div className="col-md-3">
                            <label className="form-label">
                                Date
                            </label>

                            <input
                                type="date"
                                name="date"
                                className="form-control"
                                value={form.date}
                                onChange={handleChange}
                                required
                            />
                        </div>

                    </div>

                  <button
                      type="submit"
                      className="btn btn-primary mt-3"
                  >
                      {editingId ? "Update Expense" : "Add Expense"}
                  </button>

                </form>

            </div>

            <div className="card shadow-sm p-4">

                <h5 className="mb-3">
                  Expense History
                </h5>

                {expenses.length === 0 ? (

                    <p>No expenses added yet.</p>

                ) : (

                    <div className="table-responsive">

                        <table className="table">

                            <thead>
                                <tr>
                                    <th>Category</th>
                                    <th>Description</th>
                                    <th>Date</th>
                                    <th>Amount</th>
                                    <th>Action</th>
                                </tr>
                            </thead>

                            <tbody>

                                {expenses.map((expense) => (

                                    <tr key={expense.id}>

                                        <td>{expense.category}</td>

                                        <td>
                                            {expense.description || "-"}
                                        </td>

                                        <td>{expense.date}</td>

                                        <td>
                                            ₹{expense.amount}
                                        </td>

                                        <td>

                                           <button
                                               className="btn btn-sm btn-warning me-2"
                                               onClick={() => handleEdit(expense)}
                                            >
                                               Edit
                                            </button>

                                            <button
                                                className="btn btn-sm btn-danger"
                                                onClick={() => handleDelete(expense.id)}
                                            >
                                                Delete
                                            </button>

                                        </td>

                                    </tr>

                                ))}

                            </tbody>

                        </table>

                    </div>

                )}

            </div>

        </div>
    );
}

export default Expenses;