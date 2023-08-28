import java.util.List;

public class Main {

    private static final List<Integer> VALUES = List.of(3,4,5,2);


    private static final List<Integer> VALUES_2 = List.of(
        1498,
        439,
        34,
        243,
        50,
        1898,
        109,
        117,
        100,
        162,
        109,
        961,
        322,
        201,
        135,
        46,
        1467,
        54,
        188,
        969,
        34,
        132,
        149,
        44,
        104,
        33,
        96,
        100,
        326,
        54
        );





    public static void main(String[] args) {
        System.out.println("\n\nSum:" + 9 + " Nb Transactions:" + VALUES.size());
        RecursiveWay.findSubsets(VALUES, 9);
        RecursiveWayWithMemory.findSubsets(VALUES, 9);

        System.out.println("\n\nSum:" + 3000 + " Nb Transactions:" + VALUES_2.size());
        RecursiveWay.findSubsets(VALUES_2, 3000);
        RecursiveWayWithMemory.findSubsets(VALUES_2, 3000);
    }
}