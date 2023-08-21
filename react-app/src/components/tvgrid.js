import React, { useEffect, useState } from "react";
import { useSelector, useDispatch } from "react-redux";
import { Redirect, useHistory } from "react-router-dom";
import * as tvActions from "../store/tv";
import './tv.css'

const TvGrid = () => {

    const user = useSelector((state) => state.session.user);
    const shows = Object.values(useSelector((state) => state.tv));
    const dispatch = useDispatch()
    const history = useHistory()

    console.log(shows)


    useEffect(() => {
        dispatch(tvActions.fetchAllTv());
        dispatch(tvActions.fetchAllUsers())
    }, [dispatch]);

    const deleteShow = async (show) => {
        let idx = user.showsWatched.findIndex((ele) => ele.title === show.title)
        user.showsWatched.splice(idx, 1)
        await dispatch(tvActions.fetchAllUsers())
        await dispatch(tvActions.fetchAllTv())
    };

    return (
        <div class='tv-grid'>
            {user.showsWatched?.map((show) => (
                <div class='grid-item'>
                    <div>{show?.title}</div>
                    <div>{show?.status}</div>
                    <button onClick={() => deleteShow(show)}>DELETE</button>
                    <button onClick={() => deleteShow(show)}>EDIT</button>
                </div>
            ))}
        </div>
    )
}

export default TvGrid;
