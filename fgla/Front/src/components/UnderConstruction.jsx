import React from 'react';
import { Row, Col } from 'react-bootstrap';
import { Accordion } from "../components";
import underConstruction from '../img/under-construction.png';

const UnderConstruction = () => {

    return (
        <Accordion className="mb-4" title="Under Construction">
            <Row>
                <Col md={12} className="text-center">
                    <img src={underConstruction} className="img-fluid" alt="sección en construcción" />
                </Col>
            </Row>
        </Accordion>
    );

}

export default UnderConstruction;