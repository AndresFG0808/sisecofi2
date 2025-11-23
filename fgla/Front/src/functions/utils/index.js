import showMessage from "../../components/Messages";

export const transformDataToSelectInput = (data, keyValue, keyTextValue) => {
  return data.map((item) => {
    if (keyTextValue) {
      const { [keyValue]: value, [keyTextValue]: textValue } = { ...item };

      return {
        value,
        textValue,
      };
    } else {
      const { [keyValue]: value } = { ...item };

      return {
        value,
        textValue: value,
      };
    }
  });
};

export const injectActions = (
  data,
  {
    show = false,
    edit = false,
    download = false,
    remove = false,
    upload = false,
  } = {}
) =>
  data.map((item) => ({
    ...item,
    acciones: { show, edit, download, remove, upload },
  }));

export const totalAmount = (data, keyName) =>
  data.reduce(
    (accumulator, currentValue) => accumulator + Number(currentValue[keyName]),
    0
  );

export const removeObjectFromArray = (array, keyId, id) => [
  ...array.filter((element) => element[keyId] !== id),
];

export const getCatalogDescription = (id, array, keyValue, keyTextValue) =>
  array[array.findIndex((element) => element[keyValue] == id)][keyTextValue];

export const getLocalDateFormatFromDateObject = (date) =>
  `${date.getDate()}/${date.getMonth() + 1}/${date.getFullYear()}`;

export const getLocalTimeFormatFromDateObject = (date) =>
  `${date.getHours()}:${date.getMinutes()}:${date.getSeconds()}`;

export const getLocalDateFormatFromMilliseconds = (milliseconds) =>
  getLocalDateFormatFromDateObject(new Date(parseInt(milliseconds)));

export const stringFormatDate = (separator = "/", date = new Date()) =>
  `${date.getDate()}${separator}${date.getMonth() + 1
  }${separator}${date.getFullYear()}`;

export const addOrReplace = (array, element, key) => {
  let newArray = [];
  let exist = array.findIndex((item) => item[key] === element[key]);

  if (exist >= 0) {
    newArray = [...array];
    newArray.splice(exist, 1, element);
  } else {
    newArray = [...array, element];
  }
  return newArray;
};

export const validateString = (cadena) => {
  let regex = /^[0-9A-Za-zñÑáéíóúÁÉÍÓÚ\s\-@/]*$/;

  return regex.test(cadena);
};

export const validateNumber = (cadena, decimals) => {
  let regex;

  if (decimals === undefined) {
    regex = /^[0-9]*$/;
  } else {
    regex = new RegExp(`^[0-9]+(?:\\.[0-9]{0,${decimals}})?$`);
  }
  return regex.test(cadena);
};

export const downloadExcelBlob = (excelBlob, excelName) => {
  let file = new Blob([excelBlob], {
    type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
  });
  let fileURL = URL.createObjectURL(file);
  let link = document.createElement("a");
  link.href = fileURL;
  link.setAttribute("download", excelName + ".xlsx");
  document.body.appendChild(link);
  link.click();
  document.body.removeChild(link);
};
export const FormatMoney = (value, decimales) => {
  if (value) {
    value = "" + value;
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
  }
  return "";
};

export const truncateDecimals = (value, decimals = 2) => {
  if (isNaN(value) || value === null || value === undefined) return "";
  const factor = Math.pow(10, decimals);
  return Math.trunc(parseFloat(value) * factor) / factor;
};

// Modifica formatCurrency para truncar en vez de redondear
export const formatCurrency = (value, decimales) => {
  if (value !== undefined && value !== null && value !== "") {
    value = "" + value;
    let _value = value.replaceAll(",", "");
    _value = parseFloat(_value);

    // Truncar a la cantidad de decimales deseada
    _value = truncateDecimals(_value, decimales);

    // Limita los decimales reales a 6 como máximo
    let decimalsInValue = (_value.toString().split(".")[1] || "").length;
    decimalsInValue = Math.min(decimalsInValue, decimales);

    const OPTIONS_MONEY = {
      style: "currency",
      currency: "USD",
      minimumFractionDigits: decimalsInValue > 0 ? decimalsInValue : 0,
      maximumFractionDigits: decimales,
    };

    const FORMAT_MONEY = new Intl.NumberFormat("en-US", OPTIONS_MONEY);
    let formatMoney = FORMAT_MONEY.format(Math.abs(_value)).split("$")[1];

    if (_value < 0) {
      formatMoney = `-${formatMoney}`;
    }

    return formatMoney;
  }
  return "";
};

export const formatCurrencyString = (value, decimales, customMinimumDecimal = 6) => {
  if (value) {
    value = "" + value; // Ensure value is a string
    const OPTIONS_MONEY = {
      style: "currency",
      currency: "USD",
      minimumFractionDigits: 0,
      maximumFractionDigits: decimales,
    };

    // Remove any commas and parse the value
    let _value = value.replaceAll(",", "");
    _value = parseFloat(_value);

    // Check if the value is negative and store that
    const isNegative = _value < 0;

    // Check if the value has a decimal part
    const hasDecimals = _value % 1 !== 0;

    // Adjust fraction digits based on whether the value has decimals
    OPTIONS_MONEY.minimumFractionDigits = hasDecimals
      ? Math.min(decimales, customMinimumDecimal)
      : 0;
    OPTIONS_MONEY.maximumFractionDigits = hasDecimals
      ? Math.max(decimales, customMinimumDecimal)
      : 0;

    // Truncate the number to the specified decimal places
    const factor = Math.pow(10, decimales);
    _value = Math.round(_value * factor) / factor;

    const FORMAT_MONEY = new Intl.NumberFormat("en-US", OPTIONS_MONEY);

    // Format the number
    let formatMoney = FORMAT_MONEY.format(Math.abs(_value)).split("$")[1];

    // Add back the negative sign if the value was negative
    if (isNegative) {
      formatMoney = `-${formatMoney}`;
    }

    return formatMoney;
  }

  return "";
};

export const filtrarNumeros = (cadena) => {
  const regex = /[\d.,]+/g;
  const numeros = cadena.match(regex);
  if (!numeros) {
    return ""; // Si no se encuentran números, devolvemos una cadena vacía
  }
  let result = numeros.join("");

  const numerosFiltrados = result;
  return numerosFiltrados;
};
export const filtrarNumerosNegativos = (cadena) => {
  const negativos = String(cadena).split("-");
  console.log(negativos);
  const regex = /[-]?[\d.,]+/g;
  // si hay negativos, separamos

  if (negativos?.length >= 2 && negativos[0] === "") {
    console.log("existe simbolo", negativos);
    const numeros = negativos[1].match(regex);
    if (!numeros) {
      return "-" + "";
    }
    let result = numeros.join("");

    const numerosFiltrados = result;
    return "-" + numerosFiltrados;
  }
  const numeros = cadena.match(regex);
  if (!numeros) {
    return ""; // Si no se encuentran números, devolvemos una cadena vacía
  }
  let result = numeros.join("");

  const numerosFiltrados = result;
  return numerosFiltrados;
};
export function safeParseFloat(value, defaultValue = 0) {
  value = "" + value;
  value = value.replaceAll(",", "");
  return isNaN(parseFloat(value)) ? defaultValue : parseFloat(value);
}
export const errorValidations = (getMessageExists, error) => {
  let errorMessage =
    error?.response?.data?.mensaje?.[0];
  if (errorMessage && getMessageExists(errorMessage)) {
    showMessage(errorMessage);
  } else if (errorMessage) {
    showMessage(errorMessage);
  } else {
    showMessage("Ocurrió un error");
  }
};
