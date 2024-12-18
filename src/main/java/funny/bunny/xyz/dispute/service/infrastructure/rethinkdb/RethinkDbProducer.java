package funny.bunny.xyz.dispute.service.infrastructure.rethinkdb;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RethinkDbProducer {

  @Bean
  public RethinkDB db() {
    return RethinkDB.r;
  }

  @Bean
  public Connection connection(RethinkDB rethinkDB){
    return rethinkDB.connection().hostname("localhost").port(28015).connect();
  }

}
