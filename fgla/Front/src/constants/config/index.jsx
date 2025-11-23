const {
  NODE_ENV,
  REACT_APP_BASE_URL_DEVELOP,
  REACT_APP_BASE_URL_PRODUCTION,
  REACT_APP_TAF_ENDPOINT_WINGET
} = process.env;

const config = {
  wingetUrl: REACT_APP_TAF_ENDPOINT_WINGET,
};

if (NODE_ENV === "production") {
  config.baseUrl = REACT_APP_BASE_URL_PRODUCTION;
} else {
  config.baseUrl = REACT_APP_BASE_URL_DEVELOP;
}

console.log("config.baseUrl->", config.baseUrl);

export { config };
