import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainStreams {
    private Random rnd = new Random(new Date().getTime());

    public static void main(String[] args) {
        MainStreams ms = new MainStreams();

        int[] testArray = ms.getArray();
        System.out.println("\n<--- 1 --->\n");
        System.out.println("Input array: " + Arrays.toString(testArray));
        System.out.println("Result: " + ms.minValue(testArray));

        List<Integer> testList = ms.getList();
        System.out.println("\n<--- 2 --->\n");
        System.out.println("Input list: " + testList);
        System.out.println("Result: " + ms.oddOrEven(testList));
    }

    private int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .boxed()
                .sorted()
                .reduce((o1, o2) -> o1 * 10 + o2).orElse(0);
    }

    private List<Integer> oddOrEven(List<Integer> integers) {
        Stream<Integer> stream = integers.stream();
        int sum = stream.reduce((o1, o2) -> o1 + o2).orElse(0);
        stream = integers.stream();
        return sum % 2 == 0 ?
                stream.filter((p) -> p % 2 != 0).collect(Collectors.toList())
                :
                stream.filter((p) -> p % 2 == 0).collect(Collectors.toList());
    }

    private int[] getArray() {
        int size = rnd.nextInt(10);
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = rnd.nextInt(9) + 1;
        }
        return array;
    }

    private List<Integer> getList() {
        int size = rnd.nextInt(10);
        List<Integer> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(rnd.nextInt(9) + 1);
        }
        return list;
    }

}
