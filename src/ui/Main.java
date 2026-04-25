package ui;

import model.*;
import service.BibliotecaService;
import exception.*;

public class Main {
    public static void main(String[] args) {

        BibliotecaService biblioteca = new BibliotecaService();

        // Crear libros
        Libro l1 = new Libro("111", "Java Básico", "Autor 1", 2020, true);
        Libro l2 = new Libro("222", "POO Avanzada", "Autor 2", 2021, true);
        Libro l3 = new Libro("333", "Base de Datos", "Autor 3", 2019, true);
        Libro l4 = new Libro("444", "Redes", "Autor 4", 2018, true);
        Libro l5 = new Libro("555", "Algoritmos", "Autor 5", 2022, true);

        biblioteca.agregarLibro(l1);
        biblioteca.agregarLibro(l2);
        biblioteca.agregarLibro(l3);
        biblioteca.agregarLibro(l4);
        biblioteca.agregarLibro(l5);

        // Crear estudiantes
        Estudiante e1 = new Estudiante("A01", "Camila", "Sistemas", "camila@gmail.com");
        Estudiante e2 = new Estudiante("A02", "Juan", "Informática", "juan@gmail.com");
        Estudiante e3 = new Estudiante("A03", "Ana", "Software", "ana@gmail.com");

        biblioteca.agregarEstudiante(e1);
        biblioteca.agregarEstudiante(e2);
        biblioteca.agregarEstudiante(e3);

        try {
            biblioteca.registrarPrestamo("111", "A01");
            biblioteca.registrarPrestamo("222", "A01");
            biblioteca.registrarPrestamo("333", "A01");

            // excepción por límite
            biblioteca.registrarPrestamo("444", "A01");

        } catch (LibroNoDisponibleException |
                 EstudianteNoEncontradoException |
                 LimitePrestamosExcedidoException e) {
            System.out.println("Error: " + e.getMessage());
        }

        try {
            // excepción por estudiante inexistente
            biblioteca.registrarPrestamo("555", "A99");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Buscar libro
        System.out.println("\nBuscar libro:");
        biblioteca.buscarLibro("Java");

        // Listar préstamos
        System.out.println("\nPréstamos de Camila:");
        biblioteca.listarPrestamosPorEstudiante("A01");

        // Calcular multa
        double multa = biblioteca.calcularMulta(15, 10000);
        System.out.println("\nMulta por 15 días: $" + multa);

        // Devolución
        biblioteca.registrarDevolucion("111");
    }
}