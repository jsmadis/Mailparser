package sk.smadis.facade;

import sk.smadis.api.dto.mailparser.UserDTO;
import sk.smadis.api.dto.mailparser.create.UserCreateDTO;
import sk.smadis.api.facade.UserFacade;
import sk.smadis.facade.mapper.AvoidCycleMappingContext;
import sk.smadis.facade.mapper.Mapper;
import sk.smadis.service.UserService;
import sk.smadis.storage.entity.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@Stateless
public class UserFacadeImpl implements UserFacade {
    @Inject
    private UserService userService;

    @Override
    public UserDTO create(UserCreateDTO userCreateDTO) {
        User mappedUser = Mapper.INSTANCE
                .createDTOToUser(userCreateDTO, new AvoidCycleMappingContext());
        User user = userService.create(mappedUser);
        return Mapper.INSTANCE
                .userToDTOUser(user, new AvoidCycleMappingContext());
    }

    @Override
    public void update(UserDTO userDTO) {
        User mappedUser = Mapper.INSTANCE
                .userDTOToUser(userDTO, new AvoidCycleMappingContext());
        userService.update(mappedUser);
    }

    @Override
    public void delete(Long id) {
        userService.delete(id);
    }

    @Override
    public UserDTO findById(Long id) {
        User user = userService.findById(id);
        return Mapper.INSTANCE
                .userToDTOUser(user, new AvoidCycleMappingContext());
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return Mapper.INSTANCE
                .listUsersToDTOUsers(userService.getAllUsers(), new AvoidCycleMappingContext());
    }
}
