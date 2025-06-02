package ap.exercises.EX6;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class HtmlFetcher {

    private static Set<String> downloadedUrls = new HashSet<>();

    public static List<String> fetchHtml(String urlAddress) throws IOException {
        if(downloadedUrls.contains(urlAddress)){
            System.out.println(urlAddress + "is already downloaded");
            return null;
        }
        System.out.println("Going to fetch "+urlAddress+" ...");
        URL pageLocation = new URL(urlAddress);
        Scanner in = new Scanner(pageLocation.openStream());

        List<String> htmlLines=new ArrayList<>();
        while (in.hasNext()){
            htmlLines.add(in.next());
        }
        in.close();
        downloadedUrls.add(urlAddress);
        System.out.println(urlAddress+" fetched.");
        return htmlLines;
    }

}
