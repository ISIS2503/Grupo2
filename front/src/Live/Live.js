import React, { Component } from 'react';
import { Button, Table, Form, FormGroup, FormControl, ControlLabel } from 'react-bootstrap';

class Live extends Component {
  componentWillMount() {
    this.state = {value: ''};

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleChange(event) {
    this.setState({value: event.target.value});
  }

  handleSubmit(event) {
    event.preventDefault();
    console.log(this.state.value)
    document.getElementById("prueba").innerHTML = `<iframe id="frame" src="http://localhost:8087/${this.state.value}" height='1000'  width='100%' scrolling='no' frameBorder="0"></iframe>`;
    console.log(document.getElementById("frame").src)
  }

  render() {
    return (
      <div>
      <div className="container">
      <h1>Temperatura live data</h1>
      <Form inline onSubmit={(event) => this.handleSubmit(event)}>
        <FormGroup controlId="formInlineName">
          <ControlLabel> code</ControlLabel>
          {' '}
          <FormControl type="text" placeholder="nive1.nivel1" onChange={(event) => this.handleChange(event)} />
        </FormGroup>
        {' '}
        <Button type="submit">
          Get data
        </Button>
      </Form>
      </div>
      <div id='prueba'/>
      </div>
    );
  }
}

export default Live;
