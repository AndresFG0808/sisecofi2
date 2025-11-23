import axiosInterceptor from './Interceptor';


export const postFormData = (uri, formData) => {
    const fetchData = async () => {
        try {
            const response = await axiosInterceptor.post(uri, formData);
            return response;
        } catch (error) {
            console.log("Error en peticion post " + error);
        }
        return fetchData;
    }
    return fetchData;
}

export const getDatas = (uri) => {
    const fetchData = async () => {
        try {
            const response = await axiosInterceptor.get(uri);
            return response;
        } catch (error) {
            console.log("Error en peticion get " + error);
        }
        return fetchData;
    }
    return fetchData;
}

export const getFile = (uri) => {
    const fetchData = async () => {
        try {
            const response = await axiosInterceptor.get(uri, { responseType: 'blob' });
            return response;
        } catch (error) {
            console.log("Error en peticion getFile " + error);
        }
        return fetchData;
    }
    return fetchData;
}