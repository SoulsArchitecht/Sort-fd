package sortFileData;

import java.util.Comparator;
import java.util.Objects;

public class IntegerComparator implements Comparator<Integer> {

    @Override
    public int compare(Integer o1, Integer o2) {
        if (Objects.equals(o2, o1)) {
            return 0;
        }
        return o2 > o1 ? 1 : -1;
    }
}
