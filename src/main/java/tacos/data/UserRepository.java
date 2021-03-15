package tacos.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import tacos.User;

public interface UserRepository extends CrudRepository<User, Long> {

	User findByUserName(String username) throws UsernameNotFoundException;
}

