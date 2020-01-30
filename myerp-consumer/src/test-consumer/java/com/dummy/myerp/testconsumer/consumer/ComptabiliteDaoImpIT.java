package com.dummy.myerp.testconsumer.consumer;


import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(locations = {
    "classpath:com/dummy/myerp/testconsumer/consumer/bootstrapContext.xml"})
public class ComptabiliteDaoImpIT extends ConsumerTestCase {

  private ComptabiliteDao dao = getDaoProxy().getComptabiliteDao();
  private EcritureComptable vEcritureComptable;

}
