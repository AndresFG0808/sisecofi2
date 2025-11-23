import { createContext, useReducer } from "react";
import storeReducer, { initialStore } from "./StoreReduce";
import ModalError from "../servicios/ModalError";
import Loader from '../components/Loader';


const StoreContext = createContext();

const StoreProvider = ({ children }) => {
    const [store, dispath] = useReducer(storeReducer, initialStore);
    return (
        <StoreContext.Provider value={[store, dispath]}>
            {children}
            {
                store.modal && (
                    <ModalError mostrar={store} />
                )
            }
            {
                store.cargando && (
                    <Loader />
                )
            }
        </StoreContext.Provider>
    )
}

export { StoreContext }
export default StoreProvider;