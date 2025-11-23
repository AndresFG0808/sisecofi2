import React, { useState, useContext, useEffect } from 'react';
import { Row, Col, Card, Form, Table, Pagination } from 'react-bootstrap';
import '../../styles/estilos-table.css';
import chunk from 'lodash/chunk';
import values from 'lodash/values';
import Action from '../Action';
import styled from 'styled-components';
import MoneyFormat from '../MoneyFormat';
import SwitchButton from '../buttons/SwitchButton';
import HeaderTable from '../formInputs/HeaderTable';

const TableContainer = styled.div`
  & {
    table th,
    table td {
      font-size: 12px;
      text-align: center;
    }
  }
`;
const radio = {
    display: 'inlineBlock',
    width: 17,
    height: 17,
    verticalAlign: 'middle',
};

const getIndexes = (array = [], format) => {
    const indexes = [];
    array.forEach(({ formatter }, index) => formatter === format && indexes.push(index));
    return indexes;
};

const OPTIONS_MONEY = { style: 'currency', currency: 'USD' };
const FORMAT_MONEY = new Intl.NumberFormat('en-US', OPTIONS_MONEY);
const FORMAT_NUMBER = new Intl.NumberFormat('en-US');

const TablaPaginada = ({ headers, header, data, actionFns, idKeyName, idKeyLink, handleDoubleClick, edithByUser, hasPagination, onChangeStatus, updateData, pageable }) => {
    const { totalPages, totalElements, pageNumber, size } = pageable;
    const [rowsPerPage, setRowsPerPage] = useState(size);
    const [currentPage, setCurrentPage] = useState(data.length > 0 ? pageNumber : 0);
    const [filteredData, setFilteredData] = useState([]);
    const [filterFileds, setFilterFields] = useState({});
    const [chunks, setChunks] = useState(chunk(data, rowsPerPage));
    const [lastPage, setLastPage] = useState(totalPages - 1);
    const money = useContext(MoneyFormat);
    const keys = headers.map(({ dataField }) => dataField);
    const columnsTypeMoney = getIndexes(headers, 'money');
    const columnsTypeNumber = getIndexes(headers, 'number');
    const columnsTypePercentage = getIndexes(headers, 'percentage');
    const columnsTypeCatalog = getIndexes(headers, 'catalog');
    const columnsTypeBoolean = getIndexes(headers, 'boolean');
    const columnsTypeSwitch = getIndexes(headers, 'switch');
    const columnsTypeCustom = getIndexes(headers, 'custom');
    const [sortField, setSortField] = useState(null);
    const [sortOrder, setSortOrder] = useState('asc');

    useEffect(() => {
        //PUEDE DAR CONFLICTO CUANDO SE FILTRA Y DESPUES DE ORDENA
        //AL FILTRAR Y DESPUES ORDERNAR REGRESA TODA LA INFORMACION NO APLICA EL ORDENAMIENTO SOLO A LA INFORMACION ACTUAL

        let sortedData = [...data];
        if (sortField) {
            sortedData = sortedData.sort((a, b) => {
                let valueA = validarDato(a[sortField]);
                let valueB = validarDato(b[sortField]);

                if (!isNaN(valueA) && !isNaN(valueB)) {
                    return sortOrder === 'asc' ? valueA - valueB : valueB - valueA;
                } else if (isValidDate(valueA) && isValidDate(valueB)) {
                    const dateA = parseDate(valueA);
                    const dateB = parseDate(valueB);
                    return sortOrder === 'asc' ? dateA - dateB : dateB - dateA;
                } else {
                    return sortOrder === 'asc'
                        ? String(valueA).localeCompare(String(valueB))
                        : String(valueB).localeCompare(String(valueA));
                }
            });
        }

        setFilteredData(sortedData);//porque se setea sortedData? ahí se guarda lo filtrado no ordenado
        setChunks(chunk(sortedData, rowsPerPage));
        setLastPage(chunks.length - 1);
        setRowsPerPage(rowsPerPage);
        setCurrentPage(data.length === 0 ? 0 : currentPage);
        setChunks(chunk(sortedData, rowsPerPage));
        setLastPage(totalPages - 1);
    }, [data, sortField, sortOrder, rowsPerPage, currentPage, totalPages]);

    useEffect(() => {
        setRowsPerPage(size);
        setCurrentPage(data.length > 0 ? pageNumber : 0);
        setLastPage(totalPages - 1);
    }, [pageable]);

    const validarDato = (value) => {
        if (value === undefined || value === null) {
            return 0;
        } else if (!isNaN(Number(value))) {
            return Number(value);
        }
        return value;
    }

    const handleChangePage = (index) => () => {
        setCurrentPage(index);
        let values = {
            size: rowsPerPage,
            page: index
        }
        updateData(values);
    };

    const isValidDate = (value) => {
        if (typeof value !== 'string') return false;
        const [day, month, year] = value.split('/');
        return !isNaN(Date.parse(`${month}/${day}/${year}`));
    };

    const parseDate = (value) => {
        if (typeof value !== 'string') return new Date(0);
        const [day, month, year] = value.split('/');
        return new Date(`${year}-${month}-${day}`);
    };

    const handleRowsPerPage = (e) => {
        let elements = parseInt(e.target.value);
        let values = {
            size: elements,
            page: 0
        };

        setCurrentPage(0);
        setRowsPerPage(elements);
        updateData(values);
    };

    const getOrderDataRow = (row, keys, idKeyName) => {
        const orderValues = {};
        for (let i = 0; i < keys.length; i++) {
            const k = keys[i];

            if (k !== idKeyName || k === idKeyLink) {
                orderValues[k] = row[k];
            }

            if (k === 'acciones') {
                orderValues[k] = getActions(row[k], row[idKeyName]);
            }

            if (k === 'seleccionar') {
                orderValues[k] = (
                    <input
                        type="radio"
                        id={'select_' + [idKeyName]}
                        name="select"
                        value={[idKeyName]}
                        onClick={actionFns.handleSelect(row[idKeyName])}
                        style={radio}
                    />
                );
            }
        }
        return values(orderValues);
    };

    const getActions = (actions, id) => {
        const { handleShow, handleEdit, handleDelete, handleDownload } = actionFns;
        const Component = (
            <>
                {actions.download && <Action variant="download" onClick={handleDownload(id)} />}
                {actions.show && <Action variant="show" onClick={handleShow(id)} />}
                {actions.edit && <Action variant="edit" onClick={handleEdit(id)} />}
                {actions.remove && <Action variant="delete" onClick={handleDelete(id)} />}
            </>
        );
        return Component;
    };

    const renderRows = (chunks) => {
        return chunks[0].map((r) => {
            const row = getOrderDataRow(r, keys, idKeyName);
            return (
                <tr key={r[idKeyName]} onDoubleClick={handleDoubleClick(r[idKeyName])}>
                    {row.map((cell, index) => {
                        const isMoneyFormat = columnsTypeMoney.indexOf(index) !== -1;
                        const isNumberFormat = columnsTypeNumber.indexOf(index) !== -1;
                        const isPercentageFormat = columnsTypePercentage.indexOf(index) !== -1;
                        const isCatalog = columnsTypeCatalog.indexOf(index) !== -1;
                        const isBoolean = columnsTypeBoolean.indexOf(index) !== -1;
                        const isSwitch = columnsTypeSwitch.indexOf(index) !== -1;
                        const isCustom = columnsTypeCustom.indexOf(index) !== -1;

                        const displayValue = getDisplayValue(cell, isMoneyFormat, isNumberFormat, isPercentageFormat, isCatalog, isBoolean);

                        return (
                            <td key={index} className={isMoneyFormat ? 'text-end pe-4' : ''}>
                                {
                                    isMoneyFormat ? renderMoney(displayValue) :
                                        isSwitch ? renderSwitch(r[idKeyName], displayValue) :
                                            isCustom ? headers[index].customFn(r[idKeyName], displayValue) : displayValue
                                }
                            </td>
                        );
                    })}
                </tr>
            );
        });
    };

    const getDisplayValue = (value, isMoneyFormat, isNumberFormat, isPercentageFormat, isCatalog, isBoolean) => {
        if (isMoneyFormat) {
            return FORMAT_NUMBER.format(value);
        }

        if (isNumberFormat) {
            return FORMAT_NUMBER.format(value);
        }

        if (isPercentageFormat) {
            return `${value} %`;
        }

        if (isCatalog && value) {
            return value['valor'];
        }

        if (isBoolean) {
            return value ? 'Si' : 'No';
        }
        return value;
    };

    const renderMoney = (value) => {
        return <>
            <span className="money-signo">{"$"}</span>
            <span className="money-cantidad">{value}</span>
        </>
    }

    const renderSwitch = (id, value) => {
        return <SwitchButton value={value} onChange={onChangeStatus(id)} />;
    }

    const handleSortData = (dataField) => () => {
        setSortOrder((prevSortOrder) => (sortField === dataField && prevSortOrder === 'asc' ? 'desc' : 'asc'));
        setSortField(dataField);
    };

    const handleFilterData = (e) => {
        let filterKey = e.target.name;
        let filterValue = e.target.value;
        let filters = { ...filterFileds, [filterKey]: filterValue };

        if (filterValue.trim() === "") {
            delete filters[filterKey];
        }

        setFilterFields(filters);
        let dataToFilter = [];
        dataToFilter = e.key === 'Backspace' ? [...data] : filteredData.length > 0 ? [...filteredData] : [...data];

        for (let [key, value] of Object.entries(filters)) {
            dataToFilter = dataToFilter.filter(item => item[key] && item[key].toString().toLowerCase().includes(value.toLowerCase()))
        }

        setChunks(chunk(dataToFilter, rowsPerPage));
        setLastPage(chunks.length - 1)
        setFilteredData(dataToFilter);
        setCurrentPage(0);
    }

    const RenderPagination = ({ positionPagination }) => {
        return <Row className={'justify-content-center mx-0 py-1 ' + positionPagination}>
            <Col md={2}>
                <Form.Select
                    value={rowsPerPage}
                    onChange={handleRowsPerPage}
                    style={{ width: '65px' }}
                >
                    <option value="15">15</option>
                    <option value="50">50</option>
                    <option value="100">100</option>
                </Form.Select>
            </Col>
            <Col md={5}>
                <Pagination className="m-0 justify-content-center" style={{ color: '#000' }}>
                    <Pagination.First
                        disabled={!currentPage || !chunks.length}
                        onClick={handleChangePage(0)} />
                    <Pagination.Prev
                        disabled={!currentPage || !chunks.length}
                        onClick={handleChangePage(currentPage - 1)}
                    />
                    <Pagination.Item
                        disabled={true}
                    >
                        {currentPage + 1 + " de " + (chunks.length === 0 ? 1 : pageable.totalPages)}
                    </Pagination.Item>
                    <Pagination.Next
                        disabled={currentPage === lastPage || !chunks.length}
                        onClick={handleChangePage(currentPage + 1)}
                    />
                    <Pagination.Last
                        disabled={currentPage === lastPage || !chunks.length}
                        onClick={handleChangePage(lastPage)}
                    />
                </Pagination>
            </Col>
        </Row>
    }

    return (
        <>
            <Row className="row">
                <Col md="12">
                    <Card className='card-table'>
                        {
                            header && <Card.Header className='table-header text-center'>{header}</Card.Header>
                        }
                        {hasPagination && <RenderPagination positionPagination={header ? 'paginationTop-header' : 'paginationTop'} />}
                        <TableContainer>
                            <Table className="text-center mb-0" responsive hover striped>
                                <thead>
                                    <tr className="bg-primary">
                                        {headers.map(({ dataField, text, width, filter, sort }) => (
                                            <th className='px-3' style={width || null} key={dataField} id={dataField}>
                                                <HeaderTable
                                                    name={dataField}
                                                    text={text}
                                                    sort={sort}
                                                    filter={filter}
                                                    handleSort={handleSortData}
                                                    handleFilter={handleFilterData}
                                                />
                                            </th>
                                        ))}
                                    </tr>
                                </thead>
                                <tbody>
                                    {chunks && chunks.length > 0 && renderRows(chunks, currentPage)}
                                    {chunks && chunks.length === 0 && (
                                        <tr>
                                            <td colSpan={headers.length}>
                                                <b>No se encontraron registros</b>
                                            </td>
                                        </tr>
                                    )}
                                </tbody>
                            </Table>
                        </TableContainer>
                        {hasPagination && <RenderPagination positionPagination='paginationBottom' />}
                    </Card>
                </Col>
            </Row>
        </>
    );
};

TablaPaginada.defaultProps = {
    data: [],
    headers: [],
    idKeyName: 'id',
    footerAmount: '0',
    hasFooter: false,
    actionFns: {
        handleShow: () => { },
        handleEdit: () => { },
        handleDelete: () => { },
        handleDownload: () => { },
        handleSelect: () => { },
    },
    handleDoubleClick: () => { },
    hasPagination: true,
    pageable: {
        totalPages: 0,
        totalElements: 0,
        pageNumber: 0,
        size: 15
    }
};

export default TablaPaginada;
