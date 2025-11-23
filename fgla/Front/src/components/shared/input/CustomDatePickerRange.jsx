import React, { useState, useEffect, forwardRef } from 'react';
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

export default function CustomDatePicker({ ...props }) {
    const { setFieldValue } = useFormikContext();
    const [field] = useField(props);
    const { startDateName, endDateName } = props;
    const { label, minDate, maxDate, startDate, endDate, afterChange, dateFormat, disabled, ...inputProps } = props;
    const { onChange, name, value, isInvalid, errors, onBlur, ...pickerProps } = props;

    var formato;
    if (dateFormat) {
        formato = dateFormat;
    } else {
        formato = 'DD/MM/yyyy';
    }

    useEffect(() => {
        const timer = setInterval(function () {
            console.log("Cargando calendar...");
            let inputCalendar = document.getElementsByClassName("react-datepicker__month-container");
            if (inputCalendar) {
                console.log("inputCalendar if");
                let buttonClear = document.createElement("input");
                buttonClear.setAttribute('type', "button");
                buttonClear.setAttribute('value', "Limpiar");
                buttonClear.setAttribute('class', 'btn-sm ms-2 waves-effect waves-light btn btn-secondary')
                buttonClear.innerHTML = "Limpiar";
                //inputCalendar.appendChild(buttonClear);
                buttonClear.onclick = onClear;
                clearInterval(timer);
            }
        }, 200);
    }, []);

    const onClear = () => {
        console.log("Clear date");
    }


    const showRageSelected = (range) => {

        let startDate = range[0];
        let endDate = range[1];

        if ((startDate === null || startDate === "") &&
            (endDate === null || endDate === "")) {
            setFieldValue(field.name, "");
            setFieldValue(startDateName, "");
            setFieldValue(endDateName, "");
        } else {
            setFieldValue(startDateName, moment(startDate).toDate());
            setFieldValue(endDateName, endDate === null ? null : moment(endDate).endOf("day").toDate());
            let startDateFormat = startDate === null ? "" : moment(startDate).format(formato);
            let endDateFormat = endDate === null ? "" : " - " + moment(endDate).format(formato);
            setFieldValue(field.name, startDateFormat + endDateFormat);
            
        }
    }

    const CustomInput = forwardRef((propsInput, ref) => (
        <Form.Group className='mb-3'>
            <Form.Label>{label}</Form.Label>
            <InputGroup>
                <Form.Control type="text"
                    ref={ref}
                    {...field}
                    {...inputProps}
                    value={propsInput.value}
                    readOnly
                    disabled
                    style={disabled ? null : { "backgroundColor": "#fff" }}
                />
                <InputGroup.Text className='input-icon-end' onClick={propsInput.onClick} style={{ cursor: `${disabled ? 'unset' : 'pointer'}` }}>
                    <FontAwesomeIcon icon={faCalendarAlt} />
                </InputGroup.Text>
                <Form.Control.Feedback type="invalid">
                    {getIn(props.errors, props.name)}
                </Form.Control.Feedback>
            </InputGroup>
        </Form.Group>
    ));

    return (
        <DatePicker
            {...pickerProps}
            selectsRange={true}
            startDate={startDate}
            endDate={endDate}
            popperContainer={Portal}
            locale="es"
            peekNextMonth
            showMonthDropdown
            showYearDropdown
            onChange={update => {
                console.log("update => ", update);
                showRageSelected(update);
            }}
            customInput={<CustomInput />}
            onKeyDown={e => {
                console.log("e===>", e.key);
                /* if (e.key === 'Backspace') {
                    e.preventDefault();
     
                    console.log("is Backspace");
                } */
            }}
        /* excludeDates={[new Date()]} */
        />
    );
}