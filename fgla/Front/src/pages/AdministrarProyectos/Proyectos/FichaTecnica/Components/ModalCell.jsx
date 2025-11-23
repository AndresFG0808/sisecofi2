import React, { useState } from "react";
import Comentarios from "../../../../../components/Comentarios";

const comentarios = [
  {
    usuario: "",
    fechaIngreso: "",
    observaciones: "",
  },
];
export function ModalCell({ getValue }) {
  const [show, setShow] = useState(false);

  const onSave = () => {
    console.log("saved");
  };

  return (
    <>
      <button
        style={{ border: "1px solid transparent" }}
        onClick={() => setShow(true)}
      >
        ...
      </button>
      <Comentarios
        show={show}
        handleCancel={() => setShow(false)}
        handleSave={onSave}
        comentarios={comentarios}
      />
    </>
  );
}
