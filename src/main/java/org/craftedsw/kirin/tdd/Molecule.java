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

    public void addMolecule(Molecule molecule) {
        LOGGER.info("add nested molecule {}", molecule);
        this.atoms = Stream.concat(atoms.entrySet().stream(), molecule.getAtoms().entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (integer, integer2) -> integer + integer2
                ));
    }

    public Map<String, Integer> getAtoms() {
        return Collections.unmodifiableMap(atoms);
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
