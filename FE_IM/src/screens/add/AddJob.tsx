import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import toastr from 'toastr';
import "toastr/build/toastr.min.css"
import Select from 'react-select';

const optionsSkills = [
    { value: 'JAVA', label: 'JAVA' },
    { value: 'NODEJS', label: 'NODEJS' },
    { value: 'DOT_NET', label: 'DOT_NET' },
    { value: 'C_PLUS_PLUS', label: 'C_PLUS_PLUS' },
    { value: 'BUSINESS_ANALYST', label: 'BUSINESS_ANALYST' },
    { value: 'COMMUNICATION', label: 'COMMUNICATION' },
];

const optionsBenefits = [
    { value: 'LUNCH', label: 'LUNCH' },
    { value: 'TWENTY_FIVE_DAY_LEAVE', label: 'TWENTY_FIVE_DAY_LEAVE' },
    { value: 'HEALTHCARE_INSURANCE', label: 'HEALTHCARE_INSURANCE' },
    { value: 'HYBRID_WORKING', label: 'HYBRID_WORKING' },
    { value: 'TRAVEL', label: 'TRAVEL' },
];
const optionsLevels = [
    { value: 'FRESHER', label: 'FRESHER' },
    { value: 'JUNIOR', label: 'JUNIOR' },
    { value: 'SENIOR', label: 'SENIOR' },
    { value: 'LEADER', label: 'LEADER' },
    { value: 'MANAGER', label: 'MANAGER' },
    { value: 'VICE_HEAD', label: 'VICE_HEAD' },
];
const AddJob = () => {
    const navigate = useNavigate();
    const [title, setTitle] = useState("");
    const [startDate, setStartDate] = useState("");
    const [endDate, setEndDate] = useState("");
    const [skill, setSkill] = useState([]);
    const [salaryFrom, setSalaryFrom] = useState("");
    const [salaryTo, setSalaryTo] = useState("");
    const [benefit, setBenefit] = useState([]);
    const [workAddress, setWorkAddress] = useState("");
    const [level, setLevel] = useState([]);
    const [description, setDescription] = useState("");


    const handleSubmit = async (e: any) => {
        e.preventDefault();

        const formData = {
            job_title: title,
            start_date: startDate,
            end_date: endDate,
            working_address: workAddress,
            skills: skill.map((s: any) => s.value),
            salary_from: salaryFrom,
            salary_to: salaryTo,
            benefits: benefit.map((b: any) => b.value),
            levels: level.map((l: any) => l.value),
            description: description,
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
                const res = await fetch(`${apiUrl}/api/jobs/add`, {
                    method: "POST",
                    headers: {
                        "Authorization": `Bearer ${token}`,
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(formData)
                });
                if (res.ok) {
                    toastr.success("Job has been created successfully!");
                    navigate("/job")
                } else {
                    toastr.error("Job has not been created!")
                }
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
                                <h6 className="text-white text-capitalize ps-3">Add Job</h6>
                            </div>
                        </div>
                        <div className="card-body px-0 pb-2">
                            <form onSubmit={handleSubmit} className="p-3">
                                <div className="row">
                                    <div className="col-md-6">

                                        <div className="mb-3">
                                            <label className="form-label text-dark">Job title</label>
                                            <input type="text" className="form-control" onChange={(e) => setTitle(e.target.value)} placeholder="Type a title..." />
                                        </div>
                                        <div className="mb-3">
                                            <label className="form-label text-dark">Start Date</label>
                                            <input type="date" className="form-control" onChange={(e) => setStartDate(e.target.value)} />
                                        </div>
                                        <div className="mb-3">
                                            <label className="form-label text-dark">Salary Range</label>
                                            <div className="row">
                                                <div className="col">
                                                    <input type="text" className="form-control" onChange={(e) => setSalaryFrom(e.target.value)} placeholder="From" />
                                                </div>
                                                <div className="col">
                                                    <input type="text" className="form-control" onChange={(e) => setSalaryTo(e.target.value)} placeholder="To" />
                                                </div>
                                            </div>
                                        </div>
                                        <div className="mb-3">
                                            <label className="form-label text-dark">Working Address</label>
                                            <input type="text" className="form-control" onChange={(e) => setWorkAddress(e.target.value)} placeholder='Type an address' />
                                        </div>
                                    </div>
                                    <div className="col-md-6">
                                        <div className="mb-3">
                                            <label className="form-label text-dark">Skills</label>
                                            <Select
                                                isMulti
                                                options={optionsSkills}
                                                value={skill}
                                                onChange={(e: any) => setSkill(e)}
                                                placeholder="Choose skills"
                                                closeMenuOnSelect={false}
                                            />


                                        </div>
                                        <div className="mb-3">
                                            <label className="form-label text-dark">End Date</label>
                                            <input type="date" className="form-control" onChange={(e) => setEndDate(e.target.value)} />
                                        </div>

                                        <div className="mb-3">
                                            <label className="form-label text-dark">Benefits</label>
                                            <Select
                                                isMulti
                                                options={optionsBenefits}
                                                value={benefit}
                                                onChange={(e: any) => setBenefit(e)}
                                                placeholder="Choose benefits"
                                                closeMenuOnSelect={false}
                                            />
                                        </div>
                                        <div className="mb-3">
                                            <label className="form-label text-dark" >Levels</label>
                                            <Select
                                                isMulti
                                                options={optionsLevels}
                                                value={level}
                                                onChange={(e: any) => setLevel(e)}
                                                placeholder="Choose levels"
                                                closeMenuOnSelect={false}
                                            />
                                        </div>
                                        <div className="mb-3">
                                            <label className="form-label text-dark">Description</label>
                                            <textarea className="form-control" onChange={(e) => setDescription(e.target.value)} placeholder='Type a description' />
                                        </div>
                                    </div>
                                </div>
                                <div className="d-flex justify-content-end">
                                    <button type="submit" className="btn btn-success me-2">Submit</button>

                                    <Link to={"/job"}>
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

export default AddJob