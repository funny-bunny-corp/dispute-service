package funny.bunny.xyz.dispute.service.infrastructure;

import funny.bunny.xyz.dispute.service.domain.Dispute;
import funny.bunny.xyz.dispute.service.domain.repositories.DisputeRepository;
import org.springframework.stereotype.Repository;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Repository
public class InMemoryDisputeRepository implements DisputeRepository {

    private final Map<String, Dispute> disputes = new ConcurrentHashMap<>();

    @Override
    public void add(Dispute dispute) {
        disputes.put(dispute.getId(), dispute);
    }

    @Override
    public Dispute get(String id) {
        return disputes.get(id);
    }
}