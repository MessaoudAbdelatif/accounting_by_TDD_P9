package com.dummy.myerp.testbusiness.business;

import com.dummy.myerp.business.contrat.BusinessProxy;
import com.dummy.myerp.business.impl.AbstractBusinessManager;
import com.dummy.myerp.business.impl.TransactionManager;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import org.junit.jupiter.api.BeforeAll;
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

  @BeforeAll
  public void setUp() {
    configure(businessProxy,daoProxy,transactionManager);
  }


  }
