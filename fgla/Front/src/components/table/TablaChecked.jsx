import React, { useState, useContext } from 'react';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Table from 'react-bootstrap/Table';
import Form from 'react-bootstrap/Form';
import Pagination from 'react-bootstrap/Pagination';
import chunk from 'lodash/chunk';
import values from 'lodash/values';
import styled from 'styled-components';
// import MoneyFormat from '../MoneyFormat';

const TableContainer = styled.div`
  & {
    table th,
    table td {
      font-size: 12px;
      text-align: center;
    }
  }
`;

const getIndexes = (array = [], format) => {
  const indexes = [];
  array.forEach(({ formatter }, index) => formatter === format && indexes.push(index));
  return indexes;
};
const Tabla = (props) => {
  const { headers, data } = props;

  const [rowsPerPage, setRowsPerPage] = useState(10);
  const [currentPage, setCurrentPage] = useState(0);
  // const money = useContext(MoneyFormat);
  // const columnsTypeMoney = getIndexes(props.headers, 'money');
  const key = headers.map(({ dataField }) => dataField); // Este nombre de keys esta mal cambialo porfa
  const chunks = chunk(data, rowsPerPage);
  const lastPage = chunks.length - 1;
  const handleCurrentPage = (index) => () => {
    setCurrentPage(index);
  };
  const handleRowsPerPage = (event) => {
    const { value } = event.target;
    setCurrentPage(0);
    setRowsPerPage(parseInt(value));
  };
  const getOrderDataRow = (row, key, idKeyName) => {
    const orderValues = {};
    for (let i = 0; i < key.length; i++) {
      const k = key[i];
      if (k !== idKeyName) {
        orderValues[k] = row[k];
      }
    }
    return values(orderValues);
  };
  return (
    <>
      <Row className="row">
        <Col md="12">
          <TableContainer>
            <Table className="text-center" bordered striped>
              <thead>
                <tr>
                  <th>
                    <input
                      type="checkbox"
                      checked={props.selecChekedAll}
                      onChange={props.selecAll}
                      name="check"
                    />
                  </th>
                  {props.headers.map(({ dataField, text }) => (
                    <th key={dataField} id={dataField}>
                      {text}
                    </th>
                  ))}
                </tr>
              </thead>
              <tbody>
                {chunks &&
                  chunks.length > 0 &&
                  chunks[currentPage].map((r, index) => {
                    const row = getOrderDataRow(r, key, props.idKeyName);
                    return (
                      <tr key={r[props.idKeyName]}>
                        <th>
                          <input
                            checked={
                              props.checkets && props.checkets[r.idMovimiento]
                                ? props.checkets[r.idMovimiento].checked
                                : false
                            }
                            type="checkbox"
                            onChange={props.agregarCheck(r.idMovimiento)}
                            name="datoNuevo"
                          />
                        </th>
                        {row.map((cell, index) => {
                          return <td key={index}>{cell}</td>;
                        })}
                      </tr>
                    );
                  })}
                {chunks && chunks.length == 0 && (
                  <tr>
                    <td colSpan={headers.length + 1}>
                      <b>No se encontraron registros</b>
                    </td>
                  </tr>
                )}
              </tbody>
            </Table>
          </TableContainer>
        </Col>
      </Row>
      <Row className='justify-content-center mx-0 py-1'>
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
        <Col md="6">
          <Pagination className="float-end" style={{ color: '#000' }}>
            <Pagination.First disabled={currentPage === 0} onClick={handleCurrentPage(0)} />
            <Pagination.Prev
              disabled={currentPage === 0}
              onClick={handleCurrentPage(currentPage - 1)}
            />
            {chunks &&
              chunks.length > 0 &&
              chunks.map((item, index) => (
                <Pagination.Item
                  key={index}
                  onClick={handleCurrentPage(index)}
                  active={index === currentPage}
                >
                  {index + 1}
                </Pagination.Item>
              ))}
            <Pagination.Next
              disabled={currentPage === lastPage}
              onClick={handleCurrentPage(currentPage + 1)}
            />
            <Pagination.Last
              disabled={currentPage === lastPage}
              onClick={handleCurrentPage(lastPage)}
            />
          </Pagination>
        </Col>
      </Row>
    </>
  );
};

Tabla.defaultProps = {
  data: [],
  headers: [],
  idKeyName: 'id',
};

export default Tabla;
