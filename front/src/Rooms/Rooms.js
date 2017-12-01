import React, { Component } from 'react';
import { Button, Table, Form, FormGroup, FormControl, ControlLabel } from 'react-bootstrap';
import { API_URL } from './../constants';
import axios from 'axios';

class Rooms extends Component {
  componentWillMount() {
    this.setState({
      data: [],
      room: '',
      message: ''
    });
  }

  getConsolidatedData(event) {
    event.preventDefault();
    const { getAccessToken } = this.props.auth;
    const headers = { Authorization: `Bearer ${getAccessToken()}`};
    axios.get(`${API_URL}/measure/ubicacion/${this.state.room}`, { credentials: true, headers: headers })
    .then(response => this.setState({ message: '', data: response.data }))
    .catch(error => this.setState({ message: error.message, data: [] }));
    console.log(this.state.data)
  }

  handleSearchChange(event) {
    this.setState({ room: event.target.value });
  }

  render() {
    const { isAuthenticated } = this.props.auth;
    return (
      <div className="container">
      <h1>Consolidated data</h1>
      <Form inline onSubmit={(event) => this.getConsolidatedData(event)}>
        <FormGroup controlId="formInlineName">
          <ControlLabel>Room code</ControlLabel>
          {' '}
          <FormControl type="text" placeholder="piso1.nivel1"  onChange={(event) => this.handleSearchChange(event)} />
        </FormGroup>
        {' '}
        <Button type="submit">
          Get historical data
        </Button>
      </Form>
      <Table striped bordered condensed hover>
        <thead>
          <tr>
            <th>#</th>
            <th>ID</th>
            <th>Variable</th>
            <th>Value</th>
            <th>Unidad</th>
            <th>Date start</th>
            <th>Ubicacion ID</th>
          </tr>
        </thead>
        <tbody>
          {this.state.data.map((data, index) => {
          return <tr key={index}>
            <td>{index}</td>
            <td>{data.id + 1}</td>
            <td>{data.variable}</td>
            <td>{data.valor}</td>
            <td>{data.unidad}</td>
            <td>{data.fecha}</td>
            <td>{data.ubicacion}</td>
          </tr>
        })}
        </tbody>
      </Table>
      <h2>{this.state.message}</h2>
      </div>
      );
  }
}

export default Rooms;
