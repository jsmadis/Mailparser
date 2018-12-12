package sk.smadis.facade;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import sk.smadis.api.dto.mailparser.UserDTO;
import sk.smadis.api.dto.mailparser.create.UserCreateDTO;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@RunWith(Arquillian.class)
public class UserFacadeImplTest extends AbstractFacadeTest {
    @Test
    public void testCreateUser(){
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setName("name");
        UserDTO user = userFacade.create(userCreateDTO);

        assertThat(user.getName()).isEqualTo("name");
        assertThat(user.getId()).isNotNull();
    }

    @Test
    public void testUpdateUser(){
        Long id = userDTO.getId();
        userDTO.setName("updated");
        userFacade.update(userDTO);

        UserDTO user = userFacade.findById(id);

        assertThat(user.getId()).isNotNull();
        assertThat(user.getName()).isEqualTo("updated");

    }

    @Test
    public void testDeleteUser(){
        Long id = userDTO.getId();

        userDTO = userFacade.findById(userDTO.getId());
        userFacade.delete(userDTO.getId());

        assertThat(userFacade.findById(id)).isNull();
        assertThat(mailboxFacade.findById(mailboxDTO.getId())).isNull();
        assertThat(messageFacade.findById(storedMessageDTO.getId())).isNull();
        assertThat(parsingRuleFacade.findById(parsingRuleDTO.getId())).isNull();
        assertThat(parsedDataFacade.findById(parsedDataDTO.getId())).isNull();
    }

    @Test
    public void testGetAllUsers(){
        assertThat(userFacade.getAllUsers()).containsExactly(userDTO);
    }


}
