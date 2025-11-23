import React, { useEffect, useState, useRef } from "react";
import { Card, Accordion } from "react-bootstrap";
import { useAccordionButton } from "react-bootstrap/AccordionButton";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faChevronDown,
  faChevronRight,
} from "@fortawesome/free-solid-svg-icons";
import styled from "styled-components";

const AccordionSAT = styled(Accordion)`
  color: #333333;
  border-radius: 0.25rem;
`;

const AccordionPanel = ({
  className,
  title,
  children,
  showChevron,
  collapse,
  eventKey,
  show,
  disabled,
  style,
  onClickElement,
}) => {

  function usePrevious(value) {
    const ref = useRef();
    useEffect(() => {
      ref.current = value; //assign the value of ref to the argument
    },[value]); //this code will run when the value of 'value' changes
    return ref.current; //in the end, return the current ref value.
  }
  const prevShow = usePrevious(show);

  const [open, setOpen] = useState(show);
  const [isFirstOpen, setIsFirstOpen] = useState(show);

  const CustomToggle = ({ children, eventKey }) => {
    const decoratedOnClick = useAccordionButton(eventKey, () => {});

    const handleClick = () => {
      if (!disabled) {
        const isOpen = !open;
        setOpen(isOpen);
        decoratedOnClick();
        onClickElement(isOpen);
        if (isFirstOpen === false) setIsFirstOpen(!isFirstOpen);
      }
    };

    useEffect(()=>{
      if (prevShow !== show && prevShow === true && open) {
        if (disabled === false) {
          setOpen(false);
          decoratedOnClick();
          setIsFirstOpen(false);
        }
      }
    }, [decoratedOnClick])

    return (
      <Card.Header
        className={`accordion-card-header${
          open && collapse ? "" : "-collapsed"
        } ${disabled ? "disabled" : ""}`}
        onClick={() => {
          handleClick();
        }}
      >
        {showChevron ? (
          <div className="accordion-icon">
            <FontAwesomeIcon
              icon={open && collapse ? faChevronDown : faChevronRight}
              size="lg"
            />
          </div>
        ) : null}
        {children}
      </Card.Header>
    );
  };
  return (
    <AccordionSAT
      style={style}
      className={"mb-4 shadow-sm " + className}
      defaultActiveKey={show ? "0" : "1"}
    >
      <Card className="accordion-card">
        <CustomToggle eventKey={eventKey}>{title}</CustomToggle>
        {collapse ? (
          <Accordion.Collapse eventKey={eventKey}>
            <Card.Body className="accordion-card-body">
              {(open || isFirstOpen) && children}
            </Card.Body>
          </Accordion.Collapse>
        ) : (
          <Card.Body>{children}</Card.Body>
        )}
      </Card>
    </AccordionSAT>
  );
};

AccordionPanel.defaultProps = {
  showChevron: true,
  collapse: true,
  eventKey: "0",
  show: true,
  disabled: false,
  onClickElement: () => {},
};

export default AccordionPanel;
