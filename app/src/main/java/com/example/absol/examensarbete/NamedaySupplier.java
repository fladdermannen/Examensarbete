package com.example.absol.examensarbete;

import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class NamedaySupplier {

    private static final String TAG = "NamedaySupplier";


    public NamedaySupplier() {

    }


    HashMap<String, String> parseWikitext(JSONObject jsonObject) {

        HashMap<String, String> namedays = new HashMap<>();

        String wikitext = jsonObject.toString();

        String cutString = wikitext.substring(405, wikitext.length());
        cutString = cutString.replace("\\n===Januari===\\n","");
        cutString = cutString.replace("\\n===Februari===\\n", "");
        cutString = cutString.replace("\\n===Mars===\\n", "");
        cutString = cutString.replace("\\n===April===\\n", "");
        cutString = cutString.replace("\\n===Maj===\\n", "");
        cutString = cutString.replace("\\n===Juni===\\n", "");
        cutString = cutString.replace("\\n===Juli===\\n", "");
        cutString = cutString.replace("\\n===Augusti===\\n", "");
        cutString = cutString.replace("\\n===September===\\n", "");
        cutString = cutString.replace("\\n===Oktober===\\n", "");
        cutString = cutString.replace("\\n===November===\\n", "");
        cutString = cutString.replace("\\n===December===\\n", "");
        cutString = cutString.replace("{| class=\\\"sortable wikitable\\\"\\n! style=\\\"width:6em;\\\" | Datum\\n! style=\\\"width:20em;\\\" | Namnsdag\\n", "");
        cutString = cutString.replace("{| class=\\\"sortable wikitable\\\"\\n! style=\\\"width:7em;\\\" | Datum\\n! style=\\\"width:20em;\\\" | Namnsdag\\n", "");
        cutString = cutString.replace("<span style=\\\"color:#888;float:right;\\\">''(ingen namnsdag)''<\\/span>", "[[Ingen namnsdag]");

        String[] splits = cutString.split("\\|-");


        for(int i = 1; i < splits.length; i++) {



            //Cut away all the crap
            String fix = splits[i].replace("\\n| {{dts2|", "");
            fix = fix.replace("{{dts2|", "");
            fix = fix.replace( "|md}} || [","" );
            fix = fix.replace("], ", "");
            fix = fix.replace("]\\n", "");
            fix = fix.replace(" (namn)|", ">");
            fix = fix.replace(" (mansnamn)|", ">");
            fix = fix.replace(" (förnamn)|", ">");
            fix = fix.replace("|}\"}}}", "");
            fix = fix.replace("''([", "");
            fix = fix.replace("])''\\n|}\\n", "");
            fix = fix.replace("|md}} || ", "");
            fix = fix.replace("])''\\n", "");
            fix = fix.replace("|}\\n", "");
            fix = fix.replace("\\n", "");

            //Log.d(TAG, "parseWikitext: " + fix);

            if(i==splits.length-1) {
                fix = fix.substring(0, 15);
            }


            String date = fix.substring(0, 4);
            String rest = fix.substring(5, fix.length());

            String[] names = rest.split("]");

            for(int y = 0; y < names.length; y++) {
                String replacename = names[y].replace("[[", "");
                if(replacename.contains(">")) {
                    replacename = replacename.substring(0, replacename.indexOf(">"));
                }
                replacename = replacename.replace("[", "");
                replacename = replacename.trim();


                String datemonth = date.substring(0, 2);
                String dateday = date.substring(2, 4);
                String month = "";
                switch (datemonth) {
                    case ("01") :
                        month = "Januari";
                        break;
                    case ("02") :
                        month = "Februari";
                        break;
                    case ("03") :
                        month = "Mars";
                        break;
                    case ("04") :
                        month = "April";
                        break;
                    case ("05") :
                        month = "Maj";
                        break;
                    case ("06") :
                        month = "Juni";
                        break;
                    case ("07") :
                        month = "Juli";
                        break;
                    case ("08") :
                        month = "Augusti";
                        break;
                    case ("09") :
                        month = "September";
                        break;
                    case ("10") :
                        month = "Oktober";
                        break;
                    case ("11") :
                        month = "November";
                        break;
                    case ("12") :
                        month = "December";
                        break;

                }
                if(date.equals("1106")) {
                    String[] gustavAdolf = replacename.split("\\|");
                    namedays.put(gustavAdolf[0],dateday+ " " +month);
                    namedays.put(gustavAdolf[1], dateday+ " " +month);
                } else if(date.equals("0901")) {
                    namedays.put("Sam", dateday+ " " +month);
                    namedays.put("Samuel", dateday+ " " + month);
                } else
                    namedays.put(replacename, dateday+ " " +month);
            }
        }
        return namedays;
    }
}
