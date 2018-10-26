package sk.smadis.storage.dao;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.After;
import org.junit.Before;
import sk.smadis.storage.entity.Mailbox;
import sk.smadis.storage.entity.ParsedData;
import sk.smadis.storage.entity.ParsingRule;
import sk.smadis.storage.entity.StoredMessage;
import sk.smadis.storage.entity.User;
import sk.smadis.storage.entity.enums.EmailComponent;
import sk.smadis.storage.entity.enums.ParsingType;
import sk.smadis.storage.utils.EntityFactory;

import javax.inject.Inject;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
public abstract class AbstractDaoTest {
    @Inject
    private UserTransaction tx;

    @Inject
    protected MailboxDao mailboxDao;

    @Inject
    protected ParsingRuleDao parsingRuleDao;

    @Inject
    protected UserDao userDao;

    @Inject
    protected StoredMessageDao messageDao;

    @Inject
    protected ParsedDataDao parsedDataDao;

    Mailbox mailbox1;
    Mailbox mailbox2;

    User user1;
    User user2;

    ParsingRule parsingRule1;
    ParsingRule parsingRule2;

    ParsedData parsedData1;
    ParsedData parsedData2;

    StoredMessage message1;
    StoredMessage message2;

    String value1 = "value1";
    String value2 = "value2";


    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap
                .create(WebArchive.class, "test.war")
                .addPackages(true, Mailbox.class.getPackage(), MailboxDao.class.getPackage(),
                        EntityFactory.class.getPackage(), AbstractDaoTest.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsLibraries(Maven.resolver()
                        .loadPomFromFile("pom.xml")
                        .resolve("org.assertj:assertj-core")
                        .withTransitivity()
                        .asFile());

    }


    @Before
    public void setupMethod() throws SystemException, NotSupportedException {
        tx.begin();
        mailbox1 = EntityFactory.createMailbox("mailbox1");
        mailbox2 = EntityFactory.createMailbox("mailbox2");

        user1 = EntityFactory.createUser("user1");
        user2 = EntityFactory.createUser("user2");

        parsingRule1 = new EntityFactory.ParsingRuleBuilder()
                .name("parsingRule1")
                .parsingType(ParsingType.TEXT)
                .emailComponent(EmailComponent.BODY)
                .rule("test")
                .build();

        parsingRule2 = new EntityFactory.ParsingRuleBuilder()
                .name("parsingRule2")
                .parsingType(ParsingType.TEXT)
                .emailComponent(EmailComponent.HEADERS)
                .rule("test3")
                .build();


        List<String> values1 = new ArrayList<>();
        values1.add(value1);
        List<String> values2 = new ArrayList<>();
        values2.add(value2);

        parsedData1 = EntityFactory.createParsedData(values1);
        parsedData2 = EntityFactory.createParsedData(values2);
        Calendar beforeDate = Calendar.getInstance();
        beforeDate.set(Calendar.YEAR, 2015);

        StoredMessage older_message1 = EntityFactory.createMessage("dsadsa");
        older_message1.setReceivedDate(beforeDate);
        StoredMessage older_message2 = EntityFactory.createMessage("dsafsdafa");
        older_message2.setReceivedDate(beforeDate);

        message1 = EntityFactory.createMessage("12314");
        message2 = EntityFactory.createMessage("23132141");


        userDao.create(user1);

        mailbox1.setUser(user1);
        mailboxDao.create(mailbox1);


        parsingRule1.setMailbox(mailbox1);
        parsingRuleDao.create(parsingRule1);


        older_message1.setMailbox(mailbox1);
        older_message2.setMailbox(mailbox1);
        message1.setMailbox(mailbox1);
        messageDao.create(older_message1);
        messageDao.create(older_message2);
        messageDao.create(message1);


        parsedData1.setStoredMessage(message1);
        parsedData1.setParsingRule(parsingRule1);
        parsedDataDao.create(parsedData1);

        mailbox1 = mailboxDao.findById(mailbox1.getId());

        assertThat(userDao.getAllUsers()).containsExactly(user1);
        assertThat(mailboxDao.getAllMailboxes()).containsExactly(mailbox1);
        assertThat(parsingRuleDao.getAllParsingRules()).containsExactly(parsingRule1);
        assertThat(messageDao.getAllStoredMessages())
                .containsExactlyInAnyOrder(message1, older_message1, older_message2);
        assertThat(parsedDataDao.getAllParsedData()).containsExactly(parsedData1);


    }

    @After
    public void tearDown() throws SystemException {
        tx.rollback();
    }
}
