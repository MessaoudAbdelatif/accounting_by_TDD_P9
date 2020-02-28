package com.dummy.myerp.testbusiness.business;

import static org.assertj.core.api.Assertions.assertThat;

import com.dummy.myerp.business.contrat.BusinessProxy;
import com.dummy.myerp.business.impl.AbstractBusinessManager;
import com.dummy.myerp.business.impl.TransactionManager;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import java.math.BigDecimal;
import java.time.Year;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(locations = {
    "classpath:com/dummy/myerp/testbusiness/business/bootstrapContext.xml"})
public class BusinessIT extends AbstractBusinessManager {

  @Autowired
  BusinessProxy businessProxy;
  @Autowired
  DaoProxy daoProxy;
  @Autowired
  TransactionManager transactionManager;

  @BeforeEach
  public void setUp() {
    configure(businessProxy, daoProxy, transactionManager);
  }

  private EcritureComptable getFakeEcritureComptable(){
    List<CompteComptable> listCompteComptable = getBusinessProxy().getComptabiliteManager().getListCompteComptable();
    List<JournalComptable> listJournalComptable = getBusinessProxy().getComptabiliteManager().getListJournalComptable();

    JournalComptable journalComptable = listJournalComptable.get(0);
    CompteComptable compteComptable1 = listCompteComptable.get(0);
    CompteComptable compteComptable2 = listCompteComptable.get(1);

    EcritureComptable ecritureComptable = new EcritureComptable();

    ecritureComptable.setLibelle("FakeEcitureComptable");
    ecritureComptable.setJournal(journalComptable);
    Date date = new Date();
    date.setYear(116);
    ecritureComptable.setDate(date);

    LigneEcritureComptable firstLigne = new LigneEcritureComptable();

    firstLigne.setCompteComptable(compteComptable1);
    firstLigne.setLibelle("FakeLigneEcitureComptable");
    firstLigne.setDebit(new BigDecimal(10));

    LigneEcritureComptable secondLigne = new LigneEcritureComptable();

    secondLigne.setCompteComptable(compteComptable2);
    secondLigne.setLibelle("FakeLigneEcitureComptable");
    secondLigne.setCredit(new BigDecimal(10));

    ecritureComptable.getListLigneEcriture().addAll(Arrays.asList(firstLigne,secondLigne));

    return ecritureComptable;
  }



 /* @Test
  void updateEcritureComptable() throws NotFoundException {
    //given
    EcritureComptable ecritureComptable = daoProxy.getComptabiliteDao().getEcritureComptable(-4);
    String oldLibelle = ecritureComptable.getLibelle();
    ecritureComptable.setLibelle("TEST");
    System.out.println(ecritureComptable);
    //when
    daoProxy.getComptabiliteDao().updateEcritureComptable(ecritureComptable);
    //then
    EcritureComptable updatedEcritureComptable = daoProxy.getComptabiliteDao().getEcritureComptable(-4);
    assertThat(updatedEcritureComptable.getLibelle()).isEqualTo("TEST");
    //reset db-test
    updatedEcritureComptable.setLibelle(oldLibelle);

  }*/

 /* @Test
  void addReferenceIT() throws NotFoundException {
    //Given
    List<EcritureComptable> listEcriture = getBusinessProxy().getComptabiliteManager().getListEcritureComptable();
    EcritureComptable ecritureComptable = this.getFakeEcritureComptable();
    System.out.println(ecritureComptable);
    ecritureComptable.getListLigneEcriture().get(0).setDebit(new BigDecimal(-10));
    ecritureComptable.getListLigneEcriture().get(1).setCredit(new BigDecimal(-10));
    getBusinessProxy().getComptabiliteManager().addReference(ecritureComptable);
    try {
      //When
      getBusinessProxy().getComptabiliteManager().insertEcritureComptable(ecritureComptable);
    } catch (FunctionalException e) {
      e.getMessage();
    }finally {
      //Then
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(ecritureComptable.getDate());
      int yearInRef = calendar.get(Calendar.YEAR);
      assertThat(getBusinessProxy().getComptabiliteManager().getSequenceEcritureComptables(ecritureComptable.getJournal().getCode(), yearInRef));
      assertThat(getBusinessProxy().getComptabiliteManager().getListEcritureComptable().size()).isEqualTo(listEcriture.size()+1);

      getBusinessProxy().getComptabiliteManager().deleteEcritureComptable(ecritureComptable.getId());
    }

  }*/
}
