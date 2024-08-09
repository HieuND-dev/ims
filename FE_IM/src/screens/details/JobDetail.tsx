import React, { useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import Select from 'react-select';
import LoadingImg from '../../components/LoadingImg';
const JobDetail = () => {
  const { id } = useParams();
  const [job, setJob]: any = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const hanldeShowDetailJob = async () => {
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
        const res = await fetch(`${apiUrl}/jobs/${id}`, {
          headers: {
            "Authorization": `Bearer ${token}`,
            // "Content-Type": "application/json"
          }
        });
        const data = await res.json();
        console.log(">>>check data: ", data);

        setJob(data);


      }

    } catch (error) {
      console.log("Fetch error: ", error);

    } finally {
      setIsLoading(false);
    }
  }
  useEffect(() => {
    hanldeShowDetailJob();
  }, [])
  return (
    <>
      {isLoading ? (<LoadingImg />) :
        <div className="container-fluid py-4">
          <div className="row">
            <div className="col-12">
              <div className="card my-4">
                <div className="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
                  <div className="bg-gradient-dark shadow-dark border-radius-lg pt-4 pb-3">
                    <h6 className="text-white text-capitalize ps-3">Job Details</h6>

                  </div>
                </div>
                <div className="card-body px-0 pb-2">
                  <div className="p-3">
                    <div className="row">
                      <div className="col-md-6">
                        <div className="mb-3">
                          <label className="text-uppercase text-secondary text-xs text-dark font-weight-bolder text-dark">Job title</label>
                          <p className="form-control-plaintext">{job.jobTitle}</p>
                        </div>
                        <div className="mb-3">
                          <label className="text-uppercase text-secondary text-xs text-dark font-weight-bolder text-dark">Start Date</label>
                          <p className="form-control-plaintext">{job.startDate}</p>
                        </div>
                        <div className="mb-3">
                          <label className="text-uppercase text-secondary text-xs text-dark font-weight-bolder text-dark">Salary Range</label>
                          <div className="row">
                            <div className="col">
                            </div>
                            <div >
                              <span>From: {job.salaryFrom}</span>&nbsp;&nbsp;&nbsp;
                              <span>To: {job.salaryTo}</span>
                            </div>
                          </div>


                        </div>
                        <div className="mb-3">
                          <label className="text-uppercase text-secondary text-xs text-dark font-weight-bolder text-dark">Working address</label>
                          <p className="form-control-plaintext">{job.workingAddress}</p>
                        </div>
                        <div className="mb-3">
                          <label className="text-uppercase text-secondary text-xs text-dark font-weight-bolder text-dark">Status</label>
                          <p className="form-control-plaintext">{job.jobStatus}</p>
                        </div>
                      </div>
                      <div className="col-md-6">
                        <div className="mb-3">
                          <label className="text-uppercase text-secondary text-xs text-dark font-weight-bolder text-dark">Skills</label>
                          <p className="form-control-plaintext">
                            {Array.isArray(job.skills) ? job.skills.join(', ') : job.skills}
                          </p>
                        </div>
                        <div className="mb-3">
                          <label className="text-uppercase text-secondary text-xs text-dark font-weight-bolder text-dark">End Date</label>
                          <p className="form-control-plaintext">{job.endDate}</p>
                        </div>
                        <div className="mb-3">
                          <label className="text-uppercase text-secondary text-xs text-dark font-weight-bolder text-dark">Benefits</label>
                          <p className="form-control-plaintext">
                            {Array.isArray(job.benefits) ? job.benefits.join(', ') : job.benefits}
                          </p>
                        </div>
                        <div className="mb-3">
                          <label className="text-uppercase text-secondary text-xs text-dark font-weight-bolder text-dark">Level</label>
                          <p className="form-control-plaintext">
                            {Array.isArray(job.levels) ? job.levels.join(', ') : job.levels}
                          </p>
                        </div>
                        <div className="mb-3">
                          <label className="text-uppercase text-secondary text-xs text-dark font-weight-bolder text-dark">Description</label>
                          <p className="form-control-plaintext">{job.description}</p>
                        </div>
                      </div>
                    </div>
                    <div className="d-flex justify-content-end">
                      <button type="button" className="btn btn-warning me-2">Edit</button>

                      <Link to={"/job"}>
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
    </>
  )
}

export default JobDetail