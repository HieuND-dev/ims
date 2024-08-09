import React, { useEffect, useState } from 'react'
import { Link } from 'react-router-dom';
import Swal from 'sweetalert2'
import LoadingImg from '../components/LoadingImg';
const JobManagement = () => {


    const [listJob, setListJob] = useState([]);
    const [isLoading, setIsLoading] = useState(false);

    const handleDelete = async (id: any) => {
        

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
                    const res = await fetch(`${apiUrl}/api/jobs/${id}`, {
                        method: "DELETE",
                        headers: {
                            "Authorization": `Bearer ${token}`,
                            "Content-Type": "application/json"
                        },
                    });
                    if (res.ok) {
                        const updatedListRes = await fetch(`${apiUrl}/api/jobs/jobList`, {
                            method: "GET",
                            headers: {
                                "Authorization": `Bearer ${token}`
                            }
                        });
                        const updatedList = await updatedListRes.json();
                        setListJob(updatedList);
                        Swal.fire({
                            title: "Deleted!",
                            text: "Your file has been deleted.",
                            icon: "success"
                        });
                    }
                }
            }


        } catch (error) {
            console.log("Fetch error: ", error);
        }
    }

    const getAllJob = async () => {
        try {
            setIsLoading(true);
            const apiUrl = "http://localhost:8080/api";
            const user = localStorage.getItem("user");
            let token = null;
            if (user) {
                const parseUser = JSON.parse(user);
                token = parseUser.token

            }
            if (token) {
                const res = await fetch(`${apiUrl}/jobs/jobList`, {
                    headers: {
                        "Authorization": `Bearer ${token}`,
                        "Content-Type": "application/json"
                    }
                })
                const data = await res.json();
                setListJob(data);
            }
        } catch (error) {
            console.log("Fetch error: ", error);

        } finally {
            setIsLoading(false);
        }
    }

    useEffect(() => {
        getAllJob()
    }, [])

    return (
        <>
            {isLoading ? (
                <LoadingImg />
            ) :
                <div className="container-fluid py-4">
                    <div className="row">
                        <div className="col-12">
                            <div className="card my-4">
                                <div className="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
                                    <div className="d-flex bg-gradient-dark shadow-dark border-radius-lg pt-4 pb-3 px-3 justify-content-between">
                                        <h6 className="text-white text-capitalize ps-3">Job Management</h6>
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
                                                    <th className="text-uppercase text-secondary text-xs text-dark font-weight-bolder">Job Title</th>
                                                    <th className="text-uppercase text-secondary text-xs text-dark font-weight-bolder ps-2">Required Skills</th>
                                                    <th className="text-uppercase text-secondary text-xs text-dark font-weight-bolder ps-2">Start date</th>
                                                    <th className="text-uppercase text-secondary text-xs text-dark font-weight-bolder ps-2">End date</th>
                                                    <th className="text-uppercase text-secondary text-xs text-dark font-weight-bolder ps-2">Level</th>
                                                    <th className="text-uppercase text-secondary text-xs text-dark font-weight-bolder ps-2">Status</th>
                                                    <th className="text-uppercase text-xs text-dark font-weight-bolder ps-2">Action</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                {
                                                    listJob.map((item: any, index: any) => {
                                                        return (
                                                            <tr>
                                                                <td>
                                                                    <div className="d-flex px-2 py-1">
                                                                        <div className="d-flex flex-column justify-content-center">
                                                                            <h6 className="mb-0 text-sm">{item.jobTitle}</h6>
                                                                        </div>
                                                                    </div>
                                                                </td>
                                                                <td>
                                                                    <p className="text-xs font-weight-bold mb-0 d-inline">
                                                                        {Array.isArray(item.skills) ? item.skills.join(", ") : item.skills}
                                                                    </p>
                                                                </td>
                                                                <td>
                                                                    <p className="text-xs font-weight-bold mb-0">{item.startDate}</p>
                                                                </td>
                                                                <td>
                                                                    <p className="text-xs font-weight-bold mb-0">{item.endDate}</p>
                                                                </td>
                                                                <td>
                                                                    {item.levels.map((level: any, index: any) => {
                                                                        return (
                                                                            <p key={index} className="text-xs font-weight-bold mb-0 d-inline">{level} {index < item.levels.length - 1 && ", "} </p>
                                                                        )
                                                                    })}
                                                                </td>
                                                                <td>
                                                                    <p className="text-xs font-weight-bold mb-0">{item.jobStatus}</p>
                                                                </td>
                                                                <td>
                                                                    <Link to={`details/${item.id}`}>
                                                                        <i className="material-icons opacity-10 icon-visibility">visibility</i>
                                                                    </Link>
                                                                    <Link to={"#"}>
                                                                        <i className="material-icons opacity-10 icon-border-color">border_color</i>
                                                                    </Link>
                                                                    <Link to={"#"} onClick={() => handleDelete(item.id)}>
                                                                        <i className="material-icons opacity-10 icon-delete ">delete</i>
                                                                    </Link>
                                                                </td>

                                                            </tr>
                                                        )
                                                    })
                                                }

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
    )
}

export default JobManagement