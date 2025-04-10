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

    @Lob // Almacena datos binarios en la base de datos
    @Schema(description = "Contenido de la imagen en formato binario")
    private byte[] data;

    @Column(nullable = false, length = 70)
    @Schema(description = "Tipo de contenido de la imagen", example = "image/png")
    private String contentType;
}


