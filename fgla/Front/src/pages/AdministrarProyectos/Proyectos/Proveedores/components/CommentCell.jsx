import React, { useState, useEffect } from "react";
import IconButton from "../../../../../components/buttons/IconButton";
import { Comentarios } from "../../../../../components";
import { Tooltip } from "../../../../../components/Tooltip";
import SingleBasicModal from "../../../../../modals/SingleBasicModal";
import CancelModal from "./CancelModal";
import { commentsData } from "../constants";
import isEmpty from "lodash/isEmpty";
import { getData, putData } from "../../../../../functions/api";
import { useToast } from "../../../../../hooks/useToast";
import Authorization from "../../../../../components/Authorization";

export function CommentCell({
  row,
  table,
  onUpdateComment,
  editable,
  processType = "",
  process,
}) {
  const { updateData } = table.options.meta;
  const { index } = row;

  const { infoToast, errorToast } = useToast();
  const [isOpenCommentModal, setIsOpenCommentModal] = useState(false);
  const [isOpenApproveComment, setIsOpenApproveComment] = useState(false);
  const [isOpenCancelModal, setIsOpenCancelModal] = useState(false);

  const { isNewProvider, comentario } = row.original;

  const commentValue = comentario;

  const type =
    commentValue === "" || commentValue === null ? "add" : "elllipsisH";

  const onClickCommentButton = () => {
    setIsOpenCommentModal(true);
  };

  const handleCancelButton = () => {
    setIsOpenCancelModal(false);
    setIsOpenCommentModal(false);
  };

  const handleCancel = () => {
    setIsOpenCancelModal(true);
  };

  const handleSave = (comment = "") => {
    if (isEmpty(comment) === false) {
      setIsOpenCommentModal(false);
      setIsOpenApproveComment(true);
      handleCancelButton();
      updateData(index, "comentario", comment);
      onUpdateComment(table);
    }
  };

  return (
    <>
      {(processType === "" || isNewProvider !== true) ? (
        <IconButton
          type={isNewProvider ? "add" : type}
          iconSize="lg"
          onClick={onClickCommentButton}
          disabled={false}
          tooltip={"Ver comentario"}
          tableContainer
        />
      ) : (
        <Authorization process={process}>
          <IconButton
            type={isNewProvider ? "add" : type}
            iconSize="lg"
            onClick={onClickCommentButton}
            disabled={false}
            tooltip={"Nuevo comentario"}
            tableContainer
          />
        </Authorization>
      )}

      {isOpenCommentModal && (
        <Comentarios
          disabled={!editable}
          processType='2'
          process={process}
          title="Agregar comentarios"
          placeholder="Comentarios"
          defaultValue={commentValue}
          show={isOpenCommentModal}
          comentarios={[]}
          handleCancel={handleCancel}
          handleSave={handleSave}
        />
      )}
      {isOpenApproveComment && (
        <SingleBasicModal
          handleApprove={() => setIsOpenApproveComment(false)}
          handleDeny={() => setIsOpenApproveComment(false)}
          approveText={"Aceptar"}
          show={isOpenApproveComment}
          title={"Mensaje"}
          onHide={() => setIsOpenApproveComment(false)}
        >
          La información ha sido actualizada.
        </SingleBasicModal>
      )}

      {isOpenCancelModal && (
        <CancelModal
          handleApprove={handleCancelButton}
          handleDeny={() => setIsOpenCancelModal(false)}
          isOpenCancelModal={isOpenCancelModal}
          title={
            "Se perderá la información ingresada. ¿Está seguro de continuar?"
          }
        />
      )}
    </>
  );
}
