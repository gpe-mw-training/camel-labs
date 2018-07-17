package com.redhat.gpte.training.camel;

import com.redhat.training.payment.Payment;
import com.redhat.training.payment.Payments;
import org.apache.camel.Body;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class PaymentBean {

    private JdbcTemplate jdbcTemplate;

    private static final Logger LOG = LoggerFactory.getLogger(PaymentBean.class);

    public void insert(@Body Payments payments) throws Exception {
        LOG.debug("Attempting to process an incoming message..");
        LOG.info("Message looks good -> save it to the DB");
        for ( Payment payment : payments.getPayment()) {
            jdbcTemplate.update(
                    "insert into \"Payments\" ( \"from\", \"to\", \"amount\", \"currency\" ) values ('"
                            + payment.getFrom()
                            + "', '"
                            + payment.getTo()
                            + "', '"
                            + payment.getAmount()
                            + "', '"
                            + payments.getCurrency()
                            + "');"
            );
        }

        if (payments.getCurrency().equals("???")) {
            LOG.warn("Rejecting payments file with currency '???'");
            throw new Exception("Rejecting payments file with currency '???'");
        }
    }

    public void setDataSource( DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
}
