import React from "react";
import { Col, FormCheck } from "react-bootstrap";

export function RadialButton({
  label,
  md = 4,
  checked,
  id,
  group,
  setFieldValue,
  value,
  cancelField,
  canceledValue,
}) {
  return (
    <>
      <Col
        md={md}
        className="d-flex align-items-center gap-1 rounded-form-check"
      >
        <FormCheck
          onChange={(e) => {
            setFieldValue(id, !value);
            setFieldValue(cancelField, !canceledValue);
          }}
          label={label}
          type="radio"
          id={id}
          name={group}
          checked={value}
        />
      </Col>
    </>
  );
}
