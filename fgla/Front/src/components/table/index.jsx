import withLazyLoading from '../HOCs/withLazyLoading';

const TablaDinamica = withLazyLoading(() => import(/* webpackChunkName: "LazyComponent-TablaDinamica" */ './TablaDinamica'));
const TablaEditable = withLazyLoading(() => import(/* webpackChunkName: "LazyComponent-TablaEditable" */ './TablaEditable'));
const TablaPaginada = withLazyLoading(() => import(/* webpackChunkName: "LazyComponent-TablaPaginada" */ './TablaPaginada'));
const TablaSimple = withLazyLoading(() => import(/* webpackChunkName: "LazyComponent-TablaSimple" */ './TablaSimple'));

export {
    TablaDinamica,
    TablaEditable,
    TablaPaginada,
    TablaSimple
}