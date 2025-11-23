import { TextField, FormCheck } from "../../../components/formInputs";
import showMessage from "../../../components/Messages";
import { validateString, validateNumber } from "../../../functions/utils";
import * as yup from "yup";

export const renderInput = (props, handleChange, setFieldValue, values, errors, touched) => {
    let inputProps = {
        ...props,
        onChange: handleChange,
        value: values[props.name],
        className: touched[props.name] && (errors[props.name] ? 'is-invalid' : 'is-valid'),
        helperText: touched[props.name] ? errors[props.name] : ''
    }

    let inputs = {
        String: <TextField {...{ ...inputProps, onChange: (e) => { alphanumericValidation(e, setFieldValue) } }} />,
        Integer: <TextField {...{ ...inputProps, onChange: (e) => { numericValidation(e, setFieldValue) } }} />,
        Boolean: <FormCheck {...inputProps} />
    }

    return inputs[props.tipo];
};

export const getMetadataForm = (data) => {
    let newData = Object.entries(data).map(([key, value]) => ({
        ...value,
        name: key,
        orden: parseInt(value.orden),
        label: value.nombreFront + "*:",
        value: ""
    }));

    let metadata = newData.sort((a, b) => a.orden - b.orden);
    return metadata;
};

export const getInitialValues = (data) => {
    let initialValues = {};
    data.forEach(obj => {
        if (validaMostrarCampo(obj))
            initialValues = {
                ...initialValues,
                [obj.name]: ""
            }

        if ('estatus' in initialValues)
            initialValues.estatus = true;
    });

    return initialValues;
};

export const getValidationSchema = (fields) => {
    const schemaFields = {};

    Object.keys(fields).forEach(key => {
        schemaFields[key] = yup.string().required('Dato requerido');
    });

    return yup.object().shape(schemaFields);
}

export const alphanumericValidation = (e, setFieldValue) => {
    let { name, value } = e.target;

    // Se quita validación de alafanumerico en catalogos por indicación de Maribel
    // ya que no permite avanzar en las pruebas de usuario
    /* if (validateString(value)) {
        setFieldValue(name, value);
    } */

    setFieldValue(name, value);
}

export const numericValidation = (e, setFieldValue) => {
    let { name, value } = e.target;

    if (validateNumber(value)) {
        setFieldValue(name, value);
    }
}

export const validaMostrarCampo = (campo) => {
    let mostrar = true;
    if ('vista' in campo) {
        if (campo.vista === 'false') {
            mostrar = false;
        }
    } else if (campo.tipo === "catalogo") {
        mostrar = false;
    }
    return mostrar;
};

export const getHeadersTable = (data, idKeyTable) => {
    let headers = [{ dataField: idKeyTable, text: "ID", filter: true, sort: true, width: { width: "100px" } }];
    data.forEach(obj => {
        if (obj.name === "estatus") {
            let newHeader = {
                dataField: obj.name,
                text: obj.nombreFront,
                formatter: "switch"
            }
            headers.push(newHeader)
        } else if (obj.tipo === "String" || obj.tipo === "Integer" || obj.tipo === "Date") {
            let newHeader = {
                dataField: obj.name,
                text: obj.nombreFront,
                filter: true,
                sort: true
            }
            headers.push(newHeader);
        }
    })
    headers.push({ dataField: "acciones", text: "Acciones" });
    return headers;
};

export const adminRequieredValidations = async (data) => {

    let dataErros = false;

    let schema = yup.object().shape({
        administrador: yup.string().required('Dato requerido'),
        fechaInicioVigencia: yup.string().required('Dato requerido')
    });

    let resultados = await Promise.all(data.map(async (obj) => {
        try {
            await schema.validate(obj, { abortEarly: false });
            delete obj.errors;
            return obj;
        } catch (err) {
            dataErros = true;
            return {
                ...obj,
                errors: err.inner.reduce((acc, error) => {
                    acc[error.path] = error.message;
                    return acc;
                }, {})
            };
        }
    }));

    return { dataErros, resultados };
};

export const validarAdminsEstatus = (admins) => {
    let adminsActive = admins.filter((admin) => admin?.estatus === true);

    return adminsActive.length;
};

export const errorValidations = (getMessageExists, error) => {
    let errorMessage = error?.response?.data?.mensaje[0];
    if (getMessageExists(errorMessage)) {
        showMessage(errorMessage);
    } else {
        showMessage("Ocurrió un error");
    }
};
