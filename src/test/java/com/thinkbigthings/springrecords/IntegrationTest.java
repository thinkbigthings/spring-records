package com.thinkbigthings.springrecords;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;


@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "logging.level.org.hibernate.SQL=DEBUG",
        "logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE",
        "spring.main.lazy-initialization=true",
        "spring.flyway.enabled=true",
})
public class IntegrationTest {

    private static Logger LOG = LoggerFactory.getLogger(IntegrationTest.class);

    private static final String POSTGRES_IMAGE = "postgres:12";
    private static final boolean leaveRunningAfterTests = true;
    private static final int PG_PORT = 5432;

    protected static PostgreSQLContainer<?> postgres;

//    @Autowired
//            private Flyway f;

    @LocalServerPort
    protected int randomServerPort;

    @DynamicPropertySource
    static void useDynamicProperties(DynamicPropertyRegistry registry) {

        // Since we exposed the pg port we can set static connection properties in application.properties directly.
        // If we were not using withCreateContainerCmdModifier() we'd use @DynamicPropertySource
        // to populate the DynamicPropertyRegistry, write the properties to build/postgres.properties,
        // and could have in application.properties a reference to it like
        // spring.config.import=optional:file:./build/postgres.properties


        // need "autosave conservative" config, otherwise pg driver has caching issues with blue-green deployment
        // (org.postgresql.util.PSQLException: ERROR: cached plan must not change result type)

        postgres = new PostgreSQLContainer<>(POSTGRES_IMAGE)
                .withUrlParam("autosave", "conservative")
                .withReuse(leaveRunningAfterTests)
                .withUsername("test")
                .withPassword("test")
                .withExposedPorts(PG_PORT)
                .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
                        new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(PG_PORT), new ExposedPort(PG_PORT)))
                ));

        // call start ourselves so we can reuse
        // instead of letting library manage it with @TestContainers and @Container
        postgres.start();
    }

    @BeforeEach
    public void startup(TestInfo testInfo) {

        LOG.info("");
        LOG.info("=======================================================================================");
        LOG.info("Executing test " + testInfo.getDisplayName());
        LOG.info("using web server port " + randomServerPort);
        LOG.info("TestContainer jdbc url: " + postgres.getJdbcUrl());
        LOG.info("TestContainer username: " + postgres.getUsername());
        LOG.info("TestContainer password: " + postgres.getPassword());
        LOG.info("");
    }

}
