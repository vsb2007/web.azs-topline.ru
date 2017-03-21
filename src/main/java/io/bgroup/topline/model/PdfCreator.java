package io.bgroup.topline.model;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

import javax.servlet.ServletOutputStream;
import java.io.*;

import java.nio.charset.Charset;
import java.util.Date;

public class PdfCreator {
    public boolean getDocument(ServletOutputStream outputStream, String html) {
        try {
            Document document = new Document(PageSize.A4.rotate());
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            writer.setInitialLeading(12.5f);
            document.open();
            addContent(writer, document, html);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // iText allows to add metadata to the PDF which can be viewed in your Adobe
    // Reader
    // under File -> Properties
    private static void addMetaData(Document document) {
        document.addTitle("My first PDF");
        document.addSubject("Using iText");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("Lars Vogel");
        document.addCreator("Lars Vogel");
    }

    private void addContent(PdfWriter writer, Document document, String html) throws DocumentException {
        ClassLoader classLoader = getClass().getClassLoader();

        //String myFontsDir = classLoader.getResource("fonts/timesNewRoman.ttf").getPath();
        String myFontsDir = classLoader.getResource("fonts").getPath();
        int iResult = FontFactory.registerDirectory(myFontsDir);
        /*if (iResult == 0) {
            System.out.println("TestPDF(): Could not register font directory " + myFontsDir);
        } else {
            System.out.println("TestPDF(): Registered font directory " + myFontsDir);
        }*/
        String htmlContent = html;

        InputStream inf = null;
        try {
            inf = new ByteArrayInputStream(htmlContent.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        XMLWorkerFontProvider fontImp = new XMLWorkerFontProvider(myFontsDir);
        FontFactory.setFontImp(fontImp);
        try {
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, inf, null, Charset.forName("UTF-8"), fontImp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
        XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
        fontProvider.register(classLoader.getResource("fonts/FreeSans.ttf").getPath());
        for (String s : fontProvider.getRegisteredFamilies()) {
            System.out.println(s);
        }*/

    }
}
