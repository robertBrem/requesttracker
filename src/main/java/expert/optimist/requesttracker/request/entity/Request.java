package expert.optimist.requesttracker.request.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "REQUESTS")
@Setter
@Getter
@EqualsAndHashCode(of = {"id"})
@ToString
@NamedQueries({
        @NamedQuery(name = "Requests.getAll", query = "SELECT r FROM Request r"),
        @NamedQuery(name = "Requests.findByClassName", query = "SELECT r FROM Request r WHERE r.className = :className")
})
public class Request {
    @Id
    @SequenceGenerator(name = "RequestsSequence", sequenceName = "REQUESTS_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RequestsSequence")
    @Column(name = "REQUEST_ID")
    private Long id;
    private String className;
    private String functionName;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> functionParameterValues;

    private LocalDateTime callTime;
    private String callerIp;
    private Integer callerPort;
    private String callerHost;
}
