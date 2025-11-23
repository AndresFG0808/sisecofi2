import { lazy } from 'react'
import Inicio from '../../pages/Inicio';
import Login from '../../pages/Login';
import LoginForm from '../../pages/Login/LoginForm';

const LazySistema = lazy( () => import(/* webpackChunkName: "LazySistema" */ '../../pages/Sistema'));
const LazyProveedores = lazy( () => import(/* webpackChunkName: "LazyProveedores" */ '../../pages/Proveedores'));
const LazyProyectos = lazy( () => import(/* webpackChunkName: "LazyProyectos" */ '../../pages/AdministrarProyectos'));
const LazyContratos = lazy( () => import(/* webpackChunkName: "LazyContratos" */ '../../pages/AdministrarContratos'));
const LazyConsumoServicios = lazy( () => import(/* webpackChunkName: "LazyConsumoServicios" */ '../../pages/ConsumoServicios'));
const LazyReintegros = lazy( () => import(/* webpackChunkName: "LazyReintegros" */ '../../pages/Reintegros'));
const LazyReportes = lazy( () => import(/* webpackChunkName: "LazyReportes" */ '../../pages/Reportes'));

export const DYNAMIC_COMPONENTS = {
  Inicio: <Inicio />,
  Login: <Login />,
  LoginForm: <LoginForm />,

  Sistema: <LazySistema />,
  Proveedores: <LazyProveedores />,
  Proyectos: <LazyProyectos />,
  Contratos: <LazyContratos />,
  ConsumoServicios: <LazyConsumoServicios />,
  Reintegros: <LazyReintegros />,
  Reportes: <LazyReportes />
};
