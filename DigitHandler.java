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

    public static String replaceDate(String s){
        StringBuilder sb = new StringBuilder(s);
        String split[] = sb.toString().split("\\s");                    //split at space and retain month
        removeAllNonDigits(sb);                                         //implement date - point to process denominator eventually
        split[1] = processDate(split[1]);
        return split[0].concat(" " + split[1]);
    }

    public static String replaceDollars(String s){
        s = s.replaceFirst("\\$\\s?", "");
        String result[];
        if(s.contains("illion")){
            //s = s.substring(2);
            result = s.split("\\s");
            if(result[0].contains(".")){
                String nonInteger[] = result[0].split("\\.");
                result[0] = processDigits(nonInteger[0]).concat(" point " + processDecimals(nonInteger[1]));
            }
            else{
                result[0] = processDigits(result[0]);
            }
            result[1] = result[1].concat(" dollars");
        }
        else{
            if(s.contains(".")){
                result = s.split("\\.");
                result[0] = processDigits(result[0]).concat(" dollars and ");
                result[1] = processDigits(result[1]).concat(" cents");
            }
            else{
                result = s.split("\\s");
                result[0] = processDigits(result[0]);
                result[1] = result[1].concat(" dollars");
            }
        }
        return result[0].concat(" " + result[1]);
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


    private static void removeAllNonDigits(StringBuilder sb) {
        int j = 0;
        for(int i = 0; i < sb.length(); i++) {
            if (Character.isDigit(sb.charAt(i))) {
                sb.setCharAt(j++, sb.charAt(i));
            }
        }
        sb.delete(j, sb.length());
    }

    private static String processDigits(String s){
        StringBuilder preprocess = new StringBuilder(s);
        removeAllNonDigits(preprocess);
        s = preprocess.toString();
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
            boolean first = true;
            for (int i = numberOfGroups-1; i > -1; i--) {
                digitGroups[i] = translateDigits(digitGroups[i], i, first);
                first = false;
            }
            for(int i = numberOfGroups - 1; i > -1; i--){
                result.append(digitGroups[i]);
            }
        }
        else{
            StringBuilder digits = new StringBuilder(s);
            result  = translateDigits(digits, 0, false);
        }

        return result.toString();
    }

    private static StringBuilder translateDigits(StringBuilder s, int degree, boolean first){
        StringBuilder builder = new StringBuilder();
        boolean printedWord = false;
        if (s.length() == 3){
            if(s.charAt(0) != '0') {
                builder.append(WordLibrary.simpleNumberWord(s.charAt(0))+ " hundred ");         // most significant digit
                printedWord = true;
            }
            if(s.charAt(1) == '1') {
                builder.append(WordLibrary.teenWords(s.charAt(2)) + powerOfTen(degree));                         //second digit
                return builder;
            }
            else if(s.charAt(1) != '0') {
                builder.append(WordLibrary.tenWords(s.charAt(1)) + " ");
                printedWord = true;
            }
            if(s.charAt(2) != '0')  {
                builder.append(WordLibrary.simpleNumberWord(s.charAt(2)) + powerOfTen(degree));
            }
        }
        else if(s.length() == 2){
            if(s.charAt(0) == '1'){
                builder.append(WordLibrary.teenWords(s.charAt(1)) + powerOfTen(degree));
                return  builder;
            }
            else if(s.charAt(0) != '0'){
                builder.append(WordLibrary.tenWords(s.charAt(0)) + " ");
                printedWord = true;
            }
            if(s.charAt(1) != '0') {
                builder.append(WordLibrary.simpleNumberWord(s.charAt(1)));
                printedWord = true;
            }
        }
        else if(s.length() == 1){ // change to string length 1
            if(!(s.charAt(0) == 0)) builder.append(WordLibrary.simpleNumberWord(s.charAt(0)));
            printedWord = true;
        }
        if(first || printedWord){
            builder.append(powerOfTen(degree));
        }
        return builder;
    }

    private static String powerOfTen (int i){
        String result = "";
        switch (i){
            case 4: result = " trillion ";
                break;
            case 3: result =" billion ";
                break;
            case 2: result =" million ";
                break;
            case 1: result =" thousand ";
                break;
        }
        return result;
    }

    private static String processDecimals(String s){
        StringBuilder numbers = new StringBuilder();
        for(int i = 0; i < s.length(); i++){
            numbers.append(WordLibrary.simpleNumberWord(s.charAt(i)) + " ");
        }
        return numbers.toString();
    }


    private static String processDenominator(String s){ //supports up to and including 99ths
        StringBuilder number = new StringBuilder();
        if(s.length() == 2){
            if (s.charAt(0) == '1'){                                                            //knockout teens and multiples of ten
                number.append(WordLibrary.teenFractions(s.charAt(1)));
                return number.toString();
            }
            if (s.charAt(1) == '0'){
                number.append(WordLibrary.tenFractions(s.charAt(0)));
                return number.toString();
            }
            number.append(WordLibrary.tenWords(s.charAt(0)) + " ");
            if(s.charAt(1) == '1' || s.charAt(1) == '2'){
                number.append(WordLibrary.firstorSecond(s.charAt(1)));
                return number.toString();
            }
            else number.append(WordLibrary.fractionWords(s.charAt(1)));
        }
        else{
            number.append(" " + WordLibrary.fractionWords(s.charAt(0)));
        }
        return number.toString();
    }

    private static String processDate(String s){
        StringBuilder number = new StringBuilder();
        if(s.length() == 2){
            if (s.charAt(0) == '1'){                                                            //knockout teens and multiples of ten
                number.append(WordLibrary.teenFractions(s.charAt(1)));
                return number.toString();
            }
            if (s.charAt(1) == '0'){
                number.append(WordLibrary.tenFractions(s.charAt(0)));
                return number.toString();
            }
            number.append(WordLibrary.tenWords(s.charAt(0)) + " ");
            if(s.charAt(1) == '1' || s.charAt(1) == '2'){
                number.append(WordLibrary.firstorSecond(s.charAt(1)));
                return number.toString();
            }
            number.append(WordLibrary.fractionWords(s.charAt(1)));
        }
        else{
            if(s.charAt(0) == '1') number.append(WordLibrary.firstorSecond('1'));
            else if(s.charAt(0) == '2') number.append(WordLibrary.firstorSecond('2'));
            else number.append(WordLibrary.fractionWords(s.charAt(0)));
        }
        return number.toString();
    }
}
