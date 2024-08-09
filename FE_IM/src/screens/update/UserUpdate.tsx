import React, { useEffect, useState } from 'react'
import { Link, useNavigate, useParams } from 'react-router-dom'
import toastr from 'toastr';
import "toastr/build/toastr.min.css"

const UserUpdate = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [fullName, setFullName] = useState("");
    const [dob, setDob] = useState("");
    const [phone, setPhone] = useState("");
    const [role, setRole] = useState("");
    const [status, setStatus] = useState("");
    const [email, setEmail] = useState("");
    const [address, setAddress] = useState("");
    const [gender, setGender] = useState("");
    const [department, setDepartment] = useState("");
    const [note, setNote] = useState("");
    const [user, setUser]: any = useState("");

    const handleSubmit = async (e: any) => {
        e.preventDefault();
        const formData = {
            full_name: fullName,
            date_of_birth: dob,
            phone_no: phone,
            role: role,
            account_status: status,
            email: email,
            address: address,
            gender: gender,
            department: department,
            note: note
        }
        console.log("Data", formData);

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
                const res = await fetch(`${apiUrl}/api/users/${id}`, {
                    method: "PUT",
                    headers: {
                        "Authorization": `Bearer ${token}`,
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(formData)
                });
                if (res.ok) {
                    toastr.success("Sửa thành công!");
                    navigate("/user")
                } else {
                    toastr.error("Sửa thất bại!")
                }
            }

        } catch (error) {
            console.log("Fetch error: ", error);
        }
    }

    useEffect(() => {
        const hanldeShowDetailUser = async () => {

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
                    const res = await fetch(`${apiUrl}/users/details/${id}`, {
                        headers: {
                            "Authorization": `Bearer ${token}`,
                            // "Content-Type": "application/json"
                        }
                    });
                    const data = await res.json();
                    console.log(data);
                    setUser(data);
                    setFullName(data.fullName)
                    setDob(data.dateOfBirth)
                    setPhone(data.phoneNo)
                    setRole(data.role)
                    setStatus(data.status)
                    setEmail(data.email)
                    setAddress(data.address)
                    setNote(data.note)
                    setGender(data.gender)
                    setDepartment(data.department)

                }

            } catch (error) {
                console.log("Fetch error: ", error);

            }
        }
        hanldeShowDetailUser();
    }, [])

    return (
        <div className="container-fluid py-4">
            <div className="row">
                <div className="col-12">
                    <div className="card my-4">
                        <div className="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
                            <div className="bg-gradient-dark shadow-dark border-radius-lg pt-4 pb-3">
                                <h6 className="text-white text-capitalize ps-3">Update User</h6>
                            </div>
                        </div>
                        <div className="card-body px-0 pb-2">
                            <form onSubmit={handleSubmit} className="p-3">
                                <div className="row">
                                    <div className="col-md-6">
                                        <div className="mb-3">
                                            <label className="form-label text-dark">Full name</label>
                                            <input type="text" className="form-control" defaultValue={user.fullName} onChange={(e) => setFullName(e.target.value)} />
                                        </div>
                                        <div className="mb-3">
                                            <label className="form-label text-dark">D.O.B</label>
                                            <input type="date" className="form-control" defaultValue={user.dateOfBirth} onChange={(e) => setDob(e.target.value)} />
                                        </div>
                                        <div className="mb-3">
                                            <label className="form-label text-dark">Phone number</label>
                                            <input type="text" className="form-control" defaultValue={user.phoneNo} onChange={(e) => setPhone(e.target.value)} />
                                        </div>
                                        <div className="mb-3">
                                            <label className="form-label">Roles</label>
                                            <select className="form-select" value={user.role} onChange={(e) => {
                                                user.role = e.target.value
                                                setRole(e.target.value)
                                            }}>
                                                <option value="">Select a position</option>
                                                <option value="ADMIN">ADMIN</option>
                                                <option value="RECRUITER">RECRUITER</option>
                                                <option value="MANAGER">MANAGER</option>
                                                <option value="INTERVIEWER">INTERVIEWER</option>
                                            </select>
                                        </div>
                                        <div className="mb-3">
                                            <label className="form-label">Status</label>
                                            <input type="text" disabled className="form-control" value={user.status} />
                                        </div>
                                    </div>
                                    <div className="col-md-6">

                                        <div className="mb-3">
                                            <label className="form-label text-dark">Email</label>
                                            <input type="text" className="form-control" disabled value={user.email} onChange={(e) => setEmail(e.target.value)} />
                                        </div>
                                        <div className="mb-3">
                                            <label className="form-label text-dark">Address</label>

                                            <input type="text" className="form-control" defaultValue={user.address} onChange={(e) => setAddress(e.target.value)} />
                                        </div>
                                        <div className="mb-3">
                                            <label className="form-label text-dark">Note</label>
                                            <input type="text" className="form-control" defaultValue={user.note} onChange={(e) => setNote(e.target.value)} />
                                        </div>
                                        <div className="mb-3">
                                            <label className="form-label">Gender</label>
                                            <select className="form-select" value={user.gender} onChange={(e) => {
                                                user.gender = e.target.value
                                                setGender(e.target.value)

                                            }}>
                                                <option value="" selected>Select a gender</option>
                                                <option value="MALE">MALE</option>
                                                <option value="FEMALE">FEMALE</option>
                                                <option value="OTHER">OTHER</option>
                                            </select>
                                        </div>
                                        <div className="mb-3">
                                            <label className="form-label">Department</label>
                                            <select className="form-select" value={user.department} onChange={(e) => {
                                                user.department = e.target.value
                                                setDepartment(e.target.value)
                                            }}>
                                                <option value="" >Select a department</option>
                                                <option value="IT">IT</option>
                                                <option value="HR">HR</option>
                                                <option value="COMMUNICATION">COMMUNICATION</option>
                                                <option value="FINANCE">FINANCE</option>
                                                <option value="MARKETING">MARKETING</option>
                                                <option value="ACCOUNTING">ACCOUNTING</option>
                                            </select>
                                        </div>


                                    </div>
                                </div>
                                <div className="d-flex justify-content-end">
                                    <button type="submit" className="btn btn-success me-2">Submit</button>

                                    <Link to={"/user"}>
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

export default UserUpdate