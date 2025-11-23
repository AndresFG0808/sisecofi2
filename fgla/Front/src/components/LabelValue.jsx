import React from 'react';
import { Form } from 'react-bootstrap';

const TextDisplay = ({ label, value }) => {
    return (
        <>
            <Form.Label className='d-block'>{label}</Form.Label>
            <Form.Label className='fw-bold mb-3 d-block'>{value}</Form.Label>
        </>
    );
};

export default TextDisplay;
