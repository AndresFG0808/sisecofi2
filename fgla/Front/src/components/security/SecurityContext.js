import React, { useContext } from "react";

const SecurityContext = React.createContext();

export const useAuth = () => useContext(SecurityContext);

export default SecurityContext;