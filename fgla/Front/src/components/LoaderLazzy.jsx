import React from 'react';
import Portal from "./utils/Portal";

const LoaderLazzy = () => (
  <Portal>
    <div
      style={{
        alignItems: "center",
        position: "fixed",
        width: "100%",
        height: "100%",
        background: "#000",
        opacity: "0.3",
        zIndex: "9999",
        top: 0,
      }}
      className="d-flex justify-content-center"
    >
      <div className="spinner-border" role="status"></div>
    </div>
  </Portal>
);

export default LoaderLazzy;
