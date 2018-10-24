package sk.smadis.storage.dao;

import sk.smadis.storage.entity.ParsedData;

import java.util.List;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
public interface ParsedDataDao {
    /**
     * Saves(creates) parsed data into database
     *
     * @param parsedData ParsedData to create
     * @return Created ParsedData
     */
    ParsedData create(ParsedData parsedData);

    /**
     * Updates parsed data
     *
     * @param parsedData ParsedData to update
     * @return Updated ParsedData
     */
    ParsedData update(ParsedData parsedData);

    /**
     * Deletes parsed data
     *
     * @param parsedData ParsedData to delete
     * @return Deleted ParsedData
     */
    ParsedData delete(ParsedData parsedData);

    /**
     * Find parsed data by id
     *
     * @param id Id of ParsedData
     * @return ParsedData
     */
    ParsedData findById(Long id);

    /**
     * Gets all parsd data
     *
     * @return List of ParsedData
     */
    List<ParsedData> getAllParsedData();
}
