package com.oip.infrastructure.memory;

import static org.assertj.core.api.Assertions.assertThat;

import com.oip.domain.memory.MemoryCollection;
import com.oip.domain.memory.MemoryEntry;
import com.oip.domain.memory.MemorySource;
import com.oip.domain.memory.MemorySourceType;
import com.oip.domain.workspace.Workspace;
import com.oip.infrastructure.workspace.WorkspaceJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = MemoryEntryJpaRepositoryTest.RepositoryTestConfiguration.class)
class MemoryEntryJpaRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("pgvector/pgvector:pg16"));

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.flyway.enabled", () -> true);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
    }

    @Autowired
    private WorkspaceJpaRepository workspaceJpaRepository;

    @Autowired
    private MemoryCollectionJpaRepository memoryCollectionJpaRepository;

    @Autowired
    private MemorySourceJpaRepository memorySourceJpaRepository;

    @Autowired
    private MemoryEntryJpaRepository memoryEntryJpaRepository;

    @Test
    void searchesEntriesByTitleAndContentWithinWorkspace() {
        Workspace workspace = workspaceJpaRepository.save(new Workspace("Platform Delivery", "Project workspace"));
        MemoryCollection collection = memoryCollectionJpaRepository.save(new MemoryCollection(
                workspace.getId(),
                "Runbooks",
                "Operational memory"));
        MemorySource source = memorySourceJpaRepository.save(new MemorySource(
                workspace.getId(),
                collection.getId(),
                MemorySourceType.DOCUMENT,
                "startup-runbook.md",
                "file:///runbooks/startup-runbook.md",
                "text/markdown"));
        MemoryEntry matchingEntry = memoryEntryJpaRepository.save(new MemoryEntry(
                workspace.getId(),
                collection.getId(),
                source.getId(),
                "Startup Runbook",
                "Apply Flyway migrations before starting the service."));
        memoryEntryJpaRepository.save(new MemoryEntry(
                workspace.getId(),
                collection.getId(),
                source.getId(),
                "Incident Note",
                "A previous timeout was caused by a missing container volume."));

        assertThat(memoryEntryJpaRepository.search(workspace.getId(), collection.getId(), "Flyway",
                org.springframework.data.domain.PageRequest.of(0, 5)))
                .extracting(MemoryEntry::getId)
                .containsExactly(matchingEntry.getId());
    }

    @SpringBootConfiguration
    @EnableAutoConfiguration
    @EnableJpaRepositories(basePackageClasses = {
            WorkspaceJpaRepository.class,
            MemoryCollectionJpaRepository.class,
            MemorySourceJpaRepository.class,
            MemoryEntryJpaRepository.class
    })
    @EntityScan(basePackages = "com.oip.domain")
    static class RepositoryTestConfiguration {
    }
}
