package com.imagenUpload.service;

import com.imagenUpload.entidad.ImageEntity;
import com.imagenUpload.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Value("${image.upload.dir}")
    private String uploadDir; // La carpeta donde guardaremos las imágenes

    // 📌 Método para subir una imagen
    public ImageEntity uploadImage(MultipartFile file) throws IOException {
        // Obtener el nombre del archivo
        String fileName = file.getOriginalFilename();

        // Crear una ruta donde guardar el archivo
        Path path = Paths.get(uploadDir, fileName);

        // Asegurarse de que el directorio exista
        Files.createDirectories(path.getParent());

        // Guardar el archivo en el directorio
        file.transferTo(path);

        // Crear la entidad de la imagen para almacenar en la base de datos
        ImageEntity image = ImageEntity.builder()
                .name(fileName)
                .path(path.toString())  // Guardar la ruta completa
                .contentType(file.getContentType())
                .build();

        return imageRepository.save(image);
    }

    // 📌 Método para obtener una imagen por su ID
    public Optional<ImageEntity> getImage(Long id) {
        return imageRepository.findById(id);
    }

    // 📌 Método para eliminar una imagen por su ID
    public void deleteImage(Long id) throws IOException {
        Optional<ImageEntity> imageOptional = imageRepository.findById(id);

        if (imageOptional.isPresent()) {
            ImageEntity image = imageOptional.get();
            // Eliminar el archivo físico del sistema de archivos
            Path path = Paths.get(image.getPath());
            Files.deleteIfExists(path);  // Borra el archivo
            imageRepository.delete(image); // Eliminar la entidad de la base de datos
        }
    }
}
