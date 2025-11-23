import React from 'react';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCalendarAlt } from "@fortawesome/free-solid-svg-icons";
import { Form, InputGroup } from "react-bootstrap";
import DatePicker from "react-datepicker";
import { Portal } from 'react-overlays'
import { registerLocale } from "react-datepicker";
import es from 'date-fns/locale/es';
import { useField, useFormikContext, getIn } from "formik";
import moment from "moment";

import "react-datepicker/dist/react-datepicker.css";

registerLocale('es', es);

export default function CustomYearPicker({ ...props }) {
    const { setFieldValue } = useFormikContext();
    const [field] = useField(props);
    const { label, minDate, maxDate, afterChange, dateFormat, disabled, ...inputProps } = props;
    const { onChange, name, value, isInvalid, errors, onBlur, ...pickerProps } = props;

    const FORMAT = 'yyyy';

    const CustomInput = ({ value, onClick }) => (
        <Form.Group className="mb-3">
            <Form.Label>{label}</Form.Label>
            <InputGroup>
                <Form.Control type="text"
                    {...field}
                    {...inputProps}
                    readOnly
                    disabled
                    style={disabled ? null : { "backgroundColor": "#fff" }}
                />
                <InputGroup.Text className='input-icon-end' onClick={onClick} style={{ cursor: `${disabled ? 'unset' : 'pointer'}` }}>
                    <FontAwesomeIcon icon={faCalendarAlt} />
                </InputGroup.Text>
                <Form.Control.Feedback type="invalid">
                    {getIn(props.errors, props.name)}
                </Form.Control.Feedback>
            </InputGroup>
        </Form.Group>
    );

    return (
        <DatePicker
            {...pickerProps}
            popperContainer={Portal}
            locale="es"
            showYearPicker
            dateFormat={FORMAT}
            onChange={date => {
                setFieldValue(field.name, moment(date).format(FORMAT));
                if (afterChange) {
                    afterChange();
                }
            }}
            customInput={<CustomInput />}
        />
    );
}