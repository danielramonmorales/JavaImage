package com.imagenUpload.service;

import com.imagenUpload.entidad.ImageEntity;
import com.imagenUpload.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor // 💡 Reemplaza @Autowired con inyección por constructor
public class ImageService {

    private final ImageRepository imageRepository;

    /**
     * Sube una imagen al sistema y la almacena en la base de datos.
     *
     * @param file Archivo Multipart recibido
     * @return Entidad persistida de la imagen
     * @throws IOException si ocurre un error al leer el archivo
     */
    public ImageEntity uploadImage(MultipartFile file) throws IOException {
        ImageEntity image = ImageEntity.builder()
                .name(file.getOriginalFilename())
                .contentType(file.getContentType())
                .data(file.getBytes())
                .build();

        return imageRepository.save(image);
    }

    /**
     * Busca una imagen por su ID.
     *
     * @param id ID de la imagen
     * @return Optional con la imagen si existe
     */
    public Optional<ImageEntity> getImage(Long id) {
        return imageRepository.findById(id);
    }

    /**
     * Elimina una imagen por su ID.
     *
     * @param id ID de la imagen a eliminar
     */
    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }
}
