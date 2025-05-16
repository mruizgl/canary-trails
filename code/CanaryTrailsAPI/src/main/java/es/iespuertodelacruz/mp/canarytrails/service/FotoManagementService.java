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

    private final Path root = Paths.get("src/main/resources/uploads");

    @Transactional
    public String save(MultipartFile file, String categoria) {

        try {
            // Crear el subdirectorio correspondiente
            Path subdir = this.root.resolve(categoria);
            Files.createDirectories(subdir);


            // Obtener un nombre de archivo libre dentro de esa categoría
            Path filenameFree = getFilenameFree(subdir, file.getOriginalFilename());

            Files.copy(file.getInputStream(),filenameFree);

            return categoria + "/" + filenameFree.getFileName().toString();
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("Ya existe un fichero llamado así");
            }
            throw new RuntimeException(e.getMessage());
        }
    }

    private Path getFilenameFree(Path directorio, String filename){

        Path pathCompleto = directorio.resolve(filename);
        String nombre="";
        String extension = "";

        //Si contiene punto, desglosa por punto y extension
        if( filename.contains(".")) {
            extension = filename.substring(filename.lastIndexOf(".") + 1);
            nombre = filename.substring(0, filename.length() -
                    extension.length() -1);
        } else {
            //Si no, el nombre es tal cual
            nombre = filename;
        }

        int contador=1;

        //Mientras exista el path completo, se va a actualizar con el contador para que se cree un archivo con nombre nuevo
        while(Files.exists(pathCompleto)) {
            String nuevoNombre = nombre + "_" + contador;

            //Solo le añade la extension si tiene una establecida
            if (!extension.isEmpty()) {
                nuevoNombre += "." + extension;
            }

            pathCompleto = directorio.resolve(nuevoNombre);
            contador++;
        }

        return(pathCompleto);
    }



}
