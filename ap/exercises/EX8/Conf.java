package ap.exercises.EX8;


public class Conf {

    public static final String DOMAIN_ADDRESS = "https://znu.ac.ir";

    public static final String SAVE_DIRECTORY = "fetched_html";
    public static final String SAVE_IMAGES_DIRECTORY = "fetched_images";
    public static final String SAVE_AUDIO_DIRECTORY = "fetched_songs";

    public static final boolean FILTER_DOMAIN_ONLY = true;
    public static final int DOWNLOAD_DELAY_SECONDS = 2;
    public static final int THREAD_COUNT = readThreadCount();

    private static int readThreadCount() {
        try {
            java.util.Properties prop = new java.util.Properties();
            java.io.InputStream input = ap.scraper.Conf.class.getClassLoader().getResourceAsStream("config.properties");
            if (input != null) {
                prop.load(input);
                return Integer.parseInt(prop.getProperty("thread-count", "0"));
            }
        } catch (Exception e) {
            System.err.println("Error reading config.properties: " + e.getMessage());
        }
        return 0;
    }

}