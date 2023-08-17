package sortFileData;

import java.util.Comparator;
import java.util.Objects;

public class StringComparator implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
        if (Objects.equals(o1, o2)) {
            return 0;
        }
        return o2.compareTo(o1) > 0 ? 1 : -1;
    }
}
