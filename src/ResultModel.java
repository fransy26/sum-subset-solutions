import java.time.Duration;
import java.util.List;


public class ResultModel {
    String type;
    Integer nbTransactions;
    Integer targetSum;
    boolean matchFound;
    Long hasMatchDurationSec;
    Integer nbMatchesFound;
    Long matchesFoundDurationSec;


    public ResultModel(String type, Integer nbTransactions, Integer sum, boolean matchFound, Long hasMatchDurationSec,
                          Integer nbMatchesFound,  Long matchesFoundDurationSec) {
        this.type = type;
        this.nbTransactions = nbTransactions;
        this.targetSum = sum;
        this.matchFound = matchFound;
        this.hasMatchDurationSec = hasMatchDurationSec;
        this.matchesFoundDurationSec = matchesFoundDurationSec;
        this.nbMatchesFound = nbMatchesFound;
    }
}
