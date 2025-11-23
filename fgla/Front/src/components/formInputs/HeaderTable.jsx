import React from "react";
import FilterField from "./FilterField";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSort } from "@fortawesome/free-solid-svg-icons";
import { Tooltip } from "../Tooltip";

const HeaderTable = ({
  name,
  text,
  sort,
  filter,
  tooltipMessage,
  handleSort,
  handleFilter,
}) => (
  <>
    <div style={{ display: "inline-flex", alignItems: "end" }}>
      {tooltipMessage ? (
        <Tooltip placement="top" text={tooltipMessage}>
          <span>{text}</span>
        </Tooltip>
      ) : (
        <span>{text}</span>
      )}

      {sort && (
        <FontAwesomeIcon
          style={{ cursor: "pointer", marginLeft: "5px" }}
          icon={faSort}
          onClick={handleSort(name)}
        />
      )}
    </div>
    {filter && (
      <FilterField
        name={name}
        className="inputSearch"
        onKeyUp={handleFilter}
        autoComplete="off"
      />
    )}
  </>
);

export default HeaderTable;
