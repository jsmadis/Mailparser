package sk.smadis.api.facade;

import sk.smadis.api.dto.mailparser.UserDTO;
import sk.smadis.api.dto.mailparser.create.UserCreateDTO;

import java.util.List;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
public interface UserFacade {
    UserDTO create(UserCreateDTO userCreateDTO);

    void update(UserDTO userDTO);

    void delete(UserDTO userDTO);

    UserDTO findById(Long id);

    List<UserDTO> getAllUsers();
}
