import React, { useEffect, useState } from "react";
import { Card, Accordion, Col } from "react-bootstrap";
import styled from "styled-components";
import TextFieldIcon from "./formInputs/TextFieldIcon";
import { faSearch } from "@fortawesome/free-solid-svg-icons";
import BasicModal from "../modals/BasicModal";
import Form from "react-bootstrap/Form";
import { isEmptyArray } from "formik";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faAngleLeft, faAnglesLeft, faAngleRight, faAnglesRight } from "@fortawesome/free-solid-svg-icons";

const AccordionSAT = styled(Accordion)`
  color: #333333;
  border-radius: 0.25rem;
`;

const SearchInputStyle = styled.div`
  margin: 15px;
`;

const styleContainerButtons = {
  display: "flex",
  flexDirection: "column",
  alignItems: "center",
  justifyContent: "center",
};

const styleContainer = { display: "flex", justifyContent: "space-between" };

export default function CustomPicklist({
  sourceDataList,
  targetDataList,
  sourceHeader,
  targetHeader,
  onChangeTarget,
  onChangeSource,
  textKey,
  idKey,
  rightButtonTextModal,
  leftButtonTextModal,
  isCancelChanges,
  onCanceledChanges,
}) {
  const [source, setSource] = useState([]);
  const [target, setTarget] = useState([]);
  const [isDisabledLeftButton, setIsDisabledLeftButton] = useState(true);
  const [isDisabledRightButton, setIsDisabledRightButton] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [modalMessage, setModalMessage] = useState("");
  const [fnModalSuccess, setFnModalSuccess] = useState(() => { });

  useEffect(() => {
    if (isCancelChanges) {
      setSource([...sourceDataList]);
      setTarget([...targetDataList]);
      onCanceledChanges();
    }
  }, [isCancelChanges, sourceDataList, targetDataList]);

  const selectItem = (id) => {
    if (target.some((item) => item[idKey] === id)) {
      setTarget((prevTarget) =>
        prevTarget.map((item) => {
          const itemnew = { ...item };
          return itemnew[idKey] === id
            ? { ...{ ...itemnew }, selected: itemnew.selected ? false : true }
            : { ...itemnew };
        })
      );
    } else {
      setSource((prevSource) =>
        prevSource.map((item) => {
          const itemnew = { ...item };
          return itemnew[idKey] === id
            ? { ...{ ...itemnew }, selected: itemnew.selected ? false : true }
            : { ...itemnew };
        })
      );
    }
  };

  const onResetSelectedArray = (array) =>
    array.map((item) => {
      const obj = { ...item };
      if (obj.selected) {
        obj.selected = !obj.selected;
      }
      return { ...obj };
    });

  const onRemoveSelectedItems = (array) => [
    ...array.filter((item) => item.selected === false),
  ];

  const handleArrowRight = () => {
    const targetArray = [...target, ...source.filter((item) => item.selected)];
    setTarget(onResetSelectedArray(targetArray));
    setSource((prevSource) => onRemoveSelectedItems(prevSource));
  };

  const handleArrowRightAll = () => {
    setShowModal(true);
    setModalMessage(rightButtonTextModal);
    const fn = () => {
      setShowModal(false);
      setTarget((prevTarget) =>
        [...prevTarget, ...source].map((item) => {
          item.selected = false;
          return item;
        })
      );
      setSource([]);
    };
    setFnModalSuccess(() => fn);
  };

  const handleArrowLeftAll = () => {
    setShowModal(true);
    setModalMessage(leftButtonTextModal);
    const fn = () => {
      setShowModal(false);
      setSource((prevSource) =>
        [...prevSource, ...target].map((item) => {
          item.selected = false;
          return item;
        })
      );
      setTarget([]);
    };
    setFnModalSuccess(() => fn);
  };

  const handleArrowLeft = () => {
    const sourceArray = [...source, ...target.filter((item) => item.selected)];
    setSource(onResetSelectedArray(sourceArray));
    setTarget((prevLista) => onRemoveSelectedItems(prevLista));
  };

  const onDisabledSelectedButton = (array, set) => {
    const selectedElements = array.filter((item) => item.selected);
    selectedElements.length === 0 ? set(true) : set(false);
  };

  const disabledAllButtons = (array) => (array.length === 0 ? true : false);

  useEffect(() => {
    if (source.length > 0)
      onDisabledSelectedButton(source, setIsDisabledRightButton);
    onChangeSource(source);
  }, [source]);

  useEffect(() => {
    if (target.length > 0)
      onDisabledSelectedButton(target, setIsDisabledLeftButton);
    onChangeTarget(target);
  }, [target]);

  return (
    <div className="pl-container" style={styleContainer}>
      <AccordionSAT className="mb-4 shadow-sm col-md-5">
        <BasicModal
          size="md"
          handleApprove={fnModalSuccess}
          handleDeny={() => setShowModal(false)}
          denyText={"No"}
          approveText={"Sí"}
          show={showModal}
          title={'Mensaje'}
          onHide={() => setShowModal(false)}
        >
          {modalMessage}
        </BasicModal>

        <SelectCard
          title={sourceHeader}
          arrayItems={source}
          selectItem={selectItem}
          textKey={textKey}
          idKey={idKey}
        />
      </AccordionSAT>

      <Col md={1} style={styleContainerButtons}>
        <ButtonsGroup
          disabledRightAllButtons={disabledAllButtons(source)}
          disabledLeftAllButtons={disabledAllButtons(target)}
          handleArrowRightAll={handleArrowRightAll}
          isDisabledRightButton={isDisabledRightButton}
          handleArrowRight={handleArrowRight}
          handleArrowLeftAll={handleArrowLeftAll}
          isDisabledLeftButton={isDisabledLeftButton}
          handleArrowLeft={handleArrowLeft}
        />
      </Col>

      <AccordionSAT className={"mb-4 shadow-sm col-md-5"}>
        <SelectCard
          title={targetHeader}
          arrayItems={target}
          selectItem={selectItem}
          textKey={textKey}
          idKey={idKey}
        />
      </AccordionSAT>
    </div>
  );
}

function SelectCard({ title, arrayItems, selectItem, textKey, idKey }) {
  const [filter, setFilter] = useState("");

  const handleChangeFilter = (e) => {
    setFilter(e.target.value);
  };

  useEffect(() => {
    if (isEmptyArray(arrayItems) && filter !== "") setFilter("");
  }, [arrayItems, filter]);

  return (
    <Card className="accordion-card">
      <Card.Header className="picklist-header">
        <span>
          <th>{title}</th>
        </span>
      </Card.Header>
      <Card.Body
        style={{
          padding: "0",
        }}
      >
        <SearchInputStyle>
          <TextFieldIcon
            endIcon={faSearch}
            name="Buscar"
            placeholder={"Buscar"}
            value={filter}
            onChange={handleChangeFilter}
            onBlur={() => { }}
            className={""}
            helperText={""}
            disabled={false}
          />
        </SearchInputStyle>

        <hr style={{
          margin: '0px'
        }} />
        <div
          style={{
            overflowY: "auto",
            height: "25em",
          }}
        >
          {arrayItems.length > 0 &&
            arrayItems
              .filter((item) =>
                item[textKey].toLowerCase().includes(filter.toLowerCase())
              )
              .map((item) => (
                <div
                  key={item[idKey]}
                  style={{ display: "flex" }}
                  className={`flex p-2 align-items-center gap-3 ${item.selected ? "item-selected" : "item-assign"
                    }`}
                >
                  <Form.Check
                    type={"checkbox"}
                    id={item[textKey]}
                    checked={item.selected}
                    onChange={() => selectItem(item[idKey])}
                    style={{ cursor: "pointer" }}
                  />

                  <div className="flex-1 flex flex-column gap-2">
                    <span>{item[textKey]}</span>
                  </div>
                </div>
              ))}
        </div>
      </Card.Body>
    </Card>
  );
}

function ButtonsGroup({
  disabledRightAllButtons,
  disabledLeftAllButtons,
  handleArrowRightAll,
  isDisabledRightButton,
  handleArrowRight,
  handleArrowLeftAll,
  isDisabledLeftButton,
  handleArrowLeft,
}) {
  return (
    <div className="buttons-assign-container">
      <button
        style={{
          borderTopRightRadius: "5px",
          borderTopLeftRadius: "5px",
        }}
        disabled={disabledRightAllButtons}
        onClick={handleArrowRightAll}
        className="selected-assign-button"
      >
        <FontAwesomeIcon icon={faAnglesRight} />
      </button>
      <button
        disabled={isDisabledRightButton}
        onClick={handleArrowRight}
        className="selected-assign-button"
      >
        <FontAwesomeIcon icon={faAngleRight} />
      </button>
      <button
        disabled={disabledLeftAllButtons}
        onClick={handleArrowLeftAll}
        className="selected-assign-button"
      >
        <FontAwesomeIcon icon={faAnglesLeft} />
      </button>
      <button
        disabled={isDisabledLeftButton}
        onClick={handleArrowLeft}
        style={{
          borderBottomRightRadius: "5px",
          borderBottomLeftRadius: "5px",
        }}
        className="selected-assign-button"
      >
        <FontAwesomeIcon icon={faAngleLeft} />
      </button>
    </div>
  );
}
