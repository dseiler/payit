package ch.truesolutions.payit.model;

public interface ConfigChangeListener {
    void applyLanguageChange();
    void setLookAndFeel(String lnfName);
}