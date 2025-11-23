import { useDispatch, useSelector } from "react-redux";
import {
  onLoading,
  onNotLoading,
  onActivateLoading,
} from "../store/ui/uiSlice";

export const useUi = () => {
  const dispatch = useDispatch();
  const { isLoading } = useSelector((state) => state.ui);

  const openLoader = () => {
    dispatch(onLoading());
  };

  const closeLoader = () => {
    dispatch(onNotLoading());
  };

  const activateLoader = (state) => {
    dispatch(onActivateLoading(state));
  };

  return {
    isLoading,
    openLoader,
    closeLoader,
    activateLoader,
  };
};
