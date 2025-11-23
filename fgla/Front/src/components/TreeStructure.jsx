import { faChevronRight } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React from "react";
import { useState } from "react";
import { FormCheck } from "react-bootstrap";

export function TreeStructure({ data, setData }) {
  return (
    <>
      <ul style={{ listStyleType: "none" }}>
        {data?.map((node, index) => (
          <Root node={node} key={`${node.id}`} setData={setData} data={data} />
        ))}
      </ul>
    </>
  );
}

function Root({ node, setData, data }) {
  const [isOpen, setIsOpen] = useState(true);

  const handleCheckboxChange = () => {
    // Create a new tree structure with the updated checked state
    const updateChecked = (nodes) => {
      return nodes.map((n) => {
        if (n.id === node.id) {
          // Use id for matching
          return { ...n, checked: !n.checked }; // Toggle checked state
        } else if (n.nodes.length > 0) {
          return { ...n, nodes: updateChecked(n.nodes) }; // Recur for child nodes
        }
        return n;
      });
    };

    const updatedData = updateChecked(data);
    setData(updatedData); // Update parent state
  };

  return (
    <li>
      <span>
        {node.nodes && node.nodes.length > 0 && (
          <span
            onClick={() => setIsOpen((prev) => !prev)}
            className={` tree-structure-chevron pe-1 ${
              isOpen
                ? "tree-structure-chevron-open"
                : "tree-structure-chevron-closed"
            }`}
          >
            <FontAwesomeIcon icon={faChevronRight} />
          </span>
        )}
        {node.nodes.length > 0 ? (
          <>{node.name}</>
        ) : (
          <>
            <span style={{ display: "flex", gap: "0.5rem" }}>
              <FormCheck
                checked={node.checked}
                onChange={handleCheckboxChange}
              />{" "}
              {node.name}
            </span>
          </>
        )}
      </span>
      {isOpen && (
        <ul className="pl-2" style={{ listStyleType: "none" }}>
          {node?.nodes.map((node, index) => (
            <Root
              node={node}
              key={`${node.id}`}
              setData={setData}
              data={data}
            />
          ))}
        </ul>
      )}
    </li>
  );
}
