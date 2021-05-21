import React, { Component } from 'react';
import './App.css';
import bloomLogo from './simbolo.svg';




class App extends Component {

  constructor(props) {
    super(props);

    this.state = {
      data: null // sample
    }

  }

  componentDidMount() {

    /*const requestPromise = showAPIData();

    requestPromise.then((result) => {
      this.setState({ data: result })
    })*/

    

    // TODO: get result, save it in state and show some information on UI
  }



  render() {

    const data = this.state.data;

    
    return (
      <div className="App">
        <header className="App-header">
          <img src={bloomLogo} className="App-logo" alt="logo" />
          <h2>
            BLOOM
          </h2>

          <hr></hr>

          {data ? (
            <>
              <table>
                <tbody>
                  <tr>
                    <th style={{padding:"30px"}}>Sensor ID</th>
                    <th style={{padding:"30px"}}>Sensor type</th>
                    <th style={{padding:"30px"}}>Value</th>
                    <th style={{padding:"30px"}}>Timestamp</th>
                  </tr>
                  {
                    data.map((reading, key) => {
                      return (
                        <tr key={key}>
                          <td>{reading.sensor_id}</td>
                          <td>{reading.sensor_type}</td>
                          <td>{reading.value + " " + reading.unit_abbreviation}</td>
                          <td>{reading.timestamp}</td>
                        </tr>
                      )
                    })
                  }
                </tbody>
              </table>
            </>

          ) : <p>No data 😞</p>}
        </header>
      </div>
    );
  }
}

export default App;
