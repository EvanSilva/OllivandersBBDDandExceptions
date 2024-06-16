package edu.badpals.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table (name = "t_wizards")
public class Wizard extends PanacheEntityBase {

    @Id
    @Column (name = "wizard_name")
    private String name = "";

    @Column (name = "wizard_dexterity")
    private int dexterity = 0;

    @Enumerated(EnumType.STRING)
    @Column (name = "wizard_person")
    private WizardPerson wizardPerson = null;

    public Wizard() {
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WizardPerson getWizardPerson() {
        return wizardPerson;
    }

    public void setWizardPerson(WizardPerson wizardPerson) {
        this.wizardPerson = wizardPerson;
    }

    @Override
    public String toString() {
        return "Wizard{" +
                "dexterity=" + dexterity +
                ", name='" + name + '\'' +
                ", wizardPerson=" + wizardPerson +
                '}';
    }
}
