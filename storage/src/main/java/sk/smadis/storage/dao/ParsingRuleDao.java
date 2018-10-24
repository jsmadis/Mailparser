package sk.smadis.storage.dao;

import sk.smadis.storage.entity.ParsingRule;

import java.util.List;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
public interface ParsingRuleDao {
    /**
     * Saves(creates) parsing rule into database
     *
     * @param parsingRule ParsingRule to create
     * @return Created ParsingRule
     */
    ParsingRule create(ParsingRule parsingRule);

    /**
     * Updates parsing rule
     *
     * @param parsingRule ParsingRule to update
     * @return Updated ParsingRule
     */
    ParsingRule update(ParsingRule parsingRule);

    /**
     * Deletes parsing rule
     *
     * @param parsingRule ParsingRule to Delete
     * @return Deteled ParsingRule
     */
    ParsingRule delete(ParsingRule parsingRule);

    /**
     * Finds parsing rule by ID
     *
     * @param id ID of ParsingRule
     * @return ParsingRule
     */
    ParsingRule findById(Long id);

    /**
     * Gets all parsing rules
     *
     * @return List of ParsingRules
     */
    List<ParsingRule> getAllParsingRules();
}
