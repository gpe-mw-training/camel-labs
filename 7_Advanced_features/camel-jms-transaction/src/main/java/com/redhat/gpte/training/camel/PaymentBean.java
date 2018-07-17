package com.redhat.gpte.training.camel;

import com.redhat.training.payment.Payments;
import org.apache.camel.Body;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaymentBean {

    private static final Logger LOG = LoggerFactory.getLogger(PaymentBean.class);

    public void validate(@Body Payments payments) throws Exception {
        if (payments.getCurrency().equals("???")) {
            LOG.warn("Rejecting payments file with currency '???'");
            throw new Exception("Rejecting payments file with currency '???'");
        } else {
            LOG.info("Message looks good.");
        }
    }
}
