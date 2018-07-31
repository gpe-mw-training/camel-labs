package com.redhat.gpte.training.camel.mysql;

import com.redhat.training.payment.Payment;
import com.redhat.training.payment.Payments;
import org.apache.camel.Endpoint;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.spi.IdempotentRepository;
import org.apache.camel.spring.SpringRouteBuilder;
import org.apache.camel.spring.spi.SpringTransactionPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

public class PaymentCurrencyWithTxRouter extends SpringRouteBuilder {
	
    private static final Logger LOG = LoggerFactory.getLogger(PaymentCurrencyWithTxRouter.class);

    @EndpointInject(ref="endpointQueueIncomingTxPayments")
    Endpoint sourceUri;

    @EndpointInject(ref="endpointQueueOutgoingTxPayments")
    Endpoint targetUri;

    private JdbcTemplate jdbcTemplate;

	@Override
	public void configure() throws Exception {
        SpringTransactionPolicy txnPropagationRequired = new SpringTransactionPolicy(
            lookup( "POLICY_PROPAGATION_REQUIRED", TransactionTemplate.class )
        );
        errorHandler(
            transactionErrorHandler( txnPropagationRequired ).redeliverDelay( 5000 )
        );

        IdempotentRepository<String> idempotentRepository = lookup( "processedMessagesRepository", IdempotentRepository.class);
		
        // Initialize the Jaxb context so that Camel can marshal/unmarshal
        JaxbDataFormat jaxb = new JaxbDataFormat( "com.fusesource.training.payment" );
        jaxb.setPrettyPrint( true );
        
		// From the incoming queue
		from(sourceUri)
			// Create a JDBC transaction, and enforce transaction propagation.
            .policy(txnPropagationRequired)
            .log("Received Message ${body}")
            .idempotentConsumer(
            		header(Exchange.FILE_NAME_ONLY) ,
            		//MemoryIdempotentRepository.memoryIdempotentRepository(200)
            		idempotentRepository
            )
            
            // Unmarshal the XML content to Java using JAXB
            .log( "Unmarshal Message ${body}" )
            .unmarshal( jaxb )
			// Do some processing on the message
			.process(
                new Processor() {
                            @Override
				    public void process( Exchange exchange ) throws Exception {
                                LOG.debug("Attempting to process an incoming message..");

                        Payments payments = exchange.getIn().getBody(Payments.class);

                                LOG.info("Message looks good -> save it to the DB");
                        for ( Payment payment : payments.getPayment()) {
                            jdbcTemplate.update(
                              "insert into fuse_demo.Payments ( `from`, `to`, `amount`, `currency` ) values ('"
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
                }
            )
			// Send the result to the target queue
			.marshal( jaxb )
			.convertBodyTo( String.class )
            .log( "Save Message ${body}" )
			.to( targetUri );
        LOG.info("Configured Tx MS Message Processor for Source URI: '" + sourceUri + "'");
	}

    public void setDataSource( DataSource dataSource) {
         jdbcTemplate = new JdbcTemplate(dataSource);
    }
}
