import React, { useEffect } from 'react'
import { useNavigate } from 'react-router-dom';
import toastr from 'toastr';
import "toastr/build/toastr.min.css"
import Swal from 'sweetalert2'
const Logout = () => {

    const navigate = useNavigate();

    useEffect(() => {
        localStorage.removeItem("user");
        toastr.success("Đăng xuất thành công!")
        navigate("/login")
    }, [])
    return null
}

export default Logout