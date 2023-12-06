package nl.hu.adsd.dtmreserveringen.repository;


import nl.hu.adsd.dtmreserveringen.entity.Session;
import nl.hu.adsd.dtmreserveringen.entity.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends CrudRepository<Session, Long> {

    Session findBySessionId(String sessionId);

    Iterable<Session> findByAccount(Account account);

    @Query("SELECT x FROM Session x WHERE DATE(x.createdAt) = CURRENT_DATE")
    Iterable<Session> findAllSessionsForCurrentDay();
}