package com.redhat.gpte.training.camel.h2;

import org.apache.camel.spi.IdempotentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class JdbcMessageIdRepository implements IdempotentRepository<String> {
    private static final Logger LOG = LoggerFactory.getLogger(JdbcMessageIdRepository.class);

	private JdbcTemplate jdbcTemplate;

    public void setDataSource( DataSource pDataSource ) {
		this.jdbcTemplate = new JdbcTemplate( pDataSource );
	}
    
    @Override
    public void clear(){

    }    
    
    @Override
    public boolean add( String pMessageId ) {
        LOG.debug("Entered add(), with pMessageId = " + pMessageId);
    	
    	// check we already have it because eager option can have been turned on
        if (contains(pMessageId)) {
            return false;
        }
        
    	jdbcTemplate.update( "INSERT INTO \"ProcessedPayments\" ( \"paymentIdentifier\" ) VALUES (?)", new Object[] { pMessageId } );

        return true;
    }

    @Override
    public boolean contains(String key) {
        int numMatches = jdbcTemplate.queryForInt("SELECT count(0) FROM \"ProcessedPayments\" where \"paymentIdentifier\" = ?", new Object[] { key });

        return numMatches > 0;
    }

    @Override
    public boolean remove( String pMessageId ) {
        jdbcTemplate.execute( "DELETE FROM \"ProcessedPayments\" WHERE \"paymentIdentifier\" = '" + pMessageId + "'" );

        return true;
    }

    @Override
    public boolean confirm( String pMessageId ) {
        LOG.debug("Entered confirm(), with pMessageId = " + pMessageId);
        // noop
    	return true;
    }

    @Override
    public void start() throws Exception {

}

    @Override
    public void stop() throws Exception {

    }
}
