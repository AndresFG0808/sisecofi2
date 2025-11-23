import BasicModal from "../../../../../modals/BasicModal";

export default function CancelModal({
  handleApprove,
  handleDeny,
  isOpenCancelModal,
  title,
}) {
  return (
    <>
      <BasicModal
        handleApprove={handleApprove}
        handleDeny={handleDeny}
        denyText={"No"}
        approveText={"Sí"}
        size="md"
        show={isOpenCancelModal}
        title={'Mensaje'}
        onHide={handleDeny}
      >{title}
      </BasicModal>
    </>
  );
}
