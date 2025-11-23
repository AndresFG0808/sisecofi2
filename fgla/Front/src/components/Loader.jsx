import React from "react";
import Portal from "./utils/Portal";

const Loader = ({ zIndex = "9999" }) => (
  <Portal>
    <div
      style={{
        alignItems: "center",
        position: "fixed",
        width: "100%",
        height: "100%",
        background: "#000",
        opacity: "0.3",
        zIndex: zIndex,
        top: 0,
      }}
      className="d-flex justify-content-center"
    >
      <div
        style={{
          textAlign: "center",
          fontSize: "16px",
          fontWeight: "bold",
          color: "#FFF",
          opacity: "1",
        }}
      >
        <div className="spinner-grow px-auto text-dark" role="status"></div>
        <p>Procesando...</p>
      </div>
    </div>
  </Portal>
);

export default Loader;
