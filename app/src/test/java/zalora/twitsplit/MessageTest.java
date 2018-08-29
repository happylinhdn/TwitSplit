package zalora.twitsplit;

import org.junit.After;
import org.junit.Test;

import zalora.twitsplit.ui.message.MessageInteractor;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class MessageTest {
    private String input;
    private String[] expectChunks;
    private String errorMessage;

    void setUpTestCase1() {
        System.out.println("Setup testcase 1");
        // setup
        input = "This is short message";
        expectChunks = new String[]{"This is short message"};
        errorMessage = null;
    }

    void setUpTestCase2() {
        System.out.println("Setup testcase 2");
        // setup
        input = "";
        expectChunks = new String[]{};
        errorMessage = MessageInteractor.ERROR_EMPTY_MESSAGE;
    }

    void setUpTestCase3() {
        System.out.println("Setup testcase 3");
        // setup
        input = "============== =========================================================================================================";
        expectChunks = new String[]{};
        errorMessage = MessageInteractor.ERROR_LONG_PHASE_MESSAGE;
    }

    void setUpTestCase4() {
        System.out.println("Setup testcase 4");
        // setup
        input = "I can't believe Tweeter now supports chunking my messages, so I don't have to do it myself.";
        expectChunks = new String[]{"1/2 I can't believe Tweeter now supports chunking", "2/2 my messages, so I don't have to do it myself."};
        errorMessage = null;
    }

    @Test
    public void doTest1() {
        setUpTestCase1();
        System.out.println("Do Test");
        // test
        try {
            assertArrayEquals(expectChunks, MessageInteractor.splitMessage(input));
        } catch (Exception e) {
            System.out.println("Do Test error: " + e.getMessage());
            assertEquals(errorMessage, e.getMessage());
        }
    }

    @Test
    public void doTest2() {
        setUpTestCase2();
        System.out.println("Do Test");
        // test
        try {
            assertArrayEquals(expectChunks, MessageInteractor.splitMessage(input));
        } catch (Exception e) {
            assertEquals(errorMessage, e.getMessage());
        }
    }

    @Test
    public void doTest3() {
        setUpTestCase3();
        System.out.println("Do Test");
        // test
        try {
            assertArrayEquals(expectChunks, MessageInteractor.splitMessage(input));
        } catch (Exception e) {
            assertEquals(errorMessage, e.getMessage());
        }
    }

    @Test
    public void doTest4() {
        setUpTestCase4();
        System.out.println("Do Test");
        // test
        try {
            assertArrayEquals(expectChunks, MessageInteractor.splitMessage(input));
        } catch (Exception e) {
            assertEquals(errorMessage, e.getMessage());
        }
    }

    @After
    public void after() {
        System.out.println("Test finished");
    }
}
