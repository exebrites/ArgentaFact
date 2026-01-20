package com.argentafact.controller;

import com.argentafact.model.DetalleFactura;
import com.argentafact.model.Empresa;
import com.argentafact.model.Factura;
import com.argentafact.service.FacturaService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;

import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PdfController {

    @Autowired 
    private FacturaService facturaService;
    /**
     * Ejemplo 1: PDF básico con texto
     */
    public static void crearPDFBasico(Empresa empresa, Factura factura) {
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
            document.add(new Paragraph(" "));
            // datos de la empresa
            document.add(new Paragraph("Datos de la empresa", fontTitulo));
            document.add(new Paragraph("Empresa: " + empresa.getRazonSocial(), fontNormal));

            // datos de la factura
            document.add(new Paragraph("Datos de la factura", fontTitulo));

            document.add(new Paragraph("Numero de factura: " + factura.getIdFactura(), fontNormal));
            document.add(new Paragraph("Fecha de emisión: " + factura.getFechaEmisionConFormato(), fontNormal));
            document.add(new Paragraph("Tipo de factura: " + factura.getTipoFactura(), fontNormal));
            // datos del cliente
            document.add(new Paragraph("Datos de la cliente", fontTitulo));
         
            document.add(new Paragraph("Nombre del cliente: " + factura.getCliente().getNombre() + " " + factura.getCliente().getApellido(), fontNormal));
            document.add(new Paragraph("CUIT del cliente: " + factura.getCliente().getCuit(), fontNormal));
            document.add(new Paragraph("Dirección del cliente: " + factura.getCliente().getDireccion(), fontNormal));
            // datos del servicios prestados
            document.add(new Paragraph("Datos de servicios prestados", fontTitulo));
         
            for (DetalleFactura detalleFactura : factura.getDetalleFacturas()) {
                document.add(new Paragraph("Servicio: " + detalleFactura.getServicio().getNombreServicio(), fontNormal));
                document.add(new Paragraph("Subtotal: " + detalleFactura.getSubtotal(), fontNormal));
            }
            System.out.println("PDF básico creado: DocumentoBasico.pdf");

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }

    @GetMapping("/pdf/{id}")
    public String generarFactura(@ModelAttribute("id") Long id) {

        // TODO : datos de la empresa
        var empresa = new Empresa("Empresa ficticia XXXX");
        // TODO : datos de la factura
        var factura = facturaService.obtenerFactura(id);

        // TODO : datos del cliente
        
        // TODO : datos del servicios prestados

        crearPDFBasico(empresa, factura);
        return "hola";
    }

}