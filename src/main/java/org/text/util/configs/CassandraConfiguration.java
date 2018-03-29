package org.text.util.configs;

import static org.testng.Assert.fail;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testng.Assert;
import org.text.util.entity.Schema;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PlainTextAuthProvider;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy;
import com.datastax.driver.core.policies.LoadBalancingPolicy;
import com.datastax.driver.core.policies.TokenAwarePolicy;

import info.archinnov.achilles.generated.ManagerFactory;
import info.archinnov.achilles.generated.ManagerFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class CassandraConfiguration {

    @Value("${datasource.cassandra.ip}")
    private String ip;

    @Value("${datasource.cassandra.port}")
    private int port;

    @Value("${datasource.cassandra.dc}")
    private String dataCenter;

    @Value("${datasource.cassandra.userId}")
    private String userId;

    @Value("${datasource.cassandra.password}")
    private String password;

    /**
     * Initializes inmemory cassandra data base
     *
     * @return the cassandra session
     */
    @Bean(destroyMethod = "close")
    public Cluster initializeCassandraSession() {
        Cluster cluster = null;
        try {
            EmbeddedCassandraServerHelper.startEmbeddedCassandra(50000L);
            DCAwareRoundRobinPolicy.Builder builder = DCAwareRoundRobinPolicy.builder();
            builder.withLocalDc(dataCenter);
            LoadBalancingPolicy policy = new TokenAwarePolicy(builder.build());
            Cluster.Builder cassandraClusterBuilder = Cluster.builder().withLoadBalancingPolicy(policy)
                    .addContactPoints(ip).withAuthProvider(new PlainTextAuthProvider(userId, password));
            cluster = cassandraClusterBuilder.withPort(port).build();
            log.info(
                    "Successfully Initialized cassandra with following properties: ip ==> {}, port ==> {}, datacenter ==>  {}");
            createSchema(cluster);
            log.info("Schema creation complete");

        } catch (Exception e) {
            Assert.fail("Failed to initiate embedded database", e);
        }
        return cluster;
    }

    /**
     * Initializes the Cassandra Manager
     *
     * @param cluster
     * @return managerFactory
     */
    @Bean
    public ManagerFactory createManagerFactory(Cluster cluster) {
        ManagerFactory managerFactory = null;
        try {
            managerFactory = ManagerFactoryBuilder.builder(cluster).withDefaultKeyspaceName(Schema.KEYSPACE)
                    .doForceSchemaCreation(false).withBeanValidation(true).withPostLoadBeanValidation(true).build();
            log.info("Cassandra Manager initialization complete");
        } catch (Exception e) {
            Assert.fail("Cassandra Manager initialization failed", e);
        }
        return managerFactory;
    }

    /**
     * Creates cassandra tables
     */
    private void createSchema(Cluster cluster) {
        Session session = cluster.connect();
        session.execute(getDataFromFile("src/test/resources/ddls/utils_keyspace.ddl"));
        session.execute(getDataFromFile("src/test/resources/ddls/text_util.ddl"));
        session.close();
    }

    /**
     * Loads a single text file content to a single string
     *
     * @param dataFileLocation
     *            the resource path
     * @return the string
     */
    private String getDataFromFile(String dataFileLocation) {
        StringBuilder data = new StringBuilder();
        try (BufferedReader fileReader = new BufferedReader(
                new InputStreamReader(Files.newInputStream(Paths.get(dataFileLocation))))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                data.append(line).append("\r\n");
            }
        } catch (Exception e) {
            log.error("Cannot open " + dataFileLocation, e);
            fail();
        }
        return data.toString();
    }

}
