import { useSelector } from "react-redux";
import { PROCESOS } from "../constants/reglas_rol";
import { useMemo } from "react";
function getRolesFromProcess(process) {
  if (Array.isArray(process)) {
    return process.reduce((roles, p) => {
      return [...roles, ...(PROCESOS[p] || [])];
    }, []);
  }
  return PROCESOS[process] || [];
}
const checkRolesCorrespondence = (process, roles) => {
  const processRoles = getRolesFromProcess(process);
  return roles.some((role) => processRoles.includes(role));
};

export const useGetAuthorization = (process) => {
  const { roles } = useSelector((state) => state.usuario);
  const isAuthorized = useMemo(
    () => checkRolesCorrespondence(process, roles),
    [process, roles]
  );
  return { isAuthorized };
};
