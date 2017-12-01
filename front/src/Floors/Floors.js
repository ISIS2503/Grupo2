import React, { Component } from 'react';
import { Table, Button, FormGroup, ControlLabel, FormControl, Glyphicon } from 'react-bootstrap';
import { API_URL } from './../constants';
import Floor from './Floor';
import axios from 'axios';

class Floors extends Component {
  componentWillMount() {
    this.setState({ 
      floors: [],
      message: '',
      name: '',
      code: ''
    });
  }
  componentDidMount() {
    this.getFloors();
  }
  handleNameChange(event) {
    this.setState({ name: event.target.value });
  }
  handleCodeChange(event) {
    this.setState({ code: event.target.value });
  }
  addFloor(event) {
    event.preventDefault();
    const { getIdToken } = this.props.auth;
    const headers = { Authorization: `Bearer ${getIdToken()}`};
    const floor = { name: this.state.name, code: this.state.code };
    axios.post(`${API_URL}/floors`, floor, { credentials: true, headers: headers })
    .then(response => this.getFloors())
    .catch(error => this.setState({ message: error.message }));
  }
  getFloors() {
    const { getIdToken } = this.props.auth;
    const headers = { Authorization: `Bearer ${getIdToken()}`};
    axios.get(`${API_URL}/floors`, { credentials: true, headers: headers })
    .then(response => this.setState({ floors: response.data }))
    .catch(error => this.setState({ message: error.message }));
  }
  render() {
    const { isAuthenticated } = this.props.auth;
    return (
      <div className="container">
      <h1>Floors</h1>
      <h2>Add a floor</h2>
      <form onSubmit={(event) => this.addFloor(event)}>
        <FormGroup controlId="formInlineName">
          <ControlLabel>Name</ControlLabel>
          {' '}
          <FormControl type="text" placeholder="Name" onChange={(event) => this.handleNameChange(event)} />
        </FormGroup>
        {' '}
        <FormGroup controlId="formInlineCode">
          <ControlLabel>Code</ControlLabel>
          {' '}
          <FormControl type="text" placeholder="Code" onChange={(event) => this.handleCodeChange(event)} />
        </FormGroup>
        {' '}
        <Button bsStyle="success" type="submit">
          <Glyphicon glyph="plus" /> Add
        </Button>
      </form>
      <br />
      <Table striped bordered condensed hover className="center">
        <thead>
          <tr>
            <th>#</th>
            <th>ID</th>
            <th>Name</th>
            <th>Code</th>
            <th>Delete</th>
            <th>Edit</th>
          </tr>
        </thead>
        <tbody>
          {this.state.floors.map((floor, index) => {
          return (
            <Floor
              key={index}
              number={index + 1}
              id={floor.id}
              name={floor.name}
              code={floor.code}
              auth={this.props.auth}
              getFloors={() => this.getFloors()}
            />
          );
        })}
        </tbody>
      </Table>
      <h2>{this.state.message}</h2>
      </div>
      );
  }
}

export default Floors;
