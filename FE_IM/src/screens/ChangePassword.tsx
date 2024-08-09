import React, { useState } from 'react'
import { Link, useNavigate, useParams } from 'react-router-dom'
import toastr from 'toastr';
import "toastr/build/toastr.min.css"
const ChangePassword = () => {
    const navigate = useNavigate();
    const user = localStorage.getItem("user");

    let id = null;
    if (user) {
        const parseUser = JSON.parse(user);
        id = parseUser.id;
    }

    const [oldPass, setOldPass] = useState("");
    const [newPass, setNewPass] = useState("");
    const [confirmPass, setConfirmPass] = useState("");
    const [error, setError] = useState('');
    // const [errorChange, setErrorChange] = useState('');

    const handleSubmit = async (e: any) => {
        e.preventDefault();
        if (newPass !== confirmPass) {
            setError("Password do not match!");
            return;
        } else {
            setError("");
        }
        const formData = {
            oldPassword: oldPass,
            newPassword: newPass
        }

        try {
            const apiUrl = "http://localhost:8080";
            const user = localStorage.getItem("user");
            let token = null;
            if (user) {
                const parseUser = JSON.parse(user);
                token = parseUser.token
                console.log(token);
            }
            if (token) {
                const res = await fetch(`${apiUrl}/api/users/change-password/${id}`, {
                    method: "PATCH",
                    headers: {
                        "Authorization": `Bearer ${token}`,
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(formData)
                }).then((data) => {
                    if (data.status === 403) {
                        // setErrorChange("Old password is incorrect")
                        toastr.error("Old password is incorrect ")
                    } else {
                        toastr.success("Password changed successfully!")
                        localStorage.removeItem("user");
                        navigate("/login")
                    }

                });

            }

        } catch (error) {
            console.log("Fetch error: ", error);
        }


    }


    return (
        <main className="main-content  mt-0">
            <div
                className="page-header align-items-start min-vh-100"
                style={{
                    backgroundImage: `url("https://images.unsplash.com/photo-1497294815431-9365093b7331?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1950&q=80")`
                }}
            >
                <span className="mask bg-gradient-dark opacity-6"></span>
                <div className="container my-auto">
                    <div className="row">
                        <div className="col-lg-4 col-md-8 col-12 mx-auto">
                            <div className="card z-index-0 fadeIn3 fadeInBottom">
                                <div className="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
                                    <div className="bg-gradient-primary shadow-primary border-radius-lg py-3 pe-1">
                                        <h4 className="text-white font-weight-bolder text-center mt-2 mb-0">Change Password</h4>
                                    </div>
                                </div>
                                <div className="card-body">
                                    <form onSubmit={handleSubmit} className="text-start">
                                        <div className="input-group input-group-outline my-3">
                                            <label className="form-label">Old Password</label>
                                            <input type="password" onChange={(e) => setOldPass(e.target.value)} className="form-control" />
                                        </div>
                                        <div className="input-group input-group-outline my-3">
                                            <label className="form-label">New Password</label>
                                            <input type="password" onChange={(e) => setNewPass(e.target.value)} className="form-control" />
                                        </div>
                                        <div className="input-group input-group-outline my-3">
                                            <label className="form-label">Confirm Password</label>
                                            <input type="password" onChange={(e) => setConfirmPass(e.target.value)} className="form-control" />
                                        </div>
                                        {error && <div className="text-danger">{error}</div>}
                                        {/* {errorChange && <div className="text-danger">{errorChange}</div>} */}

                                        <div className="text-center">
                                            <button type="submit" className="btn bg-gradient-primary w-100 my-4 mb-2">Send</button>
                                        </div>
                                        <p className="mt-4 text-sm text-center">
                                            Do you want to return to the login page?  &nbsp;
                                            {/* <a href="../pages/sign-up.html" className="text-primary text-gradient font-weight-bold">Forgot password</a> */}
                                            <Link to={"/login"} className="text-primary text-gradient font-weight-bold">
                                                Login
                                            </Link>
                                        </p>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </main>
    )
}

export default ChangePassword