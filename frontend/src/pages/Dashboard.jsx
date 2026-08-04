import { useEffect, useState } from "react";
import ReactMarkdown from "react-markdown";
import {
    getDashboard,
    getBudgetSummary,
    setBudget,
    getMonthlyComparison,
    getMLForecast,
    getAIInsights
} from "../services/expenseService";
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
    const [budgetSummary, setBudgetSummary] = useState(null);
const [budgetAmount, setBudgetAmount] = useState("");
const [budgetError, setBudgetError] = useState("");
const [monthlyComparison, setMonthlyComparison] = useState(null);
const [mlForecast, setMLForecast] = useState(null);
const [aiInsights, setAIInsights] = useState("");
const [aiLoading, setAILoading] = useState(false);
const [aiError, setAIError] = useState("");

const today = new Date();
const currentMonth = today.getMonth() + 1;
const currentYear = today.getFullYear();
const daysInMonth = new Date(
    currentYear,
    currentMonth,
    0
).getDate();

const remainingDays = daysInMonth - today.getDate() + 1;

const safeDailySpend =
    budgetSummary && budgetSummary.remaining > 0
        ? budgetSummary.remaining / remainingDays
        : 0;

        const daysPassed = today.getDate();

const averageDailySpend =
    budgetSummary
        ? budgetSummary.spent / daysPassed
        : 0;

const projectedMonthlySpend =
    averageDailySpend * daysInMonth;

const projectedDifference =
    budgetSummary
        ? projectedMonthlySpend - budgetSummary.budget
        : 0;

        const categoryChanges = monthlyComparison
    ? Object.keys({
        ...monthlyComparison.currentMonthCategories,
        ...monthlyComparison.previousMonthCategories
    }).map((category) => {

        const current =
            monthlyComparison.currentMonthCategories?.[category] || 0;

        const previous =
            monthlyComparison.previousMonthCategories?.[category] || 0;

        return {
            category,
            current,
            previous,
            difference: current - previous
        };

    }).sort((a, b) => b.difference - a.difference)
    : [];

    const biggestIncrease =
    categoryChanges.find(
        (item) => item.difference > 0
    );

const biggestDecrease =
    [...categoryChanges]
        .sort((a, b) => a.difference - b.difference)
        .find((item) => item.difference < 0);


const fetchBudgetSummary = async () => {

    try {

        const response = await getBudgetSummary(
            currentMonth,
            currentYear
        );

        setBudgetSummary(response.data);

    } catch (err) {

        console.error(err);
        setBudgetError("Unable to load monthly budget");

    }
};

const fetchMonthlyComparison = async () => {

    try {

        const response = await getMonthlyComparison(
            currentMonth,
            currentYear
        );

        setMonthlyComparison(response.data);

    } catch (err) {

        console.error("Unable to load monthly comparison", err);

    }
};

const fetchMLForecast = async () => {

    try {

        const response = await getMLForecast();

        setMLForecast(response.data);

    } catch (err) {

        console.error(
            "Unable to load ML forecast",
            err
        );

    }
};

const handleGenerateAIInsights = async () => {

    setAILoading(true);
    setAIError("");

    try {

        const response = await getAIInsights();

        setAIInsights(response.data);

    } catch (err) {

        console.error(err);
        setAIError("Unable to generate AI spending insights");

    } finally {

        setAILoading(false);

    }
};

const handleSetBudget = async (e) => {
    e.preventDefault();

    setBudgetError("");

    if (!budgetAmount || Number(budgetAmount) <= 0) {
        setBudgetError("Please enter a valid budget amount");
        return;
    }

    try {

        await setBudget(
            Number(budgetAmount),
            currentMonth,
            currentYear
        );

        setBudgetAmount("");

        await fetchBudgetSummary();

    } catch (err) {

        console.error(err);
        setBudgetError("Unable to save budget");

    }
};

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
        fetchBudgetSummary();
        fetchMonthlyComparison();
        fetchMLForecast();

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

                 <div className="d-flex justify-content-between align-items-center mb-4">
                 <div>
                     <h5 className="mb-1">Monthly Budget</h5>
                     <small className="text-muted">
                     {today.toLocaleString("default", { month: "long" })} {currentYear}
                      </small>
                 </div>
                 </div>

    {budgetError && (
        <div className="alert alert-danger">
            {budgetError}
        </div>
    )}

    {budgetSummary && (
        <>
            <div className="row g-4 mb-4">

                <div className="col-md-4">
                    <small className="text-muted">
                        Budget
                    </small>

                    <h3>
                        ₹{budgetSummary.budget}
                    </h3>
                </div>

                <div className="col-md-4">
                    <small className="text-muted">
                        Spent
                    </small>

                    <h3>
                        ₹{budgetSummary.spent}
                    </h3>
                </div>

                <div className="col-md-4">
                    <small className="text-muted">
                        Remaining
                    </small>

                    <h3>
                        ₹{budgetSummary.remaining}
                    </h3>
                </div>

            </div>

            <div className="mb-4">

                <div className="d-flex justify-content-between mb-2">
                    <span>Budget Used</span>

                    <strong>
                        {budgetSummary.percentageUsed?.toFixed(1)}%
                    </strong>
                </div>

                <div
                    className="progress"
                    style={{ height: "20px" }}
                >
                    <div
                        className="progress-bar"
                        role="progressbar"
                        style={{
                            width: `${Math.min(
                                budgetSummary.percentageUsed || 0,
                                100
                            )}%`
                        }}
                    >
                    </div>
                </div>

            </div>

            <div className="alert alert-info mt-3">

    <h6 className="mb-2">
        Spending Recommendation
    </h6>

    {budgetSummary.remaining > 0 ? (
        <div>
            You have <strong>₹{budgetSummary.remaining}</strong> left
            for the next <strong>{remainingDays} days</strong>.

            <div className="mt-2">
                To stay within your budget, you can spend approximately{" "}
                <strong>
                    ₹{safeDailySpend.toFixed(0)} per day
                </strong>
            </div>
            <div className="mt-3">

    At your current spending pace, you are projected to spend approximately{" "}
    <strong>
        ₹{projectedMonthlySpend.toFixed(0)}
    </strong>{" "}
    by the end of this month.

    {projectedDifference > 0 ? (
        <div className="mt-2 text-danger">
            You are on track to exceed your budget by approximately{" "}
            <strong>
                ₹{projectedDifference.toFixed(0)}
            </strong>.
        </div>
    ) : (
        <div className="mt-2 text-success">
            You are currently on track to stay within your monthly budget.
        </div>
    )}

</div>
        </div>
    ) : (
        <div>
            You have used your entire monthly budget.
        </div>
    )}

</div>


        </>
    )}

    <form
        onSubmit={handleSetBudget}
        className="row g-2"
    >

        <div className="col-md-4">

            <input
                type="number"
                className="form-control"
                placeholder="Enter monthly budget"
                value={budgetAmount}
                min="1"
                onChange={(e) =>
                    setBudgetAmount(e.target.value)
                }
            />

        </div>

        <div className="col-md-3">

            <button
                type="submit"
                className="btn btn-primary"
            >
                {budgetSummary?.budget > 0
                    ? "Update Budget"
                    : "Set Budget"}
            </button>

        </div>

    </form>

</div>
{mlForecast && (
    <div className="card shadow-sm p-4 mt-4">

        <div className="d-flex justify-content-between align-items-center mb-3">
            <h5 className="mb-0">
                ML Expense Forecast
            </h5>

            <span className="badge bg-primary">
                Machine Learning
            </span>
        </div>

        <div className="row g-4">

            <div className="col-md-4">
                <small className="text-muted">
                    Predicted Month-End Spending
                </small>

                <h3>
                    ₹{mlForecast.predicted_month_end_spending?.toFixed(0)}
                </h3>
            </div>

            <div className="col-md-4">
                <small className="text-muted">
                    Monthly Budget
                </small>

                <h3>
                    ₹{mlForecast.monthly_budget?.toFixed(0)}
                </h3>
            </div>

            <div className="col-md-4">
                <small className="text-muted">
                    Predicted Difference
                </small>

                <h3
                    className={
                        mlForecast.will_exceed_budget
                            ? "text-danger"
                            : "text-success"
                    }
                >
                    {mlForecast.predicted_difference > 0 ? "+" : ""}
                    ₹{mlForecast.predicted_difference?.toFixed(0)}
                </h3>
            </div>

        </div>

        <div
            className={
                mlForecast.will_exceed_budget
                    ? "alert alert-warning mt-4 mb-0"
                    : "alert alert-success mt-4 mb-0"
            }
        >
            {mlForecast.will_exceed_budget ? (
                <>
                    Based on your current spending pattern, the ML model
                    predicts that you may exceed your monthly budget by{" "}
                    <strong>
                        ₹{mlForecast.predicted_difference?.toFixed(0)}
                    </strong>.
                </>
            ) : (
                <>
                    Based on your current spending pattern, the ML model
                    predicts that you may finish approximately{" "}
                    <strong>
                        ₹{Math.abs(
                            mlForecast.predicted_difference
                        ).toFixed(0)}
                    </strong>{" "}
                    under budget.
                </>
            )}
        </div>

    </div>
)}

{/* AI SPENDING ASSISTANT */}

<div className="card shadow-sm p-4 mt-4">

    <div className="d-flex justify-content-between align-items-center mb-3">

        <div>
            <h5 className="mb-1">
                AI Spending Assistant
            </h5>

            <small className="text-muted">
               AI-generated insights using your budget, spending trends and ML forecast
            </small>
            <div className="mt-2">
            <small className="text-muted">
                AI insights are informational and may not always be accurate.
            </small>
          </div>
        </div>

        <span className="badge bg-dark">
            AI Powered
        </span>

    </div>


    {!aiInsights && (

        <button
            className="btn btn-primary"
            onClick={handleGenerateAIInsights}
            disabled={aiLoading}
        >
            {aiLoading
                ? "Generating Insights..."
                : "Generate AI Insights"}
        </button>

    )}


    {aiError && (

        <div className="alert alert-danger mt-3 mb-0">
            {aiError}
        </div>

    )}


    {aiInsights && (

        <div className="mt-3">

            <div
                 className="border rounded p-3 mb-3 bg-light"
                 style={{
                     lineHeight: "1.7"
                  }}
            >
                 <ReactMarkdown>
                   {aiInsights}
                 </ReactMarkdown>
            </div>

            <button
                className="btn btn-outline-primary btn-sm"
                onClick={handleGenerateAIInsights}
                disabled={aiLoading}
            >
                {aiLoading
                    ? "Refreshing..."
                    : "Refresh Insights"}
            </button>

        </div>

    )}

</div>

{monthlyComparison && (
    <div className="card shadow-sm p-4 mt-4">

        <h5 className="mb-4">
            Monthly Spending Comparison
        </h5>

        <div className="row g-4">

            <div className="col-md-4">
                <small className="text-muted">
                    This Month
                </small>

                <h3>
                    ₹{monthlyComparison.currentMonthTotal?.toFixed(0)}
                </h3>
            </div>

            <div className="col-md-4">
                <small className="text-muted">
                    Last Month
                </small>

                <h3>
                    ₹{monthlyComparison.previousMonthTotal?.toFixed(0)}
                </h3>
            </div>

            <div className="col-md-4">
                <small className="text-muted">
                    Difference
                </small>

                <h3
                    className={
                        monthlyComparison.difference > 0
                            ? "text-danger"
                            : "text-success"
                    }
                >
                    {monthlyComparison.difference > 0 ? "+" : ""}
                    ₹{monthlyComparison.difference?.toFixed(0)}
                </h3>
            </div>

        </div>

        {monthlyComparison.previousMonthTotal > 0 && (
            <div className="mt-3">

                {monthlyComparison.percentageChange > 0 ? (
                    <span className="text-danger">
                        Spending increased by{" "}
                        <strong>
                            {monthlyComparison.percentageChange.toFixed(1)}%
                        </strong>{" "}
                        compared with last month.
                    </span>
                ) : monthlyComparison.percentageChange < 0 ? (
                    <span className="text-success">
                        Spending decreased by{" "}
                        <strong>
                            {Math.abs(
                                monthlyComparison.percentageChange
                            ).toFixed(1)}%
                        </strong>{" "}
                        compared with last month.
                    </span>
                ) : (
                    <span>
                        Your spending is the same as last month.
                    </span>
                )}

            </div>
        )}

        {categoryChanges.length > 0 && (
    <div className="mt-4">

        <h6 className="mb-3">
            Category Changes
        </h6>

        {categoryChanges.map((item) => (

            <div
                key={item.category}
                className="d-flex justify-content-between border-bottom py-2"
            >
                <div>
                    <strong>{item.category}</strong>

                    <small className="text-muted ms-3">
                        ₹{item.previous.toFixed(0)}
                        {" → "}
                        ₹{item.current.toFixed(0)}
                    </small>
                </div>

                <strong
                    className={
                        item.difference > 0
                            ? "text-danger"
                            : item.difference < 0
                                ? "text-success"
                                : ""
                    }
                >
                    {item.difference > 0 ? "+" : ""}
                    ₹{item.difference.toFixed(0)}
                </strong>

            </div>

        ))}

    </div>
)}

    </div>
)}
{monthlyComparison && (
    <div className="card shadow-sm p-4 mt-4">

        <h5 className="mb-3">
            Smart Spending Insights
        </h5>

        {monthlyComparison.difference > 0 ? (
            <p>
                Your total spending increased by{" "}
                <strong className="text-danger">
                    ₹{monthlyComparison.difference.toFixed(0)}
                </strong>{" "}
                compared with last month.
            </p>
        ) : monthlyComparison.difference < 0 ? (
            <p>
                Your total spending decreased by{" "}
                <strong className="text-success">
                    ₹{Math.abs(monthlyComparison.difference).toFixed(0)}
                </strong>{" "}
                compared with last month.
            </p>
        ) : (
            <p>
                Your total spending is the same as last month.
            </p>
        )}

        {biggestIncrease && (
            <p>
                Your largest spending increase was in{" "}
                <strong>{biggestIncrease.category}</strong>, where spending
                increased from{" "}
                <strong>₹{biggestIncrease.previous.toFixed(0)}</strong>
                {" "}to{" "}
                <strong>₹{biggestIncrease.current.toFixed(0)}</strong>
                {" "}(
                <span className="text-danger">
                    +₹{biggestIncrease.difference.toFixed(0)}
                </span>
                ).
            </p>
        )}

        {biggestDecrease && (
            <p>
                You reduced spending the most in{" "}
                <strong>{biggestDecrease.category}</strong>, saving{" "}
                <strong className="text-success">
                    ₹{Math.abs(biggestDecrease.difference).toFixed(0)}
                </strong>{" "}
                compared with last month.
            </p>
        )}

        {budgetSummary && budgetSummary.remaining > 0 && (
            <p className="mb-0">
                To stay within your ₹{budgetSummary.budget} monthly budget,
                try to keep your spending near{" "}
                <strong>
                    ₹{safeDailySpend.toFixed(0)} per day
                </strong>{" "}
                for the remaining {remainingDays} days.
            </p>
        )}

    </div>
)}
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