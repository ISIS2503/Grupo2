import React, { Component } from 'react';
import { Button, Table, Form, FormGroup, FormControl, ControlLabel } from 'react-bootstrap';
import { API_URL } from './../constants';
import axios from 'axios';

class Alerts extends Component {
  componentWillMount() {
    this.setState({
      data: [],
      alert: '',
      message: ''
    });
  }

  getAlertsData(event) {
    event.preventDefault();
    const { getAccessToken } = this.props.auth;
    const headers = { Authorization: `Bearer ${getAccessToken()}`};
    axios.get(`${API_URL}/alerta/ubicacion/${this.state.alert}`, { credentials: true, headers: headers })
    .then(response => this.setState({ message: '', data: response.data }))
    .catch(error => this.setState({ message: error.message, data: [] }));
    console.log(this.state.data)
  }

  handleSearchChange(event) {
    this.setState({ alert: event.target.value });
  }

  render() {
    const { isAuthenticated } = this.props.auth;
    return (
      <div className="container">
      <h1>Alerts data</h1>
      <Form inline onSubmit={(event) => this.getAlertsData(event)}>
        <FormGroup controlId="formInlineName">
          <ControlLabel>Nivel code</ControlLabel>
          {' '}
          <FormControl type="text" placeholder="nivel1.area1"  onChange={(event) => this.handleSearchChange(event)} />
        </FormGroup>
        {' '}
        <Button type="submit">
          Get Alerts data
        </Button>
      </Form>
      <Table striped bordered condensed hover>
        <thead>
          <tr>
            <th>#</th>
            <th>ID</th>
            <th>descripcion</th>
            <th>ubicacion</th>
            <th>fecha</th>
          </tr>
        </thead>
        <tbody>
          {this.state.data.map((data, index) => {
          return <tr key={index}>
            <td>{index}</td>
            <td>{data.id + 1}</td>
            <td>{data.descripcion}</td>
            <td>{data.ubicacion}</td>
            <td>{data.fecha}</td>
          </tr>
        })}
        </tbody>
      </Table>
      <h2>{this.state.message}</h2>
      </div>
      );
  }
}

export default Alerts;
