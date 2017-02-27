package mgrzeszczak.com.github.seriesgeek.util;

/**
 * Created by Maciej on 27.02.2017.
 */
public class StringFormatter {

    public static String removeHtmlTags(String str){
        if (str == null) return null;
        return str.replaceAll("<p>|<\\/p>|<em>|<\\/em>","");
    }

}
