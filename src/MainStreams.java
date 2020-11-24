import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainStreams {
    private static Random rnd = new Random(new Date().getTime());
    private static int sum;
    private static List<Integer> resList = new ArrayList<>();

    public static void main(String[] args) {

        int[] testArray = getArray();
        System.out.println("\n<--- 1 --->\n");
        System.out.println("Input array: " + Arrays.toString(testArray));
        System.out.println("Result: " + minValue(testArray));

        List<Integer> testList = getList();
        System.out.println("\n<--- 2 --->\n");
        System.out.println("Input list: " + testList);
        System.out.println("Result: " + oddOrEven(testList));
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (o1, o2) -> o1 * 10 + o2);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        Stream<Integer> stream = integers.stream();
        sum = 0;
        resList.clear();
        stream.peek(a -> sum += a)
                .collect(Collectors.partitioningBy((p) -> p % 2 == 0))
                .forEach((a, b) -> {
                    if (sum % 2 == 0) {
                        if (!a) {
                            resList = b;
                        }
                    } else {
                        if (a) {
                            resList = b;
                        }
                    }
                });
        return resList;
    }

    private static int[] getArray() {
        int size = rnd.nextInt(10);
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = rnd.nextInt(9) + 1;
        }
        return array;
    }

    private static List<Integer> getList() {
        int size = rnd.nextInt(10);
        List<Integer> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(rnd.nextInt(9) + 1);
        }
        return list;
    }

}
