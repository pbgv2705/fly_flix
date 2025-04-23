package fly.be.flyflix.conteudo.service;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class PdfGenerator {

    public byte[] gerar(String nomeAluno, String nomeCurso, LocalDate data) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Formatação da data para estilo brasileiro
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = data.format(formatter);

        document.add(new Paragraph("CERTIFICADO DE CONCLUSÃO").setBold().setFontSize(20));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Certificamos que ").setFontSize(12));
        document.add(new Paragraph(nomeAluno).setBold().setFontSize(14));
        document.add(new Paragraph("concluiu com êxito o curso").setFontSize(12));
        document.add(new Paragraph(nomeCurso).setBold().setFontSize(14));
        document.add(new Paragraph("na data de " + dataFormatada + ".").setFontSize(12));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("_________________________________").setFontSize(12));
        document.add(new Paragraph("Assinatura da Plataforma").setFontSize(10));

        document.close();
        return baos.toByteArray();
    }


}
