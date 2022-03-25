package recipes.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

}
