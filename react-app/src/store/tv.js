const POST_TV = "TV/POST_TV";
const EDIT_TV = "TV/EDIT_TV";
const GET_TV = "TV/GET_TV";
const DELETE_TV = "TV/DELETE_TV"

const postTv = (tv) => ({
  type: POST_TV,
  payload: tv,
});

const editTv = (tv) => ({
  type: EDIT_TV,
  payload: tv
});

const getTv = (tv) => ({
  type: GET_TV,
  payload: tv,
});

const deleteTv= (id) => ({
  type: DELETE_TV,
  payload: id
});

export const fetchAllTv = () => async (dispatch) => {
  const response = await fetch("/api/shows");
  if (response.ok) {
    const tv = await response.json();
    dispatch(getTv(tv));
    return tv;
  }
};

export const fetchAllUsers = () => async (dispatch) => {
  const response = await fetch("/api/users");
  if (response.ok) {
    const tv = await response.json();
    dispatch(getTv(tv));
    return tv;
  }
};

export const fetchPostTv = (userId, tv) => async (dispatch) => {
  console.log("This is the data >>>>>>>>>>>", userId, tv)
  const response = await fetch(`/api/user/show/${userId}`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(tv),
  });
  if (response.ok) {
    const tv = await response.json();
    dispatch(postTv(tv));
    return response;
  }
};

export const fetchEditTv = (tvId, payload) => async (dispatch) => {
  console.log(tvId)
  // const formData = new FormData();
  // formData.append("title", newTitle);
  // formData.append("body", newBody);
  const res = await fetch(`/api/tv/${tvId}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(payload)
  });
  if (res.ok) {
    const data = await res.json()
    dispatch(editTv(data))
    return data
  }
}

export const fetchDeleteTv = (id, showId) => async (dispatch) => {
  const response = await fetch(`/api/user/${id}/${showId}`, {
    method: "DELETE",
  });
  console.log(response)
  if (response.ok) {
    dispatch(deleteTv(id))
    return response
  }
}

const initialState = {};

export default function reducer(state = initialState, action) {
  let newState;
  switch (action.type) {
    case GET_TV:
      newState = action.payload;
      return newState;
    case POST_TV:
      newState = Object.assign({}, state);
      newState[action.payload.id] = action.payload;
      return newState;
    case EDIT_TV:
      newState = Object.assign({}, state);
      newState[action.payload.id] = action.payload;
      return newState;
    case DELETE_TV:
      newState = Object.assign({}, state);
      delete newState[action.payload.id];
      return newState;
    default:
      return state;
  }
}
