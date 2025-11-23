import React from "react";
import { Tooltip } from "../Tooltip";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faFileArrowUp,
  faFileArrowDown,
  faCirclePlus,
  faCircleMinus,
  faPencil,
  faCalendarDays,
  faArrowUpFromBracket,
  faFileZipper,
  faPhone,
  faEllipsisH,
  faCloudArrowDown,
  faXmark,
  faSave,
  faMoneyCheckDollar,
  faArrowLeft,
  faArrowRight,
  faFileText,
  faCopy,
  faSlash,
  faFileExcel,
  faClipboardCheck,
  faSearch,
  faRotate,
  faFileCircleXmark,
  faFileInvoiceDollar,
  faDownload,
  faTriangleExclamation,
  faEllipsis
} from "@fortawesome/free-solid-svg-icons";
import {
  faEye,
  faTrashCan,
  faCalendarCheck
} from "@fortawesome/free-regular-svg-icons";

const iconMapping = {
  add: faCirclePlus,
  alert: faTriangleExclamation,
  excel: faFileArrowDown,
  excel2: faFileExcel,
  cancel: faFileCircleXmark,
  delete: faCircleMinus,
  edit: faPencil,
  calendar: faCalendarDays,
  drop: faTrashCan,
  upload: faArrowUpFromBracket,
  zip: faFileZipper,
  phone: faPhone,
  elllipsisH: faEllipsisH,
  show: faEye,
  download: faCloudArrowDown,
  undo: faXmark,
  save: faSave,
  twoFiles: faCopy,
  twoFilesBlack: faCopy,
  faSlash: faSlash,
  moneyDollar: faMoneyCheckDollar,
  arrowLeft: faArrowLeft,
  arrowRight: faArrowRight,
  arrowUp: faFileArrowUp,
  fileText: faFileText,
  fileCheck: faClipboardCheck,
  redFileText: faFileText,
  calendarUpload: faCalendarCheck,
  search: faSearch,
  rotate: faRotate,
  facturado: faFileInvoiceDollar,
  tableDownload: faDownload,
  noApply: faFileExcel,
  showDetail: faEllipsis
};

const iconStyles = {
  add: "icon-button",
  excel: "icon-button",
  excel2: "icon-button",
  cancel: "icon-button-cancel",
  delete: "icon-button",
  edit: "icon-button",
  calendar: "icon-button",
  drop: "icon-button",
  upload: "icon-button",
  zip: "icon-button",
  phone: "icon-button",
  elllipsisH: "icon-button",
  show: "icon-button",
  download: "icon-button",
  undo: "icon-button",
  save: "icon-button",
  twoFiles: "icon-button",
  twoFilesBlack: "icon-button",
  faSlash: "icon-button",
  moneyDollar: "icon-button",
  arrowLeft: "icon-button",
  arrowRight: "icon-button",
  arrowUp: "icon-button",
  redFileText: "icon-button",
  fileText: "icon-button",
  fileCheck: "icon-button",
  calendarUpload: "icon-button",
  search: "icon-button",
  rotate: "icon-button",
  facturado: "icon-button",
  tableDownload: "icon-button",
  noApply: "icon-button",
  showDetail: "icon-button",
};

const iconColor = {
  add: "#828282",
  alert: "#FFCC00",
  excel: "#828282",
  excel2: "#828282",
  cancel: "#691C32",
  delete: "#828282",
  edit: "#828282",
  calendar: "#828282",
  drop: "#955E6D",
  upload: "#828282",
  zip: "#FFCC00",
  phone: "#828282",
  elllipsisH: "#828282",
  show: "#828282",
  download: "#828282",
  undo: "#828282",
  save: "#828282",
  twoFiles: "#BC955C",
  redFileText: "#dc3545",
  twoFilesBlack: '#828282',
  moneyDollar: "#BC955C",
  arrowLeft: "#828282",
  arrowRight: "#828282",
  arrowUp: "#5F84DF",
  fileText: "#0C3C1F",
  fileCheck: "#16AB97",
  calendarUpload: "#828282",
  search: "#828282",
  rotate: "#828282",
  facturado: "#0C3C1F",
  tableDownload: "#828282",
  noApply: "#DF254A",
  showDetail: "#828282",
};

const IconButton = (props) => {
  return (
    <>
      {
        props.tooltip ?
          <Tooltip placement="top" text={props.tooltip}>
            <span>
              <Button {...props} />
            </span>
          </Tooltip> :
          <Button {...props} />
      }
    </>
  )
}

const Button = ({ type, onClick, disabled, iconSize = "2x", tableContainer = false, className="" }) => (
  <FontAwesomeIcon
    icon={iconMapping[type]}
    className={(className != "" ?  className : (disabled ? "icon-button-disabled" : iconStyles[type]))}
    size={iconSize}
    onClick={disabled ? undefined : onClick}
    style={{
      color: disabled ? "#c2c2c2" : iconColor[type],
      ...(tableContainer && { margin: '5px' }),
    }}
  />
);

IconButton.defaultProps = {
  disabled: false,
};

export default IconButton;
