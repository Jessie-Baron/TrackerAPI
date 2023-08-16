import React, { useEffect, useState } from "react";
import { useSelector, useDispatch } from "react-redux";
import { Redirect, useHistory } from "react-router-dom";
import * as tvActions from "../store/tv";

const TvDropdown = () => {

    const user = useSelector((state) => state.session.user);
    const tv = Object.values(useSelector((state) => state.tv));
    const dispatch = useDispatch()
    const history = useHistory()


    useEffect(() => {
        dispatch(tvActions.fetchAllTv());
    }, [dispatch]);

    const handleSubmit = () => {

    }

    return (
        <div class='dropdown-container'>
            <div class='dropdown-text'>
                <h3>Hello, {user?.username}</h3>
                <h5>Please select a show from the dropwdown to track</h5>
            </div>
            <div class='dropdown'>
                <form onSubmit={handleSubmit}>
                    <select name="tv">
                        {tv?.map((series) => (
                            <option>{series.title} {series.episodes}</option>
                        ))}
                    </select>
                    <button
                        type='submit'
                    >
                        Add
                    </button>
                </form>
            </div>
        </div>
    )
}

export default TvDropdown;
