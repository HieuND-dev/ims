import React from 'react'
import { Navigate, useNavigate } from 'react-router-dom';

const PrivateRouter = ({ element }: any) => {

    const isAuthentication = localStorage.getItem("user");
    return isAuthentication ? element : <Navigate to={"/login"} />
}

export default PrivateRouter