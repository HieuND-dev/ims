
import React, { useEffect, useState } from 'react'
import { Link, useNavigate } from 'react-router-dom';
import Swal from 'sweetalert2'
import LoadingImg from '../components/LoadingImg';
const CandidateManagement = () => {
    const [listCandidate, setListCandidate] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    const navigate = useNavigate();
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
                    const res = await fetch(`${apiUrl}/api/candidates/${id}`, {
                        method: "DELETE",
                        headers: {
                            "Authorization": `Bearer ${token}`,
                            "Content-Type": "application/json"
                        },

                    });
                    if (res.ok) {
                        const updatedListRes = await fetch(`${apiUrl}/api/candidates`, {
                            method: "GET",
                            headers: {
                                "Authorization": `Bearer ${token}`
                            }
                        });
                        const updatedList = await updatedListRes.json();
                        setListCandidate(updatedList);
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


    const getAllCandidate = async () => {
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
                const res = await fetch(`${apiUrl}/candidates`, {
                    headers: {
                        "Authorization": `Bearer ${token}`,
                        "Content-Type": "application/json"
                    }
                })
                const data = await res.json();
                console.log(">>>check data: ", data);

                setListCandidate(data);
            }
        } catch (error) {
            console.log("Fetch error: ", error);

        } finally {
            setIsLoading(false);
        }

    }

    useEffect(() => {
        getAllCandidate()
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
                                        <h6 className="text-white text-capitalize ps-3">Candidate Management</h6>
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
                                                    <th className="text-uppercase text-secondary text-xs text-dark font-weight-bolder">Author</th>
                                                    <th className="text-uppercase text-secondary text-xs text-dark font-weight-bolder ps-2">Phone No.</th>
                                                    <th className="text-uppercase text-secondary text-xs text-dark font-weight-bolder ps-2">Current Position</th>
                                                    <th className="text-uppercase text-secondary text-xs text-dark font-weight-bolder ps-2">Owner HR</th>
                                                    <th className="text-uppercase text-secondary text-xs text-dark font-weight-bolder ps-2">Status</th>
                                                    <th className="text-uppercase text-secondary text-xs text-dark font-weight-bolder ps-2">Action</th>
                                                </tr>
                                            </thead>
                                            <tbody>


                                                {listCandidate.map((item: any, index: any) => {
                                                    return (
                                                        <tr key={index}>
                                                            <td>
                                                                <div className="d-flex px-2 py-1">
                                                                    <div className="d-flex flex-column justify-content-center">
                                                                        <h6 className="mb-0 text-sm">{item.fullName}</h6>
                                                                        <p className="text-xs text-secondary mb-0">{item.email}</p>
                                                                    </div>
                                                                </div>
                                                            </td>
                                                            <td>
                                                                <p className="text-xs font-weight-bold mb-0">{item.phoneNo}</p>
                                                            </td>
                                                            <td>
                                                                <p className="text-xs font-weight-bold mb-0">{item.position}</p>
                                                            </td>
                                                            <td>
                                                                <p className="text-xs font-weight-bold mb-0">{item.ownerHR}</p>
                                                            </td>
                                                            <td>
                                                                {item.candidateStatus === "BANNED" ? <p className="text-xs text-danger font-weight-bold mb-0">BANNED</p> : <p className="text-xs font-weight-bold mb-0">{item.candidateStatus}</p>}
                                                                {/* <p className="text-xs font-weight-bold mb-0">{item.candidateStatus}</p> */}
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
                                                })}

                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>}
        </>
    )
}

export default CandidateManagement