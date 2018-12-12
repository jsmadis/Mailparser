package sk.smadis.service;

import sk.smadis.service.exceptions.MailparserServiceException;
import sk.smadis.storage.entity.ParsedData;

import java.util.List;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
public interface ParsedDataService {
    ParsedData create(ParsedData parsedData) throws MailparserServiceException;

    ParsedData update(ParsedData parsedData) throws MailparserServiceException;

    void delete(Long id) throws MailparserServiceException;

    ParsedData findById(Long id) throws MailparserServiceException;

    List<ParsedData> getAllParsedData() throws MailparserServiceException;

}
