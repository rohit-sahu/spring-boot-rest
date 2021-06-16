package io.devzona.springboot.emailproducer.service.impl;

import io.devzona.springboot.emailproducer.config.properties.EmailProducerProperties;
import io.devzona.springboot.emailproducer.service.EmailService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Utility to send sms
 *
 * @author Rohit.Sahu
 */
@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Value("#{emailProducerProperties.topics}")
    private Map<String, String> topics;

    @Value("#{emailProducerProperties.systemConfig}")
    private EmailProducerProperties.SystemConfig systemConfig;

    @SneakyThrows
    @Async("smsExecutor")
    public CompletableFuture<Boolean> sendFormCompletionSmsToCustomer(Object object) {
        boolean isSmsSent = false;
        if (ObjectUtils.isEmpty(object)) {
            log.info("Object can't be null and sms is not sent");
            return CompletableFuture.completedFuture(isSmsSent);
        }
        isSmsSent = true;
        log.info("Sms has been sent");
        return CompletableFuture.completedFuture(isSmsSent);
    }

    /*@Async("smsExecutor")
    public void sendOtp(String mobile, String otp) {
        String templateContent = "<OTP> is the Verification Code to Create/Edit your Home Loan application on MagicBricks";
        templateContent = templateContent.replace("<OTP>", otp);
        smsSender.sendPrioritySMS(mobile, templateContent, "sendOtp");
    }

    public void sendApplyNowSMSToBank(Tplau tplau, Tplaubank tplaubank, Tpubi tpubiBank) {
        String templateContent = "<Name> is interested in Home Loan from <Bank_Name>. Here are the details. Mobile: <number> & Email: <email_id>.";
        templateContent = templateContent.replace("<Name>", tplau.getLauname());
        templateContent = templateContent.replace("<Bank_Name>", tplaubank.getLbbankname());
        templateContent = templateContent.replace("<number>", tplau.getLauphone());
        templateContent = templateContent.replace("<email_id>", tplau.getLauemailid());

        smsSender.sendSMS(tpubiBank.getUbiprimarymobile().toString(), templateContent, "sendApplyNowSMSToBank");
    }

    public void sendApplyNowSMSToUser(Tplau tplau, Tplaubank tplaubank, Tpubi tpubiBank) {
        String templateContent;
        if (tplaubank.getLbsharecredentials() == false) {
            templateContent = "<Name>, Your details have been shared with <Bank_Name>. The Bank will get back to you shortly.";
        } else {
            templateContent = "<Name>, Your details have been shared with <Bank_Name>. You'll shortly get a callback or call the bank directly on <number>.";

            String number = tpubiBank.getUbiprimarymobile().toString();
            if (!tpubiBank.getUbicndcontmode().contains(HomeLoanConstants.CND_CONTACT_MODE_MOBILE.toString())) {
                number = tpubiBank.getUbiphone();
            }

            templateContent = templateContent.replace("<number>", number);
        }

        templateContent = templateContent.replace("<Name>", tplau.getLauname());
        templateContent = templateContent.replace("<Bank_Name>", tplaubank.getLbbankname());

        smsSender.sendSMS(tplau.getLauphone(), templateContent, "sendApplyNowSMSToUser");
    }

    @SneakyThrows
    public void sendFormPartialSMS(Tplau tplau) {
        String url = CommonUtils.getTinnyURL(String.valueOf(tplau.getLauapplubirfnum()), systemConfig.getTinyDomain(), systemConfig.getDomain() + "/homeloan/home", systemConfig.getSmsShortUrlEnv());
        String name = StringUtils.isNotNull(tplau.getLauname()) ? tplau.getLauname() : "Hi";

        String templateContent = "<name>, Your Home Loan form on Magicbricks is almost complete. Add more details & get best deals from our partner banks. Complete Now! <url>";
        templateContent = templateContent.replace("<name>", name);
        templateContent = templateContent.replace("<url>", url);

        smsSender.sendSMS(tplau.getLauphone(), templateContent, "sendFormPartialSMS");
    }

    @SneakyThrows
    public void sendFormInitiatedSMS(Tplau tplau) {
        String url = CommonUtils.getTinnyURL(String.valueOf(tplau.getLauapplubirfnum()), systemConfig.getTinyDomain(), systemConfig.getDomain() + "/homeloan/home", systemConfig.getSmsShortUrlEnv());
        String name = StringUtils.isNotNull(tplau.getLauname()) ? tplau.getLauname() : "Hi";

        String templateContent = "<name>, You expressed interest in Home Loan on Magicbricks. Fill the form & get lowest rates from our partner banks. Start Now! <url>";
        templateContent = templateContent.replace("<name>", name);
        templateContent = templateContent.replace("<url>", url);

        smsSender.sendSMS(tplau.getLauphone(), templateContent, "sendFormInitiatedSMS");
    }

    @SneakyThrows
    public void sendNonLauInitiatedSMS(Tplau tplau) {
        String url = CommonUtils.getTinnyURL(String.valueOf(tplau.getLauapplubirfnum()), systemConfig.getTinyDomain(), systemConfig.getDomain() + "/homeloan/home", systemConfig.getSmsShortUrlEnv());
        String name = StringUtils.isNotNull(tplau.getLauname()) ? tplau.getLauname() : "Hi";

        String templateContent = "<name>, You expressed interest in Home Loan on Magicbricks. Fill the form & get lowest rates from our partner banks. Start Now! <url>";
        templateContent = templateContent.replace("<name>", name);
        templateContent = templateContent.replace("<url>", url);

        smsSender.sendSMS(tplau.getLauphone(), templateContent, "sendNonLauInitiatedSMS");
    }*/
}
