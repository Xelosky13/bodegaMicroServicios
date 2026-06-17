package com.item_productos_ubicacion.item_productos_ubicacion.model;

import java.time.LocalDate;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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
@Table(name = "mermas")
public class Merma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @PastOrPresent(message = "La fecha no puede ser futura")
    @Column(nullable = false)
    @CreatedDate
    private LocalDate fechaReporte;

    @NotNull(message = "La cantidad mermada es obligatoria")
    @Min(value = 1, message = "La cantidad mermada debe ser al menos 1")
    @Column(nullable = false)
    private Integer cantidad;

    @NotBlank(message = "Debe indicar el motivo de la merma")
    @Size(min = 5, max = 255, message = "El motivo debe tener entre 5 y 255 caracteres")
    @Column(nullable = false, length = 255)
    private String motivo;

    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull(message = "Debe asociar un producto a la merma")
    private Producto producto;
}
