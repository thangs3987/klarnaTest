package assignmentOne;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class exerciseOne {
    static double totalAmount = 0;
    // Function to calculate the Total amount and return the value as Double
    public static double getBalanceByCategoryInPeriod(String transactions, String category, String start, String end) throws JSONException {
        JSONArray jsonArray = new JSONArray(transactions);
        System.out.println("Total transactions = " + jsonArray.length());
        int i;
        for (i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String categoryValue = jsonObject.getString("category");
            String time = jsonObject.getString("time");
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
            LocalDate dateTime = LocalDate.parse(time, inputFormatter);
            LocalDate startTime = LocalDate.parse(start, inputFormatter);
            LocalDate endTime = LocalDate.parse(end, inputFormatter);
            String amount = jsonObject.getString("amount");
            double amountInDouble = Double.parseDouble(amount);
            String sourceAccount = jsonObject.getString("sourceAccount");
            String targetAccount = jsonObject.getString("targetAccount");

            // Conditions to calculate
            if (((categoryValue.equalsIgnoreCase(category))) && (dateTime.isAfter(startTime)) && (dateTime.isBefore(endTime))) {
                if ((sourceAccount.equalsIgnoreCase("my_account")) || (targetAccount.equalsIgnoreCase("my_account"))) {
                    totalAmount = totalAmount + amountInDouble;
                }
            } else {
                System.out.println("Condition not met in transaction "+i);
            }
        }
        return totalAmount;
    }

    @Test
    public void mainTest() throws Exception {
        // test data file for transaction
        String file = "src/main/resources/transaction.json";
        String json = readFileAsString(file);
        Double FinalAmount = getBalanceByCategoryInPeriod(json, "eating_out", "2000-03-12T12:34:00Z", "2021-03-12T12:34:00Z");
        System.out.println("Final Amount = " + FinalAmount);
    }
    public static String readFileAsString(String file)throws Exception
    {
        return new String(Files.readAllBytes(Paths.get(file)));
    }
}
