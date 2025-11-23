import React, { useEffect, useRef } from 'react';
import { init, getInstanceByDom } from "echarts";
import * as echarts from 'echarts';

const ChartTime = ({ dataPlan }) => {

    var chartRef = useRef(null);
    var myChart;
    // nueva -----
    var option;
    var HEIGHT_RATIO = 0.7;
    const ITEMS_DEFAULT = 20;
    const DIM_TASK_NAME = 2;//nombreTarea
    const TASK_NAME = 3;// tooltip y
    var DIM_TIME_ARRIVAL = 3;//fechaInicioPlaneada
    var DIM_TIME_DEPARTURE = 4;//fechaFinPlanead
    var DIM_PERCENTAGE_COMPLETED = 10;//completado
    var _rawData;

    _rawData = {
        "parkingApron": {
            "dimensions": [
                "Name",
                "Type",
                "Near Bridge"
            ],
            "data": dataPlan
        },
        "flight": {
            "dimensions": [
                "Parking Apron Index",
                "Arrival Time",
                "Departure Time",
                "Flight Number",
                "VIP",
                "Arrival Company",
                "Departure Company",
                "Arrival Line",
                "Departure Line",
                "Report Time"
            ],
            "data": dataPlan.reverse()
        }
    };

    function makeOption() {
        return {
            tooltip: {
                backgroundColor: '#000',
                borderColor: '#000',
                padding: 4,
                textStyle: {
                    color: '#fff',
                    fontSize: 12,
                    fontWeight: '500',
                    fontFamily: 'Montserrat, sans-serif',
                },
                formatter: function (params) {
                    return params.value[2];
                }
            },
            dataZoom: [
                {
                    type: 'slider',
                    xAxisIndex: 0,
                    filterMode: 'weakFilter',
                    height: 12,
                    bottom: 8,
                    start: 0,
                    end: 100,
                    handleIcon:
                        'path://M10.7,11.9H9.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4h1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
                    handleSize: '100%',
                    showDetail: false
                },
                {
                    type: 'inside',
                    id: 'insideX',
                    xAxisIndex: 0,
                    filterMode: 'weakFilter',
                    start: 0,
                    end: 100,
                    zoomOnMouseWheel: false,
                    moveOnMouseMove: true
                },
                {
                    type: 'slider',
                    yAxisIndex: 0,
                    width: 12,
                    right: 10,
                    top: 7,
                    bottom: 20,
                    start: getZoomY(),
                    end: 100,
                    handleIcon:
                        'path://M10.7,11.9H9.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4h1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
                    handleSize: '100%',
                    showDetail: false
                },
                {
                    type: 'inside',
                    id: 'insideY',
                    yAxisIndex: 0,
                    start: getZoomY(),
                    end: 100,
                    zoomOnMouseWheel: false,
                    moveOnMouseMove: true,
                    moveOnMouseWheel: true
                }
            ],
            grid: {
                show: true,
                top: 20,
                bottom: 20,
                left: 70,
                right: 30,
                backgroundColor: '#fff',
                borderWidth: 0
            },
            xAxis: {
                type: 'time',
                position: 'top',
                axisLine: {
                    show: false
                },
                axisTick: {
                    lineStyle: {
                        color: '#929ABA'
                    }
                },
                axisLabel: {
                    color: '#929ABA',
                    inside: false,
                    align: 'center',
                }
            },
            yAxis: {
                axisTick: { show: false },
                splitLine: { show: false },
                axisLine: { show: false },
                axisLabel: { show: false },
                min: 0,
                max: _rawData.parkingApron.data.length
            },
            series: [
                {
                    id: 'tareaData',
                    type: 'custom',
                    renderItem: renderGanttItem,
                    dimensions: _rawData.flight.dimensions,
                    encode: {
                        x: [DIM_TIME_ARRIVAL, DIM_TIME_DEPARTURE],
                        tooltip: [DIM_TASK_NAME]
                    },
                    data: _rawData.flight.data,
                },
                {
                    type: 'custom',
                    renderItem: renderAxisLabelItem,
                    dimensions: _rawData.parkingApron.dimensions,
                    encode: {
                        x: -1,
                        y: TASK_NAME
                    },
                    data: _rawData.parkingApron.data.map((item, index) => {
                        return [item[0]].concat(item);
                    })
                }
            ]
        };
    }

    function renderGanttItem(params, api) {
        const categoryIndex = api.value(0); // id index de data original
        const plannedStartDate = api.coord([api.value(DIM_TIME_ARRIVAL), categoryIndex]);
        const plannedEndDate = api.coord([api.value(DIM_TIME_DEPARTURE), categoryIndex]);
        const barCompleted = plannedEndDate[0] - plannedStartDate[0];
        const barHeight = api.size([0, 1])[1] * HEIGHT_RATIO;
        const x = plannedStartDate[0];
        const y = plannedStartDate[1] - barHeight;
        const percentageCompleted = api.value(DIM_PERCENTAGE_COMPLETED) + '%';// texto dentro de la barra de avance
        const percentageCompletedWidth = echarts.format.getTextRect(percentageCompleted).width;
        const text =
            barCompleted > percentageCompletedWidth + 40 && x + barCompleted >= 180
                ? percentageCompleted : '';
        const rectNormal = clipRectByRect(params, {
            x: x,
            y: y,
            width: barCompleted,
            height: barHeight
        });
        const rectVIP = clipRectByRect(params, {
            x: x,
            y: y,
            width: getAvance(barCompleted, params, categoryIndex),
            height: barHeight
        });
        const rectText = clipRectByRect(params, {
            x: x,
            y: y,
            width: barCompleted,
            height: barHeight
        });
        return {
            type: 'group',
            children: [
                {
                    type: 'rect',
                    ignore: !rectNormal,
                    shape: rectNormal,
                    style: api.style({
                        fill: '#fff',
                        stroke: '#929ABA'
                    })
                },
                {
                    type: 'rect',
                    ignore: !rectVIP,
                    shape: rectVIP,
                    style: api.style({
                        fill: '#205e53',
                        text: text !== '0%' ? text : '',
                        textFill: '#fff',
                        align: 'right'
                    })
                },
                {
                    type: 'rect',
                    ignore: !rectText,
                    shape: rectText,
                    style: api.style({
                        fill: 'transparent',
                        stroke: 'transparent',
                        align: 'right'
                    })
                }
            ]
        };
    }

    function getZoomY() {
        let zoom = 0;
        let items = dataPlan.length;

        if (items <= ITEMS_DEFAULT) {
            zoom = 0;
        } else if (items > ITEMS_DEFAULT) {
            zoom = 100 - ((ITEMS_DEFAULT / items) * 100);
        }
        return zoom;
    }

    function getAvance(barLength, data, indexTask) {
        let tarea = dataPlan.find(element => element[0] === indexTask);
        return barLength * (tarea[DIM_PERCENTAGE_COMPLETED] / 100);
    }

    function renderAxisLabelItem(params, api) {
        var y = api.coord([0, api.value(0)])[1];
        if (y < params.coordSys.y + 5) {
            return;
        }
        return {
            type: 'group',
            position: [1, y],
            children: [
                {
                    type: 'path',
                    shape: {
                        d: 'M0 0 0-20 30-20C42-20 38-1 50-1L55-1 55 0Z',
                        x: 0,
                        y: -20,
                        width: 65,
                        height: 20,
                        layout: 'cover'
                    },
                    style: {
                        fill: '#D8D8D8'
                    }
                },
                {
                    type: 'text',
                    style: {
                        x: 24,
                        y: -3,
                        text: api.value(DIM_TASK_NAME),
                        textVerticalAlign: 'bottom',
                        textAlign: 'center',
                        textFill: '#727372',
                        FontFamily: 'Montserrat'
                    }
                }
            ]
        };
    }

    function clipRectByRect(params, rect) {
        return echarts.graphic.clipRectByRect(rect, {
            x: params.coordSys.x,
            y: params.coordSys.y,
            width: params.coordSys.width,
            height: params.coordSys.height
        });
    }

    useEffect(() => {
        if (chartRef.current !== null) {
            myChart = init(chartRef.current, null, {
                renderer: "svg",
                useDirtyRect: true
            });
            myChart.setOption((option = makeOption()));
        }

        function resizeChart() {
            myChart?.resize();
        }
        window.addEventListener("resize", resizeChart);

        return () => {
            myChart?.dispose();
            window.removeEventListener("resize", resizeChart);
        };
    }, [dataPlan]);


    useEffect(() => {
        if (chartRef.current !== null) {
            const chart = getInstanceByDom(chartRef.current);
            chart.setOption(option);
        }
    }, [option]);

    return <div ref={chartRef} style={{ width: "100%", height: dataPlan.length <= ITEMS_DEFAULT ? (dataPlan.length * 25) + 40 : 500 }} />;

}

export default ChartTime;
