package pl.jewusiak.inwentarzeeapi.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.search.engine.search.predicate.dsl.BooleanPredicateClausesStep;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.stereotype.Repository;
import pl.jewusiak.inwentarzeeapi.models.Equipment;

import java.util.List;

@Repository
public class EquipmentSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Equipment> doSearch(Long id, String location, String name, boolean searchInDescription) {
        SearchSession session = Search.session(entityManager);


        return session.search(Equipment.class).where(f -> {
            BooleanPredicateClausesStep<?> queryStep = f.bool();
            if (id != null) queryStep.must(f.match().field("id").matching(id));
            if (location != null) queryStep.must(f.match().field("location").matching(location).fuzzy(1));
            if (name != null) {
                if (!searchInDescription) queryStep.must(f.match().field("name").matching(name).fuzzy(2));
                else queryStep.must(f.match().fields("name", "description").matching(name).fuzzy(2));
            }
            return queryStep;
        }).fetchAllHits();
    }
}
