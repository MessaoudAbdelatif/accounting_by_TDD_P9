package com.dummy.myerp.business.impl.manager;

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
    AbstractBusinessManager.configure(businessProxy, daoProxy, transactionManager);
    given(getDaoProxy().getComptabiliteDao()).willReturn(this.comptabiliteDao);

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

  @Test
  void getListCompteComptable() {
    classUnderTest.getListCompteComptable();
    then(getDaoProxy()).should(times(1)).getComptabiliteDao();
  }

  @Test
  void getListJournalComptable() {
    classUnderTest.getListJournalComptable();
    then(getDaoProxy()).should(times(1)).getComptabiliteDao();
  }

  @Test
  void getListEcritureComptable() {
    classUnderTest.getListEcritureComptable();
    then(getDaoProxy()).should(times(1)).getComptabiliteDao();
  }

  @Test
  void getSequenceEcritureComptables() {
    classUnderTest.getSequenceEcritureComptables(2019);
    then(getDaoProxy()).should(times(1)).getComptabiliteDao();

  }

  @Test
  public void addReference() {

    classUnderTest.addReference(vEcritureComptable);
    then(getDaoProxy()).should(atLeast(3)).getComptabiliteDao();

  }


//  @Test
//  public void checkEcritureComptableUnit() {
//  }
//
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
