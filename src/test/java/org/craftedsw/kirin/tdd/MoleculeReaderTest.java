package org.craftedsw.kirin.tdd;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class MoleculeReaderTest {

    @Test
    void empty_or_null_formula_should_return_empty_results() {
        MoleculeReader parser = new MoleculeReader();
        Map<String, Integer> decomposition = parser.parse("");

        assertThat(decomposition).isEmpty();
    }

    @Test
    void one_unique_character_atom_molecule_should_return_decomposition() {
        MoleculeReader parser = new MoleculeReader();
        Map<String, Integer> decomposition = parser.parse("H");

        assertThat(decomposition).containsEntry("H", 1);
    }

    @Test
    void one_atom_molecule_should_return_decomposition() {
        MoleculeReader parser = new MoleculeReader();
        Map<String, Integer> decomposition = parser.parse("Fe");

        assertThat(decomposition).containsEntry("Fe", 1);
    }

    @Test
    void multiple_atom_molecule_should_return_decomposition() {
        MoleculeReader parser = new MoleculeReader();
        Map<String, Integer> decomposition = parser.parse("HMg");

        assertThat(decomposition)
                .containsEntry("H", 1)
                .containsEntry("Mg", 1);
    }


}