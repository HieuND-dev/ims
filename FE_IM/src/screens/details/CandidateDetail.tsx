import React, { useEffect, useState } from 'react'
import { Link, useNavigate, useParams } from 'react-router-dom'
import Swal from 'sweetalert2'
import toastr from 'toastr';
import "toastr/build/toastr.min.css"
import LoadingImg from '../../components/LoadingImg';
const CandidateDetail = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [candidate, setCandidate]: any = useState([]);
  const [isLoading, setIsLoading] = useState(false);

  const handleDownFile = async () => {
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
        const res = await fetch(`${apiUrl}/candidates/download/${candidate.cvName}`, {
          method: "GET",
          headers: {
            "Authorization": `Bearer ${token}`,
            // "Content-Type": "application/json"
          }
        }).then(async (data) => {
          const downloadUrl = data.url;
          console.log(">>>check downloadUrl: ", downloadUrl);
          const link = document.createElement("a");
          link.href = downloadUrl;
          link.click();
        })

      }

    } catch (error) {
      console.log("Fetch error: ", error);

    }
  }
  const hanldeShowDetailCandidate = async () => {
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
        const res = await fetch(`${apiUrl}/candidates/${id}`, {
          headers: {
            "Authorization": `Bearer ${token}`,
            // "Content-Type": "application/json"
          }
        });
        const data = await res.json();
        console.log(">>>check data: ", data);

        setCandidate(data);


      }

    } catch (error) {
      console.log("Fetch error: ", error);

    } finally {
      setIsLoading(false);
    }
  }
  const handleBanCandidate = async (e: any) => {
    e.preventDefault();

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
        const apiUrl = "http://localhost:8080/api";
        const user = localStorage.getItem("user");
        let token = null;
        if (user) {
          const parseUser = JSON.parse(user);
          token = parseUser.token
          console.log(token);

        }
        if (token) {
          const res = await fetch(`${apiUrl}/candidates/ban/${id}`, {
            method: "PATCH",
            headers: {
              "Authorization": `Bearer ${token}`,
              // "Content-Type": "application/json"
            }
          });
          if (res.ok) {
            toastr.success("Bạn đã cấm ứng viên này thành công!");
            navigate("/candidate");
          }

        }
      }


    } catch (error) {
      console.log("Fetch error: ", error);

    }

  }
  useEffect(() => {
    hanldeShowDetailCandidate();
  }, [])

  return (
    <>
      {isLoading ? (<LoadingImg />) : <div className="container-fluid py-4">
        <div className="row">
          <div className="col-12">
            <div className="card my-4">
              <div className="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
                <div className="bg-gradient-dark shadow-dark border-radius-lg pt-4 pb-3">
                  <h6 className="text-white text-capitalize ps-3">Candidate Details</h6>
                </div>
              </div>
              <div className="card-body px-0 pb-2">
                <div className="p-3">
                  {/* I. Personal Information */}
                  <div className="row">
                    <div className="d-flex justify-content-between align-items-center">
                      <h6 className="text-dark ">I. Personal Information</h6>
                      <span className="text-dark" style={{ fontStyle: 'italic', fontSize: '0.8rem' }}>{candidate.timeLabel}</span>
                    </div>
                    <div className="col-md-6">
                      <div className="mb-3">
                        <label className="form-label text-dark">Full name</label>
                        <p className="form-control-plaintext">{candidate.fullName}</p>
                      </div>
                      <div className="mb-3">
                        <label className="form-label text-dark">D.O.B</label>
                        <p className="form-control-plaintext">{candidate.dateOfBirth}</p>
                      </div>
                      <div className="mb-3">
                        <label className="form-label text-dark">Phone number</label>
                        <p className="form-control-plaintext">{candidate.phoneNo}</p>
                      </div>
                    </div>
                    <div className="col-md-6">
                      <div className="position-absolute top-4 end-0 p-3">
                        {candidate.candidateStatus === "BANNED" ? "" : <button className="btn btn-danger" onClick={handleBanCandidate}>Ban Candidate</button>}

                      </div>
                      <div className="mb-3">
                        <label className="form-label text-dark">Email</label>
                        <p className="form-control-plaintext">{candidate.email}</p>
                      </div>
                      <div className="mb-3">
                        <label className="form-label text-dark">Address</label>
                        <p className="form-control-plaintext">{candidate.address}</p>
                      </div>
                      <div className="mb-3">
                        <label className="form-label text-dark">Gender</label>
                        <p className="form-control-plaintext">{candidate.gender}</p>
                      </div>
                    </div>
                  </div>
                  {/* II. Professional Information */}
                  <div className="row">
                    <h5 className='text-dark'> II. Professional Information</h5>
                    <div className="col-md-6">
                      <div className="mb-3">
                        <label className="form-label text-dark">CV attachment</label>
                        {/* Assuming you want to show the filename or link */}
                        {candidate.cvName ? <p className="form-control-plaintext">{candidate.cvName}   <a onClick={handleDownFile}><i className="material-icons opacity-10" >download</i></a> </p> : "No file attached"}



                      </div>
                      <div className="mb-3">
                        <label className="form-label text-dark">Position</label>
                        <p className="form-control-plaintext">{candidate.position}</p>
                      </div>
                      <div className="mb-3">
                        <label className="form-label text-dark">Skills</label>
                        <p className="form-control-plaintext">
                          {Array.isArray(candidate.skills) ? (candidate.skills.join(", ")) : (candidate.skills)}
                        </p>
                      </div>
                      <div className="mb-3">
                        <label className="form-label text-dark">Note</label>
                        <p className="form-control-plaintext">{candidate.note}</p>
                      </div>
                    </div>
                    <div className="col-md-6">
                      <div className="mb-3">
                        <label className="form-label">Recruiter</label>
                        <p className="form-control-plaintext">{candidate.ownerHR}</p>
                      </div>
                      <div className="mb-3">
                        <label className="form-label text-dark">Status</label>
                        <p className="form-control-plaintext">{candidate.candidateStatus}</p>
                      </div>
                      <div className="mb-3">
                        <label className="form-label text-dark">Highest Level</label>
                        <p className="form-control-plaintext">{candidate.highestLevel}</p>
                      </div>
                      <div className="mb-3">
                        <label className="form-label text-dark">Year of Experience</label>
                        <p className="form-control-plaintext">{candidate.yearOfExperience}</p>
                      </div>
                    </div>
                  </div>
                  <div className="d-flex justify-content-end">
                    <Link to={"/candidate"}>
                      <button type="button" className="btn btn-danger">Cancel</button>
                    </Link>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>}
    </>

  )
}

export default CandidateDetail