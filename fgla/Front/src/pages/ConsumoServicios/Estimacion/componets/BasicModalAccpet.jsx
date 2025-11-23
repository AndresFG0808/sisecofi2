import React from 'react';
import Modal from 'react-bootstrap/Modal';
import Buttons from './ButtonsApprove';
import { Tooltip } from '../../../../components/Tooltip';

const BasicModalAccpet = ({
  show,
  onHide,
  size,
  asTitle,
  title,
  children,
  handleApprove,
  approveText,
  ...props
}) => (
  <Modal show={show} size={size} onHide={onHide} {...props}>
    <Modal.Header closeButton>
      <Modal.Title as={asTitle}>
        <div>{title}</div>
      </Modal.Title>
      {/* <Tooltip placement='left' text="Cerrar">
        <button class="btn-close" onClick={onHide}></button>
      </Tooltip> */}
    </Modal.Header>
    <Modal.Body className="text-center">
      {children}
    </Modal.Body>
    <Modal.Footer>
      <Buttons
        handleApprove={handleApprove}
        approveText={approveText}
      />
    </Modal.Footer>
  </Modal>
);

BasicModalAccpet.defaultProps = {
  size: 'lg',
  asTitle: 'h3',
};

export default BasicModalAccpet;
