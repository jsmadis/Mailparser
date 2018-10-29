package sk.smadis.service;

import sk.smadis.service.exceptions.MailparserServiceException;
import sk.smadis.storage.entity.ParsingRule;

import java.util.List;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
public interface ParsingRuleService {
    ParsingRule create(ParsingRule parsingRule) throws MailparserServiceException;

    ParsingRule update(ParsingRule parsingRule) throws MailparserServiceException;

    void delete(ParsingRule parsingRule) throws MailparserServiceException;

    ParsingRule findById(Long id) throws MailparserServiceException;

    List<ParsingRule> getAllParsingRules() throws MailparserServiceException;
}
