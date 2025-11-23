import axios from 'axios'
import { config } from '../constants/config';
import { useContext, useEffect } from 'react';
import { StoreContext } from '../store/StoreProvider';
import { types } from '../store/StoreReduce';
import { useNavigate } from 'react-router-dom';
import { TOKEN_SESSION } from '../constants/config/token';
import { RUTA_SERVICIOS } from '../constants/api/rutas';



const { baseUrl } = config;
const url = baseUrl;

const instance = axios.create({
    baseURL: url,
    headers: {
        "Authorization": TOKEN_SESSION.BEARER + sessionStorage.getItem(TOKEN_SESSION.GUARDAR_TOKEN),
        "Access-Control-Allow-Origin": "*",
        "Access-Control-Allow-Credentials": "true",
        "Access-Control-Allow-Methods": "DELETE, POST, GET, OPTIONS",
        "Access-Control-Allow-Headers": "Content-Type, Authorization, X-Requested-With"
    }
})

const AxiosInterceptor = ({ children }) => {
    const navigate = useNavigate();
    const [store, dispath] = useContext(StoreContext);
    useEffect(() => {
        const resInterceptor = response => {
            return response;
        }
        const errInterceptor = error => {
            console.log("Error peticion estatus:  " + error.response);
            if (error.response.status === 401) {
                navigate(RUTA_SERVICIOS.LOGIN);
            } if (error.response.status === 403) {
                navigate(RUTA_SERVICIOS.LOGIN);
            } else {
                console.log("Error peticion:  " + error.response);
                dispath({ type: types.mostrar, data: error.response });
            }
            return Promise.reject(error);
        }
        const interceptor = instance.interceptors.response.use(resInterceptor, errInterceptor);
        return () => instance.interceptors.response.eject(interceptor);
    }, [navigate])
    return children;
}


export default instance;
export { AxiosInterceptor }

