package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import com.dummy.myerp.consumer.dao.impl.cache.JournalComptableDaoCache;
import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class SequanceEcritureComptableRM implements RowMapper<SequenceEcritureComptable> {

  /** JournalComptableDaoCache */
  private final JournalComptableDaoCache journalComptableDaoCache = new JournalComptableDaoCache();

  @Override
  public SequenceEcritureComptable mapRow(ResultSet pRs, int pRowNum) throws SQLException {
    SequenceEcritureComptable vBean = new SequenceEcritureComptable();
    vBean.setJournalCode(journalComptableDaoCache.getByCode(pRs.getString("journal_code")));
    vBean.setAnnee(pRs.getInt("annee"));
    vBean.setDerniereValeur(pRs.getInt("derniere_valeur"));
    return vBean;
  }
}
