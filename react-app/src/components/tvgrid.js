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
        await dispatch(tvActions.fetchDeleteTv(user.id, show.id))
        console.log(show, show.id)
        await dispatch(tvActions.fetchAllTv())
        await dispatch(tvActions.fetchAllUsers())
        .then(history.push("/"))
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
