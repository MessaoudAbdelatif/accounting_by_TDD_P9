package com.dummy.myerp.model.bean.comptabilite;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Bean représentant une séquence pour les références d'écriture comptable
 */
public class SequenceEcritureComptable {

  // ==================== Attributs ====================
  /**
   * L'année
   */
  private Integer annee;

  /**
   * La dernière valeur utilisée
   */
  private Integer derniereValeur;

  /**
   * Code du journal comptable PFK
   **/
  @NotNull
  @Size(max = 5)
  private JournalComptable journalCode;

  // ==================== Constructeurs ====================

  /**
   * Constructeur
   */
  public SequenceEcritureComptable() {
  }

  /**
   * Constructeur
   *
   * @param pAnnee -
   * @param pDerniereValeur -
   * @param pJournalCode -
   */
  public SequenceEcritureComptable(Integer pAnnee, Integer pDerniereValeur,
      JournalComptable pJournalCode) {
    annee = pAnnee;
    derniereValeur = pDerniereValeur;
    journalCode = pJournalCode;
  }


  // ==================== Getters/Setters ====================
  public Integer getAnnee() {
    return annee;
  }

  public void setAnnee(Integer pAnnee) {
    annee = pAnnee;
  }

  public Integer getDerniereValeur() {
    return derniereValeur;
  }

  public void setDerniereValeur(Integer pDerniereValeur) {
    derniereValeur = pDerniereValeur;
  }

  public JournalComptable getJournalCode() {
    return journalCode;
  }

  public void setJournalCode(JournalComptable journalCode) {
    this.journalCode = journalCode;
  }

  // ==================== Méthodes ====================
  @Override
  public String toString() {
    final StringBuilder vStB = new StringBuilder(this.getClass().getSimpleName());
    final String vSEP = ", ";
    vStB.append("{")
        .append("annee=").append(annee)
        .append(vSEP).append("derniereValeur=").append(derniereValeur)
        .append(vSEP).append("journalCode=").append(journalCode)
        .append("}");
    return vStB.toString();
  }

}
