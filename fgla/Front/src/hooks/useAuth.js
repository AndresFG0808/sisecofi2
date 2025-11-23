import { useKeycloak } from '@react-keycloak/web';
import { useEffect } from 'react';

export const useAuth = () => {
  const { keycloak, initialized } = useKeycloak();
  
  const isKeycloakMode = process.env.NODE_ENV === 'development' || 
                         window.config?.env?.USE_KEYCLOAK === 'true';

  useEffect(() => {
    if (initialized && keycloak.authenticated && isKeycloakMode) {
      // Procesar y sincronizar información del usuario
      try {
        const decoded = JSON.parse(atob(keycloak.token.split('.')[1]));
        
        // Extraer roles de Keycloak
        const keycloakRoles = decoded.resource_access?.[keycloak.clientId]?.roles || [];
        const satRoles = keycloakRoles.filter(role => role.startsWith('SAT_SISECOFI_'));
        const rolesString = satRoles.map(role => `cn=${role}`).join(',');
        
        // Nombre completo
        const nombreCompleto = decoded.name || 
                              decoded.preferred_username || 
                              (decoded.given_name && decoded.family_name ? 
                               `${decoded.given_name} ${decoded.family_name}` : 
                               'Usuario Keycloak');

        // Sincronizar con sessionStorage
        sessionStorage.setItem('access_token', keycloak.token);
        sessionStorage.setItem('user_name', nombreCompleto);
        sessionStorage.setItem('user_roles', rolesString);
        
        if (keycloak.refreshToken) {
          sessionStorage.setItem('refreshToken', keycloak.refreshToken);
        }

        console.log('=== HOOK useAuth - Información sincronizada ===');
        console.log('Usuario:', nombreCompleto);
        console.log('Roles:', satRoles);
        
      } catch (error) {
        console.error('Error procesando token en useAuth:', error);
        // Fallback básico
        sessionStorage.setItem('access_token', keycloak.token);
        sessionStorage.setItem('user_name', keycloak.tokenParsed?.preferred_username || 'Usuario');
      }
    }
  }, [initialized, keycloak.authenticated, keycloak.token, isKeycloakMode]);

  const login = () => {
    if (isKeycloakMode) {
      keycloak.login();
    } else {
      // Mantener lógica SAT existente
      window.location.href = '/login';
    }
  };

  const logout = () => {
    if (isKeycloakMode) {
      keycloak.logout({
        redirectUri: window.location.origin
      });
    } else {
      // Mantener lógica SAT existente
      sessionStorage.clear();
      window.location.href = '/login';
    }
  };

  const isAuthenticated = () => {
    if (isKeycloakMode) {
      return initialized && keycloak.authenticated;
    }
    return sessionStorage.getItem('access_token') !== null;
  };

  const getToken = () => {
    if (isKeycloakMode) {
      return keycloak.token;
    }
    return sessionStorage.getItem('access_token');
  };

  const getUserInfo = () => {
    if (isKeycloakMode && keycloak.tokenParsed) {
      return {
        name: keycloak.tokenParsed.name || keycloak.tokenParsed.preferred_username,
        email: keycloak.tokenParsed.email,
        roles: keycloak.tokenParsed.resource_access?.[keycloak.clientId]?.roles || []
      };
    }
    return {
      name: sessionStorage.getItem('user_name'),
      roles: []
    };
  };

  return {
    isAuthenticated: isAuthenticated(),
    login,
    logout,
    getToken,
    getUserInfo,
    keycloak: isKeycloakMode ? keycloak : null,
    initialized: isKeycloakMode ? initialized : true,
    isKeycloakMode
  };
};