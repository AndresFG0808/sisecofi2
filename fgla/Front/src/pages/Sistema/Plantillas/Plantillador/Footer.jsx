import React from 'react';
import { Accordion } from '../../../../components';
import Editor from "./Editor";

const Footer = ({ footer, setFooter }) => {
    return (
        <Accordion title="Pie de página" show={false}>
            <Editor currentEdition={footer} setCurrentEdition={setFooter} />
        </Accordion>
    );
}

export default Footer;