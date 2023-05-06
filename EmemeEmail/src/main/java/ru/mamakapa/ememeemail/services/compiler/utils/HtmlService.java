package ru.mamakapa.ememeemail.services.compiler.utils;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.swing.Java2DRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
public class HtmlService {
    private static final int WIDTH = 720;
    private static final String IMAGE_FORMAT = "png";

    static public org.jsoup.nodes.Document parseHTMLToXML(String html){
        org.jsoup.nodes.Document document = Jsoup.parse(html, "UTF-8");
        document.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml);
        return document;
    }

    static public List<String> extractLinks(String html){
        return extractLinks(HtmlService.parseHTMLToXML(html));
    }

    static public List<String> extractLinks(org.jsoup.nodes.Document xhtml){
        Elements links = xhtml.select("a[href]");
        return links.eachAttr("href");
    }

    static public File saveHtmlAsImage(String html, String imageFilePath) throws IOException {
        return saveHtmlAsImage(parseHTMLToXML(html),imageFilePath);
    }

    static public File saveHtmlAsImage(org.jsoup.nodes.Document xhtml, String imageFilePath) throws IOException {
        log.info("Trying to convert HTML to Image");
        String html = xhtml.toString();
        InputStream htmlStream = new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8));
        Tidy tidy = new Tidy();
        Document document = tidy.parseDOM(new InputStreamReader(htmlStream, StandardCharsets.UTF_8), null);

        Java2DRenderer renderer = new Java2DRenderer(document, WIDTH, -1);
        log.info("Rendering started");
        BufferedImage img = renderer.getImage();
        log.info("Writing to file");
        File saveFile = new File(imageFilePath);
        ImageIO.write(img, IMAGE_FORMAT, saveFile);
        return saveFile;
    }

    static public org.jsoup.nodes.Document deleteTag(org.jsoup.nodes.Document xhtml, String tag){
        Elements el = xhtml.select(tag);
        for (Element e : el) {
            e.remove();
        }
        return xhtml;
    }

    static public org.jsoup.nodes.Document deleteTag(String html, String tag){
        return deleteTag(parseHTMLToXML(html), tag);
    }
}
