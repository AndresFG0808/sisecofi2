import Keycloak from 'keycloak-js';

// Configuración de Keycloak
const keycloakConfig = {
  url: 'http://localhost:8081',
  realm: 'sisecofi',
  clientId: 'sisecofi-client',
};

const keycloak = new Keycloak(keycloakConfig);

export default keycloak;