package com.dummy.myerp.consumer.dao.contrat;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import com.dummy.myerp.technical.exception.NotFoundException;
import java.util.List;


/**
 * Interface de DAO des objets du package Comptabilite
 */
public interface ComptabiliteDao {

  /**
   * Renvoie la liste des Comptes Comptables
   *
   * @return {@link List}
   */
  List<CompteComptable> getListCompteComptable();


  /**
   * Renvoie la liste des Journaux Comptables
   *
   * @return {@link List}
   */
  List<JournalComptable> getListJournalComptable();

  /**
   * Renvoie la liste des Séquences d'écritures comptables
   *
   * @param code: code du journal comptable associe à la séquence d'écriture en questiion.
   * @param year: l'année de la persistance de la séquence d'écriture comptable.
   * @return {@link SequenceEcritureComptable}
   **/
  SequenceEcritureComptable getSequenceEcritureComptable(String code, Integer year)
      throws NotFoundException;

  // ==================== EcritureComptable ====================

  /**
   * Renvoie la liste des Écritures Comptables
   *
   * @return {@link List}
   */
  List<EcritureComptable> getListEcritureComptable();

  /**
   * Renvoie l'Écriture Comptable d'id {@code pId}.
   *
   * @param pId l'id de l'écriture comptable
   * @return {@link EcritureComptable}
   * @throws NotFoundException : Si l'écriture comptable n'est pas trouvée
   */
  EcritureComptable getEcritureComptable(Integer pId) throws NotFoundException;

  /**
   * Renvoie l'Écriture Comptable de référence {@code pRef}.
   *
   * @param pReference la référence de l'écriture comptable
   * @return {@link EcritureComptable}
   * @throws NotFoundException : Si l'écriture comptable n'est pas trouvée
   */
  EcritureComptable getEcritureComptableByRef(String pReference) throws NotFoundException;

  /**
   * Charge la liste des lignes d'écriture de l'écriture comptable {@code pEcritureComptable}
   *
   * @param pEcritureComptable -
   */
  void loadListLigneEcriture(EcritureComptable pEcritureComptable);

  /**
   * Insert une nouvelle écriture comptable.
   *
   * @param pEcritureComptable -
   */
  void insertEcritureComptable(EcritureComptable pEcritureComptable);

  /**
   * Met à jour l'écriture comptable.
   *
   * @param pEcritureComptable -
   */
  void updateEcritureComptable(EcritureComptable pEcritureComptable);

  /**
   * Supprime l'écriture comptable d'id {@code pId}.
   *
   * @param pId l'id de l'écriture
   */
  void deleteEcritureComptable(Integer pId);


  void updateSequenceEcritureComptable(SequenceEcritureComptable sequenceEcritureComptable);

  void insertSequenceEcritureComptable(SequenceEcritureComptable sequenceEcritureComptable);

  void deleteSequenceEcritureComptable(String journalCode,Integer year);
}
