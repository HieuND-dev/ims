import React, { useEffect, useState } from 'react'
import { Link } from 'react-router-dom';
import LoadingImg from '../components/LoadingImg';
import AccessDenied from '../components/AccessDenied';

const OfferManagement = () => {

    const [listOffer, setListOffer] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    const user = localStorage.getItem("user");
    let role = null;
    if (user) {
        const parseUser = JSON.parse(user);
        role = parseUser.roles;
    }
    const getAllCandidate = async () => {
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
                const res = await fetch(`${apiUrl}/offers`, {
                    headers: {
                        "Authorization": `Bearer ${token}`,
                        "Content-Type": "application/json"
                    }
                })
                const data = await res.json();
                console.log(">>>check data: ", data);

                setListOffer(data);
            }
        } catch (error) {
            console.log("Fetch error: ", error);

        } finally {
            setIsLoading(false)
        }
    }



    useEffect(() => {
        getAllCandidate()
    }, [])
    return (
        <>
            {role === "INTERVIEWER"
                ?
                (<AccessDenied />)
                :
                <>
                    {isLoading ? (
                        <LoadingImg />
                    ) : <div className="container-fluid py-4">
                        <div className="row">
                            <div className="col-12">
                                <div className="card my-4">
                                    <div className="d-flex bg-gradient-dark shadow-dark border-radius-lg pt-4 pb-3 px-3 justify-content-between">
                                        <h6 className="text-white text-capitalize">Offer Management</h6>
                                        <Link to={"add"}>
                                            <i className="material-icons opacity-10 icon-white">add_circle</i>
                                        </Link>
                                    </div>
                                    <div className="card-body px-0 pb-2">
                                        <div className="table-responsive p-0">
                                            <table className="table align-items-center mb-0">
                                                <thead>
                                                    <tr>
                                                        <th className="text-uppercase text-secondary text-xs text-dark font-weight-bolder">Candidate Name</th>
                                                        <th className="text-uppercase text-secondary text-xs text-dark font-weight-bolder ps-2">Approver</th>
                                                        <th className="text-uppercase text-secondary text-xs text-dark font-weight-bolder ps-2">Department</th>
                                                        <th className="text-uppercase text-secondary text-xs text-dark font-weight-bolder ps-2">Notes</th>
                                                        <th className="text-uppercase text-secondary text-xs text-dark font-weight-bolder ps-2">Status</th>
                                                        <th className="text-uppercase text-secondary text-xs text-dark font-weight-bolder ps-2">Action</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    {listOffer.map((item: any, index: any) => {
                                                        return (
                                                            <tr key={index}>
                                                                <td>
                                                                    <div className="d-flex px-2 py-1">
                                                                        <div className="d-flex flex-column justify-content-center">
                                                                            <h6 className="mb-0 text-sm">{item.candidateName}</h6>
                                                                            <p className="text-xs text-secondary mb-0">{item.email}</p>
                                                                        </div>
                                                                    </div>
                                                                </td>
                                                                <td>
                                                                    <p className="text-xs font-weight-bold mb-0">{item.approver}</p>
                                                                </td>
                                                                <td>
                                                                    <p className="text-xs font-weight-bold mb-0">{item.position}</p>
                                                                </td>
                                                                <td>
                                                                    <p className="text-xs font-weight-bold mb-0">{item.offerNote}</p>
                                                                </td>
                                                                <td>
                                                                    <p className="text-xs font-weight-bold mb-0">{item.status}</p>
                                                                </td>
                                                                <td>
                                                                    <Link to={`details/${item.id}`}>
                                                                        <i className="material-icons opacity-10 icon-visibility">visibility</i>
                                                                    </Link>
                                                                    {/* <Link to={"#"}>
                                                                        <i className="material-icons opacity-10 icon-border-color">border_color</i>
                                                                    </Link> */}

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

            }
        </>
    )
}

export default OfferManagement