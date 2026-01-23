package com.argentafact.service;

import com.argentafact.model.DetalleFactura;
import com.argentafact.model.Empresa;
import com.argentafact.model.Factura;
import com.argentafact.model.NotaCredito;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;

@Service
public class PdfService {

    private static final Font TITLE_FONT = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
    private static final Font HEADER_FONT = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
    private static final Font NORMAL_FONT = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
    private static final Font BOLD_FONT = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);

    public byte[] generarFacturaPdf(Factura factura, Empresa empresa) throws DocumentException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        PdfWriter.getInstance(document, baos);

        document.open();

        // Agregar contenido
        agregarCabecera(document, factura, "FACTURA");
        agregarInfoEmpresa(document, factura, empresa);
        agregarInfoCliente(document, factura, false);
        agregarItems(document, factura, null, false);
        agregarTotales(document, factura, null);

        document.close();
        return baos.toByteArray();
    }

    private void agregarCabecera(Document document, Factura factura, String tipoComprobante) throws DocumentException {
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);

        float[] columnWidths = { 2f, 1f, 2f };
        table.setWidths(columnWidths);

        // Columna izquierda: EMPRESA | X | FACTURA
        PdfPCell cell = new PdfPCell(new Phrase("EMPRESA", HEADER_FONT));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        var tipoFactura = factura.getTipoFactura().toString();
        cell = new PdfPCell(new Phrase(tipoFactura, HEADER_FONT));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(tipoComprobante, HEADER_FONT));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);

        // Línea separadora
        cell = new PdfPCell(new Phrase(" "));
        cell.setBorder(Rectangle.BOTTOM);
        cell.setColspan(3);
        cell.setFixedHeight(2f);
        table.addCell(cell);

        document.add(table);
        document.add(Chunk.NEWLINE);
    }

    private void agregarInfoEmpresa(Document document, Factura factura, Empresa empresa) throws DocumentException {
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);

        float[] columnWidths = { 2f, 1f, 2f };
        table.setWidths(columnWidths);

        // RAZON SOCIAL
        PdfPCell cell = new PdfPCell(new Phrase("RAZON SOCIAL: " + empresa.getRazonSocial(), NORMAL_FONT));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        // Celda vacía en el medio
        cell = new PdfPCell(new Phrase(" ", NORMAL_FONT));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        // NUMERO FACTURA
        cell = new PdfPCell(new Phrase("NUMERO: " + factura.getNumeroFactura(),
                NORMAL_FONT));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);

        // DIRECCION DE EMPRESA
        cell = new PdfPCell(new Phrase("DIRECCION: " + empresa.getDireccion(), NORMAL_FONT));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        // Celda vacía en el medio
        cell = new PdfPCell(new Phrase(" ", NORMAL_FONT));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        // FECHA

        cell = new PdfPCell(new Phrase("FECHA: " + factura.getFechaEmisionConFormato(), NORMAL_FONT));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);

        // CONTACTO: TELEFONO Y CORREO
        cell = new PdfPCell(new Phrase("TELEFONO: " + empresa.getTelefono(), NORMAL_FONT));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        // Celda vacía en el medio
        cell = new PdfPCell(new Phrase(" ", NORMAL_FONT));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        // Celda vacía en el medio
        cell = new PdfPCell(new Phrase(" ", NORMAL_FONT));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("CORREO ELECTRONICO: " + empresa.getCorreoElectronico(), NORMAL_FONT));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        // Celda vacía en el medio
        cell = new PdfPCell(new Phrase(" ", NORMAL_FONT));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        // Celda vacía en el medio
        cell = new PdfPCell(new Phrase(" ", NORMAL_FONT));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        // RESPONSABLE INSCRIPTO
        cell = new PdfPCell(new Phrase("CONDICION FISCAL: " + empresa.getCondicionFiscal(), NORMAL_FONT));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        // Celda vacía en el medio
        cell = new PdfPCell(new Phrase(" ", NORMAL_FONT));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        // Celda vacía en el medio
        cell = new PdfPCell(new Phrase(" ", NORMAL_FONT));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        // Línea separadora
        cell = new PdfPCell(new Phrase(" "));
        cell.setBorder(Rectangle.BOTTOM);
        cell.setColspan(3);
        cell.setFixedHeight(2f);
        table.addCell(cell);

        document.add(table);
        document.add(Chunk.NEWLINE);

    }

    private void agregarInfoCliente(Document document, Factura factura, boolean esNotaCredito)
            throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);

        // Columna CLIENTE
        PdfPCell cell = new PdfPCell(new Phrase("CLIENTE", HEADER_FONT));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        // Celda vacía en el medio
        cell = new PdfPCell(new Phrase(" ", NORMAL_FONT));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        // CUIT
        cell = new PdfPCell(new Phrase("CUIT: " + factura.getCliente().getCuit(), NORMAL_FONT));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        // Información adicional domicilio (si se necesita)
        cell = new PdfPCell(new Phrase("DOMICILIO: " + factura.getCliente().getDireccion(), NORMAL_FONT));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        // LOCALIDAD
        cell = new PdfPCell(new Phrase("LOCALIDAD: " + factura.getCliente().getLocalidad(), NORMAL_FONT));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        // TELEFONO
        cell = new PdfPCell(new Phrase("TELEFONO: " + factura.getCliente().getTelefono(), NORMAL_FONT));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        // CONDICION FISCAL
        cell = new PdfPCell(new Phrase("CONDICION FISCAL: " +
                factura.getCliente().getCondicionFiscal(), NORMAL_FONT));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(2);
        table.addCell(cell);

        // NUMERO DE FACTURA ASOCIADA
        if (esNotaCredito) {
            cell = new PdfPCell(new Phrase("FACTURA ASOCIADA N° " +
                    factura.getNumeroFactura(), NORMAL_FONT));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(2);
            table.addCell(cell);
        }

        // Salto de línea
        cell = new PdfPCell(new Phrase(" "));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(2);
        table.addCell(cell);
      
      
        document.add(table);
        document.add(Chunk.NEWLINE);
    }

    private void agregarItems(Document document, Factura factura, NotaCredito notaCredito, boolean esNotaCredito)
            throws DocumentException {
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);

        float[] columnWidths = { 2f, 4f, 2f, 2f };
        table.setWidths(columnWidths);

        // Encabezados de la tabla de items
        PdfPCell cell = new PdfPCell(new Phrase("NOMBRE", HEADER_FONT));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("DESCRIPCION", HEADER_FONT));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("PRECIO U.", HEADER_FONT));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("SUBTOTAL", HEADER_FONT));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        // Línea separadora debajo del encabezado
        cell = new PdfPCell(new Phrase(" "));
        cell.setBorder(Rectangle.BOTTOM);
        cell.setColspan(4);
        cell.setFixedHeight(2f);
        table.addCell(cell);

        if (esNotaCredito) {
            cell = new PdfPCell(new Phrase("-", NORMAL_FONT));
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(notaCredito.getMotivo(), NORMAL_FONT));
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(String.format("$%.2f",
                    notaCredito.getMonto()), NORMAL_FONT));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(String.format("$%.2f", notaCredito.getMonto()),
                    NORMAL_FONT));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);
        } else {
            // Items de la factura
            for (DetalleFactura item : factura.getDetalleFacturas()) {
                cell = new PdfPCell(new Phrase(item.getServicio().getNombreServicio(), NORMAL_FONT));
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(item.getServicio().getDescripcion(), NORMAL_FONT));
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.format("$%.2f",
                        item.getServicio().getPrecio()), NORMAL_FONT));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.format("$%.2f", item.getSubtotal()),
                        NORMAL_FONT));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(cell);
            }
        }
        document.add(table);
        document.add(Chunk.NEWLINE);
    }

    private void agregarTotales(Document document, Factura factura, NotaCredito notaCredito) throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(50);
        table.setHorizontalAlignment(Element.ALIGN_RIGHT);

        float[] columnWidths = { 1f, 1f };
        table.setWidths(columnWidths);

        if (notaCredito != null) {
            // MONTO NOTA DE CREDITO
            PdfPCell cell = new PdfPCell(new Phrase("MONTO NOTA DE CRÉDITO", HEADER_FONT));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(String.format("$%.2f", notaCredito.getMonto()),
                    BOLD_FONT));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);
        } else {
            // IVA
            PdfPCell cell = new PdfPCell(new Phrase("IVA", HEADER_FONT));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(String.format("$%.2f", factura.getIva()),
                    BOLD_FONT));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);

            // TOTAL
            cell = new PdfPCell(new Phrase("TOTAL", HEADER_FONT));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(String.format("$%.2f", factura.getTotalConIva()),
                    BOLD_FONT));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);
        }
        document.add(table);
    }

    // TODO generarNotaCreditoPdf
    // TODO agregar cabecera nota credito
    // TODO agregar informacion de la empresa
    // TODO agregar informacion del cliente
    // TODO agregar numero de factura original asociada
    // TODO agregar datos de la nota de credito
    public byte[] generarNotaCreditoPdf(NotaCredito notaCredito, Empresa empresa) throws DocumentException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        PdfWriter.getInstance(document, baos);

        document.open();

        // Agregar contenido
        agregarCabecera(document, notaCredito.getFactura(), "NOTA DE CRÉDITO");
        agregarInfoEmpresa(document, notaCredito.getFactura(), empresa);
        agregarInfoCliente(document, notaCredito.getFactura(), true);
        agregarItems(document, notaCredito.getFactura(), notaCredito, true);
        agregarTotales(document, notaCredito.getFactura(), notaCredito);
        document.close();
        return baos.toByteArray();
    }

}