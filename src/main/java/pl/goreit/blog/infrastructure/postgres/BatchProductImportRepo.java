package pl.goreit.blog.infrastructure.postgres;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.goreit.blog.domain.model.postgres.BatchProductImport;

@Repository
public interface BatchProductImportRepo extends CrudRepository<BatchProductImport, Long> {
}
