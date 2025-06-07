package ap.exercises.EX6;

import ap.scraper.fetcher.HtmlFetcher;
import ap.scraper.fetcher.ImageDownloader;
import ap.scraper.parser.HtmlParser;
import ap.scraper.store.HtmlFileManager;
import ap.scraper.utils.DirectoryTools;

import java.io.IOException;
import java.util.stream.Collectors;

import ap.scraper.fetcher.MP3Downloader;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

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
        DirectoryTools.createDirectory(Conf.SAVE_IMAGES_DIRECTORY);
        DirectoryTools.createDirectory(Conf.SAVE_AUDIO_DIRECTORY);

        List<String> htmlLines = HtmlFetcher.fetchHtml(domainAddress);

        if (htmlLines == null || htmlLines.isEmpty()) {
            System.out.println("fetch failed or already fetched");
            return;
        }

        this.htmlFileManager.save(htmlLines, domainAddress);
        downloadAndSaveImages(htmlLines, domainAddress);
        downloadAndSaveAudioFiles(htmlLines, domainAddress);

        List<String> urls = HtmlParser.getAllUrlsFromList(htmlLines);
        if (Conf.FILTER_DOMAIN_ONLY) {
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

                this.htmlFileManager.save(htmlLines, url);
                visitedUrls.add(url);
                counter++;

                downloadAndSaveImages(htmlLines, url);
                downloadAndSaveAudioFiles(htmlLines, url);

                urls = HtmlParser.getAllUrlsFromList(htmlLines);
                if (Conf.FILTER_DOMAIN_ONLY) {
                    urls = urls.stream()
                            .filter(u -> HtmlParser.isUrlInDomain(u, domainAddress))
                            .collect(Collectors.toList());
                }
                queue.addAll(new HashSet<>(urls));

            } catch (Exception e) {
                System.out.println("ERROR: " + url + "\t -> " + e.getMessage());
            }

            System.out.println("[" + counter + "] " + url + " fetch and saved (queue size:" + queue.size() + ").");
            
            if (Conf.DOWNLOAD_DELAY_SECONDS > 0) {
                try {
                    System.out.println("Waiting " + Conf.DOWNLOAD_DELAY_SECONDS + " seconds before next download...");
                    Thread.sleep(Conf.DOWNLOAD_DELAY_SECONDS * 1000);
                } catch (InterruptedException e) {
                    System.out.println("Download delay was interrupted");
                }
            }
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

    private void downloadAndSaveAudioFiles(List<String> htmlLines, String baseUrl) {
        List<String> audioUrls = HtmlParser.getAllAudioUrls(htmlLines, baseUrl);
        if (audioUrls.isEmpty()) return;

        System.out.println("Found " + audioUrls.size() + " audio files on page: " + baseUrl);

        for (String audioUrl : audioUrls) {
            String[] parts = audioUrl.split("/");
            String filename = parts[parts.length - 1];
            String savePath = Conf.SAVE_AUDIO_DIRECTORY + "/" + filename;

            try {
                MP3Downloader.downloadMP3(audioUrl, savePath);
            } catch (IOException e) {
                System.out.println("Failed to download audio: " + audioUrl + " - " + e.getMessage());
            }
        }
    }
}