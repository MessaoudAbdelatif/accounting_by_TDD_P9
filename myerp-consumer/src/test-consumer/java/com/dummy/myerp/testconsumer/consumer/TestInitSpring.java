package com.dummy.myerp.testconsumer.consumer;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;


/**
 * Classe de test de l'initialisation du contexte Spring
 */
public class TestInitSpring extends ConsumerTestCase {

  /**
   * Constructeur.
   */
  public TestInitSpring() {
    super();
  }


  /**
   * Teste l'initialisation du contexte Spring
   */
  @Test
  public void testInit() {
    SpringRegistry.init();
    assertNotNull(SpringRegistry.getDaoProxy());
  }
}
