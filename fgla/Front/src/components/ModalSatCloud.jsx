import React,  { useState }  from "react";
import { Modal } from "react-bootstrap";
import { Button } from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCopy } from "@fortawesome/free-solid-svg-icons";

export default function ModalSatCloud({
  show = false,
  handleClose,
  url,
  password,
}) {
  const [showPass, setShowPass] = useState(false);
  const handleCopyPass = () => {
    navigator.clipboard.writeText(password).then(
      () => {
        console.log("Contenido copiado al portapapeles");
      },
      () => {
        console.error("Error al copiar");
      }
    );
  };
// FortifyFalsePositive: La password es de uso interno por el usuario.
  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header closeButton>
        <Modal.Title>Datos de descarga</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        url:{" "}
        <a href={url} target="_blank" rel="noreferrer">
          {url}
        </a>
        <br />
        contraseña:{" "}
        <span className="fw-bolder">
          {showPass ? password : "********"}
        </span>{" "}
        <Button
          variant="link"
          size="sm"
          onClick={() => setShowPass((v) => !v)}
          style={{ padding: 0, marginRight: 8 }}
        >
          {showPass ? "Ocultar" : "Mostrar"}
        </Button>
        <FontAwesomeIcon
          icon={faCopy}
          title="Copiar"
          onClick={handleCopyPass}
          style={{ cursor: "pointer" }}
        />
      </Modal.Body>

      <Modal.Footer>
        <Button
          variant="gray"
          className="btn-sm ms-2 waves-effect waves-light"
          onClick={handleClose}
        >
          Cerrar
        </Button>
      </Modal.Footer>
    </Modal>
  );
}
