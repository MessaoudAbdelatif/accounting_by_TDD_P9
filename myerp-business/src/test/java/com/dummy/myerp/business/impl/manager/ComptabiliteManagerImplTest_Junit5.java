package com.dummy.myerp.business.impl.manager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import com.dummy.myerp.business.contrat.BusinessProxy;
import com.dummy.myerp.business.impl.AbstractBusinessManager;
import com.dummy.myerp.business.impl.TransactionManager;
import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import java.math.BigDecimal;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("ComptabiliteManagerImpl == classUnderTest")
@ExtendWith(MockitoExtension.class)
public class ComptabiliteManagerImplTest_Junit5 extends AbstractBusinessManager {

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

    classUnderTest.getSequenceEcritureComptables("AC", 2019);
    then(getDaoProxy()).should(times(1)).getComptabiliteDao();

  }

  @Test
  public final void addReference_callAtLeastOneTimeDAO() {
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

  //remplace the ignored checkEcritureComptableUnit().
  @Test
  public void checkEcritureComptableUnit() throws Exception {
    initClassUnderTestDaoMock();
    SequenceEcritureComptable testSEC = new SequenceEcritureComptable();
    testSEC.setDerniereValeur(1);
    when(daoProxy.getComptabiliteDao().getSequenceEcritureComptable(any(), any()))
        .thenReturn(testSEC);

    vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
    vEcritureComptable.setDate(new Date());
    vEcritureComptable.setLibelle("Libelle");
    vEcritureComptable.setReference("AC-2020/00001");
    vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
        null, new BigDecimal(123),
        null));
    vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
        null, null,
        new BigDecimal(123)));

    classUnderTest.checkEcritureComptableUnit(vEcritureComptable);
  }

  @Test
  void checkEcritureComptableUnit_RG5_whenReferenceIsNull_thenTriggedFonctionalException() {
    vEcritureComptable.setReference(null);
    FunctionalException functionalException = assertThrows(FunctionalException.class,
        () -> classUnderTest.checkEcritureComptableUnit(vEcritureComptable));
    assertThat("La référence de l'écriture ne peut pas être nulle.")
        .isEqualTo(functionalException.getMessage());
  }

  @Test
  void checkEcritureComptableUnit_RG5_whenReferencePartOneCodeJournalIsWrong_thenTriggedFonctionalException() {
    vEcritureComptable.setReference("AZ-2020/00001");

    FunctionalException functionalException = assertThrows(FunctionalException.class,
        () -> classUnderTest.checkEcritureComptableUnit(vEcritureComptable));

    assertThat("La référence de l'écriture comptable :" + "AZ"
        + " ne correspond pas au code journal " + vEcritureComptable.getJournal()
        .getCode()).isEqualTo(functionalException.getMessage());
  }

  @Test
  void checkEcritureComptableUnit_RG5_whenReferencePartTwoYearIsWrong_thenTriggedFonctionalException() {
    vEcritureComptable.setReference("AC-2019/00001");

    FunctionalException functionalException = assertThrows(FunctionalException.class,
        () -> classUnderTest.checkEcritureComptableUnit(vEcritureComptable));

    assertThat(
        "La référence de l'écriture comptable : " + "2019"
            + " ne correspond pas à l'année' d'écriture " + "2020")
        .isEqualTo(functionalException.getMessage());
  }

  @Test
  void checkEcritureComputableUnit_RG5_whenReferencePartThreeIsWrong_thenTriggerFunctionalException() {
    initClassUnderTestDaoMock();
    vEcritureComptable.setReference("AC-2020/00003");

    JournalComptable journalComptable = new JournalComptable("AC", "Dummy");
    SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable(2020, 2,
        journalComptable);
    //Given
    given(comptabiliteDao.getSequenceEcritureComptable(anyString(), anyInt()))
        .willReturn(sequenceEcritureComptable);

    //When
    FunctionalException functionalException = assertThrows(FunctionalException.class,
        () -> classUnderTest.checkEcritureComptableUnit(vEcritureComptable));

    //Then
    assertThat("Le numéro de séquence de l'écriture " + "00003"
        + " ne correspond pas à la dernière séquence du journal "
        + "2").isEqualTo(functionalException.getMessage());
  }

  @Test
  void checkEcritureComputableContext_RG6_UniqueReferenceRule_NotRespected_TriggerFunctionalException()
      throws NotFoundException {

    initClassUnderTestDaoMock();

    EcritureComptable fakeEcritureComptable = new EcritureComptable();
    fakeEcritureComptable.setId(1);
    fakeEcritureComptable.setReference("AC-2020/00001");

    given(comptabiliteDao.getEcritureComptableByRef(anyString())).willReturn(fakeEcritureComptable);

    vEcritureComptable.setReference("AC-2020/00001");

    FunctionalException functionalException = assertThrows(FunctionalException.class,
        () -> classUnderTest.checkEcritureComptableContext(vEcritureComptable));

    assertThat("Une autre écriture comptable existe déjà avec la même référence.")
        .isEqualTo(functionalException.getMessage());

  }

  @Test
  void checkEcritureComputableContext_RG6_UniqueReferenceRule_Passing()
      throws FunctionalException, NotFoundException {
    initClassUnderTestDaoMock();

    EcritureComptable fakeEcritureComptable = new EcritureComptable();
    fakeEcritureComptable.setId(1);
    fakeEcritureComptable.setReference("AC-2020/00001");

    given(comptabiliteDao.getEcritureComptableByRef(anyString())).willReturn(fakeEcritureComptable);

    vEcritureComptable.setId(1);
    vEcritureComptable.setReference("AC-2020/00001");

    classUnderTest.checkEcritureComptableContext(vEcritureComptable);
  }

  @Test
  void checkEcritureComputableContext_RG6_TriggedNotFoundException()
      throws NotFoundException, FunctionalException {
    initClassUnderTestDaoMock();

    when(comptabiliteDao.getEcritureComptableByRef(anyString())).thenThrow(NotFoundException.class);
    vEcritureComptable.setReference("AC-2020/00001");

    classUnderTest.checkEcritureComptableContext(vEcritureComptable);

  }

  @Test
  void checkEcritureComptable_passedAfterAddReference() throws FunctionalException, NotFoundException {
    initClassUnderTestDaoMock();

    JournalComptable journalComptable = new JournalComptable("AC", "Dummy");
    SequenceEcritureComptable sequenceEcritureComptable2 = new SequenceEcritureComptable(2020, 2,
        journalComptable);
    SequenceEcritureComptable sequenceEcritureComptable3 = new SequenceEcritureComptable(2020, 3,
        journalComptable);
    given(comptabiliteDao.getSequenceEcritureComptable(any(String.class), any(Integer.class)))
        .willReturn(sequenceEcritureComptable2, sequenceEcritureComptable3);

    when(comptabiliteDao.getEcritureComptableByRef(anyString())).thenThrow(NotFoundException.class);
    classUnderTest.addReference(vEcritureComptable);
    System.out.println(vEcritureComptable);
    vEcritureComptable.setReference("AC-2020/00003");
    classUnderTest.checkEcritureComptable(vEcritureComptable);
  }
}
