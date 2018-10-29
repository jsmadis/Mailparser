package sk.smadis.facade;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.After;
import org.junit.Before;
import sk.smadis.api.dto.mailparser.MailboxDTO;
import sk.smadis.api.dto.mailparser.ParsedDataDTO;
import sk.smadis.api.dto.mailparser.ParsingRuleDTO;
import sk.smadis.api.dto.mailparser.StoredMessageDTO;
import sk.smadis.api.dto.mailparser.UserDTO;
import sk.smadis.api.dto.mailparser.create.MailboxCreateDTO;
import sk.smadis.api.dto.mailparser.create.ParsingRuleCreateDTO;
import sk.smadis.api.dto.mailparser.create.UserCreateDTO;
import sk.smadis.api.dto.mailparser.enums.EmailComponent;
import sk.smadis.api.dto.mailparser.enums.ParsingType;
import sk.smadis.api.facade.MailboxFacade;
import sk.smadis.api.facade.ParsedDataFacade;
import sk.smadis.api.facade.ParsingRuleFacade;
import sk.smadis.api.facade.StoredMessageFacade;
import sk.smadis.api.facade.UserFacade;
import sk.smadis.facade.mapper.Mapper;
import sk.smadis.service.UserService;
import sk.smadis.service.exceptions.MailparserServiceException;
import sk.smadis.storage.dao.MailboxDao;
import sk.smadis.storage.dao.UserDao;
import sk.smadis.storage.dao.impl.MailboxDaoImpl;
import sk.smadis.storage.entity.Mailbox;
import sk.smadis.utils.DTOFactory;

import javax.inject.Inject;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
public abstract class AbstractFacadeTest {
    @Inject
    private UserTransaction tx;

    @Inject
    protected UserFacade userFacade;

    @Inject
    protected MailboxFacade mailboxFacade;

    @Inject
    protected StoredMessageFacade messageFacade;

    @Inject
    protected ParsingRuleFacade parsingRuleFacade;

    @Inject
    protected ParsedDataFacade parsedDataFacade;


    UserDTO userDTO;

    MailboxDTO mailboxDTO;

    StoredMessageDTO storedMessageDTO;

    ParsingRuleDTO parsingRuleDTO;

    ParsedDataDTO parsedDataDTO;


    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap
                .create(WebArchive.class, "test.war")
                .addPackages(true, Mailbox.class.getPackage(), UserFacade.class.getPackage(),
                        UserService.class.getPackage(), MailboxDTO.class.getPackage(),
                        MailboxFacadeTest.class.getPackage(),
                        MailparserServiceException.class.getPackage(), UserDao.class.getPackage(),
                        DTOFactory.class.getPackage(), Mapper.class.getPackage(), ParsingType.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(new StringAsset("<beans xmlns=\"http://xmlns.jcp.org/xml/ns/javaee\"\n" +
                        "       xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                        "       xsi:schemaLocation=\"http://xmlns.jcp.org/xml/ns/javaee\n" +
                        "\t\thttp://xmlns.jcp.org/xml/ns/javaee/beans_1_1.xsd\"\n" +
                        "       bean-discovery-mode=\"all\">\n" +
                        "</beans>"), "beans.xml")
                .addAsLibraries(Maven.resolver()
                        .loadPomFromFile("pom.xml")
                        .resolve("org.assertj:assertj-core")
                        .withTransitivity()
                        .asFile())
                .addAsLibraries(Maven.resolver()
                        .loadPomFromFile("pom.xml")
                        .resolve("org.mapstruct:mapstruct")
                        .withoutTransitivity()
                        .asFile());
    }

    @Before
    public void setup() throws SystemException, NotSupportedException {
        tx.begin();
        UserCreateDTO userCreateDTO = DTOFactory.createUserDTO("user");
        userDTO = userFacade.create(userCreateDTO);

        MailboxCreateDTO mailboxCreateDTO = DTOFactory.createMailboxDTO("bla");
        mailboxCreateDTO.setUser(userDTO);
        mailboxDTO = mailboxFacade.create(mailboxCreateDTO);

        ParsingRuleCreateDTO parsingRuleCreateDTO = DTOFactory.createParsingRuleDTO(
                "rule", ParsingType.TEXT, EmailComponent.BODY, "*");
        parsingRuleCreateDTO.setMailbox(mailboxDTO);
        parsingRuleDTO = parsingRuleFacade.create(parsingRuleCreateDTO);

        Calendar date = Calendar.getInstance();
        date.set(Calendar.YEAR, 2012);
        StoredMessageDTO messageBefore = DTOFactory.createStoredMessageDTO("dsadsadsafsa");
        messageBefore.setReceivedDate(date);
        messageBefore.setMailbox(mailboxDTO);

        messageFacade.create(messageBefore);


        StoredMessageDTO storedMessage = DTOFactory.createStoredMessageDTO("1231");
        storedMessage.setMailbox(mailboxDTO);
        storedMessageDTO = messageFacade.create(storedMessage);

        List<String> values = new ArrayList<>();
        values.add("one");
        values.add("two");


        storedMessageDTO = messageFacade.findById(storedMessageDTO.getId());
        parsingRuleDTO = parsingRuleFacade.findById(parsingRuleDTO.getId());
        ParsedDataDTO parsedData = DTOFactory.createParsedDataDTO(values,
                storedMessageDTO, parsingRuleDTO);
        parsedDataDTO = parsedDataFacade.create(parsedData);

        userDTO = userFacade.findById(userDTO.getId());
        mailboxDTO = mailboxFacade.findById(mailboxDTO.getId());
        parsingRuleDTO = parsingRuleFacade.findById(parsingRuleDTO.getId());
        parsedDataDTO = parsedDataFacade.findById(parsedDataDTO.getId());
        storedMessageDTO = messageFacade.findById(storedMessageDTO.getId());

    }

    @After
    public void tearDown() throws SystemException {
        tx.rollback();
    }
}
