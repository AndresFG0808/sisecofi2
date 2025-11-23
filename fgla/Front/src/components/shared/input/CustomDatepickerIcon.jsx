import isNil from "lodash/isNil";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCalendarAlt } from "@fortawesome/free-solid-svg-icons";
import DatePicker, { registerLocale } from "react-datepicker";
import { Portal } from "react-overlays";
import moment from "moment";
import "react-datepicker/dist/react-datepicker.css";
import es from 'date-fns/locale/es';
registerLocale('es', es);
function CustomDatepickerIcon({ ...props }) {
  const {
    onChange,
    value,
    outputDateFormat,
    inputDateFormat,
    disabled,
    maxDate,
  } = props;
  const timeOffset = "T00:00:00";
  const newdate = isNil(value) ? null : new Date(`${value}${timeOffset}`);
  const CustomInput = ({ onClick }) => (
    <div>
      <FontAwesomeIcon
        style={disabled ? {} : { cursor: "pointer" }}
        onClick={onClick}
        icon={faCalendarAlt}
      />
    </div>
  );
  return (
    <DatePicker
      popperContainer={Portal}
      selected={newdate}
      locale="es"
      peekNextMonth
      showMonthDropdown
      disabled={disabled}
      showYearDropdown
      maxDate={maxDate}
      dateFormat={inputDateFormat}
      onChange={(date) => {
        const dateFormatted = moment(date).format(outputDateFormat);
        onChange(dateFormatted);
      }}
      customInput={<CustomInput />}
    />
  );
}

CustomDatepickerIcon.defaultProps = {
  maxDate: null,
};

export default CustomDatepickerIcon;
