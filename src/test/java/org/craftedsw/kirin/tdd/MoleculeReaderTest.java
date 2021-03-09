package org.craftedsw.kirin.tdd;

import org.assertj.core.api.Assertions;
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

    @Test
    void multiple_nested_atom_should_return_decomposition() {
        Molecule molecule = parser.parse("[HCl]2O[H]Na3Fe");
        assertThat(molecule.getAtoms())
                .containsEntry("H", 3)
                .containsEntry("O", 1)
                .containsEntry("Cl", 2)
                .containsEntry("Na", 3)
                .containsEntry("Fe", 1)
                ;

    }

    @Test
    void nested_atom_with_same_delimiter_should_return_decomposition() {
        Molecule molecule = parser.parse("K4[ON[SO3]2]2");
        assertThat(molecule.getAtoms())
                .containsEntry("K", 4)
                .containsEntry("O", 14)
                .containsEntry("N", 2)
                .containsEntry("S", 4)
        ;

    }

    @Test
    void nested_atom_with_different_delimiter_should_return_decomposition() {
        Molecule molecule = parser.parse("K9{ON(SO3)2}4");
        assertThat(molecule.getAtoms())
                .containsEntry("K", 9)
                .containsEntry("O", 28)
                .containsEntry("N", 4)
                .containsEntry("S", 8);
    }

    @Test
    void plop() {
        Molecule molecule = parser.parse("{[Co(NH3)4(OH)2]3Co}(SO4)3");
        assertThat(molecule.getAtoms())
                .containsEntry("Co", 4)
                .containsEntry("N", 12)
                .containsEntry("H", 42)
                .containsEntry("O", 18)
                .containsEntry("S", 3);

    }



}