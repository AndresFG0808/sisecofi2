import React, { useEffect } from "react";
import Loader from "./Loader";
import { useUi } from "../hooks/useUi";

export function GlobalLoader() {
  const { isLoading } = useUi();

  useEffect(() => {
    document.body.style.overflow = isLoading ? "hidden" : "unset";
  }, [isLoading]);

  if (!isLoading) return <></>;

  return (
    <Loader />
  );
}
