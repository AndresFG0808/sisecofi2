export const ROUTES = {

  inicio: {
    id: 0,
    name: 'home',
    title: 'Inicio',
    path: '/app/pe/sisecofi/',
    component: 'Inicio',
    exact: true,
    layout: 'main',
    active: false,
  },

  home: {
    id: 0,
    name: 'home',
    title: 'Inicio',
    path: '/home',
    component: 'Inicio',
    exact: true,
    layout: 'main',
    active: true,
  },

  sistema: {
    title: 'Sistema',
    path: '/sistema/*',
    menuPath: '/sistema',
    component: 'Sistema',
    active: true,
    process: "SISTEMA"
  },

  proveedores: {
    title: 'Proveedores',
    path: '/proveedores/*',
    menuPath: '/proveedores',
    component: 'Proveedores',
    active: true,
    process: "PROVEEDORES"
  },

  proyectos: {
    title: 'Proyectos',
    path: '/proyectos/*',
    menuPath: '/proyectos',
    component: 'Proyectos',
    active: true,
    process: "PROYECTOS"
  },

  contratos: {
    title: 'Contratos',
    path: '/contratos/*',
    menuPath: '/contratos',
    component: 'Contratos',
    active: true,
    process: "CONTRATOS"
  },

  consumoServicios: {
    title: 'Consumo de Servicios',
    path: '/consumoServicios/*',
    menuPath: '/consumoServicios',
    component: 'ConsumoServicios',
    active: true,
    process: "CONSUMO_SERVICIOS"
  },

  reintegros: {
    title: 'Reintegros',
    path: '/reintegros/*',
    menuPath: '/reintegros',
    component: 'Reintegros',
    active: true,
    process: "REINTEGROS"
  },

  reportes: {
    title: 'Reportes',
    path: '/reportes/*',
    menuPath: '/reportes',
    component: 'Reportes',
    active: true,
    process: "REPORTES"
  },

  module: {
    title: 'module',
    path: '/module/*',
    menuPath: '/module',
    component: '',
    active: false
  },
};