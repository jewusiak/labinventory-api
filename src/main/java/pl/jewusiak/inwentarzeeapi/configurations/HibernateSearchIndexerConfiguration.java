package pl.jewusiak.inwentarzeeapi.configurations;

import jakarta.transaction.Transactional;
import org.hibernate.search.mapper.orm.Search;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;
import pl.jewusiak.inwentarzeeapi.models.Equipment;

@Component
public class HibernateSearchIndexerConfiguration implements ApplicationListener<ContextRefreshedEvent> {


    private final LocalContainerEntityManagerFactoryBean entityManagerFactory;

    public HibernateSearchIndexerConfiguration(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        var ss = Search.session(entityManagerFactory.createNativeEntityManager(null));

        try {
            ss.massIndexer(Equipment.class).startAndWait();
        } catch (InterruptedException e) {
            System.out.println("Error occured trying to build Hibernate Search indexes "
                    + e);
        }
    }


}