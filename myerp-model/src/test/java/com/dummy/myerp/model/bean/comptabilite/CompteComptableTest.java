package com.dummy.myerp.model.bean.comptabilite;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class CompteComptableTest {

  private CompteComptable compteComptable;
  private Integer fakeInt = 1;
  private String fakeLibelle = "Compte test";

  @Test
  public void toStringTest(){
    this.compteComptable = new CompteComptable(fakeInt,fakeLibelle);
    String expectedString = "CompteComptable{numero="+fakeInt+", libelle='"+fakeLibelle+"'}";
    String resultString = compteComptable.toString();
    assertThat(expectedString).isEqualTo(resultString);
  }
}
