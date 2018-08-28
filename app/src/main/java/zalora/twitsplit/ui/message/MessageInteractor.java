package zalora.twitsplit.ui.message;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class MessageInteractor {
    public static final int MESSAGE_MAX_LENGTH = 50;
    public static final int EXTRA_PREF_LENGTH = 4;

    interface OnFinishedListener {
        void onFinished(String[] items);
        void onError(String error);
    }

    private static boolean isValid(String message) {
        if (TextUtils.isEmpty(message)) {
            return false;
        }

        if (message.length() < MESSAGE_MAX_LENGTH) return true;

        String[] chunks = message.split(" ");
        for (String chunk : chunks) {
            if (chunk.length() >= MESSAGE_MAX_LENGTH - EXTRA_PREF_LENGTH) return false;
        }
        return true;
    }


    public static void splitMessage(String message, OnFinishedListener listener) {
        try {
            String[] result = splitMessage(message);
            listener.onFinished(result);
        } catch (Exception e) {
            e.printStackTrace();
            listener.onError(e.getMessage());
        }
    }


    /**
     * Split message to chunks
     * @param message
     * @return chunks
     * @throws Exception
     */
    public static String[] splitMessage(String message) throws Exception {
        if (TextUtils.isEmpty(message)) {
            throw new Exception("The message is empty");
        }
        if (!isValid(message)) {
            throw new Exception("the message contains a span of non-whitespace characters longer than 50 characters");
        }
        if (message.length() < MESSAGE_MAX_LENGTH) {
            return new String[]{message};
        }

        // Todo: split and return
        List<String> msgChunks = new ArrayList<>();

        String[] chunks = message.split(" ");
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < chunks.length; i++) {
            String chunk = chunks[i];

            if (temp.length() + chunk.length() > MESSAGE_MAX_LENGTH - EXTRA_PREF_LENGTH) {
                msgChunks.add(temp.toString());
                temp = new StringBuilder();
            }


            if (temp.length() != 0) {
                temp.append(" ");
            }
            temp.append(chunk);
        }
        //Add last index chunk
        if (temp.length() != 0) {
            msgChunks.add(temp.toString());
        }

        String[] results = new String[msgChunks.size()];
        for (int i = 0; i < msgChunks.size(); i++) {
            results[i] = String.format("%d/%d %s", i + 1, msgChunks.size(), msgChunks.get(i));
        }
        return results;
    }
}
