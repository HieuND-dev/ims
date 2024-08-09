import React, { useState } from 'react'
import { Link, Outlet } from 'react-router-dom'

const AdminLayout = () => {
    const [isDropdownOpen, setIsDropdownOpen] = useState(false);

    const toggleDropdown = () => {
        setIsDropdownOpen(!isDropdownOpen);
    };
    const user = localStorage.getItem("user");
    let username = null;
    let department = null;
    let role = null;
    if (user) {
        const parseUser = JSON.parse(user);
        username = parseUser.username;
        department = parseUser.department;
        role = parseUser.roles;
    }

    return (
        <div>
            <aside
                className="sidenav navbar navbar-vertical navbar-expand-xs border-0 border-radius-xl my-3 fixed-start ms-3   bg-gradient-dark"
                id="sidenav-main">

                <div className="sidenav-header">

                    <a className="navbar-brand m-0" href=""
                        target="_blank">
                        <img src="https://hcmfpt.vn/vnt_upload/about/FPT_Telecom_logo.svg" className="navbar-brand-img h-100" alt="main_logo" />
                        <span className="ms-1 font-weight-bold text-white">IMS</span>
                    </a>
                </div>

                <hr className="horizontal light mt-0 mb-2" />
                <div className="collapse navbar-collapse  w-auto " id="sidenav-collapse-main">
                    <ul className="navbar-nav">
                        <li className="nav-item">
                            <Link className="nav-link text-white " to={"/"}>
                                <div className="text-white text-center me-2 d-flex align-items-center justify-content-center">
                                    <i className="material-icons opacity-10">dashboard</i>

                                </div>
                                <span className="nav-link-text ms-1">Dashboard</span>
                            </Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link text-white " to={"/job"}>
                                <div className="text-white text-center me-2 d-flex align-items-center justify-content-center">
                                    <i className="material-icons opacity-10">work</i>
                                </div>
                                <span className="nav-link-text ms-1">Job</span>
                            </Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link text-white " to={"/candidate"}>
                                <div className="text-white text-center me-2 d-flex align-items-center justify-content-center">
                                    <i className="material-icons opacity-10">groups</i>
                                </div>
                                <span className="nav-link-text ms-1">Candidate</span>
                            </Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link text-white " to={"/interview"}>
                                <div className="text-white text-center me-2 d-flex align-items-center justify-content-center">
                                    <i className="material-icons opacity-10">forum</i>
                                </div>
                                <span className="nav-link-text ms-1">Interview</span>
                            </Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link text-white " to={"/offer"}>
                                <div className="text-white text-center me-2 d-flex align-items-center justify-content-center">
                                    <i className="material-icons opacity-10">description</i>
                                </div>
                                <span className="nav-link-text ms-1">Offer</span>
                            </Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link text-white " to={"/user"}>
                                <div className="text-white text-center me-2 d-flex align-items-center justify-content-center">
                                    <i className="material-icons opacity-10">person</i>
                                </div>
                                <span className="nav-link-text ms-1">User</span>
                            </Link>
                        </li>
                    </ul>
                </div>

                <div className="sidenav-footer position-absolute w-100 bottom-0 ">
                    <div className="mx-3">

                        <a className="btn bg-gradient-dark w-100"
                            href="#" type="button">NHÓM 1
                            pro</a>
                    </div>

                </div>

            </aside>

            <main className="main-content border-radius-lg ">
                <nav className="navbar navbar-main navbar-expand-lg px-0 mx-4 shadow-none border-radius-xl" id="navbarBlur"
                    data-scroll="true">
                    <div className="container-fluid py-1 px-3">

                        <div className="collapse navbar-collapse mt-sm-0 mt-2 me-md-0 me-sm-4" id="navbar">
                            <div className="ms-md-auto pe-md-3 d-flex align-items-center">

                                {/* <div className="input-group input-group-outline">
                                    <label className="form-label">Type here...</label>
                                    <input type="text" className="form-control" />
                                </div> */}

                            </div>
                            <ul className="navbar-nav justify-content-end">
                                <li className="nav-item d-flex align-items-center position-relative">
                                    <span className="nav-link text-body font-weight-bold px-0 d-sm-inline d-none">
                                        {username}
                                    </span>
                                    &nbsp; - &nbsp;
                                    <span className="nav-link text-body font-weight-bold px-0 d-sm-inline d-none">
                                        {department} Department
                                    </span>
                                    <div className="dropdown">
                                        <i
                                            className="material-icons opacity-10 ms-sm-2 me-sm-2"
                                            onClick={toggleDropdown}
                                            style={{ cursor: 'pointer' }}
                                        >
                                            person
                                        </i>
                                        {isDropdownOpen && (
                                            <div className="dropdown-menu show" style={{ left: '-100px' }}>
                                                <span className="dropdown-item">Role: {role}</span>
                                                <Link to={"/logout"} >
                                                    <span className="dropdown-item">Logout</span>
                                                </Link>
                                                <Link to={"/change-password"} >
                                                    <span className="dropdown-item">Change Password</span>
                                                </Link>
                                            </div>
                                        )}
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>

                </nav >



                < Outlet />


            </main >




        </div >

    )
}

export default AdminLayout