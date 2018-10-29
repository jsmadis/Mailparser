package sk.smadis.facade;

import sk.smadis.api.dto.mailparser.ParsedDataDTO;
import sk.smadis.api.facade.ParsedDataFacade;
import sk.smadis.facade.mapper.AvoidCycleMappingContext;
import sk.smadis.facade.mapper.Mapper;
import sk.smadis.service.ParsedDataService;
import sk.smadis.storage.entity.ParsedData;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
@Stateless
public class ParsedDataFacadeImpl implements ParsedDataFacade {
    @Inject
    private ParsedDataService parsedDataService;

    @Override
    public ParsedDataDTO create(ParsedDataDTO parsedDataDTO) {
        ParsedData mappedParsedData = Mapper.INSTANCE.dtoToParsedData(parsedDataDTO, new AvoidCycleMappingContext());
        ParsedData parsedData = parsedDataService.create(mappedParsedData);
        return Mapper.INSTANCE.parsedDataToDto(parsedData, new AvoidCycleMappingContext());
    }

    @Override
    public void update(ParsedDataDTO parsedDataDTO) {
        ParsedData mappedParsedData = Mapper.INSTANCE.dtoToParsedData(parsedDataDTO, new AvoidCycleMappingContext());
        parsedDataService.update(mappedParsedData);
    }

    @Override
    public void delete(ParsedDataDTO parsedDataDTO) {
        ParsedData mappedParsedData = Mapper.INSTANCE.dtoToParsedData(parsedDataDTO, new AvoidCycleMappingContext());
        parsedDataService.delete(mappedParsedData);
    }

    @Override
    public ParsedDataDTO findById(Long id) {
        ParsedData parsedData = parsedDataService.findById(id);
        return Mapper.INSTANCE.parsedDataToDto(parsedData, new AvoidCycleMappingContext());
    }

    @Override
    public List<ParsedDataDTO> getAllParsedData() {
        return Mapper.INSTANCE.listParsedDataToDto(parsedDataService.getAllParsedData(), new AvoidCycleMappingContext());
    }
}
