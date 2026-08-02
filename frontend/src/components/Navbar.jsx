import { Link, useNavigate } from "react-router-dom";

function Navbar() {

    const navigate = useNavigate();

    const handleLogout = () => {
        localStorage.removeItem("token");
        navigate("/");
    };

    return (
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark border-bottom">
            <div className="container">

                <Link className="navbar-brand fw-bold" to="/dashboard">
                    Expense Tracker
                </Link>

                <div className="d-flex align-items-center gap-3">

                    <Link
                        className="text-decoration-none text-light"
                        to="/dashboard"
                    >
                        Dashboard
                    </Link>

                    <Link
                        className="text-decoration-none text-light"
                        to="/expenses"
                    >
                        Expenses
                    </Link>

                    <button
                        className="btn btn-outline-danger btn-sm"
                        onClick={handleLogout}
                    >
                        Logout
                    </button>

                </div>

            </div>
        </nav>
    );
}

export default Navbar;