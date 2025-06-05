package ap.exercises.EX6;


import ap.scraper.Conf;
import ap.scraper.fetcher.HtmlFetcher;
import ap.scraper.fetcher.ImageDownloader;
import ap.scraper.parser.HtmlParser;
import ap.scraper.store.HtmlFileManager;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class DomainHtmlScraper {

    private String domainAddress;
    private Queue<String> queue;
    private HtmlFileManager htmlFileManager;
    private Set<String> visitedUrls;

    public DomainHtmlScraper(String domainAddress, String savePath) {
        this.domainAddress = domainAddress;
        this.queue = new LinkedList<>();
        this.htmlFileManager = new HtmlFileManager(savePath);
        this.visitedUrls = new HashSet<>();
    }

    public void start() throws IOException {
        List<String> htmlLines = HtmlFetcher.fetchHtml(domainAddress);

        if (htmlLines == null || htmlLines.isEmpty()) {
            System.out.println("fetch failed or already fetched");
            return;
        }

        this.htmlFileManager.save(htmlLines);
        downloadAndSaveImages(htmlLines, domainAddress);

        List<String> urls = HtmlParser.getAllUrlsFromList(htmlLines);
        if (ap.scraper.Conf.FILTER_DOMAIN_ONLY) {
            urls = urls.stream()
                    .filter(url -> HtmlParser.isUrlInDomain(url, domainAddress))
                    .collect(Collectors.toList());
        }
        queue.addAll(new HashSet<>(urls));
        int counter = 1;

        while (!queue.isEmpty()) {
            String url = queue.remove();

            if (visitedUrls.contains(url)) {
                continue;
            }

            try {
                htmlLines = HtmlFetcher.fetchHtml(url);

                if (htmlLines == null || htmlLines.isEmpty()) {
                    System.out.println("fetch failed or already fetched for " + url);
                    continue;
                }

                this.htmlFileManager.save(htmlLines);
                visitedUrls.add(url);
                counter++;

                downloadAndSaveImages(htmlLines, url);

                urls = HtmlParser.getAllUrlsFromList(htmlLines);
                if (ap.scraper.Conf.FILTER_DOMAIN_ONLY) {
                    urls = urls.stream()
                            .filter(u -> HtmlParser.isUrlInDomain(u, domainAddress))
                            .collect(Collectors.toList());
                }
                queue.addAll(new HashSet<>(urls));

            } catch (Exception e) {
                System.out.println("ERROR: " + url + "\t -> " + e.getMessage());
            }

            System.out.println("[" + counter + "] " + url + " fetch and saved (queue size:" + queue.size() + ").");
        }

        System.out.println("Operation complete");
    }

    private void downloadAndSaveImages(List<String> htmlLines, String baseUrl) {
        List<String> imageUrls = HtmlParser.getAllImageUrls(htmlLines, baseUrl);
        if (imageUrls.isEmpty()) return;

        System.out.println("Found " + imageUrls.size() + " images on page: " + baseUrl);

        for (String imageUrl : imageUrls) {
            String[] parts = imageUrl.split("/");
            String filename = parts[parts.length - 1];
            String savePath = Conf.SAVE_IMAGES_DIRECTORY + "/" + filename;

            try {
                ImageDownloader.downloadImage(imageUrl, savePath);
            } catch (IOException e) {
                System.out.println("Failed to download image: " + imageUrl + " - " + e.getMessage());
            }
        }
    }
}
