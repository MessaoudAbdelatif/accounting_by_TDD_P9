package com.dummy.myerp.testconsumer.consumer.dao.contrat.impl.db.dao;


import static org.assertj.core.api.Assertions.assertThat;

import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.testconsumer.consumer.ConsumerTestCase;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(locations = {
    "classpath:com/dummy/myerp/testconsumer/consumer/bootstrapContext.xml"})
public class ComptabiliteDaoImpIT extends ConsumerTestCase {

  private ComptabiliteDao dao = getDaoProxy().getComptabiliteDao();


  @Test
  void getListCompteComptableTest() {
    //connected to db-dev & get DATA
    // test
    assertThat(dao.getListCompteComptable().isEmpty()).isFalse();
  }
}
