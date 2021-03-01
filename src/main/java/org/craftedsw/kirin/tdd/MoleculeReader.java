package org.craftedsw.kirin.tdd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

public class MoleculeReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(MoleculeReader.class);

    private static final Pattern PATTERN = Pattern.compile("((?<openNested>\\[)|(?<closeNested>\\])|(?<symbol>[A-Z][a-z]*))(?<multiplier>\\d*)");
    private static final String MULTIPLIER_REGEX_GROUP_NAME = "multiplier";
    public static final String OPEN_NESTED_GROUP_REGEX_NAME = "openNested";
    public static final String CLOSE_NESTED_GROUP_REGEX_NAME = "closeNested";
    public static final String SYMBOL_GROUP_REGEX_NAME = "symbol";

    private final Deque<StringBuilder> stack = new ArrayDeque<>();


    public Molecule parse(String formula) {
        LOGGER.info("Parse formula {}", formula);
        if (isNull(formula) || formula.isEmpty()) {
            return new Molecule();
        }

        Matcher matcher = PATTERN.matcher(formula);
        Molecule molecule = new Molecule();
        while (matcher.find()) {
            if (isOpen(matcher)) {
                LOGGER.debug("Start nested formula");
                StringBuilder nestedBuilder = new StringBuilder();
                stack.push(nestedBuilder);
            } else if (isClose(matcher)) {
                LOGGER.debug("End nested formula");
                int multiplier = extractAtomMultiplier(matcher);
                String nestedFormula = stack.pop().toString();

                Molecule nestedMolecule = new MoleculeReader().parse(nestedFormula).multiply(multiplier);
                if (stack.peek() != null) {
                    StringBuilder pop = stack.pop();
                    pop.append(nestedMolecule.toAtomicString());
                    stack.push(pop);
                } else {
                    molecule.addMolecule(nestedMolecule);
                }

            } else if (isCollectingNested(matcher)) {
                String atom = extractAtomSymbol(matcher, SYMBOL_GROUP_REGEX_NAME);
                int count = extractAtomMultiplier(matcher);
                StringBuilder nestedBuilder = stack.peek();
                nestedBuilder.append(atom).append(count);
                LOGGER.debug("Nested expression captured: {}", nestedBuilder.toString());
            } else {
                String atom = extractAtomSymbol(matcher, SYMBOL_GROUP_REGEX_NAME);
                int count = extractAtomMultiplier(matcher);
                molecule.addAtom(atom, count);
            }

//                if (isNestedFormula(matcher)) {
//                    String nestedFormula = extractAtomSymbol(matcher, NESTED_GROUP_REGEX_NAME);
//                    int multiplier = extractAtomMultiplier(matcher);
//                    Molecule nestedMolecule = parse(nestedFormula).multiply(multiplier);
//                    molecule.addMolecule(nestedMolecule);
//                }
        }

        LOGGER.debug("molecule: {}", molecule);
        return molecule;
    }

    private int extractAtomMultiplier(Matcher matcher) {
        return isGroupNullOrEmpty(matcher, MULTIPLIER_REGEX_GROUP_NAME) ? 1 : Integer.parseInt(matcher.group(MULTIPLIER_REGEX_GROUP_NAME));
    }

    private boolean isOpen(Matcher matcher) {
        if (isNull(matcher.group(OPEN_NESTED_GROUP_REGEX_NAME))) {
            return false;
        }
        return true;
    }

    private boolean isClose(Matcher matcher) {
        if (isNull(matcher.group(CLOSE_NESTED_GROUP_REGEX_NAME))) {
            return false;
        }
        return true;
    }

    private boolean isCollectingNested(Matcher matcher) {
        return !stack.isEmpty();
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
