import IconButton from '../../../../components/buttons/IconButton';

export function ActionCell({ row, addVersion, editPlantilla, selectPlantillaPlan, disabled }) {
  const { tipo, idTipoPlantillador, idPlantillador, idSubPlantillador, nombre } = row.original;
  const showAdd = tipo === "carpeta";
  const showUpload = idPlantillador && idTipoPlantillador === 4;
  const showEdit = (idPlantillador || idSubPlantillador) && idTipoPlantillador && (tipo === "plantilla" || tipo === "plantilla-hoja") && idTipoPlantillador !== 4;

  return (
    <>
      {showAdd &&
        <IconButton iconSize="lg" type={"add"} onClick={addVersion(idTipoPlantillador, row)} disabled={disabled} tableContainer />}
      {showEdit &&
        <IconButton iconSize="lg" type={"edit"} onClick={editPlantilla(idTipoPlantillador, idPlantillador, row)} disabled={disabled} tableContainer />}
      {showUpload &&
        <IconButton iconSize="lg" type={"upload"} onClick={selectPlantillaPlan(idPlantillador, nombre)} disabled={disabled} tableContainer />}
    </>
  );
}
