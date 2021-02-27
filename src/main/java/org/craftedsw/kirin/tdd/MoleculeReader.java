package org.craftedsw.kirin.tdd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MoleculeReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(MoleculeReader.class);

    private static final Pattern PATTERN = Pattern.compile("([A-Z][a-z]*)");

    public Map<String, Integer> parse(String formula) {
        LOGGER.info("Parse formula {}", formula);
        if (Objects.isNull(formula) || formula.isEmpty()) {
            return Collections.emptyMap();
        }

        Matcher matcher = PATTERN.matcher(formula);
        var decomposition = new HashMap<String, Integer>();

        while( matcher.find()) {
            String group = matcher.group();
            LOGGER.info("group found {}", group);
            decomposition.compute(group, (s, integer) -> {
                if (integer == null) {
                    return 1;
                } else {
                    return integer + 1;
                }
            });
        }

        LOGGER.debug("Decomposition: {}", decomposition);
        return decomposition;
    }
}
