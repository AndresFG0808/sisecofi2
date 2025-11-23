import React from "react";
import ReactDOM from "react-dom/client";
import App from "./components/App/App";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import * as serviceWorker from "./serviceWorker";
import { Provider } from "react-redux";
import { store, persistor } from "./store";
import { GlobalLoader } from "./components/GlobalLoader";
import { PersistGate } from "redux-persist/integration/react";
import { SesionCaducada } from "./components";
import KeycloakProvider from "./components/KeycloakProvider";

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <>
    <Provider store={store}>
      <PersistGate loading={null} persistor={persistor}>
        <KeycloakProvider>
          <App />
          <GlobalLoader />
          <ToastContainer />
          <SesionCaducada />
        </KeycloakProvider>
      </PersistGate>
    </Provider>
  </>
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
