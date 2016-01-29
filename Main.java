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

        Pattern pattern = Pattern.compile("(\\d*,\\d*)*");
        Matcher matcher = pattern.matcher("1234,33241,3234 asdfaesf 1,2,3");

        int count = 0;
        /*while(matcher.find()) {
            count++;
            System.out.println("found: " + count + " : "
                    + matcher.start() + " - " + matcher.end());
        }*/
        parseFile(file, encoding);
        text = findDollars(text);
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
        System.out.println(text.toString());

    }
    private static StringBuffer findDollars (StringBuffer sb){
        Pattern p0 = Pattern.compile("\\d+\\.\\d+");            // $ 123
        return findPatterns(p0);

    }

    private static StringBuffer findPatterns(Pattern p){
        Matcher m = p.matcher(text);

        System.out.println("Pattern found: " + m.matches());
        m.reset();

        StringBuffer buffer = new StringBuffer();

        while(m.find()){                                            //find each case of a pattern match
            m.appendReplacement(buffer, DigitHandler.replaceNumber(m.group()));
            System.out.println(buffer.toString());
        }
        m.appendTail(buffer);
        return buffer;
    }

}
