import React, { useEffect, useState } from 'react'
import { Link, useNavigate, useParams } from 'react-router-dom'
import Select from 'react-select';
import toastr from 'toastr';
import "toastr/build/toastr.min.css"
import LoadingImg from '../../components/LoadingImg';
const InterviewDetail = () => {
  const navigate = useNavigate();
  const { id } = useParams();
  const [interview, setInterview]: any = useState([]);
  const [result, setResult] = useState("");
  const [note, setNote] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const user = localStorage.getItem("user");
  let roles = null;
  if (user) {
    const parseUser = JSON.parse(user);
    roles = parseUser.roles;
  }
  const hanldeShowDetailInterview = async () => {
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
        const res = await fetch(`${apiUrl}/interview-schedule/schedules/${id}`, {
          headers: {
            "Authorization": `Bearer ${token}`,
            // "Content-Type": "application/json"
          }
        });
        const data = await res.json();
        console.log(">>>check data: ", data);
        setInterview(data);
      }

    } catch (error) {
      console.log("Fetch error: ", error);
    } finally {
      setIsLoading(false);
    }
  }
  useEffect(() => {
    hanldeShowDetailInterview();
  }, [])

  const handleChangeResult = async () => {
    const formData = {
      result: result,
      note: note
    };
    console.log(">>>check form: ", formData);

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
        const res = await fetch(`${apiUrl}/interview-schedule/${id}/submitResult`, {
          method: "POST",
          headers: {
            "Authorization": `Bearer ${token}`,
            "Content-Type": "application/json"
          },
          body: JSON.stringify(formData)
        }).then((data) => {
          if (data.status === 200) {
            toastr.success("Result has been submitted successfully!")
            navigate("/interview")
          }
          else if (data.status == 403) {
            data.text().then(text => toastr.error(text))
          }
        })

      }

    } catch (error) {
      console.log("Fetch error: ", error);
    }

  }
  return (
    <>
      {isLoading ? (<LoadingImg />) : <>
        {roles === "INTERVIEWER"
          ? <div className="container-fluid py-4">
            <div className="row">
              <div className="col-12">
                <div className="card my-4">
                  <div className="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
                    <div className="bg-gradient-dark shadow-dark border-radius-lg pt-4 pb-3">
                      <h6 className="text-white text-capitalize ps-3">Interview Details</h6>

                    </div>
                  </div>
                  <div className="card-body px-0 pb-2">
                    <div className="p-3">
                      <div className="row">
                        <div className="col-md-6">
                          <div className="mb-3">
                            <label className="text-uppercase text-secondary text-xs text-dark font-weight-bolder text-dark">Schedule title</label>
                            <p className="form-control-plaintext">{interview.scheduleTitle}</p>
                          </div>
                          <div className="mb-3">
                            <label className="text-uppercase text-secondary text-xs text-dark font-weight-bolder text-dark">Candidate name</label>
                            <p className="form-control-plaintext">{interview.candidateName}</p>
                          </div>
                          <div className="mb-3">
                            <label className="text-uppercase text-secondary text-xs text-dark font-weight-bolder text-dark">Schedule time</label>
                            <p className="form-control-plaintext">{interview.scheduleTime}</p>
                          </div>
                          <div className="mb-3">
                            <label className="text-uppercase text-secondary text-xs text-dark font-weight-bolder text-dark">Note</label>
                            <input type="text" className="form-control" defaultValue={interview.note} onChange={(e) => setNote(e.target.value)} placeholder="Type a note..." />
                          </div>
                          <div className="mb-3">
                            <label className="text-uppercase text-secondary text-xs text-dark font-weight-bolder text-dark">Status</label>
                            <p className="form-control-plaintext">{interview.status}</p>
                          </div>

                        </div>
                        <div className="col-md-6">
                          <div className="mb-3">
                            <label className="text-uppercase text-secondary text-xs text-dark font-weight-bolder text-dark">Job</label>
                            <p className="form-control-plaintext">{interview.jobTitle}</p>
                          </div>
                          <div className="mb-3">
                            <label className="text-uppercase text-secondary text-xs text-dark font-weight-bolder text-dark">Interviewer</label>
                            <p className="form-control-plaintext">{interview.interviewerName}</p>
                          </div>
                          <div className="mb-3">
                            <label className="text-uppercase text-secondary text-xs text-dark font-weight-bolder text-dark">Location</label>
                            <p className="form-control-plaintext">{interview.location}</p>
                          </div>
                          <div className="mb-3">
                            <label className="text-uppercase text-secondary text-xs text-dark font-weight-bolder text-dark">Recruiter Owner</label>
                            <p className="form-control-plaintext">{interview.recruiterName}</p>
                          </div>
                          <div className="mb-3">
                            <label className="text-uppercase text-secondary text-xs text-dark font-weight-bolder text-dark">Meeting ID</label>
                            <p className="form-control-plaintext">{interview.meetingId}</p>
                          </div>
                          <div className="mb-3">
                            <label className="form-label text-dark">Result</label>
                            <select className="form-select" value={interview.result} onChange={(e) => {
                              interview.result = e.target.value
                              setResult(e.target.value)
                            }}>
                              <option value="" selected className='text-secondary'>Select a result</option>
                              <option value="PASSED">PASSED</option>
                              <option value="FAILED">FAILED</option>
                            </select>
                          </div>

                        </div>
                      </div>
                      <div className="d-flex justify-content-end">
                        <button type="button" onClick={handleChangeResult} className="btn btn-warning me-2">Submit result</button>

                        <Link to={"/interview"}>
                          <button type="button" className="btn btn-danger">Cancel</button>
                        </Link>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>


          : <div className="container-fluid py-4">
            <div className="row">
              <div className="col-12">
                <div className="card my-4">
                  <div className="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
                    <div className="bg-gradient-dark shadow-dark border-radius-lg pt-4 pb-3">
                      <h6 className="text-white text-capitalize ps-3">Interview Details</h6>

                    </div>
                  </div>
                  <div className="card-body px-0 pb-2">
                    <div className="p-3">
                      <div className="row">
                        <div className="col-md-6">
                          <div className="mb-3">
                            <label className="text-uppercase text-secondary text-xs text-dark font-weight-bolder text-dark">Schedule title</label>
                            <p className="form-control-plaintext">{interview.scheduleTitle}</p>
                          </div>
                          <div className="mb-3">
                            <label className="text-uppercase text-secondary text-xs text-dark font-weight-bolder text-dark">Candidate name</label>
                            <p className="form-control-plaintext">{interview.candidateName}</p>
                          </div>
                          <div className="mb-3">
                            <label className="text-uppercase text-secondary text-xs text-dark font-weight-bolder text-dark">Schedule time</label>
                            <p className="form-control-plaintext">{interview.scheduleTime}</p>
                          </div>
                          <div className="mb-3">
                            <label className="text-uppercase text-secondary text-xs text-dark font-weight-bolder text-dark">Note</label>
                            <p className="form-control-plaintext">{interview.note === null ? "N/A" : <p className="form-control-plaintext">{interview.note}</p>}</p>
                          </div>
                          <div className="mb-3">
                            <label className="text-uppercase text-secondary text-xs text-dark font-weight-bolder text-dark">Status</label>
                            <p className="form-control-plaintext">{interview.status}</p>
                          </div>

                        </div>
                        <div className="col-md-6">
                          <div className="mb-3">
                            <label className="text-uppercase text-secondary text-xs text-dark font-weight-bolder text-dark">Job</label>
                            <p className="form-control-plaintext">{interview.jobTitle}</p>
                          </div>
                          <div className="mb-3">
                            <label className="text-uppercase text-secondary text-xs text-dark font-weight-bolder text-dark">Interviewer</label>
                            <p className="form-control-plaintext">{interview.interviewerName}</p>
                          </div>
                          <div className="mb-3">
                            <label className="text-uppercase text-secondary text-xs text-dark font-weight-bolder text-dark">Location</label>
                            <p className="form-control-plaintext">{interview.location}</p>
                          </div>
                          <div className="mb-3">
                            <label className="text-uppercase text-secondary text-xs text-dark font-weight-bolder text-dark">Recruiter Owner</label>
                            <p className="form-control-plaintext">{interview.recruiterName}</p>
                          </div>
                          <div className="mb-3">
                            <label className="text-uppercase text-secondary text-xs text-dark font-weight-bolder text-dark">Meeting ID</label>
                            <p className="form-control-plaintext">{interview.meetingId}</p>
                          </div>
                          <div className="mb-3">
                            <label className="text-uppercase text-secondary text-xs text-dark font-weight-bolder text-dark">Result</label>
                            <p className="form-control-plaintext">{interview.result === null ? "N/A" : <p className="form-control-plaintext">{interview.result}</p>}</p>
                          </div>

                        </div>
                      </div>
                      <div className="d-flex justify-content-end">
                        <button type="button" className="btn btn-warning me-2">Edit</button>

                        <Link to={"/interview"}>
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
      </>}

    </>

  )
}

export default InterviewDetail