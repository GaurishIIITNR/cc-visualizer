package com.ccvisualizer.ccvisualizer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.ccvisualizer.ccvisualizer.ImageUtility.*;

@Service
public class ImageService {

    public void getImage(String userName) {
        try {
			String filePath = "src/main/java/com/ccvisualizer/ccvisualizer/index.html";
			String outputSvgFilePath = "src/main/java/com/ccvisualizer/ccvisualizer/img/"+ userName +".svg";
			// Parse the HTML file
			File input = new File(filePath);
			Document document = Jsoup.parse(input, "UTF-8");

			// String username from user_name

			// Replace the inner text of an element with a new value
			details(userName, document);

			// Save the modified HTML content back to the file
			Element svgElement = convertToSvg(document);

			// Save the SVG content back to the file
			String svgContent = svgElement.outerHtml();
			// String another

			File output = new File(outputSvgFilePath);
			writeToFile(svgContent, output);

			Files.write(Path.of(outputSvgFilePath), svgContent.getBytes(StandardCharsets.UTF_8));

		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
