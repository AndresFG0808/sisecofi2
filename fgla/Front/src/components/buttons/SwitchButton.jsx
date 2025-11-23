import { useState } from 'react';
import Form from 'react-bootstrap/Form';
import { Tooltip } from '../Tooltip';

function SwitchButton({ value, onChange, disabled }) {

    return (
        <Form>
            <Tooltip placement="top" text="Estatus" >
                <span>
                    <Form.Check
                        type="switch"
                        value={value}
                        checked={value}
                        onChange={() => { onChange(); }}
                        disabled={disabled}
                    />
                </span>
            </Tooltip>
        </Form>
    );
}

export default SwitchButton;