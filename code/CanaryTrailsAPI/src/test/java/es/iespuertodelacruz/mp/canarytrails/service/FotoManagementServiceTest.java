package es.iespuertodelacruz.mp.canarytrails.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FotoManagementServiceTest {

    private FotoManagementService fotoService;

    @TempDir
    Path tempDir;


    @BeforeEach
    public void setUp() {
        fotoService = new FotoManagementService();
        ReflectionTestUtils.setField(fotoService, "root", tempDir);
    }


    @Test
    public void saveFile_CorrectlySavesToSubdirectory() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "foto.jpg", "image/jpeg", "test-content".getBytes());

        String pathRelativo = fotoService.save(file, "rutas");

        Path esperado = tempDir.resolve(pathRelativo);
        assertTrue(Files.exists(esperado));
        assertEquals("test-content", Files.readString(esperado));
    }

    @Test
    public void saveFile_WhenFileAlreadyExists_AppendsSuffix() throws IOException {
        String categoria = "fauna";
        Path subdir = tempDir.resolve(categoria);
        Files.createDirectories(subdir);
        Files.writeString(subdir.resolve("foto.png"), "contenido existente");

        MockMultipartFile file = new MockMultipartFile("file", "foto.png", "image/png", "nuevo contenido".getBytes());

        String pathRelativo = fotoService.save(file, categoria);

        assertTrue(pathRelativo.startsWith(categoria + "/foto_"));
        assertTrue(pathRelativo.endsWith(".png"));
    }

    @Test
    public void saveFile_WithoutExtension() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "archivoSinExtension", "text/plain", "contenido".getBytes());

        String pathRelativo = fotoService.save(file, "flora");

        assertTrue(pathRelativo.startsWith("flora/archivoSinExtension"));
        Path esperado = tempDir.resolve(pathRelativo);
        assertTrue(Files.exists(esperado));
    }

    @Test
    public void saveFile_ThrowsExceptionWhenFails() {
        MockMultipartFile file = Mockito.mock(MockMultipartFile.class);
        try {
            when(file.getOriginalFilename()).thenReturn("fallo.jpg");
            when(file.getInputStream()).thenThrow(new IOException("Error simulado"));

            RuntimeException ex = assertThrows(RuntimeException.class, () -> fotoService.save(file, "errores"));
            assertEquals("Error simulado", ex.getMessage());
        } catch (IOException e) {
            fail("No debería lanzar IOException directamente");
        }
    }
}
