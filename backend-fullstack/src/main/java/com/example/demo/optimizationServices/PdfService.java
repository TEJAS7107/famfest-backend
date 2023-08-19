package com.example.demo.optimizationServices;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.NameNotFoundException;

import java.awt.*;
import java.awt.image.BufferedImage;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Service;

import com.google.zxing.WriterException;

import com.fasterxml.jackson.core.sym.Name;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;

import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

@Service
public class PdfService {

    public byte[] getTicket(String name, String amount, String location, String event_ID, String event_Name,
            String order_Id, String count) throws IOException, NameNotFoundException, WriterException {

        try (PDDocument document = new PDDocument()) {
            if (event_ID.length() > 0) {
                PDPage page = new PDPage();
                document.addPage(page);

                PDPageContentStream contentStream = new PDPageContentStream(document, page);

                // contentStream.setNonStrokingColor(new Color(128, 0, 128)); // Purple RGB
                // values

                // Set font and font size
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);

                // // Draw a rectangle as a background
                // contentStream.setNonStrokingColor(new Color(0.8f, 0.8f, 0.8f));
                // contentStream.fillRect(50, 650, 500, 100);
                // FamFest

                contentStream.beginText();
                contentStream.setFont(PDType1Font.TIMES_BOLD, 32);
                contentStream.newLineAtOffset(250, 725);
                contentStream.showText("FamFest");
                contentStream.endText();

                // Draw text
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.newLineAtOffset(60, 680);
                contentStream.showText("Personal Details");
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(60, 640);
                contentStream.showText("Name: " + name);
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Address: " + location);
                contentStream.endText();

                //// Ticket details
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.newLineAtOffset(60, 480);
                contentStream.showText("Ticket Details");
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(60, 440);
                contentStream.showText("Order Id: " + order_Id);
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Amount: " + amount);
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Tickets: " + count);
                contentStream.endText();

                /// Event Information

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.newLineAtOffset(60, 280);
                contentStream.showText("Event Information");
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(60, 240);
                contentStream.showText("Event Id: " + event_ID);
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Event Name: " + event_Name);
                contentStream.endText();

                // contentStream.setNonStrokingColor(new Color(128, 0, 128)); // Purple RGB
                // values

                /// For Qr code
                BufferedImage qrCodeImage = generateQRCodeImage(order_Id, 150, 150);
                PDImageXObject qrCodeXObject = LosslessFactory.createFromImage(document, qrCodeImage);

                // Insert QR code image into the PDF
                contentStream.drawImage(qrCodeXObject, 400, 40, 150, 150);
                contentStream.close();

                contentStream.close();

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                document.save(outputStream);
                document.close();

                return outputStream.toByteArray();

            }
        }
        return null;
    }

    public static BufferedImage generateQRCodeImage(String text, int width, int height) throws WriterException {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

}

class MatrixToImageWriter {
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    private MatrixToImageWriter() {
    }

    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }

        return image;
    }
}