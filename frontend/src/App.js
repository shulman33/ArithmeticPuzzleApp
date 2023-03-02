import logo from './puzzle-piece.png';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import * as React from 'react';
import { useState } from 'react';
import './App.css';
import {Button, Typography} from "@mui/material";
import axios from "axios";

function App() {
    const url = 'https://q9hvwoynt0.execute-api.us-east-1.amazonaws.com/Prod/hello';

    const [augend, setAugend] = useState('');
    const [addend, setAddend] = useState('');
    const [sum, setSum] = useState('');

    const [mutationProbability, setMutationProbability] = useState('');
    const [crossoverProbability, setCrossoverProbability] = useState('');
    const [populationSize, setPpulationSize] = useState('');
    const [maxGenerations, setMaxGenerations] = useState('');

    const [beggining, setbeggining] = useState('');
    const [middle, setMiddle] = useState('');
    const [end, setEnd] = useState('');

    const [arrived, setArrived] = useState(false);

    const handleAugendChange = (event) => {
        setAugend(event.target.value);
    };

    const handleAddendChange = (event) => {
        setAddend(event.target.value);
    };

    const handleSumChange = (event) => {
        setSum(event.target.value);
    };


    const handleMutationChange = (event) => {
        setMutationProbability(event.target.value);
    };

    const handleCrossoverChange = (event) => {
        setCrossoverProbability(event.target.value);
    };

    const handlePopulationChange = (event) => {
        setPpulationSize(event.target.value);
    };
    const handleGenerationsChange = (event) => {
        setMaxGenerations(event.target.value);
    };

    const submitting = (e) => {
        e.preventDefault()
        console.log("pressed submit")
        axios.get(`${url}?augend=${augend}&addend=${addend}&sum=${sum}&mutationProbability=${mutationProbability}&crossoverProbability=${crossoverProbability}&populationSize=${populationSize}&maxGenerations=${maxGenerations}`)
            .then(response => {
                console.log(response.data.result);

                const beggining = response.data.result.substring(response.data.result.indexOf("Will try to solve the puzzle:"), response.data.result.indexOf("using this GeneticAlgorithmConfig instance:")) + " ";
                setbeggining(beggining);

                const middle = response.data.result.substring(response.data.result.indexOf("using this GeneticAlgorithmConfig instance:"), response.data.result.indexOf("The number of generations required to produce a solution is:"))
                setMiddle(middle);

                const end = response.data.result.substring(response.data.result.indexOf("The number of generations required to produce a solution is:"))
                setEnd(end);

                setArrived(true);
            })
            .catch(error => {
                console.error(error);
            });

    }


  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" style={{marginBottom: '60px'}}/>
          {arrived ? (
              <Box>
                  <Typography variant="h6">
                      {beggining}
                  </Typography>
                  <Typography variant="h6">
                      {middle}
                  </Typography>
                  <Typography variant="h6">
                      {end}
                  </Typography>
              </Box>

          ) : (
              <>
                  <Typography variant="h4" component="h2">
                      Enter Equation
                  </Typography>
                  <Box
                      component="form"
                      sx={{
                          '& > :not(style)': { m: 1, width: '5ch' },
                          marginBottom : '30px',
                          marginTop : '15px',
                          display: 'flex',
                          alignItems: 'center',
                          justifyContent: 'center',
                      }}
                      noValidate
                      autoComplete="off"
                  >
                      <TextField
                          id="cp"
                          placeholder="A"
                          helperText="augend"
                          variant="outlined"
                          style={{ borderRadius: '10px'}}
                          value={augend}
                          onChange={handleAugendChange}
                          sx={{
                              "& .MuiInputBase-root": {
                                  "& input": {
                                      textAlign: "center",
                                  },
                                  color: 'white'
                              }
                          }}
                          InputProps={{
                              classes: {
                                  notchedOutline: 'white-outline',
                              },
                          }}
                          FormHelperTextProps={{
                              style: { color: 'white', textAlign: "center" }
                          }}
                      />
                      <Typography variant="h5" component="span">+</Typography>
                      <TextField
                          id="cp"
                          placeholder="B"
                          helperText="addend"
                          variant="outlined"
                          style={{ borderRadius: '10px'}}
                          value={addend}
                          onChange={handleAddendChange}
                          sx={{
                              "& .MuiInputBase-root": {
                                  "& input": {
                                      textAlign: "center",
                                  },
                                  color: 'white'
                              }
                          }}
                          InputProps={{
                              classes: {
                                  notchedOutline: 'white-outline',
                              },
                          }}
                          FormHelperTextProps={{
                              style: { color: 'white', textAlign: "center" }
                          }}
                      />
                      <Typography variant="h5" component="span" sx={{marginTop: '90px'}}>=</Typography>
                      <TextField
                          id="cp"
                          placeholder="PQ"
                          helperText="Sum"
                          variant="outlined"
                          style={{ borderRadius: '10px'}}
                          value={sum}
                          onChange={handleSumChange}
                          sx={{
                              "& .MuiInputBase-root": {
                                  "& input": {
                                      textAlign: "center",
                                  },
                                  color: 'white'
                              }
                          }}
                          InputProps={{
                              classes: {
                                  notchedOutline: 'white-outline',
                              }
                          }}
                          FormHelperTextProps={{
                              style: { color: 'white', textAlign: "center" }
                          }}
                      />
                  </Box>
                  <Typography variant="h4" component="h2">
                      Configure the Genetic Algorithm
                  </Typography>
                  <Box
                      component="form"
                      sx={{
                          '& > :not(style)': { m: 1, width: '7ch' },
                          marginTop : '15px'
                      }}
                      noValidate
                      autoComplete="off"
                  >
                      <TextField
                          id="cp"
                          placeholder="0.8"
                          helperText="Crossover Probability"
                          variant="outlined"
                          style={{borderRadius: '10px'}}
                          value={crossoverProbability}
                          onChange={handleCrossoverChange}
                          sx={{
                              "& .MuiInputBase-root": {
                                  "& input": {
                                      textAlign: "center",
                                  },
                                  color: 'white'
                              }
                          }}
                          InputProps={{
                              classes: {
                                  notchedOutline: 'white-outline',
                              },
                          }}
                          FormHelperTextProps={{
                              style: { color: 'white', textAlign: "center" }
                          }}
                      />
                      <TextField
                          id="mp"
                          placeholder="0.2"
                          helperText="Mutation Probability"
                          variant="outlined"
                          style={{borderRadius: '10px'}}
                          value={mutationProbability}
                          onChange={handleMutationChange}
                          sx={{
                              "& .MuiInputBase-root": {
                                  "& input": {
                                      textAlign: "center",
                                  },
                                  color: 'white'
                              }
                          }}
                          InputProps={{
                              classes: {
                                  notchedOutline: 'white-outline',
                              },
                          }}
                          FormHelperTextProps={{
                              style: { color: 'white', textAlign: "center" }
                          }}

                      />
                      <TextField
                          id="cp"
                          placeholder="40"
                          helperText="Max Generation"
                          variant="outlined"
                          style={{borderRadius: '10px'}}
                          value={maxGenerations}
                          onChange={handleGenerationsChange}
                          sx={{
                              "& .MuiInputBase-root": {
                                  "& input": {
                                      textAlign: "center",
                                  },
                                  color: 'white'
                              }
                          }}
                          InputProps={{
                              classes: {
                                  notchedOutline: 'white-outline',
                              },
                          }}
                          FormHelperTextProps={{
                              style: { color: 'white', textAlign: "center" }
                          }}
                      />
                      <TextField
                          id="cp"
                          placeholder="1000"
                          helperText="Population Size"
                          variant="outlined"
                          style={{borderRadius: '10px'}}
                          value={populationSize}
                          onChange={handlePopulationChange}
                          sx={{
                              "& .MuiInputBase-root": {
                                  "& input": {
                                      textAlign: "center",
                                  },
                                  color: 'white'
                              }
                          }}
                          InputProps={{
                              classes: {
                                  notchedOutline: 'white-outline',
                              },
                          }}
                          FormHelperTextProps={{
                              style: { color: 'white', textAlign: "center" }
                          }}
                      />
                  </Box>
                  <Button variant="contained" sx={{marginTop : '30px'}} onClick={submitting}>Run</Button>
              </>
          )}

      </header>
    </div>
  );
}

export default App;
