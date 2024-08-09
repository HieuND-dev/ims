import React, { useEffect, useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import toastr from 'toastr';
import "toastr/build/toastr.min.css"
import Select from 'react-select';

const AddInterview = () => {
  const navigate = useNavigate();
  const [scheduleTitle, setScheduleTitle] = useState("");
  const [scheduleDate, setScheduleDate] = useState("");
  const [scheduleFrom, setScheduleFrom] = useState("");
  const [scheduleTo, setScheduleTo] = useState("");
  const [location, setLocation] = useState("");
  const [meetingId, setMeetingId] = useState("");
  const [note, setNote] = useState("");

  const [candidateEmail, setCandidateEmail] = useState([]);
  const [selectedEmail, setSelectedEmail] = useState("");

  const [jobTitle, setJobTitle] = useState([]);
  const [selectedJob, setSelectedJob] = useState("");


  const [interviewer, setInterviewer] = useState([]);
  const [selectedInterviewer, setSelectedInterviewer] = useState('');

  const [recruiterOwner, setRecruiterOwner] = useState([]);
  const [selectedRecruiter, setSelectedRecruiter] = useState('');

  const handleShowJob = async () => {

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
        const res = await fetch(`${apiUrl}/api/jobs?status=OPEN`, {
          method: "GET",
          headers: {
            "Authorization": `Bearer ${token}`,
          },

        });
        const data = await res.json();
        console.log(">>>check data job: ", data);
        setJobTitle(data);
      }
    } catch (error) {
      console.log("Fetch error: ", error);

    }
  }
  const handleShowInterviewer = async () => {

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
        const res = await fetch(`${apiUrl}/api/users?role=INTERVIEWER`, {
          method: "GET",
          headers: {
            "Authorization": `Bearer ${token}`,
          },

        });
        const data = await res.json();
        console.log(">>>check data inerviewer: ", data);
        setInterviewer(data);
      }
    } catch (error) {
      console.log("Fetch error: ", error);

    }
  }
  const handleShowEmail = async () => {
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
        const res = await fetch(`${apiUrl}/api/candidates?status=OPEN`, {
          method: "GET",
          headers: {
            "Authorization": `Bearer ${token}`,
          },

        });
        const data = await res.json();
        console.log(">>>check data email + name: ", data);
        setCandidateEmail(data);
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
  const handleSubmit = async (e: any) => {
    e.preventDefault();

    const formData = {
      scheduleTitle: scheduleTitle,
      scheduleDate: scheduleDate,
      scheduleFrom: scheduleFrom,
      scheduleTo: scheduleTo,
      location: location,
      meetingId: meetingId,
      note: note,
      candidateEmail: selectedEmail,
      jobTitle: selectedJob,
      interviewerName: selectedInterviewer,
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
        const res = await fetch(`${apiUrl}/api/interview-schedule/create`, {
          method: "POST",
          headers: {
            "Authorization": `Bearer ${token}`,
            "Content-Type": "application/json"
          },
          body: JSON.stringify(formData)
        }).then((data) => {
          if (data.status === 201) {
            toastr.success("Interview schedule created successfully !")
            navigate("/interview")
          } else {
            toastr.error("Failed to create Interview Schedule")
          }
        });
        // if (res.ok) {
        //   toastr.success("Thêm thành công!");
        //   navigate("/interview")
        // } else {
        //   toastr.error("Thêm thất bại!")
        // }
      }

    } catch (error) {
      console.log("Fetch error: ", error);
    }


  }
  useEffect(() => {
    handleShowRecruiter();
    handleShowEmail();
    handleShowInterviewer();
    handleShowJob();
  }, [])
  const handleSelectChangeRecruiter = (e: any) => {
    setSelectedRecruiter(e.target.value);
  };
  const handleSelectChangeInterviewer = (e: any) => {
    setSelectedInterviewer(e.target.value);
  };
  const handleSelectChangeEmail = (e: any) => {
    setSelectedEmail(e.target.value);
  };
  const handleSelectChangeJob = (e: any) => {
    setSelectedJob(e.target.value);
  };
  return (
    <div className="container-fluid py-4">
      <div className="row">
        <div className="col-12">
          <div className="card my-4">
            <div className="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
              <div className="bg-gradient-dark shadow-dark border-radius-lg pt-4 pb-3">
                <h6 className="text-white text-capitalize ps-3">Add Interview Schedule</h6>
              </div>
            </div>
            <div className="card-body px-0 pb-2">
              <form onSubmit={handleSubmit} className="p-3">
                <div className="row">
                  <div className="col-md-6">
                    <div className="mb-3">
                      <label className="form-label text-dark">Schedule title</label>
                      <input className="form-control" onChange={(e) => setScheduleTitle(e.target.value)} placeholder='Type a title' />
                    </div>
                    <div className="mb-3">
                      <label className="form-label">Candidate name</label>
                      <select className="form-select" value={selectedEmail} onChange={handleSelectChangeEmail}>
                        <option selected >Candidate name</option>
                        {Array.isArray(candidateEmail) && candidateEmail.length > 0 ? (
                          candidateEmail.map((candidate: any) => (
                            <option value={candidate.email}>
                              {candidate.name} ({candidate.email})
                            </option>
                          ))
                        ) : (
                          <option value="" disabled>
                            Not value
                          </option>
                        )}
                      </select>
                    </div>


                    <div className="mb-3">
                      <label className="form-label text-dark">Schedule Time</label>
                      <div className="form-group">

                        <input
                          type="date"
                          className="form-control"
                          onChange={(e) => setScheduleDate(e.target.value)}
                        />
                      </div>
                      <div className="row mb-3">
                        <div className="col">
                          <div className="form-group">
                            <label htmlFor="contractFrom" className="form-label">From</label>
                            <input
                              type="time"
                              className="form-control"
                              id="contractFrom"
                              onChange={(e) => setScheduleFrom(e.target.value)}
                            />
                          </div>
                        </div>
                        <div className="col">
                          <div className="form-group">
                            <label htmlFor="contractTo" className="form-label">To</label>
                            <input
                              type="time"
                              className="form-control"
                              id="contractTo"
                              onChange={(e) => setScheduleTo(e.target.value)}
                            />
                          </div>
                        </div>
                      </div>
                    </div>
                    <div className="mb-3">
                      <label className="form-label text-dark">Note</label>
                      <textarea className="form-control" onChange={(e) => setNote(e.target.value)} placeholder='Type a note' />
                    </div>

                  </div>
                  <div className="col-md-6">
                    <div className="mb-3">
                      <label className="form-label">Job</label>
                      <select className="form-select" value={selectedJob} onChange={handleSelectChangeJob}>
                        <option selected >Select a job</option>
                        {
                          Array.isArray(jobTitle) ? jobTitle.map((item, index) => {
                            return (
                              <option value={item} >{item}</option>
                            )
                          }) : <option value="" disabled>Not value</option>
                        }

                      </select>

                    </div>
                    <div className="mb-3">
                      <label className="form-label">Interviewer</label>
                      <select className="form-select" value={selectedInterviewer} onChange={handleSelectChangeInterviewer}>
                        <option selected >Interviewer name</option>
                        {
                          Array.isArray(interviewer) ? interviewer.map((item, index) => {
                            return (
                              <option value={item} >{item}</option>
                            )
                          }) : <option value="" disabled>Not value</option>
                        }
                      </select>
                    </div>
                    <div className="mb-3">
                      <label className="form-label text-dark">Location</label>
                      <input className="form-control" onChange={(e) => setLocation(e.target.value)} placeholder='Type a location' />
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
                      <label className="form-label text-dark">Meeting ID</label>
                      <input type="text" className="form-control" onChange={(e) => setMeetingId(e.target.value)} placeholder="Type a meeting" />
                    </div>

                  </div>
                </div>
                <div className="d-flex justify-content-end">
                  <button type="submit" className="btn btn-success me-2">Submit</button>
                  <Link to={"/interview"}>
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

export default AddInterview