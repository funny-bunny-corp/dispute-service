package funny.bunny.xyz.dispute.service.domain.repositories;

import funny.bunny.xyz.dispute.service.domain.Dispute;

public interface DisputeRepository {

    void add(Dispute dispute);

    Dispute get(String id);

}
