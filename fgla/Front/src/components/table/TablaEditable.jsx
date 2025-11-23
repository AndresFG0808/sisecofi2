import React, {
  useEffect,
  useState,
  forwardRef,
  useImperativeHandle,
} from "react";
import {
  flexRender,
  getCoreRowModel,
  getFilteredRowModel,
  getPaginationRowModel,
  getExpandedRowModel,
  getSortedRowModel,
  useReactTable,
} from "@tanstack/react-table";
import { Table, Card, Row, Col } from "react-bootstrap";
import Form from "react-bootstrap/Form";
import Pagination from "react-bootstrap/Pagination";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faArrowUpWideShort } from "@fortawesome/free-solid-svg-icons";
import FilterField from "../formInputs/FilterField";
import "../../styles/estilos-table.css";
import {
  arrayMove,
  SortableContext,
  verticalListSortingStrategy,
} from "@dnd-kit/sortable";
import {
  closestCenter,
  DndContext,
  KeyboardSensor,
  MouseSensor,
  TouchSensor,
  useSensor,
  useSensors,
} from "@dnd-kit/core";
import { restrictToVerticalAxis } from "@dnd-kit/modifiers";
import { DraggableRow } from "./DraggableRow";

export const TablaEditable = forwardRef(
  (
    {
      dataTable = [],
      columns,
      hasPagination = false,
      header,
      onDelete = () => { },
      onUpdate = () => { },
      onGetRowData = () => { },
      subRows = "subRows",
      PaginationRow = PaginationRowFunction,
      autoResetPageIndex = false,
      manualPagination = false,
      onChangePagination = () => { },
      pageable = {},
      initialVisibility = {},
      colSpanSubCols = null,
      isDraggable,
      rowId = "",
    },
    ref
  ) => {
    const { totalPages, pageNumber, size } = pageable;
    const [rowsPerPage, setRowsPerPage] = useState(size);
    const [currentPage, setCurrentPage] = useState(
      dataTable.length > 0 ? pageNumber : 0
    );
    const [lastPage, setLastPage] = useState(totalPages - 1);
    const [sorting, setSorting] = useState([]);
    const [data, setData] = useState(dataTable);
    const [columnFilters, setColumnFilters] = useState([]);
    const [expanded, setExpanded] = useState({});
    const [columnVisibility, setColumnVisibility] = useState(initialVisibility);
    const [pagination, setPagination] = useState({
      pageIndex: 0,
      pageSize: 15,
    });

    useEffect(() => {
      setLastPage(totalPages);
    }, [totalPages]);

    useEffect(() => {
      setData(dataTable);
    }, [dataTable]);

    const dataIds = React.useMemo(
      () => data?.map((row) => row?.["UUID"]),
      [data]
    );

    const table = useReactTable({
      data,
      columns,
      state: {
        sorting,
        columnFilters,
        expanded,
        columnVisibility,
        pagination,
      },
      getSubRows: (row) => row?.[subRows],
      getRowId: (row) => row?.["UUID"],
      onColumnVisibilityChange: setColumnVisibility,
      onSortingChange: setSorting,
      onExpandedChange: setExpanded,
      onPaginationChange: setPagination,
      onColumnFiltersChange: setColumnFilters,
      getFilteredRowModel: getFilteredRowModel(),
      getSortedRowModel: getSortedRowModel(),
      getCoreRowModel: getCoreRowModel(),
      getPaginationRowModel: getPaginationRowModel(),
      getExpandedRowModel: getExpandedRowModel(),
      filterFromLeafRows: true,
      enableSubRowSelection: true,
      autoResetPageIndex,
      paginateExpandedRows: false,
      meta: {
        getDataTable: () => [...data],
        revertData: (data) => {
          setData([...data]);
        },
        setFullTable: (newTable) => {
          setData(newTable);
          onUpdate((prev) => newTable);
        },
        updateData: (rowIndex, columnId, value) => {
          const setUpdateFunction = (prev) =>
            prev.map((row, index) =>
              index === rowIndex
                ? { ...prev[rowIndex], [columnId]: value }
                : row
            );
          setData(setUpdateFunction);
          onUpdate(setUpdateFunction, columnId);
        },
        updateDatapromise: (rowIndex, columnId, value) => {
          return new Promise((resolve, reject) => {
            try {
              const updatedData = setData(setUpdateFunction(data));
              onUpdate(setUpdateFunction, columnId);
              resolve(updatedData);
            } catch (error) {
              reject(error);
            }
          });
          function setUpdateFunction(prevData) {
            return prevData.map((row, index) =>
              index === rowIndex ? { ...row, [columnId]: value } : row
            );
          }
        },
        removeRow: (rowIndex) => {
          const setFilterFunction = (prev) =>
            prev.filter((_row, index) => index !== rowIndex);

          setData(setFilterFunction);
          onDelete(setFilterFunction);
        },
        updateSubRows: (rowIndex, newRow) => {
          const setUpdateSubRowsFunction = (prev) =>
            prev.map((row, index) => (index === rowIndex ? newRow : row));
          setData(setUpdateSubRowsFunction);
          onUpdate(setUpdateSubRowsFunction);
        },
        getRowData: (row) => {
          onGetRowData(row);
        },
        removeRowById: (UUID) => {
          const setRemoveRowByIdFunction = (prev) =>
            prev.filter((row) => row.UUID !== UUID);
          setData(setRemoveRowByIdFunction);
          onDelete(setRemoveRowByIdFunction);
        },
        updateRowById: (UUID, newRow) => {
          const setEditByIdFunction = (prev) =>
            prev.map((row) => (row.UUID === UUID ? newRow : row));
          setData(setEditByIdFunction);
          onUpdate(setEditByIdFunction);
        },
      },
    });
    useImperativeHandle(
      ref,
      () => {
        return { setData, data, table, setColumnFilters, columnFilters };
      },
      []
    );

    function handleDragEnd(event) {
      const { active, over } = event;
      if (active && over && active.id !== over.id) {
        const sortFunction = (data) => {
          const oldIndex = dataIds.indexOf(active.id);
          const newIndex = dataIds.indexOf(over.id);
          return arrayMove(data, oldIndex, newIndex); //this is just a splice util
        };
        setData(sortFunction);
        onUpdate(sortFunction);
      }
    }
    const sensors = useSensors(
      useSensor(MouseSensor, {}),
      useSensor(TouchSensor, {}),
      useSensor(KeyboardSensor, {})
    );
    const handleRowsPerPage = (e) => {
      let elements = parseInt(e.target.value);
      let values = {
        size: elements,
        page: 0,
      };
      setLastPage(pageable.totalPages);
      setCurrentPage(0);
      setRowsPerPage(elements);
      onChangePagination(values);
      setPagination({
        pageIndex: 0,
        pageSize: elements,
      });
    };

    const handleChangePage = (index) => () => {
      setCurrentPage(index);
      let values = {
        size: rowsPerPage,
        page: index,
      };
      setLastPage(pageable.totalPages);
      onChangePagination(values);
    };

    const RenderPagination = ({ positionPagination }) => {
      return (
        <Row
          className={"justify-content-center mx-0 py-1 " + positionPagination}
        >
          <Col md={2}>
            <Form.Select
              value={rowsPerPage}
              onChange={handleRowsPerPage}
              style={{ width: "65px" }}
            >
              <option value="15">15</option>
              <option value="50">50</option>
              <option value="100">100</option>
            </Form.Select>
          </Col>
          <Col md={5}>
            <Pagination
              className="m-0 justify-content-center"
              style={{ color: "#000" }}
            >
              <Pagination.First
                disabled={!currentPage}
                onClick={handleChangePage(0)}
              />
              <Pagination.Prev
                disabled={!currentPage}
                onClick={handleChangePage(currentPage - 1)}
              />
              <Pagination.Item disabled={true}>
                {currentPage +
                  1 +
                  " de " +
                  (pageable.totalPages === 0 ? 1 : pageable.totalPages)}
              </Pagination.Item>
              <Pagination.Next
                disabled={
                  currentPage + 1 === lastPage || pageable.totalPages === 0
                }
                onClick={handleChangePage(currentPage + 1)}
              />
              <Pagination.Last
                disabled={
                  currentPage + 1 === lastPage || pageable.totalPages === 0
                }
                onClick={handleChangePage(pageable.totalPages - 1)}
              />
            </Pagination>
          </Col>
        </Row>
      );
    };

    if (isDraggable)
      return (
        <DndContext
          collisionDetection={closestCenter}
          modifiers={[restrictToVerticalAxis]}
          onDragEnd={handleDragEnd}
          sensors={sensors}
        >
          <Card className="card-table mb-3 editable-table">
            {/* Header */}
            {header ? (
              <Card.Header className="table-header text-center">
                {header}
              </Card.Header>
            ) : null}
            {hasPagination ? (
              <>
                {manualPagination ? (
                  <RenderPagination
                    positionPagination={
                      header ? "paginationTop-header" : "paginationTop"
                    }
                  />
                ) : (
                  <PaginationRow
                    table={table}
                    positionPagination={
                      header ? "paginationTop-header" : "paginationTop"
                    }
                  />
                )}
              </>
            ) : null}
            <Table className="text-center mb-0" responsive striped>
              <thead className="bg-editable-table">
                <>
                  {table.getHeaderGroups().map((headerGroup) => (
                    <tr key={headerGroup.id}>
                      {headerGroup.headers.map((header) => (
                        <th key={header.id} colSpan={header.colSpan}>
                          {typeof header.column.columnDef.header === "function"
                            ? header.column.columnDef.header()
                            : header.column.columnDef.header}
                          {/* Sorting  */}
                          {header.column.getCanSort() ? (
                            <span
                              className={`sorting-button ${header.column.getIsSorted()
                                  ? header.column.getIsSorted().toString()
                                  : "inactive"
                                }`}
                              onClick={header.column.getToggleSortingHandler()}
                            >
                              <FontAwesomeIcon
                                style={{
                                  paddingInline: "0.5rem",
                                  cursor: "pointer",
                                }}
                                icon={faArrowUpWideShort}
                              // el estilo del sorter
                              />
                            </span>
                          ) : null}
                          {/* Filtering */}
                          {header.column.getCanFilter() ? (
                            <>
                              <FilterField
                                value={header.column.getFilterValue() ?? ""}
                                onChange={(event) => {
                                  header.column.setFilterValue(
                                    event.target.value
                                  );
                                }}
                              />
                            </>
                          ) : null}
                        </th>
                      ))}
                    </tr>
                  ))}
                </>
              </thead>
              <tbody>
                <SortableContext
                  items={dataIds}
                  strategy={verticalListSortingStrategy}
                >
                  {table.getRowModel().rows?.length > 0 ? (
                    table
                      .getRowModel()
                      .rows.map((row) => (
                        <DraggableRow key={row.id} row={row} id={rowId} />
                      ))
                  ) : (
                    <tr>
                      <td
                        colSpan={
                          colSpanSubCols !== null
                            ? colSpanSubCols
                            : columns.length
                        }
                        className="empty-table"
                      >
                        <b>No se encontraron registros</b>
                      </td>
                    </tr>
                  )}
                </SortableContext>
              </tbody>
            </Table>
            {hasPagination ? (
              <>
                {manualPagination ? (
                  <RenderPagination positionPagination="paginationBottom" />
                ) : (
                  <PaginationRow
                    table={table}
                    positionPagination="paginationBottom"
                  />
                )}
              </>
            ) : null}
          </Card>
        </DndContext>
      );

    return (
      <Card className="card-table mb-3 editable-table">
        {/* Header */}
        {header ? (
          <Card.Header className="table-header text-center">
            {header}
          </Card.Header>
        ) : null}
        {hasPagination ? (
          <>
            {manualPagination ? (
              <RenderPagination
                positionPagination={
                  header ? "paginationTop-header" : "paginationTop"
                }
              />
            ) : (
              <PaginationRow
                table={table}
                positionPagination={
                  header ? "paginationTop-header" : "paginationTop"
                }
              />
            )}
          </>
        ) : null}
        <Table className="text-center mb-0" responsive striped>
          <thead className="bg-editable-table">
            <>
              {table.getHeaderGroups().map((headerGroup) => (
                <tr key={headerGroup.id}>
                  {headerGroup.headers.map((header) => (
                    <th key={header.id} colSpan={header.colSpan}>
                      {typeof header.column.columnDef.header === "function"
                        ? header.column.columnDef.header()
                        : header.column.columnDef.header}
                      {/* Sorting  */}
                      {header.column.getCanSort() ? (
                        <span
                          className={`sorting-button ${header.column.getIsSorted()
                              ? header.column.getIsSorted().toString()
                              : "inactive"
                            }`}
                          onClick={header.column.getToggleSortingHandler()}
                        >
                          <FontAwesomeIcon
                            style={{
                              paddingInline: "0.5rem",
                              cursor: "pointer",
                            }}
                            icon={faArrowUpWideShort}
                          // el estilo del sorter
                          />
                        </span>
                      ) : null}
                      {/* Filtering */}
                      {header.column.getCanFilter() ? (
                        <>
                          <FilterField
                            value={header.column.getFilterValue() ?? ""}
                            onChange={(event) => {
                              header.column.setFilterValue(event.target.value);
                            }}
                          />
                        </>
                      ) : null}
                    </th>
                  ))}
                </tr>
              ))}
            </>
          </thead>
          <tbody>
            {table.getRowModel().rows?.length > 0 ? (
              table.getRowModel().rows.map((row) => (
                <tr key={row.id}>
                  {row.getVisibleCells().map((cell) => (
                    <td key={cell.id}>
                      {flexRender(
                        cell.column.columnDef.cell,
                        cell.getContext()
                      )}
                    </td>
                  ))}
                </tr>
              ))
            ) : (
              <tr>
                <td
                  colSpan={
                    colSpanSubCols !== null ? colSpanSubCols : columns.length
                  }
                  className="empty-table"
                >
                  <b>No se encontraron registros</b>
                </td>
              </tr>
            )}
          </tbody>
        </Table>
        {hasPagination ? (
          <>
            {manualPagination ? (
              <RenderPagination positionPagination="paginationBottom" />
            ) : (
              <PaginationRow
                table={table}
                positionPagination="paginationBottom"
              />
            )}
          </>
        ) : null}
      </Card>
    );
  }
);
function PaginationRowFunction({ table, positionPagination }) {
  return (
    <>
      <Row className={"justify-content-center mx-0 py-1 " + positionPagination}>
        <Col md={1}>
          <Form.Select
            value={`${table.getState().pagination.pageSize}`}
            onChange={({ target }) => {
              table.setPageSize(Number(target.value));
            }}
          >
            <option value="15">15</option>
            <option value="50">50</option>
            <option value="100">100</option>
          </Form.Select>
        </Col>
        <Col md={5}>
          <Pagination
            className="m-0 justify-content-center"
            style={{ color: "#000" }}
          >
            <Pagination.First onClick={() => table.setPageIndex(0)} />
            <Pagination.Prev
              disabled={!table.getCanPreviousPage()}
              onClick={() => table.previousPage()}
            />
            <Pagination.Item disabled={true}>
              {table.getState().pagination.pageIndex + 1} of{" "}
              {table.getPageCount()}
            </Pagination.Item>
            <Pagination.Next
              disabled={!table.getCanNextPage()}
              onClick={() => table.nextPage()}
            />
            <Pagination.Last
              onClick={() => table.setPageIndex(table.getPageCount() - 1)}
            />
          </Pagination>
        </Col>
      </Row>
    </>
  );
}
