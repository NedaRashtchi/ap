package ap.exercises.EX6;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public static List<String> getAllUrlsFromList(List<String> htmlLines) {
        return htmlLines.stream()
                .map(ap.scraper.parser.HtmlParser::getFirstUrl)
                .filter(u -> u != null && !u.isEmpty())
                .collect(Collectors.toList());
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

    public static List<String> getAllAudioUrls(List<String> htmlLines, String baseUrl) {
        List<String> audioUrls = new ArrayList<>();
        try {
            URL base = new URL(baseUrl);
            for (String line : htmlLines) {
                int currentIndex = 0;
                while (currentIndex < line.length()) {
                    int anchorStart = line.indexOf("<a ", currentIndex);
                    if (anchorStart == -1) break;

                    int hrefIndex = line.indexOf("href=\"", anchorStart);
                    if (hrefIndex == -1) break;

                    int urlStart = hrefIndex + 6;
                    int urlEnd = line.indexOf("\"", urlStart);
                    if (urlEnd == -1) break;

                    String fileUrl = line.substring(urlStart, urlEnd);
                    if (isAudioFile(fileUrl)) {
                        URL resolvedUrl = new URL(base, fileUrl);
                        audioUrls.add(resolvedUrl.toString());
                    }

                    currentIndex = urlEnd + 1;
                }
            }
        } catch (Exception e) {
            System.out.println("Error parsing audio URLs: " + e.getMessage());
        }
        return audioUrls;
    }

    private static boolean isAudioFile(String url) {
        String lowerUrl = url.toLowerCase();
        return lowerUrl.endsWith(".mp3") || lowerUrl.endsWith(".wav") ||
                lowerUrl.endsWith(".ogg") || lowerUrl.endsWith(".m4a");
    }

    public static boolean isUrlInDomain(String url, String domain) {
        if (url == null || domain == null) return false;

        String lowerUrl = url.toLowerCase();
        String lowerDomain = domain.toLowerCase();

        if (lowerUrl.startsWith(lowerDomain)) return true;
        if (lowerUrl.startsWith("/")) return true;

        try {
            URL parsedUrl = new URL(url);
            URL targetUrl = new URL(domain);

            String host = parsedUrl.getHost().toLowerCase();
            String targetHost = targetUrl.getHost().toLowerCase();

            return host.endsWith(targetHost);
        } catch (Exception e) {
            return false;
        }
    }
}