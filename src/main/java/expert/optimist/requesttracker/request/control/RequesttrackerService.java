package expert.optimist.requesttracker.request.control;

import expert.optimist.requesttracker.request.entity.Request;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    public Request create(Request request) {
        return em.merge(request);
    }

}
