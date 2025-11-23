import React, { useState, useEffect } from 'react';
import keys from 'lodash/keys';
import values from 'lodash/values';
import client from 'axios';
import { config } from '../constants/config';
const { baseUrl } = config;

function useDataClients(endpoints = {}) {
  const [loading, setLoading] = useState(true);
  const [results, setResults] = useState([]);
  const [error, setError] = useState('');

  useEffect(() => {
    async function requestData() {
      try {
        const promises = values(endpoints).map(({ dataSource, method, data }) =>
          client({
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
          }),
        );
        const responses = await Promise.all(promises);
        const json = {};
        const names = keys(endpoints);
        responses.forEach((response, index) => {
          const name = names[index];
          console.log('requestData -> name', name);
          json[name] = response.data;
          console.log('requestData -> response.data', response.data);
        });
        console.log('requestData -> json', json);

        setResults(json);
        setLoading(false);
      } catch (error) {
        setError(error.message);
        setLoading(false);
      }

      setLoading(false);
    }

    requestData();
  }, []);

  return {
    loading,
    results,
    error,
    setResults,
  };
}

export default useDataClients;
