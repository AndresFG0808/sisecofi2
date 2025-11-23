const {
  REACT_APP_BASE_URL_OAUTH,
  REACT_APP_CLIENT_ID,
  REACT_APP_CLIENT_SECRET,
  REACT_APP_REDIRECT_URI
} = process.env;

const REST_IDP = `${REACT_APP_BASE_URL_OAUTH}/nidp/oauth/nam`;
const GET_AUTH_CODE_PKCE_URL = `${REST_IDP}/authz`;
const GET_ACCESS_TOKEN_URL = `${REST_IDP}/token`;

const GET_REVOKE_TOKEN = `${REST_IDP}/revoke`;
const GET_TOKEN_INFO_URL = `${REST_IDP}/tokeninfo`;
const CLIENT_ID = REACT_APP_CLIENT_ID;
const CLIENT_SECRET = REACT_APP_CLIENT_SECRET;
const REDIRECT_URI = REACT_APP_REDIRECT_URI;
const RESOURCE_SERVER = 'msempleado';
const SCOPE = 'msempleado';
const GRANT_TYPE = 'authorization_code';
const GRANT_TYPE_REFRESH = 'refresh_token';

class IDPService {
  getAuthCodePKCE(codeChallenge) {
    console.log('endpoint a consultar:::');
    console.log(
      `${GET_AUTH_CODE_PKCE_URL}?client_id=${CLIENT_ID}&response_type=code&redirect_uri=${REDIRECT_URI}&scope=${SCOPE}&resourceServer=${RESOURCE_SERVER}&state=test`,
      );
    return `${GET_AUTH_CODE_PKCE_URL}?client_id=${CLIENT_ID}&response_type=code&redirect_uri=${REDIRECT_URI}&scope=${SCOPE}&resourceServer=${RESOURCE_SERVER}&state=test`;
  }

  getAccessToken(code, codeVerifier) {
    return fetch(`${GET_ACCESS_TOKEN_URL}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8',
      },
      body: `grant_type=${GRANT_TYPE}&client_id=${CLIENT_ID}&code=${code}&redirect_uri=${REDIRECT_URI}&code_verifier=${codeVerifier}&resourceServer=${RESOURCE_SERVER}&client_secret=${CLIENT_SECRET}`,
    });
  }

  getRefreshToken(refresh) {
    return fetch(`${GET_ACCESS_TOKEN_URL}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8',
      },
      body: `grant_type=${GRANT_TYPE_REFRESH}&client_id=${CLIENT_ID}&client_secret=${CLIENT_SECRET}&scope=${SCOPE}&refresh_token=${refresh}`,
    });
  }

  getRevokeToken() {
    return fetch(`${GET_REVOKE_TOKEN}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8',
      },
      body:
        `client_id=${CLIENT_ID}&client_secret=${CLIENT_SECRET}&token=` +
        sessionStorage.getItem('accessTokenSession'),
    });
  }

  getTokenInfo(method) {
    console.log('Endpoint a consultar');
    console.log(`${GET_TOKEN_INFO_URL}`);
    console.log(method);
    console.log(sessionStorage.getItem('accessTokenSession'));
    const hardValid = sessionStorage.getItem('hardToken');
    if (hardValid) {
      console.log('prueba DEV()');

      return true;
    } else {
      return fetch(`${GET_TOKEN_INFO_URL}`, {
        method: 'POST',
        mode: 'no-cors',
        headers: {
          'Access-Control-Allow-Origin': '*',
          'Content-Type': 'application/json',
          'Authorization': 'Bearer ' + sessionStorage.getItem('accessTokenSession'),
        },
        withCredentials: true,
        credentials: 'same-origin',
      });
    }
  }
}

export default new IDPService();