import { useDispatch, useSelector } from "react-redux";

export const useCatalogos = () => {
  const dispatch = useDispatch();
  const { isLoading, catalogos, error } = useSelector(
    (state) => state.catalogos
  );
};
