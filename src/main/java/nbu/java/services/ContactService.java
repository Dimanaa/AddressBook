package nbu.java.services;

import nbu.java.exceptions.BadRequestException;
import nbu.java.exceptions.NotFoundException;
import nbu.java.dto.ContactDTO;
import nbu.java.repositories.ContactRepository;
import nbu.java.entity.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ContactService {

    ContactRepository<Contact> contactRepository;

    @Autowired
    public ContactService(ContactRepository<Contact> contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Transactional
    public List<Contact> findAll() {
        return contactRepository.findAll();
    }

    @Transactional
    public Contact findById(int id) throws NotFoundException {
        Contact contact = contactRepository.findById(id);
        if (contact == null) throw new NotFoundException("This contact doesn't exits.");
        return contact;
    }

    @Transactional
    public List<Contact> findByUserId(int id) {

        return contactRepository.findByUserId(id);
    }

    @Transactional
    public List<Contact> findByFirstnameAndLastnameAndUserId(String firstname, String lastname, int id) {

        return contactRepository.findByFirstnameAndLastnameAndUserId(firstname, lastname, id);
    }

    @Transactional
    public List<Contact> findBySameFirstnameAndDistinctLastname(int id) {

        return contactRepository.findBySameFirstnameAndDistinctLastname(id);
    }

    @Transactional
    public List<Contact> findBySameLastnameAndDistinctFirstname(int id) {

        return contactRepository.findBySameLastnameAndDistinctFirstname(id);
    }

    @Transactional
    public void addContact(Contact contact) throws BadRequestException {

        if (contact == null) {
            throw new BadRequestException("The value of the object is null.");
        }

        validate(contact.getFirstname());

        validate(contact.getLastname());

        validate(contact.getCompanyname());

        validate(contact.getAddress());

        validate(contact.getPhonenumber());

        validate(contact.getEmail());

        validate(contact.getFaxnumber());

        validate(contact.getMobilephone());

       if (contact.getUser() == null){}
        contactRepository.save(contact);

    }

    @Transactional
    public void deleteContact(Contact contact) {
        contactRepository.delete(contact);
    }

    @Transactional
    public void updateContact(int id, ContactDTO contactDTO) {

        Contact contact = contactRepository.findById(id);

        if (contactDTO.getFirstname() != null) {
            contact.setFirstname(contactDTO.getFirstname());
        }

        if (contactDTO.getLastname() != null) {
            contact.setLastname(contactDTO.getLastname());
        }

        if (contactDTO.getCompanyname() != null) {
            contact.setCompanyname(contactDTO.getCompanyname());
        }

        if (contactDTO.getAddress() != null) {
            contact.setAddress(contactDTO.getAddress());
        }

        if (contactDTO.getPhonenumber() != null) {
            contact.setPhonenumber(contactDTO.getPhonenumber());
        }

        if (contactDTO.getEmail() != null) {
            contact.setEmail(contactDTO.getEmail());
        }

        if (contactDTO.getFaxnumber() != null) {
            contact.setFaxnumber(contactDTO.getFaxnumber());
        }

        if (contactDTO.getMobilephone() != null) {
            contact.setMobilephone(contactDTO.getMobilephone());
        }

        if (contactDTO.getComment() != null) {
            contact.setComment(contactDTO.getComment());
        }

        if (contactDTO.getUser() != null) {
            contact.setUser(contactDTO.getUser());
        }

        contactRepository.save(contact);
    }

   private void validate(String value) throws BadRequestException {
        if (value == null) {
            throw new BadRequestException("The value of the object is null.");
        }
    }

    public List<Contact> getContactsWithMostCommonLabels(Integer userId) {
        Map<Contact.Label, List<Contact>> groupedContacts = findByUserId(userId)
                .stream()
                .collect(Collectors.groupingBy(Contact::getLabel));
        int maxSize = groupedContacts
                .values()
                .stream()
                .max((contacts1, contacts2) -> contacts1.size() - contacts2.size())
                .orElse(new ArrayList<>())
                .size();

        return groupedContacts
                .values()
                .stream()
                .filter(contacts -> contacts.size() == maxSize)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}
