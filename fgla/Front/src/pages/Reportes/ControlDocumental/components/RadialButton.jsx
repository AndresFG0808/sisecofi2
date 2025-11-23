import React from "react";
import { Col, FormCheck } from "react-bootstrap";

export function RadialButton({ onClick, label, md = 4, checked, id, group }) {
  return (
    <>
      <Col
        md={md}
        className="d-flex align-items-center gap-1 rounded-form-check"
      >
        <span>{label}:</span>
        <FormCheck
          onClick={(e) => {
            onClick(e);
            console.log("On click");
            console.log(e);
          }}
          type="radio"
          id={id}
          name={group}
          checked={checked}
        />
      </Col>
    </>
  );
}
