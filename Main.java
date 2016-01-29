package A1;

import java.io.*;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    static StringBuffer text = new StringBuffer();

    public static void main(String[] args) throws IOException {
        // write your code here
        File file = new File("numbers.txt");
        Charset encoding = Charset.defaultCharset();
        parseFile(file, encoding);
        for (int i = 0; i < 22; i++) {
            text = findDates(i);
        }
        for (int i = 0; i < 2; i++) {
            text = findFractions(i);                                    //vary
        }
        for (int i = 0; i < 2; i++) {
            text = findNumbers(i);
        }
        System.out.println(text.toString());
    }
    private static void parseFile(File file, Charset encoding) throws IOException{ // read file
        try(InputStream in = new FileInputStream(file)) {
            Reader reader = new InputStreamReader(in, encoding);
            Reader buffer = new BufferedReader(reader);
            handleCharacters(buffer);
        }
    }

    private static void handleCharacters(Reader reader) // build stringbuffer from text
            throws IOException {
        int r;
        while ((r = reader.read()) != -1) {
            char ch = (char) r;
            text.append(ch);
        }
        removeBackslash(text);
        System.out.println(text.toString());
    }

    private static void removeBackslash(StringBuffer sb) {
        for(int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) == '\\') {
                sb.deleteCharAt(i);
            }
        }
    }

    private static StringBuffer findDates (int i){
        Pattern p0 = Pattern.compile("uary\\s\\d\\d?");
        Pattern p1 = Pattern.compile("Jan\\.\\s\\d\\d?");
        Pattern p2 = Pattern.compile("Feb\\.\\s\\d\\d?");
        Pattern p3 = Pattern.compile("Mar\\.\\s\\d\\d?");
        Pattern p4 = Pattern.compile("Apr\\.\\s\\d\\d?");
        Pattern p5 = Pattern.compile("May\\s\\d\\d?");
        Pattern p6 = Pattern.compile("Jun\\.\\s\\d\\d?");
        Pattern p7 = Pattern.compile("Jul\\.\\s\\d\\d?");
        Pattern p8 = Pattern.compile("Aug\\.\\s\\d\\d?");
        Pattern p9 = Pattern.compile("Sep\\.\\s\\d\\d?");
        Pattern p10 = Pattern.compile("Oct\\.\\s\\d\\d?");
        Pattern p11 = Pattern.compile("Nov\\.\\s\\d\\d?");
        Pattern p12 = Pattern.compile("Dec\\.\\s\\d\\d?");
        Pattern p13 = Pattern.compile("March\\s\\d\\d?");
        Pattern p14 = Pattern.compile("April\\s\\d\\d?");
        Pattern p15 = Pattern.compile("June\\s\\d\\d?");
        Pattern p16 = Pattern.compile("July\\s\\d\\d?");
        Pattern p17 = Pattern.compile("August\\s\\d\\d?");
        Pattern p18 = Pattern.compile("September\\s\\d\\d?");
        Pattern p19 = Pattern.compile("October\\s\\d\\d?");
        Pattern p20 = Pattern.compile("November\\s\\d\\d?");
        Pattern p21 = Pattern.compile("December\\s\\d\\d?");
        Pattern dates[] = {p0, p1 ,p2 ,p3 ,p4 ,p5 ,p6 ,p7 ,p8 ,p9 ,p10,p11,p12,p13,p14,p15,p16,p17,p18,p19,p20,p21};
        return findPatterns(dates[i], false, true);
    }

    private static StringBuffer findFractions (int i){
        Pattern p0 = Pattern.compile("\\d+\\s\\d+/\\d+");
        Pattern p1 = Pattern.compile("\\d+/\\d+");
        Pattern variations[] = {p0, p1};
        return findPatterns(variations[i], true, false);
    }

    private static StringBuffer findNumbers(int i){
        Pattern p1 = Pattern.compile("\\d+.\\d+");
        Pattern p2 = Pattern.compile("\\d+");
        Pattern variations[] = {p1, p2};
        return findPatterns(variations[i], false, false);
    }

    private static StringBuffer findPatterns(Pattern p, boolean fraction, boolean date){
        Matcher m = p.matcher(text);

        //System.out.println("Pattern found: " + m.matches());
        //m.reset();

        StringBuffer buffer = new StringBuffer();

        while(m.find()){                                            //find each case of a pattern match
            if(fraction) m.appendReplacement(buffer, DigitHandler.replaceFraction(m.group()));
            else if (date) m.appendReplacement(buffer, DigitHandler.replaceDate(m.group()));
            else m.appendReplacement(buffer, DigitHandler.replaceNumber(m.group()));
            System.out.println(buffer.toString());
        }
        m.appendTail(buffer);
        return buffer;
    }

}
