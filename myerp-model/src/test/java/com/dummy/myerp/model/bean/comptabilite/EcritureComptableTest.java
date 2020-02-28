package com.dummy.myerp.model.bean.comptabilite;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class EcritureComptableTest {

  private EcritureComptable vEcriture;


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

  @BeforeEach
  void startJunit5() {
    this.vEcriture = new EcritureComptable();
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

  @Test
  void getTotalDebit() {
    BigDecimal TROIS_CENT_UN = BigDecimal.valueOf(301);
    vEcriture.getListLigneEcriture().clear();
    vEcriture.setLibelle("Test_Debit");
    vEcriture.getListLigneEcriture().add(this.createLigne(1, "200", null));
    vEcriture.getListLigneEcriture().add(this.createLigne(1, "101", "33"));

    assertThat(vEcriture.getTotalDebit()).as("The Sum of all debit colon !")
        .isEqualTo(TROIS_CENT_UN);

    vEcriture.getListLigneEcriture().clear();
    vEcriture.setLibelle("Test_Debit");
    vEcriture.getListLigneEcriture().add(this.createLigne(1, "200", null));
    vEcriture.getListLigneEcriture().add(this.createLigne(1, "100", "33"));

    assertThat(vEcriture.getTotalDebit()).as("The Sum of all debit colon !")
        .isLessThan(TROIS_CENT_UN);
  }

  @Test
  public void toStringTest(){
    EcritureComptable ecritureComptable = new EcritureComptable();
    ecritureComptable.setLibelle("fakeLibelle");
    ecritureComptable.setJournal(new JournalComptable("dummyCode", "dummyLibelle"));
    ecritureComptable.setId(1);
    ecritureComptable.setDate(new Date());
    ecritureComptable.setReference("AA-0000-00000");
    String sep = ", ";
    String expectedString = "EcritureComptable{id="+ecritureComptable.getId()
        +sep+"journal="+ecritureComptable.getJournal()
        +sep+"reference='"+ecritureComptable.getReference()+"'"
        +sep+"date="+ecritureComptable.getDate()
        +sep+"libelle='"+ecritureComptable.getLibelle()+"'"
        +sep+"totalDebit="+ecritureComptable.getTotalDebit().toPlainString()
        +sep+"totalCredit="+ecritureComptable.getTotalCredit().toPlainString()
        +sep+"listLigneEcriture=[\n"+
        StringUtils.join(ecritureComptable.getListLigneEcriture(), "\n")+
        "\n]}";
    String resultString = ecritureComptable.toString();
    Assert.assertEquals(expectedString, resultString);
  }
}
