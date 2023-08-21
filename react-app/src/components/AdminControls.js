import React, { useEffect, useState } from "react";
import { useSelector, useDispatch } from "react-redux";
import { Redirect, useHistory } from "react-router-dom";
import * as tvActions from "../store/tv";
import './tv.css'

const AdminControls = () => {

    const user = useSelector((state) => state.session.user);
    const dispatch = useDispatch()
    const history = useHistory()

    return (
                <div class='admin-box'>
                    <button>ADD SHOW</button>
                    <button>ADD USER</button>
                    <button>DELETE SHOW</button>
                    <button>DELETE USER</button>
                </div>
    )
}

export default AdminControls;
