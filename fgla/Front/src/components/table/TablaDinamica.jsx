import React, { useEffect, useState, useContext } from 'react';
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

const TablaDinamica = ({ headers, header, data, actionFns, idKeyName, idKeyLink, handleDoubleClick, hasPagination, onChangeStatus }) => {
  const [rowsPerPage, setRowsPerPage] = useState(15);
  const [currentPage, setCurrentPage] = useState(0);
  const [filteredData, setFilteredData] = useState([]);
  const [filterFileds, setFilterFields] = useState({});
  const [chunks, setChunks] = useState(chunk(data, rowsPerPage));
  const [lastPage, setLastPage] = useState(chunks.length - 1 || 0);
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
    let sortedData = [...data];
    if (sortField) {
      sortedData = sortedData.sort((a, b) => {
        let valueA = a[sortField];
        let valueB = b[sortField];

        if (valueA == null || valueA === "") valueA = 0;
        if (valueB == null || valueB === "") valueB = 0;
        if (sortField === 'id' || !isNaN(valueA) && !isNaN(valueB)) {
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
    setFilteredData(sortedData);
    setChunks(chunk(sortedData, rowsPerPage));
    setLastPage(chunks.length - 1);
  }, [data, sortField, sortOrder, rowsPerPage]);

  useEffect(() => {
    setCurrentPage(data.length === 0 ? 0 : currentPage);
    setChunks(chunk(data, rowsPerPage));
    setLastPage(chunks.length - 1);
  }, [data, rowsPerPage]);

  useEffect(() => {
    setLastPage(chunks.length - 1);
  }, [chunks]);

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

  const handleCurrentPage = (index) => () => {
    setCurrentPage(index);
  };

  const handleRowsPerPage = (e) => {
    setCurrentPage(0);
    setRowsPerPage(parseInt(e.target.value));
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

  const renderRows = (chunks, currentPage) => {
    return chunks[currentPage].map((r, ri) => {
      const row = getOrderDataRow(r, keys, idKeyName);
      return (
        <tr key={r[idKeyName] + ":" + ri} onDoubleClick={handleDoubleClick(r[idKeyName])}>
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
              <td key={(ri * 10) + index} className={isMoneyFormat ? 'text-end pe-4' : ''}>
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

  const handleEnterFilters = (e) => {
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
      <Col md={2} >
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
            onClick={handleCurrentPage(0)} />
          <Pagination.Prev
            disabled={!currentPage || !chunks.length}
            onClick={handleCurrentPage(currentPage - 1)}
          />
          <Pagination.Item
            disabled={true}
          >
            {currentPage + 1 + " de " + (chunks.length === 0 ? 1 : chunks.length)}
          </Pagination.Item>
          <Pagination.Next
            disabled={currentPage === lastPage || !chunks.length}
            onClick={handleCurrentPage(currentPage + 1)}
          />
          <Pagination.Last
            disabled={currentPage === lastPage || !chunks.length}
            onClick={handleCurrentPage(lastPage)}
          />
        </Pagination>
      </Col>
    </Row>
  }

  const handleOrganizedFilter = (dataField) => () => {
    const isAscending = sortField === dataField && sortOrder === 'asc';
    setSortOrder(isAscending ? 'desc' : 'asc');
    setSortField(dataField);
  };

  return (
    <>
      <Row className="row">
        <Col md="12">
          <Card className='card-table'>
            {header && <Card.Header className='table-header text-center'>{header}</Card.Header>}
            {hasPagination && <RenderPagination positionPagination={header ? 'paginationTop-header' : 'paginationTop'} />}
            <TableContainer>
              <Table id='tablerender' className="text-center mb-0" responsive hover striped>
                <thead>
                  <tr className="bg-primary">
                    {headers.map(({ dataField, text, width, filter, sort, tooltipMessage, }) => (
                      <th className='px-3' style={width || null} key={dataField} id={dataField}>
                        <HeaderTable
                          name={dataField}
                          text={text}
                          sort={sort}
                          filter={filter}
                          tooltipMessage={tooltipMessage}
                          handleSort={handleOrganizedFilter}
                          handleFilter={handleEnterFilters}
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

TablaDinamica.defaultProps = {
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

export default TablaDinamica;
