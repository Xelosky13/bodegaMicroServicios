package com.item_productos_ubicacion.item_productos_ubicacion.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ubicaciones")
public class Ubicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "El pasillo es obligatorio")
    @Min(value = 0, message = "El numero de pasillo no puedes ser negativo")
    @Column(nullable = false, length = 3)
    private Integer pasillo;

    @NotNull(message = "El estante es obligatorio")
    @Min(value = 0, message = "El numero de estante no puedes ser negativo")
    @Column(nullable = false, length = 3)
    private Integer estante;

    @NotBlank(message = "Debe indicar descripcion")
    @Size(min = 5, max = 255, message = "La descripcion debe tener entre 5 y 255 caracteres")
    @Column(nullable = false, length = 255)
    private String descripcion;

    @OneToMany(mappedBy = "ubicacion")
    private List<Producto> productos;
}
