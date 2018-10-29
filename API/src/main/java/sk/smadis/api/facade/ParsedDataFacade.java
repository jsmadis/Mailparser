package sk.smadis.api.facade;

import sk.smadis.api.dto.mailparser.ParsedDataDTO;

import java.util.List;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
public interface ParsedDataFacade {
    ParsedDataDTO create(ParsedDataDTO parsedDataDTO);

    void update(ParsedDataDTO parsedDataDTO);

    void delete(ParsedDataDTO parsedDataDTO);

    ParsedDataDTO findById(Long id);

    List<ParsedDataDTO> getAllParsedData();
}
