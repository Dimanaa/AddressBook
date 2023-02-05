package nbu.java.repositories;

import nbu.java.entity.Contact;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ContactRepository<C> extends CrudRepository<Contact,Long> {
    List<Contact> findAll();

    List<Contact> findByUserId(int id);

    Contact findById(int id);

    List<Contact> findByFirstnameAndLastnameAndUserId(String firstname, String lastname, int id);

   @Query(value = "Select * from (SELECT * FROM contact WHERE user_id = :id) as t Where firstname IN " +
           "(SELECT firstname FROM (SELECT * FROM contact WHERE user_id = :id) as t GROUP BY firstname HAVING COUNT(distinct lastname) > 1)", nativeQuery = true)
   List<Contact> findBySameFirstnameAndDistinctLastname(int id);

    @Query(value = "Select * from (SELECT * FROM contact WHERE user_id = :id) as t Where lastname IN " +
            "(SELECT lastname FROM (SELECT * FROM contact WHERE user_id = :id) as t GROUP BY lastname HAVING COUNT(distinct firstname) > 1)", nativeQuery = true)
    List<Contact> findBySameLastnameAndDistinctFirstname(int id);
}
