package ap.exercises.EX5;

import java.util.ArrayList;
import java.util.List;

public class UrlCounter {

    private List<String> link;
    private List<Integer> count;

    public UrlCounter() {
        link = new ArrayList<>();
        count = new ArrayList<>();
    }
    public void add(String item) {
        int index = link.indexOf(item);
        if (index != -1) {
            count.set(index, count.get(index) + 1);
        } else {
            link.add(item);
            count.add(1);
        }
    }
    public List<String> getTop(int k) {
        List<String> result = new ArrayList<>();
        List<Integer> Indexes = new ArrayList<>();
        for (int i = 0; i < link.size(); i++) {
            Indexes.add(i);
        }
        Indexes.sort((a, b) -> count.get(b) - count.get(a));
        for (int i = 0; i < Math.min(k, Indexes.size()); i++) {
            int index = Indexes.get(i);
            String s = link.get(index) + " -> " + count.get(index);
            result.add(s);
        }
        return result;
    }
}