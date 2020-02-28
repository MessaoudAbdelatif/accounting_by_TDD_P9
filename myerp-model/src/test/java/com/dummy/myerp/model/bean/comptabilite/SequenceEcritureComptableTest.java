package com.dummy.myerp.model.bean.comptabilite;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class SequenceEcritureComptableTest {
  private int annee = 2020;
  private int derniereValeur = 1;
  private JournalComptable journalComptable= new JournalComptable();

  @Test
  public void constructorTest(){
    SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable(annee,derniereValeur,journalComptable);
    assertThat(sequenceEcritureComptable.getAnnee()).isEqualTo(annee);
    assertThat(sequenceEcritureComptable.getDerniereValeur()).isEqualTo(derniereValeur);
  }

  @Test
  void toStringTest() {
    SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable();
    sequenceEcritureComptable.setAnnee(annee);
    sequenceEcritureComptable.setDerniereValeur(derniereValeur);
    JournalComptable journal = new JournalComptable("AC", "Achat");
    sequenceEcritureComptable.setJournalCode(journal);
    String expectedString = "SequenceEcritureComptable{annee=2020, derniereValeur=1, journalCode=JournalComptable{code='AC', libelle='Achat'}}";
    String resultString = sequenceEcritureComptable.toString();
    assertThat(expectedString).isEqualTo(resultString);
  }
}
