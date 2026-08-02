import { useEffect, useState } from "react";
import { getDashboard } from "../services/expenseService";
import {
    BarChart,
    Bar,
    XAxis,
    YAxis,
    Tooltip,
    ResponsiveContainer
} from "recharts";

function Dashboard() {

    const [dashboard, setDashboard] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {

        const fetchDashboard = async () => {

            try {
                const response = await getDashboard();

                setDashboard(response.data);

            } catch (err) {
                console.error(err);
                setError("Unable to load dashboard");
            } finally {
                setLoading(false);
            }
        };

        fetchDashboard();

    }, []);

    if (loading) {
        return <h3 className="text-center mt-5">Loading...</h3>;
    }

    if (error) {
        return (
            <div className="alert alert-danger m-5">
                {error}
            </div>
        );
    }

    const chartData = Object.entries(
    dashboard.categorySummary || {}
).map(([category, amount]) => ({
    category: category,
    amount: amount
}));

    return (
        <div className="container mt-4">

            <h2 className="mb-4">
                Dashboard
            </h2>

            <div className="row g-4">

                <div className="col-md-4">
                    <div className="card shadow-sm p-4">

                        <h6>Total Expense</h6>

                        <h2>
                            ₹{dashboard.totalExpense}
                        </h2>

                    </div>
                </div>

                <div className="col-md-4">
                    <div className="card shadow-sm p-4">

                        <h6>Expense Count</h6>

                        <h2>
                            {dashboard.expenseCount}
                        </h2>

                    </div>
                </div>

                <div className="col-md-4">
                    <div className="card shadow-sm p-4">

                        <h6>Highest Expense</h6>

                        <h2>
                            ₹{dashboard.highestExpense?.amount || 0}
                        </h2>

                        <small>
                            {dashboard.highestExpense?.category || "No expenses"}
                        </small>

                    </div>
                </div>

            </div>

            <div className="card shadow-sm p-4 mt-4">

                <h5>Category Summary</h5>

                {Object.entries(
                    dashboard.categorySummary || {}
                ).map(([category, amount]) => (

                    <div
                        key={category}
                        className="d-flex justify-content-between border-bottom py-2"
                    >
                        <span>{category}</span>

                        <strong>
                            ₹{amount}
                        </strong>
                    </div>

                ))}

            </div>

            <div className="card shadow-sm p-4 mt-4">

    <h5 className="mb-4">
        Spending by Category
    </h5>

    <div style={{ width: "100%", height: 350 }}>

        <ResponsiveContainer width="100%" height="100%">

            <BarChart data={chartData}>

                <XAxis dataKey="category" />

                <YAxis />

                <Tooltip />

                <Bar
                    dataKey="amount"
                    fill="#0d6efd"
                />

            </BarChart>

        </ResponsiveContainer>

    </div>

</div>

        </div>
    );
}

export default Dashboard;