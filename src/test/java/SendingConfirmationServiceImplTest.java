import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import services.SenderService;
import services.SendingConfirmationService;
import services.SendingConfirmationServiceImpl;

import java.util.concurrent.ThreadLocalRandom;

public class SendingConfirmationServiceImplTest {

    static SendingConfirmationService confirmationService;


    public SendingConfirmationServiceImplTest() {

    }

    @BeforeAll
    static void init() {
        confirmationService = new SendingConfirmationServiceImpl();
    }

    @Test
    public void sendCustomTest() {
        confirmationService.sendForConfirmationCustomValues(new SenderServiceImpl(),
                "src/test/resources/results_backwards.json",
                1.0,
                3.0919090686339907);
    }

    @Test
    public void sendMinMessagesTest() {
        confirmationService.sendForConfirmationMinMessages(new SenderServiceImpl(),
                "src/test/resources/results_backwards.json");
    }

    @Test
    public void sendMaxProbabilityTest() {
        confirmationService.sendForConfirmationMaxProbability(new SenderServiceImpl(),
                "src/test/resources/results_backwards.json");
    }

    static class SenderServiceImpl implements SenderService {
        @Override
        public int getReply(String orgName, int transactionId) {
            // random either accepts or rejects a transaction
            return ThreadLocalRandom.current().nextInt(0, 2);
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
