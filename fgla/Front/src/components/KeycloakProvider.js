import React from 'react';
import { ReactKeycloakProvider } from '@react-keycloak/web';
import keycloak from '../keycloak';

const KeycloakProvider = ({ children }) => {
  const isKeycloakMode = process.env.NODE_ENV === 'development' || 
                         window.config?.env?.USE_KEYCLOAK === 'true';

  // Configuración de eventos de Keycloak
  const keycloakProviderInitConfig = {
    onLoad: 'login-required',
    silentCheckSsoRedirectUri: window.location.origin + '/silent-check-sso.html',
    pkceMethod: 'S256'
  };

  const handleKeycloakEvent = (event, error) => {
    console.log('Evento Keycloak:', event, error);
    
    if (event === 'onAuthSuccess') {
      console.log('Autenticación Keycloak exitosa');
      // Almacenar token para compatibilidad
      sessionStorage.setItem('access_token', keycloak.token);
      sessionStorage.setItem('refreshToken', keycloak.refreshToken);
    }
    
    if (event === 'onTokenExpired') {
      console.log('Token Keycloak expirado, renovando...');
      keycloak.updateToken(30);
    }
  };

  const handleKeycloakTokens = (tokens) => {
    console.log('Tokens Keycloak recibidos');
    // Actualizar tokens en sessionStorage
    if (tokens.token) {
      sessionStorage.setItem('access_token', tokens.token);
    }
    if (tokens.refreshToken) {
      sessionStorage.setItem('refreshToken', tokens.refreshToken);
    }
  };

  // Si no es modo Keycloak, devolver children directamente
  if (!isKeycloakMode) {
    return <>{children}</>;
  }

  return (
    <ReactKeycloakProvider
      authClient={keycloak}
      initOptions={keycloakProviderInitConfig}
      onEvent={handleKeycloakEvent}
      onTokens={handleKeycloakTokens}
      LoadingComponent={<div>Cargando autenticación...</div>}
    >
      {children}
    </ReactKeycloakProvider>
  );
};

export default KeycloakProvider;