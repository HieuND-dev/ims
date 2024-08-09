import React, { useEffect, useState } from 'react'
import { Link, useNavigate, useParams } from 'react-router-dom'
import Swal from 'sweetalert2'
import LoadingImg from "../../components/LoadingImg"
const UserDetail = () => {
    const navigate = useNavigate();
    const { id } = useParams();
    const [user, setUser]: any = useState("");
    const [isLoading, setIsLoading] = useState(false);
    const handleChangeActive = async () => {
        try {
            const result = await Swal.fire({
                title: "Are you sure?",
                icon: "warning",
                showCancelButton: true,
                confirmButtonColor: "#3085d6",
                cancelButtonColor: "#d33",
                confirmButtonText: "Yes!"
            });
            if (result.isConfirmed) {
                const apiUrl = "http://localhost:8080";
                const user = localStorage.getItem("user");
                let token = null;
                if (user) {
                    const parseUser = JSON.parse(user);
                    token = parseUser.token
                    console.log(token);
                }
                if (token) {
                    const res = await fetch(`${apiUrl}/api/users/activate/${id}`, {
                        method: "PUT",
                        headers: {
                            "Authorization": `Bearer ${token}`,
                            "Content-Type": "application/json"
                        },
                    });
                    navigate("/user")

                    if (res.ok) {
                        const updatedListRes = await fetch(`${apiUrl}/api/users/viewList`, {
                            method: "GET",
                            headers: {
                                "Authorization": `Bearer ${token}`
                            }
                        });
                        const updatedList = await updatedListRes.json();
                        setUser(updatedList);
                        Swal.fire({
                            title: "Change!",
                            text: "Account has been activated!",
                            icon: "success"
                        });
                    }
                }
            }


        } catch (error) {
            console.log("Fetch error: ", error);
        }

    }
    const handleChangeInactive = async () => {

        try {
            const result = await Swal.fire({
                title: "Are you sure?",
                icon: "warning",
                showCancelButton: true,
                confirmButtonColor: "#3085d6",
                cancelButtonColor: "#d33",
                confirmButtonText: "Yes!"
            });
            if (result.isConfirmed) {
                const apiUrl = "http://localhost:8080";
                const user = localStorage.getItem("user");
                let token = null;
                if (user) {
                    const parseUser = JSON.parse(user);
                    token = parseUser.token
                    console.log(token);
                }
                if (token) {
                    const res = await fetch(`${apiUrl}/api/users/deactivate/${id}`, {
                        method: "PUT",
                        headers: {
                            "Authorization": `Bearer ${token}`,
                            "Content-Type": "application/json"
                        },
                    });
                    navigate("/user")

                    if (res.ok) {
                        const updatedListRes = await fetch(`${apiUrl}/api/users/viewList`, {
                            method: "GET",
                            headers: {
                                "Authorization": `Bearer ${token}`
                            }
                        });
                        const updatedList = await updatedListRes.json();
                        setUser(updatedList);
                        Swal.fire({
                            title: "Change!",
                            text: "Account has been deactivated!",
                            icon: "success"
                        });
                    }
                }
            }


        } catch (error) {
            console.log("Fetch error: ", error);
        }

    }
    const hanldeShowDetailUser = async () => {
        setIsLoading(true);
        try {
            const apiUrl = "http://localhost:8080/api";
            const user = localStorage.getItem("user");
            let token = null;
            if (user) {
                const parseUser = JSON.parse(user);
                token = parseUser.token
                console.log(token);

            }
            if (token) {
                const res = await fetch(`${apiUrl}/users/details/${id}`, {
                    headers: {
                        "Authorization": `Bearer ${token}`,
                        // "Content-Type": "application/json"
                    }
                });
                const data = await res.json();
                setUser(data);

            }

        } catch (error) {
            console.log("Fetch error: ", error);

        } finally {
            setIsLoading(false);
        }
    }
    useEffect(() => {
        hanldeShowDetailUser();
    }, [])

    return (
        <>
            {isLoading
                ? (<LoadingImg />) : <div className="container-fluid py-4">
                    <div className="row">
                        <div className="col-12">
                            <div className="card my-4">
                                <div className="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
                                    <div className="bg-gradient-dark shadow-dark border-radius-lg pt-4 pb-3">
                                        <h6 className="text-white text-capitalize ps-3">User Details</h6>

                                    </div>
                                </div>
                                <div className="card-body px-0 pb-2">
                                    <div className="p-3">
                                        <div className="row">
                                            <div className="col-md-6">
                                                <div className="mb-3">
                                                    <label className="text-uppercase text-secondary text-xs text-dark font-weight-bolder text-dark">Full name</label>
                                                    <p className="form-control-plaintext">{user.fullName}</p>
                                                </div>
                                                <div className="mb-3">
                                                    <label className="text-uppercase text-secondary text-xs text-dark font-weight-bolder text-dark">D.O.B</label>
                                                    <p className="form-control-plaintext">{user.dateOfBirth}</p>
                                                </div>
                                                <div className="mb-3">
                                                    <label className="text-uppercase text-secondary text-xs text-dark font-weight-bolder text-dark">Phone Number</label>
                                                    <p className="form-control-plaintext">{user.phoneNo}</p>
                                                </div>
                                                <div className="mb-3">
                                                    <label className="text-uppercase text-secondary text-xs text-dark font-weight-bolder text-dark">Role</label>
                                                    <p className="form-control-plaintext">{user.role}</p>
                                                </div>
                                                <div className="mb-3">
                                                    <label className="text-uppercase text-secondary text-xs text-dark font-weight-bolder text-dark">Status</label>
                                                    <p className="form-control-plaintext">{user.status}</p>
                                                </div>
                                            </div>
                                            <div className="col-md-6">
                                                <div className="position-absolute top-7 end-0 p-3">
                                                    {/* <button className="btn btn-success">{user.status}</button> */}
                                                    {user.status === "ACTIVE" ?
                                                        <button className="btn btn-danger" onClick={handleChangeInactive}>De-active user</button>
                                                        :
                                                        <button className="btn btn-success" onClick={handleChangeActive}>Active user</button>
                                                    }
                                                </div>
                                                <div className="mb-3">
                                                    <label className="text-uppercase text-secondary text-xs text-dark font-weight-bolder text-dark">Email</label>
                                                    <p className="form-control-plaintext">{user.email}</p>
                                                </div>
                                                <div className="mb-3">
                                                    <label className="text-uppercase text-secondary text-xs text-dark font-weight-bolder text-dark">Address</label>
                                                    <p className="form-control-plaintext">{user.address}</p>
                                                </div>
                                                <div className="mb-3">
                                                    <label className="text-uppercase text-secondary text-xs text-dark font-weight-bolder text-dark">Gender</label>
                                                    <p className="form-control-plaintext">{user.gender}</p>
                                                </div>
                                                <div className="mb-3">
                                                    <label className="text-uppercase text-secondary text-xs text-dark font-weight-bolder text-dark">Department</label>
                                                    <p className="form-control-plaintext">{user.department}</p>
                                                </div>
                                                <div className="mb-3">
                                                    <label className="text-uppercase text-secondary text-xs text-dark font-weight-bolder text-dark">Note</label>
                                                    <p className="form-control-plaintext">{user.note}</p>
                                                </div>
                                            </div>
                                        </div>
                                        <div className="d-flex justify-content-end">
                                            {/* <button type="submit" className="btn btn-primary me-2">Submit</button> */}

                                            <Link to={"/user"} >
                                                <button type="button" className="btn btn-danger">Cancel</button>
                                            </Link>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            }
        </>

    )
}

export default UserDetail