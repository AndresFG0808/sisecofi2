import React, { useState, useEffect } from 'react';
import client from 'axios';
import { config } from '../constants/config';
import IDSServices from '../functions/api/IDPService.jsx'
import { logError } from '../components/utils/logError.js';
const { baseUrl } = config;

function useDataClient(dataSource, { method, data = {} } = { method: 'GET' }) {
  const [loading, setLoading] = useState(true);
  const [results, setResults] = useState([]);
  const [error, setError] = useState('');
  const validarToken=()=>
  {
    const validoToken=IDSServices.getTokenInfo(sessionStorage.getItem('accessTokenSession'));
    if(!validoToken)
    {
      IDSServices.getRefreshToken()
      .then((response) => response.json())
    .then((data) => {
      console.log(data);
      console.log(data.access_token);
      sessionStorage.removeItem('codeSession');
      sessionStorage.removeItem('codeVerSession');
      sessionStorage.removeItem('accessTokenSession');
      if (data.access_token) {
        sessionStorage.setItem('accessTokenSession', data.access_token);
        sessionStorage.setItem('refreshToken',data.refresh_token);
      }
    })
    .catch((ex) => {
      logError('Error:::obtenerRefreshToken->', ex);
      console.log('Se inicia nuevamente el ciclo');
      sessionStorage.removeItem('accessTokenSession');
      sessionStorage.removeItem('codeSession');
    });;
    }
  }
  useEffect(() => {
    validarToken();
    async function requestData() {
      try {
        const response = await client({
          url: `${baseUrl}${dataSource}`,
          method,
          data,
          mode: 'no-cors',
          headers: {
            'Access-Control-Allow-Origin': '*',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + sessionStorage.getItem('accessTokenSession'),
          },
          withCredentials: true,
          credentials: 'same-origin',
        });
        const json = await response.data;

        if (response.status === 200 || response.status === 201) {
          setResults(json);
          setLoading(false);
        }
      } catch (error) {
        setError(error.message);
        setLoading(false);
      }

      setLoading(false);
    }

    requestData();
  }, [dataSource]);

  return {
    loading,
    results,
    error,
    setLoading,
    setError,
    setResults,
  };
}

export default useDataClient;
