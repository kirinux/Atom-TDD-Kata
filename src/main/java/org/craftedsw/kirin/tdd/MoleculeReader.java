package org.craftedsw.kirin.tdd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

public class MoleculeReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(MoleculeReader.class);

    private static final Pattern PATTERN = Pattern.compile("(\\[(?<nested>.*)\\]|(?<symbol>[A-Z][a-z]*))(?<multiplier>\\d*)");
    private static final String MULTIPLIER_REGEX_GROUP_NAME = "multiplier";
    public static final String NESTED_GROUP_REGEX_NAME = "nested";
    public static final String SYMBOL_GROUP_REGEX_NAME = "symbol";

    public Molecule parse(String formula) {
        LOGGER.info("Parse formula {}", formula);
        if (isNull(formula) || formula.isEmpty()) {
            return new Molecule();
        }

        Matcher matcher = PATTERN.matcher(formula);
        Molecule molecule = new Molecule();
        while (matcher.find()) {
            if (isNestedFormula(matcher)) {
                String nestedFormula = extractAtomSymbol(matcher, NESTED_GROUP_REGEX_NAME);
                molecule.addMolecule(parse(nestedFormula));
            } else {
                String atom = extractAtomSymbol(matcher, SYMBOL_GROUP_REGEX_NAME);
                int count = extractAtomMultiplier(matcher);
                molecule.addAtom(atom, count);
            }
        }

        LOGGER.debug("molecule: {}", molecule);
        return molecule;
    }

    private int extractAtomMultiplier(Matcher matcher) {
        return isGroupNullOrEmpty(matcher, MULTIPLIER_REGEX_GROUP_NAME) ? 1 : Integer.parseInt(matcher.group(MULTIPLIER_REGEX_GROUP_NAME));
    }

    private boolean isNestedFormula(Matcher matcher) {
        if (isNull(matcher.group(NESTED_GROUP_REGEX_NAME))) {
            return false;
        }
        return true;
    }

    private String extractAtomSymbol(Matcher matcher, String group) {
        String symbol = matcher.group(group);
        LOGGER.debug("extracted atom symbol {}", symbol);
        return symbol;
    }

    private boolean isGroupNullOrEmpty(Matcher matcher, String name) {
        String group = matcher.group(name);
        if (isNull(group) || group.isEmpty()) {
            return true;
        }
        return false;
    }
}
