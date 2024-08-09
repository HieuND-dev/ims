import { log } from 'console';
import React, { useEffect, useState } from 'react'
import { Link, Outlet } from 'react-router-dom';
import LoadingImg from '../components/LoadingImg';
import AccessDenied from '../components/AccessDenied';
const UserManagement = () => {
    const [listUser, setListuser] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    const user = localStorage.getItem("user");
    let role = null;
    if (user) {
        const parseUser = JSON.parse(user);
        role = parseUser.roles;
    }

    const getAllUser = async () => {
        setIsLoading(true);
        try {
            const apiUrl = "http://localhost:8080/api";
            const user = localStorage.getItem("user");
            let token = null;
            if (user) {
                const parseUser = JSON.parse(user);
                token = parseUser.token

            }
            if (token) {
                const res = await fetch(`${apiUrl}/users/viewList`, {
                    headers: {
                        "Authorization": `Bearer ${token}`,
                        "Content-Type": "application/json"
                    }
                })
                const data = await res.json();

                setListuser(data);
            }
        } catch (error) {
            console.log("Fetch error: ", error);
        } finally {
            setIsLoading(false);
        }
    }

    useEffect(() => {
        getAllUser()
    }, [])

    return (
        <>
            {role === "ADMIN"
                ?
                <>
                    {
                        isLoading ? (

                            <LoadingImg />

                        ) :
                            <div className="container-fluid py-4">
                                <div className="row">
                                    <div className="col-12">
                                        <div className="card my-4">
                                            <div className="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
                                                <div className="d-flex bg-gradient-dark shadow-dark border-radius-lg pt-4 pb-3 px-3 justify-content-between">
                                                    <h6 className="text-white text-capitalize">User Management</h6>
                                                    <Link to={"add"}>
                                                        <i className="material-icons opacity-10 icon-white">add_circle</i>
                                                    </Link>
                                                </div>

                                            </div>
                                            <div className="card-body px-0 pb-2">
                                                <div className="table-responsive p-0">
                                                    <table className="table align-items-center mb-0">
                                                        <thead>
                                                            <tr>
                                                                <th className="text-uppercase text-secondary text-xs text-dark font-weight-bolder ">Username</th>
                                                                <th className="text-uppercase text-secondary text-xs text-dark font-weight-bolder ps-2">Role</th>
                                                                <th className="text-uppercase text-secondary text-xs text-dark font-weight-bolder ps-2">PhoneNo</th>
                                                                <th className="text-center text-uppercase text-secondary text-xs text-dark font-weight-bolder">Status
                                                                </th>
                                                                <th className="text-center text-uppercase text-secondary text-xs text-dark font-weight-bolder">
                                                                    Action</th>

                                                            </tr>
                                                        </thead>
                                                        <tbody>

                                                            {listUser.map((item: any, index: any) => {
                                                                return (
                                                                    <>
                                                                        <tr>
                                                                            <td>
                                                                                <div className="d-flex px-2 py-1">
                                                                                    <div className="d-flex flex-column justify-content-center">
                                                                                        <h6 className="mb-0 text-sm">{item.username}</h6>
                                                                                        <p className="text-xs text-secondary mb-0">{item.email}</p>
                                                                                    </div>
                                                                                </div>
                                                                            </td>
                                                                            <td>
                                                                                <p className="text-xs font-weight-bold mb-0">{item.role}</p>

                                                                            </td>
                                                                            <td> <p className="text-xs font-weight-bold mb-0">{item.phoneNo}</p></td>
                                                                            <td className="align-middle text-center text-sm">
                                                                                {item.status === "ACTIVE" ? <span className="badge badge-sm bg-gradient-success"> ACTIVE</span> : <span className="badge badge-sm bg-gradient-danger">INACTIVE</span>}

                                                                            </td>
                                                                            <td className="align-middle text-center text-sm">

                                                                                <Link to={`details/${item.id}`}>
                                                                                    <i className="material-icons opacity-10 icon-visibility ">visibility</i>
                                                                                </Link>
                                                                                &nbsp;&nbsp;
                                                                                <Link to={`update/${item.id}`}>
                                                                                    <i className="material-icons opacity-10  icon-border-color">border_color</i>
                                                                                </Link>
                                                                            </td>
                                                                        </tr>
                                                                    </>
                                                                )
                                                            })}



                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </div>
                    }
                </>

                : (<AccessDenied />)}
        </>
    )
}

export default UserManagement