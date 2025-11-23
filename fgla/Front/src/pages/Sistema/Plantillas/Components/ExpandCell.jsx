import React, { useEffect } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faChevronDown, faChevronRight } from "@fortawesome/free-solid-svg-icons";

export function ExpandCell({
    getValue,
    row,
    levelExpand
}) {

    useEffect(() => {
        if(levelExpand) {
            console.log("levelExpand => ", levelExpand);
        }
      }, [levelExpand]);

    return (
        <div
            style={{
                paddingLeft: `${row.depth * 1.5}rem`,
                textAlign: "left"
            }}
        >
            <div>
                {row.getCanExpand() ? (
                    <button
                        {...{
                            onClick: row.getToggleExpandedHandler(),
                            style: {
                                border: "1px solid transparent",
                                background: "transparent",
                                paddingLeft: "0px"
                            },
                        }}
                    >
                        {row.getIsExpanded() ? (
                            <FontAwesomeIcon icon={faChevronDown} />
                        ) : (
                            <FontAwesomeIcon icon={faChevronRight} />
                        )}
                    </button>
                ) : (
                    ""
                )}
                {<>{getValue()}</>}
            </div>
        </div>
    );
}
