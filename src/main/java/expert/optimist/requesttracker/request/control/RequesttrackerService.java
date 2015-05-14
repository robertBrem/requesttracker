package expert.optimist.requesttracker.request.control;

import expert.optimist.requesttracker.request.entity.Request;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Stateless
public class RequesttrackerService {

    public static final Long MIN_DELAY_IN_SECONDS = 5L;

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

    public Map<LocalDateTime, Set<Request>> findPerHour(String className) {
        List<Request> requests = getAllRequests(className);
        removeDuplicates(requests);

        Map<LocalDateTime, Set<Request>> requestsPerHour = new HashMap<>();
        for (Request request : requests) {
            LocalDateTime callTimePerHour = request.getCallTime().truncatedTo(ChronoUnit.HOURS);

            Set<Request> requestForHour = new HashSet<>();
            if (requestsPerHour.containsKey(callTimePerHour)) {
                requestForHour = requestsPerHour.get(callTimePerHour);
            } else {
                requestsPerHour.put(callTimePerHour, requestForHour);
            }
            requestForHour.add(request);
        }

        return requestsPerHour;
    }

    public void removeDuplicates(List<Request> requests) {
        Collections.sort(requests, (o1, o2) -> o1.getCallTime().compareTo(o2.getCallTime()));
        List<Request> toRemove = new ArrayList<>();
        LocalDateTime lastTime = LocalDateTime.MIN;
        for (Request request : requests) {
            LocalDateTime callTime = request.getCallTime();
            if (ChronoUnit.SECONDS.between(lastTime, callTime) < MIN_DELAY_IN_SECONDS) {
                toRemove.add(request);
            } else {
                lastTime = callTime;
            }
        }
        requests.removeAll(toRemove);
    }

    public List<Request> getAllRequests(String className) {
        List<Request> requests;
        if (className == null) {
            return new ArrayList<>(getAll());
        } else {
            return new ArrayList<>(findByClassName(className));
        }
    }

    public Request create(Request request) {
        request.setCallTime(LocalDateTime.now());
        return em.merge(request);
    }

}
