import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.tsx'
// import './index.css'
import { createBrowserRouter, Router, RouterProvider } from 'react-router-dom'
import AdminLayout from './layout/AdminLayout.tsx'
import UserManagement from './screens/UserManagement.tsx'
import JobManagement from './screens/JobManagement.tsx'
import Login from './screens/Login.tsx'
import Dashboard from './screens/Dashboard.tsx'
import OfferManagement from './screens/OfferManagement.tsx'
import InterviewManagement from './screens/InterviewManagement.tsx'
import CandidateManagement from './screens/CandidateManagement.tsx'
import Logout from './screens/Logout.tsx'
import ForgotPassword from './screens/ForgotPassword.tsx'
import ResetPassword from './screens/ResetPassword.tsx'
import PrivateRouter from './screens/PrivateRouter.tsx'
import AddUser from './screens/add/AddUser.tsx'
import UserDetail from './screens/details/UserDetail.tsx'
import UserUpdate from './screens/update/UserUpdate.tsx'
import AddJob from './screens/add/AddJob.tsx'
import JobDetail from './screens/details/JobDetail.tsx'
import JobUpdate from './screens/update/JobUpdate.tsx'
import AddCandidate from './screens/add/AddCandidate.tsx'
import CandidateDetail from './screens/details/CandidateDetail.tsx'
import CandidateUpdate from './screens/update/CandidateUpdate.tsx'
import AddOffer from './screens/add/AddOffer.tsx'
import OfferDetail from './screens/details/OfferDetail.tsx'
import UpdateOffer from './screens/update/OfferUpdate.tsx'
import AddInterview from './screens/add/AddInterview.tsx'
import InterviewDetail from './screens/details/InterviewDetail.tsx'
import UpdateInterview from './screens/update/InterviewUpdate.tsx'
import InterviewUpdate from './screens/update/InterviewUpdate.tsx'
import OfferUpdate from './screens/update/OfferUpdate.tsx'
import ChangePassword from './screens/ChangePassword.tsx'



const router = createBrowserRouter([
  {
    path: "/login",
    element: <Login />
  },
  {
    path: "/logout",
    element: <Logout />
  },
  {
    path: "/forgot-password",
    element: <ForgotPassword />
  },
  {
    path: "/change-password",
    element: <ChangePassword />
  },
  {
    path: "/reset-password",
    element: <ResetPassword />
  },
  {
    path: "/",
    element: <PrivateRouter element={<App />} />,
    children: [
      {
        path: "/",
        element: <PrivateRouter element={<Dashboard />} />,
      },
      // Router User
      {
        path: "user",
        element: <PrivateRouter element={<UserManagement />} />,
      },
      {
        path: "user/add",
        element: <PrivateRouter element={<AddUser />} />,
      },
      {
        path: "user/details/:id",
        element: <PrivateRouter element={<UserDetail />} />,
      },
      {
        path: "user/update/:id",
        element: <PrivateRouter element={<UserUpdate />} />,
      },
      // End Router User

      // Router Job
      {
        path: "job",
        element: <PrivateRouter element={<JobManagement />} />,
      },
      {
        path: "job/add",
        element: <PrivateRouter element={<AddJob />} />,
      },
      {
        path: "job/details/:id",
        element: <PrivateRouter element={<JobDetail />} />,
      },
      {
        path: "job/update/:id",
        element: <PrivateRouter element={<JobUpdate />} />,
      },

      // End Router Job

      // Router Offer
      {
        path: "offer",
        element: <PrivateRouter element={<OfferManagement />} />,
      },
      {
        path: "offer/add",
        element: <PrivateRouter element={<AddOffer />} />,
      },
      {
        path: "offer/details/:id",
        element: <PrivateRouter element={<OfferDetail />} />,
      },
      {
        path: "offer/update/:id",
        element: <PrivateRouter element={<OfferUpdate />} />,
      },

      // End Router Offer
      {
        path: "interview",
        element: <PrivateRouter element={<InterviewManagement />} />,
      },
      {
        path: "interview/add",
        element: <PrivateRouter element={<AddInterview />} />,
      },
      {
        path: "interview/details/:id",
        element: <PrivateRouter element={<InterviewDetail />} />,
      },
      {
        path: "interview/update/:id",
        element: <PrivateRouter element={<InterviewUpdate />} />,
      },

      // Router Candidate 
      {
        path: "candidate",
        element: <PrivateRouter element={<CandidateManagement />} />,
      },
      {
        path: "candidate/add",
        element: <PrivateRouter element={<AddCandidate />} />,
      },
      {
        path: "candidate/details/:id",
        element: <PrivateRouter element={<CandidateDetail />} />,
      },
      {
        path: "candidate/update/:id",
        element: <PrivateRouter element={<CandidateUpdate />} />,
      },

      //End Router Candidate
    ],
  }
]);


// Render your app
ReactDOM.createRoot(document.getElementById('root')!).render(
  // <React.StrictMode>
  <RouterProvider router={router} />
  // </React.StrictMode>
);
