import React, { useState, useEffect } from 'react';
import { Container, } from 'react-bootstrap';
import { MainTitle, Loader } from "../../../components";
import CatalogosGenerales from "./generales/CatalogosGenerales";
import CatalogosComplementarios from "./complementarios/CatalogosComplementarios";
import { getAllCatalogs, getAllComplementarios } from './Catalogos';
import Authorization from '../../../components/Authorization';
import { useToast } from "../../../hooks/useToast";
import showMessage from '../../../components/Messages';
import { logError } from '../../../components/utils/logError.js';

const FormularioDinamico = () => {
    const [loading, setLoading] = useState(true);
    const [generalesOptions, setGeneralesOptions] = useState([]);
    const [complementariosOptions, setComplementariosOptions] = useState([]);


    useEffect(() => {
        getDataInit();
    }, []);

    const getDataInit = () => {
        Promise.all([getAllCatalogs(), getAllComplementarios()])
            .then(([getAllCatalogsResp, getAllComplementariosResp]) => {
                setGeneralesOptions(getAllCatalogsResp.data);
                setComplementariosOptions(getAllComplementariosResp.data);
                setLoading(false);
            })
            .catch((error) => {
                logError("error => ", error);
                setLoading(false);
                showMessage("Ocurrió un error");
            });
    };

    return (

        <Container className='mt-3 px-3'>

            {loading && <Loader />}

            <MainTitle title="Catálogos" />

            <Authorization process={"CAT_GRAL_ADMIN"}>
                <CatalogosGenerales catalogosOptions={generalesOptions} />
            </Authorization>

            <Authorization process={"CAT_COMP_ADMIN"}>
                <CatalogosComplementarios catalogosOptions={complementariosOptions} />
            </Authorization>

        </Container>
    );
}

export default FormularioDinamico;