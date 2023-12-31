import React, { useState, useEffect } from "react";
import { useDispatch, useSelector } from 'react-redux';
import { Route, Switch } from "react-router-dom";
import SignupFormPage from "./components/SignupFormPage";
import LoginFormPage from "./components/LoginFormPage";
import { authenticate } from "./store/session";
import Navigation from "./components/Navigation";
import TvDropdown from "./components/TvDropdown";
import TvGrid from "./components/tvgrid";
import AdminControls from "./components/AdminControls";

function App() {
  const dispatch = useDispatch();
  const [isLoaded, setIsLoaded] = useState(false);
  const user = useSelector((state) => state.session.user);

  useEffect(() => {
    dispatch(authenticate()).then(() => setIsLoaded(true));
  }, [dispatch]);

  return (
    <>
      <Navigation isLoaded={isLoaded} />
      {isLoaded && (
        <Switch>
          <Route path="/login" >
            <LoginFormPage />
          </Route>
          <Route path="/signup">
            <SignupFormPage />
          </Route>
          <Route path='/'>
          {user?.role === 'ROLE_ADMIN' && <AdminControls />}
            {user && <TvDropdown />}
            {user && <TvGrid />}
          </Route>
        </Switch>
      )}
    </>
  );
}

export default App;
