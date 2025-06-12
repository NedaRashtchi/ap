package ap.exercises.EX8;

import ap.scraper.Conf;
import ap.scraper.fetcher.HtmlFetcher;
import ap.scraper.fetcher.ImageDownloader;
import ap.scraper.parser.HtmlParser;
import ap.scraper.store.HtmlFileManager;
import ap.scraper.utils.DirectoryTools;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import ap.scraper.fetcher.MP3Downloader;

public class DomainHtmlScraper {

    private String domainAddress;
    private BlockingQueue<String> queue;
    private HtmlFileManager htmlFileManager;
    private ConcurrentHashMap<String, Boolean> visitedUrls;

    public DomainHtmlScraper(String domainAddress, String savePath) {
        this.domainAddress = domainAddress;
        this.queue = new LinkedBlockingQueue<>();
        this.htmlFileManager = new HtmlFileManager(savePath);
        this.visitedUrls = new ConcurrentHashMap<>();
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

        if (Conf.THREAD_COUNT > 1) {
            ExecutorService executor = Executors.newFixedThreadPool(Conf.THREAD_COUNT);
            for (int i = 0; i < Conf.THREAD_COUNT; i++) {
                executor.submit(new DomainHtmlScraper.Worker());
            }
            executor.shutdown();
            try {
                executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } else {
            processQueue();
        }

        System.out.println("Operation complete");
    }

    private void processQueue() {
        int counter = 1;

        while (!queue.isEmpty()) {
            String url = queue.poll();
            if (url == null) break;

            if (visitedUrls.putIfAbsent(url, true) != null) {
                continue;
            }

            try {
                List<String> htmlLines = HtmlFetcher.fetchHtml(url);

                if (htmlLines == null || htmlLines.isEmpty()) {
                    System.out.println("fetch failed or already fetched for " + url);
                    continue;
                }

                this.htmlFileManager.save(htmlLines, url);
                counter++;

                downloadAndSaveImages(htmlLines, url);
                downloadAndSaveAudioFiles(htmlLines, url);

                List<String> urls = HtmlParser.getAllUrlsFromList(htmlLines);
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
    private class Worker implements Runnable {
        @Override
        public void run() {
            processQueue();
        }
    }
}