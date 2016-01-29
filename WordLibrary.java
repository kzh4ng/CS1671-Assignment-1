package A1;

/**
 * Created by kevin on 1/29/16.
 */
public class WordLibrary {

    private static final String NUMBERS[] = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "tweleve",
            "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen", "twenty","thirty", "forty",
            "fifty", "sixty", "seventy", "eighty", "ninety"};
    private static final String POWERS[] = { "hundred", "thousand", "million", "billion", "trillion"};
    private static final String FRACTIONS[] = {"half", "third", "fourth", "fifth", "sixth", "seventh", "eighth", "ninth", "tenth",
            "eleventh", "twelfth", "thirteenth", "fourteenth", "fifteenth", "sixteenth",
            "seventeenth", "eighteenth", "nineteenth", "twentieth",
            "thirtieth", "fortieth", "fiftieth", "sixtieth", "seventieth", "eightieth", "ninetieth"};
    private static final String FRACTIONPOWERS[] = { "hundredth" , "thousandth", "millionth", "billionth", "trillionth"};

    public static String simpleNumberWord(char digit){
        String representation;
        switch (digit){
            case '0': representation = NUMBERS[0];
                break;
            case '1':  representation = NUMBERS[1];
                break;
            case '2':  representation = NUMBERS[2];
                break;
            case '3':  representation = NUMBERS[3];
                break;
            case '4':  representation = NUMBERS[4];
                break;
            case '5':  representation = NUMBERS[5];
                break;
            case '6':  representation = NUMBERS[6];
                break;
            case '7':  representation = NUMBERS[7];
                break;
            case '8':  representation = NUMBERS[8];
                break;
            case '9':  representation = NUMBERS[9];
                break;
            default: representation = "something went wrong";
                break;
        }
        return representation;
    }

    public static String teenWords(char digit){
        String representation;
        switch (digit){
            case '0': representation = NUMBERS[10];
                break;
            case '1': representation = NUMBERS[11];
                break;
            case '2': representation = NUMBERS[12];
                break;
            case '3': representation = NUMBERS[13];
                break;
            case '4': representation = NUMBERS[14];
                break;
            case '5': representation = NUMBERS[15];
                break;
            case '6': representation = NUMBERS[16];
                break;
            case '7': representation = NUMBERS[17];
                break;
            case '8': representation = NUMBERS[18];
                break;
            case '9': representation = NUMBERS[19];
                break;
            default: representation = "uh oh";
                break;
        }
        return representation;
    }

    public static String tenWords(char digit){
        String representation;
        switch (digit){
            case '2': representation = NUMBERS[20];
                break;
            case '3': representation = NUMBERS[21];
                break;
            case '4': representation = NUMBERS[22];
                break;
            case '5': representation = NUMBERS[23];
                break;
            case '6': representation = NUMBERS[24];
                break;
            case '7': representation = NUMBERS[25];
                break;
            case '8': representation = NUMBERS[26];
                break;
            case '9': representation = NUMBERS[27];
                break;
            default: representation = "uh oh";
                break;
        }
        return representation;
    }
}
