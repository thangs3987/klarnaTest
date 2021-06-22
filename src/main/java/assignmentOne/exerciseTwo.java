package assignmentOne;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class exerciseTwo {

    public static ArrayList<ArrayList<String>> getDuplicateTransactionInPeriod(String transactions) throws JSONException {
        JSONArray jsonArray = new JSONArray(transactions);
        JSONArray tempArray = new JSONArray();
        ArrayList<ArrayList<String>> finalgroup = new ArrayList<ArrayList<String>>();
        HashSet<String> hashSet = new HashSet<String>();
        ArrayList<String> listdata = new ArrayList<String>();
        int i;
        int k = 0;
        for (i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String categoryValue = jsonObject.getString("category");
            String time = jsonObject.getString("time");
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
            LocalDateTime dateTime = LocalDateTime.parse(time, inputFormatter);
            String amount = jsonObject.getString("amount");
            String sourceAccount = jsonObject.getString("sourceAccount");
            String targetAccount = jsonObject.getString("targetAccount");

            for (int j = i + 1; j < jsonArray.length(); j++) {
                JSONObject jsonObjectNext = jsonArray.getJSONObject(j);
                String categoryValueNext = jsonObjectNext.getString("category");
                String timeNext = jsonObjectNext.getString("time");
                DateTimeFormatter inputFormatterNext = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
                LocalDateTime dateTimeNew = LocalDateTime.parse(timeNext, inputFormatterNext);
                String amountNext = jsonObjectNext.getString("amount");
                String sourceAccountNext = jsonObjectNext.getString("sourceAccount");
                String targetAccountNext = jsonObjectNext.getString("targetAccount");
                long diffTime = ChronoUnit.SECONDS.between(dateTime, dateTimeNew);


                if ((sourceAccount.equalsIgnoreCase(sourceAccountNext)) && (targetAccount.equalsIgnoreCase(targetAccountNext)) && (categoryValue.equalsIgnoreCase(categoryValueNext)) && (amount.equalsIgnoreCase(amountNext)) && (diffTime < 60 && diffTime > -60)) {
                    k++;
                    System.out.println("Time difference is ? " + diffTime);

                    tempArray.put(jsonObject);
                    tempArray.put(jsonObjectNext);
                    try {
                        if (tempArray != null) {
                            for (int m = 0; m < tempArray.length(); m++) {
                                listdata.add(tempArray.getJSONObject(m).toString());
                                hashSet.addAll(listdata);
                                listdata.clear();
                                listdata.addAll(hashSet);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }


        }
        finalgroup.add(listdata);
        finalgroup.stream().sorted();
        return finalgroup;
    }

    public static String readFileAsString(String file) throws Exception {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    @Test
    public void mainTestNew() throws Exception {
        // test data file for transaction
        String file = "src/main/resources/transactionDuplicate.json";
        String json = readFileAsString(file);
        ArrayList<ArrayList<String>> DuplicateTransactions = getDuplicateTransactionInPeriod(json);
        System.out.println("Final list ==" + DuplicateTransactions);
    }
}


