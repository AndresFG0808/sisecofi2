import React from "react";
import { useGetAuthorization } from "../hooks/useGetAuthorization";
import { Navigate } from "react-router-dom";

export default function Authorization({
  children,
  process,
  redirect,
  ...otherProps
}) {
  const { isAuthorized } = useGetAuthorization(process);

  if (!isAuthorized && redirect) return redirect; // Redireccionar
  if (!isAuthorized) return null;
  return <>{children}</>;
}
