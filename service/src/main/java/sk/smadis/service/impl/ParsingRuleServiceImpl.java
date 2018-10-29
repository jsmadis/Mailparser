package sk.smadis.service.impl;

import sk.smadis.service.ParsingRuleService;
import sk.smadis.service.exceptions.MailparserServiceException;
import sk.smadis.storage.dao.ParsingRuleDao;
import sk.smadis.storage.entity.ParsingRule;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@Stateless
public class ParsingRuleServiceImpl implements ParsingRuleService {

    @Inject
    private ParsingRuleDao parsingRuleDao;

    @Override
    public ParsingRule create(ParsingRule parsingRule) throws MailparserServiceException {
        if (parsingRule == null) {
            throw new IllegalArgumentException("Parsing rule shouldn't be null.");
        }
        try {
            return parsingRuleDao.create(parsingRule);
        } catch (Throwable t) {
            throw new MailparserServiceException(t.getMessage());
        }
    }

    @Override
    public ParsingRule update(ParsingRule parsingRule) throws MailparserServiceException {
        if (parsingRule == null) {
            throw new IllegalArgumentException("Parsing rule shouldn't be null.");
        }
        try {
            return parsingRuleDao.update(parsingRule);
        } catch (Throwable t) {
            throw new MailparserServiceException(t.getMessage());
        }
    }

    @Override
    public void delete(ParsingRule parsingRule) throws MailparserServiceException {
        if (parsingRule == null) {
            throw new IllegalArgumentException("Parsing rule shouldn't be null.");
        }
        try {
            parsingRuleDao.delete(parsingRule);
        } catch (Throwable t) {
            throw new MailparserServiceException(t.getMessage());
        }
    }

    @Override
    public ParsingRule findById(Long id) throws MailparserServiceException {
        if (id == null) {
            throw new IllegalArgumentException("Id shouldn't be null.");
        }
        try {
            return parsingRuleDao.findById(id);
        } catch (Throwable t) {
            throw new MailparserServiceException(t.getMessage());
        }
    }

    @Override
    public List<ParsingRule> getAllParsingRules() throws MailparserServiceException {
        try {
            return parsingRuleDao.getAllParsingRules();
        } catch (Throwable t) {
            throw new MailparserServiceException(t.getMessage());
        }
    }
}
