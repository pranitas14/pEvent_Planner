
package com.example.Event.Management.EventRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.Event.Management.Entity.Event;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {
}