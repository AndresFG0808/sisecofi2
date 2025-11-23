import React, { useEffect, useState } from "react";
import moment from "moment";

const FORMAT_DATE = "DD/MM/YYYY";

export function DateCell({
    getValue
}) {
    const initialValue = getValue();
    const [value, setValue] = useState(initialValue);

    useEffect(() => {
        setValue(dateFormat(initialValue));
    }, [initialValue]);

    const dateFormat = (date) => {
        let formatedDateTime = date ? moment(date).format(FORMAT_DATE) : "";
        return formatedDateTime;
    }

    return <>{value}</>;
}
