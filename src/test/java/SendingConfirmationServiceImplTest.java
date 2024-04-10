import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import services.SenderService;
import services.SendingConfirmationService;
import services.SendingConfirmationServiceImpl;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SendingConfirmationServiceImplTest {

    static SendingConfirmationService confirmationService;


    public SendingConfirmationServiceImplTest() {

    }

    @BeforeAll
    static void init() {
        confirmationService = new SendingConfirmationServiceImpl();
    }

    @Test
    public void sendCustomIdTest() {
        boolean consensusReached = confirmationService.sendForConfirmationCustomId(new SenderServiceImpl(),
                "src/test/resources/results.json",
                0);

        assertTrue(consensusReached);
    }

    @Test
    public void sendCustomValuesTest() {
        boolean consensusReached = confirmationService.sendForConfirmationCustomValues(new SenderServiceImpl(),
                "src/test/resources/results.json",
                0.9975136,
                4.0);

        assertTrue(consensusReached);
    }

    @Test
    public void sendMinMessagesTest() {
        boolean consensusReached = confirmationService.sendForConfirmationMinMessages(new SenderServiceImpl(),
                "src/test/resources/results.json");

        assertTrue(consensusReached);
    }

    @Test
    public void sendMaxProbabilityTest() {
        boolean consensusReached = confirmationService.sendForConfirmationMaxProbability(new SenderServiceImpl(),
                "src/test/resources/results.json");

        assertTrue(consensusReached);
    }

    static class SenderServiceImpl implements SenderService {
        @Override
        public int getReply(String orgName, int transactionId) {
            // random either accepts or rejects a transaction
            int randomNum = ThreadLocalRandom.current().nextInt(0, 100);
            double org_pr = 0;
            switch (orgName) {
                case "Org1":
                    org_pr = 0.96;
                    break;
                case "Org2":
                    org_pr = 0.95;
                    break;
                case "Org3":
                    org_pr = 0.97;
                    break;
                case "Org4":
                    org_pr = 0.94;
                    break;
            }
            if (randomNum < org_pr*100) {
                return 1;
            }
            return 0;
        }

        @Override
        public int getMaxRequestNum() {
            return 10;
        }

        @Override
        public int getMaxRequestTotalNum() {
            return 50;
        }

        @Override
        public long getTimeoutSec() {
            return 0;
        }

        @Override
        public long getWaitingTimeSec() {
            return 0;
        }

        @Override
        public String getLogPath() {
            return "src/test/resources/runTest.log";
        }
    }
}
