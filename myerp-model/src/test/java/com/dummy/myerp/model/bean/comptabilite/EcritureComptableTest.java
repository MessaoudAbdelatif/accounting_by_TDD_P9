package com.dummy.myerp.model.bean.comptabilite;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;


public class EcritureComptableTest {

  EcritureComptable vEcriture;

  private LigneEcritureComptable createLigne(Integer pCompteComptableNumero, String pDebit,
      String pCredit) {
    BigDecimal vDebit = pDebit == null ? null : new BigDecimal(pDebit);
    BigDecimal vCredit = pCredit == null ? null : new BigDecimal(pCredit);
    String vLibelle = ObjectUtils.defaultIfNull(vDebit, BigDecimal.ZERO)
        .subtract(ObjectUtils.defaultIfNull(vCredit, BigDecimal.ZERO)).toPlainString();
    LigneEcritureComptable vRetour = new LigneEcritureComptable(
        new CompteComptable(pCompteComptableNumero),
        vLibelle,
        vDebit, vCredit);
    return vRetour;
  }

  @Before
  public void start() {

    vEcriture = new EcritureComptable();
  }

  @BeforeEach
  void setUp() {
    vEcriture = new EcritureComptable();
  }

  @Test
  public void isEquilibree() {

    vEcriture.setLibelle("Equilibrée");
    vEcriture.getListLigneEcriture().add(this.createLigne(1, "200.50", null));
    vEcriture.getListLigneEcriture().add(this.createLigne(1, "100.50", "33.00"));
    vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "200.50"));
    vEcriture.getListLigneEcriture().add(this.createLigne(2, "33.00", "100.50"));
    Assert.assertTrue(vEcriture.toString(), vEcriture.isEquilibree());

    vEcriture.getListLigneEcriture().clear();
    vEcriture.setLibelle("Non équilibrée");
    vEcriture.getListLigneEcriture().add(this.createLigne(1, "10.00", null));
    vEcriture.getListLigneEcriture().add(this.createLigne(1, "20.00", "1.00"));
    vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "20.00"));
    vEcriture.getListLigneEcriture().add(this.createLigne(2, "1.00", "2.00"));
    Assert.assertFalse(vEcriture.toString(), vEcriture.isEquilibree());
  }

  @org.junit.jupiter.api.Test
  void getTotalDebit() {
    BigDecimal TROIS_CENT_UN = BigDecimal.valueOf(301);
    vEcriture.getListLigneEcriture().clear();
    vEcriture.setLibelle("Test_Debit");
    vEcriture.getListLigneEcriture().add(this.createLigne(1, "200", null));
    vEcriture.getListLigneEcriture().add(this.createLigne(1, "101", "33"));

    assertThat(vEcriture.getTotalDebit()).isEqualTo(TROIS_CENT_UN);
  }
}
