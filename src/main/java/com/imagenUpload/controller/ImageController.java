package com.imagenUpload.controller;

import com.imagenUpload.entidad.ImageEntity;
import com.imagenUpload.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * Controlador encargado de gestionar las imágenes en la aplicación.
 */
@RestController
@RequestMapping("/images")
@Tag(name = "Imágenes", description = "Operaciones para subir, obtener y eliminar imágenes")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    @Operation(summary = "Subir una imagen", description = "Permite subir una imagen al sistema. Retorna el ID de la imagen subida.")
    public ResponseEntity<String> uploadImage(
            @Parameter(description = "Archivo de imagen a subir", required = true)
            @RequestParam("file") MultipartFile file) {
        try {
            ImageEntity image = imageService.uploadImage(file);
            return ResponseEntity.ok("Imagen subida con éxito. ID: " + image.getId());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir la imagen");
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una imagen por ID", description = "Retorna una imagen en formato byte[] dado su ID.")
    public ResponseEntity<byte[]> getImage(
            @Parameter(description = "ID de la imagen a obtener", required = true)
            @PathVariable Long id) {
        Optional<ImageEntity> imageOptional = imageService.getImage(id);

        if (imageOptional.isPresent()) {
            ImageEntity image = imageOptional.get();
            Path path = Paths.get(image.getPath());

            try {
                byte[] imageBytes = Files.readAllBytes(path);
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, image.getContentType())
                        .body(imageBytes);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una imagen por ID", description = "Permite eliminar una imagen del sistema dado su ID.")
    public ResponseEntity<String> deleteImage(
            @Parameter(description = "ID de la imagen a eliminar", required = true)
            @PathVariable Long id) {
        try {
            imageService.deleteImage(id);
            return ResponseEntity.status(HttpStatus.OK).body("Imagen eliminada con éxito.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la imagen.");
        }
    }
}
