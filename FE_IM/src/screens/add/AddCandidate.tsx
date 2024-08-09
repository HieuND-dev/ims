import React, { useEffect, useState } from 'react'
import { Link, useNavigate } from 'react-router-dom';
import Select from 'react-select';
import toastr from 'toastr';
import "toastr/build/toastr.min.css"
const optionsSkills = [
    { value: 'JAVA', label: 'JAVA' },
    { value: 'NODEJS', label: 'NODEJS' },
    { value: 'DOT_NET', label: 'DOT_NET' },
    { value: 'C_PLUS_PLUS', label: 'C_PLUS_PLUS' },
    { value: 'BUSINESS_ANALYST', label: 'BUSINESS_ANALYST' },
    { value: 'COMMUNICATION', label: 'COMMUNICATION' },
];
const optionsPosition = [
    { value: 'BACKEND_DEVELOPER', label: 'BACKEND_DEVELOPER' },
    { value: 'BUSINESS_ANALYST', label: 'BUSINESS_ANALYST' },
    { value: 'TESTER', label: 'TESTER' },
    { value: 'HR', label: 'HR' },
    { value: 'PROJECT_MANAGER', label: 'PROJECT_MANAGER' },
    { value: 'NOT_AVAILABLE', label: 'NOT_AVAILABLE' },
];
const optionsCandidateStatus = [
    { value: 'WAITING_FOR_INTERVIEW', label: 'WAITING_FOR_INTERVIEW' },
    { value: 'WAITING_FOR_APPROVAL', label: 'WAITING_FOR_APPROVAL' },
    { value: 'WAITING_FOR_RESPONSE', label: 'WAITING_FOR_RESPONSE' },
    { value: 'OPEN', label: 'OPEN' },
    { value: 'PASSED_INTERVIEW', label: 'PASSED_INTERVIEW' },
    { value: 'APPROVED_OFFER', label: 'APPROVED_OFFER' },
    { value: 'REJECTED_OFFER', label: 'REJECTED_OFFER' },
    { value: 'ACCEPTED_OFFER', label: 'ACCEPTED_OFFER' },
    { value: 'DECLINED_OFFER', label: 'DECLINED_OFFER' },
    { value: 'CANCELLED_OFFER', label: 'CANCELLED_OFFER' },
    { value: 'FAILED_INTERVIEW', label: 'FAILED_INTERVIEW' },
    { value: 'CANCELLED_INTERVIEW', label: 'CANCELLED_INTERVIEW' },
    { value: 'BANNED', label: 'BANNED' },
];
const optionsHighestLevel = [
    { value: 'HIGH_SCHOOL', label: 'HIGH_SCHOOL' },
    { value: 'BACHELOR_DEGREE', label: 'BACHELOR_DEGREE' },
    { value: 'MASTER_DEGREE', label: 'MASTER_DEGREE' },
];
const AddCandidate = () => {
    const navigate = useNavigate();
    const [fullName, setFullName] = useState("");
    const [dateOfBirth, setDateOfBirth] = useState("");
    const [phoneNo, setPhoneNo] = useState("");
    const [address, setAddress] = useState("");
    const [email, setEmail] = useState("");
    const [gender, setGender] = useState("");
    const [fileCV, setFileCV] = useState("");
    const [position, setPosition] = useState('');
    const [skills, setSkills] = useState([]);
    const [note, setNote] = useState("");
    const [candidateStatus, setCandidateStatus] = useState("OPEN");
    const [yearOfExperience, setYearOfExperience] = useState("");
    const [highestLevel, setHighestLevel] = useState('');
    const [ownerHR, setOwnerHR] = useState([]);
    const [selectedRecruiter, setSelectedRecruiter] = useState('');
    console.log("selectedRecruiter: ", selectedRecruiter);


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
                console.log(">>>check data owner: ", data);
                setOwnerHR(data);
            }
        } catch (error) {
            console.log("Fetch error: ", error);

        }
    }


    useEffect(() => {
        handleShowRecruiter();
    }, [])
    const handleSelectChange = (e: any) => {
        setSelectedRecruiter(e.target.value);
    };

    const handleSubmit = async (e: any) => {
        e.preventDefault();
        try {
            const formData = new FormData();

            formData.append('fullName', fullName);
            formData.append('dateOfBirth', dateOfBirth);
            formData.append('phoneNo', phoneNo);
            formData.append('address', address);
            formData.append('email', email);
            formData.append('gender', gender);
            formData.append('fileCV', fileCV);
            formData.append('position', position);
            skills.forEach((skill: any) => formData.append('skills', skill.value));
            formData.append('note', note);
            formData.append('candidateStatus', candidateStatus);
            formData.append('yearOfExperience', yearOfExperience);
            formData.append('highestLevel', highestLevel);
            formData.append('ownerHR', selectedRecruiter);

            const apiUrl = "http://localhost:8080";
            const user = localStorage.getItem("user");
            let token = null;
            if (user) {
                const parseUser = JSON.parse(user);
                token = parseUser.token
                console.log(token);

            }
            if (token) {
                const res = await fetch(`${apiUrl}/api/candidates`, {
                    method: "POST",
                    headers: {
                        "Authorization": `Bearer ${token}`,

                    },
                    body: formData
                }).then((data) => {
                    if (data.status === 201) {
                        toastr.success("Candidate has been created successfully!");
                        navigate("/candidate")
                    } else {
                        toastr.error("Failed to create Candidate!")
                    }

                });
                // if (res.ok) {
                //     toastr.success("Candidate has been created successfully!");
                //     navigate("/candidate")
                // } else {
                //     toastr.error("Failed to create Candidate!")
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
                                <h6 className="text-white text-capitalize ps-3">Add Candidate</h6>
                            </div>
                        </div>
                        <div className="card-body px-0 pb-2">
                            <form onSubmit={handleSubmit} className="p-3">
                                {/* I. Personal Information */}
                                <div className="row">
                                    <h5 className=' text-dark'> I. Personal Information</h5>
                                    <div className="col-md-6">

                                        <div className="mb-3">
                                            <label className="form-label text-dark">Full name</label>
                                            <input type="text" className="form-control" onChange={(e) => setFullName(e.target.value)} placeholder="Type a name..." />
                                        </div>
                                        <div className="mb-3">
                                            <label className="form-label text-dark">D.O.B</label>
                                            <input type="date" className="form-control" onChange={(e) => setDateOfBirth(e.target.value)} placeholder="Type a name..." />
                                        </div>
                                        <div className="mb-3">
                                            <label className="form-label text-dark">Phone number</label>
                                            <input type="text" className="form-control" onChange={(e) => setPhoneNo(e.target.value)} placeholder="Type a number..." />
                                        </div>
                                    </div>
                                    <div className="col-md-6">

                                        <div className="mb-3">
                                            <label className="form-label text-dark">Email</label>
                                            <input type="text" className="form-control" onChange={(e) => setEmail(e.target.value)} placeholder="Type an email..." />
                                        </div>
                                        <div className="mb-3">
                                            <label className="form-label text-dark">Address</label>

                                            <input type="text" className="form-control" onChange={(e) => setAddress(e.target.value)} placeholder="Type an address..." />
                                        </div>
                                        <div className="mb-3">
                                            <label className="form-label text-dark">Gender</label>
                                            <select className="form-select" onChange={(e) => setGender(e.target.value)}>
                                                <option value="" selected className='text-secondary'>Select a gender</option>
                                                <option value="MALE">MALE</option>
                                                <option value="FEMALE">FEMALE</option>
                                                <option value="OTHER">OTHER</option>
                                            </select>
                                        </div>



                                    </div>
                                </div>
                                {/* II. Professional Information */}
                                <div className="row">
                                    <h5 className=' text-dark'> II. Professional Information</h5>
                                    <div className="col-md-6">

                                        <div className="mb-3">
                                            <label className="form-label text-dark">CV attachment</label>
                                            <input type="file" id="fileCV" className="form-control" onChange={(e: any) => setFileCV(e.target.files[0])} placeholder="Type a name..." />
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
                                            <label className="form-label text-dark">Skills</label>
                                            <Select
                                                isMulti
                                                options={optionsSkills}
                                                value={skills}
                                                onChange={(e: any) => setSkills(e)}
                                                placeholder="Choose skills"
                                                closeMenuOnSelect={false}
                                            />
                                        </div>
                                        <div className="mb-3">
                                            <label className="form-label text-dark">Note</label>
                                            <input type="text" className="form-control" onChange={(e) => setNote(e.target.value)} placeholder="N/A" />
                                        </div>
                                    </div>
                                    <div className="col-md-6">

                                        <div className="mb-3">
                                            <label className="form-label">Recruiter</label>
                                            <select className="form-select" value={selectedRecruiter} onChange={handleSelectChange}>
                                                <option selected >Recruiter name</option>
                                                {
                                                    Array.isArray(ownerHR) ? ownerHR.map((item, index) => {
                                                        return (
                                                            <option value={item} >{item}</option>
                                                        )
                                                    }) : <option value="" disabled>Not value</option>
                                                }
                                            </select>
                                        </div>

                                        {/* <div className="mb-3">
                                            <label className="form-label text-dark">Status</label>
                                            <select className="form-select" onChange={(e) => setCandidateStatus(e.target.value)}>
                                                <option value="" selected className='text-secondary'>Select a status</option>
                                                {optionsCandidateStatus.map((can, index) => {
                                                    return (
                                                        <option>{can.value}</option>
                                                    )
                                                })}
                                            </select>
                                        </div> */}
                                        <div className="mb-3">
                                            <label className="form-label text-dark">Highest Level</label>
                                            <select className="form-select" onChange={(e) => setHighestLevel(e.target.value)}>
                                                <option value="" selected className='text-secondary'>Select a highestLevel</option>
                                                {optionsHighestLevel.map((high, index) => {
                                                    return (
                                                        <option>{high.value}</option>
                                                    )
                                                })}
                                            </select>
                                        </div>
                                        <div className="mb-3">
                                            <label className="form-label text-dark">Year of Experience</label>
                                            <input type="number" className="form-control" onChange={(e) => setYearOfExperience(e.target.value)} placeholder="Type a number." />
                                        </div>
                                    </div>
                                </div>
                                <div className="d-flex justify-content-end">
                                    <button type="submit" className="btn btn-success me-2">Submit</button>

                                    <Link to={"/candidate"}>
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

export default AddCandidate