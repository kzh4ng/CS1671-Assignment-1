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
        text = findFractions(0);                                    //vary
        text = findNumbers(0);
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
    private static StringBuffer findFractions (int i){
        Pattern p0 = Pattern.compile("\\d+\\s\\d+/\\d+");
        Pattern p1 = Pattern.compile("\\d+/\\d+");
        Pattern variations[] = {p0, p1};
        return findPatterns(variations[i], true);
    }

    private static StringBuffer findNumbers(int i){
        Pattern p1 = Pattern.compile("\\d+.\\d+");
        Pattern p2 = Pattern.compile("\\d+");
        Pattern variations[] = {p1, p2};
        return findPatterns(variations[i], false);
    }

    private static StringBuffer findPatterns(Pattern p, boolean fraction){
        Matcher m = p.matcher(text);

        System.out.println("Pattern found: " + m.matches());
        m.reset();

        StringBuffer buffer = new StringBuffer();

        while(m.find()){                                            //find each case of a pattern match
            if(fraction) m.appendReplacement(buffer, DigitHandler.replaceFraction(m.group()));
            else m.appendReplacement(buffer, DigitHandler.replaceNumber(m.group()));
            System.out.println(buffer.toString());
        }
        m.appendTail(buffer);
        return buffer;
    }

}
