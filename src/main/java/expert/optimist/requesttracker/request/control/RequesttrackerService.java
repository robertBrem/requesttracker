package expert.optimist.requesttracker.request.control;

import expert.optimist.requesttracker.request.entity.Request;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Stateless
public class RequesttrackerService {

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    public Set<Request> getAll() {
        return new HashSet<>(em.createNamedQuery("Requests.getAll").getResultList());
    }

    @SuppressWarnings("unchecked")
    public Set<Request> findByClassName(String className) {
        return new HashSet<>(
                em.createNamedQuery("Requests.findByClassName")
                        .setParameter("className", className)
                        .getResultList());
    }

    public Request create(Request request) {
        request.setCallTime(LocalDateTime.now());
        return em.merge(request);
    }

}
