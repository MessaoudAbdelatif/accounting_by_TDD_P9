package com.dummy.myerp.testbusiness.business;

import static org.assertj.core.api.Assertions.assertThat;

import com.dummy.myerp.business.contrat.BusinessProxy;
import com.dummy.myerp.business.impl.AbstractBusinessManager;
import com.dummy.myerp.business.impl.TransactionManager;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.junit.jupiter.api.BeforeAll;
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
    configure(businessProxy,daoProxy,transactionManager);
  }

  @Test
  void updateEcritureComptable() throws NotFoundException {
    //given
    EcritureComptable ecritureComptable = daoProxy.getComptabiliteDao().getEcritureComptable(-4);
    String oldLibelle = ecritureComptable.getLibelle();
    ecritureComptable.setLibelle("TEST");
    //when
    daoProxy.getComptabiliteDao().updateEcritureComptable(ecritureComptable);
    //then
    EcritureComptable updatedEcritureComptable = daoProxy.getComptabiliteDao().getEcritureComptable(-4);
    assertThat(updatedEcritureComptable.getLibelle()).isEqualTo("TEST");
    //reset db-test
    updatedEcritureComptable.setLibelle(oldLibelle);

  }
}
