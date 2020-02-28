package com.dummy.myerp.model.bean.comptabilite;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JournalComptableTest {
  private JournalComptable journalComptable;
  private String code = "fakeCode";
  private String libelle = "fakeLibelle";

  @BeforeEach
  public void setUp(){
    this.journalComptable = new JournalComptable(code,libelle);
  }

  @Test
  public void toStringTest(){
    String expectedString = "JournalComptable{code='"+code+"', libelle='"+libelle+"'}";
    String resultString = this.journalComptable.toString();
    assertThat(expectedString).isEqualTo(resultString);
  }

  @Test
  public void Constructor_withDefaultValues(){
    assertThat(journalComptable.getLibelle()).isEqualTo(libelle);
    assertThat(journalComptable.getCode()).isEqualTo(code);
  }
}
