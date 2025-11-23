package com.sisecofi.libreria.comunes.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Value;
import org.aarboard.nextcloud.api.NextcloudConnector;
import org.aarboard.nextcloud.api.ServerConfig;
import org.aarboard.nextcloud.api.filesharing.FilesharingConnector;
import org.aarboard.nextcloud.api.filesharing.Share;
import org.aarboard.nextcloud.api.filesharing.SharePermissions;
import org.aarboard.nextcloud.api.filesharing.ShareType;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import java.util.Set;
import com.sisecofi.libreria.comunes.dto.CarpetaCompartidaDto;
import com.sisecofi.libreria.comunes.dto.CompartidoCloudModel;
import com.sisecofi.libreria.comunes.service.NexusService;
import com.sisecofi.libreria.comunes.util.PasswordGeneratorUtil;
import com.sisecofi.libreria.comunes.util.archivo.ConvertirArchivoZip;
import com.sisecofi.libreria.comunes.util.enums.ErroresSistema;
import com.sisecofi.libreria.comunes.util.exception.DescargaArchivoException;
import com.sisecofi.libreria.comunes.util.exception.NexusException;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author ayuso2104@gmail.com
 *
 */

@Slf4j
public abstract class NexusServiceImpl implements NexusService {

	private static final String PAPELERA = "/PAPELERA/";
	private static final String ERROR_AL_ELIMINAR = "No se pudo eliminar el archivo temporal: {}";
	private static final String PAHT = "Path base: {}";
	private static final String REGEX = "^[\\p{L}0-9 _.-]+$";
	private final Set<String> dirCache =Collections.newSetFromMap(new ConcurrentHashMap<>());

    @Value("${sat.cloud.server.name}") private String server; 
    @Value("${nexus.https:true}") private boolean https;
    @Value("${sat.cloud.server.port:443}") private int port;
    @Value("${sat.cloud.server.user}") private String user;
    @Value("${sat.cloud.server.pass}") private String pass;
    private ServerConfig sc;
    @Value("${nexus.carga.habilitada:true}")
	private boolean cargaHabilitada;
    private ServerConfig ocsConfig;

    private volatile NextcloudConnector connector;

    @PostConstruct
    void init() throws MalformedURLException {
      connector = new NextcloudConnector(server, user, pass);
		
		try {
	        URL url = new URL(server); 
	        boolean httpsEff = "https".equalsIgnoreCase(url.getProtocol());
	        int portEff = (url.getPort() > 0) ? url.getPort() : (httpsEff ? 443 : 80);

	        String contextRoot = extractContextRoot(url.getPath()); 
	        ServerConfig sc = new ServerConfig(url.getHost(), httpsEff, portEff, user, pass);
	        if (!contextRoot.isBlank()) {
	            sc.setSubPathPrefix(contextRoot);
	        }
	        this.ocsConfig = sc; 
	    } catch (Exception e) {
	        throw new IllegalStateException("URL de Nextcloud inválida: " + server, e);
	    }
    }
    
    private String extractContextRoot(String fullPath) {
        if (fullPath == null || fullPath.isBlank() || "/".equals(fullPath)) return "";
        String p = fullPath.replaceAll("/{2,}", "/"); 
        int idx = p.indexOf("/remote.php");
        String context = (idx >= 0) ? p.substring(0, idx) : p; 
        if (context.endsWith("/")) context = context.substring(0, context.length() - 1);
        return context;
    }
	

      private NextcloudConnector getConnector() { return connector; }

      @PreDestroy
      public void onDestroy() {
          log.info("Finalizando NexusServiceImpl; el conector Nextcloud no requiere cierre explícito.");
      }
	
      
      
	@Override
	public NextcloudConnector conectar() throws NexusException {
		return getConnector();
	}

	@Override
	public void generarCarpetasVacias(String path) {
	    try {
	        if (!cargaHabilitada) {
	            return;
	        }

	        String rutaNormalizada = normalizarDirectorio(path);

	        String soloDirectorio = soloDirectorioDe(rutaNormalizada);

	        if (soloDirectorio.isEmpty() || "/".equals(soloDirectorio)) {
	            return;
	        }

	        NextcloudConnector nc = getConnector();

	        generarCarpetas(nc, soloDirectorio);

	        log.info("Carpetas vacías generadas exitosamente en la ruta: {}", soloDirectorio);
	    } catch (Exception e) {
	        log.error("Error inesperado al generar carpetas vacías en la ruta: {}", path, e);
	        throw new DescargaArchivoException("Error inesperado al generar carpetas vacías.", e);
	    }
	}
	
	
	private String normalizarDirectorio(String p) {
	    if (p == null) return "";
	    String s = p.trim().replace('\\', '/');

	    while (s.contains("//")) s = s.replace("//", "/");

	    if (!s.isEmpty() && !s.startsWith("/")) s = "/" + s;

	    if (s.length() > 1 && s.endsWith("/")) s = s.substring(0, s.length() - 1);

	    return s;
	}

	private String soloDirectorioDe(String ruta) {
	    if (ruta == null || ruta.isEmpty() || "/".equals(ruta)) return ruta;

	    int lastSlash = ruta.lastIndexOf('/');
	    String lastSegment = (lastSlash >= 0) ? ruta.substring(lastSlash + 1) : ruta;


	    boolean pareceArchivo = lastSegment.contains(".") && !lastSegment.startsWith(".");
	    if (pareceArchivo && lastSlash > 0) {
	        return ruta.substring(0, lastSlash);
	    }
	    return ruta;
	}

	@Override
	public boolean cargarArchivo(InputStream inputStream, String remotePath, String nombreArchivo)
	        throws NexusException {
	    if (!cargaHabilitada) {
	        return true;
	    }

	    File tempFile = null;
	    try {
	        String archivoSeguro = FilenameUtils.getName(nombreArchivo == null ? "" : nombreArchivo);
	        Matcher matcher = Pattern.compile(REGEX, Pattern.UNICODE_CHARACTER_CLASS).matcher(archivoSeguro);
	        if (!matcher.matches()) {
	            throw new IllegalArgumentException("Nombre de archivo inválido: " + archivoSeguro);
	        }

	        String dirNormalizado = normalizarDirectorio(remotePath);
	        if (dirNormalizado.isEmpty() || "/".equals(dirNormalizado)) {
	            throw new IllegalArgumentException("Ruta remota inválida (directorio vacío): " + remotePath);
	        }

	        String rutaCompleta = joinRemoto(dirNormalizado, archivoSeguro);
	        log.info("Subiendo archivo '{}' a '{}'", archivoSeguro, rutaCompleta);

	        NextcloudConnector nc = getConnector();

	        generarCarpetas(nc, dirNormalizado);

	        tempFile = File.createTempFile("upload_", "_" + UUID.randomUUID());
	        FileUtils.copyInputStreamToFile(inputStream, tempFile);

	        nc.uploadFile(tempFile, rutaCompleta);

	        log.info("Archivo cargado exitosamente: {}", archivoSeguro);
	        return true;

	    } catch (Exception e) {
	        log.error("Error al cargar el archivo '{}' en la ruta '{}'", nombreArchivo, remotePath, e);
	        throw new NexusException(ErroresSistema.ERROR_NEXUS_CARGAR_ARCHIVO, e);
	    } finally {
	        if (tempFile != null) {
	            try {
	                Files.deleteIfExists(tempFile.toPath());
	                log.debug("Archivo temporal eliminado: {}", tempFile.getAbsolutePath());
	            } catch (IOException ex) {
	                log.warn(ERROR_AL_ELIMINAR, tempFile.getAbsolutePath(), ex);
	            }
	        }
	    }
	}
	

	
	private String joinRemoto(String dir, String fileName) {
	    if (dir.endsWith("/")) return dir + fileName;
	    return dir + "/" + fileName;
	}

	@Override
	public boolean cargarArchivos(InputStream inputStream, String remotePath, String nombreArchivo)
			throws NexusException {
		if (!cargaHabilitada) {
			return true;
		}
	
		return cargarArchivo(inputStream,remotePath,nombreArchivo);
	}

	@Override
	public boolean crearFolder(InputStream inputStream, String path, String nombreArchivo) throws NexusException {
		if (!cargaHabilitada) {
			return true;
		}
		
		return cargarArchivo(inputStream,path,nombreArchivo);
	}
	

	@Override
	public InputStream descargarFolder(String remotePath) throws NexusException {
	    if (!cargaHabilitada) {
	        return new ByteArrayInputStream(new byte[0]);
	    }

	    NextcloudConnector nc = getConnector();
	    Path tempRoot = null;
	    Path downloadDir = null;
	    Path zipPath = null;

	    try {
	        log.info("Descargar carpeta: {}", remotePath);

	        String rutaNorm = normalizarDirectorio(remotePath);
	        if (rutaNorm.isEmpty() || "/".equals(rutaNorm)) {
	            throw new IllegalArgumentException("Ruta remota inválida: " + remotePath);
	        }

	        String lastSegment = nombreArchivo(rutaNorm, "/");
	        String etiqueta = FilenameUtils.getName(lastSegment == null ? "" : lastSegment);
	        if (!Pattern.compile(REGEX, Pattern.UNICODE_CHARACTER_CLASS).matcher(etiqueta).matches()) {
	            throw new IllegalArgumentException("Nombre de carpeta inválido: " + etiqueta);
	        }

	        tempRoot = Files.createTempDirectory("nx_dl_");
	        downloadDir = tempRoot.resolve(etiqueta);
	        Files.createDirectories(downloadDir);
	        log.info("Path base: {}", tempRoot.toAbsolutePath());


	        nc.downloadFolder(rutaNorm, downloadDir.toString());

	        zipPath = tempRoot.resolve(etiqueta + ".zip");
	        ConvertirArchivoZip.zipDir(downloadDir.toFile(), zipPath.toFile());

	        return deleteOnCloseInputStream(zipPath, downloadDir, tempRoot);

	    } catch (IOException | SecurityException e) {
	        log.error("Error durante la descarga o empaquetado de la carpeta '{}'", remotePath, e);
	        throw new DescargaArchivoException("No se pudo completar la descarga de la carpeta", e);
	    } catch (Exception e) {
	        throw new NexusException(ErroresSistema.ERROR_NEXUS_DESCARGAR_ARCHIVO, e);
	    }
	}
	
	
	private InputStream deleteOnCloseInputStream(Path zipPath, Path downloadDir, Path tempRoot) throws IOException {
	    final InputStream base = Files.newInputStream(zipPath, java.nio.file.StandardOpenOption.DELETE_ON_CLOSE);
	    return new java.io.FilterInputStream(base) {
	        @Override
	        public void close() throws IOException {
	            super.close(); 
	            safeDeleteTree(downloadDir);
	            safeDeleteTree(tempRoot);
	        }
	    };
	}

	@Override
	public boolean borrarFolder(String path) throws NexusException {
	    if (!cargaHabilitada) {
	        return true;
	    }

	    final String dir = normalizarDirectorio(path);

	    if (dir.isEmpty() || "/".equals(dir)) {
	        throw new IllegalArgumentException("Ruta inválida para borrar carpeta: " + path);
	    }

	    final NextcloudConnector nc = getConnector();

	    try {
	        log.info("Intentando borrar carpeta en Nextcloud: {}", dir);
	        nc.deleteFolder(dir); 
	        log.info("Carpeta borrada: {}", dir);
	        return true;

	    } catch (org.aarboard.nextcloud.api.exception.NextcloudApiException e) {
	        final String msg = e.getMessage() == null ? "" : e.getMessage();

	        if (msg.contains("404") || msg.contains("Not Found")) {
	            log.warn("La carpeta no existe (404) en la ruta: {}", dir);
	            return false; 
	        }

	        log.error("Error al borrar carpeta en '{}': {}", dir, msg, e);
	        throw new NexusException(ErroresSistema.ERROR_NEXUS_BORRAR_FOLDER, e);

	    } catch (Exception e) {
	        log.error("Error inesperado al borrar la carpeta '{}'", dir, e);
	        throw new NexusException(ErroresSistema.ERROR_NEXUS_BORRAR_FOLDER, e);
	    }
	}

	@Override
	public CompartidoCloudModel compartirSoloLectura(String path) throws NexusException {
	    try {
	        String ruta = normalizarDirectorio(path);
	        if (ruta.isEmpty() || "/".equals(ruta)) {
	            throw new IllegalArgumentException("Ruta inválida para compartir: " + path);
	        }

	        NextcloudConnector nc = getConnector();

	        FilesharingConnector sharing = new FilesharingConnector(ocsConfig);

	        Collection<Share> shares = nc.getShares(ruta, false, false);
	        for (Share s : shares) {
	            if (s.getShareType() == ShareType.PUBLIC_LINK) {
	                try { nc.deleteShare(s.getId()); } catch (Exception ignore) {}
	            }
	        }

	        String password = PasswordGeneratorUtil.generateCommonLangPassword();
	        SharePermissions perms = new SharePermissions(SharePermissions.SingleRight.READ); // solo lectura

	        Share nuevo = sharing.doShare(
	            ruta,
	            ShareType.PUBLIC_LINK,
	            "",
	            false,     
	            password,
	            perms
	        );

	        var out = new CompartidoCloudModel();
	        out.setUrl(nuevo.getUrl());
	        out.setPassword(password);
	        return out;

	    } catch (Exception e) {
	        throw new NexusException(ErroresSistema.ERROR_NEXUS_COMPARTIR_ARCHIVO, e);
	    }
	}

	@Override
	public InputStream descargarArchivo(String remotePath) throws NexusException {
	    if (!cargaHabilitada) {
	        return new ByteArrayInputStream(new byte[0]);
	    }

	    final NextcloudConnector nc = getConnector();
	    Path tempRoot = null;
	    Path targetFile = null;

	    try {
	        log.info("Descargar archivo: {}", remotePath);

	        String rutaNorm = normalizarDirectorio(remotePath);
	        String nombre = nombreArchivo(rutaNorm, "/");
	        if (!Pattern.compile(REGEX, Pattern.UNICODE_CHARACTER_CLASS).matcher(
	                org.apache.commons.io.FilenameUtils.getName(nombre == null ? "" : nombre)
	        ).matches()) {
	            throw new IllegalArgumentException("Nombre de archivo inválido: " + nombre);
	        }

	        tempRoot = Files.createTempDirectory("nx_file_");
	        targetFile = tempRoot.resolve(nombre);
	        log.info("Path base: {}", tempRoot.toAbsolutePath());

	        nc.downloadFile(rutaNorm, tempRoot.toString());

	        return deleteOnCloseFileInputStream(targetFile, tempRoot);

	    } catch (org.aarboard.nextcloud.api.exception.NextcloudApiException e) {
	        String msg = e.getMessage() == null ? "" : e.getMessage();
	        if (msg.contains("404") || msg.contains("Not Found")) {
	            log.warn("Archivo no encontrado (404): {}", remotePath);
	            throw new DescargaArchivoException("Archivo no encontrado",e);
	        }
	        log.error("Error Nextcloud al descargar '{}': {}", remotePath, msg, e);
	        throw new NexusException(ErroresSistema.ERROR_NEXUS_DESCARGAR_ARCHIVO, e);

	    } catch (Exception e) {
	        log.error("Error al descargar archivo desde Nextcloud: {}", remotePath, e);
	        throw new NexusException(ErroresSistema.ERROR_NEXUS_DESCARGAR_ARCHIVO, e);
	    }
	}


	private InputStream deleteOnCloseFileInputStream(Path file, Path tempRoot) throws IOException {
	    final InputStream base = Files.newInputStream(file, java.nio.file.StandardOpenOption.DELETE_ON_CLOSE);
	    return new java.io.FilterInputStream(base) {
	        @Override public void close() throws IOException {
	            try {
	                super.close(); 
	            } finally {
	                safeDeleteTree(tempRoot);
	            }
	        }
	    };
	}

	private void safeDeleteTree(Path root) {
	    if (root == null) return;
	    try {
	        if (!Files.exists(root)) return;
	        try (java.util.stream.Stream<Path> walk = Files.walk(root)) {
	            walk.sorted(Comparator.reverseOrder())
	                .forEach(p -> { try { Files.deleteIfExists(p); } catch (IOException ignore) {} });
	        }
	    } catch (IOException ignore) {}
	}


	@Override
	public void eliminarArchivo(String path, Long idPapelera) throws NexusException {
	    if (!cargaHabilitada) {
	        // Si prefieres permitir eliminar aunque la "carga" esté bloqueada, mueve este guard a otro flag (ej. eliminacionHabilitada)
	        return;
	    }
	    if (idPapelera == null) {
	        throw new IllegalArgumentException("idPapelera no puede ser null");
	    }

	    final NextcloudConnector nc = getConnector();

	    try {
	        final String srcPath = normalizarDirectorio(path); 
	        final String fileName = FilenameUtils.getName(nombreArchivo(srcPath, "/"));
	        if (!Pattern.compile(REGEX, Pattern.UNICODE_CHARACTER_CLASS).matcher(fileName).matches()) {
	            throw new IllegalArgumentException("Nombre de archivo inválido: " + fileName);
	        }

	        final String trashRoot = normalizarDirectorio(PAPELERA + idPapelera); 
	        final String destDir   = joinRemoto(trashRoot, extraerDirectorioRelativo(srcPath)); 
	        final String destPath  = joinRemoto(destDir, fileName);                    

	        log.info("Moviendo a papelera: {} -> {}", srcPath, destPath);

	        generarCarpetas(nc, destDir);

	        nc.renameFile(srcPath, destPath, true);

	        log.info("Archivo movido exitosamente a la papelera: {}", destPath);

	    } catch (org.aarboard.nextcloud.api.exception.NextcloudApiException e) {
	        final String msg = e.getMessage() == null ? "" : e.getMessage();
	        if (msg.contains("404") || msg.contains("Not Found")) {
	            log.warn("El archivo no existe (404), nada que mover. path={}", path);
	            return;
	        }
	        log.error("Error Nextcloud al mover a papelera: {} → {}", path, idPapelera, e);
	        throw new NexusException(ErroresSistema.ERROR_NEXUS_ELIMINAR_ARCHIVO, e);

	    } catch (Exception e) {
	        log.error("Error al eliminar (mover a papelera) el archivo: {}", path, e);
	        throw new NexusException(ErroresSistema.ERROR_NEXUS_ELIMINAR_ARCHIVO, e);
	    }
	}
	
	private String extraerDirectorioRelativo(String fullPath) {
	    if (fullPath == null || fullPath.isBlank()) return "/";
	    String norm = normalizarDirectorio(fullPath);
	    int lastSlash = norm.lastIndexOf('/');
	    if (lastSlash <= 0) return "/";            
	    return norm.substring(0, lastSlash);      
	}

	@Override
	public void renameFile(String pathOriginal, String pathDestino) {
	    if (!cargaHabilitada) return;

	    final NextcloudConnector nc = getConnector();
	    try {
	        final String src = normalizarDirectorio(pathOriginal);
	        final String dst = normalizarDirectorio(pathDestino);

	        if (src.isEmpty() || "/".equals(src)) {
	            throw new IllegalArgumentException("Ruta origen inválida: " + pathOriginal);
	        }
	        if (dst.isEmpty() || "/".equals(dst)) {
	            throw new IllegalArgumentException("Ruta destino inválida: " + pathDestino);
	        }

	        final int lastSlash = dst.lastIndexOf('/');
	        final String dstDir = (lastSlash > 0) ? dst.substring(0, lastSlash) : "/";
	        if (!"/".equals(dstDir)) {
	            generarCarpetas(nc, dstDir);
	        }

	        log.info("Renombrando/moviendo: '{}' -> '{}'", src, dst);
	        nc.renameFile(src, dst, true);
	        log.info("Renombrado/movido OK: '{}' -> '{}'", src, dst);

	    } catch (org.aarboard.nextcloud.api.exception.NextcloudApiException e) {
	        final String msg = e.getMessage() == null ? "" : e.getMessage();
	        if (msg.contains("404") || msg.contains("Not Found")) {
	            log.warn("No se encontró el origen (404). Nada que renombrar. origen='{}'", pathOriginal);
	            return;
	        }
	        log.error("Error Nextcloud al renombrar '{}' -> '{}': {}", pathOriginal, pathDestino, msg, e);
	        throw e;

	    } catch (Exception e) {
	        log.error("Error al renombrar/mover '{}' -> '{}'", pathOriginal, pathDestino, e);
	    }
	}


	@Override
	public void eliminarArchivoSat(String path) {
	    if (!cargaHabilitada) return;

	    final NextcloudConnector nc = getConnector();
	    try {
	        final String src = normalizarDirectorio(path);
	        if (src.isEmpty() || "/".equals(src)) {
	            throw new IllegalArgumentException("Ruta inválida para eliminar: " + path);
	        }

	        final String nombre = FilenameUtils.getName(nombreArchivo(src, "/"));
	        if (!Pattern.compile(REGEX, Pattern.UNICODE_CHARACTER_CLASS).matcher(nombre).matches()) {
	            throw new IllegalArgumentException("Nombre de archivo inválido: " + nombre);
	        }

	        log.info("Eliminando archivo en Nextcloud: {}", src);
	        nc.removeFile(src);  
	        log.info("Archivo eliminado: {}", src);

	    } catch (org.aarboard.nextcloud.api.exception.NextcloudApiException e) {
	        final String msg = e.getMessage() == null ? "" : e.getMessage();
	        if (msg.contains("404") || msg.contains("Not Found")) {
	            log.warn("El archivo no existe (404), nada que eliminar. path={}", path);
	            return;
	        }
	        log.error("Error Nextcloud al eliminar archivo '{}': {}", path, msg, e);
	    } catch (Exception e) {
	        log.error("Error inesperado al eliminar archivo '{}'", path, e);
	    }
	}


	@Override
	public void restautarArchivo(String path, String pathOriginal) throws NexusException {
		if (cargaHabilitada) {
		try {
			renameFile(path, pathOriginal);
			generarPathArchivoBorrado(path);
		} catch (Exception e) {
			throw new NexusException(ErroresSistema.ERROR_NEXUS_ELIMINAR_ARCHIVO, e);
		}
		}
	}

	@Override
	public File[] informacionFolder(String remotepath) throws NexusException {
		if (!cargaHabilitada) {
			return new File[0];
		}
		NextcloudConnector localConnector = getConnector();
		Path tempDir = null;
		try {
			log.info("Descargar información del folder: {}", remotepath);
			String nombreArchivo = nombreArchivo(remotepath, "/");
			tempDir = Files.createTempDirectory(nombreArchivo);
			log.info(PAHT, tempDir.toAbsolutePath());

			localConnector.downloadFolder(remotepath, tempDir.toString());

			File[] files = tempDir.toFile().listFiles();
			if (files != null && files.length > 0) {
				return files[0].listFiles();
			}
			log.warn("El folder en {} está vacío o no se encontraron archivos.", remotepath);
			return new File[0];
		} catch (Exception e) {
			log.error("Error al obtener la información del folder en la ruta: {}", remotepath);
			throw new NexusException(ErroresSistema.ERROR_NEXUS_ELIMINAR_ARCHIVO, e);
		} finally {
		    if (tempDir != null) {
		        try {
		            Files.walk(tempDir)
		                .sorted(Comparator.reverseOrder())
		                .forEach(path -> {
		                    try {
		                        Files.delete(path);
		                    } catch (IOException e) {
		                        log.warn("No se pudo eliminar el archivo: {}", path, e);
		                    }
		                });
		            log.info("Directorio temporal eliminado: {}", tempDir.toAbsolutePath());
		        } catch (IOException e) {
		            log.error("Error al eliminar el directorio temporal: {}", tempDir.toAbsolutePath(), e);
		        }
		    }
		}

	}

	@Override
	public CarpetaCompartidaDto crearCarpetaCompartida(String remotepath) throws NexusException {
		CompartidoCloudModel cm= compartirSoloLectura(remotepath);
		CarpetaCompartidaDto carpetaCompartidaDto = new CarpetaCompartidaDto();
		carpetaCompartidaDto.setUrl(cm.getUrl());
		carpetaCompartidaDto.setTemporal(cm.getPassword());
		return carpetaCompartidaDto;
	}

	private String nombreArchivo(String path, String patron) {
		String[] archivos = path.split(patron);
		return archivos[archivos.length - 1];
	}

	private void generarCarpetas(NextcloudConnector nc, String path) throws NexusException {
	    String dir = normalizarDirectorio(path);
	    if (dir.isEmpty() || "/".equals(dir)) return;

	    String[] parts = dir.split("/");
	    StringBuilder curr = new StringBuilder();
	    for (String part : parts) {
	        if (part == null || part.isBlank()) continue;
	        curr.append('/').append(part);
	        String segmento = curr.toString();
	        if (dirCache.contains(segmento) || nc.folderExists(segmento)) continue; //<--
	        try {
	            nc.createFolder(segmento);           
	            log.debug("Carpeta creada: {}", segmento);
	        } catch (Exception e) {
	        	throw new NexusException(ErroresSistema.ERROR_NEXUS_CARGAR_ARCHIVO, e);
	        }
	        dirCache.add(segmento);
	    }
	}

	@SuppressWarnings("unused")
	private String generarCarpetaBorrado(String path) {
		if (!cargaHabilitada) {
			return "";
		}
		String regex = "(/[^/]+/\\d+)";
		Pattern patron = Pattern.compile(regex);
		Matcher match = patron.matcher(path);
		if (match.find()) {
			return match.group(1);
		}
		log.info("pathCompleto: {}", match.toString());
		return null;
	}



	private String generarPathArchivoBorrado(String path) {
		int lastSlashIndex = path.lastIndexOf('/');
		if (lastSlashIndex == -1) {
			throw new IllegalArgumentException("Invalid file path");
		}
		return path.substring(0, lastSlashIndex + 1);
	}

	public List<CompartidoCloudModel> compartirSoloLectura(List<String> paths) throws NexusException {
		if (!cargaHabilitada) {
			return new ArrayList<>();
		}
		NextcloudConnector localConnector = getConnector();
		try {
			List<CompartidoCloudModel> lista = new ArrayList<>();
			for (String path : paths) {
				log.info("Ruta para compartir: {}", path);
				CompartidoCloudModel obj = new CompartidoCloudModel();
				String password = PasswordGeneratorUtil.generateCommonLangPassword();
				FilesharingConnector instance = new FilesharingConnector(sc);
				SharePermissions permissions = new SharePermissions(SharePermissions.SingleRight.UPDATE);
				Collection<Share> shares = localConnector.getShares(path, false, false);
				log.info("Shares: {}", shares.size());
				shares.forEach(s -> localConnector.deleteShare(s.getId()));
				Share result = instance.doShare(path, ShareType.PUBLIC_LINK, "", false, password, permissions);
				log.info("Link generado para compartir: {}", result.getUrl());
				obj.setUrl(result.getUrl());
				obj.setPassword(password);
				lista.add(obj);
			}
			return lista;
		} catch (Exception e) {
			log.error("Error al generar links:{}", e.getMessage());
			throw new NexusException(ErroresSistema.ERROR_NEXUS_COMPARTIR_ARCHIVO, e);
		}
	}

	public String obtenerContenidoCarpeta(String path) throws NexusException {
		if (!cargaHabilitada) {
			return "";
		}
		NextcloudConnector localConnector = getConnector();
		try {
			int depth = 12;
			boolean excludeFolderNames = true;
			boolean returnFullPath = false;

			List<String> contenidos = localConnector.listFolderContent(path, depth, excludeFolderNames, returnFullPath);

			String resultado = String.join(" | ", contenidos);

			log.info("Contenido obtenido para la carpeta en {}: {}", path, resultado);

			return resultado;
		} catch (Exception e) {
			log.error("Error al obtener el contenido de la carpeta en la ruta: {}", path);
			throw new NexusException(ErroresSistema.ERROR_NEXUS_COMPARTIR_ARCHIVO, e);
		}
	}
	
	public List<String> obtenerContenidoCarpetaLista(String path) throws NexusException {
		if (!cargaHabilitada) {
			return new ArrayList<>();
		}
		NextcloudConnector localConnector = getConnector();
		try {
			int depth = 1;
			boolean excludeFolderNames = true;
			boolean returnFullPath = false;

			return  localConnector.listFolderContent(path, depth, excludeFolderNames, returnFullPath);

		} catch (Exception e) {
			log.error("Error al obtener el contenido de la carpeta en la ruta: {}", path);
			throw new NexusException(ErroresSistema.ERROR_NEXUS_COMPARTIR_ARCHIVO, e);
		}
	}

}
