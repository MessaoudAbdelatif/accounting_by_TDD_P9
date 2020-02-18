package com.dummy.myerp.business.impl.manager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;

import com.dummy.myerp.business.contrat.BusinessProxy;
import com.dummy.myerp.business.impl.AbstractBusinessManager;
import com.dummy.myerp.business.impl.TransactionManager;
import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import java.math.BigDecimal;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ComptabiliteManagerImplNewTest extends AbstractBusinessManager {

  private EcritureComptable vEcritureComptable;

  @InjectMocks
  private ComptabiliteManagerImpl classUnderTest;

  @Mock
  ComptabiliteDao comptabiliteDao;

  @Mock
  DaoProxy daoProxy;

  @Mock
  BusinessProxy businessProxy;

  @Mock
  TransactionManager transactionManager;

  @BeforeEach
  public void setUp() {
    this.vEcritureComptable = new EcritureComptable();

    vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
    vEcritureComptable.setDate(new Date());
    vEcritureComptable.setLibelle("Libelle");
    vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
        null, new BigDecimal(123),
        null));
    vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
        null, null,
        new BigDecimal(123)));
  }

  private void initClassUnderTestDaoMock() {
    AbstractBusinessManager.configure(businessProxy, daoProxy, transactionManager);
    given(getDaoProxy().getComptabiliteDao()).willReturn(this.comptabiliteDao);
  }

  @Test
  final void getListCompteComptable() {
    initClassUnderTestDaoMock();

    classUnderTest.getListCompteComptable();
    then(getDaoProxy()).should(times(1)).getComptabiliteDao();
  }

  @Test
  void getListJournalComptable() {
    initClassUnderTestDaoMock();

    classUnderTest.getListJournalComptable();
    then(getDaoProxy()).should(times(1)).getComptabiliteDao();
  }

  @Test
  void getListEcritureComptable() {
    initClassUnderTestDaoMock();

    classUnderTest.getListEcritureComptable();
    then(getDaoProxy()).should(times(1)).getComptabiliteDao();
  }

  @Test
  void getSequenceEcritureComptables() {
    initClassUnderTestDaoMock();

    classUnderTest.getSequenceEcritureComptables(2019);
    then(getDaoProxy()).should(times(1)).getComptabiliteDao();

  }

  @Test
  public final void addReference() {
    initClassUnderTestDaoMock();

    classUnderTest.addReference(vEcritureComptable);
    then(getDaoProxy()).should(atLeast(1)).getComptabiliteDao();
  }

  @Test
  final void checkEcritureComptable() {
    //Add new LigneEcritureComptable that make our vEcritureComptable unbalance and triggers a FunctionalException.
    this.vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable());
    //Testing the checkEcritureComptable method.
    FunctionalException functionalException = assertThrows(FunctionalException.class,
        () -> classUnderTest.checkEcritureComptable(vEcritureComptable));
    //Checking the right FunctionalExceptionMessage.
    assertThat(functionalException.getMessage())
        .isEqualTo("L'écriture comptable ne respecte pas les règles de gestion.");
  }


  @Test
  public final void checkEcritureComptableUnit_regexPatternFail_thenTriggedFunctionalException() {

    vEcritureComptable.setReference("KSBCKQSBC");

    FunctionalException functionalException = assertThrows(FunctionalException.class,
        () -> classUnderTest.checkEcritureComptable(vEcritureComptable));

    assertThat(functionalException.getMessage())
        .isEqualTo("L'écriture comptable ne respecte pas les règles de gestion.");

  }

  @Test
  public final void checkEcritureComptableUnit_labelleSizeFail_thenTriggedFunctionalException() {

    vEcritureComptable.setLibelle("");

    FunctionalException functionalException = assertThrows(FunctionalException.class,
        () -> classUnderTest.checkEcritureComptable(vEcritureComptable));

    assertThat(functionalException.getMessage())
        .isEqualTo("L'écriture comptable ne respecte pas les règles de gestion.");

  }

  @Test
  public final void checkEcritureComptableUnit_RG2_UnBalanced_thenTriggedFunctionalException() {
    vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
        null, new BigDecimal(12),
        null));

    FunctionalException functionalException = assertThrows(FunctionalException.class,
        () -> classUnderTest.checkEcritureComptableUnit(vEcritureComptable));
    assertThat(functionalException.getMessage())
        .isEqualTo("L'écriture comptable n'est pas équilibrée.");

  }

  @Test
  public final void checkEcritureComptableUnit_RG3_atLeast2LignesOneDebitOneCredit_thenTriggedFunctionalException() {
    // Keep Balanced ListLigneEcriture by removing one line and adding new one with negative value.
    vEcritureComptable.getListLigneEcriture().remove(1);
    vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
        null, new BigDecimal(-123),
        null));

    FunctionalException functionalException = assertThrows(FunctionalException.class,
        () -> classUnderTest.checkEcritureComptableUnit(vEcritureComptable));
    assertThat(functionalException.getMessage())
        .isEqualTo(
            "L'écriture comptable doit avoir au moins deux lignes : une ligne au débit et une ligne au crédit.");
  }

//  @Test
//  public void checkEcritureComptableContext() {
//  }
//
//  @Test
//  public void insertEcritureComptable() {
//  }
//
//  @Test
//  public void updateEcritureComptable() {
//  }
//
//  @Test
//  public void deleteEcritureComptable() {
//  }
}
