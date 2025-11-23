import React, { useEffect, useState } from "react";
import { Container } from "react-bootstrap";
import { Accordion, MainTitle } from "../../components";

import RegistrarReintegros from "./RegistrarReintegros";
import GestionDocumental from "./GestionDocumental";

export default function ReintegrosSecciones() {
  const [idContratoGestion, setIdContratoGestion] = useState(null);
  const [disabledReintegros, setDisabledReintegros] = useState(true);
  const [isOpen, setIsOpen]=useState(false);

useEffect(()=>{
  if(!idContratoGestion && isOpen){
    setIsOpen(false)
  }
},[idContratoGestion,isOpen])

  return (
    <Container className="mt-3 px-3">
      <MainTitle title="Reintegros" />

      <Accordion title="Registro" showChevron={false} collapse={false}>
        <RegistrarReintegros
          setIdContratoGestion={setIdContratoGestion}
          setDisabledReintegros={setDisabledReintegros}
        />
      </Accordion>

      <Accordion title="Gestión documental" show={isOpen} onClickElement={(e)=>setIsOpen(e)}>
        <GestionDocumental
          idContratoGestion={idContratoGestion}
          disabledReintegros={disabledReintegros}
        
        />
      </Accordion>
    </Container>
  );
}
