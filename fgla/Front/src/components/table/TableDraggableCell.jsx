import { useSortable } from "@dnd-kit/sortable";
import React from "react";

export function TableDraggableCell({ rowId, isDisabled }) {
  const { attributes, listeners } = useSortable({ id: rowId });

  return (
    <>
      <button
        {...attributes}
        {...listeners}
        className="drag-button"
        disabled={isDisabled}
      >
        🟰
      </button>
    </>
  );
}
