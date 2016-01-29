package A1;


public class DigitHandler {


    public static String replaceNumber(String s){
        StringBuilder sb = new StringBuilder(s);
        StringBuilder finished = new StringBuilder();
        removeSpacesAndCommas(sb);
        String numbers[] = new String[2];
        if(sb.indexOf(".") > 0){                            //if there is a decimal in the number
            String str = sb.toString();
            numbers = str.split("\\.");
            numbers[0] = processDigits(numbers[0]);
            numbers[1] = processDecimals(numbers[1]);
            finished.append(numbers[0] + " point " + numbers[1]);
        }
        else{
            numbers[0] = processDigits(sb.toString());
            finished.append(numbers[0]);
        }
        return finished.toString();
    }

    public static String replaceFraction(String s){         //split at the slash and process numerator/denominator separately
        StringBuilder sb = new StringBuilder(s);
        StringBuilder finished = new StringBuilder();
        removeSpacesAndCommas(sb);
        String mixed[];
        boolean containsWhitespace = false;
        for (int i = 0; i < sb.length() && !containsWhitespace; i++) {
            if (Character.isWhitespace(sb.charAt(i))) {
                containsWhitespace = true;
            }
        }
        if(containsWhitespace){                                     //mixed number
            String mixedNumber = sb.toString();
            mixed = mixedNumber.split("\\s");
            mixed[0] = processDigits(mixed[0]);
            String fraction[] = mixed[1].split("/");
            fraction[0] = processDigits(fraction[0]);
            fraction[1] = processDenominator(fraction[1]);
            finished.append(mixed[0] + " and " + fraction[0] + " " + fraction[1]);
        }
        else{                                                       //not a mixed number
            String str = sb.toString();
            String fraction[] = str.split("/");
            fraction[0] = processDigits(fraction[0]);
            fraction[1] = processDenominator(fraction[1]);
            finished.append(fraction[0] + " " + fraction[1]);
        }

        return finished.toString();
    }

    private static void removeSpacesAndCommas(StringBuilder sb) {
        int j = 0;
        for(int i = 0; i < sb.length(); i++) {
            if (!Character.isWhitespace(sb.charAt(i)) || !Character.isLetter(',')) {
                sb.setCharAt(j++, sb.charAt(i));
            }
        }
        sb.delete(j, sb.length());
    }


    private static String processDigits(String s){
        StringBuilder result = new StringBuilder();
        if(s.length() > 3){
            StringBuilder digitGroups[] = new StringBuilder[4];
            int numberOfGroups = Math.floorDiv(s.length(), 3);
            if(s.length()%3 != 0) numberOfGroups++;                 //add a new group without prompting the next power (million, billion)
            for (int i = 0; i < numberOfGroups; i++) {
                digitGroups[i] = new StringBuilder();
            }
            for(int i = 0; i < s.length(); i++){
                digitGroups[Math.floorDiv(i,3)].append(s.charAt(s.length()- i - 1));                //every three digits move to next stringbuilder
            }
            for (int i = 0; i < numberOfGroups; i++) {
                digitGroups[i] = digitGroups[i].reverse();                          //append put the digits in reverse order
            }
            for (int i = numberOfGroups-1; i > -1; i--) {
                digitGroups[i] = translateDigits(digitGroups[i]);
                switch (i){
                    case 4: digitGroups[i].append(" trillion ");
                        break;
                    case 3: digitGroups[i].append(" billion ");
                        break;
                    case 2: digitGroups[i].append(" million ");
                        break;
                    case 1: digitGroups[i].append(" thousand ");
                        break;
                }
            }
            for(int i = numberOfGroups - 1; i > -1; i--){
                result.append(digitGroups[i]);
            }
        }
        else{
            StringBuilder digits = new StringBuilder(s);
            result  = translateDigits(digits);
        }

        return result.toString();
    }

    private static StringBuilder translateDigits(StringBuilder s){
        StringBuilder builder = new StringBuilder();
        if (s.length() == 3){
            builder.append(WordLibrary.simpleNumberWord(s.charAt(0))+ " hundred ");         // most significant digit
            if(s.charAt(1) == '1'){
                builder.append(WordLibrary.teenWords(s.charAt(2)));                         //second digit
                return builder;
            }
            else if(s.charAt(1) != '0'){
                builder.append(WordLibrary.tenWords(s.charAt(1)) + " ");
            }
            if(s.charAt(2) == '0') return builder;                              //last digit
            else builder.append(WordLibrary.simpleNumberWord(s.charAt(2)));
        }
        else if(s.length() == 2){
            if(s.charAt(0) == '1'){
                builder.append(WordLibrary.teenWords(s.charAt(1)));
            }
            else if(s.charAt(0) != '0'){
                builder.append(WordLibrary.tenWords(s.charAt(0)) + " ");
            }
            if(s.charAt(1) == '0') return builder;                              //last digit
            else builder.append(WordLibrary.simpleNumberWord(s.charAt(1)));
        }
        else if(s.length() == 1){ // change to string length 1
            builder.append(WordLibrary.simpleNumberWord(s.charAt(0)));
        }

        return builder;
    }

    private static String processDecimals(String s){
        StringBuilder numbers = new StringBuilder();
        for(int i = 0; i < s.length(); i++){
            numbers.append(WordLibrary.simpleNumberWord(s.charAt(i)) + " ");
        }
        return numbers.toString();
    }

    private static String processDenominator(String s){ //supports up to 20ths
        StringBuilder number = new StringBuilder();
        int denominator = Integer.parseInt(s);
        number.append(WordLibrary.fractionWords(denominator));
        return number.toString();
    }
}
