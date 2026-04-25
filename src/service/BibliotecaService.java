package service;

import model.*;
import exception.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class BibliotecaService {

    private ArrayList<Libro> catalogo;
    private HashMap<String, Estudiante> estudiantes;
    private HashSet<Prestamo> prestamosActivos;

    public BibliotecaService() {
        catalogo = new ArrayList<>();
        estudiantes = new HashMap<>();
        prestamosActivos = new HashSet<>();
    }

    // Agregar libro
    public void agregarLibro(Libro libro) {
        catalogo.add(libro);
    }

    // Agregar estudiante
    public void agregarEstudiante(Estudiante estudiante) {
        estudiantes.put(estudiante.getLegajo(), estudiante);
    }

    // Registrar préstamo
    public void registrarPrestamo(String isbn, String legajo)
            throws LibroNoDisponibleException,
                   EstudianteNoEncontradoException,
                   LimitePrestamosExcedidoException {

        Libro libroEncontrado = null;

        for (Libro libro : catalogo) {
            if (libro.getIsbn().equals(isbn)) {
                libroEncontrado = libro;
                break;
            }
        }

        if (libroEncontrado == null || !libroEncontrado.isDisponible()) {
            throw new LibroNoDisponibleException("Libro no disponible.");
        }

        Estudiante estudiante = estudiantes.get(legajo);

        if (estudiante == null) {
            throw new EstudianteNoEncontradoException("Estudiante no encontrado.");
        }

        int cantidadPrestamos = 0;
        for (Prestamo p : prestamosActivos) {
            if (p.getEstudiante().getLegajo().equals(legajo)) {
                cantidadPrestamos++;
            }
        }

        if (cantidadPrestamos >= 3) {
            throw new LimitePrestamosExcedidoException("Límite de préstamos excedido.");
        }

        Prestamo prestamo = new Prestamo(libroEncontrado, estudiante, LocalDate.now(), null);
        prestamosActivos.add(prestamo);
        libroEncontrado.setDisponible(false);

        System.out.println("Préstamo registrado correctamente.");
    }

    // Registrar devolución
    public void registrarDevolucion(String isbn) {
        Prestamo prestamoEliminar = null;

        for (Prestamo p : prestamosActivos) {
            if (p.getLibro().getIsbn().equals(isbn)) {
                prestamoEliminar = p;
                break;
            }
        }

        if (prestamoEliminar != null) {
            prestamoEliminar.getLibro().setDisponible(true);
            prestamosActivos.remove(prestamoEliminar);
            System.out.println("Devolución registrada.");
        }
    }

    // Buscar libro por título
    public void buscarLibro(String titulo) {
        for (Libro libro : catalogo) {
            if (libro.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                System.out.println(libro);
            }
        }
    }

    // Listar préstamos por estudiante
    public void listarPrestamosPorEstudiante(String legajo) {
        for (Prestamo p : prestamosActivos) {
            if (p.getEstudiante().getLegajo().equals(legajo)) {
                System.out.println(p);
            }
        }
    }

    // Método recursivo para calcular multa
    public double calcularMulta(int diasRetraso, double valorLibro) {
        if (diasRetraso <= 0 || diasRetraso > 30) {
            return 0;
        }
        return (valorLibro * 0.01) + calcularMulta(diasRetraso - 1, valorLibro);
    }
}
