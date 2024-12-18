package funny.bunny.xyz.dispute.service.adapter.rethinkdb;

import funny.bunny.xyz.dispute.service.domain.Dispute;
import funny.bunny.xyz.dispute.service.domain.repositories.DisputeRepository;
import org.springframework.stereotype.Service;

@Service
public class RethinkDbDisputeRepository implements DisputeRepository {

  @Override
  public void add(Dispute dispute) {

  }

  @Override
  public Dispute get(String id) {
    return null;
  }
}
