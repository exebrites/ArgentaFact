package com.argentafact.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
 

import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PdfController {


    /**
     * Ejemplo 1: PDF básico con texto
     */
    public static void crearPDFBasico() {
        Document document = new Document();
        
        try {
            // Crear el escritor PDF
            PdfWriter.getInstance(document, 
                new FileOutputStream("DocumentoBasico.pdf"));
            
            // Abrir documento
            document.open();
            
            // Agregar contenido
            document.add(new Paragraph("Documento PDF de ejemplo"));
            document.add(new Paragraph(" "));
            
            // Texto con diferentes formatos
            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Font fontNormal = FontFactory.getFont(FontFactory.HELVETICA, 12);
            Font fontItalica = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 12);
            
            document.add(new Paragraph("Título del documento", fontTitulo));
            document.add(new Paragraph("Este es un párrafo de texto normal.", fontNormal));
            document.add(new Paragraph("Este texto está en itálica.", fontItalica));
            
            // Lista de elementos
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Lista de elementos:"));
            document.add(new Paragraph("• Primer elemento"));
            document.add(new Paragraph("• Segundo elemento"));
            document.add(new Paragraph("• Tercer elemento"));
            
            System.out.println("PDF básico creado: DocumentoBasico.pdf");
            
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }
 
    @GetMapping("/pdf")
    public String getMethodName( ) {
        crearPDFBasico();
        return "hola";
    }
    
}