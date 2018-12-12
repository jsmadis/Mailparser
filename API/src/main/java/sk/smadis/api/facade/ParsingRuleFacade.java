package sk.smadis.api.facade;

import sk.smadis.api.dto.mailparser.ParsingRuleDTO;
import sk.smadis.api.dto.mailparser.create.ParsingRuleCreateDTO;

import java.util.List;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
public interface ParsingRuleFacade {
    ParsingRuleDTO create(ParsingRuleCreateDTO parsingRuleDTO);

    void update(ParsingRuleDTO parsingRuleDTO);

    void delete(Long id);

    ParsingRuleDTO findById(Long id);

    List<ParsingRuleDTO> getAllParsingRules();
}
