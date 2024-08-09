import React, { useEffect, useState } from 'react'
import { Link } from 'react-router-dom';
import LoadingImg from '../components/LoadingImg';

const InterviewManagement = () => {
    const [listInterview, setListInterview] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    const getAllInterview = async () => {
        try {
            setIsLoading(true)
            const apiUrl = "http://localhost:8080/api";
            const user = localStorage.getItem("user");
            let token = null;
            if (user) {
                const parseUser = JSON.parse(user);
                token = parseUser.token

            }
            if (token) {
                const res = await fetch(`${apiUrl}/interview-schedule/schedulesList`, {
                    headers: {
                        "Authorization": `Bearer ${token}`,
                        "Content-Type": "application/json"
                    }
                })
                const data = await res.json();
                console.log(">>>check data: ", data);

                setListInterview(data);
            }
        } catch (error) {
            console.log("Fetch error: ", error);

        } finally {
            setIsLoading(false)
        }
    }
    useEffect(() => {
        getAllInterview()
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
                                        <h6 className="text-white text-capitalize ps-3">Interview Management</h6>
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
                                                    <th className="text-uppercase text-secondary text-xs text-dark font-weight-bolder">Title</th>
                                                    <th className="text-uppercase text-secondary text-xs text-dark font-weight-bolder ps-2">Candidate Name</th>
                                                    <th className="text-uppercase text-secondary text-xs text-dark font-weight-bolder ps-2">Interviewer</th>
                                                    <th className="text-uppercase text-secondary text-xs text-dark font-weight-bolder ps-2">Schedule</th>
                                                    <th className="text-uppercase text-secondary text-xs text-dark font-weight-bolder ps-2">Result</th>
                                                    <th className="text-uppercase text-secondary text-xs text-dark font-weight-bolder ps-2">Status</th>
                                                    <th className="text-uppercase text-secondary text-xs text-dark font-weight-bolder ps-2">Job</th>
                                                    <th className="text-uppercase text-secondary text-xs text-dark font-weight-bolder ps-2">Action</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                {listInterview.map((item: any, index: any) => {
                                                    return (
                                                        <tr key={index}>
                                                            <td>
                                                                <div className="d-flex px-2 py-1">
                                                                    <div className="d-flex flex-column justify-content-center">
                                                                        <h6 className="mb-0 text-sm">{item.scheduleTitle}</h6>

                                                                    </div>
                                                                </div>
                                                            </td>
                                                            <td>
                                                                <p className="text-xs font-weight-bold mb-0">{item.candidateName}</p>
                                                            </td>
                                                            <td>
                                                                <p className="text-xs font-weight-bold mb-0">{item.interviewerName}</p>
                                                            </td>
                                                            <td>
                                                                <p className="text-xs font-weight-bold mb-0">{item.scheduleTime}</p>
                                                            </td>
                                                            <td>
                                                                {/* <p className="text-xs font-weight-bold mb-0">{item.result}</p> */}
                                                                {item.result === null ? (
                                                                    <p className="text-xs font-weight-bold mb-0">N/A</p>
                                                                ) : (
                                                                    <>
                                                                        {item.result === "PASSED" ? <p className="text-xs font-weight-bold mb-0 text-success">PASSED</p> : <p className="text-xs font-weight-bold mb-0 text-danger">FAILED</p>}
                                                                    </>
                                                                )}
                                                            </td>
                                                            <td>
                                                                <p className="text-xs font-weight-bold mb-0">{item.status}</p>
                                                            </td>
                                                            <td>
                                                                <p className="text-xs font-weight-bold mb-0">{item.jobTitle}</p>
                                                            </td>
                                                            <td>
                                                                <Link to={`details/${item.id}`}>
                                                                    <i className="material-icons opacity-10 icon-visibility">visibility</i>
                                                                </Link>
                                                                <Link to={"#"}>
                                                                    <i className="material-icons opacity-10 icon-border-color">border_color</i>
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
                </div>
            }
        </>
    )
}

export default InterviewManagement