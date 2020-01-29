package com.dummy.myerp.business.impl.manager;

import static org.mockito.Mockito.when;

import com.dummy.myerp.business.contrat.BusinessProxy;
import com.dummy.myerp.business.impl.AbstractBusinessManager;
import com.dummy.myerp.business.impl.TransactionManager;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import java.math.BigDecimal;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ComptabiliteManagerImplTest {

  private EcritureComptable vEcritureComptable;
  private ComptabiliteManagerImpl manager;

  @Mock
  private DaoProxy daoProxy;

  @Mock
  private BusinessProxy businessProxy;

  @Before
  public void setUp() {

    this.vEcritureComptable = new EcritureComptable();
    this.manager = new ComptabiliteManagerImpl();
    AbstractBusinessManager.configure(businessProxy, daoProxy, TransactionManager.getInstance());
  }

  @Test
  public void checkEcritureComptableUnit() throws Exception {

    vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
    vEcritureComptable.setDate(new Date());
    vEcritureComptable.setLibelle("Libelle");
//        vEcritureComptable.setReference("AC-2020/00001");
    vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
        null, new BigDecimal(123),
        null));
    vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
        null, null,
        new BigDecimal(123)));

    manager.checkEcritureComptableUnit(vEcritureComptable);
  }

  @org.junit.Test(expected = FunctionalException.class)
  public void checkEcritureComptableUnitViolation() throws Exception {
    EcritureComptable vEcritureComptable;
    vEcritureComptable = new EcritureComptable();
    manager.checkEcritureComptableUnit(vEcritureComptable);
  }

  @org.junit.Test(expected = FunctionalException.class)
  public void checkEcritureComptableUnitRG2() throws Exception {
//    EcritureComptable vEcritureComptable;
//    vEcritureComptable = new EcritureComptable();
    vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
    vEcritureComptable.setDate(new Date());
    vEcritureComptable.setLibelle("Libelle");
    vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
        null, new BigDecimal(123),
        null));
    vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
        null, null,
        new BigDecimal(1234)));
    manager.checkEcritureComptableUnit(vEcritureComptable);
  }

  @org.junit.Test(expected = FunctionalException.class)
  public void checkEcritureComptableUnitRG3() throws Exception {
//    EcritureComptable vEcritureComptable;
//    vEcritureComptable = new EcritureComptable();
    vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
    vEcritureComptable.setDate(new Date());
    vEcritureComptable.setLibelle("Libelle");
    vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
        null, new BigDecimal(123),
        null));
    vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
        null, new BigDecimal(123),
        null));
    manager.checkEcritureComptableUnit(vEcritureComptable);
  }
}
