import React, { useEffect, useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faChevronDown, faChevronRight } from "@fortawesome/free-solid-svg-icons";

export function ExpandCell({ getValue, row, levelExpand }) {

    const [isExpanded, setIsExpanded] = useState(false);

    useEffect(() => {
        if (levelExpand) {
            let { nivelEsquema } = row.original;
            if (levelExpand !== null && nivelEsquema < levelExpand) {
                row.toggleExpanded(true);
            } else {
                row.toggleExpanded(false);
            }
        }
    }, [levelExpand]);

    useEffect(() => {
        setIsExpanded(row.getIsExpanded());
    }, [getValue()]);

    return (
        <div
            style={{
                paddingLeft: `${row.depth * 1.5}rem`,
                textAlign: "left",
                width: '250px'
            }}
        >
            <div>
                {row.getCanExpand() ? (
                    <button
                        {...{
                            onClick: () => {
                                console.log("onClick");
                                row.getToggleExpandedHandler()();
                                setIsExpanded(!isExpanded);
                            },
                            style: {
                                border: "1px solid transparent",
                                background: "transparent",
                                paddingLeft: "0px"
                            },
                        }}
                    >
                        {row.getIsExpanded() || isExpanded ? (
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
