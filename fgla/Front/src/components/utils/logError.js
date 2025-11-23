export function logError(error, contextMessage = 'Error en la operación') {
  if (process.env.NODE_ENV !== 'production') {
    console.error(`[${contextMessage}]`, error);
  } else {
    console.error(`${contextMessage}. Ocurrió un error.`);
  }
}
