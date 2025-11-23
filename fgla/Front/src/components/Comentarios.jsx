import React, { useState } from "react";
import { Modal, Card, Button } from "react-bootstrap";
import TextArea from "./formInputs/TextArea";
import moment from "moment";
import Authorization from "./Authorization";

const FORMAT_DATE_TIME = "DD/MM/YYYY HH:mm";

const Comentarios = ({
  show,
  comentarios,
  handleCancel,
  handleSave,
  defaultValue,
  title,
  placeholder,
  cancelText,
  disabled,
  process,
  processType = "",
}) => {
  const [comentarioValue, seTComentarioValue] = useState(defaultValue);

  const formatDate = (date) => {
    let formatedDateTime =
      date !== null ? moment(date).format(FORMAT_DATE_TIME) : "";
    return formatedDateTime;
  };

  const onChangeComentario = (e) => {
    seTComentarioValue(e.target.value);
  };

  return (
    <Modal show={show} onHide={handleCancel} size="lg" centered>
      <Modal.Header closeButton>
        <Modal.Title>{title}</Modal.Title>
      </Modal.Header>
      <Modal.Body className="py-1">
        <div className="content-bitacora-dictaminador">
          {comentarios.map((item, i) => {
            return (
              <>
                <Card className="mt-3 p-2 card-comments">
                  <span className="comment-user">{item.usuario}</span>
                  <span className="comment-datetime">
                    {formatDate(item.fechaIngeso)}
                  </span>
                  <span className="mt-2">{item.observaciones}</span>
                </Card>
              </>
            );
          })}
        </div>

        <TextArea
          placeholder={placeholder}
          value={comentarioValue}
          onChange={onChangeComentario}
          rows={4}
          disabled={disabled}
        />
      </Modal.Body>
      <Modal.Footer>
        <Button
          variant="red"
          className="btn-sm ms-2 waves-effect waves-light"
          onClick={handleCancel}
        >
          {cancelText}
        </Button>
        {processType === "" ? (
          <Button
            variant="green"
            disabled={disabled}
            className="btn-sm ms-2 waves-effect waves-light"
            onClick={() => {
              handleSave(comentarioValue);
            }}
          >
            Guardar
          </Button>
        ) : (
          <Authorization process={process}>
            <Button
              variant="green"
              disabled={disabled}
              className="btn-sm ms-2 waves-effect waves-light"
              onClick={() => {
                handleSave(comentarioValue);
              }}
            >
              Guardar
            </Button>
          </Authorization>
        )}
      </Modal.Footer>
    </Modal>
  );
};

Comentarios.defaultProps = {
  title: "Comentario",
  placeholder: "Agregar comentario...",
  cancelText: "Cancelar",
  disabled: false,
};

export default Comentarios;
