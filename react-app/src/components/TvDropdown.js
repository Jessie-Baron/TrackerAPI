import React, { useEffect, useState } from "react";
import { useSelector, useDispatch } from "react-redux";
import { Redirect, useHistory } from "react-router-dom";
import * as tvActions from "../store/tv";
import './tv.css'

const TvDropdown = () => {

    const user = useSelector((state) => state.session.user);
    const tv = Object.values(useSelector((state) => state.tv));
    const dispatch = useDispatch()
    const history = useHistory()
    const [hasSubmitted, setHasSubmitted] = useState(false);
    const [show, setShow] = useState("");
    const [rating, setRating] = useState("")
    const [epiWatched, setEpiWatched] = useState("")
    const [completed, setCompleted] = useState("")
    const [validationErrors, setValidationErrors] = useState([]);

    const selectShow = (value) => {
        console.log(value)
        setShow(value)
    }

    useEffect(() => {
        dispatch(tvActions.fetchAllTv());
        dispatch(tvActions.fetchAllUsers())
    }, [dispatch]);


    useEffect(() => {
        dispatch(tvActions.fetchAllTv());
    }, [dispatch]);

    const handleSubmit = async (e) => {
        // Prevent the default form behavior so the page doesn't reload.
        e.preventDefault();
        setHasSubmitted(true);

        // Create a new object for the song form information.
        console.log("this is the show >>>>>>>>>>>", show)
        const showForm = {
            "title": show,
            "status": completed,
            "rating": rating,
            "episodesWatched": epiWatched
        };

        await dispatch(tvActions.fetchPostTv(user.id, showForm))
        user.showsWatched.push(showForm)
        await dispatch(tvActions.fetchAllUsers())
        await dispatch(tvActions.fetchAllTv())
        .then(history.push("/"))



        // Reset the form state.
        setValidationErrors([]);
        setHasSubmitted(false);
    }

    return (
        <div class='dropdown-container'>
            <div class='dropdown'>
                <form onSubmit={handleSubmit}>
                    <ul>
                        {validationErrors.map((error, idx) => (
                            <li key={idx}>{error}</li>
                        ))}
                    </ul>
                    <div class='dropdown-text'>
                        <h3>Hello, {user?.username}</h3>
                        <h5>Please select a show from the dropwdown to track</h5>
                    </div>
                    <select class='select' name="tv"
                        onChange={(e) => selectShow(e.target.value)}>
                        {tv?.map((series) => (
                            <option>{series.title}</option>
                        ))}
                    </select>
                    <label>Rate this show</label>
                    <select
                        class='select-2'
                        onChange={(e) => setRating(e.target.value)}
                    >
                        <option>1</option>
                        <option>2</option>
                        <option>3</option>
                        <option>4</option>
                        <option>5</option>
                    </select>
                    <label>How many episodes have you completed?</label>
                    <input
                        class='select'
                        onChange={(e) => setEpiWatched(e.target.value)}
                    ></input>
                    <label>Have you completed this show?</label>
                    <select
                        class='select'
                        onChange={(e) => setCompleted(e.target.value)}
                    >
                        <option>COMPLETED</option>
                        <option>CURRENTLY_WATCHING</option>
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
