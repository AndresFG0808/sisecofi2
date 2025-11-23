import { useEffect } from "react";
import { Row, Col, FormCheck } from "react-bootstrap";
import ChartTime from "./Components/Chart";
import { useState } from "react";

const PlanGrafica = ({ data }) => {
    const [dataChart, setDataChart] = useState([]);
    const [allTasks, setAllTasks] = useState(false);

    useEffect(() => {
        if (data.length > 0) {
            //setDataChart(processDataChart(data[0]));

            processDataChart(cleanData(data[0]));
        } else {
            setDataChart([]);
        }
    }, [data, allTasks]);

    const processDataChart = (data) => {
        console.log("processDataChart => ", data);

        let dataArray = [];

        let keys = [
            'idTarea',
            'nombreTarea',
            'fechaInicioPlaneada',
            'fechaFinPlaneada',
            'duracionPlaneada',
            'fechaInicioReal',
            'fechaFinReal',
            'duracionReal',
            'planeado',
            'completado'
        ];

        let sorted = data.sort((a, b) => a.idTarea - b.idTarea);

        sorted.forEach((obj, index) => {
            //console.log("forEach > obj => ", obj);
            let objClean = [(sorted.length - 1) - index];
            keys.forEach(key => {
                objClean.push(key.includes('fecha') ? getMilisDate(key, obj[key]) : obj[key]);
            });
            dataArray.push(objClean);
        })

        console.log("dataArray => ", dataArray);
        setDataChart(dataArray);
    }

    const getMilisDate = (key, date) => {

        if (key.toLowerCase().includes('fin')) {
            return dateToMarktime(date, 'final');
        } if (key.toLowerCase().includes('inicio')) {
            return dateToMarktime(date, 'inicial');
        }
    }

    const dateToMarktime = (date, dateType) => {
        let dateTypeStart = 'inicial';
        let endDate = "T23:59:59";

        if (date) {
            let arrayDate = date && date.split('/');
            let newStringDate = arrayDate[2] + '-' + arrayDate[1] + '-' + arrayDate[0];
            let stringDate = dateType === dateTypeStart ? newStringDate : newStringDate + endDate;

            let dateTime = new Date(stringDate).getTime();
            return dateTime;
        } else {
            return "";
        }
    }

    const cleanData = (data) => {
        const resultado = [];
        function recorrer(obj) {
            if (allTasks) {
                resultado.push(obj);
            } else if (obj.nivelEsquema === 0 || obj.nivelEsquema === 1) {
                resultado.push(obj);
            }

            if (obj.subRows) {
                obj.subRows.forEach(recorrer);
            }
        }
        recorrer(data);
        return resultado;
    }

    return (
        <>
            <Row className='mb-4'>
                <Col md={2} className='mb-3'>
                    Mostrar todas las tareas:&nbsp;&nbsp;
                    <FormCheck
                        className='d-inline'
                        checked={allTasks}
                        onChange={() => setAllTasks(!allTasks)}
                    />
                </Col>
                <Col md={8}>
                    <Row className='justify-content-center'>
                        <Col md={2} className='text-center'>
                            % Completado
                            <svg viewBox="0 0 100 100" width="120" height="25" style={{ background: '#205e53' }}></svg>
                        </Col>
                        <Col md={2} className='text-center'>
                            Fecha planeada
                            <svg viewBox="0 0 100 100" width="120" height="25" style={{ background: '#FFF', border: '2px solid #929ABA' }}></svg>
                        </Col>
                    </Row>
                </Col>
            </Row>

            <Row>
                <Col md={12}>
                    {dataChart.length > 0 && <ChartTime dataPlan={dataChart} />}
                </Col>
            </Row>
            <hr />
        </>
    );

}

export default PlanGrafica;