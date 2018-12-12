package sk.smadis.facade;

import sk.smadis.api.dto.mailparser.ParsingRuleDTO;
import sk.smadis.api.dto.mailparser.create.ParsingRuleCreateDTO;
import sk.smadis.api.facade.ParsingRuleFacade;
import sk.smadis.facade.mapper.AvoidCycleMappingContext;
import sk.smadis.facade.mapper.Mapper;
import sk.smadis.service.ParsingRuleService;
import sk.smadis.storage.entity.ParsingRule;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@Stateless
public class ParsingRuleFacadeImpl implements ParsingRuleFacade {
    @Inject
    private ParsingRuleService parsingRuleService;

    @Override
    public ParsingRuleDTO create(ParsingRuleCreateDTO parsingRuleCreateDTO) {
        ParsingRule mappedParsingRule = Mapper.INSTANCE
                .createDtoToParsingRule(parsingRuleCreateDTO, new AvoidCycleMappingContext());
        ParsingRule parsingRule = parsingRuleService.create(mappedParsingRule);
        return Mapper.INSTANCE
                .parsingRuleToDto(new AvoidCycleMappingContext(),parsingRule);
    }

    @Override
    public void update(ParsingRuleDTO parsingRuleDTO) {
        ParsingRule mappedParsingRule = Mapper.INSTANCE
                .dtoToParsingRule(new AvoidCycleMappingContext(),parsingRuleDTO);
        parsingRuleService.update(mappedParsingRule);
    }

    @Override
    public void delete(Long id) {
        parsingRuleService.delete(id);
    }

    @Override
    public ParsingRuleDTO findById(Long id) {
        ParsingRule parsingRule = parsingRuleService.findById(id);
        return Mapper.INSTANCE
                .parsingRuleToDto(new AvoidCycleMappingContext(), parsingRule);
    }

    @Override
    public List<ParsingRuleDTO> getAllParsingRules() {
        return Mapper.INSTANCE
                .listParsingRulesToDto(new AvoidCycleMappingContext(),parsingRuleService.getAllParsingRules());
    }
}
