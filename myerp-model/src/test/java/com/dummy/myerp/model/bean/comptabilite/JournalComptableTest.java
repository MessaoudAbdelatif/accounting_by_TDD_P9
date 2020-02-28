package com.dummy.myerp.model.bean.comptabilite;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class JournalComptableTest {
  private JournalComptable journalComptable;
  private String code = "fakeCode";
  private String libelle = "fakeLibelle";

  @Test
  public void toStringTest(){
    this.journalComptable = new JournalComptable(code,libelle);
    String expectedString = "JournalComptable{code='"+code+"', libelle='"+libelle+"'}";
    String resultString = this.journalComptable.toString();
    assertThat(expectedString).isEqualTo(resultString);
  }
}
