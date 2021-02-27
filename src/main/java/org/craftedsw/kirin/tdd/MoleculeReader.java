package org.craftedsw.kirin.tdd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

public class MoleculeReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(MoleculeReader.class);

    private static final Pattern PATTERN = Pattern.compile("([A-Z][a-z]*)(\\d*)");

    public Molecule parse(String formula) {
        LOGGER.info("Parse formula {}", formula);
        if (isNull(formula) || formula.isEmpty()) {
            return new Molecule();
        }

        Matcher matcher = PATTERN.matcher(formula);
        Molecule molecule = new Molecule();
        while (matcher.find()) {
            String atom = extractAtomSymbol(matcher);
            int count = extractAtomMultiplier(matcher);
            molecule.addAtom(atom, count);
        }

        LOGGER.debug("molecule: {}", molecule);
        return molecule;
    }

    private int extractAtomMultiplier(Matcher matcher) {
        return matcher.group(2).isEmpty() ? 1 : Integer.parseInt(matcher.group(2));
    }

    private String extractAtomSymbol(Matcher matcher) {
        return matcher.group(1);
    }
}
