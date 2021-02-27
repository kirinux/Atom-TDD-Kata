package org.craftedsw.kirin.tdd;

import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ToString
public class Molecule {

    private static final Logger LOGGER = LoggerFactory.getLogger(Molecule.class);
    
    private final Map<String, Integer> atoms = new HashMap<>();

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
