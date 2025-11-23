import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPlusSquare } from '@fortawesome/free-regular-svg-icons/faPlusSquare';
import { faSearch } from '@fortawesome/free-solid-svg-icons/faSearch';
import { faSyncAlt } from '@fortawesome/free-solid-svg-icons/faSyncAlt';
import { faTrashAlt } from '@fortawesome/free-solid-svg-icons/faTrashAlt';
import { faEdit } from '@fortawesome/free-regular-svg-icons/faEdit';
import { faFileExcel} from '@fortawesome/free-solid-svg-icons/faFileExcel'
import Button from './Button';

const icons = {
  add: faPlusSquare,
  filter: faSearch,
  refresh: faSyncAlt,
  search: faSearch,
  clear: faTrashAlt,
  manage: faSearch,
  edit: faEdit,
  excel: faFileExcel,
};

const texts = {
  add: 'Agregar',
  filter: 'Filtrar',
  refresh: 'Actualizar',
  search: 'Buscar',
  clear: 'Limpiar',
  manage: 'Administrar',
  edit: 'Editar',
  excel: 'Descargar',
  close: 'Cerrar'
};

const ButtonTypes = ({ type, buttonType, onClick, size, disabled }) => (
  <Button
    type={type}
    color="secondary"
    className="ms-2"
    size={size}
    onClick={onClick}
    disabled={disabled}
  >
    <FontAwesomeIcon icon={icons[buttonType]} className="me-2" />
    {texts[buttonType]}
  </Button>
);

ButtonTypes.defaultProps = {
  size: 'sm',
  type: 'button',
  disabled: false,
};

export default ButtonTypes;
