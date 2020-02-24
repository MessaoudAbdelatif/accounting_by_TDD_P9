package com.dummy.myerp.testconsumer.consumer.dao.contrat.impl.db.dao;


import static org.assertj.core.api.Assertions.assertThat;

import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.db.AbstractDbConsumer;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(locations = {
    "classpath:com/dummy/myerp/testconsumer/consumer/bootstrapContext.xml"})
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
    System.out.println(getListEcritureComptables);

  }

//  @Test
//  void getEcritureComptable(Integer pId) throws NotFoundException {
//    EcritureComptable ecritureComptable = dao.getEcritureComptable(-1);
//    System.out.println(ecritureComptable);
////    assertThat(ecritureComptable).isNotNull();
//
//  }

}
