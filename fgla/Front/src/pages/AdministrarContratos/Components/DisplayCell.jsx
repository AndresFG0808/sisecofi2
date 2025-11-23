import React, { useEffect } from "react";

export function DisplayCell({
  getValue,
  options,
  keyValue,
  optionsKey,
  table,
  row,
  column,
}) {
  const { updateData } = table.options.meta;

  return (
    <>
      {
        options?.find(
          (user) =>
            user?.[optionsKey] ===
            parseInt(row?.original?.nombreServidorPublico)
        )?.[keyValue]
      }
    </>
  );
}
