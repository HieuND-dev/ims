import { off } from 'process';
import React, { useEffect, useState } from 'react'
import { Link, useNavigate, useParams } from 'react-router-dom'
import Select from 'react-select';
import toastr from 'toastr';
import "toastr/build/toastr.min.css"
import LoadingImg from '../../components/LoadingImg';
const optionsPosition = [
  { value: 'BACKEND_DEVELOPER', label: 'BACKEND_DEVELOPER' },
  { value: 'BUSINESS_ANALYST', label: 'BUSINESS_ANALYST' },
  { value: 'TESTER', label: 'TESTER' },
  { value: 'HR', label: 'HR' },
  { value: 'PROJECT_MANAGER', label: 'PROJECT_MANAGER' },
  { value: 'NOT_AVAILABLE', label: 'NOT_AVAILABLE' },
];
const optionsLevels = [
  { value: 'FRESHER', label: 'FRESHER' },
  { value: 'JUNIOR', label: 'JUNIOR' },
  { value: 'SENIOR', label: 'SENIOR' },
  { value: 'LEADER', label: 'LEADER' },
  { value: 'MANAGER', label: 'MANAGER' },
  { value: 'VICE_HEAD', label: 'VICE_HEAD' },
];
const OfferDetail = () => {
  const user = localStorage.getItem("user");
  const [isLoading, setIsLoading] = useState(false);
  let roles = null;
  if (user) {
    const parseUser = JSON.parse(user);
    roles = parseUser.roles;
  }
  const navigate = useNavigate();
  const { id } = useParams();
  const [offer, setOffer]: any = useState([]);
  const [initOffer, setIntOffer] = useState({});
  const [position, setPosition] = useState("");
  const [contractType, setContractType] = useState("");
  const [department, setDepartment] = useState("");
  const [contractFrom, setContractFrom] = useState("");
  const [contractTo, setContractTo] = useState("");
  const [level, setLevel] = useState("");
  const [dueDate, setDueDate] = useState("");
  const [basicSalary, setBasicSalary] = useState("");
  const [offerNote, setOfferNote] = useState("");
  const [approver, setApprover] = useState([]);
  const [selectedManager, setSelectedManager] = useState('');
  const [recruiterOwner, setRecruiterOwner] = useState([]);
  const [selectedRecruiter, setSelectedRecruiter] = useState('');
  const [status, setStatus] = useState('');
  const [initStatus, setInitStatus] = useState(offer.status);

  const handleShowRecruiter = async () => {

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
        const res = await fetch(`${apiUrl}/api/users?role=RECRUITER`, {
          method: "GET",
          headers: {
            "Authorization": `Bearer ${token}`,
          },

        });
        const data = await res.json();

        setRecruiterOwner(data);
      }
    } catch (error) {
      console.log("Fetch error: ", error);

    }
  }
  const handleShowManager = async () => {

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
        const res = await fetch(`${apiUrl}/api/users?role=MANAGER`, {
          method: "GET",
          headers: {
            "Authorization": `Bearer ${token}`,
          },

        });
        const data = await res.json();

        setApprover(data);
      }
    } catch (error) {
      console.log("Fetch error: ", error);

    }
  }
  const handleSelectChangeRecruiter = (e: any) => {
    setSelectedRecruiter(e.target.value);
  };
  const handleSelectChangeManager = (e: any) => {
    setSelectedManager(e.target.value);
  };
  const hanldeShowDetailOffer = async () => {
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
        const res = await fetch(`${apiUrl}/offers/${id}`, {
          headers: {
            "Authorization": `Bearer ${token}`,
            // "Content-Type": "application/json"
          }
        });
        const data = await res.json();

        setOffer(data);
        setIntOffer(data);
        setPosition(data.position || "");
        setContractType(data.contractType || "");
        setDepartment(data.department || "");
        setContractFrom(data.contractFrom || "");
        setContractTo(data.contractTo || "");
        setLevel(data.level || "");
        setDueDate(data.dueDate || "");
        setBasicSalary(data.basicSalary || "");
        setOfferNote(data.offerNote || "");
        setSelectedManager(data.approver || '');
        setSelectedRecruiter(data.recruiterOwner || '');
        setStatus(data.status || '');

      }

    } catch (error) {
      console.log("Fetch error: ", error);
    } finally {
      setIsLoading(false);
    }
  }
  const handleApprove = async () => {
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
        const res = await fetch(`${apiUrl}/offers/approve/${id}`, {
          method: "PATCH",
          headers: {
            "Authorization": `Bearer ${token}`,
            // "Content-Type": "application/json"
          }
        }).then((data) => {
          console.log(">>>check status data: ", data.status);
          if (data.status === 200) {
            toastr.success("Offer has been approved!")
            navigate("/offer")
          } else {
            toastr.error("You do not have permission to access this!")
          }

        });

      }

    } catch (error) {
      console.log("Fetch error: ", error);
    }


  }
  const handleReject = async () => {
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
        const res = await fetch(`${apiUrl}/offers/reject/${id}`, {
          method: "PATCH",
          headers: {
            "Authorization": `Bearer ${token}`,
            // "Content-Type": "application/json"
          }
        }).then((data) => {

          if (data.status === 200) {
            toastr.success("Offer has been rejected!")
            navigate("/offer")
          } else {
            toastr.error("You do not have permission to access this!")
          }

        });
      }

    } catch (error) {
      console.log("Fetch error: ", error);
    }
  }
  const handleCancel = async () => {
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
        const res = await fetch(`${apiUrl}/offers/cancel/${id}`, {
          method: "PATCH",
          headers: {
            "Authorization": `Bearer ${token}`,
            // "Content-Type": "application/json"
          }
        });
        if (res.ok) {
          toastr.success("Offer has been cancelled!");
          navigate("/offer")
        }
      }

    } catch (error) {
      console.log("Fetch error: ", error);
    }
  }
  const handleAccept = async () => {
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
        const res = await fetch(`${apiUrl}/offers/accept/${id}`, {
          method: "PATCH",
          headers: {
            "Authorization": `Bearer ${token}`,
            // "Content-Type": "application/json"
          }
        }).then((data) => {

          if (data.status === 200) {
            toastr.success("Offer has been accepted!")
            navigate("/offer")
          } else {
            toastr.error("You do not have permission to access this!")
          }

        });
      }

    } catch (error) {
      console.log("Fetch error: ", error);
    }


  }
  const handleDecline = async () => {
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
        const res = await fetch(`${apiUrl}/offers/decline/${id}`, {
          method: "PATCH",
          headers: {
            "Authorization": `Bearer ${token}`,
            // "Content-Type": "application/json"
          }
        }).then((data) => {

          if (data.status === 200) {
            toastr.success("Offer has been declined!")
            navigate("/offer")
          } else {
            toastr.error("You do not have permission to access this!")
          }

        });
      }

    } catch (error) {
      console.log("Fetch error: ", error);
    }
  }
  const handleSubmit = async (e: any) => {
    e.preventDefault();
    const updateOffer = {
      position: position,
      contractType: contractType,
      level: level,
      department: department,
      contractFrom: contractFrom,
      contractTo: contractTo,
      dueDate: dueDate,
      basicSalary: basicSalary,
      offerNote: offerNote,
      approver: selectedManager,
      recruiterOwner: selectedRecruiter,
      status: status
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
        const res = await fetch(`${apiUrl}/api/offers/${id}`, {
          method: "PUT",
          headers: {
            "Authorization": `Bearer ${token}`,
            "Content-Type": "application/json"
          },
          body: JSON.stringify(updateOffer)
        }).then((data) => {

          if (data.status === 200) {
            toastr.success("Update successfully!")
            navigate("/offer")
          } else {
            toastr.error("You do not have permission to access this!")
          }
        });;
      }

    } catch (error) {
      console.log("Fetch error: ", error);
    }

  }
  useEffect(() => {
    handleShowRecruiter();
    handleShowManager();
    hanldeShowDetailOffer();
  }, [])
  return (
    <>
      {isLoading ? (<LoadingImg />) : (
        <div className="container-fluid py-4">
          <div className="row">
            <div className="col-12">
              <div className="card my-4">
                <div className="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
                  <div className="bg-gradient-dark shadow-dark border-radius-lg pt-4 pb-3">
                    <h6 className="text-white text-capitalize ps-3">Offer Details</h6>

                  </div>
                </div>
                <div className="card-body px-0 pb-2">

                  {initStatus === "WAITING_FOR_RESPONSE" ?
                    (<div className="d-flex justify-content-end pe-3">
                      <button className="btn btn-success me-3" onClick={handleAccept}>Accepted Offer</button>
                      <button className="btn btn-warning me-3" onClick={handleDecline}>Declined Offer</button>
                      <button className="btn btn-danger" onClick={handleCancel}>Cancel Offer</button>
                    </div>)
                    : (initStatus === "WAITING_FOR_APPROVAL"
                      ?
                      (roles === "RECRUITER"
                        ? (<button className="btn btn-danger" onClick={handleCancel}>Cancel Offer</button>)
                        : (<div className="d-flex justify-content-end pe-3">
                          <button className="btn btn-success me-3" onClick={handleApprove}>Approval </button>
                          <button className="btn btn-warning me-3" onClick={handleReject}>Reject </button>
                          <button className="btn btn-danger" onClick={handleCancel}>Cancel Offer</button>
                        </div>))
                      : (<div className="d-flex justify-content-end pe-3">
                        <button className="btn btn-danger" onClick={handleCancel}>Cancel Offer</button>
                      </div>))
                  }
                  <div className="p-3">
                    <form onSubmit={handleSubmit}>
                      <div className="row">
                        <div className="col-md-6">
                          <div className="mb-3">
                            <label className="text-uppercase text-secondary text-xs text-dark font-weight-bolder text-dark">Candidate</label>
                            <p className="form-control-plaintext">{offer.candidateName}</p>
                          </div>
                          <div className="mb-3">
                            <label className="form-label text-dark">Position</label>
                            <select className="form-select" value={offer.position} onChange={(e) => {
                              offer.position = e.target.value
                              setPosition(e.target.value)
                            }
                            }>
                              <option value="" disabled selected className='text-secondary'>Select a position</option>
                              {optionsPosition.map((pos, index) => {
                                return (
                                  <option>{pos.value}</option>
                                )
                              })}
                            </select>
                          </div>
                          <div className="mb-3">
                            <label className="form-label">Approver</label>
                            <select className="form-select" defaultValue={offer.approver} value={selectedManager} onChange={handleSelectChangeManager}>
                              <option selected disabled >Manager name</option>
                              {
                                Array.isArray(approver) ? approver.map((item, index) => {
                                  return (
                                    <option value={item} >{item}</option>
                                  )
                                }) : <option value="" disabled>Not value</option>
                              }
                            </select>
                          </div>
                          <div className="mb-3">
                            <label className="text-uppercase text-secondary text-xs text-dark font-weight-bolder text-dark">Interview info</label>
                            <p className="form-control-plaintext">{offer.interviewInfo}</p>
                          </div>
                          <div className="row mb-3">
                            <div className="col">
                              <div className="form-group">
                                <label htmlFor="contractFrom" className="form-label">From</label>
                                <input
                                  type="date"
                                  className="form-control"
                                  id="contractFrom"
                                  defaultValue={offer.contractFrom}
                                  onChange={(e) => setContractFrom(e.target.value)}
                                />
                              </div>
                            </div>
                            <div className="col">
                              <div className="form-group">
                                <label htmlFor="contractTo" className="form-label">To</label>
                                <input
                                  type="date"
                                  className="form-control"
                                  id="contractTo"
                                  defaultValue={offer.contractTo}
                                  onChange={(e) => setContractTo(e.target.value)}
                                />
                              </div>
                            </div>
                          </div>
                          <div className="mb-3">
                            <label className="form-label text-dark">Status</label>
                            <select className="form-select" value={offer.status} onChange={(e) => {
                              offer.status = e.target.value
                              setStatus(e.target.value)
                            }}>
                              <option value="" disabled defaultValue={offer.status} selected className='text-secondary'>Select a status</option>
                              <option value="WAITING_FOR_APPROVAL">WAITING_FOR_APPROVAL</option>
                              <option value="WAITING_FOR_RESPONSE">WAITING_FOR_RESPONSE</option>

                            </select>
                          </div>
                        </div>

                        <div className="col-md-6">

                          <div className="mb-3">
                            <label className="form-label text-dark">Contract Type</label>
                            <select className="form-select" value={offer.contractType} onChange={(e) => {
                              offer.contractType = e.target.value
                              setContractType(e.target.value)
                            }}>
                              <option value="" selected disabled className='text-secondary'>Select a ContractType</option>
                              <option value="TRIAL">TRIAL</option>
                              <option value="TRAINEE">TRAINEE</option>
                              <option value="ONE_YEAR">ONE_YEAR</option>
                              <option value="THREE_YEARS">THREE_YEARS</option>
                              <option value="UNLIMITED">UNLIMITED</option>
                            </select>
                          </div>
                          <div className="mb-3">
                            <label className="form-label text-dark">levels</label>
                            <select className="form-select" value={offer.level} onChange={(e) => {
                              offer.level = e.target.value
                              setLevel(e.target.value)
                            }}>
                              <option value="" selected disabled className='text-secondary'>Select a level</option>
                              {optionsLevels.map((lev, index) => {
                                return (
                                  <option>{lev.value}</option>
                                )
                              })}
                            </select>
                          </div>
                          <div className="mb-3">
                            <label className="form-label">Department</label>
                            <select className="form-select" value={offer.department} onChange={(e) => {
                              offer.department = e.target.value
                              setDepartment(e.target.value)
                            }}>
                              <option value="" disabled selected>Select a department</option>
                              <option value="IT">IT</option>
                              <option value="HR">HR</option>
                              <option value="COMMUNICATION">COMMUNICATION</option>
                              <option value="FINANCE">FINANCE</option>
                              <option value="MARKETING">MARKETING</option>
                              <option value="ACCOUNTING">ACCOUNTING</option>
                            </select>
                          </div>
                          <div className="mb-3">
                            <label className="form-label">Recruiter Owner</label>
                            <select className="form-select" defaultValue={offer.recruiterOwner} value={selectedRecruiter} onChange={handleSelectChangeRecruiter}>
                              <option selected disabled >Recruiter name</option>
                              {
                                Array.isArray(recruiterOwner) ? recruiterOwner.map((item, index) => {
                                  return (
                                    <option value={item} >{item}</option>
                                  )
                                }) : <option value="" disabled>Not value</option>
                              }
                            </select>
                          </div>
                          <div className="mb-3">
                            <label className="form-label text-dark">Due Date</label>
                            <input type="date" defaultValue={offer.dueDate} className="form-control" onChange={(e) => setDueDate(e.target.value)} />
                          </div>
                          <div className="mb-3">
                            <label className="form-label text-dark">Basic Salary</label>
                            <input type="text" defaultValue={offer.basicSalary} className="form-control" onChange={(e) => setBasicSalary(e.target.value)} placeholder="Type a salary..." />
                          </div>
                          <div className="mb-3">
                            <label className="text-uppercase text-secondary text-xs text-dark font-weight-bolder text-dark">Note</label>
                            <input type="text" defaultValue={offer.offerNote} className="form-control" onChange={(e) => setOfferNote(e.target.value)} placeholder="Type a note..." />
                          </div>
                        </div>
                      </div>
                      <div className="d-flex justify-content-end">
                        <button type="submit" className="btn btn-warning me-2">Edit</button>

                        <Link to={"/offer"}>
                          <button type="button" className="btn btn-danger">Cancel</button>
                        </Link>
                      </div>
                    </form>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      )}
    </>
  )
}

export default OfferDetail