package sk.smadis.storage.dao;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import sk.smadis.storage.entity.ParsedData;
import sk.smadis.storage.entity.ParsingRule;
import sk.smadis.storage.utils.EntityFactory;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@RunWith(Arquillian.class)
public class ParsedDataDaoTest extends AbstractDaoTest {

    @Test
    public void testCreateParsedData() {
        List<String> values = new ArrayList<>();
        values.add("parsedData");
        ParsedData parsedData = EntityFactory.createParsedData(values);
        parsedData.setParsingRule(parsingRule1);
        parsedData.setStoredMessage(message1);

        parsedDataDao.create(parsedData);

        ParsedData created = parsedDataDao.findById(parsedData.getId());

        assertThat(created).isEqualToComparingFieldByFieldRecursively(parsedData);
        assertThat(created.getValues()).containsExactly("parsedData");

        ParsingRule parsingRule = parsingRuleDao.findById(parsingRule1.getId());

        assertThat(parsingRule.getParsedData()).contains(created);
    }

    @Test
    public void testUpdateParsedData() {
        List<String> values = new ArrayList<>();
        values.add("updated");
        Long id = parsedData1.getId();
        parsedData1.setValues(values);

        parsedDataDao.update(parsedData1);

        ParsedData updated = parsedDataDao.findById(id);

        assertThat(updated.getParsingRule()).isEqualToComparingFieldByFieldRecursively(parsingRule1);
        assertThat(updated.getValues()).contains("updated");

        ParsingRule parsingRule = parsingRuleDao.findById(parsingRule1.getId());

        assertThat(parsingRule.getParsedData()).contains(updated);
    }

    @Test
    public void testDeleteParsedData() {
        Long id = parsedData1.getId();
        parsedDataDao.delete(parsedData1);

        assertThat(parsedDataDao.findById(id)).isNull();

        ParsingRule parsingRule = parsingRuleDao.findById(parsingRule1.getId());
        assertThat(parsingRule.getParsedData()).doesNotContain(parsedData1);
    }

    @Test
    public void testFindParsedDataByID() {
        ParsedData parsedData = parsedDataDao.findById(parsedData1.getId());

        assertThat(parsedData.getValues()).containsExactly(value1);
        assertThat(parsedData).isEqualToComparingFieldByFieldRecursively(parsedData1);
        assertThat(parsedData.getValues()).containsExactly(value1);
    }

    @Test
    public void testFindParsedDataByIDNotInDB() {
        assertThat(parsedDataDao.findById(10000L)).isNull();
    }

    @Test
    public void testGetAllParsedData() {
        List<ParsedData> parsedData = parsedDataDao.getAllParsedData();

        assertThat(parsedData).containsOnly(parsedData1);
    }

}
