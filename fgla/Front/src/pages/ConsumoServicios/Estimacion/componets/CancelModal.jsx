import BasicModal from "../../../../modals/BasicModal";

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
        size={"md"}
        handleDeny={handleDeny}
        denyText={"No"}
        approveText={"Sí"}
        show={isOpenCancelModal}
        title={'Mensaje'}
        onHide={handleDeny}
      >{title}
      </BasicModal>
    </>
  );
}
