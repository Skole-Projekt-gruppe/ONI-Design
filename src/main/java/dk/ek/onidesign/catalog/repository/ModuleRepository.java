    package dk.ek.onidesign.catalog.repository;

    import dk.ek.onidesign.catalog.entity.Module;
    import org.springframework.data.domain.Sort;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.data.repository.query.Param;

    import java.util.List;


    @Repository
    public interface ModuleRepository extends JpaRepository<Module, Long> {
        @Query("""
               SELECT m
               FROM Module m
               WHERE LOWER(m.moduleName) LIKE LOWER(CONCAT('%', :moduleName, '%'))
               """)
        List<Module> findByModuleNameContainingIgnoreCase(
                @Param("moduleName") String moduleName,
                Sort sort
        );

    }