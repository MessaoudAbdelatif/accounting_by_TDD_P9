package com.dummy.myerp.testconsumer.consumer.dao.contrat.impl.db.dao;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.db.AbstractDbConsumer;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import com.dummy.myerp.technical.exception.NotFoundException;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

// Spring 5 only
@SpringJUnitConfig(locations = {
    "classpath:com/dummy/myerp/testconsumer/consumer/bootstrapContext.xml"})
//Spring 4
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:com/dummy/myerp/testconsumer/consumer/bootstrapContext.xml")
public class ComptabiliteDaoImpIT extends AbstractDbConsumer {

  @Autowired
  private ComptabiliteDao dao;

  @Test
  void getListCompteComptableTest() {
    //connected to db-dev & get DATA
    assertThat(dao.getListCompteComptable().isEmpty()).isFalse();

  }

  @Test
  void getListCompteComptable() {
    List<CompteComptable> listCompteComptables = dao.getListCompteComptable();

    assertThat(listCompteComptables).isNotNull();
    assertThat(listCompteComptables).size().isGreaterThan(1);
  }

  @Test
  void getListJournalComptable() {
    List<JournalComptable> getListJournalComptables = dao.getListJournalComptable();

    assertThat(getListJournalComptables).isNotNull();
    assertThat(getListJournalComptables).size().isGreaterThan(1);

  }

  @Test
  void getListEcritureComptable() {
    List<EcritureComptable> getListEcritureComptables = dao.getListEcritureComptable();

    assertThat(getListEcritureComptables).isNotNull();
    assertThat(getListEcritureComptables).size().isGreaterThan(2);

  }

  @Test
  void getEcritureComptable() throws NotFoundException {
    EcritureComptable ecritureComptable = dao.getEcritureComptable(-1);
    assertThat(ecritureComptable).isNotNull();
  }

  @Test
  void getEcritureComptable_TriggedNotFoundException() {
    assertThrows(NotFoundException.class, () -> dao.getEcritureComptable(1));

  }

  @Test
  void getEcritureComptableByRef() throws NotFoundException {
    EcritureComptable ecritureComptable = dao.getEcritureComptableByRef("AC-2016/00001");
    assertThat(ecritureComptable).isNotNull();
  }

  @Test
  void getEcritureComptableByRef_TriggedNotFoundException() {
    assertThrows(NotFoundException.class, () -> dao.getEcritureComptableByRef("AC-1016/00001"));
  }

  @Test
  void insertEcritureComptable_triggedDataIntegrityViolationException() {
    //Given
    int sizeEcritureComptables = dao.getListEcritureComptable().size();
    EcritureComptable ecritureComptable = new EcritureComptable();
    JournalComptable journal = new JournalComptable();
    ecritureComptable.setJournal(journal);

    //Then
    assertThrows(DataIntegrityViolationException.class,
        () -> dao.insertEcritureComptable(ecritureComptable));
    //my DB records doesn't increase.
    assertThat(dao.getListEcritureComptable().size()).isEqualTo(sizeEcritureComptables);
  }

  @Test
  void insertEcritureComptable_WithListLigneEcritureComptable_ProcessingInsertIntoDB() {
    //Given

    EcritureComptable dummyEcritureComptable = new EcritureComptable();
    dummyEcritureComptable.setLibelle("Dummy");
    dummyEcritureComptable.setDate(new Date());

    JournalComptable dummeyJournal = new JournalComptable();
    dummeyJournal.setCode("AC");
    dummeyJournal.setLibelle("AchatTest");

    dummyEcritureComptable.setJournal(dummeyJournal);
    dummyEcritureComptable.setReference("AC-2016/00002");

    int sizeEC = dao.getListEcritureComptable().size();

    //When
    dao.insertEcritureComptable(dummyEcritureComptable);

    //Then
    assertThat(dummyEcritureComptable.getId()).isNotNull();
    assertThat(dao.getListEcritureComptable().size()).isEqualTo(sizeEC+1);

    /*
    Reset the db.
    */
    dao.deleteEcritureComptable(dummyEcritureComptable.getId());
  }

  @Test
  void updateEcritureComptable_replaceLibelle() throws NotFoundException {
    //Given
    EcritureComptable usedEcritureComptable = dao.getEcritureComptable(-1);
    String oldlibelle = usedEcritureComptable.getLibelle();
    String newLibelle = oldlibelle.concat("s");
    usedEcritureComptable.setLibelle(newLibelle);

    //When
    dao.updateEcritureComptable(usedEcritureComptable);

    //Then
    EcritureComptable updatedEcritureComptable = dao.getEcritureComptable(-1);
    assertThat(updatedEcritureComptable.getLibelle().equals(newLibelle)).isTrue();
    assertThat(updatedEcritureComptable.getLibelle().endsWith("s")).isTrue();
  }

  @Test
  void getSequenceEcritureComptable() throws NotFoundException {
    SequenceEcritureComptable sequenceEcritureComptable = dao.getSequenceEcritureComptable("AC", 2016);

    assertThat(sequenceEcritureComptable).isNotNull();
  }

  @Test
  void updateSequenceEcritureComptable() throws NotFoundException {
    //Given
    SequenceEcritureComptable oldSEC = dao.getSequenceEcritureComptable("AC", 2016);
    Integer derniereValeur = oldSEC.getDerniereValeur();
    int newDerniereValeur = derniereValeur + 1;
    oldSEC.setDerniereValeur(newDerniereValeur);
    //when
    System.out.println(oldSEC);
    dao.updateSequenceEcritureComptable(oldSEC);
    //Then
    SequenceEcritureComptable updateSEC = dao.getSequenceEcritureComptable("AC", 2016);
    assertThat(updateSEC).isNotNull();
    assertThat(updateSEC).isNotEqualTo(oldSEC);
  }

  @Test
  void insertSequenceEcritureComptable() throws NotFoundException {
    //Given
    SequenceEcritureComptable sequenceEcritureComptable = dao.getSequenceEcritureComptable("AC", 2016);
    sequenceEcritureComptable.setDerniereValeur(11);
    sequenceEcritureComptable.setAnnee(1901);
    //When
    dao.insertSequenceEcritureComptable(sequenceEcritureComptable);
    //Then
    assertThat(dao.getSequenceEcritureComptable("AC",1901)).isNotNull();
    //reset test-db
    dao.deleteSequenceEcritureComptable("AC",1901);

  }
}
