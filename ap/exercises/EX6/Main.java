package ap.exercises.EX6;

import ap.scraper.Conf;
import ap.scraper.DomainHtmlScraper;
import ap.scraper.utils.DirectoryTools;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        DirectoryTools.createDirectory(Conf.SAVE_IMAGES_DIRECTORY);

        String domainAddress = Conf.DOMAIN_ADDRESS;
        String savePath = Conf.SAVE_DIRECTORY;

        ap.scraper.DomainHtmlScraper domainHtmlScraper = new DomainHtmlScraper(domainAddress, savePath);
        domainHtmlScraper.start();
    }
}
