package io.devzona.springboot.emailproducer.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

@Slf4j
public class CalculatorsUtility {

    private static final String SRP_EMI_TENURE = "srpEmiTenure"; //this value should be same in emiCalculatorPopup.js
    private static final String SRP_EMI_ROI = "srpEmiRoi"; ////this value should be same in emiCalculatorPopup.js
    private static final float ROI = 10f;
    private static final float LOAN_PERCENTAGE = 0.80f;
    private static final int TENURE = 240;
    public static SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

    public static double calculateLog(double radix, double base) {
        double log = Math.log(radix) / Math.log(base);
        return log;
    }

    public static double cumipmt(double rate, int nper, double pv,
                                 int start_period, int end_period, int type) {
        rate = rate / 1200.0;
        double cumipmt = 0;
        double thePmt = pmtForCumipmt(rate, nper, pv, 0, type);
        double endValue = pv;

        if (type == 0) {
            for (int i = 1; i <= end_period; i++) {
                double beginning = endValue;
                double interest = beginning * rate;
                endValue = beginning + interest + thePmt;

                if (i >= start_period) {
                    cumipmt += interest;
                }
            }
        } else {
            for (int i = 1; i < end_period; i++) {
                double beginning = endValue + thePmt;
                double interest = beginning * rate;
                endValue = beginning + interest + thePmt;

                if (i >= start_period) {
                    cumipmt += interest;
                }
            }
        }

        return -cumipmt;
    }

    private static double pvFactor(double rate, int nper) {
        return (1 / fvFactor(rate, nper));
    }

    private static double fvFactor(double rate, int nper) {
        return Math.pow(1 + rate, nper);
    }

    private static double annuityPvFactor(double rate, int nper, int type) {
        if (rate == 0) {
            return nper;
        } else {
            return (1 + rate * type) * (1 - pvFactor(rate, nper)) / rate;
        }

    }

    private static double pmtForCumipmt(double rate, int nper, double pv,
                                        double fv, int type) {
        return (-1 * (pv + fv + pvFactor(rate, nper)))
                / (annuityPvFactor(rate, nper, type));
    }

    public static double calculateFutureValue(double rate, int value,
                                              double amount) {
        double currentPriceOfEmiPaid = 0.0;
        for (int i = value - 1; i >= 0; i--) {
            currentPriceOfEmiPaid += amount
                    * (Math.pow(1 + (rate / 1200.0), i));
        }
        return -currentPriceOfEmiPaid;
    }

    public static String formatCurrencyFields(double value) {
        String formattedMesg = "";
        DecimalFormat df = new DecimalFormat("#.00");
        double lacValue;
        double crValue;
        double thsValue;
        crValue = value / 10000000;
        lacValue = value / 100000;
        thsValue = value / 1000;
        if (crValue >= 1) {
            formattedMesg = df.format(crValue) + "Cr";
        } else if (lacValue >= 1) {
            formattedMesg = df.format(lacValue) + "Lac";
        } else if (thsValue >= 1) {
            formattedMesg = df.format(thsValue) + "Ths";
        } else {
            formattedMesg = String.valueOf(value);
        }
        return formattedMesg;
    }

    public void setEmiPriceInProperyBean(final HttpServletRequest request, Map<Object, Object> map) {
        try {
            final Cookie[] cookies = request.getCookies();
            String emiTenure = null;
            String emiRoi = null;
            float roi = 0;
            int tenure = 0;
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equalsIgnoreCase(SRP_EMI_TENURE)) {
                        emiTenure = cookie.getValue();
                    }
                    if (cookie.getName().equalsIgnoreCase(SRP_EMI_ROI)) {
                        emiRoi = cookie.getValue();
                    }
                }
            }
            roi = StringUtils.isEmpty(emiRoi) ? ROI : Float.valueOf(emiRoi);
            tenure = StringUtils.isEmpty(emiTenure) ? TENURE : Integer.valueOf(emiTenure);
            if (tenure <= 50) tenure = tenure * 12;
            float annuity = calculateAnnuity(tenure, roi);
            log.info("Annuity============================================" + annuity);
            float loanAmount = 0, emiPaid = 0;
            long emi = 0;
            String price = null;
            try {
                price = map.get("price").toString();
                loanAmount = StringUtils.isEmpty(price) ? 0 : Float.valueOf(price);
                emiPaid = (roi * loanAmount * LOAN_PERCENTAGE * annuity) / 1200;
                emi = (long) Math.ceil(emiPaid);
                map.put("emiPrice", MathUtils.formatCurrency(emi));
            } catch (final RuntimeException e) {
                log.error("================error occured while calculating emi ");
            }
        } catch (final Exception e) {
            log.error("================error occured inside setEmiPriceInProperyBean ", e);
        }
    }

    public long calculateEMI(String price, float roi, float loanPercentage,
                             float annuity) {
        price = MathUtils.CurrencyWordsToNumbers(price);
        float loanAmount = StringUtils.isEmpty(price) ? 0 : Float.valueOf(price);
        float emiPaid = (roi * loanAmount * LOAN_PERCENTAGE * annuity) / 1200;
        long emi = (long) Math.ceil(emiPaid);
        return emi;
    }

    /**
     * This method calculates annuity
     *
     * @param loanInterestRate
     * @param loanTenureInput
     * @return
     */
    public float calculateAnnuity(int loanTenureInput, float loanInterestRate) {
        float fact = 1;
        for (int i = 0; i < loanTenureInput; i++) {
            fact = fact * (1 + (loanInterestRate / 1200));
        }

        return fact / (fact - 1);
    }
}
