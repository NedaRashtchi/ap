package ap.exercises.EX6;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HtmlParser {

    public static String getFirstUrl(String htmlLine) {
        String url = null;
        int startIndex = htmlLine.indexOf("href=\"");
        if (startIndex >= 0) {
            try {
                int hrefLength = "href\"".length();
                int endIndex = htmlLine.indexOf("\"", startIndex + hrefLength + 1);
                url = htmlLine.substring(startIndex + hrefLength + 1, endIndex);
            } catch (Exception e) {
            }
        }
        return url;
    }

    private static List<String> getAllUrlsFromHtmlLinesStream(Stream<String> htmlLinesStream) throws IOException {
        List<String> urls = htmlLinesStream
                .map(line -> getFirstUrl(line))
                .filter(s -> s != null)
                .collect(Collectors.toList());
        return urls;
    }

    public static List<String> getAllUrlsFromFile(String filePath) throws IOException {
        return getAllUrlsFromHtmlLinesStream(Files.lines(Path.of(filePath)));
    }

    public static List<String> getAllUrlsFromList(List<String> htmlLines) throws IOException {
        return getAllUrlsFromHtmlLinesStream(htmlLines.stream());
    }

    public static List<String> getAllImageUrls(List<String> htmlLines, String baseUrl) {
        List<String> imageUrls = new ArrayList<>();
        try {
            URL base = new URL(baseUrl);
            for (String line : htmlLines) {
                int currentIndex = 0;
                while (currentIndex < line.length()) {
                    int imgTagStart = line.indexOf("<img", currentIndex);
                    if (imgTagStart == -1) break;

                    int srcIndex = line.indexOf("src=\"", imgTagStart);
                    if (srcIndex == -1) break;

                    int urlStart = srcIndex + 5;
                    int urlEnd = line.indexOf("\"", urlStart);
                    if (urlEnd == -1) break;

                    String imageUrl = line.substring(urlStart, urlEnd);

                    URL resolvedUrl = new URL(base, imageUrl);
                    imageUrls.add(resolvedUrl.toString());

                    currentIndex = urlEnd + 1;
                }
            }
        } catch (Exception e) {
            System.out.println("Error parsing image URLs: " + e.getMessage());
        }
        return imageUrls;
    }
}
