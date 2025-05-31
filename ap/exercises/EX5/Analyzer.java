package ap.exercises.EX5;

import ap.scraper.Conf;
import ap.scraper.parser.HtmlParser;
import ap.scraper.utils.DirectoryTools;
import ap.scraper.utils.FileTools;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Analyzer {

    private static List<String> fileList = DirectoryTools.getFilesAbsolutePathInDirectory(Conf.SAVE_DIRECTORY);

    public static List<String> getAllUrls() {
        return fileList.stream()
                .map(FileTools::getTextFileLines)
                .filter(lines -> lines != null)
                .flatMap(List::stream)
                .map(HtmlParser::getFirstUrl)
                .filter(s -> s != null && s.length() > 1)
                .collect(Collectors.toList());
    }
    public static List<String> getTopUrls(int k){
        Map<String, Long> urlCount = getAllUrls().stream()
                .collect(Collectors.groupingBy(
                        s -> s,
                        Collectors.counting()
                ));

        List<String> topUrls = urlCount.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(k)
                .map(s -> s.getKey())
                .collect(Collectors.toList());

        return topUrls;
    }
    public static void printTopCountUrls(int k) {
        UrlCounter urlCounter = new UrlCounter();
        getAllUrls().forEach(urlCounter::add);
        for (String s : urlCounter.getTop(k)) {
            System.out.println(s);
        }
    }
    public static void main(String[] args) {
        printTopCountUrls(10);
        System.out.println("____________________");
        Analyzer.getTopUrls(10).forEach(s -> System.out.println(s));
    }
}