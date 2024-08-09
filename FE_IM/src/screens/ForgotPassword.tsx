import React, { useEffect, useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import toastr from 'toastr';
import "toastr/build/toastr.min.css"
const ForgotPassword = () => {

    const [email, setEmail] = useState("");
    const navigate = useNavigate();


    const handleForgot = async (e: any) => {
        e.preventDefault();
        try {
            const formData = new FormData();
            formData.append("email", email);
            const apiUrl = "http://localhost:8080/password";
            const res = await fetch(`${apiUrl}/forgot`, {
                method: "PUT",
                body: formData
            })
            if (res.ok) {
                toastr.success("We've sent an email with the link to reset your password!");
                navigate("/login");
            }
        } catch (error) {
            console.log("Fetch error: ", error);

        }

    }

    useEffect(() => {
        handleForgot();
    }, [])

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
                                        <h4 className="text-white font-weight-bolder text-center mt-2 mb-0">Forgot Password</h4>

                                    </div>
                                </div>
                                <div className="card-body">
                                    <form onSubmit={handleForgot} className="text-start">
                                        <div className="input-group input-group-outline my-3">
                                            <label className="form-label">Email</label>
                                            <input type="email" onChange={(e) => setEmail(e.target.value)} className="form-control" />
                                        </div>


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

export default ForgotPassword