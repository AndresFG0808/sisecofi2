import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTrashAlt } from '@fortawesome/free-regular-svg-icons/faTrashAlt';
import { faPencil } from "@fortawesome/free-solid-svg-icons/faPencil";
import { faEye } from '@fortawesome/free-regular-svg-icons/faEye';
import { faDownload, faUpload } from '@fortawesome/free-solid-svg-icons';
import styled from 'styled-components';
import { COLORS } from '../constants/colors';
import { Tooltip } from './Tooltip';

const icons = {
  delete: faTrashAlt,
  edit: faPencil,
  show: faEye,
  download: faDownload,
  upload: faUpload,
};

const colors = {
  delete: COLORS.red,
  edit: "#828282",
  show: "#828282",
  download: "#828282",
  upload: "#828282",
};

const tootips = {
  delete: "Eliminar",
  edit: "Editar",
  show: "Ver detalle",
  download: "Descargar",
  upload: "Cargar"
};

const Icon = styled(FontAwesomeIcon)`
  cursor: pointer;
  margin: 5px;
  &:hover {
    color: ${(props) => props.colorhover};
  }
`;

const IconDisabled = styled(FontAwesomeIcon)`
  cursor: unset;
  margin: 5px;
  color: silver;
`;

const Action = ({ variant, onClick, disabled, ...props }) => (
  <>
    {!disabled && <Tooltip placement="top" text={tootips[variant]}><span><Icon size="lg" icon={icons[variant]} color={colors[variant]} onClick={onClick} {...props} /></span></Tooltip>}
    {disabled && <IconDisabled disabled size="lg" icon={icons[variant]} colorhover={colors[variant]} {...props} />}
  </>
);

export default Action;
