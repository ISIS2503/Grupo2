import React, { Component } from 'react';
import { Panel, ControlLabel, Glyphicon } from 'react-bootstrap';

class Live extends Component {
  render() {
    return (
      <iframe src="http://localhost:8087/" height='1000'  width='100%' scrolling='no'></iframe>
    );
  }
}

export default Live;
