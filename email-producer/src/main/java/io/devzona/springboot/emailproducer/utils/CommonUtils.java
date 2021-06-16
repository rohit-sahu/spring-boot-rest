package io.devzona.springboot.emailproducer.utils;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {

    public static final String ID = "id";
    public static final Map<Integer, String> colorMap = new LinkedHashMap<>();
    static Map<String, String> shortPropTypeMap = new HashMap<>();
    static Map<String, Integer> areaClusterMap = new HashMap<>();
    static Map<String, String> tcCodeMap = new LinkedHashMap<>();
    private static List<String> luxuryList;

    static {
        areaClusterMap.put("sqft", 12800);
        areaClusterMap.put("sqyrd", 12803);
        areaClusterMap.put("sqm", 12801);
        areaClusterMap.put("acre", 12853);
        areaClusterMap.put("bigha", 12854);
        areaClusterMap.put("hectare", 12855);
        areaClusterMap.put("marla", 12856);
        areaClusterMap.put("kanal", 12857);
        areaClusterMap.put("biswa1", 12588);
        areaClusterMap.put("ground", 12590);
        areaClusterMap.put("aankadam", 12591);
        areaClusterMap.put("rood", 12592);
        areaClusterMap.put("chatak", 12593);
        areaClusterMap.put("Kottah", 12594);
        areaClusterMap.put("Marla", 12595);
        areaClusterMap.put("Cent", 12596);
        areaClusterMap.put("Perch", 12597);
        areaClusterMap.put("Guntha", 12598);
        areaClusterMap.put("Are", 12599);
        areaClusterMap.put("Katha", 12594);
        areaClusterMap.put("Gaj", 12851);
        areaClusterMap.put("Killa", 12853);
        areaClusterMap.put("Kuncham", 12858);
    }

    static {
        tcCodeMap.put("AF", "93");
        tcCodeMap.put("AL", "355");
        tcCodeMap.put("DZ", "213");
        tcCodeMap.put("AS", "1");
        tcCodeMap.put("AD", "376");
        tcCodeMap.put("AO", "244");
        tcCodeMap.put("AI", "1");
        tcCodeMap.put("AG", "1");
        tcCodeMap.put("AR", "54");
        tcCodeMap.put("AM", "374");
        tcCodeMap.put("AW", "297");
        tcCodeMap.put("AU", "61");
        tcCodeMap.put("AT", "43");
        tcCodeMap.put("AZ", "994");
        tcCodeMap.put("BH", "973");
        tcCodeMap.put("BD", "880");
        tcCodeMap.put("BB", "1");
        tcCodeMap.put("BY", "375");
        tcCodeMap.put("BE", "32");
        tcCodeMap.put("BZ", "501");
        tcCodeMap.put("BJ", "229");
        tcCodeMap.put("BM", "1");
        tcCodeMap.put("BT", "975");
        tcCodeMap.put("BO", "591");
        tcCodeMap.put("BQ", "599");
        tcCodeMap.put("BA", "387");
        tcCodeMap.put("BW", "267");
        tcCodeMap.put("BR", "55");
        tcCodeMap.put("IO", "246");
        tcCodeMap.put("VG", "1");
        tcCodeMap.put("BN", "673");
        tcCodeMap.put("BG", "359");
        tcCodeMap.put("BF", "226");
        tcCodeMap.put("BI", "257");
        tcCodeMap.put("KH", "855");
        tcCodeMap.put("CM", "237");
        tcCodeMap.put("CA", "1");
        tcCodeMap.put("CV", "238");
        tcCodeMap.put("KY", "1");
        tcCodeMap.put("CF", "236");
        tcCodeMap.put("TD", "235");
        tcCodeMap.put("CL", "56");
        tcCodeMap.put("CN", "86");
        tcCodeMap.put("CO", "57");
        tcCodeMap.put("KM", "269");
        tcCodeMap.put("CK", "682");
        tcCodeMap.put("CR", "506");
        tcCodeMap.put("CI", "225");
        tcCodeMap.put("HR", "385");
        tcCodeMap.put("CU", "53");
        tcCodeMap.put("CW", "599");
        tcCodeMap.put("CY", "357");
        tcCodeMap.put("CZ", "420");
        tcCodeMap.put("CD", "243");
        tcCodeMap.put("DK", "45");
        tcCodeMap.put("DJ", "253");
        tcCodeMap.put("DM", "1");
        tcCodeMap.put("DO", "1");
        tcCodeMap.put("EC", "593");
        tcCodeMap.put("EG", "20");
        tcCodeMap.put("SV", "503");
        tcCodeMap.put("GQ", "240");
        tcCodeMap.put("ER", "291");
        tcCodeMap.put("EE", "372");
        tcCodeMap.put("ET", "251");
        tcCodeMap.put("FK", "500");
        tcCodeMap.put("FO", "298");
        tcCodeMap.put("FM", "691");
        tcCodeMap.put("FJ", "679");
        tcCodeMap.put("FI", "358");
        tcCodeMap.put("FR", "33");
        tcCodeMap.put("GF", "594");
        tcCodeMap.put("PF", "689");
        tcCodeMap.put("GA", "241");
        tcCodeMap.put("GE", "995");
        tcCodeMap.put("DE", "49");
        tcCodeMap.put("GH", "233");
        tcCodeMap.put("GI", "350");
        tcCodeMap.put("GR", "30");
        tcCodeMap.put("GL", "299");
        tcCodeMap.put("GD", "1");
        tcCodeMap.put("GP", "590");
        tcCodeMap.put("GU", "1");
        tcCodeMap.put("GT", "502");
        tcCodeMap.put("GG", "44");
        tcCodeMap.put("GN", "224");
        tcCodeMap.put("GW", "245");
        tcCodeMap.put("GY", "592");
        tcCodeMap.put("HT", "509");
        tcCodeMap.put("HN", "504");
        tcCodeMap.put("HK", "852");
        tcCodeMap.put("HU", "36");
        tcCodeMap.put("IS", "354");
        tcCodeMap.put("IN", "91");
        tcCodeMap.put("ID", "62");
        tcCodeMap.put("IR", "98");
        tcCodeMap.put("IQ", "964");
        tcCodeMap.put("IE", "353");
        tcCodeMap.put("IM", "44");
        tcCodeMap.put("IL", "972");
        tcCodeMap.put("IT", "39");
        tcCodeMap.put("JM", "1");
        tcCodeMap.put("JP", "81");
        tcCodeMap.put("JE", "44");
        tcCodeMap.put("JO", "962");
        tcCodeMap.put("KZ", "7");
        tcCodeMap.put("KE", "254");
        tcCodeMap.put("KI", "686");
        tcCodeMap.put("KW", "965");
        tcCodeMap.put("KG", "996");
        tcCodeMap.put("LA", "856");
        tcCodeMap.put("LV", "371");
        tcCodeMap.put("LB", "961");
        tcCodeMap.put("LS", "266");
        tcCodeMap.put("LR", "231");
        tcCodeMap.put("LY", "218");
        tcCodeMap.put("LI", "423");
        tcCodeMap.put("LT", "370");
        tcCodeMap.put("LU", "352");
        tcCodeMap.put("MO", "853");
        tcCodeMap.put("MK", "389");
        tcCodeMap.put("MG", "261");
        tcCodeMap.put("MW", "265");
        tcCodeMap.put("MY", "60");
        tcCodeMap.put("MV", "960");
        tcCodeMap.put("ML", "223");
        tcCodeMap.put("MT", "356");
        tcCodeMap.put("MH", "692");
        tcCodeMap.put("MQ", "596");
        tcCodeMap.put("MR", "222");
        tcCodeMap.put("MU", "230");
        tcCodeMap.put("YT", "262");
        tcCodeMap.put("MX", "52");
        tcCodeMap.put("MD", "373");
        tcCodeMap.put("MC", "377");
        tcCodeMap.put("MN", "976");
        tcCodeMap.put("ME", "382");
        tcCodeMap.put("MS", "1");
        tcCodeMap.put("MA", "212");
        tcCodeMap.put("MZ", "258");
        tcCodeMap.put("MM", "95");
        tcCodeMap.put("NA", "264");
        tcCodeMap.put("NR", "674");
        tcCodeMap.put("NP", "977");
        tcCodeMap.put("NL", "31");
        tcCodeMap.put("NC", "687");
        tcCodeMap.put("NZ", "64");
        tcCodeMap.put("NI", "505");
        tcCodeMap.put("NE", "227");
        tcCodeMap.put("NG", "234");
        tcCodeMap.put("NU", "683");
        tcCodeMap.put("NF", "672");
        tcCodeMap.put("KP", "850");
        tcCodeMap.put("MP", "1");
        tcCodeMap.put("NO", "47");
        tcCodeMap.put("OM", "968");
        tcCodeMap.put("PK", "92");
        tcCodeMap.put("PW", "680");
        tcCodeMap.put("PS", "970");
        tcCodeMap.put("PA", "507");
        tcCodeMap.put("PG", "675");
        tcCodeMap.put("PY", "595");
        tcCodeMap.put("PE", "51");
        tcCodeMap.put("PH", "63");
        tcCodeMap.put("PL", "48");
        tcCodeMap.put("PT", "351");
        tcCodeMap.put("PR", "1");
        tcCodeMap.put("QA", "974");
        tcCodeMap.put("CG", "242");
        tcCodeMap.put("RE", "262");
        tcCodeMap.put("RO", "40");
        tcCodeMap.put("RU", "7");
        tcCodeMap.put("RW", "250");
        tcCodeMap.put("SH", "290");
        tcCodeMap.put("KN", "1");
        tcCodeMap.put("PM", "508");
        tcCodeMap.put("VC", "1");
        tcCodeMap.put("WS", "685");
        tcCodeMap.put("SM", "378");
        tcCodeMap.put("ST", "239");
        tcCodeMap.put("SA", "966");
        tcCodeMap.put("SN", "221");
        tcCodeMap.put("RS", "381");
        tcCodeMap.put("SC", "248");
        tcCodeMap.put("SL", "232");
        tcCodeMap.put("SG", "65");
        tcCodeMap.put("SX", "1");
        tcCodeMap.put("SK", "421");
        tcCodeMap.put("SI", "386");
        tcCodeMap.put("SB", "677");
        tcCodeMap.put("SO", "252");
        tcCodeMap.put("ZA", "27");
        tcCodeMap.put("KR", "82");
        tcCodeMap.put("SS", "211");
        tcCodeMap.put("ES", "34");
        tcCodeMap.put("LK", "94");
        tcCodeMap.put("LC", "1");
        tcCodeMap.put("SD", "249");
        tcCodeMap.put("SR", "597");
        tcCodeMap.put("SZ", "268");
        tcCodeMap.put("SE", "46");
        tcCodeMap.put("CH", "41");
        tcCodeMap.put("SY", "963");
        tcCodeMap.put("TW", "886");
        tcCodeMap.put("TJ", "992");
        tcCodeMap.put("TZ", "255");
        tcCodeMap.put("TH", "66");
        tcCodeMap.put("BS", "1");
        tcCodeMap.put("GM", "220");
        tcCodeMap.put("TL", "670");
        tcCodeMap.put("TG", "228");
        tcCodeMap.put("TK", "690");
        tcCodeMap.put("TO", "676");
        tcCodeMap.put("TT", "1");
        tcCodeMap.put("TN", "216");
        tcCodeMap.put("TR", "90");
        tcCodeMap.put("TM", "993");
        tcCodeMap.put("TC", "1");
        tcCodeMap.put("TV", "688");
        tcCodeMap.put("UG", "256");
        tcCodeMap.put("UA", "380");
        tcCodeMap.put("AE", "971");
        tcCodeMap.put("GB", "44");
        tcCodeMap.put("US", "1");
        tcCodeMap.put("UY", "598");
        tcCodeMap.put("VI", "1");
        tcCodeMap.put("UZ", "998");
        tcCodeMap.put("VU", "678");
        tcCodeMap.put("VE", "58");
        tcCodeMap.put("VN", "84");
        tcCodeMap.put("WF", "681");
        tcCodeMap.put("EH", "212");
        tcCodeMap.put("YE", "967");
        tcCodeMap.put("ZM", "260");
    }

    public static boolean isNumeric(String str) {
        boolean isNumeric = false;
        if (!StringUtils.isEmpty(str)) {
            isNumeric = org.apache.commons.lang3.StringUtils.isNumeric(str);
        }
        return isNumeric;
    }

    public static String getRandom(String[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    public static String replaceLast(String input, String regex, String replacement) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (!matcher.find()) {
            return input;
        }
        int lastMatchStart = 0;
        do {
            lastMatchStart = matcher.start();
        } while (matcher.find());
        matcher.find(lastMatchStart);
        StringBuffer sb = new StringBuffer(input.length());
        matcher.appendReplacement(sb, replacement);
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> ObjectUtils.isEmpty(keyExtractor.apply(t)) || map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
        //return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public static <T> Predicate<T> notPredicate(Predicate<T> t) {
        return t.negate();
    }
}
