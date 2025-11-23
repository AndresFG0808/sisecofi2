import React from "react"
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCircle } from "@fortawesome/free-solid-svg-icons";

const icons = {
    circle: faCircle,
};

const COLORS = {
    verde: "#228B22",
    amarillo: "#FFD700",
    naranja: "#ff7e00",
    rojo: "#FF0000",
}

export const Semaforo = ({ color }) => (
    <FontAwesomeIcon
        icon={icons["circle"]}
        size="2x"
        style={{ color: COLORS[color] }}
    />
);
