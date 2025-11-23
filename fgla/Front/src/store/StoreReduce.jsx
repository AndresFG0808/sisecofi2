
const types = {
    mostrar: 'mostar-ventana',
    ocultar: 'ocultar-ventana',
    cargando: 'mostrar-barra-cargando',
    nocargando: 'ocultar-barra-cargando',
}

const initialStore = {
    debug: false,
    modal: false,
    cargando: false,
    mensajes: {
        id: 'Mensaje',
        msj: 'Error desconocido',
        detalle: ''
    }
}

const storeReducer = (state, action) => {

    if (action.data?.status === 404) {
        initialStore.mensajes.msj = "Recurso no encontrado";
        initialStore.mensajes.detalle = action.data.statusText;
    } else if (action.data?.status === 400) {
        initialStore.mensajes.msj = action.data?.data.mensaje
        if (initialStore.debug) {
            initialStore.mensajes.detalle = action.data?.data?.traza || action.data?.data?.trace;
        }
    } else if (action.data?.status === 500) {
        initialStore.mensajes.msj = "Error desconocido";
        if (initialStore.debug) {
            initialStore.mensajes.detalle = action.data?.data?.traza || action.data?.data?.trace;
        }
    }
    switch (action.type) {
        case types.cargando:
            return {
                ...state,
                cargando: true
            }
        case types.mostrar:
            return {
                ...state,
                modal: true
            }
        case types.ocultar:
            return {
                ...state,
                modal: false
            }

        case types.nocargando:
            return {
                ...state,
                cargando: false
            }
        default:
            return state;
    }
}

export { initialStore, types }
export default storeReducer;