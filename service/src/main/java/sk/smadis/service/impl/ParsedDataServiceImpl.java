package sk.smadis.service.impl;

import sk.smadis.service.ParsedDataService;
import sk.smadis.service.exceptions.MailparserServiceException;
import sk.smadis.storage.dao.ParsedDataDao;
import sk.smadis.storage.entity.ParsedData;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@Stateless
public class ParsedDataServiceImpl implements ParsedDataService {
    @Inject
    private ParsedDataDao parsedDataDao;

    @Override
    public ParsedData create(ParsedData parsedData) throws MailparserServiceException {
        if (parsedData == null) {
            throw new IllegalArgumentException("Parsed data shouldn't be null.");
        }
        try {
            return parsedDataDao.create(parsedData);
        } catch (Throwable t) {
            throw new MailparserServiceException(t.getMessage());
        }
    }

    @Override
    public ParsedData update(ParsedData parsedData) throws MailparserServiceException {
        if (parsedData == null) {
            throw new IllegalArgumentException("Parsed data shouldn't be null.");
        }
        try {
            return parsedDataDao.update(parsedData);
        } catch (Throwable t) {
            throw new MailparserServiceException(t.getMessage());
        }
    }

    @Override
    public void delete(ParsedData parsedData) throws MailparserServiceException {
        if (parsedData == null) {
            throw new IllegalArgumentException("Parsed data shouldn't be null.");
        }
        try {
            parsedDataDao.delete(parsedData);
        } catch (Throwable t) {
            throw new MailparserServiceException(t.getMessage());
        }
    }

    @Override
    public ParsedData findById(Long id) throws MailparserServiceException {
        if (id == null) {
            throw new IllegalArgumentException("Id shouldn't be null.");
        }
        try {
            return parsedDataDao.findById(id);
        } catch (Throwable t) {
            throw new MailparserServiceException(t.getMessage());
        }
    }

    @Override
    public List<ParsedData> getAllParsedData() throws MailparserServiceException {
        try {
            return parsedDataDao.getAllParsedData();
        } catch (Throwable t) {
            throw new MailparserServiceException(t.getMessage());
        }
    }
}
