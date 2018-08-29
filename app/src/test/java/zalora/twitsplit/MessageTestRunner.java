package zalora.twitsplit;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class MessageTestRunner {
    public static void main(String[] args) {
        Result rs = JUnitCore.runClasses(MessageTest.class);
        System.out.println("================= Result ==================: " + rs.wasSuccessful());
        for(Failure failure : rs.getFailures()) {
            System.out.println(failure.toString());
        }
    }
}
