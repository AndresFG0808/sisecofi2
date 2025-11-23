import React, { useCallback, useEffect, useState } from "react";
import { useSelector, useDispatch } from "react-redux";
import * as catalogosSlice from "../../../../../store/catalogos/catalogosSlice";
import { GetPlantillasVigentes } from "../../../../../store/infoComites/infoComitesActions";
import { GetInfoComites } from "../../../../../store/infoComites/infoComitesActions";
import { GetColumnsInfoComitesTable } from "./InformacionComiteTableUtilities";
import { mapOptionsSelect } from "../Components/ValidationSchema";
import { TablaEditable } from "../../../../../components/table/TablaEditable";
import { Loader } from "../../../../../components";
import _ from "lodash";
export default function InformacionComiteTable({
  handleShow,
  handleEdit,
  ShowMessage,
  idProyecto,
  setComiteCount,
}) {
  const infoComites = useSelector((state) => state.infoComites);
  const catalogos = useSelector((state) => state.catalogos);
  const [isLoading, setIsLoading] = useState(false);
  var { tableData } = infoComites;

  const dispatch = useDispatch();

  const findValue = (options, value) => {
    var selectedValue = options.find((s) => s.idValue === value);
    if (selectedValue) {
      return selectedValue.value;
    }
  };

  const { editable } = useSelector((state) => state.proyectos);
  useEffect(() => {
    if (idProyecto) {
      setIsLoading(true);
      dispatch(catalogosSlice.getContratos(idProyecto));
      dispatch(GetInfoComites(idProyecto)).then(() => {
        setIsLoading(false);
      });
    }
  }, [dispatch, idProyecto]);

  useEffect(() => {
    dispatch(catalogosSlice.getContratoConvenio());

    dispatch(catalogosSlice.getComite());
    dispatch(catalogosSlice.getAfectacion());
    dispatch(catalogosSlice.getMoneda());
    dispatch(catalogosSlice.getSesionNumero());
    dispatch(catalogosSlice.getSesionClasificacion());
    dispatch(GetPlantillasVigentes());
  }, [dispatch]);

  const [dataTable, setDataTable] = useState([]);

  const FormatMoney = (value, decimales=2) => {
    value = "" + value;
    let [entero, decimal = ""] = value.split(".");
    value = `${entero}.${decimal.slice(0, 2)}`;
    const OPTIONS_MONEY = {
      style: "currency",
      currency: "USD",
      minimumFractionDigits: decimales,
      maximumFractionDigits: decimales,
    };
    const FORMAT_MONEY = new Intl.NumberFormat("en-US", OPTIONS_MONEY);
    let _value = value.replaceAll(",", "");
  
    let _formatMoney = FORMAT_MONEY.format(_value);
    let formatMoney = _formatMoney.replace("$", "");
    return formatMoney;
  };
  const MapInfoComites = useCallback(
    (data) => {
      let result = [];
      if (Array.isArray(data) && catalogos?.catalogos) {
        setComiteCount(data.length);
        let { contratoConvenio, afectacion, comite } = catalogos.catalogos;

        let contratosConvenio = mapOptionsSelect(contratoConvenio);
        let comites = mapOptionsSelect(comite);
        let afectaciones = mapOptionsSelect(afectacion);

        result = data.map((comiteItem) => {
          var { informacionComite } = comiteItem;

          let idContratoConvenio = findValue(
            contratosConvenio,
            informacionComite.idContratoConvenio
          );
          let idComite = findValue(comites, informacionComite.idComite);

          let afectacionList = !Array.isArray(informacionComite?.idsAfectacion)
            ? []
            : informacionComite.idsAfectacion.map((item) =>
                findValue(afectaciones, item)
              );

          afectacionList = _.uniq(afectacionList);

          let afectacion = afectacionList.join(",");
          let { infomacionArchivos, informacionArchivosOtrosDocumentos } =
            comiteItem;
          let _infomacionArchivos = [];
          let _informacionArchivosOtrosDocumentos = [];
          if (Array.isArray(infomacionArchivos)) {
            _infomacionArchivos = infomacionArchivos.filter(
              (s) => s.tamanoMb && s.tamanoMb > 0
            );
          }
          if (Array.isArray(informacionArchivosOtrosDocumentos)) {
            _informacionArchivosOtrosDocumentos =
              informacionArchivosOtrosDocumentos.filter(
                (s) => s.tamanoMb && s.tamanoMb > 0
              );
          }

          let {montoString, monto}=informacionComite ?? {};
          let _informacionComite = {
            ...informacionComite,
            monto:
              montoString && (monto || monto === 0)
                ? FormatMoney(montoString)
                : "",
            fechaSesion: convertDate(informacionComite.fechaSesion),
            idContratoConvenio: idContratoConvenio,
            idComite: idComite,
            idsAfectacion: afectacion,
          };
          return {
            ..._informacionComite,
            ...comiteItem,
            infomacionArchivos: _infomacionArchivos,
            informacionArchivosOtrosDocumentos:
              _informacionArchivosOtrosDocumentos,
          };
        });
        result = result.filter((s) => s);
      }
      setDataTable(result);
    },
    [catalogos]
  );

  useEffect(() => {
    if (tableData?.data) MapInfoComites(tableData.data);
  }, [tableData, catalogos, MapInfoComites]);

  //#region Delete Comite

  //#endregion

  //#endregion
  const [columns, setColumns] = useState([]);

  useEffect(() => {
    setColumns(
      GetColumnsInfoComitesTable({
        handleShow,
        ShowMessage,
        handleEdit,
        idProyecto,
        editable,
        setIsLoading,
      })
    );
  }, [ShowMessage, handleShow, handleEdit, idProyecto, editable]);

  return (
    <>
      {isLoading && <Loader />}
      <TablaEditable
        dataTable={dataTable}
        columns={columns}
        header={"Comités"}
        hasPagination
        isFiltered
        onDelete={setDataTable}
      />
    </>
  );
}

function convertDate(dateString) {
  var date = new Date(dateString);
  if (!isNaN(date.getTime())) {
    var day = date.getDate();
    var month = date.getMonth() + 1;
    var year = date.getFullYear();
    var formattedDate =
      (day < 10 ? "0" : "") +
      day +
      "/" +
      (month < 10 ? "0" : "") +
      month +
      "/" +
      year;
    return formattedDate;
  } else {
    return dateString;
  }
}
