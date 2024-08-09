import React, { useEffect, useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import toastr from 'toastr';
import "toastr/build/toastr.min.css"
import Select from 'react-select';
const optionsLevels = [
  { value: 'FRESHER', label: 'FRESHER' },
  { value: 'JUNIOR', label: 'JUNIOR' },
  { value: 'SENIOR', label: 'SENIOR' },
  { value: 'LEADER', label: 'LEADER' },
  { value: 'MANAGER', label: 'MANAGER' },
  { value: 'VICE_HEAD', label: 'VICE_HEAD' },
];
const optionsPosition = [
  { value: 'BACKEND_DEVELOPER', label: 'BACKEND_DEVELOPER' },
  { value: 'BUSINESS_ANALYST', label: 'BUSINESS_ANALYST' },
  { value: 'TESTER', label: 'TESTER' },
  { value: 'HR', label: 'HR' },
  { value: 'PROJECT_MANAGER', label: 'PROJECT_MANAGER' },
  { value: 'NOT_AVAILABLE', label: 'NOT_AVAILABLE' },
];
const AddOffer = () => {
  const navigate = useNavigate();

  const [interviewTitle, setInterviewTitle] = useState([]);
  const [selectInterviewTitle, setSelectInterviewTitle] = useState("")
  const [position, setPosition] = useState("");
  const [status, setStatus] = useState("WAITING_FOR_APPROVAL");
  const [contractType, setContractType] = useState("");
  const [level, setLevel] = useState("");
  const [department, setDepartment] = useState("");
  const [contractFrom, setContractFrom] = useState("");
  const [contractTo, setContractTo] = useState("");
  const [dueDate, setDueDate] = useState("");
  const [basicSalary, setBasicSalary] = useState("");
  const [offerNote, setOfferNote] = useState("");
  const [approver, setApprover] = useState([]);
  const [selectedManager, setSelectedManager] = useState('');
  const [recruiterOwner, setRecruiterOwner] = useState([]);
  const [selectedRecruiter, setSelectedRecruiter] = useState('');
  const handleShowInterviewTitle = async () => {
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
        const res = await fetch(`${apiUrl}/api/interview-schedule/validInterviews`, {
          method: "GET",
          headers: {
            "Authorization": `Bearer ${token}`,
          },

        });
        const data = await res.json();
        console.log(">>>check data interview title: ", data);
        setInterviewTitle(data);
      }
    } catch (error) {
      console.log("Fetch error: ", error);

    }
  }
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
        console.log(">>>check data recruiterOwner: ", data);
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
        console.log(">>>check data approver: ", data);
        setApprover(data);
      }
    } catch (error) {
      console.log("Fetch error: ", error);

    }
  }
  useEffect(() => {
    handleShowRecruiter();
    handleShowManager();
    handleShowInterviewTitle();
  }, [])
  const handleSelectChangeRecruiter = (e: any) => {
    setSelectedRecruiter(e.target.value);
  };
  const handleSelectChangeManager = (e: any) => {
    setSelectedManager(e.target.value);
  };
  const handleSelectInterviewTitle = (e: any) => {
    setSelectInterviewTitle(e.target.value);
  };

  const handleSubmit = async (e: any) => {
    e.preventDefault();

    const formData = {
      interviewTitle: selectInterviewTitle,
      position: position,
      status: status,
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
    }
    console.log(">>>check formData Offer: ", formData);


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
        const res = await fetch(`${apiUrl}/api/offers`, {
          method: "POST",
          headers: {
            "Authorization": `Bearer ${token}`,
            "Content-Type": "application/json"
          },
          body: JSON.stringify(formData)
        }).then((data) => {
          if (data.status === 201) {
            toastr.success("Create offer successfully!")
            navigate("/offer")
          } else {
            toastr.error("Failed to create offer!")
          }
        });
        // if (res.ok) {
        //   toastr.success("Thêm thành công!");
        //   navigate("/offer")
        // } else {
        //   toastr.error("Thêm thất bại!")
        // }
      }

    } catch (error) {
      console.log("Fetch error: ", error);
    }


  }
  return (
    <div className="container-fluid py-4">
      <div className="row">
        <div className="col-12">
          <div className="card my-4">
            <div className="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
              <div className="bg-gradient-dark shadow-dark border-radius-lg pt-4 pb-3">
                <h6 className="text-white text-capitalize ps-3">Add Offer</h6>
              </div>
            </div>
            <div className="card-body px-0 pb-2">
              <form onSubmit={handleSubmit} className="p-3">
                <div className="row">
                  <div className="col-md-6">
                    <div className="mb-3">
                      <label className="form-label">Interview info</label>
                      <select className="form-select" value={selectInterviewTitle} onChange={handleSelectInterviewTitle}>
                        <option selected >Interview Title</option>
                        {
                          Array.isArray(interviewTitle) ? interviewTitle.map((item, index) => {
                            return (
                              <option value={item} >{item}</option>
                            )
                          }) : <option value="" disabled>Not value</option>
                        }
                      </select>
                    </div>
                    <div className="mb-3">
                      <label className="form-label text-dark">Position</label>
                      <select className="form-select" onChange={(e) => setPosition(e.target.value)}>
                        <option value="" selected className='text-secondary'>Select a position</option>
                        {optionsPosition.map((pos, index) => {
                          return (
                            <option>{pos.value}</option>
                          )
                        })}
                      </select>
                    </div>
                    <div className="mb-3">
                      <label className="form-label">Approver</label>
                      <select className="form-select" value={selectedManager} onChange={handleSelectChangeManager}>
                        <option selected >Manager name</option>
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
                      <label className="form-label text-dark">Contract Period</label>
                      <div className="row mb-3">
                        <div className="col">
                          <div className="form-group">
                            <label htmlFor="contractFrom" className="form-label">From</label>
                            <input
                              type="date"
                              className="form-control"
                              id="contractFrom"
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
                              onChange={(e) => setContractTo(e.target.value)}
                            />
                          </div>
                        </div>
                      </div>
                    </div>
                    <div className="mb-3">
                      <label className="form-label text-dark">Note</label>
                      <textarea className="form-control" onChange={(e) => setOfferNote(e.target.value)} placeholder='Type a note' />
                    </div>

                  </div>
                  <div className="col-md-6">
                    <div className="mb-3">
                      <label className="form-label text-dark">Contract Type</label>
                      <select className="form-select" onChange={(e) => setContractType(e.target.value)}>
                        <option value="" selected className='text-secondary'>Select a ContractType</option>
                        <option value="TRIAL">TRIAL</option>
                        <option value="TRAINEE">TRAINEE</option>
                        <option value="ONE_YEAR">ONE_YEAR</option>
                        <option value="THREE_YEARS">THREE_YEARS</option>
                        <option value="UNLIMITED">UNLIMITED</option>
                      </select>
                    </div>
                    <div className="mb-3">
                      <label className="form-label text-dark">levels</label>
                      <select className="form-select" onChange={(e) => setLevel(e.target.value)}>
                        <option value="" selected className='text-secondary'>Select a level</option>
                        {optionsLevels.map((lev, index) => {
                          return (
                            <option>{lev.value}</option>
                          )
                        })}
                      </select>
                    </div>

                    <div className="mb-3">
                      <label className="form-label">Department</label>
                      <select className="form-select" onChange={(e) => setDepartment(e.target.value)}>
                        <option value="" selected>Select a department</option>
                        <option value="IT">IT</option>
                        <option value="HR">HR</option>
                        <option value="COMMUNICATION">COMMUNICATION</option>
                        <option value="FINANCE">FINANCE</option>
                        <option value="MARKETING">MARKETING</option>
                        <option value="ACCOUNTING">ACCOUNTING</option>
                      </select>
                    </div>
                    <div className="mb-3">
                      <label className="form-label text-dark">Due Date</label>
                      <input type="date" className="form-control" onChange={(e) => setDueDate(e.target.value)} />
                    </div>
                    <div className="mb-3">
                      <label className="form-label">Recruiter Owner</label>
                      <select className="form-select" value={selectedRecruiter} onChange={handleSelectChangeRecruiter}>
                        <option selected >Recruiter name</option>
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
                      <label className="form-label text-dark">Basic Salary</label>
                      <input type="text" className="form-control" onChange={(e) => setBasicSalary(e.target.value)} placeholder="Type a salary..." />
                    </div>

                  </div>
                </div>
                <div className="d-flex justify-content-end">
                  <button type="submit" className="btn btn-success me-2">Submit</button>

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
  )
}

export default AddOffer