import keycloak from '../keycloak';
import jwtDecode from 'jwt-decode';

class AuthService {
  constructor() {
    this.isKeycloakMode = process.env.NODE_ENV === 'development' || 
                          window.config?.env?.USE_KEYCLOAK === 'true';
  }

  // Método para inicializar autenticación
  async initAuth() {
    if (this.isKeycloakMode) {
      return this.initKeycloak();
    }
    // Para producción, mantener lógica SAT existente
    return true;
  }

  // Inicializar Keycloak
  async initKeycloak() {
    try {
      const authenticated = await keycloak.init({
        onLoad: 'login-required',
        silentCheckSsoRedirectUri: window.location.origin + '/silent-check-sso.html',
        pkceMethod: 'S256'
      });

      if (authenticated) {
        // Almacenar token en sessionStorage para compatibilidad
        sessionStorage.setItem('access_token', keycloak.token);
        sessionStorage.setItem('refreshToken', keycloak.refreshToken);
        
        // Extraer información del usuario y roles
        this.processKeycloakToken();
        
        return true;
      }
      return false;
    } catch (error) {
      console.error('Error inicializando Keycloak:', error);
      return false;
    }
  }

  // Procesar token de Keycloak y adaptarlo al formato SAT
  processKeycloakToken() {
    try {
      const decoded = jwtDecode(keycloak.token);
      console.log('Token Keycloak decodificado:', decoded);

      // Extraer roles de Keycloak
      const keycloakRoles = decoded.resource_access?.[keycloak.clientId]?.roles || [];
      const satRoles = keycloakRoles.filter(role => role.startsWith('SAT_SISECOFI_'));
      
      // Formatear roles para compatibilidad con el sistema existente
      const rolesString = satRoles.map(role => `cn=${role}`).join(',');
      
      // Crear nombre completo basado en el token
      const nombreCompleto = decoded.name || 
                            decoded.preferred_username || 
                            decoded.given_name + ' ' + decoded.family_name ||
                            'Usuario Keycloak';
      
      // Crear token compatible con el formato SAT mejorado
      const compatibleTokenData = {
        ...decoded,
        nombreCompleto: nombreCompleto,
        roles: rolesString,
        exp: decoded.exp,
        iat: decoded.iat
      };

      // Crear un token JWT compatible que incluya la información SAT
      const enhancedToken = this.createEnhancedToken(compatibleTokenData);

      // Almacenar información del usuario en el formato esperado
      sessionStorage.setItem('user_name', nombreCompleto);
      sessionStorage.setItem('accessTokenSession', enhancedToken);
      sessionStorage.setItem('access_token', enhancedToken);
      sessionStorage.setItem('habilitarbtn', 'ocultame');
      sessionStorage.setItem('habilitarmenu', 'mostrar');

      // También almacenar el token original de Keycloak
      sessionStorage.setItem('keycloak_token', keycloak.token);

      console.log('=== INFORMACIÓN PROCESADA ===');
      console.log('Nombre completo:', nombreCompleto);
      console.log('Roles SAT extraídos:', satRoles);
      console.log('Token mejorado creado');

    } catch (error) {
      console.error('Error procesando token Keycloak:', error);
    }
  }

  // Crear token mejorado que incluya información SAT
  createEnhancedToken(tokenData) {
    try {
      // Crear un payload mejorado
      const enhancedPayload = {
        sub: tokenData.sub,
        iat: tokenData.iat,
        exp: tokenData.exp,
        nombreCompleto: tokenData.nombreCompleto,
        roles: tokenData.roles,
        preferred_username: tokenData.preferred_username,
        name: tokenData.name,
        // Incluir roles en formato individual para compatibilidad
        resource_access: tokenData.resource_access
      };

      // Para desarrollo, crear un token base64 simple (no usar en producción)
      const header = btoa(JSON.stringify({ typ: 'JWT', alg: 'HS256' }));
      const payload = btoa(JSON.stringify(enhancedPayload));
      const signature = btoa('keycloak-dev-signature');
      
      return `${header}.${payload}.${signature}`;
    } catch (error) {
      console.error('Error creando token mejorado:', error);
      return keycloak.token; // Fallback al token original
    }
  }

  // Verificar si el usuario está autenticado
  isAuthenticated() {
    if (this.isKeycloakMode) {
      return keycloak.authenticated && keycloak.token;
    }
    // Lógica existente para SAT
    return sessionStorage.getItem('access_token') !== null;
  }

  // Obtener token actual
  getToken() {
    if (this.isKeycloakMode) {
      return keycloak.token;
    }
    return sessionStorage.getItem('access_token');
  }

  // Renovar token
  async refreshToken() {
    if (this.isKeycloakMode) {
      try {
        const refreshed = await keycloak.updateToken(30);
        if (refreshed) {
          sessionStorage.setItem('access_token', keycloak.token);
          console.log('Token Keycloak renovado');
        }
        return refreshed;
      } catch (error) {
        console.error('Error renovando token Keycloak:', error);
        return false;
      }
    }
    // Mantener lógica existente para SAT
    return false;
  }

  // Cerrar sesión
  logout() {
    if (this.isKeycloakMode) {
      keycloak.logout({
        redirectUri: window.location.origin
      });
    }
    // Lógica existente para SAT se mantiene en App.js
  }

  // Obtener información del usuario
  getUserInfo() {
    if (this.isKeycloakMode) {
      return {
        name: keycloak.tokenParsed?.name || keycloak.tokenParsed?.preferred_username,
        email: keycloak.tokenParsed?.email,
        roles: this.getUserRoles()
      };
    }
    return {
      name: sessionStorage.getItem('user_name'),
      roles: []
    };
  }

  // Obtener roles del usuario
  getUserRoles() {
    if (this.isKeycloakMode && keycloak.tokenParsed) {
      const roles = keycloak.tokenParsed.resource_access?.[keycloak.clientId]?.roles || [];
      return roles.filter(role => role.startsWith('SAT_SISECOFI_'));
    }
    return [];
  }
}

export default new AuthService();