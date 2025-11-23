import React, { memo } from "react";
import { Col, Row } from "react-bootstrap";
import { TablaEditable } from "../../../../../components/table/TablaEditable";


function TableComponent({ dataTable, columns, setDataTable, providerTableTitle, tableReference = null, }) {
  return (
    <Row>
      <Col>
        <TablaEditable
          ref={tableReference}
          dataTable={dataTable}
          columns={columns}
          header={providerTableTitle}
          hasPagination={true}
          isFiltered={true}
          autoResetPageIndex={false}
          onUpdate={(data, prop, value)=>{
            setDataTable(data, prop, value);
          }}
        />
      </Col>
    </Row>
  );
}

export default memo(TableComponent, (prevProps, nextProps) => {
  return (
    prevProps.dataTable === nextProps.dataTable
  );
});
