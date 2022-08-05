package com.realworld.graphqlapi;

import com.realworld.graphqlapi.model.NewsArticle;
import com.realworld.graphqlapi.model.StaticImageArticle;
import com.realworld.graphqlapi.repository.ArticleRepository;
import com.realworld.graphqlapi.repository.AuthorRepository;
import com.realworld.graphqlapi.repository.DummyArticleRepository;
import com.realworld.graphqlapi.repository.DummyAuthorRepository;
import graphql.kickstart.tools.SchemaParserDictionary;
import lombok.extern.slf4j.Slf4j;
import org.jboss.as.quickstarts.ejb.remote.stateless.RemoteCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

@Configuration
@Slf4j
public class SampleDataConfiguration {
    @Bean
    ArticleRepository articleRepository(AuthorRepository authors) {
        return new DummyArticleRepository(authors);
    }

    @Bean
    AuthorRepository authorRepository() {
        return new DummyAuthorRepository();
    }

    @Bean
    SchemaParserDictionary dictionary() {
        return new SchemaParserDictionary()
            .add("NewsArticle", NewsArticle.class)
            .add("StaticImageArticle", StaticImageArticle.class);
    }

//    @Bean
//    List<Instrumentation> instrumentations() {
//        return new ArrayList<>(
//            List.of(new MaxQueryDepthInstrumentation(4)));
//    }

    @Bean
    RemoteCalculator lookupRemoteStatelessCalculator() throws NamingException {
        final Hashtable<String, String> jndiProperties = new Hashtable<>();
        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        if (Boolean.getBoolean("http")) {
//            use HTTP based invocation. Each invocation will be a HTTP request
            jndiProperties.put(Context.PROVIDER_URL, "http://localhost:8080/wildfly-services");
        } else {
            //use HTTP upgrade, an initial upgrade requests is sent to upgrade to the remoting protocol
            jndiProperties.put(Context.PROVIDER_URL, "remote+http://localhost:8080");
        }
        final Context context = new InitialContext(jndiProperties);

        // The JNDI lookup name for a stateless session bean has the syntax of:
        // ejb:<appName>/<moduleName>/<distinctName>/<beanName>!<viewClassName>
        //
        // <appName> The application name is the name of the EAR that the EJB is deployed in
        // (without the .ear). If the EJB JAR is not deployed in an EAR then this is
        // blank. The app name can also be specified in the EAR's application.xml
        //
        // <moduleName> By the default the module name is the name of the EJB JAR file (without the
        // .jar suffix). The module name might be overridden in the ejb-jar.xml
        //
        // <distinctName> : EAP allows each deployment to have an (optional) distinct name.
        // This example does not use this so leave it blank.
        //
        // <beanName> : The name of the session been to be invoked.
        //
        // <viewClassName>: The fully qualified classname of the remote interface. Must include
        // the whole package name.

        // let's do the lookup
        log.info("Creating bean with remote calculator");
        RemoteCalculator remoteCalculator = (RemoteCalculator) context.lookup("ejb:/ejb-remote-server-side/CalculatorBean!"
            + RemoteCalculator.class.getName());
        log.info("Created bean with remote calculator: " + remoteCalculator);
        return remoteCalculator;
    }
}
