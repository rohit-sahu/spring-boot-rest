package io.devzona.springboot.emailproducer.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;

@Slf4j
public class MathUtils {

    private static final String DEFAULT_ZERO = "0";
    private static final String COMMA = ",";
    static String DEFAULT_COUNTRY_CNDCODE = "50";
    static String FWD_SLASH = "/";
    static String HYPHEN = "-";

    private static final String SRP_EMI_TENURE = "srpEmiTenure"; //this value should be same in emiCalculatorPopup.js
    private static final String SRP_EMI_ROI = "srpEmiRoi"; ////this value should be same in emiCalculatorPopup.js
    private static final float ROI = 10f;
    private static final float LOAN_PERCENTAGE = 0.80f;
    private static final int TENURE = 240;
    public static SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

    public static String CurrencyWordsToNumbers(String currencyWords) {
        String finalStr = "";
        if (currencyWords != null && !"".equals(currencyWords)) {
            if (currencyWords.contains("-")) {
                currencyWords = currencyWords.replaceAll("-", "");
            } else if (currencyWords.contains(",")) {
                currencyWords = currencyWords.replaceAll(",", "");
            } else if (currencyWords.contains("_")) {
                currencyWords = currencyWords.replaceAll("_", "");
            } else if (currencyWords.contains("Rs.")) {
                currencyWords = currencyWords.replaceAll("Rs.", "");
            } else if (currencyWords.contains(">")) {
                currencyWords = currencyWords.replaceAll(">", "");
            }


            if (currencyWords.contains("lac") || currencyWords.contains("lacs") || currencyWords.contains("lakh") || currencyWords.contains("lakhs") || currencyWords.contains("Lac")) {
                if (currencyWords.contains("Lac")) {
                    currencyWords = currencyWords.substring(0, currencyWords.indexOf("La"));
                } else {
                    currencyWords = currencyWords.substring(0, currencyWords.indexOf("la"));
                }
                if (currencyWords.contains(" ")) {
                    currencyWords = currencyWords.replaceAll(" ", "");
                }
                if (currencyWords.contains(".")) {
                    double val = Double.valueOf(currencyWords) * 10;
                    int valBudg = (int) val;
                    int currencyWord = valBudg * 10000;
                    finalStr = String.valueOf(currencyWord);
                } else {
                    int current = Integer.parseInt(currencyWords);
                    int currencyWord = current * 100000;
                    finalStr = String.valueOf(currencyWord);
                }
            } else {
                finalStr = currencyWords;
            }

            if (currencyWords.contains("crore") || currencyWords.contains("crores") || currencyWords.contains("cror") || currencyWords.contains("Cr")) {
                if (currencyWords.contains("Cr")) {
                    currencyWords = currencyWords.substring(0, currencyWords.indexOf("Cr"));
                } else {
                    currencyWords = currencyWords.substring(0, currencyWords.indexOf("cr"));
                }
                if (currencyWords.contains(" ")) {
                    currencyWords = currencyWords.replaceAll(" ", "");
                }
                if (currencyWords.contains(".")) {
                    double val = Double.valueOf(currencyWords) * 10;
                    int valBudg = (int) val;
                    int currencyWord = valBudg * 1000000;
                    finalStr = String.valueOf(currencyWord);
                } else {
                    int current = Integer.parseInt(currencyWords);
                    int currencyWord = current * 10000000;
                    finalStr = String.valueOf(currencyWord);
                }
            }
        }
        return finalStr;
    }

    public static String formatCurrencyInWord(long number) {
        try {
            if (number == 0) {
                return DEFAULT_ZERO;
            } else if (number < 1000) {
                return String.valueOf(number);
            } else if (number >= 10000000) {
                // In Crore(s)
                long croresAmt = number / 10000000;
                int lacsAmt = (int) (number % 10000000);
                StringBuffer buffer = new StringBuffer(String.valueOf(croresAmt));
                buffer.append(".");
                String lacsString = String.valueOf(lacsAmt);
                if (!StringUtils.isEmpty(lacsString)) {
                    if (lacsString.length() == 6) {
                        buffer.append(DEFAULT_ZERO + lacsString.charAt(0));
                    } else if (lacsString.length() == 7) {
                        buffer.append(lacsString, 0, 2);
                    } else {
                        buffer.append(DEFAULT_ZERO);
                    }
                } else {
                    buffer.append(lacsString);
                }
                if (buffer.toString().endsWith(".0")) {
                    return buffer.toString().replace(".0", "") + " Cr";
                }
				/*if(buffer.toString().equals("999999.0")) {
					buffer.replace(0, 11, "5+");
				}*/
                //buffer.append(" Cr");
				/*if(buffer.toString().equals("100.0 Cr")) {
					return "10+ Lacs";
				}*/
                //float val=Math.round(Float.parseFloat(buffer.toString())*10)/10f;
                return buffer.toString() + " Cr";
            } else if (number >= 100000 && number < 10000000) {
                // In Lac(s)
                long lacsAmt = number / 100000;
                int thousandAmt = (int) (number % 100000);
                StringBuilder buffer = new StringBuilder(String.valueOf(lacsAmt));
                buffer.append(".");
                String thousandString = String.valueOf(thousandAmt);
                if (!StringUtils.isEmpty(thousandString)) {
                    if (thousandString.length() == 4) {
                        buffer.append(DEFAULT_ZERO);
                        buffer.append(thousandString.charAt(0));
                    } else if (thousandString.length() == 5) {
                        buffer.append(thousandString, 0, 2);
                    } else {
                        buffer.append(DEFAULT_ZERO);
                    }
                } else {
                    buffer.append(thousandString);
                }

                float val = Math.round(Float.parseFloat(buffer.toString()) * 10) / 10f;
                if ((val + "").endsWith(".0")) {
                    return (val + "").replace(".0", "") + " Lac";
                }
                //buffer.append(" Lacs");
                return val + " Lac";
            } else {
                String numberString = String.valueOf(number);
                StringBuilder buffer = new StringBuilder(numberString);
                int length = buffer.length();
                length = length - 3;
                buffer.insert(length, COMMA);
                while (length > 2) {
                    length = length - 2;
                    buffer.insert(length, COMMA);
                }
                if (buffer.toString().endsWith(".0")) {
                    return buffer.toString().replace(".0", "");
                }
                return buffer.toString();
            }
        } catch (Exception e) {
            System.out.println("Exception in formatCurrencyInWord");
        }
        return number + "";
    }

    public static String formatCurrency(long number) {
        if (number == 0) {
            return DEFAULT_ZERO;
        } else if (number < 1000) {
            return String.valueOf(number);
        } else {
            String numberString = String.valueOf(number);
            StringBuffer buffer = new StringBuffer(numberString);
            int length = buffer.length();
            length = length - 3;
            buffer.insert(length, COMMA);
            while (length > 2) {
                length = length - 2;
                buffer.insert(length, COMMA);
            }
            return buffer.toString();
        }
    }

    public static String ordinal(int i) {
        int mod100 = i % 100;
        int mod10 = i % 10;
        if (mod10 == 1 && mod100 != 11) {
            return i + "st";
        } else if (mod10 == 2 && mod100 != 12) {
            return i + "nd";
        } else if (mod10 == 3 && mod100 != 13) {
            return i + "rd";
        } else {
            return i + "th";
        }
    }

    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        } else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2))
                    + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            dist = dist * 1.609344;
            return (dist);
        }
    }

    public static String calculateNavigationData(Integer data) {
        int calculatedValue = 0;
        String preparedCalVal = data.toString();
        if (data <= 999 && data > 100) {
            calculatedValue = data / 100;
            preparedCalVal = calculatedValue + "00+";
        } else if (data < 100 && data > 10) {
            calculatedValue = data / 10;
            preparedCalVal = calculatedValue + "0+";
        } else if (data > 999 && data <= 99999) {
            calculatedValue = data / 1000;
            preparedCalVal = calculatedValue + "K+";
        } else if (data > 99999 && data < 9999999) {
            calculatedValue = data / 100000;
            preparedCalVal = calculatedValue + "Lac+";
        } else if (data > 9999999) {
            calculatedValue = data / 10000000;
            preparedCalVal = calculatedValue + "Cr+";
        }
        return preparedCalVal;
    }

    public static String convertBudgetToValueWithUnits(String budgetValueOriginal) {
        if (StringUtils.isEmpty(budgetValueOriginal) || budgetValueOriginal.trim().equals("") || budgetValueOriginal.equals("0")) {
            return null;
        }
        String budgetValueImmutable = budgetValueOriginal;
        if (budgetValueImmutable.contains("0000000") && (Integer.parseInt(budgetValueImmutable) / 10000000) == (Float.parseFloat(budgetValueImmutable) / 10000000)) {
            String tempVal = Integer.toString((Integer.parseInt(budgetValueImmutable) / 10000000));
            budgetValueImmutable = tempVal + " Cr";
        } else if (budgetValueImmutable.contains("000000") && (Float.parseFloat((budgetValueImmutable.replaceAll("000000", ""))) / 10) > 1 && (Integer.parseInt(budgetValueImmutable) / 1000000) == (Float.parseFloat(budgetValueImmutable) / 1000000)) {
            String tempVal = CommonUtils.replaceLast(budgetValueImmutable, "000000", "");
            float floatVal = Float.parseFloat(tempVal) / 10;
            budgetValueImmutable = String.format("%.1f", floatVal) + " Cr";
        } else if (budgetValueImmutable.contains("00000") && (Integer.parseInt(budgetValueImmutable) / 100000) == (Float.parseFloat(budgetValueImmutable) / 100000)) {
            String tempVal = Integer.toString((Integer.parseInt(budgetValueImmutable) / 100000));
            budgetValueImmutable = tempVal + " Lac";
        }
        return budgetValueImmutable;
    }
}
