package org.craftedsw.kirin.tdd;

import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ToString
public class Molecule {

    private static final Logger LOGGER = LoggerFactory.getLogger(Molecule.class);

    private Map<String, Integer> atoms = new HashMap<>();

    public void addSubMolecule(Molecule molecule) {
        LOGGER.info("add nested molecule {}", molecule);
        this.atoms = Stream.concat(atoms.entrySet().stream(), molecule.getAtoms().entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        Integer::sum
                ));
    }

    public Molecule multiply(int multiplier) {
        LOGGER.info("multiply x{}", multiplier);
        atoms.replaceAll((s, integer) -> integer * multiplier);
        return this;
    }

    public Map<String, Integer> getAtoms() {
        return Collections.unmodifiableMap(atoms);
    }

    public String toAtomicString() {
        StringBuilder builder = new StringBuilder();
        atoms.forEach((s, integer) -> builder.append(s).append(integer));
        return builder.toString();
    }

    public void addAtom(String atom, int multiplier) {
        LOGGER.debug("atom found {} x {}", atom, multiplier);
        atoms.compute(atom, (s, integer) -> {
            if (integer == null) {
                return multiplier;
            } else {
                return integer + multiplier;
            }
        });
    }

    public boolean isEmpty() {
        return atoms.isEmpty();
    }
}
