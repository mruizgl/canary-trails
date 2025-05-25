package es.iespuertodelacruz.mp.canarytrails.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FotoManagementService {

    private final Path root = Paths.get("./images");

    @Transactional
    public String save(MultipartFile file, String categoria) {

        try {
            // Crear el subdirectorio correspondiente
            Path subdir = this.root.resolve(categoria);
            Files.createDirectories(subdir);


            // Obtener un nombre de archivo libre dentro de esa categoría
            Path filenameFree = getFilenameFree(subdir, file.getOriginalFilename());

            Files.copy(file.getInputStream(),filenameFree);

            return filenameFree.getFileName().toString();
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("Ya existe un fichero llamado así");
            }
            throw new RuntimeException(e.getMessage());
        }
    }

    private Path getFilenameFree(Path directorio, String filename){

        Path pathCompleto = directorio.resolve(filename);
        String nombre = filename;
        String extension = "";

        int index = filename.lastIndexOf(".");
        if (index != -1) {
            nombre = filename.substring(0, index);
            extension = filename.substring(index); // incluye el punto
        }

        int contador = 1;

        while (Files.exists(pathCompleto)) {
            String nuevoNombre = nombre + "_" + contador + extension;
            pathCompleto = directorio.resolve(nuevoNombre);
            contador++;
        }

        return pathCompleto;
    }



}
