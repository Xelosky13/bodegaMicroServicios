package com.servicio.recepcion.model;

//import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
//import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "proveedor")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Nombre Proveedor es obligatorio")
    @Size(min = 3, max = 100, message = "Nombre debe tener entre 3 a 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "Rut Proveedor es obligatorio")
    @Size(min = 8, max = 10, message = "Rut debe tener entre 8 a 10 caracteres")
    @Column(nullable = false, length = 10)
    private String rut;

    @NotBlank(message = "El teléfono es obligatorio para despachos")
    @Size(min = 9, max = 9, message = "Telefono debe tener 9 digitos")
    @Column(nullable = false, length = 9)
    private String telefono;

    @NotBlank(message = "Nombre Contacto es obligatorio")
    @Size(min = 3, max = 100, message = "Nombre debe tener entre 3 a 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nombreContacto;

    /*
     * @OneToMany(mappedBy = "proveedor")
     * private List<OrdenRecepcion> ordenes;
     */
}
