package com.imagenUpload.entidad;


import jakarta.persistence.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Representa una imagen almacenada en el sistema de e-commerce.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "images")
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la imagen", example = "1")
    private Long id;

    @Column(nullable = false, length = 255)
    @Schema(description = "Nombre de la imagen", example = "producto1.png")
    private String name;

    @Column(nullable = false, length = 500)
    @Schema(description = "Ruta del archivo de la imagen", example = "/uploads/producto1.png")
    private String path;  // Ruta del archivo en el sistema de archivos

    @Column(nullable = false, length = 70)
    @Schema(description = "Tipo de contenido de la imagen", example = "image/png")
    private String contentType;
}


