import React from "react";
import { Card } from "react-bootstrap";
import styled from "styled-components";


const CardPanel = styled(Card)`
  color: #333333;
  border-radius: 0.25rem;
`;

const CardContainer = ({ title, children }) => {
  return (
    <CardPanel className="shadow-sm mb-4">
      {
        title && <Card.Header className="card-container-title">{title}</Card.Header>
      }
      <Card.Body className="card-container-body">{children}</Card.Body>
    </CardPanel>
  );
};

export default CardContainer;
