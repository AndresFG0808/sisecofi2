import { toast } from 'react-toastify';

export const useToast = () => {

    let configure = {
        position: toast.POSITION.TOP_RIGHT,
        autoClose: 5000,
        hideProgressBar: false,
        closeButton: true,
        pauseOnHover: true
    };

    const successToast = (message) => toast.success(message, configure);
    const errorToast = (message) => toast.error(message, configure);
    const warningToast = (message) => toast.warning(message, configure);
    const infoToast = (message) => toast.info(message, configure);

    return {
        successToast,
        errorToast,
        warningToast,
        infoToast,
    };
};
