package com.ccvisualizer.ccvisualizer;

import com.ccvisualizer.ccvisualizer.Details;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class ImageUtility {

	public  static final String  OUTPUT_SVG_FILE_PATH = "https://github.com/GaurishIIITNR/cc-visualizer/blob/main/src/main/java/com/ccvisualizer/ccvisualizer/";
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

	static void writeToFile(String content, File file) throws IOException {
		// Write the content to the specified file
		org.apache.commons.io.FileUtils.writeStringToFile(file, content, "UTF-8");
	}

	static Element convertToSvg(Document document) {
		// Create an SVG-like structure
		Element svgElement = new Element("svg");
		svgElement.attr("xmlns", "http://www.w3.org/2000/svg");

		// Copy the body content into a foreignObject
		Element foreignObject = new Element("foreignObject");
		foreignObject.attr("width", "100%");
		foreignObject.attr("height", "100%");

		Element body = document.body();
		if (body != null) {
			foreignObject.append(body.html());
		}

		// Append the foreignObject to the SVG
		svgElement.appendChild(foreignObject);

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
