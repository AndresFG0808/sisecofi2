import React from "react";
import _ from "lodash";
import moment from "moment";
import { Link } from "react-router-dom";
import IconButton from "../../../components/buttons/IconButton";
import Authorization from "../../../components/Authorization";

export const FormatMoney = (value, decimales = 2) => {
  try {
    value = value ? value.toString() : "0";
    const OPTIONS_MONEY = {
      style: "currency",
      currency: "USD",
      minimumFractionDigits: decimales,
      maximumFractionDigits: decimales,
    };
    const FORMAT_MONEY = new Intl.NumberFormat("en-US", OPTIONS_MONEY);
    let _value = value.replaceAll(",", "");
    _value = parseFloat(_value, decimales);
    let formatMoney = FORMAT_MONEY.format(_value).split("$")[1];
    return formatMoney;
  } catch (error) {
    return "0.00";
  }
};

const mapValueCat = (idValue, cat, key = "primaryKey", name = "nombre") => {
  let value = idValue;
  if (!_.isEmpty(cat)) {
    let valueItem = cat.find((s) => s[key] === idValue);
    if (valueItem) {
      value = valueItem[name];
    }
  }
  return value;
};
const mapValueCatList = (
  idsValue,
  cat,
  key = "primaryKey",
  name = "nombre"
) => {
  let value = "";
  if (!_.isEmpty(cat) && !_.isEmpty(idsValue)) {
    let valueItems = cat
      .filter((s) => idsValue.includes(s[key]))
      .map((s) => s[name]);
    if (!_.isEmpty(valueItems)) {
      value = valueItems.join(",");
    }
  }
  return value;
};

function formatDate(date) {
  if (!date) return null;

  const dateMoment = moment(date, "YYYY-MM-DDTHH:mm:ss", true);
  if (!dateMoment.isValid()) return null;

  return dateMoment.format("DD/MM/YYYY");
}


function cutText(text, word) {
  if (typeof text !== "string" || typeof word !== "string") {
    throw new Error("Both parameters must be strings.");
  }

  if (text.length === 0 || word.length === 0) {
    return text; // If the text or the word is empty, return the complete text.
  }

  const wordIndex = text.toLowerCase().lastIndexOf(word.toLowerCase());
  if (wordIndex !== -1) {
    const endWord = wordIndex;
    return text.substring(0, endWord);
  } else {
    return text; // If the word is not found, return the complete text.
  }
}

function mapFiles(item) {
  let path = "";
  let fileCount = 0;
  let { infomacionArchivos = [], informacionArchivosOtrosDocumentos = [] } =
    item;

  if (
    !_.isEmpty(infomacionArchivos) ||
    !_.isEmpty(informacionArchivosOtrosDocumentos)
  ) {
    const validateFiles = (files) => (_.isArray(files) ? files : []);

    let allFiles = [
      ...validateFiles(infomacionArchivos),
      ...validateFiles(informacionArchivosOtrosDocumentos),
    ];

    if (!_.isEmpty(allFiles)) {
      allFiles = allFiles.filter((s) => s.tamanoMb);
      fileCount = _.size(allFiles);
      if (fileCount === 1) {
        path = allFiles[0].ruta;
      } else if (fileCount > 0) {
        path = cutText( allFiles[0].carpeta,"/");
      }
    }
  }

  return { path, fileCount };
}



export const mapComites = (comitesData, catalogos) => {
  let result = [];
  let { contratoConvenio, afectacion, comite } = catalogos;
  if (_.isArray(comitesData?.data)) {
    let { data } = comitesData;

    result = data.map((item) => {
      let {
        idContratoConvenio,
        idComite,
        fechaSesion,
        vigencia,
        acuerdo,
        monto,
        montoAutorizado,
        idsAfectacion,
      } = item.informacionComite;
      let { fileCount, path } = mapFiles(item);
      let result = {
        contratoConvenio: mapValueCat(idContratoConvenio, contratoConvenio),
        afectacion: mapValueCatList(idsAfectacion, afectacion),
        comite: mapValueCat(idComite, comite),
        fechaSesion: formatDate(fechaSesion),
        vigencia: vigencia,
        acuerdo: acuerdo,
        monto: monto || monto === 0 ? FormatMoney(monto) : monto,
        montoAutorizado: montoAutorizado || montoAutorizado === 0
          ? FormatMoney(montoAutorizado)
          : montoAutorizado,
        fileCount,
        path,
      };

      return result;
    });
  }

  return result;
};

export const LabelCell = ({ getValue, ...props }) => {
  let value = getValue();
  return <>{value}</>;
};
export const LabelCellArray = ({ getValue, ...props }) => {
  let value = getValue();
  if (value) {
    let values = value
      .split(",")
      .map((item, index) => <div key={index}>{item}</div>);
    return <>{values}</>;
  }
  return <>{value}</>;
};

export const fileCell = ({
  getValue,
  row,
  dercargarArchivo,
  descargarFolder,
  descargarSatCloud,
  ...props
}) => {
  let { fileCount, path } = row?.original;

  let response = <></>;
  if (fileCount === 1) {
    response = (
      <div className="d-flex px-2">
        <span className="ml-5" style={{ marginLeft: "5px" }}>
          <Link
            href="#"
            onClick={() => {
              dercargarArchivo(path);
            }}
          >
            Clic
          </Link>
        </span>
      </div>
    );
  } else if (fileCount > 1) {
    response = (
      <div href="#" className="d-flex">
        <IconButton
          type={"download"}
          onClick={() => {
            descargarSatCloud(path);
          }}
          iconSize="lg"
          tooltip={"SATCloud"}
          tableContainer
        />
        <IconButton
          type={"zip"}
          onClick={() => {
            descargarFolder(path);
          }}
          iconSize="lg"
          tooltip={"Descarga masiva"}
          tableContainer
        />
      </div>
    );
  }


  return (
    <Authorization process={"REP_TAB_CONT_CONS"}>{response}</Authorization>
  ); 
};
