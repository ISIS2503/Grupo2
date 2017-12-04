import React from 'react';
import { Redirect, Route, Router } from 'react-router-dom';
import App from './App';
import Home from './Home/Home';
import Profile from './Profile/Profile';
import Callback from './Callback/Callback';
import Auth from './Auth/Auth';
import Floors from './Floors/Floors';
import Rooms from './Rooms/Rooms';
import history from './history';
import Alerts from './Alerts/Alerts';
import Live from './Live/Live'

const auth = new Auth();

const handleAuthentication = (nextState, replace) => {
  if (/access_token|id_token|error/.test(nextState.location.hash)) {
    auth.handleAuthentication();
  }
}

export const makeMainRoutes = () => {
  return (
    <Router history={history} component={App}>
        <div>
          <Route path="/" render={(props) => <App auth={auth} {...props} />} />
          <Route path="/home" render={(props) => <Home auth={auth} {...props} />} />
          <Route path="/profile" render={(props) => (
            !auth.isAuthenticated() ? (
              <Redirect to="/home"/>
            ) : (
              <Profile auth={auth} {...props} />
            )
          )} />
          <Route path="/floors" render={(props) => (
            !auth.isAuthenticated() || !auth.userHasRole(['admin']) ? (
              <Redirect to="/home"/>
            ) : (
              <Floors auth={auth} {...props} />
            )
          )} />
          <Route path="/rooms" render={(props) => (
            !auth.isAuthenticated() ? (
              <Redirect to="/home"/>
            ) : (
              <Rooms auth={auth} {...props} />
            )
          )} />
          <Route path="/alerts" render={(props) => (
            !auth.isAuthenticated() ? (
              <Redirect to="/home"/>
            ) : (
              <Alerts auth={auth} {...props} />
            )
          )} />
          <Route path="/live" render={(props) => (
            !auth.isAuthenticated() ? (
              <Redirect to="/home"/>
            ) : (
              <Live auth={auth} {...props} />
            )
          )} />
          <Route path="/callback" render={(props) => {
            handleAuthentication(props);
            return <Callback {...props} />
          }}/>
        </div>
      </Router>
  );
}
