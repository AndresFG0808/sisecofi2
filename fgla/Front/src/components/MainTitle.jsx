import React from 'react';
import styled from 'styled-components';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import { COLORS } from '../constants/colors';

const Line = styled.hr`
  margin: 10px 0;
  border-top-color: ${COLORS.grey2};
  color: ${COLORS.primary};
  opacity: 1;
  &:before {
    display: block;
    content: ' ';
    width: 35px;
    height: 5px;
    background-color: #BC955C;
    position: absolute;
  }
`;

const Title = styled.div`
  text-align: left;
  color: #424242;
  font-weight: 600;
  font-size: 26px;
`;

const MainTitle = ({ title, className }) => (
  <Row className ={className ? className : "mb-4"}>
    <Col md={12}>
      <Title>
        {title}
        <Line />
      </Title>
    </Col>
  </Row>
);

export default MainTitle;
