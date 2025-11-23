import client from 'axios';
import IDPService from './IDPServiceSAT';
import { ROUTES } from '../../constants/routes';
import { logError } from '../../components/utils/logError.js';


const {
  REACT_APP_USER,
  REACT_APP_PASS,
  REACT_APP_BASE_URL
} = process.env;

const url = REACT_APP_BASE_URL;
const headers = {
  "Content-Type": "application/json",
  "Access-Control-Allow-Origin": "*",
  "Access-Control-Allow-Credentials": "true",
  "Access-Control-Allow-Methods": "DELETE, POST, GET, OPTIONS",
  "Access-Control-Allow-Headers": "Content-Type, Authorization, X-Requested-With"
};

export const getData = (endPoint, temp) =>
  client({
    url: url + `${endPoint}`,
    method: 'GET',
    headers: headers,
  });

export const getDataParams = (endPoint, params) =>
  client({
    url: url + `${endPoint}`,
    method: 'GET',
    data: params,
    headers: headers,
    withCredentials: true,
    credentials: 'same-origin',
  });

export const postData = (endPoint, data) =>
  client({
    url: url + `${endPoint}`,
    method: 'POST',
    data,
    headers: headers,
  });

export const postMultipartData = (endPoint, data) =>
  client({
    url: url + `${endPoint}`,
    method: 'POST',
    data,
    headers: {
      'Content-Type': 'multipart/form-data',
      Authorization: 'Bearer ' + sessionStorage.getItem('access_token'),
      "Access-Control-Allow-Origin": "*",
      "Access-Control-Allow-Credentials": "true",
      "Access-Control-Allow-Methods": "DELETE, POST, GET, OPTIONS",
      "Access-Control-Allow-Headers": "Content-Type, Authorization, X-Requested-With"
    },
  });

export const postDataTimeOut = (endPoint, data, myTimeout) =>
  client({
    url: url + `${endPoint}`,
    method: 'POST',
    data,
    headers: headers,
    withCredentials: true,
    timeout: myTimeout,
    credentials: 'same-origin',
  });

export const putData = (endPoint, data) =>
  client({
    url: url + `${endPoint}`,
    method: 'PUT',
    data,
    headers: headers,
  });

export const putDataForm = (endPoint, formData) =>
  client({
    url: url + `${endPoint}`,
    method: 'PUT',
    data: formData,
    headers: {
      ...headers,
      'Content-Type': 'multipart/form-data',
    }
  });

export const postDataForm = (endPoint, formData) =>
  client({
    url: url + `${endPoint}`,
    method: 'POST',
    data: formData,
    headers: {
      ...headers,
      'Content-Type': 'multipart/form-data',
    }
  });

export const putDataV2 = (endPoint, data) =>
  client({
    url: url + `${endPoint}`,
    method: 'PUT',
    data,
    headers: headers,
  });

export const deleteData = (endPoint, data) =>
  client({
    url: url + `${endPoint}`,
    method: 'DELETE',
    data,
    headers: headers,
  });

export const uploadDocument = (endPoint, formData) =>
  client({
    url: url + `${endPoint}`,
    method: 'POST',
    data: formData,
    headers: {
      ...headers,
      'Content-Type': 'multipart/form-data',
    }
  });

export const uploadDocumentPut = (endPoint, formData) =>
  client({
    url: url + `${endPoint}`,
    method: 'PUT',
    data: formData,
    headers: {
      ...headers,
      'Content-Type': 'multipart/form-data',
    }
  });

export const downloadDocument = (endPoint) =>
  client({
    url: url + `${endPoint}`,
    method: 'GET',
    responseType: 'blob',
    headers: headers
  });

export const downloadDocumentPost = (endPoint, data) =>
  client({
    url: url + `${endPoint}`,
    method: 'POST',
    data: data,
    responseType: 'blob',
    headers: headers,
  });

export const refreshToken = (callback = () => { }) => {
  const refresh = sessionStorage.getItem('refreshToken');
  if (refresh) {
    IDPService.getRefreshToken(refresh)
      .then((response) => response.json())
      .then((data) => {
        if (data.access_token) {
          sessionStorage.removeItem('accessTokenSession');
          sessionStorage.removeItem('access_token');
          sessionStorage.setItem('accessTokenSession', data.access_token);
          sessionStorage.setItem('access_token', data.access_token);
        };
        console.log("refreshToken => token actualizado correctamente");
      })
      .catch((error) => {
        logError("refreshToken error => ", error);
      })
      .finally(() => {
        callback();
      })
  } else {
    console.error('sin refreshToken');
  }
};

export const getToken = (endPoint, data) =>
  client({
    url: url + `${endPoint}`,
    method: 'POST',
    data,
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
      'Authorization': 'Basic ' + btoa(REACT_APP_USER + ":" + REACT_APP_PASS)
    },
  });
