import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public class Main {

    private static final List<Integer> VALUES_LIST_1 = List.of(3,4,5,2);


    private static final List<Integer> VALUES_LIST_30 = List.of(
 1498, 439,  34, 243, 50, 1898, 109, 117, 100,
        162, 109, 961, 322,  201, 135, 46, 1467,
        54, 188, 969, 34, 132, 149, 44, 104, 33, 96, 100, 326, 54 );

    private static final List<Integer> VALUES_LIST_50 = List.of(153,27,56,462,195,377,16,33,38,419,59,312,93,15,112,103,91,65,173,49,24,47,374,39,73,438,26,2000,115,16,293,21,60,227,381,346,189,101,103,25,451,40,41,110,18,238,16,16,2,166);

    private static final List<Integer> VALUES_LIST_100 = List.of(153,27,56,462,195,377,16,33,38,419,59,312,93,15,112,103,91,65,173,49,24,47,374,39,73,438,26,2000,115,16,293,21,60,227,381,346,189,101,103,25,451,40,41,110,18,238,16,16,2,166,141,76,152,46,18,335,119,94,46,89,188,295,422,98,280,2327,45,147,100,235,74,893,15,8,220,30,109,135,25,226,36,666,134,711,386,253,345,26,33,5,69,217,104,81,143,33,276,13,111,1530);

    private static Map<Integer, List<Integer>> TEST_VALUES = Map.ofEntries(
        entry(9, VALUES_LIST_1),
        entry(6, List.of(3, 2, 7, 1)),
        entry(3000, VALUES_LIST_30)
        //entry(5000, VALUES_LIST_50)
        //entry(10000, VALUES_LIST_100)
    );

    private static final String CSV_SEPARATOR = ",";
    private static void writeToCSV(ArrayList<ResultModel> resultModels)
    {
        try
        {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("results.csv"), "UTF-8"));
            var headerLine = "type,nbTransactions,targetSum,matchFound,matchFoundDuration(sec),nbMatches,matchesDuration(sec)";
            bw.write(headerLine.toString());
            bw.newLine();
            for (ResultModel resultModel : resultModels)
            {
                StringBuffer oneLine = new StringBuffer();
                oneLine.append(resultModel.type);
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(resultModel.nbTransactions);
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(resultModel.targetSum);
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(resultModel.matchFound);
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(resultModel.hasMatchDurationSec);
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(resultModel.nbMatchesFound);
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(resultModel.matchesFoundDurationSec);
                bw.write(oneLine.toString());
                bw.newLine();
            }
            bw.flush();
            bw.close();
        }
        catch (UnsupportedEncodingException e) {}
        catch (FileNotFoundException e){}
        catch (IOException e){}
    }

    public static void main(String[] args) {
        var resultList = new ArrayList<ResultModel>();
        TEST_VALUES.entrySet().forEach(entry -> {
            var sum =entry.getKey();
            var transactionList = entry.getValue();

            System.out.println("Running test for sum " + sum + " nb transactions:" + transactionList.size());

            resultList.add(RecursiveWay.getTestResult(transactionList, sum));
            resultList.add(DynamicWay.getTestResult(transactionList, sum));
            //resultList.add(RecursiveWayWithMemory.get(transactionList, sum));
        });
        System.out.println(LogUtils.getMaxMemory() + "Mb for max memory");
        System.out.println(LogUtils.getHeapMemory() + "Mb for heap memory");
        writeToCSV(resultList);
        System.out.println("\nCheck results in the results.csv file");
    }
}