package com.ccvisualizer.ccvisualizer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class CcVisualizerApplication {

	public static void main(String[] args) {
		try {
			String filePath = "src/main/java/com/ccvisualizer/ccvisualizer/index.html";
			String outputSvgFilePath = "src/main/java/com/ccvisualizer/ccvisualizer/output.svg";

			// Parse the HTML file
			File input = new File(filePath);
			Document document = Jsoup.parse(input, "UTF-8");

			// String username from user_name
			 user_name userr= new user_name();

			// Replace the inner text of an element with a new value
			details(userr.getUsername(), document);

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

	private static void replaceInnerText(Document document, String elementId, String newValue) {
		// Select the element by ID
		Element element = document.getElementById(elementId);

		// Replace the inner text with a new value
		if (element != null) {
			element.text(newValue);
		} else {
			System.out.println("Element with ID '" + elementId + "' not found.");
		}
	}

	private static void replaceAttrValue(Document document, String elementId, String atr, String atrVal) {
		// Select the element by ID
		Element element = document.getElementById(elementId);

		// Replace the inner text with a new value
		if (element != null) {
			element.attr(atr, atrVal);
		} else {
			System.out.println("Element with ID '" + elementId + "' not found.");
		}
	}

	private static void writeToFile(String content, File file) throws IOException {
		// Write the content to the specified file
		org.apache.commons.io.FileUtils.writeStringToFile(file, content, "UTF-8");
	}

	private static Element convertToSvg(Document document) {
		// Create an SVG-like structure
		// Element svgElement = new Element("svg");
		// svgElement.attr("xmlns", "http://www.w3.org/2000/svg");

		// // Copy the body content into a foreignObject
		// Element foreignObject = new Element("foreignObject");
		// foreignObject.attr("width", "100%");
		// foreignObject.attr("height", "100%");

		// Element body = document.body();
		// if (body != null) {
		// 	foreignObject.append(body.html());
		// }

		// // Append the foreignObject to the SVG
		// svgElement.appendChild(foreignObject);

		Element svgElement = document.select("svg").first();
		return svgElement;
	}

	public static void details(String username, Document document) throws IOException {
		Details user = new Details();

		String url = "https://www.codechef.com/users/" + username;
		Document doc = Jsoup.connect(url).get();

		// set username
		user.setUsername(username);

		// set name
		Element nameElement = doc.select("h1.h2-style").first();
		if (nameElement != null) {
			user.setName(nameElement.text());
		}

		// set current star and rating
		Element currStarElement = doc.select("span.rating").first();
		if (currStarElement != null) {
			user.setCurrStar(currStarElement.text());
		}
		Element currRatingElement = doc.select("div.rating-number").first();
		if (currRatingElement != null) {
			user.setCurrRating(currRatingElement.text());
		}

		// set institute
		Elements instituteElements = doc.select("li:has(label:contains(Institution)) span");
		if (!instituteElements.isEmpty()) {
			user.setInstitute(instituteElements.first().text());
		}

		// set country
		Element countryElement = doc.select("span.user-country-name").first();
		if (countryElement != null) {
			user.setCountry(countryElement.text());
		}

		// set global rank and country rank
		List<Element> ranks = doc.select(".rating-ranks .inline-list strong");
		if (!ranks.isEmpty()) {
			user.setGlobalRank(ranks.get(0).text());
		}
		if (ranks.size() > 1) {
			user.setCountryRank(ranks.get(1).text());
		}

		// set contest participated and max rating
		Element contestParticipatedElement = doc.select("div.contest-participated-count b").first();
		if (contestParticipatedElement != null) {
			user.setContestParticipated(contestParticipatedElement.text());
		}
		String temp = doc.select(".rating-header.text-center small").text().split(" ")[2];
		if (temp.length() > 0) {
			Integer last = temp.length() - 1;
			user.setMaxRating(temp.substring(0, last));
		}

		// set current star color
		Element styleElement = doc.select("span.rating").first();
		if (styleElement != null) {
			String style = styleElement.attr("style");
			if (style != null) {
				String[] styleParts = style.split(";");
				if (styleParts.length > 2) {
					user.setCurr_col(styleParts[2].split(": ")[1]);
				}
			}
		}

		// set max star and color
		String star_col = user.colorFind(user.getMaxRating());
		if (star_col != null) {
			String[] star_colParts = star_col.split(";");
			if (star_colParts.length > 1) {
				user.setMxStar(star_colParts[0]);
				user.setMx_col(star_colParts[1]);
			}
		}

		// Select all <tspan> elements within <text> elements
		List<String> problem = doc.select("section.problems-solved h3").eachText();
		Integer sm = 0;

		for (String str : problem) {
			sm += extractNumericValue(str);
		}

		String newStr = sm.toString();
		user.setAccepted(newStr);

		replaceInnerText(document, "name", username);
		replaceInnerText(document, "user-curr", user.getName() + " ");
		replaceInnerText(document, "curr", user.getCurrStar() + " Rated ");
		replaceInnerText(document, "curr-r", user.getCurrRating());
		replaceInnerText(document, "t-contest", user.getContestParticipated());
		replaceInnerText(document, "max-r", user.getMaxRating());
		replaceInnerText(document, "max", user.getMxStar() + " Rated");
		replaceAttrValue(document, "max", "style", "color: " + user.getMx_col() + " ");
		replaceAttrValue(document, "curr", "style", "color: " + user.getCurr_col() + " ");
		replaceAttrValue(document, "curr-r", "style", "color: " + user.getCurr_col() + " ");
		replaceAttrValue(document, "max-r", "style", "color: " + user.getMx_col());
		replaceInnerText(document, "clg", user.getInstitute() + " | " + user.getCountry());
		replaceInnerText(document, "c-rank", user.getCountryRank());
		replaceInnerText(document, "g-rank", user.getGlobalRank());
		replaceInnerText(document, "accepted", user.getAccepted());
		replaceAttrValue(document, "max-r", "style", "color: " + user.getMx_col());
		replaceAttrValue(document, "curr-r", "style", "color: " + user.getCurr_col());
		replaceAttrValue(document, "name", "style", "color: " + user.getCurr_col() + " ");
		replaceAttrValue(document, "curr-r-name", "style", "color: " + user.getCurr_col() + " ");
		replaceAttrValue(document, "max-r-name", "style", "color: " + user.getMx_col() + " ");

		System.out.println(user.toString());
		// return user;
	}

	public static int extractNumericValue(String input) {
		if (input == null) {
			return 0;
		}
		// Remove non-numeric characters using regular expression
		String numericString = input.replaceAll("[^0-9]", "");

		// Check if the resulting string is not empty
		if (!numericString.isEmpty()) {
			// Parse the numeric string to an integer
			return Integer.parseInt(numericString);
		} else {
			// If no numeric characters found, return a default value (you can modify this part)
			return 0;
		}
	}

}
