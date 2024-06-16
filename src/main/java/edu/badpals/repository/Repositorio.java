package edu.badpals.repository;

import edu.badpals.domain.MagicalItem;
import edu.badpals.domain.Order;
import edu.badpals.domain.Wizard;
import edu.badpals.domain.WizardPerson;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class Repositorio {

    public Optional<Wizard> loadWizard(String name) {


        Optional<Wizard> wizard = Wizard.findByIdOptional(name);

        try {

            if (!wizard.isPresent()){
                throw new IllegalArgumentException(name + " no existe");
            }

        }catch (IllegalArgumentException exception){

            System.out.println(exception.getMessage());
            return Optional.empty();

        }


        return wizard;
    }

    public Optional<MagicalItem> loadItem(String name) {

        Optional<MagicalItem> magicalItem = MagicalItem.find("name = ?1", name).firstResultOptional();

        try {

            if (!magicalItem.isPresent()) {
                throw new IllegalArgumentException("El objeto no fue encontrado");
            }

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }

        return magicalItem;

    }

    public Optional<MagicalItem> loadItem(MagicalItem item) {

        Optional<MagicalItem> itemToFind = MagicalItem.find("name = ?1 and quality = ?2 and type = ?3", item.getName(), item.getQuality(), item.getType()).firstResultOptional();

        try {

            if (!itemToFind.isPresent()) {
                throw new IllegalArgumentException("El objeto no fue encontrado");
            }

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }

        return itemToFind;

    }

    public List<MagicalItem> loadItems(String name) {

        List<MagicalItem> allItemsList = MagicalItem.listAll();
        List<MagicalItem> filteredItemsList = new ArrayList<>();

        for (MagicalItem item : allItemsList) {

            if (item.getName().equals(name)) {

                filteredItemsList.add(item);

            }

        }

        try {

            if (filteredItemsList.isEmpty()) {
                throw new IllegalArgumentException("No se ha encontrado ningun objeto con ese nombre");
            }

        } catch (IllegalArgumentException e) {

            System.out.println(e.getMessage());

        }

        return filteredItemsList;
    }

    @Transactional
    public Optional<Order> placeOrder(String wizard, String item) {

        Optional<Wizard> wizardBuyer = Wizard.findByIdOptional(wizard);
        Optional<MagicalItem> itemToBuy = MagicalItem.find("name = ?1", item).firstResultOptional();

        try {

            if (wizardBuyer.get().getWizardPerson().equals(WizardPerson.MUDBLOOD)) {
                throw new IllegalArgumentException("El comprador es un Mudblood y no puede realizar la compra.");
            }

        }catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }

        try {

            if (!wizardBuyer.isPresent() && !itemToBuy.isPresent()) {
                throw new IllegalArgumentException("El Mago o el Item no existen");

            }
        } catch (IllegalArgumentException e) {

            System.out.printf(e.getMessage());
        }

        Order orden = new Order(wizardBuyer.get(), itemToBuy.get());
        orden.persist();
        return Optional.ofNullable(orden);

    }

    @Transactional
    public void createItem(String name, int quality, String type){

        MagicalItem itemToCreate = new MagicalItem(name,quality,type);
        itemToCreate.persist();
    }

    @Transactional
    public void createItems(List<MagicalItem> itemList) {

        for (MagicalItem item : itemList) {

            item.persist();
        }
    }

    @Transactional
    public void deleteItem(MagicalItem item) {

        Optional<MagicalItem> itemToDelete = MagicalItem.find("name = ?1 and quality ?2 and type =3", item.getName(), item.getQuality(), item.getType()).firstResultOptional();

        itemToDelete.get().delete();
    }


}
