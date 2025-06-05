package ap.exercises.EX6;

import ap.scraper.Conf;
import ap.scraper.utils.DirectoryTools;

import java.io.PrintWriter;
import java.net.URL;
import java.util.List;

public class HtmlFileManager {

    private String saveFileBasePath;
    private static int saveCounter=0;

    public HtmlFileManager(String saveFileBasePath) {
//        this.saveFileBasePath = DirectoryTools.createDirectoryWithTimeStamp(saveFileBasePath);

        this.saveFileBasePath = saveFileBasePath;
        DirectoryTools.createDirectory(saveFileBasePath);
    }

    public void save(List<String> lines, String url) {
        try {
            String saveHtmlFileAddress = getSaveHtmlFileAddress(url);
            PrintWriter out = new PrintWriter(saveHtmlFileAddress);
            for (String line : lines) {
                out.println(line);
            }
            out.close();

            System.out.println("save counter: " + saveCounter);
        }catch (Exception e){
            System.out.println("save failed: " + e.getMessage());
        }
    }

    public String getSaveHtmlFileAddress(String url) {
        saveCounter++;
        try {
            URL parsedUrl = new URL(url);
            String host = parsedUrl.getHost();
            String path = parsedUrl.getPath();

            String domainBase = Conf.DOMAIN_ADDRESS.replace("https://",  "").replace("http://", "");
            String[] domainParts = domainBase.split("\\.");
            String mainDomain = domainParts[domainParts.length - 2] + "." + domainParts[domainParts.length - 1];

            String basePath = saveFileBasePath;

            if (!host.equals(domainBase)) {
                if (host.endsWith("." + mainDomain)) {
                    String subdomainPrefix = host.split("\\.")[0];
                    basePath += "/_" + subdomainPrefix;
                }
            }
            String[] pathSegments = path.split("/");
            for (int i = 1; i < pathSegments.length - 1; i++) {
                if (!pathSegments[i].isEmpty()) {
                    basePath += "/" + pathSegments[i];
                    DirectoryTools.createDirectory(basePath);
                }
            }
            String filename = saveCounter + ".html";
            return basePath + "/" + filename;
        } catch (Exception e) {
            return saveFileBasePath + "/" + saveCounter + ".html";
        }
    }
}