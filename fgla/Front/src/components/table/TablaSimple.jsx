import React from 'react';
import '../../styles/estilos-table.css';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Table from 'react-bootstrap/Table';
import styled from 'styled-components';
import values from 'lodash/values';

const TableContainer = styled.div`
  & {
    table th,
    table td {
      font-size: 12px;
      text-align: center;
    }
  }
`;

const TablaSimple = ({ headers, data, idKeyName }) => {
  const keys = headers.map(({ dataField }) => dataField);

  const getOrderDataRow = (row, keys, idKeyName) => {
    const orderValues = {};
    for (let i = 0; i < keys.length; i++) {
      const k = keys[i];
      if (k !== idKeyName) {
        orderValues[k] = row[k];
      }
    }
    return values(orderValues);
  };

  return (
    <Row className="mb-2">
      <Col md={12}>
        <TableContainer>
          <Table responsive="lg" bordered>
            <thead>
              <tr className="bg-light">
                {headers.map(({ dataField, text }) => (
                  <th className='px-5' key={dataField} id={dataField}>
                    {text}
                  </th>
                ))}
              </tr>
            </thead>
            <tbody>
              {data &&
                data.length > 0 &&
                data.map((r) => {
                  const row = getOrderDataRow(r, keys, idKeyName);
                  return (
                    <tr key={r[idKeyName]}>
                      {row.map((cell, index) => {
                        return <td key={index}>{cell}</td>;
                      })}
                    </tr>
                  );
                })}
            </tbody>
          </Table>
        </TableContainer>
      </Col>
    </Row>
  );
};

TablaSimple.defaultProps = {
  headers: [],
  data: [{}],
  idKeyName: 'id',
};

export default TablaSimple;
