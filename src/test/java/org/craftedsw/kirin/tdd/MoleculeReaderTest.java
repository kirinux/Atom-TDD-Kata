package org.craftedsw.kirin.tdd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MoleculeReaderTest {

    private MoleculeReader parser;

    @BeforeEach
    void setUp() {
         parser = new MoleculeReader();
    }

    @Test
    void empty_or_null_formula_should_return_empty_results() {
        Molecule molecule = parser.parse("");
        assertThat(molecule.isEmpty()).isTrue();
    }

    @Test
    void one_unique_character_atom_molecule_should_return_decomposition() {
        Molecule molecule = parser.parse("H");
        assertThat(molecule.getAtoms()).containsEntry("H", 1);
    }

    @Test
    void one_atom_molecule_should_return_decomposition() {
        Molecule molecule = parser.parse("Fe");
        assertThat(molecule.getAtoms()).containsEntry("Fe", 1);
    }

    @Test
    void multiple_atom_molecule_should_return_decomposition() {
        Molecule molecule = parser.parse("HMg");
        assertThat(molecule.getAtoms())
                .containsEntry("H", 1)
                .containsEntry("Mg", 1);
    }

    @Test
    void atom_with_multiplier_molecule_should_return_decomposition() {
        Molecule molecule = parser.parse("H2Mg");
        assertThat(molecule.getAtoms())
                .containsEntry("H", 2)
                .containsEntry("Mg", 1);

    }

    @Test
    void multiple_atom_with_multiplier_molecule_should_return_decomposition() {
        Molecule molecule = parser.parse("H2MgH3");
        assertThat(molecule.getAtoms())
                .containsEntry("H", 5)
                .containsEntry("Mg", 1);
    }

    @Test
    void unique_nested_atom_should_return_decomposition() {
        Molecule molecule = parser.parse("[H]FeMg");
        assertThat(molecule.getAtoms())
                .containsEntry("H", 1)
                .containsEntry("Fe", 1)
                .containsEntry("Mg", 1);

    }
    @Test
    void nested_atom_with_multiplier_should_return_decomposition() {
        Molecule molecule = parser.parse("[HO]2Mg");
        assertThat(molecule.getAtoms())
                .containsEntry("H", 2)
                .containsEntry("O", 2)
                .containsEntry("Mg", 1);

    }



}