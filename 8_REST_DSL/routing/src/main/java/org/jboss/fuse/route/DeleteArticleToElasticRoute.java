package org.jboss.fuse.route;

import org.jboss.fuse.service.ElasticSearchService;
import org.apache.camel.Exchange;
import org.apache.camel.component.elasticsearch.ElasticsearchConfiguration;
import org.apache.camel.component.elasticsearch.ElasticsearchConstants; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteArticleToElasticRoute extends OnExceptionElasticSearch {

    final static Logger LOG = LoggerFactory.getLogger(ElasticSearchService.class);

    @Override
    public void configure() throws Exception {

        from("direct:remove").id("remove-direct-route")
                .log("Remove a Blog entry service called !")
                .setHeader(ElasticsearchConstants.PARAM_INDEX_NAME).simple("{{indexname}}")
                .setHeader(ElasticsearchConstants.PARAM_INDEX_TYPE).simple("{{indextype}}")

                // We will search for the ID of the Blog Article
                .setHeader(ElasticsearchConstants.PARAM_OPERATION).constant(ElasticsearchConstants.OPERATION_GET_BY_ID)
                
                // Set the id of the article to search for
                .setBody().simple("${header.id}")
                
                .to("elasticsearch://{{clustername}}?ip={{address}}")
                .beanRef("elasticSearchService", "getBlog")
                
                .choice()
                    .when()
                       // If No article has been retrieved, we generate a message for the HTTP Client
                       .simple("${body} == null")
                       .setBody().simple("No article has been retrieved from the ES DB for this id ${header.id}.")
                       .setHeader(Exchange.CONTENT_TYPE).constant("text/plain")
                       .setHeader(Exchange.HTTP_RESPONSE_CODE).constant(200)
                       .endChoice()
                    .otherwise()
                       // We will delete now the article if a result has been retrieved
                       .setHeader(ElasticsearchConstants.PARAM_OPERATION).constant(ElasticsearchConstants.OPERATION_DELETE)
                       .beanRef("elasticSearchService", "remove")
                       .to("elasticsearch://{{clustername}}?ip={{address}}");
        
    }
}
