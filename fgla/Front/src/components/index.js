import withLazyLoading from './HOCs/withLazyLoading';

const Loader = withLazyLoading(() => import(/* webpackChunkName: "LazyComponent-Loader" */ './Loader'));
const MainTitle = withLazyLoading(() => import(/* webpackChunkName: "LazyComponent-MainTitle" */ './MainTitle'));
const LoaderLazzy = withLazyLoading(() => import(/* webpackChunkName: "LazyComponent-LoaderLazzy" */ './LoaderLazzy'));
const Accordion = withLazyLoading(() => import(/* webpackChunkName: "LazyComponent-Accordion" */ './Accordion'));
const VerDocumento = withLazyLoading(() => import(/* webpackChunkName: "LazyComponent-VerDocumento" */ './VerDocumento'));
const UnderConstruction = withLazyLoading(() => import(/* webpackChunkName: "LazyComponent-UnderConstruction" */ './UnderConstruction'));
const SesionCaducada = withLazyLoading(() => import(/* webpackChunkName: "LazyComponent-SesionCaducada" */ './SesionCaducada'));
const LabelValue = withLazyLoading(() => import(/* webpackChunkName: "LazyComponent-LabelValue" */ './LabelValue'));
const Sidebar = withLazyLoading(() => import(/* webpackChunkName: "LazyComponent-Sidebar" */ './Sidebar'));
const ShowMessage = withLazyLoading(() => import(/* webpackChunkName: "LazyComponent-ShowMessage" */ './Messages'));
const Comentarios = withLazyLoading(() => import(/* webpackChunkName: "LazyComponent-Comentarios" */ './Comentarios'));
const ComentariosSimple = withLazyLoading(() => import(/* webpackChunkName: "LazyComponent-ComentariosSimple" */ './ComentariosSimple'));
const ModalSatCloud = withLazyLoading(() => import(/* webpackChunkName: "LazyComponent-ModalSatCloud" */ './ModalSatCloud'));
const { Tooltip } = withLazyLoading(() => import(/* webpackChunkName: "LazyComponent-Tooltip" */ './Tooltip'));

export {
    Loader,
    MainTitle,
    LoaderLazzy,
    Accordion,
    VerDocumento,
    UnderConstruction,
    SesionCaducada,
    LabelValue,
    Sidebar,
    ShowMessage,
    Comentarios,
    ComentariosSimple,
    ModalSatCloud,
    Tooltip
};